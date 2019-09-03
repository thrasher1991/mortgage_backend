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
public class MortgageRequestDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private String operationType;
	private Double propertyCost;
	private Double deposit;
	private String occupation;
	private String customerName;
	private String dateOfBirth;
	private String phoneNumber;
	private String email;

}
