package com.alibaba.dingtalk.openapi.demo;

public class OApiException extends Exception {

    public static final int ERR_RESULT_RESOLUTION = -2;

    public OApiException(String field) {
        this(ERR_RESULT_RESOLUTION, "Cannot resolve field " + field + " from oapi resonpse");
    }

	public OApiException(int errCode, String errMsg) {
		super("error code: " + errCode + ", error message: " + errMsg);
	}
}
