package com.alibaba.dingtalk.openapi.demo.eventchange;

import com.alibaba.dingtalk.openapi.demo.Env;
import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.utils.HttpHelper;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * 通讯录回调相关事件
 * <p>
 * https://open-doc.dingtalk.com/docs/doc.htm?treeId=371&articleId=104975&docType=1
 */
public class eventChangeHelper {

    /**
     * 注册事件回调接口
     */
    public static String registerEventChange(String accessToken, List<String> callBackTag, String token, String aesKey, String url) throws OApiException {
        String signUpUrl = Env.OAPI_HOST + "/call_back/register_call_back?" +
                "access_token=" + accessToken;
        JSONObject args = new JSONObject();
        args.put("call_back_tag", callBackTag);
        args.put("token", token);
        args.put("aes_key", aesKey);
        args.put("url", url);

        JSONObject response = HttpHelper.httpPost(signUpUrl, args);
        if (response.containsKey("errcode")) {
            return response.getString("errcode");
        } else {
            return null;
        }
    }

    //查询事件回调接口
    public static String getEventChange(String accessToken) throws OApiException {
        String url = Env.OAPI_HOST + "/call_back/get_call_back?" +
                "access_token=" + accessToken;
        JSONObject response = HttpHelper.httpGet(url);
        return response.toString();
    }

    //更新事件回调接口
    public static String updateEventChange(String accessToken, List<String> callBackTag, String token, String aesKey, String url) throws OApiException {
        String signUpUrl = Env.OAPI_HOST + "/call_back/update_call_back?" +
                "access_token=" + accessToken;
        JSONObject args = new JSONObject();
        args.put("call_back_tag", callBackTag);
        args.put("token", token);
        args.put("aes_key", aesKey);
        args.put("url", url);

        JSONObject response = HttpHelper.httpPost(signUpUrl, args);
        if (response.containsKey("errcode")) {
            return response.getString("errcode");
        } else {
            return null;
        }
    }

    //删除事件回调接口
    public static String deleteEventChange(String accessToken) throws OApiException {
        String url = Env.OAPI_HOST + "/call_back/delete_call_back?" +
                "access_token=" + accessToken;
        JSONObject response = HttpHelper.httpGet(url);
        return response.toString();
    }


    public static String getFailedResult(String accessToken) throws OApiException {
        String url = Env.OAPI_HOST + "/call_back/get_call_back_failed_result?" +
                "access_token=" + accessToken;
        JSONObject response = HttpHelper.httpGet(url);
        return response.toString();
    }


}
