package com.kero.health.core.domain.food.diary.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kero.health.core.dao.food.diary.FoodDiaryRepository;
import com.kero.health.core.domain.energy.EnergyAmount;
import com.kero.health.core.domain.food.diary.FoodDiary;
import com.kero.health.core.domain.food.diary.exceptions.IntakeAlreadyExistsException;
import com.kero.health.core.domain.food.diary.intake.FoodIntake;
import com.kero.health.core.domain.food.diary.intake.impl.FoodIntakeId;
import com.kero.health.core.domain.food.diary.intake.impl.FoodIntakeImpl;
import com.kero.health.core.domain.food.nutrient.Nutrient;
import com.kero.health.core.domain.food.nutrient.NutrientDose;
import com.kero.health.core.domain.life.units.LifeDay;
import com.kero.health.core.domain.life.units.impl.LifeDayImpl;

@Entity(name = "FoodDiary")
public class FoodDiaryImpl implements FoodDiary {
	
	private static FoodDiaryRepository repository;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_diary_seq")
	@SequenceGenerator(name = "food_diary_seq", sequenceName = "food_diary_seq", allocationSize = 1)
	private Long id;
	
	@OneToOne(targetEntity = LifeDayImpl.class, mappedBy = "foodDiary", orphanRemoval = true, fetch = FetchType.LAZY, optional = false)
	private LifeDay day;
	
	@OrderBy("relativeTime ASC")
	@OneToMany(targetEntity = FoodIntakeImpl.class, orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy = "diary")
	private SortedSet<FoodIntake> intakes = new TreeSet<>();
	
	public FoodDiaryImpl() {}
	
	public FoodDiaryImpl(Long id, LifeDay day) {
		
		if(day == null) throw new IllegalArgumentException("Day can't be null!");
		
		this.id = id;
		this.day = day;
	}
	
	public boolean hasIntake(String name) {
		
		for(FoodIntake intake : intakes) {
			
			if(intake.getName().equals(name)) {
				
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasIntake(int relativeTime) {
		
		for(FoodIntake intake : intakes) {
			
			if(intake.getRelativeTime().intValue() == relativeTime) {
				
				return true;
			}
		}
		
		return false;
	}
	
	public EnergyAmount getConsumedEnergy() {
		
		return calcConsumedEnergy();
	}
	
	private EnergyAmount calcConsumedEnergy() {
		
		long amount = 0;
		
		for(FoodIntake intake : intakes) {
			
			amount += intake.getEnergyChange().getRaw();
		}
		
		return new EnergyAmount(amount);
	}
	
	public NutrientDose getConsumedNutrient(Nutrient nutrient) {
		
		NutrientDose dose = null;
		
		for(FoodIntake intake : getIntakes()) {
			
			NutrientDose intakeDose = intake.getConsumedNutrient(nutrient);
		
			if(intakeDose == null) continue;
			
			if(dose != null) {
				
				dose = dose.add(intakeDose);
			}
			else {
				
				dose = intakeDose.clone();
			}
		}
		
		return dose;
	}

	public Set<NutrientDose> getConsumedNutrients() {

		Map<Nutrient, NutrientDose> nutrients = new HashMap<>();
		
		for(FoodIntake intake : getIntakes()) {
			
			for(NutrientDose nutrientDose : intake.getConsumedNutrients()){
				
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
	
	@Override
	public SortedSet<FoodIntake> getIntakes() {
		
		return this.intakes;
	}
	
	@Override
	public LifeDay getDay() {
		
		return this.day;
	}
	
	public Long getId() {
		
		return this.id;
	}
	
	public FoodIntake createIntake(String name, int relativeTime) {
	
		if(hasIntake(name)) throw new IntakeAlreadyExistsException("Intake with name: "+name+" already exists!");
		if(hasIntake(relativeTime)) throw new IntakeAlreadyExistsException("Intake with relative time: "+relativeTime+" already exists!");
		
		FoodIntakeImpl intake = new FoodIntakeImpl(new FoodIntakeId((short) relativeTime, this.id), this, name, relativeTime);
	
		this.intakes.add(intake);
		
		return intake;
	}
	
	public FoodDiaryImpl save() {
		
		return repository.save(this);
	}
	
	@Service
	private static class RepositoryInjector {
		
		@Autowired
		private FoodDiaryRepository rep;
	
		@PostConstruct
		private void inject() {
			
			repository = rep;
		}
	}
}
