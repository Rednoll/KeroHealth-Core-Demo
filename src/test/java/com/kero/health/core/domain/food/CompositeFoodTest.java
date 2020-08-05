package com.kero.health.core.domain.food;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.kero.health.core.domain.energy.EnergyAmount;
import com.kero.health.core.domain.food.impl.CompositeFoodImpl;
import com.kero.health.core.domain.food.impl.SimpleFoodImpl;
import com.kero.health.core.domain.food.nutrient.NutrientDose;
import com.kero.health.core.domain.food.nutrient.impl.NutrientsRegistry;

@RunWith(BlockJUnit4ClassRunner.class)
public class CompositeFoodTest {

	@Test
	public void calcEnergyFromIngridients() {
		
		CompositeFoodImpl food = new CompositeFoodImpl();
	
			SimpleFood ingr1 = new SimpleFoodImpl();
				ingr1.setEnergy(new EnergyAmount(100));
				
			food.addIngridient(ingr1, 5000);
			
			SimpleFood ingr2 = new SimpleFoodImpl();
				ingr2.setEnergy(new EnergyAmount(325));
			
			food.addIngridient(ingr2, 5000);
	
		EnergyAmount energy = food.getEnergy();
	
		assertTrue(energy.getRaw() == 212);
	}
	
	@Test
	public void calcNutrientsFromIngridients() {
		
		CompositeFoodImpl food = new CompositeFoodImpl();
	
			SimpleFood ingr1 = new SimpleFoodImpl();
				ingr1.setNutrient(NutrientsRegistry.CARBOHYDRATE, new NutrientDose(NutrientsRegistry.CARBOHYDRATE, 8), 100);
				ingr1.setNutrient(NutrientsRegistry.FAT, new NutrientDose(NutrientsRegistry.FAT, 28), 75);
				ingr1.setNutrient(NutrientsRegistry.PROTEIN, new NutrientDose(NutrientsRegistry.PROTEIN, 10), 150);
				
			food.addIngridient(ingr1, 5000);
			
			SimpleFood ingr2 = new SimpleFoodImpl();
				ingr2.setNutrient(NutrientsRegistry.CARBOHYDRATE, new NutrientDose(NutrientsRegistry.CARBOHYDRATE, 18), 50);
				ingr2.setNutrient(NutrientsRegistry.FAT, new NutrientDose(NutrientsRegistry.FAT, 30), 100);
				ingr2.setNutrient(NutrientsRegistry.PROTEIN, new NutrientDose(NutrientsRegistry.PROTEIN, 40), 200);
			
			food.addIngridient(ingr2, 5000);
	
		FoodNutrient carbohydrate = food.getNutrient(NutrientsRegistry.CARBOHYDRATE).toPerFoodUnit(100);
		FoodNutrient fat = food.getNutrient(NutrientsRegistry.FAT).toPerFoodUnit(100);
		FoodNutrient protein = food.getNutrient(NutrientsRegistry.PROTEIN).toPerFoodUnit(100);

		assertTrue(carbohydrate.getDose().getRaw() == 22);
		assertTrue(fat.getDose().getRaw() == 33);
		assertTrue(protein.getDose().getRaw() == 13);
	}
}
