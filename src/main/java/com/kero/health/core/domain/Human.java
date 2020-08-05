package com.kero.health.core.domain;

import java.time.LocalDate;
import java.util.Set;

import com.kero.health.core.domain.account.Account;
import com.kero.health.core.domain.additionals.HumanContacts;
import com.kero.health.core.domain.impl.HumanImpl;
import com.kero.health.core.domain.life.Life;
import com.kero.health.core.domain.types.Sex;

public interface Human {

	public Sex getSex();
	public Integer getMass();
	public Integer getHeight();
	public Integer getAge();
	public LocalDate getBirthDate();
	public HumanContacts getContacts();
	public Life getLife();
	
	public Set<Account> getAccounts();
	
	public Human save();
	
	public static boolean existsByEmail(String email) {
		
		return HumanImpl.existsByEmail(email);
	}

	public static Human create(Sex sex, Integer mass, Integer height, LocalDate birthDate) {
		
		return HumanImpl.create(sex, mass, height, birthDate);
	}
	
	public static Human findById(Long id) {
		
		return HumanImpl.findById(id);
	}
	
	public Long getId();
}
