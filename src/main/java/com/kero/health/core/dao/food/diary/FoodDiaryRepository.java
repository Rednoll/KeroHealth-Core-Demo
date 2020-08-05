package com.kero.health.core.dao.food.diary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kero.health.core.domain.food.diary.impl.FoodDiaryImpl;

public interface FoodDiaryRepository extends JpaRepository<FoodDiaryImpl, Long>{

}
