package com.barclays.store.dto;

import javax.validation.constraints.NotNull;

import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize
@JsonDeserialize(as = ImmutablePaymentResponseDto.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Value.Immutable
@Value.Style(jdkOnly=true)
public interface PaymentResponseDto {
	
	
	@NotNull
	public String getId();
	
	@Nullable
	public String getPhone();
	
	
	@Nullable
	public String getEmail();
	
	@NotNull
	public String getBuyer_name();
	
	
	@NotNull
	public String getPurpose();
	
	@Nullable
	public String getExpires_at();
	
	@Nullable
	public String getStatus();
	
	@NotNull
	public Boolean getSend_sms();
	
	@NotNull
	public Boolean getSend_email();
	
	@Nullable
	public String getSms_Status();
	
	@Nullable
	public String getEmail_Status();
	
	@Nullable
	public String getShorturl();
	
	
	@Nullable
	public String getLongurl();
	
	@Nullable
	public String getRedirect_url();
	
	@Nullable
	public String getWebhook();
	
	@NotNull
	public Boolean getAllow_repeated_payments();
	
	@Nullable
	public String getCreated_at();
	
	@Nullable
	public String getModified_at();
  

}
