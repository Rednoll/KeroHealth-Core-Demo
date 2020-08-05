package com.kero.health.core.web.food.diary.intake;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kero.health.core.domain.Human;
import com.kero.health.core.domain.account.Account;
import com.kero.health.core.domain.account.auth.AccessToken;
import com.kero.health.core.domain.food.Food;
import com.kero.health.core.domain.food.FoodDose;
import com.kero.health.core.domain.food.diary.FoodDiary;
import com.kero.health.core.domain.food.diary.intake.FoodIntake;
import com.kero.health.core.domain.life.Life;
import com.kero.health.core.domain.life.units.LifeDay;

@Controller
@RequestMapping("/food/intake")
public class FoodIntakeEndpoint {

	@PostMapping(value = "/create")
	@Transactional
	public ResponseEntity create(@Valid @RequestBody RequestDto data) {
		
		AccessToken token = AccessToken.fromString(data.tokenType, data.token);
		
		Account account = token.getAccount();
		Human human = account.getOwner();
		
		if(human == null) return ResponseEntity.notFound().build();
		
		Life life = human.getLife();
		
		LocalDate date = Instant.ofEpochSecond(data.date).atOffset(ZoneOffset.UTC).toLocalDate();
		
		LifeDay day = life.findDay(date);
		
		if(day == null) {
			
			day = life.createDay(date);
		}
		
		FoodDiary diary = day.getFoodDiary();
		
		if(diary.hasIntake(data.intake.name) || diary.hasIntake(data.intake.relativeTime)) return ResponseEntity.status(HttpStatus.CONFLICT).build();
		
		FoodIntake intake = diary.createIntake(data.intake.name, data.intake.relativeTime);
		
		for(IntakeUnitDto unitDto : data.intake.units) {
			
			Food food = Food.findById(unitDto.food);
			
			if(food == null) throw new RuntimeException("Food with id: "+unitDto.food+" not found!");
			
			FoodDose dose = new FoodDose(food, unitDto.amount);
			
			intake.createUnit(food, dose);
		}
		
		intake.save();
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	private static class RequestDto {
		
		@NotNull(message = "TokenType must have!")
		protected String tokenType;
		
		@NotNull(message = "Token must have!")
		protected String token;
		
		@NotNull(message = "Date must have!")
		protected Long date;
		
		@NotNull(message = "Intake must have!")
		@Valid
		protected IntakeDto intake;

		public String getTokenType() {
			return tokenType;
		}

		public void setTokenType(String tokenType) {
			this.tokenType = tokenType;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public Long getDate() {
			return date;
		}

		public void setDate(Long date) {
			this.date = date;
		}

		public IntakeDto getIntake() {
			return intake;
		}

		public void setIntake(IntakeDto intake) {
			this.intake = intake;
		}
	}
	
	private static class IntakeDto {
		
		@NotBlank(message = "Intake name must have!")
		protected String name;
		
		@NotNull(message = "Relative time must have!")
		protected Short relativeTime;
		
		@NotEmpty(message = "Units cannot be empty!")
		protected Set<@Valid IntakeUnitDto> units;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Short getRelativeTime() {
			return relativeTime;
		}

		public void setRelativeTime(Short relativeTime) {
			this.relativeTime = relativeTime;
		}

		public Set<IntakeUnitDto> getUnits() {
			return units;
		}

		public void setUnits(Set<IntakeUnitDto> units) {
			this.units = units;
		}
	}
	
	private static class IntakeUnitDto {
		
		@NotNull(message = "Intake unit must have food id!")
		protected Long food;
		
		@NotNull(message = "Intake unit must have food amount!")
		protected Integer amount;

		public Long getFood() {
			return food;
		}

		public void setFood(Long food) {
			this.food = food;
		}

		public Integer getAmount() {
			return amount;
		}

		public void setAmount(Integer amount) {
			this.amount = amount;
		}
	}
}
