package com.hcl.mortgage.service;

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
import com.hcl.mortgage.repository.AccountRepository;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {
	@Mock
	AccountRepository accountRepository;
	@InjectMocks
	AccountServiceImpl accountServiceImpl;
	List<Account> accountList;
	Account transactionalAccount;
	Account mortgageAccount;
	Account account;

	@Before
	public void setUp() {
		accountList = new ArrayList<>();
		account = new Account();
		account.setAccountId(1);
		accountList.add(account);

		transactionalAccount = new Account();
		transactionalAccount.setAccountType("Transactional Account");
		transactionalAccount.setAccountNumber("ACC82163067271854");

		mortgageAccount = new Account();
		mortgageAccount.setAccountType("Mortgage Account");
		mortgageAccount.setAccountNumber("MRT73189768086149");
	}

	@Test
	public void testGetAllAccounts() {
		Mockito.when(accountRepository.findAll()).thenReturn(accountList);
		List<Account> accounts = accountServiceImpl.getAllAccounts();
		Assert.assertEquals(accountList.size(), accounts.size());
	}

	@Test
	public void testGetTransactionalAccount() {
		Mockito.when(accountRepository.findByCustomerIdAndAccountType(Mockito.anyInt(), Mockito.anyString()))
				.thenReturn(transactionalAccount);
		Account transactionalAccount = accountServiceImpl.getTransactionalAccount(Mockito.anyInt(),
				Mockito.anyString());
		Assert.assertEquals("Transactional Account", transactionalAccount.getAccountType());
	}

	@Test
	public void testGetMortgageAccount() {
		Mockito.when(accountRepository.findByCustomerIdAndAccountType(Mockito.anyInt(), Mockito.anyString()))
				.thenReturn(mortgageAccount);
		Account mortgageAccount = accountServiceImpl.getMortgageAccount(Mockito.anyInt(), Mockito.anyString());
		Assert.assertEquals("Mortgage Account", mortgageAccount.getAccountType());

	}

	@Test
	public void testsave() {
		Mockito.when(accountRepository.save(account)).thenReturn(account);
		Account acc = accountServiceImpl.save(account);
		Assert.assertEquals(account.getAccountId(), acc.getAccountId());
	}

}
