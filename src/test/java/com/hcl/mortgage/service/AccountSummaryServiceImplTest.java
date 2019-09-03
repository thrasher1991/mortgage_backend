package com.hcl.mortgage.service;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.hcl.mortgage.dto.AccountSummaryResponseDto;
import com.hcl.mortgage.entity.Account;
import com.hcl.mortgage.exception.CommonException;
import com.hcl.mortgage.repository.AccountRepository;

@RunWith(MockitoJUnitRunner.class)
public class AccountSummaryServiceImplTest {

	@InjectMocks
	AccountSummaryServiceImpl accountSummaryServiceImpl;
	
	@Mock
	AccountRepository accountRepository;
	
	AccountSummaryResponseDto accountSummaryResponseDto;
	List<AccountSummaryResponseDto> listAccountSummary;
	
	Account account;
	List<Account> listAccounts;
	
	
	public AccountSummaryResponseDto getAccountSummaryResponseDto()
	{
		AccountSummaryResponseDto accountSummaryResponseDto = new AccountSummaryResponseDto();
		accountSummaryResponseDto.setAccountNumber("12345678912345");
		accountSummaryResponseDto.setAccountType("Mortgage");
		accountSummaryResponseDto.setBalance(20000d);
		accountSummaryResponseDto.setCreationDate(LocalDate.now());
		return accountSummaryResponseDto;
	}
	
	public Account getAccount()
	{
		Account account = new Account();
		account.setAccountId(1);
		account.setAccountNumber("12345678912345");
		account.setAccountType("Mortgage");
		account.setBalance(20000D);
		account.setCreationDate(LocalDate.now());
		account.setCustomerId(1);
		return account;
	}
	
	@Before
	public void setup()
	{
		accountSummaryResponseDto = getAccountSummaryResponseDto();
		account = getAccount();
		listAccountSummary = new ArrayList<>();
		listAccountSummary.add(accountSummaryResponseDto);
		listAccounts = new ArrayList<>();
		listAccounts.add(account);
	}
	
	@Test
	public void testGetAccountSummary()
	{
		Pageable pageable = PageRequest.of(0, 5);
		Mockito.when(accountRepository.findByCustomerId(1,pageable)).thenReturn(listAccounts);
		List<AccountSummaryResponseDto> response = accountSummaryServiceImpl.getAccountSummary(1);
		assertEquals(listAccountSummary.get(0).getAccountNumber(), response.get(0).getAccountNumber());
	}
	
	@Test(expected = CommonException.class)
	public void testGetAccountSummary_1()
	{
		accountSummaryServiceImpl.getAccountSummary(1);
	}
	
}
