package com.randstad.parkingsystem.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> noSuchElementExceptionHandler(NoSuchElementException noSuchElementException){
        ErrorResponse errorResponse = new ErrorResponse(noSuchElementException.getMessage(), HttpStatus.NOT_FOUND.toString() );
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> serviceExceptionHandler(ServiceException serviceException){
        ErrorResponse errorResponse = new ErrorResponse(serviceException.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.toString());
        return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorResponse> resourceAccessExceptionHandler(ResourceAccessException resourceAccessException){
        ErrorResponse errorResponse = new ErrorResponse(resourceAccessException.getMessage(), HttpStatus.NO_CONTENT.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.NO_CONTENT);
    }

}
