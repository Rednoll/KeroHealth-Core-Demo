package com.kero.health.core.domain.food.diary.intake.dd;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.kero.health.core.domain.food.Food;
import com.kero.health.core.domain.food.FoodDose;
import com.kero.health.core.domain.food.diary.FoodDiary;
import com.kero.health.core.domain.food.diary.impl.FoodDiaryImpl;
import com.kero.health.core.domain.food.diary.intake.FoodIntake;
import com.kero.health.core.domain.food.diary.intake.impl.FoodIntakeId;
import com.kero.health.core.domain.food.impl.SimpleFoodImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FoodIntakeDdTest {
	
	@Test
	public void unitsRetention() {
	
		Food food1 = SimpleFoodImpl.create();
		Food food2 = SimpleFoodImpl.create();
		
		FoodDiary diary = new FoodDiaryImpl();
		
		diary = diary.save();
		
		FoodIntake intake = diary.createIntake("test", 200);
			intake.createUnit(food1, new FoodDose(food1, 300));
			intake.createUnit(food2, new FoodDose(food2, 400));
		
		intake = intake.save();
		
		FoodIntakeId intakeId = intake.getId();
		
		FoodIntake fetchedIntake = FoodIntake.findById(intakeId);
	
		assertNotNull(fetchedIntake.getUnitByFood(food1));
		assertNotNull(fetchedIntake.getUnitByFood(food2));
	}
}
