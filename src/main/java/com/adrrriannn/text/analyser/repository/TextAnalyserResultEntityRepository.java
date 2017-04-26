package com.adrrriannn.text.analyser.repository;

import com.adrrriannn.text.analyser.entity.TextAnalyserResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by adrian on 25/04/2017.
 */
public interface TextAnalyserResultEntityRepository extends JpaRepository<TextAnalyserResultEntity, Long> {
    
    List<TextAnalyserResultEntity> findTop10ByOrderByCreatedDesc(); 
}
