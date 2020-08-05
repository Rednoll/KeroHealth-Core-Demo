package com.kero.health.core.domain.food.diary.intake.unit.impl;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

import com.kero.health.core.domain.food.diary.intake.impl.FoodIntakeId;

public class FoodIntakeUnitImplId implements Serializable {

	private static final long serialVersionUID = 1L;

	private FoodIntakeId intakeId;
	
	@Column(name = "food_id")
	private Long foodId;
	
	public FoodIntakeUnitImplId() {}
	
	public FoodIntakeUnitImplId(FoodIntakeId intakeId, Long foodId) {
		
		this.intakeId = intakeId;
		this.foodId = foodId;
	}
	
	@Override
	public int hashCode() {
		
		return Objects.hash(foodId, intakeId);
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FoodIntakeUnitImplId other = (FoodIntakeUnitImplId) obj;
		return Objects.equals(foodId, other.foodId) && Objects.equals(intakeId, other.intakeId);
	}

	public void setFoodId(Long food) {
		
		this.foodId = food;
	}
	
	public Long getFoodId() {
		
		return this.foodId;
	}
	
	public void setIntakeId(FoodIntakeId id) {
		
		this.intakeId = id;
	}
	
	public FoodIntakeId getIntakeId() {
		
		return this.intakeId;
	}
}
