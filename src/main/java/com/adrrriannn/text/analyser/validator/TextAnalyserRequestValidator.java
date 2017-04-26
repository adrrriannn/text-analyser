package com.adrrriannn.text.analyser.validator;

import com.adrrriannn.text.analyser.exception.BadParameterException;
import com.adrrriannn.text.analyser.exception.ExceptionCode;

/**
 * Created by adrian on 24/04/2017.
 */
public class TextAnalyserRequestValidator {

    public static void validate(Integer pStart, Integer pEnd, Integer wCountMin,
                                Integer wCountMax) throws BadParameterException {

        validateParagraph(pStart, ExceptionCode.INVALID_MIN_PARAGRAPHS_NUMBER, "Invalid min paragraphs number");
        validateParagraph(pEnd, ExceptionCode.INVALID_MAX_PARAGRAPHS_NUMBER, "Invalid max paragraph number");
        validateRanges(pStart, pEnd, ExceptionCode.INVALID_PARAGRAPH_VALUES, "Paragraph start cannot be bigger than paragraph end");

        validateWords(wCountMin, ExceptionCode.INVALID_MIN_WORDS_NUMBER, "Invalid min words number");
        validateWords(wCountMax, ExceptionCode.INVALID_MAX_WORDS_NUMBER, "Invalid max words number");
        validateRanges(wCountMin, wCountMax, ExceptionCode.INVALID_WORDS_VALUES, "Min words cannot be bigger than max words");
    }

    private static void validateParagraph(Integer paragraphNumber, ExceptionCode exceptionCode, String errorMessage) throws BadParameterException{

        if(paragraphNumber < 1 || paragraphNumber > 1000) {
            throw new BadParameterException(errorMessage, exceptionCode);
        }
    }

    private static void validateWords(Integer wordsNumber, ExceptionCode exceptionCode, 
                                      String errorMessage) throws BadParameterException {
        if(wordsNumber < 1 || wordsNumber > 50) {
            throw new BadParameterException(errorMessage, exceptionCode);
        }
    }
    
    private static void validateRanges(Integer start, Integer end, ExceptionCode exceptionCode, 
                                       String errorMessage) throws BadParameterException {
        if(start > end) {
            throw new BadParameterException(errorMessage, exceptionCode);
        }
    }
}
