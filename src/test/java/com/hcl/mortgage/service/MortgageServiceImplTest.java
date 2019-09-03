package com.hcl.mortgage.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.hcl.mortgage.dto.MortgageRequestDto;
import com.hcl.mortgage.repository.AccountRepository;
import com.hcl.mortgage.repository.CustomerRepository;
import com.hcl.mortgage.repository.MortgageRepository;
import com.hcl.mortgage.util.EmailSender;
import com.hcl.mortgage.util.PasswordUtil;

@RunWith(MockitoJUnitRunner.class)
public class MortgageServiceImplTest {

	@Mock
	MortgageRepository mortgageRepository;

	@Mock
	CustomerRepository customerRepository;

	@Mock
	AccountRepository accountRepository;

	@Mock
	PasswordUtil passwordUtil;

	@Mock
	EmailSender emailSender;

	@InjectMocks
	MortgageServiceImpl mortgageServiceIMpl;
	
	MortgageRequestDto mortgageRequestDto;

	@Before
	public void setUp() {

	}

	@Test()
	public void createMortgageTest() {

	}

	public MortgageRequestDto getMortgageRequestDto() {
		return new MortgageRequestDto("buy", 110000.00, 100.00, "Software", "priya", "1988-08-03", "8908908908",
				"haripriya517@gmail.com");
	}

}
