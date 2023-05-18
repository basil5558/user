package com.shoppingportal.user.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.shoppingportal.user.model.Error;

@ControllerAdvice

public class GlobalExceptionHandler {
	@ExceptionHandler(value = CustomerException.class)
	public ResponseEntity<Error> handleCustomException(CustomerException customerException) {
		Error error = new Error();
		error.setErrorMessageString(customerException.getMessage());
		error.setDateString(LocalDateTime.now().toString());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
