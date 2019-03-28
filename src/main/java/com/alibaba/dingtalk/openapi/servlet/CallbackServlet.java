package com.alibaba.dingtalk.openapi.servlet;

import com.alibaba.dingtalk.openapi.demo.Env;
import com.alibaba.dingtalk.openapi.demo.auth.AuthHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCallBackDeleteCallBackRequest;
import com.dingtalk.api.request.OapiCallBackRegisterCallBackRequest;
import com.dingtalk.api.response.OapiCallBackRegisterCallBackResponse;
import com.dingtalk.oapi.lib.aes.DingTalkEncryptor;
import com.dingtalk.oapi.lib.aes.Utils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * @Author: xiaoqian
 * @Date: 2019/3/25 上午10:34
 * @Description:
 */
public class CallbackServlet extends HttpServlet {

    /**
     * 创建套件后，验证回调URL创建有效事件
     */
    private static final String CHECK_URL = "check_url";

    /**
     * 通讯录用户增加事件
     */
    public static final String USER_ADD_ORG = "user_add_org";

    /**
     * 通讯录用户更改事件
     */
    public static final String USER_MODIFY_ORG =  "user_modify_org";

    public static final String CALLBACK_RESPONSE_SUCCESS = "success";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            DingTalkEncryptor dingTalkEncryptor = new DingTalkEncryptor(Env.TOKEN, Env.ENCODING_AES_KEY,
                    Env.CORP_ID);
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
            /**
             * 从post请求的body中获取回调信息的加密数据进行解密处理
             */
            JSONObject jsonObject = JSON.parseObject(sb.toString());
            String encryptMsg = jsonObject.getString("encrypt");
            String plainText = dingTalkEncryptor.getDecryptMsg(signature, timestamp, nonce, encryptMsg);
            JSONObject obj = JSON.parseObject(plainText);
            /**
             * 根据回调数据类型做不同的业务处理
             */
            String eventType = obj.getString("EventType");
            if (CHECK_URL.equals(eventType)) {
                System.out.println("验证回调URL创建有效事件:" + plainText);
            } else if (USER_ADD_ORG.equals(eventType)) {
                System.out.println("通讯录用户增加事件事件:" + plainText);
                //todo 实现相关业务逻辑
            } else if (USER_MODIFY_ORG.equals(eventType)) {
                System.out.println("通讯录用户更改事件:" + plainText);
                //todo 实现相关业务逻辑
            } else {
                //todo 其他类型事件处理
            }
            /**
             * 返回success的加密信息表示回调处理成功
             */
            Map<String, String> encryptedMap = dingTalkEncryptor.getEncryptedMap(CALLBACK_RESPONSE_SUCCESS, System.currentTimeMillis(), Utils.getRandomStr(8));
            response.getWriter().append(JSON.toJSONString(encryptedMap));
        } catch (Exception e) {
            /**
             * 失败的情况，应用的开发者应该通过告警感知，并干预修复
             */
            System.err.println(e.getMessage());
            System.out.println("process callback failed！");
        } finally {
            if (br != null) {
                br.close();
            }
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    public static void main(String[] args) throws Exception{
        /**
         * 先删除企业已有的回调
         */
        DingTalkClient client = new DefaultDingTalkClient(Env.DELETE_CALLBACK);
        OapiCallBackDeleteCallBackRequest request = new OapiCallBackDeleteCallBackRequest();
        request.setHttpMethod("GET");
        client.execute(request, AuthHelper.getAccessToken());

        /**
         * 重新为企业注册回调
         */
        client = new DefaultDingTalkClient(Env.REGISTER_CALLBACK);
        OapiCallBackRegisterCallBackRequest registerRequest = new OapiCallBackRegisterCallBackRequest();
        registerRequest.setUrl(Env.CALLBACK_URL_HOST + "/callback");
        registerRequest.setAesKey(Env.ENCODING_AES_KEY);
        registerRequest.setToken(Env.TOKEN);
        /**
         * 需要注册的回调事件
         * 参考 https://open-doc.dingtalk.com/microapp/serverapi2/skn8ld
         */
        registerRequest.setCallBackTag(Arrays.asList("user_add_org", "user_modify_org"));
        OapiCallBackRegisterCallBackResponse registerResponse = client.execute(registerRequest, AuthHelper.getAccessToken());
        if (registerResponse.isSuccess()) {
            System.out.println("回调注册成功了！！！");
        }
    }

}
