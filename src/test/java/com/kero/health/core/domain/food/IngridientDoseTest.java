package com.kero.health.core.domain.food;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.kero.health.core.domain.food.additionals.IngridientDoseId;
import com.kero.health.core.domain.food.impl.SimpleFoodImpl;

@RunWith(BlockJUnit4ClassRunner.class)
public class IngridientDoseTest {

	@Test
	public void create() {
		
		new IngridientDose(new IngridientDoseId(1L, 2L), new SimpleFoodImpl(2L), 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void create_IngridientNull() {
		
		new IngridientDose(new IngridientDoseId(1L, 2L), null, 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void create_shareBase0() {
		
		new IngridientDose(new IngridientDoseId(1L, 2L), new SimpleFoodImpl(2L), 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void create_shareMinus30() {
		
		new IngridientDose(new IngridientDoseId(1L, 2L), new SimpleFoodImpl(2L), -30);
	}
	
	@Test
	public void shareCalculation() {
		
		IngridientDose dose = new IngridientDose(new IngridientDoseId(1L, 2L), new SimpleFoodImpl(2L), 2500);
	
		int share = dose.getShareFrom(100);
		
		assertTrue(share == 25);
	}
}
