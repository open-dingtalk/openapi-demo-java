package com.alibaba.dingtalk.openapi.demo.message;

import java.util.List;

/** 
  { 
    "message_url": "http://dingtalk.com", 
    "head": {
        "bgcolor": "FFCC0000"
    }, 
    "body": {
        "title": "标题", 
        "form": [
            {
                "key": "姓名", 
                "value": "张三"
            }, 
            {
                "key": "年龄", 
                "value": "30"
            }
        ], 
        "rich": {
            "num": "15.6", 
            "unit": "元"
        }, 
        "content": "大段文本", 
        "image": "@lADOAAGXIszazQKA", 
        "file_count": "3", 
        "author": "李四"
    }
 */
public class OAMessage extends Message {
	
	public String message_url;
	public Head head;
	public Body body;
	

	@Override
	public String type() {
		return "oa";
	}
	
	//content
	public static class Head {
		public String bgcolor;
	}
	
	public static class Body {
		public String title;
		public List<Form> form;
		public Rich rich;
		public String content;
		public String image;
		public String file_found;
		public String author;
		
		public static class Form {
			public String key;
			public String value;
		}
		
		public static class Rich {
			public String num;
			public String unit;
		}
	}
}
