package com.zerobase.haito.exception;

import org.springframework.http.HttpStatus;

import com.zerobase.haito.exception.type.ErrorDesc;

public class UsernameAlreadyInUseException extends MemberException {

	private static final long serialVersionUID = -1123840824099335725L;
	
	private static final ErrorDesc errorDescription = ErrorDesc.USER_NAME_ALREADY_IN_USE;

	@Override
	public int getStatusCode() {
		return HttpStatus.BAD_REQUEST.value();
	}

	@Override
	public ErrorDesc getErroDescription() {
		return errorDescription;
	}

	
}
