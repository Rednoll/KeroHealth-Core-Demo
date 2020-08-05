package com.kero.health.core.domain.food.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;

import com.kero.health.core.domain.energy.EnergyAmount;
import com.kero.health.core.domain.food.CompositeFood;
import com.kero.health.core.domain.food.Food;
import com.kero.health.core.domain.food.FoodNutrient;
import com.kero.health.core.domain.food.IngridientDose;
import com.kero.health.core.domain.food.additionals.FoodNutrientId;
import com.kero.health.core.domain.food.additionals.IngridientDoseId;
import com.kero.health.core.domain.food.nutrient.Nutrient;
import com.kero.health.core.domain.food.nutrient.NutrientDose;
import com.kero.health.core.domain.food.nutrient.impl.NutrientsRegistry;

@Entity(name = "CompositeFood")
public class CompositeFoodImpl extends FoodBase implements CompositeFood {

	public static int compositePerFoodUnit = 100;
	
	@OrderBy("id")
	@ManyToMany(targetEntity = IngridientDose.class)
	private SortedSet<IngridientDose> ingridients = new TreeSet<>();
	
	@Override
	public Set<FoodNutrient> getNutrients() {
	
		Map<Nutrient, FoodNutrient> nutrients = new HashMap<>();
		
		for(Nutrient nutrient : NutrientsRegistry.getAll()) {
			
			FoodNutrient foodNutrient = getNutrient(nutrient);
		
			if(foodNutrient != null) {
				
				nutrients.put(nutrient, foodNutrient);
			}
		}
		
		return new HashSet<>(nutrients.values());
	}
	
	@Override
	public boolean hasNutrient(Nutrient nutrient) {
		
		return getNutrient(nutrient) != null;
	}
	
	@Override
	public FoodNutrient getNutrient(Nutrient nutrient) {
	
		NutrientDose dose = null;
		
		for(IngridientDose ingridientDose : getIngridients()) {
			
			Food ingridient = ingridientDose.getIngridient();
			int share = ingridientDose.getShareFrom(compositePerFoodUnit);
			
			FoodNutrient ingridientFoodNutrient = ingridient.getNutrient(nutrient);
			
			if(ingridientFoodNutrient == null) continue;
			
			ingridientFoodNutrient = ingridientFoodNutrient.toPerFoodUnit(share);
		
			NutrientDose ingridientNutrientDose = ingridientFoodNutrient.getDose();

			if(dose != null) {
				
				dose = dose.add(ingridientNutrientDose);
			}
			else {
				
				dose = ingridientNutrientDose.clone();
			}
		}
		
		if(dose != null) {
			
			return new FoodNutrient(new FoodNutrientId(this.getId(), nutrient.getId()), dose, compositePerFoodUnit);
		}
		else {
		
			return null;
		}
	}
	
	@Override
	public void addIngridient(Food ingridient, int shareBase) {
		
		this.ingridients.add(new IngridientDose(new IngridientDoseId(this.getId(), ingridient.getId()), ingridient, shareBase));
	}
	
	@Override
	public Set<IngridientDose> getIngridients() {
	
		return this.ingridients;
	}
	
	@Override
	public EnergyAmount getEnergy() {
		
		return this.calcEnergyAmount();
	}
	
	@Override
	protected EnergyAmount calcEnergyAmount() {
		
		long rawAmount = 0;
		
		for(IngridientDose ingridientDose : this.ingridients) {
			
			Food ingridient = ingridientDose.getIngridient();
			int share = ingridientDose.getShareFrom(compositePerFoodUnit);
			
			rawAmount += ingridient.getEnergy().multiply((double) share / (double) compositePerFoodUnit).getRaw();
		}
		
		return new EnergyAmount(rawAmount);
	}
	
	public CompositeFoodImpl save() {
		
		return (CompositeFoodImpl) repository.save(this);
	}
}
