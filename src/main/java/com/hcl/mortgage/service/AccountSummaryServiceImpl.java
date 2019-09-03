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

/**
 * @author Gurpreet Singh. This is the service class for getting account summary
 *         for a customer by customerId.
 *
 */
@Service
public class AccountSummaryServiceImpl implements AccountSummaryService {

	@Autowired
	AccountRepository accountRepository;

	/**
	 * This method is use to get account summary of a customer based on customerId
	 * 
	 * @param Integer customerId is the input parameter
	 * @return List<AccountSummaryResponseDto> is the output parameter which returns
	 *         a list containing accountNumber, accountType, balance, creationDate
	 *         with status code.
	 * @exception ERROR_CUSTOMER_NOT_FOUND if no customer is found
	 */
	@Override
	public List<AccountSummaryResponseDto> getAccountSummary(Integer customerId) {
		List<AccountSummaryResponseDto> responseList = new ArrayList<>();
		Pageable pageable = PageRequest.of(0, 5);
		List<Account> accounts = accountRepository.findByCustomerId(customerId, pageable);
		if (accounts.isEmpty())
			throw new CommonException(MortgageConstants.ERROR_CUSTOMER_NOT_FOUND);
		accounts.stream().forEach(a -> {
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
