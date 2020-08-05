package com.kero.health.core.dao.food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kero.health.core.domain.food.FoodNutrient;
import com.kero.health.core.domain.food.additionals.FoodNutrientId;

@Repository
public interface FoodNutrientRepository extends JpaRepository<FoodNutrient, FoodNutrientId>{

}
