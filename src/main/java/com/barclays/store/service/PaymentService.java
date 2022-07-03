package com.barclays.store.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.transaction.Transactional;

import org.springframework.util.MultiValueMap;

import com.barclays.store.dto.PaymentRequestDto;
import com.barclays.store.execption.PaymentException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface PaymentService {
	
	@Transactional
	public String generatePaymentUrl(PaymentRequestDto request) throws JsonMappingException, JsonProcessingException,PaymentException;
	
	@Transactional
	public void acceptPaymentStatus(MultiValueMap<String,String> paramMap) throws PaymentException,InvalidKeyException,NoSuchAlgorithmException;
	

}
