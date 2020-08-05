package com.kero.health.core.domain.food.diary.intake.unit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.kero.health.core.domain.energy.EnergyAmount;
import com.kero.health.core.domain.food.FoodDose;
import com.kero.health.core.domain.food.SimpleFood;
import com.kero.health.core.domain.food.diary.intake.unit.impl.FoodIntakeUnitImpl;
import com.kero.health.core.domain.food.impl.SimpleFoodImpl;
import com.kero.health.core.domain.food.nutrient.NutrientDose;
import com.kero.health.core.domain.food.nutrient.NutrientDoseUnit;
import com.kero.health.core.domain.food.nutrient.impl.NutrientsRegistry;

@RunWith(BlockJUnit4ClassRunner.class)
public class FoodIntakeUnitTest {

	@Test
	public void getEnergy() {
		
		SimpleFood food = new SimpleFoodImpl();
			food.setEnergy(new EnergyAmount(150));
		
		FoodIntakeUnit unit = new FoodIntakeUnitImpl(null, null, new FoodDose(food, 250));
		
		EnergyAmount amount = unit.getEnergy();
	
		assertTrue(amount.getRaw() == 375);
	}
	
	@Test
	public void getNutrients() {
		
		SimpleFood food = new SimpleFoodImpl();
			food.setNutrient(NutrientsRegistry.CARBOHYDRATE, new NutrientDose(NutrientsRegistry.CARBOHYDRATE, 12 * NutrientDoseUnit.GRAM.multiplier), 200);
			food.setNutrient(NutrientsRegistry.FAT, new NutrientDose(NutrientsRegistry.FAT, 20 * NutrientDoseUnit.GRAM.multiplier), 50);
			food.setNutrient(NutrientsRegistry.PROTEIN, new NutrientDose(NutrientsRegistry.PROTEIN, 3 * NutrientDoseUnit.GRAM.multiplier), 25);
		
		FoodIntakeUnit unit = new FoodIntakeUnitImpl(null, null, new FoodDose(food, 250));
		
		assertTrue(unit.getNutrient(NutrientsRegistry.CARBOHYDRATE).getGram() == 15);
		assertTrue(unit.getNutrient(NutrientsRegistry.FAT).getGram() == 100);
		assertTrue(unit.getNutrient(NutrientsRegistry.PROTEIN).getGram() == 30);
	}
}
