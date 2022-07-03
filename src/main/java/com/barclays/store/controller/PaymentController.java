package com.barclays.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.barclays.store.dto.PaymentRequestDto;
import com.barclays.store.execption.PaymentException;
import com.barclays.store.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@RequestMapping(method=RequestMethod.POST,path="/request")
	@Operation(summary = "payment request", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> createPaymentRequest(@RequestBody PaymentRequestDto paymentRequest){
		try {
			String response=paymentService.generatePaymentUrl(paymentRequest);
			
			return ResponseEntity.ok(response);
			
		}catch (PaymentException e) {
			
			return ResponseEntity.status(e.getStatus()).body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		
	}
	
	@RequestMapping(method = RequestMethod.POST, path ="/accept/notification",
			  consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	
	public ResponseEntity<?> handlePaymentNotification(@RequestParam MultiValueMap<String,String> paramMap)  {
			try {
				paymentService.acceptPaymentStatus(paramMap);
				
				return ResponseEntity.ok().build();
			} catch (PaymentException e) {
				
				return ResponseEntity.status(e.getStatus()).body(e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();			
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}   
		
	}
	
	@RequestMapping(method=RequestMethod.GET,path="/dummy-return")
	//payment_id=MOJO5a06005J21512197&
	//payment_status=Credit&
	//payment_request_id=d66cb29dd059482e8072999f995c4eef
	
	public ResponseEntity<?> processDummyReturn(@RequestParam("payment_id") String payment_id,
			@RequestParam("payment_status") String payment_status,
			@RequestParam("payment_request_id") String payment_request_id){
		
		
		return ResponseEntity.ok().build();
	}
	
	
	
}
