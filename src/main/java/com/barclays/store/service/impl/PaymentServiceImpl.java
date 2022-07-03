package com.barclays.store.service.impl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.barclays.store.dto.PaymentRequestDto;
import com.barclays.store.dto.PaymentResponseDto;
import com.barclays.store.entity.PaymentDetails;
import com.barclays.store.entity.User;
import com.barclays.store.execption.PaymentException;
import com.barclays.store.repository.PaymentRepository;
import com.barclays.store.service.PaymentService;
import com.barclays.store.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Value("${instamojoApiKey}")
	private String instamojoApiKey;
	
	@Value("${instamojoAuthToken}")
	private String instamojoAuthToken;

	@Value("${instamojoSalt}")
	private String instamojoSalt;

	@Value("${paymentOrderNote}")
	private String paymentOrderNote;

	@Value("${paymentReturnURL}")
	private String paymentReturnURL;

	@Value("${paymentNotifyURL}")
	private String paymentNotifyURL;

	@Value("${payment.base.url}")
	private String paymentBaseURL;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private PaymentRepository paymentRepository;


	@Override
	public String generatePaymentUrl(PaymentRequestDto request) throws JsonMappingException, JsonProcessingException,PaymentException {
		
		User user=userService.getUser(request.getUserId());
		
		
		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
		requestBody.add("allow_repeated_payments", "False");
		requestBody.add("amount", request.getPaymentAmount());
		requestBody.add("buyer_name", user.getFirstName()+" "+user.getLastName());
		requestBody.add("email", user.getEmail());
		requestBody.add("purpose", paymentOrderNote);
		requestBody.add("phone", user.getPhone());
		requestBody.add("redirect_url", paymentReturnURL);
		requestBody.add("webhook", paymentNotifyURL);
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Api-Key", instamojoApiKey);
		headers.set("X-Auth-Token", instamojoAuthToken);
		HttpEntity<?> formEntity = new HttpEntity<MultiValueMap<String, String>>(requestBody, headers);

		
		ResponseEntity<String> response = 
				   restTemplate.exchange(paymentBaseURL+"api/1.1/payment-requests/", HttpMethod.POST, formEntity, String.class);
		
		System.out.println(response.getBody()+":"+response.getStatusCodeValue());
		JSONObject responseObject=new JSONObject(response.getBody());
		if(response.getStatusCodeValue()==201) {
			String jsonResponse=responseObject.getJSONObject("payment_request").toString();
			ObjectMapper objectMapper=new ObjectMapper();
			PaymentResponseDto paymentResponse=objectMapper.readValue(jsonResponse, PaymentResponseDto.class);
			
			PaymentDetails paymentDetails=new PaymentDetails();
			
			paymentDetails.setId(UUID.randomUUID().toString());
			paymentDetails.setUserId(user.getId());
			long milis=System.currentTimeMillis();
			paymentDetails.setCrtnTs(new Timestamp(milis));
			paymentDetails.setUpdtTs(new Timestamp(milis));
			paymentDetails.setPaymentRequestId(paymentResponse.getId());
			paymentDetails.setTxnStatus(paymentResponse.getStatus());
			
			paymentDetails.setCreatedAtInstamojo(paymentResponse.getCreated_at());
			paymentDetails.setUpdatedAtInstamojo(paymentResponse.getModified_at());
			paymentDetails.setOrderAmount(Double.valueOf(request.getPaymentAmount()));
			
			
			paymentRepository.save(paymentDetails);
			
			return paymentResponse.getLongurl();
		}
		String errorResponse;
		if(response.getStatusCodeValue()==401) {
			errorResponse=responseObject.getString("message");
		}else {
			errorResponse=responseObject.getJSONObject("message").toString();
		}
		
		throw new PaymentException(response.getStatusCode(), errorResponse);
	}
	
	 
	
	
	
	private String generateSignature(String data) throws NoSuchAlgorithmException, InvalidKeyException {
		String secretKey = instamojoSalt;

		Mac sha256HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
		sha256HMAC.init(secretKeySpec);
		return Base64.getEncoder().encodeToString(sha256HMAC.doFinal(data.getBytes()));

	}





	@Override
	public void acceptPaymentStatus(MultiValueMap<String, String> paramMap) throws PaymentException, InvalidKeyException, NoSuchAlgorithmException {
		String mac=paramMap.getFirst("mac");
		paramMap.remove("mac");
		StringBuilder builder=new StringBuilder();
		for(Entry<String, List<String>> entry:paramMap.entrySet()) {
			builder.append(entry.getKey()).append("|").append(entry.getValue().get(0));
		}
		String generatedMac=generateSignature(builder.toString());
		if(!generatedMac.equals(mac)) {
			throw new PaymentException(HttpStatus.BAD_REQUEST, "Invalid Mac");
		}
		String requestId=paramMap.getFirst("payment_request_id");
		PaymentDetails paymentDetails=paymentRepository.getPaymentDetailsByRequestId(requestId);
		if(paymentDetails==null) {
			throw new PaymentException(HttpStatus.BAD_REQUEST, "Invalid Payment Info");
		}
		paymentDetails.setTxnStatus(paramMap.getFirst("status"));
		paymentDetails.setUpdtTs(new Timestamp(System.currentTimeMillis()));
		paymentRepository.save(paymentDetails);
		
	}


}
