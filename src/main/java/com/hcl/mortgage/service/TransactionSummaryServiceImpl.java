package com.hcl.mortgage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hcl.mortgage.dto.TransactionSummaryResponseDto;
import com.hcl.mortgage.entity.Transaction;
import com.hcl.mortgage.exception.CommonException;
import com.hcl.mortgage.repository.TransactionRepository;
import com.hcl.mortgage.util.MortgageConstants;

@Service
public class TransactionSummaryServiceImpl implements TransactionSummaryService {

	@Autowired
	TransactionRepository transactionRepository;

	/**
	 * This method is use to get transaction summary of a customer based on account
	 * number and transaction type
	 * 
	 * @param String transactionSummaryRequestDto is the input parameter which includes
	 *                                     accountNumber and transactionType
	 * @return List<TransactionSummaryResponseDto> is the output parameter which
	 *         returns a list containing accountNumber, transactionType, amount,
	 *         comments, transactionDate with status code.
	 *
	 */
	@Override
	public List<TransactionSummaryResponseDto> getTransactionSummary(String transactionSummaryRequestDto) {
		List<TransactionSummaryResponseDto> responseList = new ArrayList<>();
		Pageable pageable = PageRequest.of(0, 5);
		List<Transaction> transactionList = transactionRepository.findByAccountNumber(transactionSummaryRequestDto,pageable);
		if (transactionList.isEmpty())
			throw new CommonException(MortgageConstants.ERROR_NO_TRANSACTION_FOUND);
		transactionList.stream().forEach(t -> {
			TransactionSummaryResponseDto transactionSummaryResponseDto = new TransactionSummaryResponseDto();
			transactionSummaryResponseDto.setAccountNumber(transactionList.get(0).getAccountNumber());
			transactionSummaryResponseDto.setAmount(transactionList.get(0).getAmount());
			transactionSummaryResponseDto.setComments(transactionList.get(0).getComments());
			transactionSummaryResponseDto.setTransactionDate(transactionList.get(0).getTransactionDate());
			transactionSummaryResponseDto.setTransactionId(transactionList.get(0).getTransactionId());
			transactionSummaryResponseDto.setTransactionType(transactionList.get(0).getTransactionType());
			BeanUtils.copyProperties(t, transactionSummaryResponseDto);
			responseList.add(transactionSummaryResponseDto);

		});

		return responseList;
	}

}
