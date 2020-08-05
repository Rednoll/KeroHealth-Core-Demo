package com.kero.health.core.web.human.contacts;

import java.util.concurrent.ForkJoinPool;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kero.health.core.domain.Human;
import com.kero.health.core.domain.account.auth.AccessToken;
import com.kero.health.core.domain.additionals.HumanContacts;

@Controller
@RequestMapping("/human/contacts")
public class ContactsEndpoint {

	@Value("${email.domain}")
	private String emailDomain;
	
	@Autowired
	private JavaMailSender emailAgent;
	
	@Autowired
	private ObjectMapper mapper;
	
	@PostMapping("/availability")
	private ResponseEntity<ObjectNode> availability(@RequestBody ObjectNode data) {
	
		ObjectNode response = mapper.createObjectNode();
		
		if(data.has("email")) {
			
			String email = data.get("email").asText();
		
			response.put("emailFree", !Human.existsByEmail(email));
		}
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/email/change")
	private ResponseEntity changeEmail(@RequestBody ObjectNode data) {
		
		if(!data.has("token")) return ResponseEntity.badRequest().build();
		if(!data.has("tokenType")) return ResponseEntity.badRequest().build();
		if(!data.has("email")) return ResponseEntity.badRequest().build();
		
		AccessToken token = AccessToken.fromString(data.get("tokenType").asText(), data.get("token").asText());
		
		if(token != null) {
			
			Human owner = token.getAccount().getOwner();
		
			if(owner == null) return ResponseEntity.notFound().build();
			
			HumanContacts contacts = owner.getContacts();
			
			if(contacts.isEmailVerified()) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			
			contacts.setEmail(data.get("email").asText());
			
			owner.save();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/email/sendCode")
	@Transactional
	private ResponseEntity sendEmailVerifyCode(@RequestBody ObjectNode data) {
		
		if(!data.has("token")) return ResponseEntity.badRequest().build();
		if(!data.has("tokenType")) return ResponseEntity.badRequest().build();
	
		AccessToken token = AccessToken.fromString(data.get("tokenType").asText(), data.get("token").asText());
	
		if(token != null) {
	
			Human owner = token.getAccount().getOwner();
			HumanContacts contacts = owner.getContacts();
			
			int code = contacts.generateEmailVerifyCode();
			contacts.setEmailVerified(false);
			
			SimpleMailMessage message = new SimpleMailMessage();
				message.setFrom("noreply@"+emailDomain);
				message.setTo(contacts.getEmail());
				message.setSubject("Verify email");
				message.setText("Your verify code: "+code);
		
			ForkJoinPool.commonPool().execute(()-> {
				
				emailAgent.send(message);
			});
			
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/email/verifyCode")
	private ResponseEntity verifyEmailCode(@RequestBody ObjectNode data) {
		
		if(!data.has("token")) return ResponseEntity.badRequest().build();
		if(!data.has("tokenType")) return ResponseEntity.badRequest().build();
		if(!data.has("code")) return ResponseEntity.badRequest().build();
		
		AccessToken token = AccessToken.fromString(data.get("tokenType").asText(), data.get("token").asText());
		
		if(token != null) {
			
			Human human = token.getAccount().getOwner();
			HumanContacts contacts = human.getContacts();
		
			boolean verifyResult = contacts.verifyEmail(data.get("code").asInt());
		
			if(verifyResult) {
				
				human.save();
				
				return ResponseEntity.accepted().build();
			}
			else {
				
				return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
			}
		}
		
		return ResponseEntity.notFound().build();
	}
}
