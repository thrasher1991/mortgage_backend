package com.hcl.mortgage.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.hcl.mortgage.service.TransactionSummaryService;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class TransactionSummaryControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(TransactionSummaryControllerTest.class);

	@InjectMocks
	TransactionSummaryController transactionSummaryController;

	@Mock
	TransactionSummaryService transactionSummaryServiceImpl;

	MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(transactionSummaryController).build();
	}

	@Test
	public void testGetTransactionSummary() throws Exception {
		logger.info("in getTransactionSummary() ");
		mockMvc.perform(MockMvcRequestBuilders.get("/api/transactionSummary/{accountNumber}", "MRT73189768186149")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

}
