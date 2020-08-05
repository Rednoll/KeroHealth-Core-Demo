package com.kero.health.core.web.account;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kero.health.core.domain.Human;
import com.kero.health.core.domain.account.auth.AccessToken;
import com.kero.health.core.domain.account.impl.KeroHealthAccount;

@Controller
@RequestMapping("/account")
public class AccountEndpoint {
	
	@PostMapping("/create")
	@Transactional
	private ResponseEntity<String> createAccount(@RequestBody ObjectNode data) {
		
		if(!data.has("accountType")) return ResponseEntity.badRequest().build();
		if(!data.has("tokenType")) return ResponseEntity.badRequest().build();
		
		String accountType = data.get("accountType").asText();
		
		if(accountType.equals("KERO_HEALTH")) {
			
			if(!data.has("login")) return ResponseEntity.badRequest().build();
			if(!data.has("pass")) return ResponseEntity.badRequest().build();
			
			String login = data.get("login").asText();
			if(login.length() < 4) return ResponseEntity.badRequest().build();
			
			String pass = data.get("pass").asText();
			if(pass.length() < 4) return ResponseEntity.badRequest().build();
			
			byte[] passHash = DigestUtils.sha256Hex(pass.getBytes()).getBytes();
			
			if(KeroHealthAccount.existsByLogin(login)) {
				
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
			
			KeroHealthAccount account = KeroHealthAccount.create(null, login, passHash);
		
			Instant expirationTime = Instant.now().plus(AccessToken.defaultValidTime);
			
			AccessToken token = account.createAccessToken(data.get("tokenType").asText(), expirationTime);
		
			String stringForm = token.toStringForm();
			
			return ResponseEntity.ok().body(stringForm);
		}
		
		return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("/verify_credentials")
	@ResponseBody
	private ResponseEntity<Map<String, Boolean>> validateCredentials(@RequestBody ObjectNode data) throws JsonProcessingException {
		
		if(!data.has("accountType")) return ResponseEntity.badRequest().build();
		
		Map<String, Boolean> result = new HashMap<>();
		
		if(data.get("accountType").asText().equals("KERO_HEALTH")) {
			
			if(data.has("login")) {
				
				result.put("loginFree", !KeroHealthAccount.existsByLogin(data.get("login").asText()));
			}
		}
		
		return ResponseEntity.ok(result);
	}
}
