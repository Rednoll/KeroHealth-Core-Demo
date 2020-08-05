package com.kero.health.core.web.account;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
public class AccountEndpointTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;
	
	@Test
	public void create() throws Exception {
		
		String login = "create";
		
		ObjectNode content = mapper.createObjectNode();
			content.put("tokenType", "JWT");
			content.put("accountType", "KERO_HEALTH");
			content.put("login", login);
			content.put("pass", "test_pass");
	
		MvcResult result = mockMvc.perform(post("/account/create")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content.toString())
			.accept(MediaType.TEXT_PLAIN))
			.andExpect(status().isOk())
			.andReturn();
		
		String receivedToken = result.getResponse().getContentAsString();
		
		AccessToken token = AccessToken.fromString("JWT", receivedToken);
		
		KeroHealthAccount account = (KeroHealthAccount) token.getAccount();
	
		assertEquals(account.getLogin(), login);
	}
	
	@Test
	public void create_LoginBusy() throws Exception {
		
		KeroHealthAccount.create(null, "create_LoginBusy", DigestUtils.sha256Hex("test_pass".getBytes()).getBytes());
		
		ObjectNode content = mapper.createObjectNode();
			content.put("tokenType", "JWT");
			content.put("accountType", "KERO_HEALTH");
			content.put("login", "create_LoginBusy");
			content.put("pass", "test_pass");
	
		mockMvc.perform(post("/account/create")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content.toString())
			.accept(MediaType.TEXT_PLAIN))
			.andExpect(status().isConflict())
			.andReturn();
	}
	
	@Test
	public void create_TokenTypeNotSpecified() throws Exception {
		
		ObjectNode content = mapper.createObjectNode();
			content.put("accountType", "KERO_HEALTH");
			content.put("login", "create_TokenTypeNotSpecified");
			content.put("pass", "test_pass");
	
		mockMvc.perform(post("/account/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content.toString())
				.accept(MediaType.TEXT_PLAIN))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	@Test
	public void create_AccountTypeNotSpecified() throws Exception {
		
		ObjectNode content = mapper.createObjectNode();
			content.put("tokenType", "JWT");
			content.put("login", "create_AccountTypeNotSpecified");
			content.put("pass", "test_pass");
	
		mockMvc.perform(post("/account/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content.toString())
				.accept(MediaType.TEXT_PLAIN))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	@Test
	public void create_LoginNotSpecified() throws Exception {
		
		ObjectNode content = mapper.createObjectNode();
			content.put("tokenType", "JWT");
			content.put("accountType", "KERO_HEALTH");
			content.put("pass", "test_pass");
	
		mockMvc.perform(post("/account/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content.toString())
				.accept(MediaType.TEXT_PLAIN))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	@Test
	public void create_LoginLessLength() throws Exception {
		
		ObjectNode content = mapper.createObjectNode();
			content.put("tokenType", "JWT");
			content.put("accountType", "KERO_HEALTH");
			content.put("pass", "test_pass");
			content.put("login", "123");
			
		mockMvc.perform(post("/account/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content.toString())
				.accept(MediaType.TEXT_PLAIN))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	@Test
	public void create_PassNotSpecified() throws Exception {
		
		ObjectNode content = mapper.createObjectNode();
			content.put("tokenType", "JWT");
			content.put("accountType", "KERO_HEALTH");
			content.put("login", "create_PassNotSpecified");
	
		mockMvc.perform(post("/account/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content.toString())
				.accept(MediaType.TEXT_PLAIN))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	@Test
	public void create_PassLessLength() throws Exception {
		
		ObjectNode content = mapper.createObjectNode();
			content.put("tokenType", "JWT");
			content.put("accountType", "KERO_HEALTH");
			content.put("pass", "123");
			content.put("login", "create_PassLessLength");
			
		mockMvc.perform(post("/account/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content.toString())
				.accept(MediaType.TEXT_PLAIN))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	@Test
	public void verify_Login() throws Exception {
		
		ObjectNode content = mapper.createObjectNode();
			content.put("accountType", "KERO_HEALTH");	
			content.put("login", "verify_Login");
			
		mockMvc.perform(post("/account/verify_credentials")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content.toString())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.loginFree", is(true)))
				.andReturn();
	}
	
	@Test
	public void verify_AccountTypeNotSpecified() throws Exception {
		
		ObjectNode content = mapper.createObjectNode();
			content.put("login", "verify_AccountTypeNotSpecified");
			
		mockMvc.perform(post("/account/verify_credentials")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content.toString())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	@Test
	public void verify_LoginBusy() throws Exception {
		
		KeroHealthAccount.create(null, "verify_LoginBusy", DigestUtils.sha256Hex("test_pass".getBytes()).getBytes());
		
		ObjectNode content = mapper.createObjectNode();
			content.put("accountType", "KERO_HEALTH");	
			content.put("login", "verify_LoginBusy");
			
		mockMvc.perform(post("/account/verify_credentials")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content.toString())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.loginFree", is(false)))
				.andReturn();
	}
}
