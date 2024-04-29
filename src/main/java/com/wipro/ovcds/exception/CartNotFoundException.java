package com.wipro.ovcds.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class CartNotFoundException extends Exception {

	public CartNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
    
	
}
