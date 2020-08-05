package com.kero.health.core.domain.account.auth.impl;

import java.time.Instant;

import com.kero.health.core.domain.account.Account;
import com.kero.health.core.domain.account.auth.AccessToken;

public abstract class AccessTokenBase implements AccessToken {
	
	protected Instant expirationTime;
	
	protected Account account;
	
	public AccessTokenBase() {}

	public AccessTokenBase(Account account, Instant expirationTime) {
		
		this.account = account;
		this.expirationTime = expirationTime;
	}
	
	public Account getAccount() {
	
		return this.account;
	}
	
	public Instant getExpirationTime() {
		
		return this.expirationTime;
	}
}
