package com.shoppingportal.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoppingportal.user.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String>{
	
	
}
