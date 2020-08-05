package com.kero.health.core.domain.food.diary.intake;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.Mockito;

import com.kero.health.core.domain.energy.EnergyAmount;
import com.kero.health.core.domain.food.Food;
import com.kero.health.core.domain.food.FoodDose;
import com.kero.health.core.domain.food.SimpleFood;
import com.kero.health.core.domain.food.diary.FoodDiary;
import com.kero.health.core.domain.food.diary.intake.exceptions.IntakeUnitAlreadyExistsException;
import com.kero.health.core.domain.food.diary.intake.impl.FoodIntakeImpl;
import com.kero.health.core.domain.food.impl.SimpleFoodImpl;
import com.kero.health.core.domain.food.nutrient.NutrientDose;
import com.kero.health.core.domain.food.nutrient.impl.NutrientsRegistry;

@RunWith(BlockJUnit4ClassRunner.class)
public class FoodIntakeTest {
	
	private FoodDiary diaryMock;
	
	@Before
	public void init() {
		
		diaryMock = Mockito.mock(FoodDiary.class);
	}
	
	@Test
	public void create() {
		
		new FoodIntakeImpl(null, diaryMock, "test", 200);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void create_DiaryNull() {
		
		new FoodIntakeImpl(null, null, "test", 200);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void create_RelativeTime0() {
		
		new FoodIntakeImpl(null, diaryMock, "test", 0);
	}
	
	@Test
	public void create_RelativeTime1() {
		
		new FoodIntakeImpl(null, diaryMock, "test", 1);
	}
	
	@Test
	public void create_RelativeTime1440() {
		
		new FoodIntakeImpl(null, diaryMock, "test", 1440);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void create_RelativeTime1441() {
		
		new FoodIntakeImpl(null, diaryMock, "test", 1441);
	}

	@Test
	public void createUnit() {
		
		Food food = Mockito.mock(Food.class);
		
		FoodIntake intake = new FoodIntakeImpl(null, diaryMock, "test", 300);
			intake.createUnit(food, new FoodDose(food, 200));
			
		assertTrue(intake.getUnits().size() == 1);
	}
	
	@Test(expected = IntakeUnitAlreadyExistsException.class)
	public void createUnit_Conflict() {
		
		Food food = Mockito.mock(Food.class);
		
		FoodIntake intake = new FoodIntakeImpl(null, diaryMock, "test", 300);
			intake.createUnit(food, new FoodDose(food, 300));
			intake.createUnit(food, new FoodDose(food, 400));
	}
	
	@Test
	public void getEnergyChange() {
	
		SimpleFood food1 = new SimpleFoodImpl(); 
			food1.setEnergy(new EnergyAmount(200));
		
		SimpleFood food2 = new SimpleFoodImpl(); 
			food2.setEnergy(new EnergyAmount(300));
		
		FoodIntakeImpl intake = new FoodIntakeImpl(null, diaryMock, "test", 200);
			intake.createUnit(food1, new FoodDose(food1, 100));
			intake.createUnit(food2, new FoodDose(food2, 50));
			
		EnergyAmount energy = intake.getEnergyChange();
	
		assertTrue(energy.getKCal() == 350);
	}
	
	@Test
	public void getConsumedNutrients() {
		
		SimpleFood food1 = new SimpleFoodImpl(); 
			food1.setNutrient(NutrientsRegistry.CARBOHYDRATE, new NutrientDose(NutrientsRegistry.CARBOHYDRATE, 100), 50);
			food1.setNutrient(NutrientsRegistry.FAT, new NutrientDose(NutrientsRegistry.FAT, 30), 100);
			food1.setNutrient(NutrientsRegistry.PROTEIN, new NutrientDose(NutrientsRegistry.PROTEIN, 500), 200);
			
		SimpleFood food2 = new SimpleFoodImpl(); 
			food2.setNutrient(NutrientsRegistry.CARBOHYDRATE, new NutrientDose(NutrientsRegistry.CARBOHYDRATE, 60), 100);
			food2.setNutrient(NutrientsRegistry.PROTEIN, new NutrientDose(NutrientsRegistry.PROTEIN, 250), 100);
		
		FoodIntakeImpl intake = new FoodIntakeImpl(null, diaryMock, "test", 200);
			intake.createUnit(food1, new FoodDose(food1, 100));
			intake.createUnit(food2, new FoodDose(food2, 50));
		
		NutrientDose carbohydrate = intake.getConsumedNutrient(NutrientsRegistry.CARBOHYDRATE);
		NutrientDose fat = intake.getConsumedNutrient(NutrientsRegistry.FAT);
		NutrientDose protein = intake.getConsumedNutrient(NutrientsRegistry.PROTEIN);
		
		assertTrue(carbohydrate.getRaw() == 230);
		assertTrue(fat.getRaw() == 30);
		assertTrue(protein.getRaw() == 375);
	}
}
