package com.hcl.mortgage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hcl.mortgage.dto.AccountSummaryResponseDto;
import com.hcl.mortgage.entity.Account;
import com.hcl.mortgage.exception.CommonException;
import com.hcl.mortgage.repository.AccountRepository;
import com.hcl.mortgage.util.MortgageConstants;

@Service
public class AccountSummaryServiceImpl implements AccountSummaryService{
	
	@Autowired
	AccountRepository accountRepository;

	@Override
	public List<AccountSummaryResponseDto> getAccountSummary(Integer customerId) {
		List<AccountSummaryResponseDto> responseList = new  ArrayList<>();
		Pageable pageable = PageRequest.of(0, 5);
		List<Account> accounts = accountRepository.findByCustomerId(customerId,pageable);
		if(accounts.isEmpty())
			throw new CommonException(MortgageConstants.ERROR_CUSTOMER_NOT_FOUND);
		accounts.stream().forEach(a->{
			AccountSummaryResponseDto response = new AccountSummaryResponseDto();
			response.setAccountNumber(accounts.get(0).getAccountNumber());
			response.setAccountType(accounts.get(0).getAccountType());
			response.setBalance(accounts.get(0).getBalance());
			response.setCreationDate(accounts.get(0).getCreationDate());
			BeanUtils.copyProperties(a, response);
			responseList.add(response);
		});
		return responseList;
	}

}
