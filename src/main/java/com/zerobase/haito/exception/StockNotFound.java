package com.zerobase.haito.exception;

import org.springframework.http.HttpStatus;

import com.zerobase.haito.exception.type.ErrorDesc;

public class StockNotFound extends ApplicationException {
	private static final long serialVersionUID = -3033488224111237962L;
	
	private static final ErrorDesc errorDescription = ErrorDesc.STOCK_NOT_FOUND;

	@Override
	public int getStatusCode() {
		return HttpStatus.BAD_REQUEST.value();
	}

	@Override
	public ErrorDesc getErroDescription() {
		// TODO Auto-generated method stub
		return errorDescription;
	}

	
}
