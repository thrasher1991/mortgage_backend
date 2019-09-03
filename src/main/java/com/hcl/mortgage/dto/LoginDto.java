package com.hcl.mortgage.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Venkat
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String loginId;
	private String password;

}
	

