package com.kero.health.core.domain.food;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.hibernate.annotations.Target;

import com.kero.health.core.domain.food.additionals.IngridientDoseId;
import com.kero.health.core.domain.food.impl.FoodBase;

@Entity(name = "IngridientDose")
public class IngridientDose implements Comparable<IngridientDose> {
	
	@EmbeddedId
	private IngridientDoseId id;
	
	@MapsId("ingridientId")
	@Target(FoodBase.class)
	@ManyToOne(targetEntity = FoodBase.class)
	private Food ingridient;
	
	private Short shareBase;
	
	public IngridientDose() {}
	
	public IngridientDose(IngridientDoseId id, Food ingridient, int shareBase) {
		
		if(ingridient == null) throw new IllegalArgumentException("Ingridient can't be null!");
		if(shareBase <= 0) throw new IllegalArgumentException("Share base must be > 0!");
		
		this.ingridient = ingridient;
		this.shareBase = (short) shareBase;
	}
	
	@Override
	public int compareTo(IngridientDose another) {
		
		return this.shareBase > another.shareBase ? -1 : 1;
	}
	
	public Food getIngridient() {
	
		return this.ingridient;
	}
	
	public int getShareFrom(int mass) {
		
		return (int) ((double) shareBase / 10000D * (double) mass);
	}
	
	public IngridientDoseId getId() {
		
		return this.id;
	}
}
