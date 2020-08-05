package com.kero.health.core.dao.food;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kero.health.core.domain.food.impl.FoodBase;

public interface FoodRepository extends JpaRepository<FoodBase, Long> {
	
}
