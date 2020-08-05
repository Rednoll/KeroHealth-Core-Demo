package com.kero.health.core.domain.account.impl;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kero.health.core.dao.account.KeroHealthAccountRepository;
import com.kero.health.core.domain.Human;

@Entity(name = "KeroHealthAccount")
public class KeroHealthAccountImpl extends AccountBase implements KeroHealthAccount {

	private static KeroHealthAccountRepository repository;
	
	private String login;
	
	private byte[] passHash;
	
	public KeroHealthAccountImpl() {}
	
	public KeroHealthAccountImpl(Long id, Human owner, String login, byte[] passHash) {
		super(id, owner);
	
		this.login = login;
		this.passHash = passHash;
	}
	
	public static KeroHealthAccountImpl findById(Long id) {
		
		return repository.findById(id).orElse(null);
	}
	
	public static boolean existsByLogin(String login) {
		
		return repository.existsByLogin(login);
	}
	
	public static KeroHealthAccount findByLoginOrEmail(String login, String email) {
		
		return repository.findByLoginOrOwnerContactsEmail(login, email);
	}
	
	public static KeroHealthAccount create(Human owner, String login, byte[] passHash) {
		
		KeroHealthAccountImpl account = new KeroHealthAccountImpl(null, owner, login, passHash);
		
		account = repository.save(account);
		
		if(owner != null) {
			
			owner.getAccounts().add(account);
			owner.save();
		}
		
		return account;
	}
	
	public String getLogin() {
		
		return this.login;
	}
	
	public byte[] getPassHash() {
		
		return this.passHash;
	}
	
	public String getType() {
		
		return "KERO_HEALTH";
	}
	
	@Service
	private static class RepositoryInjector {
		
		@Autowired
		private KeroHealthAccountRepository rep;
	
		@PostConstruct
		private void inject() {
			
			repository = rep;
		}
	}
}
