package com.kero.health.core.dao.food.diary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kero.health.core.domain.food.diary.FoodDiary;
import com.kero.health.core.domain.food.diary.intake.impl.FoodIntakeImpl;
import com.kero.health.core.domain.food.diary.intake.impl.FoodIntakeId;

public interface FoodIntakeRepository extends JpaRepository<FoodIntakeImpl, FoodIntakeId> {

	public boolean existsByDiaryAndNameAndRelativeTime(FoodDiary diary, String name, Short relativeTime);
}
