package com.alibaba.dingtalk.openapi.demo.service;

import com.alibaba.dingtalk.openapi.demo.Env;
import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.utils.HttpHelper;
import com.alibaba.fastjson.JSONObject;


public class ServiceHelper {

	
	public static String getSuiteToken(String suite_key, String suite_secret,String suite_ticket){
		String url = Env.OAPI_HOST + "/service/get_suite_token";
		JSONObject json = new JSONObject();
		json.put("suite_key", suite_key);
		json.put("suite_secret", suite_secret);
		json.put("suite_ticket", suite_ticket);
		JSONObject reponseJson = null;
		String suiteAccessToken = null;
		try {
			reponseJson = HttpHelper.httpPost(url,json);
			suiteAccessToken = reponseJson.getString("suite_access_token");
		} catch (OApiException e) {
			e.printStackTrace();
		}
		return suiteAccessToken;
	}
	
	public static JSONObject getPermanentCode(String tmp_auth_cod, String suiteAccessToken ){
		String url = Env.OAPI_HOST + "/service/get_permanent_code?suite_access_token=" + suiteAccessToken ;
		JSONObject json = new JSONObject();
		json.put("tmp_auth_code", tmp_auth_cod);
		JSONObject reponseJson = null;
		try {
			reponseJson = HttpHelper.httpPost(url,json);
		} catch (OApiException e) {
			e.printStackTrace();
		}
		return reponseJson;		
	}
	
	public static String getCorpToken(String auth_corpid, String permanent_code, String suiteAccessToken ){
		String url = Env.OAPI_HOST + "/service/get_corp_token?suite_access_token=" + suiteAccessToken ;
		JSONObject json = new JSONObject();
		json.put("auth_corpid", auth_corpid);
		json.put("permanent_code", permanent_code);
		JSONObject reponseJson = null;
		String corpToken = null;
		try {
			reponseJson = HttpHelper.httpPost(url,json);
			corpToken = reponseJson.getString("access_token");
			
		} catch (OApiException e) {
			e.printStackTrace();
		}
		return corpToken;		
	}
	
	public static JSONObject getAuthInfo(String suiteAccessToken, String suite_key, String auth_corpid, String permanent_code){
		String url = Env.OAPI_HOST + "/service/get_auth_info?suite_access_token=" + suiteAccessToken ;
		JSONObject json = new JSONObject();
		json.put("suite_key", suite_key);
		json.put("auth_corpid", auth_corpid);
		json.put("permanent_code", permanent_code);

		JSONObject reponseJson = null;
		try {
			reponseJson = HttpHelper.httpPost(url,json);
		} catch (OApiException e) {
			e.printStackTrace();
		}
		return reponseJson;		
	}
	
	public static JSONObject getAgent(String suiteAccessToken, String suite_key, String auth_corpid, String permanent_code, String agentid){
		String url = Env.OAPI_HOST + "/service/get_agent?suite_access_token=" + suiteAccessToken ;
		JSONObject json = new JSONObject();
		json.put("suite_key", suite_key);
		json.put("auth_corpid", auth_corpid);
		json.put("permanent_code", permanent_code);
		json.put("agentid", agentid);//agentid可以通过getAuthInfo返回的json中得到

		JSONObject reponseJson = null;
		try {
			reponseJson = HttpHelper.httpPost(url,json);
		} catch (OApiException e) {
			e.printStackTrace();
		}
		return reponseJson;		
	}
	

	public static JSONObject getActivateSuite(String suiteAccessToken, String suite_key, String auth_corpid, String permanent_code ){
		String url = Env.OAPI_HOST + "/service/activate_suite?suite_access_token=" + suiteAccessToken ;
		JSONObject json = new JSONObject();
		json.put("suite_key", suite_key);
		json.put("auth_corpid", auth_corpid);
		json.put("permanent_code", permanent_code);

		JSONObject reponseJson = null;
		try {
			reponseJson = HttpHelper.httpPost(url,json);
		} catch (OApiException e) {
			e.printStackTrace();
		}
		return reponseJson;		
	}
	
	
	
	
}
