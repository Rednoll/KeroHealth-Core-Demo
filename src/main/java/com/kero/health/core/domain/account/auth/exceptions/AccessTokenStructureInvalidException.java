package com.kero.health.core.domain.account.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kero.health.core.utils.ExceptionWithCode;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Access token structure invalid!")
public class AccessTokenStructureInvalidException extends RuntimeException implements ExceptionWithCode {

	public AccessTokenStructureInvalidException() {
		super("Access token structure invalid!");
	}

	@Override
	public int getCode() {
		
		return 102;
	}
}
