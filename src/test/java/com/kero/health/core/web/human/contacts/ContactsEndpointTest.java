package com.kero.health.core.web.human.contacts;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kero.health.core.domain.Human;
import com.kero.health.core.domain.additionals.HumanContacts;
import com.kero.health.core.domain.types.Sex;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
public class ContactsEndpointTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;
	
	@Test
	public void availability_email() throws Exception {
		
		ObjectNode content = mapper.createObjectNode();
			content.put("email", "availability_email@test.com");
	
		mockMvc.perform(post("/human/contacts/availability")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content.toString())
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.emailFree", is(true)))
			.andReturn();
	}
	
	@Test
	public void availability_emailBusy() throws Exception {
		
		Human human = Human.create(Sex.MALE, 1500000, 150, LocalDate.now());
		
		HumanContacts contacts = human.getContacts();
			contacts.setEmail("availability_emailBusy@test.com");
		
		human.save();
			
		ObjectNode content = mapper.createObjectNode();
			content.put("email", "availability_emailBusy@test.com");
	
		mockMvc.perform(post("/human/contacts/availability")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content.toString())
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.emailFree", is(false)))
			.andReturn();
	}
}