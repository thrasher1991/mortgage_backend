package com.hcl.mortgage.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.mortgage.dto.MortgageRequestDto;
import com.hcl.mortgage.service.MortgageService;

@RunWith(MockitoJUnitRunner.class)
public class MortgageControllerTest {

	@Mock
	MortgageService mortgageService;
	@InjectMocks
	MortgageController mortgageController;
	MockMvc mockMvc;

	MortgageRequestDto mortgageRequestDto;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(mortgageController).build();
		mortgageRequestDto = getMortgageRequestDto();
	}

	@Test
	public void createMortgageTest() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/api/createMortgage").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(mortgageRequestDto))).andExpect(MockMvcResultMatchers.status().isCreated());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public MortgageRequestDto getMortgageRequestDto() {
		return new MortgageRequestDto("buy", 110000.00, 100.00, "Software", "priya", "1988-08-03", "8908908908",
				"haripriya517@gmail.com");
	}
}
