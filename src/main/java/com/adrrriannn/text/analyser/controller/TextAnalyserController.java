package com.adrrriannn.text.analyser.controller;

import com.adrrriannn.text.analyser.entity.TextAnalyserResultEntity;
import com.adrrriannn.text.analyser.exception.BadParameterException;
import com.adrrriannn.text.analyser.exception.InternalServerError;
import com.adrrriannn.text.analyser.service.TextAnalyserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.adrrriannn.text.analyser.validator.TextAnalyserRequestValidator;

/**
 * Created by adrian on 24/04/2017.
 */
@RestController
public class TextAnalyserController {
    
    private TextAnalyserService textAnalyserService;
    
    @Autowired
    public TextAnalyserController(TextAnalyserService textAnalyserService) {
        this.textAnalyserService = textAnalyserService;
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/text")
    public TextAnalyserResultEntity analyseText(@RequestParam("p_start") int pStart,
                                                @RequestParam("p_end") int pEnd,
                                                @RequestParam("w_count_min") int wCountMin,
                                                @RequestParam("w_count_max") int wCountMax
    ) throws BadParameterException, InternalServerError {
        
        TextAnalyserRequestValidator.validate(pStart, pEnd, wCountMin, wCountMax);
        
        return textAnalyserService.analyseText(pStart, pEnd, wCountMin, wCountMax);    
    }

    @RequestMapping(method = RequestMethod.GET, value = "/history")
    public Iterable<TextAnalyserResultEntity> getHistory() {

        return textAnalyserService.getLast10Results();
    }

}
