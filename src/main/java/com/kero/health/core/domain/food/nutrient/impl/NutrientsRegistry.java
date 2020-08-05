package com.kero.health.core.domain.food.nutrient.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.kero.health.core.domain.food.nutrient.Nutrient;

public class NutrientsRegistry {

	private static final Map<Short, Nutrient> nutrients = new HashMap<>();
	
	public static final Nutrient PROTEIN = new BaseNutrient(0, "Protein", "P");
	public static final Nutrient FAT = new BaseNutrient(1, "Fat", "F");
	public static final Nutrient CARBOHYDRATE = new BaseNutrient(2, "Carbohydrate", "C");
	
	static {
		
		register(PROTEIN);
		register(FAT);
		register(CARBOHYDRATE);
	}
	
	private static void register(Nutrient nutrient) {
		
		nutrients.put(nutrient.getId(), nutrient);
	}
	
	public static Nutrient get(int id) {
		
		return nutrients.get((short) id);
	}
	
	public static Nutrient get(String name) {
		
		for(Nutrient nutrient : nutrients.values()) {
			
			if(nutrient.getName().equals(name)) {
				
				return nutrient;
			}
		}
		
		return null;
	}
	
	public static Collection<Nutrient> getAll() {
		
		return nutrients.values();
	}
}
