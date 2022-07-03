package com.barclays.store.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PaymentDetails")
public class PaymentDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 101822422738902846L;
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "userId")
	private String userId;
	
	@Column(name = "paymentRequestId")
	private String paymentRequestId;
	
	
	@Column(name = "paymentResponseId")
	private String paymentResponseId;
	
	
	@Column(name="orderAmount")
	private Double orderAmount;
	
	@Column(name="txnStatus")
	private String txnStatus;

	@Column(name="crtnTs")
	private Timestamp crtnTs;
	
	@Column(name="updtTs")
	private Timestamp updtTs;

	@Column(name="createdAtInstamojo")
	private String createdAtInstamojo;
	
	@Column(name="updatedAtInstamojo")
	private String updatedAtInstamojo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPaymentRequestId() {
		return paymentRequestId;
	}

	public void setPaymentRequestId(String paymentRequestId) {
		this.paymentRequestId = paymentRequestId;
	}

	public String getPaymentResponseId() {
		return paymentResponseId;
	}

	public void setPaymentResponseId(String paymentResponseId) {
		this.paymentResponseId = paymentResponseId;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getTxnStatus() {
		return txnStatus;
	}

	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}

	public Timestamp getCrtnTs() {
		return crtnTs;
	}

	public void setCrtnTs(Timestamp crtnTs) {
		this.crtnTs = crtnTs;
	}

	public Timestamp getUpdtTs() {
		return updtTs;
	}

	public void setUpdtTs(Timestamp updtTs) {
		this.updtTs = updtTs;
	}

	public String getCreatedAtInstamojo() {
		return createdAtInstamojo;
	}

	public void setCreatedAtInstamojo(String createdAtInstamojo) {
		this.createdAtInstamojo = createdAtInstamojo;
	}

	public String getUpdatedAtInstamojo() {
		return updatedAtInstamojo;
	}

	public void setUpdatedAtInstamojo(String updatedAtInstamojo) {
		this.updatedAtInstamojo = updatedAtInstamojo;
	}

}
