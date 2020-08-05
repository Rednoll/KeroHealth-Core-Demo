package com.kero.health.core.domain.food.diary.intake.unit.impl;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.hibernate.annotations.Target;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kero.health.core.dao.food.diary.FoodIntakeUnitRepository;
import com.kero.health.core.domain.energy.EnergyAmount;
import com.kero.health.core.domain.food.Food;
import com.kero.health.core.domain.food.FoodDose;
import com.kero.health.core.domain.food.FoodNutrient;
import com.kero.health.core.domain.food.diary.intake.FoodIntake;
import com.kero.health.core.domain.food.diary.intake.impl.FoodIntakeImpl;
import com.kero.health.core.domain.food.diary.intake.unit.FoodIntakeUnit;
import com.kero.health.core.domain.food.nutrient.Nutrient;
import com.kero.health.core.domain.food.nutrient.NutrientDose;

@Entity(name = "FoodIntakeUnit")
public class FoodIntakeUnitImpl implements FoodIntakeUnit, Comparable<FoodIntakeUnitImpl> {

	private static FoodIntakeUnitRepository repository;
	
	@EmbeddedId
	private FoodIntakeUnitImplId id;
	
	@MapsId("intakeId")
	@Target(FoodIntakeImpl.class)
	@ManyToOne(targetEntity = FoodIntakeImpl.class, fetch = FetchType.EAGER, optional = false)
	private FoodIntake intake;
	
	@Embedded
	private FoodDose dose;
	
	public FoodIntakeUnitImpl() {}
	
	public FoodIntakeUnitImpl(FoodIntakeUnitImplId id, FoodIntake intake, FoodDose dose) {
		
		this.id = id;
		this.intake = intake;
		this.dose = dose;
	}
	
	public EnergyAmount getEnergy() {
		
		Food food = dose.getFood();
	
		double amount = dose.getAmount();
		
		return food.getEnergy().multiply(amount / 100D);
	}
	
	public NutrientDose getNutrient(Nutrient nutrient) {
		
		Food food = this.getFood();
	
		FoodNutrient foodNutrient = food.getNutrient(nutrient);

		if(foodNutrient == null) return null;
		
		return calcDoseFromFoodNutrient(foodNutrient);
	}
	
	public Set<NutrientDose> getNutrients() {
		
		Set<NutrientDose> nutrients = new HashSet<>();
		
		Food food = this.getFood();
				
		for(FoodNutrient foodNutrient : food.getNutrients()) {

			nutrients.add(calcDoseFromFoodNutrient(foodNutrient));
		}
		
		return nutrients;
	}
	
	private NutrientDose calcDoseFromFoodNutrient(FoodNutrient foodNutrient) {
		
		if(foodNutrient == null) return null;
		
		return foodNutrient.toPerFoodUnit((int) this.dose.getAmount()).getDose();
	}
	
	@Override
	public FoodDose getDose() {
		
		return this.dose;
	}

	@Override
	public Food getFood() {
		
		return this.dose.getFood();
	}

	@Override
	public FoodIntake getIntake() {
	
		return this.intake;
	}
	
	@Override
	public int compareTo(FoodIntakeUnitImpl another) {
	
		return (int) (this.dose.getAmount() - another.dose.getAmount());
	}

	@Override
	public FoodIntakeUnitImpl save() {
		
		return repository.save(this);
	}
	
	@Service
	private static class RepositoryInjector {
	
		@Autowired
		private FoodIntakeUnitRepository rep;
		
		@PostConstruct
		private void inject() {
		
			repository = rep;
		}
	}
}
