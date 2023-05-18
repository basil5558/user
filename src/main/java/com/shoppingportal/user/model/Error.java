package com.shoppingportal.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Error {
	private String errorMessageString;
	private String dateString;
}
