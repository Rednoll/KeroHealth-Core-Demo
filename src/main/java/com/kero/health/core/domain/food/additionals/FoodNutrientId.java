package com.kero.health.core.domain.food.additionals;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

public class FoodNutrientId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "food_id")
	private Long foodId;
	
	@Column(name = "nutrient_id", updatable = false, insertable = false)
	private Short nutrientId;
	
	public FoodNutrientId() {}
	
	public FoodNutrientId(Long foodId, Short nutrientId) {
		
		this.foodId = foodId;
		this.nutrientId = nutrientId;
	}
	
	@Override
	public int hashCode() {
		
		return Objects.hash(foodId, nutrientId);
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FoodNutrientId other = (FoodNutrientId) obj;
		return Objects.equals(foodId, other.foodId) && nutrientId == other.nutrientId;
	}

	public void setNutrientId(Short nutrientId) {
		
		this.nutrientId = nutrientId;
	}
	
	public Short getNutrientId() {
		
		return this.nutrientId;
	}
	
	public void setFoodId(Long foodId) {
		
		this.foodId = foodId;
	}
	
	public Long getFoodId() {
		
		return this.foodId;
	}
}
