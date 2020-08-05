package com.kero.health.core.web.human;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kero.health.core.domain.Human;
import com.kero.health.core.domain.account.Account;
import com.kero.health.core.domain.account.auth.AccessToken;
import com.kero.health.core.domain.types.Sex;

@Controller
@RequestMapping("/human")
public class HumanEndpoint {

	@Autowired
	private ObjectMapper mapper;
	
	@PostMapping("/create")
	@ResponseBody
	@Transactional
	private ResponseEntity<ObjectNode> verify(@RequestBody ObjectNode data) {
	
		if(!data.has("sex")) return ResponseEntity.badRequest().build();
		if(!data.has("mass")) return ResponseEntity.badRequest().build();
		if(!data.has("height")) return ResponseEntity.badRequest().build();
		if(!data.has("birthDate")) return ResponseEntity.badRequest().build();
		if(!data.has("contacts")) return ResponseEntity.badRequest().build();
		if(!data.has("tokenType")) return ResponseEntity.badRequest().build();
		if(!data.has("token")) return ResponseEntity.badRequest().build();
		if(!data.has("contacts")) return ResponseEntity.badRequest().build();

		ObjectNode contacts = (ObjectNode) data.get("contacts");
		
		if(!contacts.has("email")) return ResponseEntity.badRequest().build();
				
		Sex sex = Sex.valueOf(data.get("sex").asText()); 
		Integer mass = data.get("mass").asInt();
		Integer height = data.get("height").asInt();
		LocalDate birthDate = Instant.ofEpochSecond(data.get("birthDate").asLong()).atZone(ZoneOffset.UTC).toLocalDate();

		String email = contacts.get("email").asText();
		
		if(Human.existsByEmail(email)) {
			
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
		Human human = Human.create(sex, mass, height, birthDate);

			human.getContacts().setEmail(email);
			
			AccessToken tokenObj = AccessToken.fromString(data.get("tokenType").asText(), data.get("token").asText());
		
			Account account = tokenObj.getAccount();
				account.setOwner(human);
				
			account.save();
			
		human.save();
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
