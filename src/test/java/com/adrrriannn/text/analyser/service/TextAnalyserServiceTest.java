package com.adrrriannn.text.analyser.service;

import com.adrrriannn.text.analyser.entity.TextAnalyserResultEntity;
import com.adrrriannn.text.analyser.model.ParagraphAnalysisResult;
import com.adrrriannn.text.analyser.repository.TextAnalyserResultEntityRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doReturn;

/**
 * Created by adrian on 26/04/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TextAnalyserServiceTest {
    
    @Mock
    RandomTextAPIService randomTextAPIService;
    
    TextAnalyserService textAnalyserService;
    
    @Autowired
    TextAnalyserResultEntityRepository textAnalyserResultEntityRepository;

    private static final String oneParagraph = "<p>Sold contrary a this.</p>\r";
    private static final String twoParagraph = "<p>Thus fish purred less.</p>\r<p>Squid oh that and.</p>\r";
    private static final String threeParagraph = "<p>Up glib sensual reasonably the.</p>\r<p>Artificially despicably felicitously less alongside.</p>\r<p>Yet that.</p>\r";
    private static final String lotsOfTestParagraph = "<p>Test glib test reasonably the.</p>\r<p>Test despicably felicitously less alongside.</p>\r<p>Yet that.</p>\r";
    
    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        
        textAnalyserService = new TextAnalyserService(randomTextAPIService, textAnalyserResultEntityRepository);
    }

    @Test
    public void analyseTextParentTest() throws Exception {
        
        doReturn(oneParagraph).when(randomTextAPIService).getRandomText(1, 2, 5);
        doReturn(twoParagraph).when(randomTextAPIService).getRandomText(2, 2, 5);
        doReturn(threeParagraph).when(randomTextAPIService).getRandomText(3, 2, 5);
        
        TextAnalyserResultEntity textAnalyserResultEntity = textAnalyserService.analyseText(1,3,2,5);
        Assert.assertFalse(textAnalyserResultEntity.getMostFrequentWord() == null);
        Assert.assertFalse(textAnalyserResultEntity.getAverageParagraphProcessingTime() == null);
        Assert.assertFalse(textAnalyserResultEntity.getAverageParagraphSize() == null);
        Assert.assertTrue(textAnalyserResultEntity.getTotalProcessingTime() > 0);
    }
    
    @Test
    public void analyseTextTest() {
        
        ParagraphAnalysisResult paragraphAnalysisResult = textAnalyserService.analyseText(lotsOfTestParagraph, new CountDownLatch(3));
        Assert.assertTrue(paragraphAnalysisResult.getParagraphQuantity().equals(3));
    }
    
    @Test
    public void analyseParagraphsTest() {
        
        List<String> paragraphs = textAnalyserService.preProcessText(lotsOfTestParagraph); 
        
        ParagraphAnalysisResult paragraphAnalysisResult = textAnalyserService.analyseText(paragraphs);
        TextAnalyserResultEntity textAnalyserResultEntity = paragraphAnalysisResult.toTextAnalyserResult(0L);
        
        Assert.assertTrue(textAnalyserResultEntity.getMostFrequentWord().equals("test"));
    }

    @Test
    public void analyseParagraphTest() {

        String oneParagraph = "<p>Sold a contrary a this.</p>\r";
        
        ParagraphAnalysisResult paragraphAnalysisResult = textAnalyserService.analyseParagraph(oneParagraph);
        
        Assert.assertTrue(paragraphAnalysisResult.getParagraphQuantity().equals(1));
        
        TextAnalyserResultEntity textAnalyserResultEntity = paragraphAnalysisResult.toTextAnalyserResult(0L);
        Assert.assertTrue(textAnalyserResultEntity.getMostFrequentWord().equals("a"));
    }
    
    @Test
    public void preProcessTextTest() {

        List<String> oneParagraphList = textAnalyserService.preProcessText(oneParagraph);
        Assert.assertTrue(oneParagraphList.size() == 1);

        List<String> twoParagraphList = textAnalyserService.preProcessText(twoParagraph);
        Assert.assertTrue(twoParagraphList.size() == 2);

        List<String> threeParagraphList = textAnalyserService.preProcessText(threeParagraph);
        Assert.assertTrue(threeParagraphList.size() == 3);
    }
}
