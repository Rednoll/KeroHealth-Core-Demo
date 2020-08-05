package com.kero.health.core.domain.food.diary;

import java.util.Set;
import java.util.SortedSet;

import com.kero.health.core.domain.energy.EnergyAmount;
import com.kero.health.core.domain.food.diary.intake.FoodIntake;
import com.kero.health.core.domain.food.nutrient.Nutrient;
import com.kero.health.core.domain.food.nutrient.NutrientDose;
import com.kero.health.core.domain.life.units.LifeDay;

public interface FoodDiary {

	public SortedSet<FoodIntake> getIntakes();
	public LifeDay getDay();

	public EnergyAmount getConsumedEnergy();
	public Set<NutrientDose> getConsumedNutrients();
	public NutrientDose getConsumedNutrient(Nutrient nutrient);
	
	public FoodDiary save();
	
	public Long getId();
	
	public boolean hasIntake(String name);
	public boolean hasIntake(int relativeTime);
	
	public FoodIntake createIntake(String name, int relativeTime);
}
