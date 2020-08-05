package com.kero.health.core.dao.food.diary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kero.health.core.domain.food.diary.intake.unit.impl.FoodIntakeUnitImpl;
import com.kero.health.core.domain.food.diary.intake.unit.impl.FoodIntakeUnitImplId;

public interface FoodIntakeUnitRepository extends JpaRepository<FoodIntakeUnitImpl, FoodIntakeUnitImplId>{

}
