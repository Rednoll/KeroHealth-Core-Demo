package com.kero.health.core.domain.account.impl;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kero.health.core.dao.account.AccountRepository;
import com.kero.health.core.domain.Human;
import com.kero.health.core.domain.account.Account;
import com.kero.health.core.domain.impl.HumanImpl;

@Entity(name = "Account")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AccountBase implements Account {

	protected static AccountRepository repository;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
	@SequenceGenerator(name = "account_seq", sequenceName = "account_seq", allocationSize = 1)
	private Long id;
	
	@ManyToOne(targetEntity = HumanImpl.class, fetch = FetchType.LAZY)
	private Human owner;
	
	public AccountBase() {}
	
	public AccountBase(Long id, Human owner) {
		
		this.id = id;
		this.owner = owner;
	}
	
	public void setOwner(Human owner) {
		
		this.owner = owner;
		
		if(owner != null) {
			
			owner.getAccounts().add(this);
			owner.save();
		}
	}
	
	public Human getOwner() {
		
		return this.owner;
	}
	
	public Long getId() {
		
		return this.id;
	}
	
	public AccountBase save() {
		
		return repository.save(this);
	}
	
	public static AccountBase findById(Long id) {
		
		return repository.findById(id).orElse(null);
	}
	
	@Service
	private static class RepositoryInjector {
		
		@Autowired
		private AccountRepository rep;
	
		@PostConstruct
		private void inject() {
			
			repository = rep;
		}
	}
}
