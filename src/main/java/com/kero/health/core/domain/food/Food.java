package com.kero.health.core.domain.food;

import java.util.Set;

import com.kero.health.core.domain.energy.EnergyAmount;
import com.kero.health.core.domain.food.impl.FoodBase;
import com.kero.health.core.domain.food.nutrient.Nutrient;

public interface Food {

	public boolean hasNutrient(Nutrient nutrient);
	public FoodNutrient getNutrient(Nutrient nutrient);
	public Set<FoodNutrient> getNutrients();
	
	public EnergyAmount getEnergy();

	public Long getId();
	
	public Food save();
	
	public static Food findById(Long id) {
		
		return FoodBase.findById(id);
	}
}
