package com.alibaba.dingtalk.openapi.demo.media;

import java.io.File;

import com.alibaba.dingtalk.openapi.demo.Env;
import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.OApiResultException;
import com.alibaba.dingtalk.openapi.demo.utils.HttpHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.open.client.ServiceFactory;
import com.dingtalk.open.client.api.model.corp.UploadResult;
import com.dingtalk.open.client.api.service.corp.MediaService;

public class MediaHelper {
	
	public static final String TYPE_IMAGE = "image";
	public static final String TYPE_VOICE = "voice";
	public static final String TYPE_VIDEO = "video";
	public static final String TYPE_FILE = "file";
	
	
	public static class MediaUploadResult {
		public String type;
		public String media_id;
		public String created_at;
	}
	

	public static UploadResult upload(String accessToken, String type, File file) throws Exception {
		
		MediaService mediaService = ServiceFactory.getInstance().getOpenService(MediaService.class);
		UploadResult uploadResult = mediaService.uploadMediaFile(accessToken, type, file);
		
		return uploadResult;
	}
	
	
	public static void download(String accessToken, String mediaId, String fileDir) throws Exception {
		
		String url = Env.OAPI_HOST + "/media/get?" +
				"access_token=" + accessToken + "&media_id="  + mediaId;
		JSONObject response = HttpHelper.downloadMedia(url, fileDir);
		System.out.println(response);
	}
}
