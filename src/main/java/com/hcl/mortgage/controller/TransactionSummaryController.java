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

import com.hcl.mortgage.dto.TransactionSummaryResponseDto;
import com.hcl.mortgage.service.TransactionSummaryService;


/**
 * @author Gurpreet Singh
 *
 */
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RequestMapping("/api")
public class TransactionSummaryController {

	private static final Logger logger = LoggerFactory.getLogger(TransactionSummaryController.class);

	@Autowired
	TransactionSummaryService transactionSummaryServiceImpl;

	/**
	 * This method is use to get transaction summary of a customer based on account
	 * number and transaction type
	 * 
	 * @param TransactionSummaryRequestDto is the input parameter which includes
	 *                                     accountNumber and transactionType
	 * @return List<TransactionSummaryResponseDto> is the output parameter which
	 *         returns a list containing accountNumber, transactionType, amount,
	 *         comments, transactionDate with status code.
	 *
	 */
	@GetMapping("/transactionSummary/{accountNumber}")
	public ResponseEntity<List<TransactionSummaryResponseDto>> getTransactionSummary(
			@PathVariable String accountNumber) {
		logger.info("in getAccountSummary()");
		List<TransactionSummaryResponseDto> response = transactionSummaryServiceImpl
				.getTransactionSummary(accountNumber);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
