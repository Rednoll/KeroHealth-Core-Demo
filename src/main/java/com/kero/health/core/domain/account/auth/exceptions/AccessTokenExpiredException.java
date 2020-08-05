package com.kero.health.core.domain.account.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kero.health.core.utils.ExceptionWithCode;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Access token expired!")
public class AccessTokenExpiredException extends RuntimeException implements ExceptionWithCode {

	public AccessTokenExpiredException() {
		super("Access token expired!");
		
	}

	@Override
	public int getCode() {
		
		return 100;
	}
}
