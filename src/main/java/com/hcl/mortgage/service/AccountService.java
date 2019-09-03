package com.hcl.mortgage.service;

import java.util.List;

import com.hcl.mortgage.entity.Account;

public interface AccountService {

	List<Account> getAllAccounts();

	Account getTransactionalAccount(Integer customerId, String string);

	Account getMortgageAccount(Integer customerId, String string);

	Account save(Account account);

}
