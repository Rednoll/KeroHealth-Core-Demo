package com.kero.health.core.domain.food.nutrient;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.kero.health.core.domain.food.nutrient.impl.NutrientsRegistry;

@RunWith(BlockJUnit4ClassRunner.class)
public class NutrientDoseTest {

	@Test
	public void create() {
		
		new NutrientDose(NutrientsRegistry.PROTEIN, 10);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void create_Amount0() {
		
		new NutrientDose(NutrientsRegistry.PROTEIN, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void create_AmountMinus10() {
		
		new NutrientDose(NutrientsRegistry.PROTEIN, -10);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void create_NutrientNull() {
		
		new NutrientDose(null, 100);
	}
	
	@Test
	public void units_Convension() {
		
		NutrientDose dose = new NutrientDose(NutrientsRegistry.PROTEIN, 1000000000000L);
	
		assertTrue(dose.getRaw() == 1000000000000L);
		assertTrue(dose.getMicrogram() == 1000000000000L);
		assertTrue(dose.getMilligram() == 1000000000L);
		assertTrue(dose.getGram() == 1000000L);
		assertTrue(dose.getKilogram() == 1000L);
		assertTrue(dose.getTon() == 1L);
	}
	
	@Test
	public void sum() {
		
		NutrientDose a = new NutrientDose(NutrientsRegistry.PROTEIN, 10);
		NutrientDose b = new NutrientDose(NutrientsRegistry.PROTEIN, 22);
	
		NutrientDose sum = a.add(b);
	
		assertTrue(sum.getRaw() == 32);
	}
}
