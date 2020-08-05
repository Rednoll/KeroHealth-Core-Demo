package com.kero.health.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.kero.health.core.dao.life.LifeRepository;
import com.kero.health.core.dao.life.units.LifeYearRepository;

@EnableTransactionManagement
@SpringBootApplication
public class KeroHealthCoreApplication {

	public static void main(String[] args) {
		
		ApplicationContext context = SpringApplication.run(KeroHealthCoreApplication.class, args);
	
		TestService test = (TestService) context.getBean(TestService.class);
	
		test.test();
	}
	
	@Service
	public static class TestService {
	
		@Autowired
		private LifeRepository lifeRep;
		
		@Autowired
		private LifeYearRepository yearRep;
		
		@Transactional
		public void test() {
			
			/*
			SimplePhysicalActivity.create("Sambo", 3000);
			
			MetTable table = new MetTable();
				table.addPoint(1788, 6000);
				table.addPoint(2235, 8300);
				table.addPoint(2325, 9000);
				table.addPoint(2682, 9800);
				table.addPoint(2995, 10500);
				table.addPoint(3129, 11000);
				table.addPoint(3352, 11500);
				table.addPoint(3576, 11800);
				table.addPoint(3844, 12300);
				table.addPoint(4023, 12800);
				table.addPoint(4470, 14500);
				table.addPoint(4917, 16000);
				table.addPoint(5364, 19000);
				table.addPoint(5811, 19800);
				table.addPoint(6258, 23000);
				
			ComplexPhysicalActivity.create("Running", table);
			*/
			
			/*
			Human human = Human.findById(4L);
			LifeDay day = human.getLife().findDay(LocalDate.of(2020, 12, 19));
			SportsDiary sportsDiary = day.getSportsDiary();			

			for(SportsTraining training : sportsDiary.getTrainings()) {
			
				for(TrainingUnit unit : training.getUnits()) {
					
					System.out.println("unit: "+unit.getExercise().getName()+" / energy: "+unit.getSpentEnergy().getKCal());
				}
			}
			*/
			
			/*
			Food cheese = Food.create();
				cheese.addNutrient(NutrientsRegistry.PROTEIN, new NutrientDose(NutrientsRegistry.PROTEIN, (long) (25 * NutrientDoseUnit.GRAM.multiplier)), 100);
				cheese.addNutrient(NutrientsRegistry.FAT, new NutrientDose(NutrientsRegistry.FAT, (long) (32.14 * NutrientDoseUnit.GRAM.multiplier)), 100);
				cheese.addNutrient(NutrientsRegistry.CARBOHYDRATE, new NutrientDose(NutrientsRegistry.CARBOHYDRATE, (long) (3.57 * NutrientDoseUnit.GRAM.multiplier)), 100);
				
			Human human = Human.create(Sex.MALE, 105, 175, LocalDate.of(2001, 12, 19), "test@test.com");
			KeroHealthAccount.create(human, "test", DigestUtils.sha256Hex("test_pass".getBytes()).getBytes());
				
			Life life = human.getLife();
			LifeDay day = life.createDay(LocalDate.of(2020, 12, 19));
			
			FoodDiary foodDiary = day.getFoodDiary(); 
			FoodIntake intake = foodDiary.createIntake("�������", Short.valueOf((short) (10 * 60)));
			
			intake.createUnit(cheese, new FoodDose(cheese, 175));
			*/
			
			/*
			Set<String> aliases = new HashSet<>();
				aliases.add("���");
				aliases.add("�����");
				
			Food food = Food.create(aliases);
			*/
			
			/*
			Human human = Human.findById(4L);
			Life life = human.getLife();
			LifeDay day = life.createDay(LocalDate.of(2020, 9, 11));
			
			FoodDiary foodDiary = day.getFoodDiary();
			FoodIntake intake = foodDiary.createIntake("�������", Short.valueOf((short) (10 * 60)));
			
			Food cheese = Food.findByAliase("���");
			
			intake.createUnit(cheese, new FoodDose(cheese, 100));
			*/
			
			/*
			Human human = Human.create(Sex.MALE, 105D, 175D, 18);
			KeroHealthAccount.create(human, "test", DigestUtils.sha256Hex("test_pass".getBytes()).getBytes());
			*/
			
			/*
			Human human = Human.findById(4L);
			Account account = Account.findById(1L);
			
			AccessToken token = account.createAccessToken("JWT", Instant.now().plusSeconds(5 * 60 * 1000));
			*/
			
//			KeroHealthAccount.create(human, "test", DigestUtils.sha256Hex("test_pass".getBytes()).getBytes());
			
			/*
			Life life = human.getLife();
			
			/*
			life.createDay(LocalDate.of(2020, 1, 1));
			life.createDay(LocalDate.of(2019, 12, 31));
			
			life.createDay(LocalDate.of(2021, 1, 1));
			life.createDay(LocalDate.of(2020, 12, 31));
			
			life.createDay(LocalDate.of(2020, 7, 31));
			life.createDay(LocalDate.of(2020, 8, 1));
			//
			
			SortedSet<LifeYear> years = life.getYears();
			
			for(LifeYear year : years) {

				for(LifeMonth month : year.getMonths()) {
	
					for(LifeDay day : month.getDays()) {
						
						System.out.println("year: "+year.getNumber()+" month: "+month.getNumber()+" day: "+day.getDate());
					}
				}
			}
			*/
		}
	}
}
