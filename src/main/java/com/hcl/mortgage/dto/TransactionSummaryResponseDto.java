package com.hcl.mortgage.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionSummaryResponseDto implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer transactionId;
	private Double amount;
	private LocalDate transactionDate;
	private String transactionType;
	private String comments;
	private String accountNumber;

}
