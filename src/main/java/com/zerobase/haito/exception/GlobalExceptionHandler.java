package com.zerobase.haito.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.zerobase.haito.exception.dto.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ErrorResponse handleApplicationException(ApplicationException e) {
		log.error(e.getErroDescription().getErrorMessage());
		return ErrorResponse.from(e);
	}
	
}
