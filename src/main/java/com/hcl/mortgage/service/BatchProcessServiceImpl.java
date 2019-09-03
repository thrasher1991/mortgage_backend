package com.hcl.mortgage.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.hcl.mortgage.entity.Account;
import com.hcl.mortgage.entity.Transaction;
import com.hcl.mortgage.exception.CommonException;
import com.hcl.mortgage.repository.TransactionRepository;
import com.hcl.mortgage.util.MortgageConstants;

@Service
public class BatchProcessServiceImpl implements BatchProcessService {
	private static final Logger logger = LoggerFactory.getLogger(BatchProcessServiceImpl.class);
	@Autowired
	TransactionRepository transactionRepository;
	@Autowired
	AccountService accountService;

	@Override
	public String monthlyPayment() {
		logger.info("monthly payment in service");
		List<Account> allAccounts = accountService.getAllAccounts();
		if (allAccounts.isEmpty()) {
			throw new CommonException(MortgageConstants.ACCOUNT_NOT_FOUND);
		} else {
			Integer previousCustomerId = 0;
			Integer currentCustomerId = 0;
			for (Account a : allAccounts) {
				Integer customerId = a.getCustomerId();
				currentCustomerId = customerId;
				if (!currentCustomerId.equals(previousCustomerId)) {
					Account transactionalAccount = accountService.getTransactionalAccount(customerId,
							"Transactional Account");
					Account mortgageAccount = accountService.getMortgageAccount(customerId, "Mortgage Account");
					if(transactionalAccount.getBalance() ==0)
						throw new CommonException(MortgageConstants.INSUFFICIENT_BALANCE);
					if (transactionalAccount.getBalance() >= 200 && mortgageAccount.getBalance() < 0) {
						Double transactionalAccountBalance = transactionalAccount.getBalance() - 200;
						Double mortgageAccountBalance = mortgageAccount.getBalance() + 200;
						transactionalAccount.setBalance(transactionalAccountBalance);
						mortgageAccount.setBalance(mortgageAccountBalance);
						accountService.save(transactionalAccount);
						accountService.save(mortgageAccount);

						Transaction transactional = new Transaction();
						transactional.setAccountNumber(transactionalAccount.getAccountNumber());
						transactional.setAmount(200d);
						transactional.setComments("Monthly deduction");
						transactional.setTransactionDate(LocalDate.now());
						transactional.setTransactionType("Debit");

						Transaction mortgage = new Transaction();
						mortgage.setAccountNumber(mortgageAccount.getAccountNumber());
						mortgage.setAmount(200d);
						mortgage.setComments("Monthly credition");
						mortgage.setTransactionDate(LocalDate.now());
						mortgage.setTransactionType("Credit");

						transactionRepository.save(transactional);
						transactionRepository.save(mortgage);
						previousCustomerId = currentCustomerId;

					}
				}

			}
			return "Monthly payment successful";
		}

	}

	@Scheduled(fixedRate = 1 * 60 * 1000)
	public void testSchedule() {
		monthlyPayment();

	}
}
