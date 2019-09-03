package com.hcl.mortgage.util;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.hcl.mortgage.dto.MortgageResponseDto;

@Component
public class EmailSender {

	public static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);
	@Autowired
	JavaMailSender mailSender;

	public String sendMail(MortgageResponseDto mortgageResponseDto, String emailId) {

		LOGGER.info("Enter send mail");

		String returnString = "Email sent sucess";
		try {

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);

			helper.setTo(emailId);
			helper.setSubject("Mortgage Credentials");
			helper.setText("Login Id is: " + mortgageResponseDto.getLoginId() + "\n password is:"
					+ mortgageResponseDto.getPassword() + "\n Mortgage Accountnumber is:"
					+ mortgageResponseDto.getMortageAccountNumber() + "\n Transaction Account number:"
					+ mortgageResponseDto.getTransactionAccountNumer());

			mailSender.send(message);

		} catch (Exception e) {
			returnString = "Mail failed";
			LOGGER.error(e.getMessage());
		}
		return returnString;

	}

}
