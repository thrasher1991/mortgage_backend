package com.hcl.mortgage.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.hcl.mortgage.entity.Account;
import com.hcl.mortgage.entity.Transaction;
import com.hcl.mortgage.repository.TransactionRepository;

@RunWith(MockitoJUnitRunner.class)
public class BatchProcessServiceImplTest {
	@Mock
	TransactionRepository transactionRepository;
	@InjectMocks
	BatchProcessServiceImpl batchProcessServiceImpl;
	@Mock
	AccountService accountService;

	Transaction transactional;
	Transaction mortgage;
	List<Account> accountList;
	Account transactionalAccount;
	Account mortgageAccount;
	Account account;

	@Before
	public void setUp() {
		transactional = new Transaction(1, 200d, LocalDate.now(), "Debit", "Monthly deduction", "ACC82163067271854");
		mortgage = new Transaction(2, 200d, LocalDate.now(), "Credit", "Monthly credition", "MRT73189768086149");
		accountList = new ArrayList<>();
		account = new Account();
//		account.setAccountId(1);
//		account.setAccountNumber("1234");
//		account.setAccountType(accountType);
//		accountList.add(account);

		transactionalAccount = new Account();
		transactionalAccount.setAccountId(1);
		transactionalAccount.setBalance(120000.00);
		transactionalAccount.setCreationDate(LocalDate.now());
		transactionalAccount.setCustomerId(1);
		transactionalAccount.setAccountType("Transactional Account");
		transactionalAccount.setAccountNumber("ACC82163067271854");

		mortgageAccount = new Account();
		mortgageAccount.setAccountId(2);
		mortgageAccount.setBalance(110000.00);
		mortgageAccount.setCreationDate(LocalDate.now());
		mortgageAccount.setCustomerId(1);
		mortgageAccount.setAccountType("Mortgage Account");
		mortgageAccount.setAccountNumber("MRT73189768086149");

		accountList.add(mortgageAccount);
		accountList.add(transactionalAccount);
	}

	@Test
	public void testMonthlyPayment() {

		Mockito.when(accountService.getAllAccounts()).thenReturn(accountList);
		Mockito.when(accountService.getTransactionalAccount(Mockito.anyInt(), Mockito.anyString()))
				.thenReturn(transactionalAccount);
		Mockito.when(accountService.getMortgageAccount(Mockito.anyInt(), Mockito.anyString()))
				.thenReturn(mortgageAccount);
		Mockito.lenient().when(accountService.save(Mockito.any())).thenReturn(mortgageAccount);
		Mockito.lenient().when(accountService.save(Mockito.any())).thenReturn(mortgageAccount);
		Mockito.lenient().when(transactionRepository.save(Mockito.any())).thenReturn(transactional);
		Mockito.lenient().when(transactionRepository.save(Mockito.any())).thenReturn(mortgage);
		String msg = batchProcessServiceImpl.monthlyPayment();
		Assert.assertEquals("Monthly payment successful", msg);

	}
}
