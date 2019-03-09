package com.company;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EcommerceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = FileInputException.class)
    public ResponseEntity<Object> exception(FileInputException exception) {

        return new ResponseEntity<>(exception.getMsg(), HttpStatus.BAD_REQUEST);
    }
}