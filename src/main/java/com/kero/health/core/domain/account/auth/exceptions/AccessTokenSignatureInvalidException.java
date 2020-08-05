package com.kero.health.core.domain.account.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kero.health.core.utils.ExceptionWithCode;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Access token signature invalid!")
public class AccessTokenSignatureInvalidException extends RuntimeException implements ExceptionWithCode {

	public AccessTokenSignatureInvalidException() {
		super("Access token signature invalid!");
		
	}
	
	@Override
	public int getCode() {
		
		return 101;
	}
}
