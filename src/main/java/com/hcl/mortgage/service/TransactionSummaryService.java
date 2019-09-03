package com.hcl.mortgage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hcl.mortgage.dto.TransactionSummaryResponseDto;

@Service
public interface TransactionSummaryService {

	public List<TransactionSummaryResponseDto> getTransactionSummary(String transactionSummaryRequestDto);
	
 }
