package com.account.accountservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class AccountServiceException extends RuntimeException{
	private static final long serialVersionUID = 1L;
    
    public AccountServiceException(String message) {
        super(message);
    }
}