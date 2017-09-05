package com.alibaba.dingtalk.openapi.demo.message;

public class LinkMessage extends Message {
	
	public String messageUrl;
    public String picUrl;
    public String title;
    public String text;
	
	public LinkMessage(String messageUrl, String picUrl, String title, String text) {
		super();
		this.messageUrl = messageUrl;
		this.picUrl = picUrl;
		this.title = title;
		this.text = text;
	}
	
	@Override
	public String type() {
		return "link";
	}
}
