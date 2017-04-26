package com.adrrriannn.text.analyser.exception;

/**
 * Created by adrian on 24/04/2017.
 */
public abstract class BaseException extends Exception {

    private ExceptionCode code;
    
    public BaseException(String message, ExceptionCode code) {
        super(message);
        this.code = code;
    }
    
    public ExceptionCode getCode () {
        return code;
    }
}
