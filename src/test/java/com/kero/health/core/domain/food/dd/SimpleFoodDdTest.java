package com.kero.health.core.domain.food.dd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.kero.health.core.domain.food.Food;
import com.kero.health.core.domain.food.FoodNutrient;
import com.kero.health.core.domain.food.SimpleFood;
import com.kero.health.core.domain.food.impl.SimpleFoodImpl;
import com.kero.health.core.domain.food.nutrient.NutrientDose;
import com.kero.health.core.domain.food.nutrient.NutrientDoseUnit;
import com.kero.health.core.domain.food.nutrient.impl.NutrientsRegistry;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class SimpleFoodDdTest {

	@Test
	public void nutrientsRetention() {
		
		NutrientDose carbohydratesDose = new NutrientDose(NutrientsRegistry.CARBOHYDRATE, 12 * NutrientDoseUnit.GRAM.multiplier);
		NutrientDose fatsDose = new NutrientDose(NutrientsRegistry.FAT, 20 * NutrientDoseUnit.GRAM.multiplier);
		NutrientDose proteinsDose = new NutrientDose(NutrientsRegistry.PROTEIN, 3 * NutrientDoseUnit.GRAM.multiplier);
		
		SimpleFood food = new SimpleFoodImpl();
			food.setNutrient(NutrientsRegistry.CARBOHYDRATE, carbohydratesDose, 200);
			food.setNutrient(NutrientsRegistry.FAT, fatsDose, 50);
			food.setNutrient(NutrientsRegistry.PROTEIN, proteinsDose, 25);
			
		food = food.save();
			
		Long foodId = food.getId();
		
		SimpleFood fetchedFood = (SimpleFood) Food.findById(foodId);
		
		FoodNutrient carbohydrates = fetchedFood.getNutrient(NutrientsRegistry.CARBOHYDRATE);
		FoodNutrient fats = fetchedFood.getNutrient(NutrientsRegistry.FAT);
		FoodNutrient proteins = fetchedFood.getNutrient(NutrientsRegistry.PROTEIN);
		
		assertEquals(carbohydrates.getDose(), carbohydratesDose);
		assertEquals(fats.getDose(), fatsDose);
		assertEquals(proteins.getDose(), proteinsDose);
	
		assertEquals(carbohydrates.getPerFoodUnit(), 200);
		assertEquals(fats.getPerFoodUnit(), 50);
		assertEquals(proteins.getPerFoodUnit(), 25);
	}
}
