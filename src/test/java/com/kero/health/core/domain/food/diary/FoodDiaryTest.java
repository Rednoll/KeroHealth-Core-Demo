package com.kero.health.core.domain.food.diary;

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
import com.kero.health.core.domain.food.diary.exceptions.IntakeAlreadyExistsException;
import com.kero.health.core.domain.food.diary.impl.FoodDiaryImpl;
import com.kero.health.core.domain.food.diary.intake.FoodIntake;
import com.kero.health.core.domain.food.impl.SimpleFoodImpl;
import com.kero.health.core.domain.food.nutrient.NutrientDose;
import com.kero.health.core.domain.food.nutrient.impl.NutrientsRegistry;
import com.kero.health.core.domain.life.units.LifeDay;

@RunWith(BlockJUnit4ClassRunner.class)
public class FoodDiaryTest {

	private LifeDay mockDay = null;
	
	@Before
	public void init() {
		
		mockDay = Mockito.mock(LifeDay.class);
	}
	
	@Test
	public void create() {
		
		new FoodDiaryImpl(null, mockDay);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void create_DayNull() {
		
		new FoodDiaryImpl(null, null);
	}
	
	@Test
	public void hasIntakeByName() {
		
		FoodDiary diary = new FoodDiaryImpl();
			diary.createIntake("test", 200);
	
		assertTrue(diary.hasIntake("test"));
	}
	
	@Test
	public void hasIntakeByRelativeTime() {
		
		FoodDiary diary = new FoodDiaryImpl();
			diary.createIntake("test", 200);
	
		assertTrue(diary.hasIntake(200));
	}
	
	@Test(expected = IntakeAlreadyExistsException.class)
	public void createIntake_ExistsName() {
		
		FoodDiary diary = new FoodDiaryImpl();
			diary.createIntake("test", 200);
			diary.createIntake("test", 300);
	}
	
	@Test(expected = IntakeAlreadyExistsException.class)
	public void createIntake_ExistsTime() {
		
		FoodDiary diary = new FoodDiaryImpl();
			diary.createIntake("test", 200);
			diary.createIntake("test2", 200);
	}
	
	@Test
	public void getConsumedEnergy() {
		
		Food food1 = Mockito.mock(Food.class);
		Mockito.when(food1.getEnergy()).thenReturn(new EnergyAmount(100));
		
		Food food2 = Mockito.mock(Food.class);
		Mockito.when(food2.getEnergy()).thenReturn(new EnergyAmount(400));
		
		FoodDiary diary = new FoodDiaryImpl();
			
			FoodIntake breakfast = diary.createIntake("breakfast", 200);
				breakfast.createUnit(food1, new FoodDose(food1, 50));
				breakfast.createUnit(food2, new FoodDose(food2, 150));
			
			FoodIntake dinner = diary.createIntake("dinner", 1200);
				dinner.createUnit(food1, new FoodDose(food1, 200));
				
		EnergyAmount energy = diary.getConsumedEnergy();
	
		assertTrue(energy.getKCal() == 850);
	}
	
	@Test
	public void getNutrients() {
		
		SimpleFood food1 = new SimpleFoodImpl();
			food1.setNutrient(NutrientsRegistry.CARBOHYDRATE, new NutrientDose(NutrientsRegistry.CARBOHYDRATE, 250), 100);
			food1.setNutrient(NutrientsRegistry.FAT, new NutrientDose(NutrientsRegistry.FAT, 30), 200);
			
		SimpleFood food2 = new SimpleFoodImpl();
			food2.setNutrient(NutrientsRegistry.CARBOHYDRATE, new NutrientDose(NutrientsRegistry.CARBOHYDRATE, 150), 50);
			food2.setNutrient(NutrientsRegistry.PROTEIN, new NutrientDose(NutrientsRegistry.PROTEIN, 50), 50);
			
		FoodDiary diary = new FoodDiaryImpl();
			
			FoodIntake breakfast = diary.createIntake("breakfast", 200);
				breakfast.createUnit(food1, new FoodDose(food1, 50));
				breakfast.createUnit(food2, new FoodDose(food2, 150));
				
			FoodIntake dinner = diary.createIntake("dinner", 1200);
				dinner.createUnit(food1, new FoodDose(food1, 200));

		assertTrue(diary.getConsumedNutrient(NutrientsRegistry.CARBOHYDRATE).getRaw() == 1075);
		assertTrue(diary.getConsumedNutrient(NutrientsRegistry.FAT).getRaw() == 37);
		assertTrue(diary.getConsumedNutrient(NutrientsRegistry.PROTEIN).getRaw() == 150);
	}
}
