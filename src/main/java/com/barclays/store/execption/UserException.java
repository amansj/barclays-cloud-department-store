package com.barclays.store.execption;

import org.springframework.http.HttpStatus;

public class UserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 302597840574104757L;

	private HttpStatus status;

	public UserException(HttpStatus status,String message) {
		super(message);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}
	
	
	
}
