package com.kero.health.core.domain.account;

import java.time.Instant;

import com.kero.health.core.domain.Human;
import com.kero.health.core.domain.account.auth.AccessToken;
import com.kero.health.core.domain.account.impl.AccountBase;

public interface Account {

	public void setOwner(Human owner);
	public Human getOwner();
	public Long getId();
	
	public String getType();
	
	public Account save();
	
	public default AccessToken createAccessToken(String tokenType, Instant expirationTime) {
		
		return AccessToken.create(tokenType, this, expirationTime);
	}
	
	public static Account findById(Long id) {
		
		return AccountBase.findById(id);
	}
}
