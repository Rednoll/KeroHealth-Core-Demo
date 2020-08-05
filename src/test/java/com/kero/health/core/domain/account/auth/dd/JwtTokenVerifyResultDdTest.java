package com.kero.health.core.domain.account.auth.dd;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Instant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.kero.health.core.domain.account.Account;
import com.kero.health.core.domain.account.auth.VerifyResult;
import com.kero.health.core.domain.account.auth.impl.JwtToken;
import com.kero.health.core.domain.account.impl.KeroHealthAccountImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class JwtTokenVerifyResultDdTest {
 
	@Test
	public void main() {
	
		Account accountStub = new KeroHealthAccountImpl(1L, null, null, null);
		
		JwtToken token = new JwtToken(accountStub, Instant.MAX);
	
		VerifyResult result = JwtToken.verify(token.toStringForm());
		
		assertFalse(result.isExpired());
		assertTrue(result.isSignatureValid());
	}
	
	@Test
	public void expiredCheck_CorrectToken() {
	
		Account accountStub = new KeroHealthAccountImpl(1L, null, null, null);
		
		JwtToken token = new JwtToken(accountStub, Instant.MAX);
	
		VerifyResult result = JwtToken.verify(token.toStringForm());
		
		assertFalse(result.isExpired());
	}
	
	@Test
	public void expiredCheck_ExpiredToken() {
	
		Account accountStub = new KeroHealthAccountImpl(1L, null, null, null);
		
		JwtToken token = new JwtToken(accountStub, Instant.MIN);
	
		VerifyResult result = JwtToken.verify(token.toStringForm());
		
		assertTrue(result.isExpired());
	}
	
	@Test
	public void signatureCheck_CorrectToken() {
	
		Account accountStub = new KeroHealthAccountImpl(1L, null, null, null);
		
		JwtToken token = new JwtToken(accountStub, Instant.MAX);
	
		VerifyResult result = JwtToken.verify(token.toStringForm());
		
		assertTrue(result.isSignatureValid());
	}
	
	@Test
	public void signatureCheck_InvalidToken() {
	
		Account accountStub = new KeroHealthAccountImpl(1L, null, null, null);
		
		JwtToken token = new JwtToken(accountStub, Instant.MAX);
	
		String textToken = token.toStringForm();
		textToken = textToken.split("\\.")[0] + "." + textToken.split("\\.")[1] + ".1";
		
		VerifyResult result = JwtToken.verify(textToken);
		
		assertFalse(result.isSignatureValid());
	}
}
