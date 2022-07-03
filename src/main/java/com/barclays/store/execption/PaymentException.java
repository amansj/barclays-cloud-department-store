package com.barclays.store.execption;

import org.springframework.http.HttpStatus;

public class PaymentException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 506105902252951302L;
	
	private HttpStatus status;
	
	public PaymentException(HttpStatus status,String message) {
		super(message);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

}
