package com.kero.health.core.domain.account.auth;

import java.time.Instant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.kero.health.core.domain.account.Account;
import com.kero.health.core.domain.account.auth.impl.JwtToken;
import com.kero.health.core.domain.account.impl.KeroHealthAccountImpl;

@RunWith(BlockJUnit4ClassRunner.class)
public class JwtTokenTest {

	@Test
	public void create() {
		
		Account stubAccount = new KeroHealthAccountImpl(1L, null, null, null);
		
		new JwtToken(stubAccount, Instant.MAX);
	}
}
