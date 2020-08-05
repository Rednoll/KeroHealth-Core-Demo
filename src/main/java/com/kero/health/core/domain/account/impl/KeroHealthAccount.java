package com.kero.health.core.domain.account.impl;

import com.kero.health.core.domain.Human;
import com.kero.health.core.domain.account.Account;

public interface KeroHealthAccount extends Account {
	
	public static boolean existsByLogin(String login) {
		
		return KeroHealthAccountImpl.existsByLogin(login);
	}
	
	public static KeroHealthAccount findById(Long id) {
		
		return KeroHealthAccountImpl.findById(id);
	}
	
	public static KeroHealthAccount findByLoginOrEmail(String login, String email) {
		
		return KeroHealthAccountImpl.findByLoginOrEmail(login, email);
	}
	
	public static KeroHealthAccount create(Human owner, String login, byte[] passHash) {
		
		if(KeroHealthAccountImpl.existsByLogin(login)) throw new RuntimeException("Account with login "+login+" already exist!");
		
		return KeroHealthAccountImpl.create(owner, login, passHash);
	}
	
	public String getLogin();
	public byte[] getPassHash();
}
