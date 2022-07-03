package com.barclays.store.dto;

import javax.validation.constraints.NotNull;

import org.immutables.value.Value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonDeserialize(as = ImmutableJwtRequestDto.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Value.Immutable
@Value.Style(jdkOnly=true)
public interface JwtRequestDto {
		
	
	
	
	@NotNull
	public String getEmail();
	
	@NotNull
	public String getPassword();
	
}
