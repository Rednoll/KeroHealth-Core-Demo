package com.kero.health.core.domain.food.nutrient.impl;

import com.kero.health.core.domain.food.nutrient.Nutrient;

public class BaseNutrient implements Nutrient {

	private Short id;
	private String name;
	private String abbreviation;
	
	public BaseNutrient() {}
	
	public BaseNutrient(int id, String name, String abbreviation) {
		
		this.id = (short) id;
		this.name = name;
		this.abbreviation = abbreviation;
	}
	
	@Override
	public String getName() {
		
		return this.name;
	}

	@Override
	public String getAbbreviation() {
		
		return this.abbreviation;
	}
	
	@Override
	public Short getId() {
		
		return this.id;
	}
}
