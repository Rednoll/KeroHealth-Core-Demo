package com.kero.health.core.domain.food.nutrient;

public enum NutrientDoseUnit {
	
	MICROGRAMS(1), MILLIGRAM(1000), GRAM(1000000), KILOGRAM(1000000000), TON(1000000000),
	DFE, // Dietary Folate Equivalents
	IU, // International Unit
	MGNE, // Milligrams Niacin Equivalents
	MGAT, // Milligrams Alpha Tocopherol
	RAE; // MICROGRAMS Retinol
	
	public final long multiplier;
	
	private NutrientDoseUnit() {
		
		this.multiplier = 0;
	}
	
	private NutrientDoseUnit(long multiplier) {
		
		this.multiplier = multiplier;
	}
}
