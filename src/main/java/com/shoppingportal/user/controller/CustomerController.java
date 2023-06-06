package com.shoppingportal.user.controller;

import com.shoppingportal.user.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shoppingportal.user.entity.Customer;
import com.shoppingportal.user.service.CustomerService;



@RestController
@RequestMapping("/user")

public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@PostMapping("/register")
	@CrossOrigin(origins = "*")
	public ResponseEntity<String> registerCustomer(@RequestBody Customer customer){
		return customerService.registerCustomer(customer);
	}
	
	@PostMapping("/login")
	@CrossOrigin(origins = "*")
	public ResponseEntity<String> loginCustomer(@RequestBody Customer customer){
		return customerService.loginCustomer(customer);
	}

	
}
