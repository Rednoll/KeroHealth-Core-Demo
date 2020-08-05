package com.kero.health.core.domain.food;

import java.util.Set;

public interface CompositeFood extends Food {

	public void addIngridient(Food food, int shareBase);
	public Set<IngridientDose> getIngridients();
	
	public CompositeFood save();
}
