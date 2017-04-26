package com.adrrriannn.text.analyser.exception;

/**
 * Created by adrian on 26/04/2017.
 */
public class InternalServerError extends BaseException {

    public InternalServerError (String message, ExceptionCode code) {
        super(message, code);
    }
}
