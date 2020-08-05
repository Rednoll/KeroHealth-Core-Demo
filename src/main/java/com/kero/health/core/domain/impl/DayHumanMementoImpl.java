package com.kero.health.core.domain.impl;

import javax.annotation.PostConstruct;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Target;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kero.health.core.dao.DayHumanMementoRepository;
import com.kero.health.core.domain.DayHumanMemento;
import com.kero.health.core.domain.Human;
import com.kero.health.core.domain.life.additionals.DayHumanMementoId;
import com.kero.health.core.domain.life.units.LifeDay;
import com.kero.health.core.domain.life.units.impl.LifeDayImpl;
import com.kero.health.core.domain.types.Sex;

@Entity(name = "HumanMemento")
public class DayHumanMementoImpl implements DayHumanMemento {
	
	protected static DayHumanMementoRepository repository;
	
	@EmbeddedId
	private DayHumanMementoId id;
	
	private Integer age;
	
	private Integer height;
	
	private Integer mass;
	
	@Enumerated(EnumType.STRING)
	private Sex sex;
	
	@MapsId("humanId")
	@Target(HumanImpl.class)
	@ManyToOne(targetEntity = HumanImpl.class, optional = false)
	private Human human;
	
	@MapsId("creationDayId")
	@Target(LifeDayImpl.class)
	@OneToOne(targetEntity = LifeDayImpl.class)
	private LifeDay creationDay;
	
	public DayHumanMementoImpl() {}
	
	public DayHumanMementoImpl(Human human, LifeDay creationDay) {
		
		this.id = new DayHumanMementoId(human.getId(), creationDay.getId());
	
		this.human = human;
		this.creationDay = creationDay;
		
		this.age = human.getAge();
		this.height = human.getHeight();
		this.mass = human.getMass();
		this.sex = human.getSex();
	}
	
	@Override
	public LifeDay getCreationDay() {
		
		return this.creationDay;
	}
	
	@Override
	public Human getHuman() {
		
		return this.human;
	}
	
	@Override
	public Sex getSex() {
		
		return this.sex;
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
		
		return this.age;
	}

	@Override
	public DayHumanMementoId getId() {
		
		return this.id;
	}
	
	public DayHumanMementoImpl save() {
		
		return repository.save(this);
	}
	
	@Service
	private static class RepositoryInjector {
		
		@Autowired
		private DayHumanMementoRepository rep;
	
		@PostConstruct
		private void inject() {
			
			repository = rep;
		}
	}
}
