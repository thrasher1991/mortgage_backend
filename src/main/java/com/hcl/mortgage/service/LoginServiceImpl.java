package com.hcl.mortgage.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.mortgage.dto.LoginDto;
import com.hcl.mortgage.dto.LoginResponseDto;
import com.hcl.mortgage.entity.Customer;
import com.hcl.mortgage.exception.CommonException;
import com.hcl.mortgage.repository.CustomerRepository;
import com.hcl.mortgage.util.MortgageConstants;
import com.hcl.mortgage.util.PasswordUtil;


@Service
public class LoginServiceImpl implements LoginService{

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
	@Autowired CustomerRepository customerRepository;	
	
	@Override
	public LoginResponseDto loginUser(LoginDto loginDto) {
		LoginResponseDto loginResponseDto = new LoginResponseDto();
		PasswordUtil passwordUtil = new PasswordUtil();
		logger.info("inside the loginUser method..");
		Optional<Customer> customer = customerRepository.findByLoginId(loginDto.getLoginId());
		if (!customer.isPresent())
			throw new CommonException(MortgageConstants.USER_NOT_FOUND);
				
		if (customer.get().getLoginId().equalsIgnoreCase(loginDto.getLoginId())
				//&& customer.get().getPassword().equals(passwordUtil.encodePassword(loginDto.getPassword()))) {
			&& customer.get().getPassword().equals(loginDto.getPassword())) {
			loginResponseDto.setMessage("Login success..");
			loginResponseDto.setCustomerId(customer.get().getCustomerId());
	}
		return loginResponseDto;
}
}