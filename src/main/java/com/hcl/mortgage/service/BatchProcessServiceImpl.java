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

/**
 * 
 * @author DeepikaSivarajan
 *
 */
@Service
public class BatchProcessServiceImpl implements BatchProcessService {
	private static final Logger logger = LoggerFactory.getLogger(BatchProcessServiceImpl.class);
	@Autowired
	TransactionRepository transactionRepository;
	@Autowired
	AccountService accountService;

	/**
	 * This method is intended to do monthly payment periodically (every 1minute)
	 * 
	 * 
	 */
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
					if (transactionalAccount.getBalance() >= 200 && mortgageAccount.getBalance() < 0) {
						Double transactionalAccountBalance = transactionalAccount.getBalance() - 200;
						Double mortgageAccountBalance = mortgageAccount.getBalance() + 200;
						transactionalAccount.setBalance(transactionalAccountBalance);
						mortgageAccount.setBalance(mortgageAccountBalance);
						accountService.save(transactionalAccount);
						accountService.save(mortgageAccount);
						Transaction transactional = Transaction.builder()
								.accountNumber(transactionalAccount.getAccountNumber()).amount(200d)
								.comments("Monthly deduction").transactionDate(LocalDate.now()).transactionType("Debit")
								.build();
						Transaction mortgage = Transaction.builder().accountNumber(mortgageAccount.getAccountNumber())
								.amount(200d).comments("Monthly credition").transactionDate(LocalDate.now())
								.transactionType("Credit").build();
						transactionRepository.save(transactional);
						transactionRepository.save(mortgage);
						previousCustomerId = currentCustomerId;

					}
				}

			}
			return "Monthly payment successful";
		}

	}

	//@Scheduled(fixedRate = 1 * 60 * 1000)
	public void testSchedule() {
		monthlyPayment();

	}
}
