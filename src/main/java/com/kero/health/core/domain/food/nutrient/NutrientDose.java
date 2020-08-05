package com.kero.health.core.domain.food.nutrient;

import java.util.Objects;

import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.MapsId;

import com.kero.health.core.domain.MafsUtils;

@Embeddable
public class NutrientDose implements Cloneable {

	@MapsId("nutrientId")
	@Convert(converter = NutrientToIdConverter.class)
	private Nutrient nutrient;

	private long amount;
	
	public NutrientDose() {}
	
	public NutrientDose(Nutrient nutrient, long amount) {
		
		if(nutrient == null) throw new IllegalArgumentException("Nutrient can't be null!");
		if(amount <= 0) throw new IllegalArgumentException("Amount must be > 0!");
		
		this.nutrient = nutrient;
		this.amount = amount;
	}
	
	@Override
	public int hashCode() {
		
		return Objects.hash(amount, nutrient);
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NutrientDose other = (NutrientDose) obj;
		return amount == other.amount && Objects.equals(nutrient, other.nutrient);
	}

	public NutrientDose add(NutrientDose another) {
		
		if(!this.nutrient.equals(another.getNutrient())) throw new RuntimeException("Can't add dose with nutrient: "+another.getNutrient()+" to "+this.getNutrient());
		
		return new NutrientDose(this.nutrient, this.amount + another.amount);
	}
	
	public NutrientDose multiply(double mult) {
		
		return new NutrientDose(this.nutrient, (long) (this.amount*mult));
	}
	
	public long getMicrogram() {
		
		return this.amount;
	}
	
	public long getMilligram() {
		
		return this.amount / MafsUtils.ACCURACY_MULTIPLIER;
	}
	
	public long getGram() {
		
		return this.amount / (long) Math.pow(MafsUtils.ACCURACY_MULTIPLIER, 2);
	}
	
	public long getKilogram() {
		
		return this.amount / (long) Math.pow(MafsUtils.ACCURACY_MULTIPLIER, 3);
	}
	
	public long getTon() {
		
		return this.amount / (long) Math.pow(MafsUtils.ACCURACY_MULTIPLIER, 4);
	}
	
	public long getRaw() {
		
		return this.amount;
	}

	public Nutrient getNutrient() {
		
		return this.nutrient;
	}
	
	@Override
	public NutrientDose clone() {
		
		return new NutrientDose(this.nutrient, this.amount);
	}
}
