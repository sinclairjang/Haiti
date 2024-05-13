package com.zerobase.haito.exception;

import org.springframework.http.HttpStatus;

import com.zerobase.haito.exception.type.ErrorDesc;

public class StockAlreadyExistException extends ApplicationException {
	private static final long serialVersionUID = 2478799005297882866L;
	
	private static final ErrorDesc errorDescription = ErrorDesc.STOCK_ALREADY_EXIST;

	@Override
	public int getStatusCode() {
		return HttpStatus.BAD_REQUEST.value();
	}

	@Override
	public ErrorDesc getErroDescription() {
		return errorDescription;
	}

}
