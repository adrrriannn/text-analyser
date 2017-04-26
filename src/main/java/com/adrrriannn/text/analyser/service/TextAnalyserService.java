package com.adrrriannn.text.analyser.service;

import com.adrrriannn.text.analyser.entity.TextAnalyserResultEntity;
import com.adrrriannn.text.analyser.exception.ExceptionCode;
import com.adrrriannn.text.analyser.exception.InternalServerError;
import com.adrrriannn.text.analyser.model.ParagraphAnalysisResult;
import com.adrrriannn.text.analyser.repository.TextAnalyserResultEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

/**
 * Created by adrian on 24/04/2017.
 */

@Service
public class TextAnalyserService {
    
    private static final Logger log = LoggerFactory.getLogger(TextAnalyserService.class);
    
    private static final String START_PARAGRAPH_DELIMITER = "<p>";

    //Assuming that every paragraph ends with a point
    private static final String END_PARAGRAPH_DELIMITER = ".</p>\r"; 
    
    private TextAnalyserResultEntityRepository textAnalyserResultRepository;
    private ThreadPoolExecutor threadPoolExecutor;
    private RandomTextAPIService randomTextAPIService;
    
    @Autowired
    public TextAnalyserService(RandomTextAPIService randomTextAPIService,
                               TextAnalyserResultEntityRepository textAnalyserResultRepository) {
        this.randomTextAPIService = randomTextAPIService;
        this.textAnalyserResultRepository = textAnalyserResultRepository;
        this.threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }
    
    public TextAnalyserResultEntity analyseText(Integer startParagraphs, Integer endParagraphs, Integer minWordsSentence,
                                          Integer maxWordsSentence) throws InternalServerError{

        try {
            long before = System.currentTimeMillis();
            
            CopyOnWriteArrayList<ParagraphAnalysisResult> paragraphAnalysisResultList = new CopyOnWriteArrayList<>();
    
            final CountDownLatch latch = new CountDownLatch(endParagraphs - startParagraphs + 1);
            
            for(int paragraphs = startParagraphs; paragraphs <= endParagraphs; paragraphs++) {
                
                final int currentParagraphs = paragraphs;
                threadPoolExecutor.execute(() -> {
                    String randomText = randomTextAPIService.getRandomText(currentParagraphs, minWordsSentence, maxWordsSentence);
                    paragraphAnalysisResultList.add(analyseText(randomText, latch));
                    
                });
            }
            
            latch.await();
            
            ParagraphAnalysisResult paragraphAnalysisResult = ParagraphAnalysisResult.join(paragraphAnalysisResultList);
            
            long after = System.currentTimeMillis();
            
            long total = after - before;
    
            TextAnalyserResultEntity textAnalyserResultEntity = paragraphAnalysisResult.toTextAnalyserResult(total);
            return textAnalyserResultRepository.save(textAnalyserResultEntity);
        } catch(Exception ex) {
            log.error("Error analysing text. Exception {}", ex);
            throw new InternalServerError("An internal server error occurred. Please try again later", ExceptionCode.INTERNAL_SERVER_ERROR);
        }
        
    }
    
    protected ParagraphAnalysisResult analyseText(String inputText, CountDownLatch latch) {

        List<String> paragraphs = preProcessText(inputText);
        ParagraphAnalysisResult paragraphAnalysisResults = analyseText(paragraphs);
        latch.countDown();
        
        return paragraphAnalysisResults;
    }
    
    protected ParagraphAnalysisResult analyseText(List<String> paragraphs) {

        ParagraphAnalysisResult paragraphAnalysisResult;
        try {
            final CountDownLatch innerLatch = new CountDownLatch(paragraphs.size());
            CopyOnWriteArrayList<ParagraphAnalysisResult> list = new CopyOnWriteArrayList<>();

            paragraphs.forEach(
                paragraph -> 
                    threadPoolExecutor.execute(
                        () ->{
                            list.add(analyseParagraph(paragraph));
                            innerLatch.countDown();
                        }
                    )
                );
            
            innerLatch.await();
            
            paragraphAnalysisResult = ParagraphAnalysisResult.join(list); 
        } catch (InterruptedException ex) {
            log.error("Exception processing analysing paragraphs : {}, exception : {}", paragraphs, ex);
            paragraphAnalysisResult = new ParagraphAnalysisResult();
        }

        return paragraphAnalysisResult;
    }
    
    protected ParagraphAnalysisResult analyseParagraph(String paragraph) {
        
        long before = System.currentTimeMillis();
        
        paragraph = paragraph.toLowerCase();
        String[] words = paragraph.split(" ");
        
        Map<String, Long> wordsUses = Arrays.stream(words)
            .collect(
                Collectors.groupingBy(
                    x -> x,
                    Collectors.counting()
                )
            );
        
        long after = System.currentTimeMillis();
        
        long total = after - before;
        
        return new ParagraphAnalysisResult(wordsUses, words.length, total, 1);
    }
    
    protected List<String> preProcessText(String inputText) {
        
        return stream(inputText.split(END_PARAGRAPH_DELIMITER))
            .map(paragraph -> paragraph.replace(START_PARAGRAPH_DELIMITER, ""))
            .collect(Collectors.toList());
    }
    
    public Iterable<TextAnalyserResultEntity> getLast10Results() {
        return textAnalyserResultRepository.findTop10ByOrderByCreatedDesc();
    }
    
}
