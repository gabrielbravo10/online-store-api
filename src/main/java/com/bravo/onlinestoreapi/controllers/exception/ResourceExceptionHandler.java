package com.bravo.onlinestoreapi.controllers.exception;

import com.bravo.onlinestoreapi.services.exceptions.DataIntegrityException;
import com.bravo.onlinestoreapi.services.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException exception,
                                                        HttpServletRequest request) {

        StandardError standardError = new StandardError(HttpStatus.NOT_FOUND.value(),
                exception.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardError> dataIntregity(DataIntegrityException exception,
                                                        HttpServletRequest request) {

        StandardError standardError = new StandardError(HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException exception,
                                                       HttpServletRequest request) {

        ValidationError validationError = new ValidationError(HttpStatus.BAD_REQUEST.value(),
                "Error de validacao", System.currentTimeMillis());

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()){
            validationError.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
    }
}
