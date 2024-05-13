package com.zerobase.haito.exception;

import org.springframework.http.HttpStatus;

import com.zerobase.haito.exception.type.ErrorDesc;

public class ParseException extends ScrapeException {

	private static final long serialVersionUID = -6325093668221780151L;
	
	private static final ErrorDesc errorDescription = ErrorDesc.UNEXPECTED_FORMAT;

	@Override
	public int getStatusCode() {
		return HttpStatus.INTERNAL_SERVER_ERROR.value();
	}

	@Override
	public ErrorDesc getErroDescription() {
		return errorDescription;
	}


}
