package com.kero.health.core.domain.impl;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Target;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kero.health.core.dao.HumanRepository;
import com.kero.health.core.domain.Human;
import com.kero.health.core.domain.account.Account;
import com.kero.health.core.domain.account.impl.AccountBase;
import com.kero.health.core.domain.additionals.HumanContacts;
import com.kero.health.core.domain.additionals.impl.HumanContactsImpl;
import com.kero.health.core.domain.life.Life;
import com.kero.health.core.domain.life.impl.LifeImpl;
import com.kero.health.core.domain.types.Sex;

@Entity(name = "Human")
public class HumanImpl implements Human {
	
	private static HumanRepository repository;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "human_seq")
	@SequenceGenerator(name = "human_seq", sequenceName = "human_seq", allocationSize = 1)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private Sex sex;
	
	@Column(nullable = false)
	private Integer mass;
	
	@Column(nullable = false)
	private Integer height;

	@Column(nullable = false)
	private LocalDate birthDate;
	
	@Embedded
	@Target(HumanContactsImpl.class)
	private HumanContacts contacts;
	
	@OneToOne(targetEntity = LifeImpl.class, mappedBy = "owner", orphanRemoval = true, fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
	private Life life;
	
	@OneToMany(targetEntity = AccountBase.class, orphanRemoval = true, mappedBy = "owner")
	private Set<Account> accounts = new HashSet<>();
	
	public HumanImpl() {}
	
	public HumanImpl(Long id, Sex sex, Integer mass, Integer height, LocalDate birthDate) {
		
		this.id = id;
		this.sex = sex;
		this.mass = mass;
		this.height = height;
		this.birthDate = birthDate;
		this.contacts = HumanContacts.create();
	}
	
	@Override
	public Sex getSex() {
		
		return this.sex;
	}

	@Override
	public HumanContacts getContacts() {
		
		return this.contacts;
	}
	
	@Override
	public Integer getMass() {
		
		return this.mass;
	}

	@Override
	public Integer getHeight() {
		
		return this.height;
	}

	@Override
	public Integer getAge() {
		
		return Period.between(this.birthDate, LocalDate.now(ZoneOffset.UTC)).getYears();
	}
	
	@Override
	public LocalDate getBirthDate() {
		
		return this.birthDate;
	}

	@Override
	public Life getLife() {
		
		return this.life;
	}
	
	@Override
	public Set<Account> getAccounts() {
	
		return this.accounts;
	}
	
	public Long getId() {
		
		return this.id;
	}
	
	public HumanImpl save() {
		
		return repository.save(this);
	}
	
	public static boolean existsByEmail(String email) {
		
		return repository.existsByContactsEmail(email);
	}
	
	public static HumanImpl findById(Long id) {
		
		return repository.findById(id).orElse(null);
	}
	
	public static HumanImpl create(Sex sex, Integer mass, Integer height, LocalDate birthDate) {
		
		HumanImpl human = new HumanImpl(null, sex, mass, height, birthDate);
			human.life = new LifeImpl(null, human);
		
		human = repository.save(human);
		
		return human;
	}

	@Service
	private static class RepositoryInjector {
		
		@Autowired
		private HumanRepository rep;
		
		@PostConstruct
		private void inject() {
			
			repository = rep;
		}
	}
}