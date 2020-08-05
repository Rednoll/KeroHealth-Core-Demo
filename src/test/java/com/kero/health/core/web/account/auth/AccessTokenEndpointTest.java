package com.kero.health.core.web.account.auth;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.time.LocalDate;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kero.health.core.domain.Human;
import com.kero.health.core.domain.account.auth.AccessToken;
import com.kero.health.core.domain.account.impl.KeroHealthAccount;
import com.kero.health.core.domain.additionals.HumanContacts;
import com.kero.health.core.domain.types.Sex;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
public class AccessTokenEndpointTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;
	
	@Test
	public void generate_Login() throws Exception {
		
		KeroHealthAccount.create(null, "barakuda", DigestUtils.sha256Hex("test_pass".getBytes()).getBytes());
		
		ObjectNode content = mapper.createObjectNode();
			content.put("tokenType", "JWT");
			content.put("accountType", "KERO_HEALTH");
			content.put("login", "barakuda");
			content.put("pass", "test_pass");
		
		MvcResult result = mockMvc.perform(post("/token/generate")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content.toString())
			.accept(MediaType.TEXT_PLAIN))
			.andExpect(status().isOk())
			.andReturn();
		
		String receivedToken = result.getResponse().getContentAsString();
		
		AccessToken token = AccessToken.fromString("JWT", receivedToken);
		
		KeroHealthAccount account = (KeroHealthAccount) token.getAccount();
	
		assertEquals(account.getLogin(), "barakuda");
	}
	
	@Test
	public void generate_Email() throws Exception {
		
		Human human = Human.create(Sex.MALE, 1, 1, LocalDate.of(2019, 10, 3));
		
		HumanContacts contacts = human.getContacts();
			contacts.setEmail("barakuda@test.com");
			contacts.setEmailVerified(true);
			
		human.save();
		
		KeroHealthAccount.create(human, "barakuda", DigestUtils.sha256Hex("test_pass".getBytes()).getBytes());
		
		ObjectNode content = mapper.createObjectNode();
			content.put("tokenType", "JWT");
			content.put("accountType", "KERO_HEALTH");
			content.put("email", "barakuda@test.com");
			content.put("pass", "test_pass");
		
		MvcResult result = mockMvc.perform(post("/token/generate")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content.toString())
			.accept(MediaType.TEXT_PLAIN))
			.andExpect(status().isOk())
			.andReturn();
		
		String receivedToken = result.getResponse().getContentAsString();
		
		AccessToken token = AccessToken.fromString("JWT", receivedToken);
		
		KeroHealthAccount account = (KeroHealthAccount) token.getAccount();
	
		assertEquals(account.getLogin(), "barakuda");
	}
	
	@Test
	public void generate_UserNotFound() throws Exception {
		
		ObjectNode content = mapper.createObjectNode();
			content.put("tokenType", "JWT");
			content.put("accountType", "KERO_HEALTH");
			content.put("login", "not_found_user");
			content.put("pass", "not_found_user");

		mockMvc.perform(post("/token/generate")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content.toString())
			.accept(MediaType.TEXT_PLAIN))
			.andExpect(status().isNotFound())
			.andReturn();
	}
	
	@Test
	public void generate_TokenTypeNotSpecified() throws Exception {
		
		ObjectNode content = mapper.createObjectNode();
			content.put("accountType", "KERO_HEALTH");
			content.put("login", "not_found_user");
			content.put("pass", "not_found_user");
	
		mockMvc.perform(post("/token/generate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content.toString())
				.accept(MediaType.TEXT_PLAIN))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	@Test
	public void generate_AccountTypeNotSpecified() throws Exception {
		
		ObjectNode content = mapper.createObjectNode();
			content.put("tokenType", "JWT");
			content.put("login", "not_found_user");
			content.put("pass", "not_found_user");
	
		mockMvc.perform(post("/token/generate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content.toString())
				.accept(MediaType.TEXT_PLAIN))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	@Test
	public void generate_PassNotSpecified() throws Exception {
		
		ObjectNode content = mapper.createObjectNode();
			content.put("tokenType", "JWT");
			content.put("accountType", "KERO_HEALTH");
			content.put("login", "not_found_user");
	
		mockMvc.perform(post("/token/generate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content.toString())
				.accept(MediaType.TEXT_PLAIN))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	@Test
	public void generate_LoginAndEmailNotSpecified() throws Exception {
		
		ObjectNode content = mapper.createObjectNode();
			content.put("accountType", "KERO_HEALTH");
			content.put("pass", "not_found_user");

		mockMvc.perform(post("/token/generate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content.toString())
				.accept(MediaType.TEXT_PLAIN))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	@Test
	public void verify() throws Exception {

		KeroHealthAccount account = KeroHealthAccount.create(null, "barakuda", DigestUtils.sha256Hex("test_pass".getBytes()).getBytes());
		
		AccessToken token = AccessToken.create("JWT", account, Instant.MAX);
		
		ObjectNode content = mapper.createObjectNode();
			content.put("tokenType", "JWT");
			content.put("token", token.toStringForm());

		mockMvc.perform(post("/token/verify")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content.toString())
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.signatureValid", is(true)))
			.andExpect(jsonPath("$.expired", is(false)))
			.andReturn();
	}
	
	@Test
	public void verify_Expired() throws Exception {

		KeroHealthAccount account = KeroHealthAccount.create(null, "barakuda", DigestUtils.sha256Hex("test_pass".getBytes()).getBytes());
		
		AccessToken token = AccessToken.create("JWT", account, Instant.MIN);
		
		ObjectNode content = mapper.createObjectNode();
			content.put("tokenType", "JWT");
			content.put("token", token.toStringForm());

		mockMvc.perform(post("/token/verify")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content.toString())
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.signatureValid", is(true)))
			.andExpect(jsonPath("$.expired", is(true)))
			.andReturn();
	}
	
	@Test
	public void verify_SignatureInvalid() throws Exception {

		KeroHealthAccount account = KeroHealthAccount.create(null, "barakuda", DigestUtils.sha256Hex("test_pass".getBytes()).getBytes());
		
		AccessToken token = AccessToken.create("JWT", account, Instant.MAX);
		
		ObjectNode content = mapper.createObjectNode();
			content.put("tokenType", "JWT");
			content.put("token", token.toStringForm()+"111");

		mockMvc.perform(post("/token/verify")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content.toString())
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.signatureValid", is(false)))
			.andExpect(jsonPath("$.expired", is(false)))
			.andReturn();
	}
	
	@Test
	public void verify_TokenTypeNotSpecified() throws Exception {
		
		ObjectNode content = mapper.createObjectNode();
			content.put("token", "");

		mockMvc.perform(post("/token/verify")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content.toString())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	@Test
	public void verify_TokenNotSpecified() throws Exception {
		
		ObjectNode content = mapper.createObjectNode();
			content.put("tokenType", "");

		mockMvc.perform(post("/token/verify")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content.toString())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	@Test
	public void verify_TokenTypeNotSuported() throws Exception {
		
		ObjectNode content = mapper.createObjectNode();
			content.put("tokenType", "1");
			content.put("token", "");

		mockMvc.perform(post("/token/verify")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content.toString())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
}
