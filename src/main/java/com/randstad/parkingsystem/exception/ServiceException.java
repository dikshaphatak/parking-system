package com.randstad.parkingsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ServiceException extends  RuntimeException{

    private String message;

    public ServiceException(String message){
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
