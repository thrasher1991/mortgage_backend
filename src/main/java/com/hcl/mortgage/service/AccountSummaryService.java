package com.hcl.mortgage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hcl.mortgage.dto.AccountSummaryResponseDto;

@Service
public interface AccountSummaryService {

	public List<AccountSummaryResponseDto> getAccountSummary(Integer customerId);
}
