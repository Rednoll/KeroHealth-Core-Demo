package com.kero.health.core.domain.account.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kero.health.core.utils.ExceptionWithCode;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Account not found!")
public class AccessTokenAccountNotFoundException extends RuntimeException implements ExceptionWithCode {

	public AccessTokenAccountNotFoundException() {
		super("Account not found!");
		
	}

	@Override
	public int getCode() {
		
		return 103;
	}
}
