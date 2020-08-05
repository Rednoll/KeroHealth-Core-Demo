package com.kero.health.core.web.graphql.food;

import org.springframework.stereotype.Component;

import com.kero.health.core.domain.food.Food;

import graphql.kickstart.tools.GraphQLQueryResolver;

@Component
public class FoodQueryResolver implements GraphQLQueryResolver {

	public Food food(Long id) {
		
		if(id != null) {
			
			return Food.findById(id);
		}
		
		return null;
	}
}
