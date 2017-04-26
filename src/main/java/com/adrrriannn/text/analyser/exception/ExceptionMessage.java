package com.adrrriannn.text.analyser.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by adrian on 26/04/2017.
 */
public class ExceptionMessage {

    public Class exception;
    public String message;
    public int status;
    public long timestamp;
    public String url;
    public ExceptionCode code;

    public ExceptionMessage(Exception ex, HttpStatus status, String url, String message)
    {
        this.exception = ex.getClass();
        this.message = message;
        this.status = status.value();
        this.url = url;
        this.timestamp = System.currentTimeMillis();
    }

    public ExceptionMessage(Exception ex, HttpStatus status, String url, ExceptionCode code)
    {
        this(ex, status, url, ex.getMessage());
        this.code = code;
    }

    public ExceptionMessage(Exception ex, HttpStatus status, String url, ExceptionCode code, String message)
    {
        this(ex, status, url, message);
        this.code = code;
    }
}
