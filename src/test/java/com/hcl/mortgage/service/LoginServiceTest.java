package com.hcl.mortgage.service;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcl.mortgage.dto.LoginDto;
import com.hcl.mortgage.dto.LoginResponseDto;
import com.hcl.mortgage.entity.Customer;
import com.hcl.mortgage.exception.CommonException;
import com.hcl.mortgage.repository.CustomerRepository;
import com.hcl.mortgage.util.PasswordUtil;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceTest.class);
	@Mock
	CustomerRepository customerRepository;
	@InjectMocks
	LoginServiceImpl loginServiceImpl;
	
	@Mock
	PasswordUtil passwordUtil;

	Customer customer;
	LoginDto loginDto;
	LoginResponseDto loginResponseDto;

	public Customer getCustomer() {

		Customer customer = new Customer();
		customer.setCustomerId(11);
		customer.setCustomerName("venkat");
		customer.setLoginId("1233");
		customer.setPassword(passwordUtil.encodePassword("password"));
		return customer;
	}
	public LoginResponseDto getLoginResponseDto() {
		LoginResponseDto loginResponseDto = new LoginResponseDto();
		loginResponseDto.setMessage("Login success");
		loginResponseDto.setCustomerId(11);
		return loginResponseDto;
	}
	public LoginDto getLoginDto() {
		LoginDto loginDto = new LoginDto();
		loginDto.setLoginId("1233");
		loginDto.setPassword("password");
		return loginDto;
	}
	@Before
	public void setup() {
		customer = getCustomer();
		loginDto = getLoginDto();
		loginResponseDto = getLoginResponseDto();
	}
	@Test
	public void userLoginTest() {
		logger.info("inside the userLoginTest method..");
		Mockito.when(customerRepository.findByLoginIdAndPassword(loginDto.getLoginId(),
				passwordUtil.encodePassword(loginDto.getPassword()))).thenReturn(Optional.of(customer));
		LoginResponseDto response = loginServiceImpl.loginUser(loginDto);
		assertEquals("Login success..", response.getMessage());
	}
	
	@Test(expected = CommonException.class)
	public void userLoginTest_1() {
		loginServiceImpl.loginUser(loginDto);
	}
}

