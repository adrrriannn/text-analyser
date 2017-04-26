package com.adrrriannn.text.analyser.controller;

import com.adrrriannn.text.analyser.exception.BadParameterException;
import com.adrrriannn.text.analyser.exception.ExceptionMessage;
import com.adrrriannn.text.analyser.exception.InternalServerError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by adrian on 26/04/2017.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ResponseStatus (HttpStatus.BAD_REQUEST)
    @ExceptionHandler ({BadParameterException.class})
    @ResponseBody
    public ExceptionMessage handleBadParameter(HttpServletRequest req, Exception ex) {
        BadParameterException bExp = (BadParameterException)ex;
        
        return new ExceptionMessage(ex, HttpStatus.BAD_REQUEST, req.getRequestURI(), bExp.getCode());
    }

    @ResponseStatus (HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler ({InternalServerError.class})
    @ResponseBody
    public ExceptionMessage handleInternalServerError(HttpServletRequest req, Exception ex) {
        BadParameterException bExp = (BadParameterException)ex;
        return new ExceptionMessage(ex, HttpStatus.BAD_REQUEST, req.getRequestURI(), bExp.getCode());
    }
}

