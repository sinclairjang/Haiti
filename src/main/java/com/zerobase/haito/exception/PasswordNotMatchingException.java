package com.zerobase.haito.exception;

import org.springframework.http.HttpStatus;

import com.zerobase.haito.exception.type.ErrorDesc;

public class PasswordNotMatchingException extends ApplicationException {
	
	private static final long serialVersionUID = -2383999113566938057L;
	
	private static final ErrorDesc errorDescription = ErrorDesc.PASSWORD_NOT_MATCHING;

	@Override
	public int getStatusCode() {
		return HttpStatus.BAD_REQUEST.value();
	}

	@Override
	public ErrorDesc getErroDescription() {
		return errorDescription;
	}

	
	


}
