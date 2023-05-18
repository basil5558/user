package com.shoppingportal.user.exception;

public class CustomerException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomerException(String exceptionString) {
		super(exceptionString);
	}
}
