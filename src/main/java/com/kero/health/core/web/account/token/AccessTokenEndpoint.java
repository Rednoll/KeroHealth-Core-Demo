package com.kero.health.core.web.account.token;

import java.time.Instant;
import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kero.health.core.domain.account.Account;
import com.kero.health.core.domain.account.auth.AccessToken;
import com.kero.health.core.domain.account.auth.VerifyResult;
import com.kero.health.core.domain.account.auth.exceptions.AccessTokenStructureInvalidException;
import com.kero.health.core.domain.account.auth.exceptions.AccessTokenTypeNotSupported;
import com.kero.health.core.domain.account.impl.KeroHealthAccount;

@Controller
@RequestMapping("/token")
public class AccessTokenEndpoint {
	
	@PostMapping("/verify")
	@ResponseBody
	private ResponseEntity<VerifyResult> verify(@RequestBody ObjectNode data) {
		
		if(!data.has("tokenType")) return ResponseEntity.badRequest().build();
		if(!data.has("token")) return ResponseEntity.badRequest().build();
	
		String type = data.get("tokenType").asText();
		String token = data.get("token").asText();
	
		try {
			
			return ResponseEntity.ok(AccessToken.verify(type, token));
		}
		catch(AccessTokenStructureInvalidException | AccessTokenTypeNotSupported e) {
		
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping(value = "/generate")
	@ResponseBody
	private ResponseEntity<String> generate(@RequestBody ObjectNode data) {

		if(!data.has("tokenType")) return ResponseEntity.badRequest().build();
		if(!data.has("accountType")) return ResponseEntity.badRequest().build();
		
		Account account;
		
		if(data.get("accountType").asText().equals("KERO_HEALTH")) {
			
			if(!data.has("pass")) return ResponseEntity.badRequest().build();
			if(!data.has("login") && !data.has("email")) return ResponseEntity.badRequest().build();
			
			
			String login = data.has("login") ? data.get("login").asText() : "";
			String email = data.has("email") ? data.get("email").asText() : "";
			
			KeroHealthAccount keroAccount = KeroHealthAccount.findByLoginOrEmail(login, email);
		
			if(keroAccount == null) return ResponseEntity.notFound().build();
			
			byte[] calculatedPassHash = DigestUtils.sha256Hex(data.get("pass").asText().getBytes()).getBytes();
		
			if(Arrays.equals(keroAccount.getPassHash(), calculatedPassHash)) {
				
				account = keroAccount;
			}
			else {
				
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // NotFound?
			}
		}
		else {
			
			return ResponseEntity.badRequest().build();
		}
		
		Instant expirationTime = Instant.now().plus(AccessToken.defaultValidTime);
		
		AccessToken token = account.createAccessToken(data.get("tokenType").asText(), expirationTime);
	
		String stringForm = token.toStringForm();
		
		return ResponseEntity.ok().body(stringForm);
	}
}
