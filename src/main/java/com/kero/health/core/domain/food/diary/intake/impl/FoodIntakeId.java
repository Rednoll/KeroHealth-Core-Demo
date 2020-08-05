package com.kero.health.core.domain.food.diary.intake.impl;

import java.io.Serializable;
import java.util.Objects;

public class FoodIntakeId implements Serializable {

	private static final long serialVersionUID = 1L;

	private Short relativeTime;
	
	private Long diaryId;
	
	public FoodIntakeId() {}
	
	public FoodIntakeId(Short relativeTime, Long diaryId) {
		super();
		
		this.relativeTime = relativeTime;
		this.diaryId = diaryId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(diaryId, relativeTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FoodIntakeId other = (FoodIntakeId) obj;
		return Objects.equals(diaryId, other.diaryId) && relativeTime == other.relativeTime;
	}

	public short getRelativeTime() {
		return relativeTime;
	}

	public void setRelativeTime(short relativeTime) {
		this.relativeTime = relativeTime;
	}

	public Long getDiary() {
		return diaryId;
	}

	public void setDiary(Long diary) {
		this.diaryId = diary;
	}
}
