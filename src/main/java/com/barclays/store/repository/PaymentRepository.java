package com.barclays.store.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.barclays.store.entity.PaymentDetails;

@Repository
public interface PaymentRepository extends CrudRepository<PaymentDetails, String> {
	
	@Query("select paymentDetails from PaymentDetails paymentDetails where paymentDetails.paymentRequestId=?1 ")
	public PaymentDetails getPaymentDetailsByRequestId(String paymentRequestId);

}
