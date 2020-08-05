package com.kero.health.core.domain.food;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.kero.health.core.domain.energy.EnergyAmount;
import com.kero.health.core.domain.food.impl.SimpleFoodImpl;
import com.kero.health.core.domain.food.nutrient.NutrientDose;
import com.kero.health.core.domain.food.nutrient.NutrientDoseUnit;
import com.kero.health.core.domain.food.nutrient.impl.NutrientsRegistry;

@RunWith(BlockJUnit4ClassRunner.class)
public class SimpleFoodTest {
	
	@Test
	public void calcEnergyAmountByNutrients() {
		
		SimpleFood food = new SimpleFoodImpl();
			food.setNutrient(NutrientsRegistry.CARBOHYDRATE, new NutrientDose(NutrientsRegistry.CARBOHYDRATE, 12 * NutrientDoseUnit.GRAM.multiplier), 200);
			food.setNutrient(NutrientsRegistry.FAT, new NutrientDose(NutrientsRegistry.FAT, 20 * NutrientDoseUnit.GRAM.multiplier), 50);
			food.setNutrient(NutrientsRegistry.PROTEIN, new NutrientDose(NutrientsRegistry.PROTEIN, 3 * NutrientDoseUnit.GRAM.multiplier), 25);

		EnergyAmount amount = food.getEnergy();
		
		assertTrue(amount.getKCal() == 432);
	}
	
	@Test
	public void getEnergyAmount_IgnoreNutrients() {
		
		SimpleFood food = new SimpleFoodImpl();
			food.setNutrient(NutrientsRegistry.CARBOHYDRATE, new NutrientDose(NutrientsRegistry.CARBOHYDRATE, 12 * NutrientDoseUnit.GRAM.multiplier), 200);
			food.setNutrient(NutrientsRegistry.FAT, new NutrientDose(NutrientsRegistry.FAT, 20 * NutrientDoseUnit.GRAM.multiplier), 50);
			food.setNutrient(NutrientsRegistry.PROTEIN, new NutrientDose(NutrientsRegistry.PROTEIN, 3 * NutrientDoseUnit.GRAM.multiplier), 25);

		food.setEnergy(new EnergyAmount(250));
			
		EnergyAmount amount = food.getEnergy();
	
		assertTrue(amount.getKCal() == 250);
	}
	
	@Test
	public void setNutrient_PerFoodUnit10() {
		
		SimpleFood food = new SimpleFoodImpl();
			food.setNutrient(NutrientsRegistry.CARBOHYDRATE, new NutrientDose(NutrientsRegistry.CARBOHYDRATE, 12 * NutrientDoseUnit.GRAM.multiplier), 10);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setNutrient_PerFoodUnit0() {
		
		SimpleFood food = new SimpleFoodImpl();
			food.setNutrient(NutrientsRegistry.CARBOHYDRATE, new NutrientDose(NutrientsRegistry.CARBOHYDRATE, 12 * NutrientDoseUnit.GRAM.multiplier), 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setNutrient_PerFoodUnitMinus20() {
		
		SimpleFood food = new SimpleFoodImpl();
			food.setNutrient(NutrientsRegistry.CARBOHYDRATE, new NutrientDose(NutrientsRegistry.CARBOHYDRATE, 12 * NutrientDoseUnit.GRAM.multiplier), -20);
	}
	
	@Test
	public void setNutrient_Override() {
		
		SimpleFood food = new SimpleFoodImpl();
			food.setNutrient(NutrientsRegistry.CARBOHYDRATE, new NutrientDose(NutrientsRegistry.CARBOHYDRATE, 12 * NutrientDoseUnit.GRAM.multiplier), 10);
			food.setNutrient(NutrientsRegistry.CARBOHYDRATE, new NutrientDose(NutrientsRegistry.CARBOHYDRATE, 12 * NutrientDoseUnit.GRAM.multiplier), 30);
			
		assertTrue(food.getNutrient(NutrientsRegistry.CARBOHYDRATE).getPerFoodUnit() == 30);
	}
}