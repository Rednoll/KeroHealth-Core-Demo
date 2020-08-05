package com.kero.health.core.domain.food.nutrient;

import javax.persistence.AttributeConverter;

import com.kero.health.core.domain.food.nutrient.impl.NutrientsRegistry;

public class NutrientToIdConverter implements AttributeConverter<Nutrient, Short>{

	@Override
	public Short convertToDatabaseColumn(Nutrient attribute) {
	
		return attribute.getId();
	}

	@Override
	public Nutrient convertToEntityAttribute(Short dbData) {
	
		return NutrientsRegistry.get(dbData);
	}
}
