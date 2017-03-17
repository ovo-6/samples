package com.ovo6.expenses.repo;

import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *  Transforms exception from UniqueUserValidator to 400 Bad Request with message containing "userName is already taken".
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ RepositoryConstraintViolationException.class })
    public ResponseEntity<Object> handleRepositoryConstraintViolationException(Exception ex, WebRequest request) {

        RepositoryConstraintViolationException nevEx = (RepositoryConstraintViolationException) ex;

        String msg = "RepositoryConstraintViolationException";
        FieldError error = nevEx.getErrors().getFieldError("name");
        if ((error != null) && "name.alreadyExists".equals(error.getCode())) {
            msg = error.getRejectedValue() + " is already taken";
        }

        return new ResponseEntity<Object>("{\"message\":\"" + msg + "\"}", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}