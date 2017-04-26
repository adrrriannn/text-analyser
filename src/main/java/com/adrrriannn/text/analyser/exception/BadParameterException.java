package com.adrrriannn.text.analyser.exception;

/**
 * Created by adrian on 24/04/2017.
 */
public class BadParameterException extends BaseException {
    
    public BadParameterException (String message, ExceptionCode code) {
        super(message, code);
    }
}
