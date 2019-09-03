package com.hcl.mortgage.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.hcl.mortgage.dto.MortgageRequestDto;
import com.hcl.mortgage.dto.MortgageResponseDto;
import com.hcl.mortgage.entity.Account;
import com.hcl.mortgage.entity.Customer;
import com.hcl.mortgage.entity.Mortgage;
import com.hcl.mortgage.exception.CommonException;
import com.hcl.mortgage.repository.AccountRepository;
import com.hcl.mortgage.repository.CustomerRepository;
import com.hcl.mortgage.repository.MortgageRepository;
import com.hcl.mortgage.util.EmailSender;
import com.hcl.mortgage.util.MortgageConstants;
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

	Customer customer;

	Account transactionAccount;

	Account mortgageAccount;
	
	Mortgage mortgage;

	@Before
	public void setUp() {
		mortgageRequestDto = getMortgageRequestDto();
		customer = getCustomer();
		transactionAccount = getTransactionAccount();
		mortgageAccount = getMortgageAccount();
		mortgage=getMortgage();
		

	}

	
	@Test()
	public void createMortgageTest() {

		Mockito.when(customerRepository.save(Mockito.any())).thenReturn(customer);
		Mockito.when(accountRepository.save(Mockito.any())).thenReturn(transactionAccount);
		Mockito.when(accountRepository.save(Mockito.any())).thenReturn(mortgageAccount);
		Mockito.when(mortgageRepository.save(Mockito.any())).thenReturn(mortgage);
		MortgageResponseDto actual=mortgageServiceIMpl.createMortgage(mortgageRequestDto);
		Assert.assertEquals("Account created successfully", actual.getMessage());

	}
	

	@Test(expected = CommonException.class)
	public void createMortgageTest_1() {

		mortgageRequestDto.setCustomerName("hari priya");
		mortgageServiceIMpl.createMortgage(mortgageRequestDto);

	}
	
	@Test(expected = CommonException.class)
	public void createMortgageTest_2() {

		mortgageRequestDto.setPhoneNumber("12345");
		mortgageServiceIMpl.createMortgage(mortgageRequestDto);

	}
	
	@Test(expected = CommonException.class)
	public void createMortgageTest_3() {

		mortgageRequestDto.setEmail("12345@");
		mortgageServiceIMpl.createMortgage(mortgageRequestDto);

	}
	
	@Test(expected = CommonException.class)
	public void createMortgageTest_4() {

		mortgageRequestDto.setDateOfBirth("2017-08-03");
		mortgageServiceIMpl.createMortgage(mortgageRequestDto);

	}
	
	@Test(expected = CommonException.class)
	public void createMortgageTest_5() {

		mortgageRequestDto.setPropertyCost(100.00);
		mortgageServiceIMpl.createMortgage(mortgageRequestDto);

	}
	
	@Test(expected = CommonException.class)
	public void createMortgageTest_6() {

		mortgageRequestDto.setDeposit(0.0);
		mortgageServiceIMpl.createMortgage(mortgageRequestDto);

	}


	public MortgageRequestDto getMortgageRequestDto() {
		return new MortgageRequestDto("buy", 110000.00, 100.00, "Software", "priya", "1988-08-03", "8908908908",
				"haripriya517@gmail.com");
	}

	public Customer getCustomer() {
		return new Customer(1, "12A", "priya", passwordUtil.encodePassword("1234"));
	}

	public Account getTransactionAccount() {
		return new Account(1, "Acc123", "Transactional Account", 110000.00, LocalDate.now(), 1);
	}

	public Account getMortgageAccount() {
		return new Account(1, "Mor123", "Mortgage Account", 110000.00, LocalDate.now(), 1);
	}

	public Mortgage getMortgage() {
		return new Mortgage(1, "buy", 110000.00, 100.00, "Software", "priya", getLocalDate("1988-08-03"), "8908908908",
				"haripriya517@gmail.com", 1);
	}

	public LocalDate getLocalDate(String data) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(MortgageConstants.DATE_FORMAT);
		return LocalDate.parse(data, dateTimeFormatter);

	}

}
