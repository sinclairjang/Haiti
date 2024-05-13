package com.zerobase.haito.exception;

import com.zerobase.haito.exception.type.ErrorDesc;

public abstract class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = -2676106916704976373L;
	
	abstract public int getStatusCode();
	abstract public ErrorDesc getErroDescription();
	
}
