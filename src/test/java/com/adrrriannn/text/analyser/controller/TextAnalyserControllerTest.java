package com.adrrriannn.text.analyser.controller;

import com.adrrriannn.text.analyser.entity.TextAnalyserResultEntity;
import com.adrrriannn.text.analyser.exception.BadParameterException;
import com.adrrriannn.text.analyser.service.TextAnalyserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doReturn;

/**
 * Created by adrian on 26/04/2017.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TextAnalyserControllerTest {
    
    @Mock
    TextAnalyserService textAnalyserService;
    
    TextAnalyserResultEntity textAnalyserResultEntity = new TextAnalyserResultEntity("bird", 12.5, 0.12, 25L);
    
    TextAnalyserController textAnalyserController;
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    @Before
    public void setUp() throws Exception{
        doReturn(textAnalyserResultEntity).when(textAnalyserService).analyseText(any(), any(), anyInt(), any());
        textAnalyserController = new TextAnalyserController(textAnalyserService);
    }
    
    @Test
    public void analyseTextSuccess() throws Exception{
        
        TextAnalyserResultEntity textAnalyserResult = textAnalyserController.analyseText(1, 50, 25, 25);
        Assert.assertTrue(textAnalyserResult.equals(this.textAnalyserResultEntity));
    }
    
    @Test
    public void analyseTextInvalidStartParagraph() throws Exception{
        expectedException.expect(BadParameterException.class);
     
        textAnalyserController.analyseText(-2, 50, 25, 25);
    }

    @Test
    public void analyseTextInvalidEndParagraph() throws Exception{
        expectedException.expect(BadParameterException.class);

        textAnalyserController.analyseText(1, 1001, 25, 25);
    }

    @Test
    public void analyseTextInvalidMinWords() throws Exception{
        expectedException.expect(BadParameterException.class);

        textAnalyserController.analyseText(1, 50, -1, 25);
    }

    @Test
    public void analyseTextInvalidMaxWords() throws Exception{
        expectedException.expect(BadParameterException.class);

        textAnalyserController.analyseText(1, 50, 25, 51);
    }

    @Test
    public void analyseTextInvalidParagraphNumberRange() throws Exception{
        expectedException.expect(BadParameterException.class);

        textAnalyserController.analyseText(51, 50, 25, 25);
    }

    @Test
    public void analyseTextInvalidWordsNumberRange() throws Exception{
        expectedException.expect(BadParameterException.class);

        textAnalyserController.analyseText(1, 50, 26, 25);
    }
    
}
