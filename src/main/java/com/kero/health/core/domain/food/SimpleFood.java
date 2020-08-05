package com.kero.health.core.domain.food;

import com.kero.health.core.domain.energy.EnergyAmount;
import com.kero.health.core.domain.food.nutrient.Nutrient;
import com.kero.health.core.domain.food.nutrient.NutrientDose;

public interface SimpleFood extends Food {

	public FoodNutrient setNutrient(Nutrient nutrient, NutrientDose dose, int perFoodUnit);

	public void setEnergy(EnergyAmount amount);
	
	public SimpleFood save();
}
