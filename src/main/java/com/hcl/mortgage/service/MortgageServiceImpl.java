package com.hcl.mortgage.service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.mortgage.dto.MortgageRequestDto;
import com.hcl.mortgage.dto.MortgageResponseDto;
import com.hcl.mortgage.entity.Account;
import com.hcl.mortgage.entity.Customer;
import com.hcl.mortgage.entity.Mortgage;
import com.hcl.mortgage.exception.CommonException;
import com.hcl.mortgage.repository.AccountRepository;
import com.hcl.mortgage.repository.CustomerRepository;
import com.hcl.mortgage.repository.MortgageRepository;
import com.hcl.mortgage.util.EmailSender;
import com.hcl.mortgage.util.MortgageConstants;
import com.hcl.mortgage.util.PasswordUtil;

@Service
public class MortgageServiceImpl implements MortgageService {

	public static final Logger LOGGER = LoggerFactory.getLogger(MortgageServiceImpl.class);

	@Autowired
	MortgageRepository mortgageRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	PasswordUtil passwordUtil;

	@Autowired
	EmailSender emailSender;

	Random rand = new Random();

	@Override
	public MortgageResponseDto createMortgage(MortgageRequestDto mortgageRequestDto) {

		LOGGER.info("Create mortgage service ");

		if (!validateCustomerName(mortgageRequestDto.getCustomerName()))
			throw new CommonException(MortgageConstants.ERROR_NAME);
		if (!validPhoneNumber(mortgageRequestDto.getPhoneNumber()))
			throw new CommonException(MortgageConstants.ERROR_PHONENUMBER);
		if (!validEmail(mortgageRequestDto.getEmail()))
			throw new CommonException(MortgageConstants.ERROR_EMAIL);
		int age = calculateAge(mortgageRequestDto.getDateOfBirth());
		if (age < 18)
			throw new CommonException(MortgageConstants.ERROR_AGE);
		if (mortgageRequestDto.getPropertyCost() < 100000)
			throw new CommonException(MortgageConstants.ERROE_PROPERTY);
		if (mortgageRequestDto.getDeposit() <= 0)
			throw new CommonException(MortgageConstants.ERROE_DEPOSIT);

		// Storing customer info into customer table
		Customer customer = new Customer();
		customer.setLoginId(mortgageRequestDto.getCustomerName() + LocalDate.now().getDayOfMonth());
		String pwd = generatePassword();
		customer.setPassword(passwordUtil.encodePassword(pwd));
		customer.setCustomerName(mortgageRequestDto.getCustomerName());
		Customer customerDb = customerRepository.save(customer);

		// Storing transaction account and mortgage account in Account table
		Account transactionAccount = new Account();
		transactionAccount.setAccountNumber("ACC" + accountNumber());
		transactionAccount.setAccountType("Transactional Account");
		transactionAccount.setBalance(mortgageRequestDto.getPropertyCost() - mortgageRequestDto.getDeposit());
		transactionAccount.setCustomerId(customerDb.getCustomerId());
		accountRepository.save(transactionAccount);

		Account mortgageAccount = new Account();
		mortgageAccount.setAccountNumber("MRT" + accountNumber());
		mortgageAccount.setAccountType("Mortgage Account");
		mortgageAccount.setBalance(-(mortgageRequestDto.getPropertyCost() - mortgageRequestDto.getDeposit()));
		mortgageAccount.setCustomerId(customerDb.getCustomerId());
		accountRepository.save(mortgageAccount);

		// Storing Mortgage account information in Mortgage table
		Mortgage mortgage = new Mortgage();
		mortgage.setDateOfBirth(getLocalDate(mortgageRequestDto.getDateOfBirth()));
		BeanUtils.copyProperties(mortgageRequestDto, mortgage);
		mortgage.setCustomerId(customerDb.getCustomerId());
		mortgageRepository.save(mortgage);

		LOGGER.info("Mortage Registered Successfully");
		MortgageResponseDto mortgageResponseDTO = new MortgageResponseDto();
		mortgageResponseDTO.setLoginId(customerDb.getLoginId());
		mortgageResponseDTO.setPassword(pwd);
		mortgageResponseDTO.setCustomerName(customerDb.getCustomerName());
		mortgageResponseDTO.setTransactionAccountNumer(transactionAccount.getAccountNumber());
		mortgageResponseDTO.setMortageAccountNumber(mortgageAccount.getAccountNumber());
		mortgageResponseDTO.setMessage("Accoutn created successfully");

		emailSender.sendMail(mortgageResponseDTO, mortgageRequestDto.getEmail());
		return mortgageResponseDTO;
	}

	private boolean validateCustomerName(String customerName) {
		String name = ("^[a-zA-Z]*$");
		return customerName.matches(name);
	}

	private boolean validEmail(String email) {
		Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(email);
		return (m.find() && m.group().equals(email));
	}

	private boolean validPhoneNumber(String number) {
		Pattern p = Pattern.compile("^[0-9]{10}$");
		Matcher m = p.matcher(number);
		return (m.find() && m.group().equals(number));
	}

	public int calculateAge(String birhtDate) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MortgageConstants.DATE_FORMAT);
		LocalDate localDate = LocalDate.now();
		String dateStr = formatter.format(localDate);

		LocalDate birthLocalDate = LocalDate.parse(birhtDate, formatter);

		String birhDateStr = formatter.format(birthLocalDate);

		LocalDate nowDate = LocalDate.parse(dateStr, formatter);
		LocalDate birthDate = LocalDate.parse(birhDateStr, formatter);

		return Period.between(birthDate, nowDate).getYears();
	}

	public String generatePassword() {
		SecureRandom r = new SecureRandom();
		final String alphaCaps = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final String alpha = "abcdefghijklmnopqrstuvwxyz";
		final String numeric = "0123456789";
		final String specialChars = "!@#$%^&*_=+-/";
		int length = 5;
		String dic = alphaCaps + alpha + numeric + specialChars;
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int index = r.nextInt(dic.length());
			result.append(dic.charAt(index));
		}

		return result.toString();
	}

	public String accountNumber() {

		StringBuilder number = new StringBuilder();
		for (int i = 0; i < 14; i++) {
			int n = rand.nextInt(10) + 0;
			number.append(Integer.toString(n));
		}
		return number.toString();

	}

	public LocalDate getLocalDate(String data) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(MortgageConstants.DATE_FORMAT);
		return LocalDate.parse(data, dateTimeFormatter);
	}

}
