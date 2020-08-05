package com.kero.health.core.domain.food;

import javax.annotation.PostConstruct;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kero.health.core.dao.food.FoodNutrientRepository;
import com.kero.health.core.domain.food.additionals.FoodNutrientId;
import com.kero.health.core.domain.food.nutrient.Nutrient;
import com.kero.health.core.domain.food.nutrient.NutrientDose;

@Entity(name = "FoodNutrient")
public class FoodNutrient implements Cloneable {

	private static FoodNutrientRepository repository;
	
	@EmbeddedId
	private FoodNutrientId id;

	@Embedded
	private NutrientDose dose;
	
	private int perFoodUnit;
	
	public FoodNutrient() {}
	
	public FoodNutrient(FoodNutrientId id, NutrientDose dose, int perFoodUnit) {
		
		this.id = id;
		this.dose = dose;
		this.perFoodUnit = perFoodUnit;
	}

	public FoodNutrient toPerFoodUnit(int targetPerFoodUnit) {
		
		return new FoodNutrient(null, this.dose.multiply((double) targetPerFoodUnit / (double) this.perFoodUnit), targetPerFoodUnit);
	}
	
	public NutrientDose getDose() {
		
		return this.dose;
	}
	
	public int getPerFoodUnit() {
		
		return this.perFoodUnit;
	}
	
	public Nutrient getNutrient() {
		
		return this.dose.getNutrient();
	}
	
	public FoodNutrientId getId() {
		
		return this.id;
	}
	
	public FoodNutrient save() {
		
		return repository.save(this);
	}
	
	@Service
	private static class RepositoryInjector {
		
		@Autowired
		private FoodNutrientRepository rep;
		
		@PostConstruct
		private void inject() {
			
			repository = rep;
		}
	}
}
