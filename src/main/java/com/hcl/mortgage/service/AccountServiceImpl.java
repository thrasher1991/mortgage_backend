package com.hcl.mortgage.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.mortgage.entity.Account;
import com.hcl.mortgage.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {
	private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
	@Autowired
	AccountRepository accountRepository;

	@Override
	public List<Account> getAllAccounts() {
		logger.info("get all accounts");
		return accountRepository.findAll();
	}

	@Override
	public Account getTransactionalAccount(Integer customerId, String accountType) {
		logger.info("get transactional account");
		return accountRepository.findByCustomerIdAndAccountType(customerId, accountType);
	}

	@Override
	public Account getMortgageAccount(Integer customerId, String accountType) {
		logger.info("get mortgage account");
		return accountRepository.findByCustomerIdAndAccountType(customerId, accountType);
	}

	@Override
	public Account save(Account account) {
		logger.info("save account");
		return accountRepository.save(account);
	}

}
