package com.adrrriannn.text.analyser.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by adrian on 26/04/2017.
 */
@Entity
public class TextAnalyserResultEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    private String mostFrequentWord;

    private Double averageParagraphSize;

    private Double averageParagraphProcessingTime;

    private long totalProcessingTime;

    @JsonIgnore
    private Long created;
    
    private TextAnalyserResultEntity() {}

    public TextAnalyserResultEntity(String mostFrequentWord, Double averageParagraphSize,
                                    Double averageParagraphProcessingTime, 
                                    Long totalProcessingTime) {
        this.mostFrequentWord = mostFrequentWord;
        this.averageParagraphSize = averageParagraphSize;
        this.averageParagraphProcessingTime = averageParagraphProcessingTime;
        this.totalProcessingTime = totalProcessingTime; 
        this.created = System.currentTimeMillis();
    }

    public Long getId () {
        return id;
    }

    public long getTotalProcessingTime () {
        return totalProcessingTime;
    }

    public Long getCreated () {
        return created;
    }

    public String getMostFrequentWord () {
        return mostFrequentWord;
    }

    public Double getAverageParagraphSize () {
        return averageParagraphSize;
    }

    public Double getAverageParagraphProcessingTime () {
        return averageParagraphProcessingTime;
    }
    
    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextAnalyserResultEntity that = (TextAnalyserResultEntity) o;

        if (totalProcessingTime != that.totalProcessingTime) return false;
        if (created != that.created) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (mostFrequentWord != null ? !mostFrequentWord.equals(that.mostFrequentWord) : that.mostFrequentWord != null)
            return false;
        if (averageParagraphSize != null ? !averageParagraphSize.equals(that.averageParagraphSize) : that.averageParagraphSize != null)
            return false;
        return averageParagraphProcessingTime != null ? averageParagraphProcessingTime.equals(that.averageParagraphProcessingTime) : that.averageParagraphProcessingTime == null;

    }

    @Override
    public int hashCode () {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (mostFrequentWord != null ? mostFrequentWord.hashCode() : 0);
        result = 31 * result + (averageParagraphSize != null ? averageParagraphSize.hashCode() : 0);
        result = 31 * result + (averageParagraphProcessingTime != null ? averageParagraphProcessingTime.hashCode() : 0);
        result = 31 * result + (int) (totalProcessingTime ^ (totalProcessingTime >>> 32));
        result = 31 * result + (int) (created ^ (created >>> 32));
        return result;
    }
}
