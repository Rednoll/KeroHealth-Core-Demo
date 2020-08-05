package com.kero.health.core.domain.food.impl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import com.kero.health.core.domain.energy.EnergyAmount;
import com.kero.health.core.domain.food.FoodNutrient;
import com.kero.health.core.domain.food.SimpleFood;
import com.kero.health.core.domain.food.additionals.FoodNutrientId;
import com.kero.health.core.domain.food.nutrient.Nutrient;
import com.kero.health.core.domain.food.nutrient.NutrientDose;

@Entity
public class SimpleFoodImpl extends FoodBase implements SimpleFood {

	@ManyToMany(targetEntity = FoodNutrient.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<FoodNutrient> nutrients = new HashSet<>();

	@Embedded
	private EnergyAmount energy;
	
	public SimpleFoodImpl() {
		super();
		
	}
	
	public SimpleFoodImpl(Long id) {
		super(id);
		
	}

	@Override
	public int hashCode() {
		return Objects.hash(energy, nutrients);
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleFoodImpl other = (SimpleFoodImpl) obj;
		return Objects.equals(energy, other.energy) && Objects.equals(nutrients, other.nutrients) && Objects.equals(id, other.id);
	}

	@Override
	public FoodNutrient setNutrient(Nutrient nutrient, NutrientDose dose, int perFoodUnit) {
		
		if(perFoodUnit <= 0) throw new IllegalArgumentException("PerFoodUnit must be > 0");
		
		FoodNutrient foodNutrient = new FoodNutrient(new FoodNutrientId(this.getId(), nutrient.getId()), dose, perFoodUnit);
		
		for(FoodNutrient suspect : this.nutrients) {
			
			if(suspect.getNutrient().equals(nutrient)) {
				
				this.nutrients.remove(suspect);
				break;
			}
		}
		
		nutrients.add(foodNutrient);

		return foodNutrient;
	}
	
	public boolean hasNutrient(Nutrient nutrient) {
		
		return getNutrient(nutrient) != null;
	}
	
	public FoodNutrient getNutrient(Nutrient nutrient) {
		
		for(FoodNutrient foodNutrient : this.nutrients) {
			
			if(foodNutrient.getNutrient().equals(nutrient)) {
				
				return foodNutrient;
			}
		}
		
		return null;
	}

	public void setEnergy(EnergyAmount amount) {
		
		this.energy = amount;
	}
	
	@Override
	public EnergyAmount getEnergy() {
		
		if(this.energy != null) {
			
			return this.energy;
		}
		else {
		
			return this.calcEnergyAmount();
		}
	}
	
	@Override
	public Set<FoodNutrient> getNutrients() {
		
		return this.nutrients;
	}
	
	public SimpleFoodImpl save() {
		
		return (SimpleFoodImpl) repository.save(this);
	}
	
	public static SimpleFoodImpl create() {
		
		SimpleFoodImpl food = new SimpleFoodImpl();
		
		food = repository.save(food);
		
		return food;
	}
}
