package com.shoppingportal.user.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.shoppingportal.user.model.MailContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shoppingportal.user.entity.Customer;
import com.shoppingportal.user.repository.CustomerRepository;
import com.shoppingportal.user.util.JwtUtil;
import com.shoppingportal.user.exception.CustomerException;


@Service

public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	MailService mailService;

	@Autowired
	private JwtUtil jwtUtil;
	
	public ResponseEntity<String> registerCustomer(Customer customer) {
		String mailString = customer.getMail();
		//String passString = customer.getPassword();
		validatePhoneNumber(customer.getPhoneNumber());
		if(validateMail(mailString)) {
			if(validateUser(mailString))
			{
				customer.setPassword(EncryptionDecryptionService.encrypt(customer.getPassword()));
				customerRepository.save(customer);
				//return new ResponseEntity<String>("Successfully registered",HttpStatus.CREATED);
				//return "Successfully Registered";
				MailContent mailContent = new MailContent();
				mailContent.setToAddress(customer.getMail());
				mailContent.setMailContent("Successfully registered to shopping portal");
				mailContent.setMailSubject("Registration completed");
				mailService.triggerMail(mailContent);
			}
		}
		else {
			throw new CustomerException("Mail format is invalid");
		}	
		return new ResponseEntity<String>("Successfully registered",HttpStatus.CREATED);
	}
	
	public ResponseEntity<String> loginCustomer(Customer customer) {
		String mailString = customer.getMail();
		String passString = customer.getPassword();
		if(validateMail(mailString)) {
			if(userExist(mailString))
			{
				// Login functionality
				Optional<Customer> existingUser = customerRepository.findById(mailString);
				Customer existCustomer = existingUser.get();
				String savedPassString = EncryptionDecryptionService.decrypt(existCustomer.getPassword());
				if(passString.equals(savedPassString)) {
					// Login
					return generateToken(existCustomer);
				}
				else {
					throw new CustomerException("Password incorrect, try again");
				}
			}
		}
		else {
			throw new CustomerException("Mail format is invalid");
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	private ResponseEntity<String> generateToken(Customer existCustomer) {
		String token = jwtUtil.generateToken(existCustomer.getMail());

		return new ResponseEntity<String>(token, HttpStatus.CREATED);
	}

	public static boolean validateMail(String emailAddress) {
		String regexPattern = "^(.+)@(\\S+)$";
	    return Pattern.compile(regexPattern)
	      .matcher(emailAddress)
	      .matches();
	}
	
	public boolean validateUser(String mail) {
		Optional<Customer> customer = customerRepository.findById(mail);
		if(customer.isEmpty()) {  
			return true;
		}
		else {
			throw new CustomerException("Mail ID is already registered, please choose another mail ID");
		}
	}
	
	public boolean validatePhoneNumber(String phoneNumber) {
		String regex = "^\\d{10}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(phoneNumber);
		if(!matcher.matches())
			throw new CustomerException("Plese enter valid phone number ");
		
		List<Customer> customerList = customerRepository.findAll();
		for (Customer customer : customerList) {
		if(customer.getPhoneNumber().equals(phoneNumber))
			throw new CustomerException("Phone Number is already registered, please choose another Phone Number");
		}
				return true;
	}
	
	public boolean userExist(String mail) {
		Optional<Customer> customer = customerRepository.findById(mail);
		if(customer.isPresent()) {  
			return true;
		}
		else {
			throw new CustomerException("Mail ID is not registered, please register as new user");
		}
	}

}
