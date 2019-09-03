package com.hcl.mortgage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.mortgage.dto.MortgageRequestDto;
import com.hcl.mortgage.dto.MortgageResponseDto;
import com.hcl.mortgage.service.MortgageService;

/**
 * 
 * @author HAriPriya G
 *
 */

@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RequestMapping("/api")
public class MortgageController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MortgageController.class);

	@Autowired
	MortgageService mortgageService;

	@PostMapping("/createMortgage")
	public ResponseEntity<MortgageResponseDto> createBook(@RequestBody MortgageRequestDto mortgageRequestDto) {
		LOGGER.info("book controller");
		return new ResponseEntity<>(mortgageService.createMortgage(mortgageRequestDto), HttpStatus.CREATED);

	}

}
