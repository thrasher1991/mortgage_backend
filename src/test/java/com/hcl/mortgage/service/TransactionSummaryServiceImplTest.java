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

import com.hcl.mortgage.dto.TransactionSummaryResponseDto;
import com.hcl.mortgage.entity.Transaction;
import com.hcl.mortgage.exception.CommonException;
import com.hcl.mortgage.repository.TransactionRepository;

@RunWith(MockitoJUnitRunner.class)
public class TransactionSummaryServiceImplTest {
	@InjectMocks
	TransactionSummaryServiceImpl accountSummaryServiceImpl;
	
	@Mock
	TransactionRepository transactionRepository;
	
	TransactionSummaryResponseDto accountSummaryResponseDto;
	List<TransactionSummaryResponseDto> listAccountSummary;
	
	Transaction transaction;
	List<Transaction> listTransaction;
	
	public TransactionSummaryResponseDto getTransactionSummaryResponseDto()
	{
		TransactionSummaryResponseDto transactionSummaryResponseDto = new TransactionSummaryResponseDto();
		transactionSummaryResponseDto.setAccountNumber("123435678845");
		transactionSummaryResponseDto.setAmount(123254D);
		transactionSummaryResponseDto.setComments("Gurpreet");
		transactionSummaryResponseDto.setTransactionDate(LocalDate.now());
		transactionSummaryResponseDto.setTransactionId(1);
		transactionSummaryResponseDto.setTransactionType("Credit");
		return transactionSummaryResponseDto;
	}
	
	public Transaction getTransaction()
	{
		Transaction transaction = new Transaction();
		transaction.setAccountNumber("123435678845");
		transaction.setAmount(21323D);
		transaction.setComments("Gurpreet");
		transaction.setTransactionDate(LocalDate.now());
		transaction.setTransactionId(1);
		transaction.setTransactionType("Credit");
		return transaction;
	}
	
	@Before
	public void setup()
	{
		accountSummaryResponseDto = getTransactionSummaryResponseDto();
		transaction = getTransaction();
		listAccountSummary = new ArrayList<>();
		listAccountSummary.add(accountSummaryResponseDto);
		listTransaction = new ArrayList<>();
		listTransaction.add(transaction);
	}
	
	@Test
	public void testGetTransactionSummary()
	{
		Pageable pageable = PageRequest.of(0, 5);
		Mockito.when(transactionRepository.findByAccountNumber("123435678845",pageable)).thenReturn(listTransaction);
		List<TransactionSummaryResponseDto> response = accountSummaryServiceImpl.getTransactionSummary("123435678845");
		assertEquals(listAccountSummary.get(0).getAccountNumber(), response.get(0).getAccountNumber());
	}
	
	@Test(expected = CommonException.class)
	public void testGetTransactionSummary_1()
	{
		accountSummaryServiceImpl.getTransactionSummary("123435678845");
	}
	

}
