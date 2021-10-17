package dev.taljaard.training.trnmicroservbrewery.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MvcExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> validationExceptionHandler(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations().stream().map(error -> {
            return ex.getMessage();
        }).collect(Collectors.toList());
        return new ResponseEntity<List<String>>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<ObjectError>> handleBindException(BindException ex) {
        return new ResponseEntity<List<ObjectError>>(ex.getAllErrors(), HttpStatus.BAD_REQUEST);
    }
}
