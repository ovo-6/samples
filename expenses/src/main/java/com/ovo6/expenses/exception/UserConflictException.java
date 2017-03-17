package com.ovo6.expenses.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for conflict in username during sign-up.
 */
@ResponseStatus(value= HttpStatus.CONFLICT)
public class UserConflictException extends RuntimeException {

    public UserConflictException(String msg) {
        super(msg);
    }
}
