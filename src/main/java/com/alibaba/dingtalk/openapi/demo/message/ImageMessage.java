package com.alibaba.dingtalk.openapi.demo.message;


public class ImageMessage extends Message {
	
	public String media_id;
	
	public ImageMessage(String mediaId) {
		super();
		media_id = mediaId;
	}
	
	@Override
	public String type() {
		return "image";
	}
}
