package com.zerobase.haito.exception;

import org.springframework.http.HttpStatus;

import com.zerobase.haito.exception.type.ErrorDesc;

public class TokenExpiredException extends MemberException {

	private static final long serialVersionUID = -2910680770616649648L;

	private static final ErrorDesc errorDescription = ErrorDesc.TOKEN_EXPIRED;

	@Override
	public int getStatusCode() {
		return HttpStatus.FORBIDDEN.value();
	}

	@Override
	public ErrorDesc getErroDescription() {
		return errorDescription;
	}

	
}
