package com.kero.health.core.domain.account.auth.dd;

import static org.junit.Assert.assertTrue;

import java.time.Instant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.kero.health.core.domain.account.Account;
import com.kero.health.core.domain.account.auth.exceptions.AccessTokenAccountNotFoundException;
import com.kero.health.core.domain.account.auth.exceptions.AccessTokenExpiredException;
import com.kero.health.core.domain.account.auth.exceptions.AccessTokenSignatureInvalidException;
import com.kero.health.core.domain.account.auth.exceptions.AccessTokenStructureInvalidException;
import com.kero.health.core.domain.account.auth.impl.JwtToken;
import com.kero.health.core.domain.account.impl.KeroHealthAccount;
import com.kero.health.core.domain.account.impl.KeroHealthAccountImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class JwtTokenDdTest {
    
	@Test
	public void fromString() {
		
		Account account = KeroHealthAccount.create(null, "tokenFromStringTest", null);
		
		String rawToken = new JwtToken(account, Instant.MAX).toStringForm();
		
		JwtToken token = JwtToken.fromString(rawToken);
		
		Account tokenAccount = token.getAccount();
	
		assertTrue(account.getId().equals(tokenAccount.getId()));
	}
	
	@Test(expected = AccessTokenStructureInvalidException.class)
	public void fromString_StructureException111() {
		
		JwtToken.fromString("1.1.1");
	}
	
	@Test(expected = AccessTokenStructureInvalidException.class)
	public void fromString_StructureExceptionFragmentLoss() {
		
		JwtToken.fromString("1.1");
	}
	
	@Test(expected = AccessTokenStructureInvalidException.class)
	public void fromString_StructureExceptionFragmentOverflow() {
		
		JwtToken.fromString("1.1.1.1");
	}
	
	@Test(expected = AccessTokenSignatureInvalidException.class)
	public void fromString_SignatureExceptionSignatureBroken() {
		
		JwtToken.fromString("eyJhbGciOiJTSEEyNTYiLCJ0eXAiOiJKV1QifQ==.eyJleHAiOjE1OTYzNTA4ODUsImFjY291bnRJZCI6N30=.1");
	}
	
	@Test(expected = AccessTokenExpiredException.class)
	public void fromString_ExpiredException() {
		
		Account account = KeroHealthAccount.create(null, "tokenFromStringExpiredException", null);
		
		String rawToken = new JwtToken(account, Instant.MIN).toStringForm();
		
		JwtToken.fromString(rawToken);
	}
	
	@Test(expected = AccessTokenAccountNotFoundException.class)
	public void fromString_AccountNotFoundException() {
		
		Account stubAccount = new KeroHealthAccountImpl(-1L, null, null, null);
		
		String rawToken = new JwtToken(stubAccount, Instant.MAX).toStringForm();
		
		JwtToken.fromString(rawToken);
	}
}