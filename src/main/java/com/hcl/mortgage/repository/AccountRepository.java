package com.hcl.mortgage.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.mortgage.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

	Account findByCustomerIdAndAccountType(Integer customerId, String accountType);

	public List<Account> findByCustomerId(Integer customerId, Pageable pageable);

}
