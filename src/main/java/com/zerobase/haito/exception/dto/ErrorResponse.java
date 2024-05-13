package com.zerobase.haito.exception.dto;

import com.zerobase.haito.exception.ApplicationException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
	private int statusCode;
	private String errorMessage;
	
	public static ErrorResponse from(ApplicationException e) {
		return ErrorResponse.builder()
				.statusCode(e.getStatusCode())
				.errorMessage(e.getErroDescription()
				.getErrorMessage())
				.build();
	}
}
