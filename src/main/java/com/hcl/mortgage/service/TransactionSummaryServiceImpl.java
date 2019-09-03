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
			transactionSummaryResponseDto.setAccountNumber(t.getAccountNumber());
			transactionSummaryResponseDto.setAmount(t.getAmount());
			transactionSummaryResponseDto.setComments(t.getComments());
			transactionSummaryResponseDto.setTransactionDate(t.getTransactionDate());
			transactionSummaryResponseDto.setTransactionId(t.getTransactionId());
			transactionSummaryResponseDto.setTransactionType(t.getTransactionType());
			transactionSummaryResponseDto.setClosingBalance(t.getClosingBalance());
			BeanUtils.copyProperties(t, transactionSummaryResponseDto);
			responseList.add(transactionSummaryResponseDto);

		});

		return responseList;
	}

}
