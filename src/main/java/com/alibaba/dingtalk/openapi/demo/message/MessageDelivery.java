package com.alibaba.dingtalk.openapi.demo.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.open.client.api.model.corp.MessageBody;

public class MessageDelivery {
	
	public String msgType;
	public MessageBody message;
	
	public MessageDelivery withMessage(String msgType, MessageBody msg) {
		this.msgType = msgType;
		this.message = msg;
		return this;
	}
}
