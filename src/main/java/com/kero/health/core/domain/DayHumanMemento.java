package com.kero.health.core.domain;

import com.kero.health.core.domain.life.additionals.DayHumanMementoId;
import com.kero.health.core.domain.life.units.LifeDay;
import com.kero.health.core.domain.types.Sex;

public interface DayHumanMemento {

	public Sex getSex();
	public Integer getMass();
	public Integer getHeight();
	public Integer getAge();
	
	public Human getHuman();
	public LifeDay getCreationDay();
	
	public DayHumanMementoId getId();
	
	public DayHumanMemento save();
}
