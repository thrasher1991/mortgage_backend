package com.hcl.mortgage.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.mortgage.dto.AccountSummaryResponseDto;
import com.hcl.mortgage.service.AccountSummaryServiceImpl;

/**
 * @author Gurpreet Singh. This is the controller class for getting account
 *         summary for a customer by customerId.
 *
 */
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RequestMapping("/api")
public class AccountSummaryController {

	private static final Logger logger = LoggerFactory.getLogger(AccountSummaryController.class);

	@Autowired
	AccountSummaryServiceImpl accountSummaryServiceImpl;

	/**
	 * This method is use to get account summary of a customer based on customerId
	 * 
	 * @param Integer customerId is the input parameter
	 * @return List<AccountSummaryResponseDto> is the output parameter which returns
	 *         a list containing accountNumber, accountType, balance, creationDate
	 *         with status code.
	 *
	 */
	@GetMapping("/summary/{customerId}")
	public ResponseEntity<List<AccountSummaryResponseDto>> getAccountSummary(@PathVariable Integer customerId) {
		logger.info("in getAccountSummary()");
		List<AccountSummaryResponseDto> response = accountSummaryServiceImpl.getAccountSummary(customerId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
