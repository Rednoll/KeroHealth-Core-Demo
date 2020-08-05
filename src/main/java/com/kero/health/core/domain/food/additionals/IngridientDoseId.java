package com.kero.health.core.domain.food.additionals;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

public class IngridientDoseId implements Serializable {

	@Column(name = "dish_id", updatable = false, insertable = false)
	private Long dishId;
	
	@Column(name = "ingridient_id", updatable = false, insertable = false)
	private Long ingridientId;

	public IngridientDoseId() {}
	
	public IngridientDoseId(Long dishId, Long ingridientId) {
		
		this.dishId = dishId;
		this.ingridientId = ingridientId;
	}
	
	@Override
	public int hashCode() {
	
		return Objects.hash(dishId, ingridientId);
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IngridientDoseId other = (IngridientDoseId) obj;
		
		return Objects.equals(dishId, other.dishId) && Objects.equals(ingridientId, other.ingridientId);
	}

	public void setIngridientId(Long id) {
		
		this.ingridientId = id;
	}
	
	public Long getIngridientId() {
		
		return this.ingridientId;
	}
	
	public void setDishId(Long i) {
		
		this.dishId = i;
	}
	
	public Long getDishId() {
		
		return this.dishId;
	}
}
