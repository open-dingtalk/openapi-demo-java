package com.alibaba.dingtalk.openapi.demo.user;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class User {
	public String userid;
	public String name;
	public boolean active;
	public String avatar;
	public List<Long> department;
	public String position;
	public String mobile;
	public String tel;
	public String workPlace;
	public String remark;
	public String email;
	public String jobnumber;
	public JSONObject extattr;
	public boolean isAdmin;
	public boolean isBoss;
	public String dingId;


	
	public User() {
	}
	
	public User(String userid, String name) {
		this.userid = userid;
		this.name = name;
	}
	
	@Override
	public String toString() {
		List<User> users;
		return "User[userid:" + userid + ", name:" + name + ", active:" + active + ", "
				+ "avatar:" + avatar + ", department:" + department +
				", position:" + position + ", mobile:" + mobile + ", email:" + email + 
				", extattr:" + extattr;
	}
}
