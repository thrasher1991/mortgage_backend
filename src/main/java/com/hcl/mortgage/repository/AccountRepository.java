package com.hcl.mortgage.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import com.hcl.mortgage.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

	public List<Account> findByCustomerId(Integer customerId, Pageable pageable);
	
}
