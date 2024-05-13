package com.zerobase.haito.exception;

import org.springframework.http.HttpStatus;

import com.zerobase.haito.exception.type.ErrorDesc;

public class InvalidTickerException extends ScrapeException {
	private static final long serialVersionUID = -2917764380476737070L;

	private static final ErrorDesc errorDescription = ErrorDesc.INVALID_TICKER;

	@Override
	public int getStatusCode() {
		return HttpStatus.BAD_REQUEST.value();
	}

	@Override
	public ErrorDesc getErroDescription() {
		return errorDescription;
	}
}
	

