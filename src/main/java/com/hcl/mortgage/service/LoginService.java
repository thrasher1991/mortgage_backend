package com.hcl.mortgage.service;

import org.springframework.stereotype.Service;

import com.hcl.mortgage.dto.LoginDto;
import com.hcl.mortgage.dto.LoginResponseDto;

@Service
public interface LoginService {

	public LoginResponseDto loginUser(LoginDto loginDto);
}
