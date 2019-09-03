package com.hcl.mortgage.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MortgageResponseDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private String message;
	private String loginId;
	private String password;
	private String transactionAccountNumer;
	private String mortageAccountNumber;
	private String customerName;

}
