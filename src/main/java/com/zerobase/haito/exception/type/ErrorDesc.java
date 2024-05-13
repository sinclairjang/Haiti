package com.zerobase.haito.exception.type;

public enum ErrorDesc {
	UNEXPECTED_FORMAT("예상한 데이터 형식이 아닙니다. 배당 기록이 없거나 크롤링 대상 웹사이트 HTML형식이 바뀌었을 수도 있습니다."),
	INTERNAL_SERVER_ERROR("내부 서버 오류입니다."), 
	INVALID_TICKER("요청하신 틱커의 배당정보를 찾지 못했습니다. 틱커에 혹시 오타가 없는지 확인해주세요."), 
	SCRPAING_ERROR("웹 스크랩핑 중 에러가 발생했습니다."), 
	
	STOCK_ALREADY_EXIST("요청하신 종목은 이미 추가하셨습니다."), 
	STOCK_NOT_FOUND("해당 종목을 찾지 못했습니다. 기존에 추가하신 종목이 밎는지 확인해주세요."), 
	USER_NAME_ALREADY_IN_USE("이미 사용중인 이름입니다."), 
	TOKEN_EXPIRED("인증 토큰의 유효기간이 만료되었습니다."), 
	PASSWORD_NOT_MATCHING("패스워드가 맞지 않습니다."), 
	;

	private final String errorMessage;
	
	private ErrorDesc(String errorDescription) {
		this.errorMessage = errorDescription;
	}
	public String getErrorMessage() {
		return errorMessage;
	}

}
