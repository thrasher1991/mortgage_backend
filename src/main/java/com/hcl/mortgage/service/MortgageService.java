package com.hcl.mortgage.service;

import com.hcl.mortgage.dto.MortgageResponseDto;
import com.hcl.mortgage.dto.MortgageRequestDto;

public interface MortgageService {
	
	MortgageResponseDto createMortgage(MortgageRequestDto mortgageRequestDto);

}
