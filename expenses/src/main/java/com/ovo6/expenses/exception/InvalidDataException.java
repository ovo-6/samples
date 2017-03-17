package com.ovo6.expenses.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception meaning some problem in input data.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class InvalidDataException extends RuntimeException {

    public InvalidDataException(String msg) {
        super(msg);
    }
}
