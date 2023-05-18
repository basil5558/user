package com.shoppingportal.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingportal.user.entity.Customer;
import com.shoppingportal.user.service.CustomerService;



@RestController
@RequestMapping("/user")

public class CustomerController {
	
	@Autowired
	CustomerService customerService;


	
	@PostMapping("/register")
	public ResponseEntity<String> registerCustomer(@RequestBody Customer customer){
		return customerService.registerCustomer(customer);
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> loginCustomer(@RequestBody Customer customer){
		return customerService.loginCustomer(customer);
	}
}
