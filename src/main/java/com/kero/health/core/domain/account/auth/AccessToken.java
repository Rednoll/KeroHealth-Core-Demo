package com.kero.health.core.domain.account.auth;

import java.time.Duration;
import java.time.Instant;

import com.kero.health.core.domain.account.Account;
import com.kero.health.core.domain.account.auth.exceptions.AccessTokenTypeNotSupported;
import com.kero.health.core.domain.account.auth.impl.JwtToken;

public interface AccessToken {

	public static Duration defaultValidTime = Duration.ofDays(1);
	
	public String toStringForm();
	
	public Account getAccount();
	public Instant getExpirationTime();
	
	public default boolean isExpired() {
		
		return getExpirationTime().getEpochSecond() < Instant.now().getEpochSecond();
	}
	
	public static VerifyResult verify(String tokenType, String token) {
		
		if(tokenType.equals("JWT")) {
			
			return JwtToken.verify(token);
		}
		
		throw new AccessTokenTypeNotSupported("Token type: "+tokenType+" not supported!");
	}
	
	public static AccessToken create(String tokenType, Account account, Instant expirationTime) {
		
		if(tokenType.equals("JWT")) {
			
			return new JwtToken(account, expirationTime);
		}
		
		throw new AccessTokenTypeNotSupported("Token type: "+tokenType+" not supported!");
	}
	
	public static AccessToken fromString(String tokenType, String token) {
		
		if(tokenType.equals("JWT")) {
			
			return JwtToken.fromString(token);
		}

		throw new AccessTokenTypeNotSupported("Token type: "+tokenType+" not supported!");
	}
}
