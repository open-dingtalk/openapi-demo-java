package com.alibaba.dingtalk.openapi.demo;

public class OApiResultException extends OApiException {

	public static final int ERR_RESULT_RESOLUTION = -2;
	
	public OApiResultException(String field) {
		super(ERR_RESULT_RESOLUTION, "Cannot resolve field " + field + " from oapi resonpse");
	}

}
