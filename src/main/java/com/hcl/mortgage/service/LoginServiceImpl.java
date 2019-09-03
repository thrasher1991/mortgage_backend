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

/**
 * @author Venkat . This is the service class for login the customer
 *
 */
@Service
public class LoginServiceImpl implements LoginService {

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	PasswordUtil passwordUtil;

	/**
	 * This method is use to login the customer
	 * 
	 * @param LoginDto is the input parameter which contains loginId and password.
	 * @return LoginResponseDto is the output parameter which contains customerId
	 *         and message with status code.
	 * @exception  USER_NOT_FOUND if no user is found
	 */
	@Override
	public LoginResponseDto loginUser(LoginDto loginDto) {
		LoginResponseDto loginResponseDto = new LoginResponseDto();
		logger.info("inside the loginUser method..");
		Optional<Customer> customer = customerRepository.findByLoginIdAndPassword(loginDto.getLoginId(),
				passwordUtil.encodePassword(loginDto.getPassword()));
		if (!customer.isPresent())
			throw new CommonException(MortgageConstants.USER_NOT_FOUND);
		loginResponseDto.setMessage("Login success..");
		loginResponseDto.setCustomerId(customer.get().getCustomerId());
		return loginResponseDto;
	}
}