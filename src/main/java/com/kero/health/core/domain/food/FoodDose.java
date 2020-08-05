package com.kero.health.core.domain.food;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.hibernate.annotations.Target;

import com.kero.health.core.domain.food.impl.FoodBase;

@Embeddable
public class FoodDose implements Comparable<FoodDose> {
	
	@MapsId("foodId")
	@Target(FoodBase.class)
	@ManyToOne(targetEntity = FoodBase.class, fetch = FetchType.EAGER, optional = false)
	private Food food;

	private Long amount;
	
	public FoodDose() {}
	
	public FoodDose(Food food, long amount) {
		
		if(amount <= 0) throw new IllegalArgumentException("Amount must be > 0!");
		
		this.food = food;
		this.amount = amount;
	}
	
	public Food getFood() {
		
		return this.food;
	}
	
	public long getAmount() {
		
		return this.amount;
	}
	
	@Override
	public int compareTo(FoodDose another) {
		
		return (int) (this.amount - another.amount);
	}
}
