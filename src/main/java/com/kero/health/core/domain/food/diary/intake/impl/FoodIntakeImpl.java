package com.kero.health.core.domain.food.diary.intake.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Target;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kero.health.core.dao.food.diary.FoodIntakeRepository;
import com.kero.health.core.domain.energy.EnergyAmount;
import com.kero.health.core.domain.food.Food;
import com.kero.health.core.domain.food.FoodDose;
import com.kero.health.core.domain.food.diary.FoodDiary;
import com.kero.health.core.domain.food.diary.impl.FoodDiaryImpl;
import com.kero.health.core.domain.food.diary.intake.FoodIntake;
import com.kero.health.core.domain.food.diary.intake.exceptions.IntakeUnitAlreadyExistsException;
import com.kero.health.core.domain.food.diary.intake.unit.FoodIntakeUnit;
import com.kero.health.core.domain.food.diary.intake.unit.impl.FoodIntakeUnitImpl;
import com.kero.health.core.domain.food.diary.intake.unit.impl.FoodIntakeUnitImplId;
import com.kero.health.core.domain.food.nutrient.Nutrient;
import com.kero.health.core.domain.food.nutrient.NutrientDose;

@Entity(name = "FoodIntake")
public class FoodIntakeImpl implements FoodIntake, Comparable<FoodIntakeImpl> {
	
	private static FoodIntakeRepository repository;
	
	@EmbeddedId
	private FoodIntakeId id;
	
	private String name;
	
	@Column(updatable = false, insertable = false)
	private Short relativeTime;
	
	@MapsId("diaryId")
	@Target(FoodDiaryImpl.class)
	@ManyToOne(targetEntity = FoodDiaryImpl.class, fetch = FetchType.LAZY, optional = false)
	private FoodDiary diary;
	
	@OrderBy("dose.amount DESC")
	@OneToMany(targetEntity = FoodIntakeUnitImpl.class, mappedBy = "intake", cascade = CascadeType.ALL)
	private SortedSet<FoodIntakeUnit> units = new TreeSet<>();

	public FoodIntakeImpl() {}
	
	public FoodIntakeImpl(FoodIntakeId id, FoodDiary diary, String name, int relativeTime) {
		
		if(relativeTime < 1 || relativeTime > 1440) throw new IllegalArgumentException("RelativeTime must be > 0 and <= 1440!");
		if(diary == null) throw new IllegalArgumentException("Diary can't be null!");
		
		this.id = id;
		this.diary = diary;
		this.name = name;
		this.relativeTime = (short) relativeTime;
	}
	
	@Override
	public boolean hasUnit(Food food) {
		
		return getUnitByFood(food) != null;
	}
	
	public FoodIntakeUnit getUnitByFood(Food food) {

		for(FoodIntakeUnit unit : this.units) {
			
			if(unit.getFood().equals(food)) {
				
				return unit;
			}
		}
		
		return null;
	}
	
	public NutrientDose getConsumedNutrient(Nutrient nutrient) {
		
		NutrientDose dose = null;
		
		for(FoodIntakeUnit unit : getUnits()) {
			
			NutrientDose unitDose = unit.getNutrient(nutrient);
		
			if(unitDose == null) continue;
			
			if(dose == null) {
				
				dose = unitDose.clone();
			}
			else {
				
				dose = dose.add(unitDose);
			}
		}
		
		return dose;
	}
	
	public Set<NutrientDose> getConsumedNutrients() {

		Map<Nutrient, NutrientDose> nutrients = new HashMap<>();
		
		for(FoodIntakeUnit unit : getUnits()) {
			
			for(NutrientDose nutrientDose : unit.getNutrients()){
				
				Nutrient nutrient = nutrientDose.getNutrient();
			
				if(nutrients.containsKey(nutrient)) {
				
					nutrients.put(nutrient, nutrients.get(nutrient).add(nutrientDose));
				}
				else {
					
					nutrients.put(nutrient, nutrientDose);
				}
			}
		}
		
		return new HashSet<>(nutrients.values());
	}
	
	public EnergyAmount getEnergyChange() {
		
		long amount = 0;
		
		for(FoodIntakeUnit unit : getUnits()) {
			
			amount += unit.getEnergy().getRaw();
		}
		
		return amount != 0 ? new EnergyAmount(amount) : null;
	}
	
	@Override
	public FoodDiary getDiary() {
		
		return this.diary;
	}
	
	public String getName() {
		
		return this.name;
	}
	
	@Override
	public SortedSet<FoodIntakeUnit> getUnits() {
	
		return this.units;
	}

	@Override
	public Short getRelativeTime() {
		
		return this.relativeTime;
	}
	
	@Override
	public int compareTo(FoodIntakeImpl another) {
		
		return this.relativeTime - another.relativeTime;
	}
	
	public FoodIntakeId getId() {
		
		return this.id;
	}
	
	public FoodIntakeImpl save() {
		
		return repository.save(this);
	}
	
	public FoodIntakeUnit createUnit(Food food, FoodDose dose) {
		
		if(hasUnit(food)) throw new IntakeUnitAlreadyExistsException("Unit with food "+food+" already exists!");
		
		FoodIntakeUnitImpl unit = new FoodIntakeUnitImpl(new FoodIntakeUnitImplId(this.id, food.getId()), this, dose);
		
		this.units.add(unit);
		
		return unit;
	}
	
	public static boolean existsByNameAndRelativeTime(FoodDiary diary, String name, Short relativeTime) {
		
		return repository.existsByDiaryAndNameAndRelativeTime(diary, name, relativeTime);
	}
	
	public static FoodIntakeImpl findById(FoodIntakeId id) {
		
		return repository.findById(id).orElse(null);
	}
	
	@Service
	private static class RepositoryInject {
		
		@Autowired
		private FoodIntakeRepository rep;
	
		@PostConstruct
		private void inject() {
			
			repository = rep;
		}
	}
}
