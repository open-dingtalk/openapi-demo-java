package com.alibaba.dingtalk.openapi.demo.media;

import com.alibaba.dingtalk.openapi.demo.Env;
import com.alibaba.dingtalk.openapi.demo.utils.HttpHelper;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.open.client.ServiceFactory;
import com.dingtalk.open.client.api.model.corp.UploadResult;
import com.dingtalk.open.client.api.service.corp.MediaService;

import java.io.File;

/**
 * 管理多媒体文件
 * https://open-doc.dingtalk.com/docs/doc.htm?source=search&treeId=373&articleId=104971&docType=1
 */
public class MediaHelper {

    /**
     * 资源文件类型
     */
    public static final String TYPE_IMAGE = "image";
    public static final String TYPE_VOICE = "voice";
    public static final String TYPE_VIDEO = "video";
    public static final String TYPE_FILE = "file";


    public static class MediaUploadResult {
        public String type;
        public String media_id;
        public String created_at;
    }

    /**
     * 上传多媒体文件
     * <p>
     */
    public static UploadResult upload(String accessToken, String type, File file) throws Exception {

        MediaService mediaService = ServiceFactory.getInstance().getOpenService(MediaService.class);
        UploadResult uploadResult = mediaService.uploadMediaFile(accessToken, type, file);
        return uploadResult;
    }

    /**
     * 下载多媒体文件，目前sdk没有封装此接口，需要通过HTTP访问
     */
    public static void download(String accessToken, String mediaId, String fileDir) throws Exception {
        String url = Env.OAPI_HOST + "/media/downloadFile?" +
                "access_token=" + accessToken + "&media_id=" + mediaId;
        JSONObject response = HttpHelper.downloadMedia(url, fileDir);
        System.out.println(response);
    }
}
