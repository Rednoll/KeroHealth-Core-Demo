package com.kero.health.core.domain.food.impl;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kero.health.core.dao.food.FoodRepository;
import com.kero.health.core.domain.MafsUtils;
import com.kero.health.core.domain.energy.EnergyAmount;
import com.kero.health.core.domain.food.Food;
import com.kero.health.core.domain.food.FoodNutrient;
import com.kero.health.core.domain.food.nutrient.NutrientDose;
import com.kero.health.core.domain.food.nutrient.impl.NutrientsRegistry;

@Entity(name = "Food")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class FoodBase implements Food, Comparable<FoodBase> {
	
	protected static FoodRepository repository;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_seq")
	@SequenceGenerator(name = "food_seq", sequenceName = "food_seq", allocationSize = 1)
	protected Long id;
	
	public FoodBase() {}
	
	public FoodBase(Long id) {
		
		this.id = id;
	}
	
	public int compareTo(FoodBase another) {
		
		long myId = this.id != null ? this.id.longValue() : 0;
		long anotherId = another.id != null ? another.id.longValue() : 1;
		
		return (int) (myId - anotherId);
	}
	
	protected EnergyAmount calcEnergyAmount() {
	
		FoodNutrient carbohydrate = getNutrient(NutrientsRegistry.CARBOHYDRATE);
		FoodNutrient protein = getNutrient(NutrientsRegistry.PROTEIN);
		FoodNutrient fat = getNutrient(NutrientsRegistry.FAT);
		
		NutrientDose carbohydrateDose = carbohydrate.getDose().multiply(100D / carbohydrate.getPerFoodUnit());
		NutrientDose proteinDose = protein.getDose().multiply(100D / protein.getPerFoodUnit());
		NutrientDose fatDose = fat.getDose().multiply(100D / fat.getPerFoodUnit());
		
		double energy = carbohydrateDose.getMilligram() * 4 + proteinDose.getMilligram() * 4 + fatDose.getMilligram() * 9;

		energy /= MafsUtils.ACCURACY_MULTIPLIER;
		
		return new EnergyAmount((long) energy);
	}
	
	public Long getId() {
		
		return this.id;
	}
	
	public FoodBase save() {
		
		return repository.save(this);
	}
	
	public static FoodBase findById(Long id) {
		
		return repository.findById(id).orElse(null);
	}
	
	@Service
	private static class RepositoryInjector {
		
		@Autowired
		private FoodRepository rep;
		
		@PostConstruct
		private void inject() {
			
			repository = rep;
		}
	}
}
