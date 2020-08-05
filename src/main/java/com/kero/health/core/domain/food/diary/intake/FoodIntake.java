package com.kero.health.core.domain.food.diary.intake;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.SortedSet;

import com.kero.health.core.domain.energy.EnergyChangeUnit;
import com.kero.health.core.domain.food.Food;
import com.kero.health.core.domain.food.FoodDose;
import com.kero.health.core.domain.food.diary.FoodDiary;
import com.kero.health.core.domain.food.diary.intake.impl.FoodIntakeId;
import com.kero.health.core.domain.food.diary.intake.impl.FoodIntakeImpl;
import com.kero.health.core.domain.food.diary.intake.unit.FoodIntakeUnit;
import com.kero.health.core.domain.food.nutrient.Nutrient;
import com.kero.health.core.domain.food.nutrient.NutrientDose;

public interface FoodIntake extends EnergyChangeUnit {
	
	public FoodDiary getDiary();
	
	public Short getRelativeTime();
	
	public FoodIntakeUnit getUnitByFood(Food food);
	
	public boolean hasUnit(Food food);
	
	public SortedSet<FoodIntakeUnit> getUnits();

	public NutrientDose getConsumedNutrient(Nutrient nutrient);
	public Set<NutrientDose> getConsumedNutrients();
	
	public FoodIntakeUnit createUnit(Food food, FoodDose dose);
	
	public FoodIntake save();
	
	public FoodIntakeId getId();
	
	public default Instant getTime() {
		
		return Instant.from(getDiary().getDay().getDate()).plus(getRelativeTime(), ChronoUnit.MINUTES);
	}
	
	public static FoodIntakeImpl findById(FoodIntakeId id) {

		return FoodIntakeImpl.findById(id);
	}
	
	public static boolean existsByNameAndRelativeTime(FoodDiary diary, String name, Short relativeTime) {
		
		return FoodIntakeImpl.existsByNameAndRelativeTime(diary, name, relativeTime);
	}
}
