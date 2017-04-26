package com.adrrriannn.text.analyser.model;

import com.adrrriannn.text.analyser.entity.TextAnalyserResultEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adrian on 25/04/2017.
 */
public class ParagraphAnalysisResult {
    
    private Map<String, Long> wordsUses;
    private Integer paragraphSize;
    private long processingTime;
    private Integer paragraphQuantity;
    
    public ParagraphAnalysisResult(Map<String, Long> wordsUses, Integer paragraphSize, long processingTime, Integer paragraphQuantity) {
        this.wordsUses = wordsUses;
        this.paragraphSize = paragraphSize;
        this.processingTime = processingTime;
        this.paragraphQuantity = paragraphQuantity;
    }
    
    public ParagraphAnalysisResult() {
        this(new HashMap<>(), 0, 0L, 0);
    }

    public Map<String, Long> getWordsUses () {
        return wordsUses;
    }

    public Integer getParagraphSize () {
        return paragraphSize;
    }

    public long getProcessingTime () {
        return processingTime;
    }
    
    public Integer getParagraphQuantity() {
        return paragraphQuantity;
    }
    
    private static Map<String, Long> joinMap(Map<String, Long> parent, Map<String, Long> child) {

        for(Map.Entry<String, Long> entry : child.entrySet()) {
            String key = entry.getKey();
            Long count = entry.getValue();
            if(parent.containsKey(key)) {

                count = parent.get(key);
            }
            parent.put(key, count);
        }
        
        return parent;
    }
    
    public static ParagraphAnalysisResult join(List<ParagraphAnalysisResult> paragraphAnalysisResultList) {
        return paragraphAnalysisResultList
            .stream()
            .reduce(ParagraphAnalysisResult::join)
            .orElse(new ParagraphAnalysisResult());
    }
    
    private static ParagraphAnalysisResult join(ParagraphAnalysisResult p1, ParagraphAnalysisResult p2) {
        
        Map<String,Long> wordsUses = joinMap(p1.getWordsUses(), p2.getWordsUses());
        Integer paragraphSize = p1.getParagraphSize() + p2.getParagraphSize();
        Long processingTime = p1.getProcessingTime() + p2.getProcessingTime();
        Integer paragraphQuantity = p1.getParagraphQuantity() + p2.getParagraphQuantity();
        
        return new ParagraphAnalysisResult(wordsUses, paragraphSize, processingTime, paragraphQuantity);
    }
    
    public TextAnalyserResultEntity toTextAnalyserResult(long time) {
        
        String mostFrequentWord = wordsUses.entrySet()
            .stream()
            .max((x1, x2) -> x1.getValue().compareTo(x2.getValue()))
            .get()
            .getKey();

        return new TextAnalyserResultEntity(
            mostFrequentWord,
            (double) paragraphSize/paragraphQuantity,
            (double) processingTime/paragraphQuantity,
            time
        );
    }
    
}
