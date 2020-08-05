package com.kero.health.core.domain.food.diary.intake.unit;

import java.util.Set;

import com.kero.health.core.domain.energy.EnergyAmount;
import com.kero.health.core.domain.food.Food;
import com.kero.health.core.domain.food.FoodDose;
import com.kero.health.core.domain.food.diary.intake.FoodIntake;
import com.kero.health.core.domain.food.nutrient.Nutrient;
import com.kero.health.core.domain.food.nutrient.NutrientDose;

public interface FoodIntakeUnit {

	public FoodDose getDose();
	public Food getFood();
	public FoodIntake getIntake();
	
	public NutrientDose getNutrient(Nutrient nutrient);
	public Set<NutrientDose> getNutrients();
	public EnergyAmount getEnergy();
	
	public FoodIntakeUnit save();
}
