package com.kero.health.core.web.graphql.human.accessor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.kero.health.core.domain.Human;
import com.kero.health.core.domain.account.Account;
import com.kero.health.core.domain.additionals.HumanContacts;
import com.kero.health.core.domain.life.Life;
import com.kero.health.core.domain.types.Sex;
import com.kero.health.core.web.graphql.account.accessor.AccountAccessor;

public class HumanRootAccessor implements Human {

	protected Human human;
	
	public HumanRootAccessor() {}
	
	public HumanRootAccessor(Human human) {
		
		this.human = human;
	}
	
	@Override
	public Sex getSex() {
		
		return human.getSex();
	}

	@Override
	public Integer getMass() {
		
		return human.getMass();
	}

	@Override
	public Integer getHeight() {
		
		return human.getHeight();
	}

	@Override
	public Integer getAge() {
		
		return human.getAge();
	}
	
	@Override
	public LocalDate getBirthDate() {
		
		return human.getBirthDate();
	}

	@Override
	public Life getLife() {
		
		return human.getLife();
	}

	@Override
	public HumanContacts getContacts() {
		
		return human.getContacts();
	}
	
	@Override
	public Set<Account> getAccounts() {

		Set<Account> accounts = new HashSet<>();
		
		for(Account account : human.getAccounts()){
			
			accounts.add(AccountAccessor.createRootAccessor(account));
		}
		
		return accounts;
	}

	@Override
	public Human save() {
		
		return null;
	}

	@Override
	public Long getId() {
		
		return human.getId();
	}
}
