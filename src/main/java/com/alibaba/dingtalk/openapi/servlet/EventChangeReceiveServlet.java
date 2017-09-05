package com.alibaba.dingtalk.openapi.servlet;

import com.alibaba.dingtalk.openapi.demo.Env;
import com.alibaba.dingtalk.openapi.demo.utils.aes.DingTalkEncryptException;
import com.alibaba.dingtalk.openapi.demo.utils.aes.DingTalkEncryptor;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * 企业通讯录回调地址实现<br/>
 *
 * 详细文档见:  https://open-doc.dingtalk.com/docs/doc.htm?treeId=385&articleId=104975&docType=1
 */
public class EventChangeReceiveServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**url中的签名**/
        String msgSignature = request.getParameter("signature");
        /**url中的时间戳**/
        String timeStamp = request.getParameter("timestamp");
        /**url中的随机字符串**/
        String nonce = request.getParameter("nonce");

        /**post数据包数据中的加密数据**/
        ServletInputStream sis = request.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(sis));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        JSONObject jsonEncrypt = JSONObject.parseObject(sb.toString());
        String encrypt = jsonEncrypt.getString("encrypt");

        // 对回调的参数进行解密，确保请求合法
        /**对encrypt进行解密**/
        DingTalkEncryptor dingTalkEncryptor = null;
        String plainText = null;
        try {
            // 根据用户注册的token和AES_KEY进行解密
            dingTalkEncryptor = new DingTalkEncryptor(Env.TOKEN, Env.ENCODING_AES_KEY, Env.CORP_ID);
            plainText = dingTalkEncryptor.getDecryptMsg(msgSignature, timeStamp, nonce, encrypt);
        } catch (DingTalkEncryptException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        /**对从encrypt解密出来的明文进行处理**/
        JSONObject plainTextJson = JSONObject.parseObject(plainText);
        String eventType = plainTextJson.getString("EventType");
        switch (eventType) {
            case "user_add_org"://通讯录用户增加 do something
                break;
            case "user_modify_org"://通讯录用户更改 do something
                break;
            case "user_leave_org"://通讯录用户离职  do something
                break;
            case "org_admin_add"://通讯录用户被设为管理员 do something
                break;
            case "org_admin_remove"://通讯录用户被取消设置管理员 do something
                break;
            case "org_dept_create"://通讯录企业部门创建 do something
                break;
            case "org_dept_modify"://通讯录企业部门修改 do something
                break;
            case "org_dept_remove"://通讯录企业部门删除 do something
                break;
            case "org_remove"://企业被解散 do something
                break;


            case "check_url"://do something
            default: //do something
                break;
        }

        /**对返回信息进行加密**/
        long timeStampLong = Long.parseLong(timeStamp);
        Map<String, String> jsonMap = null;
        try {
            jsonMap = dingTalkEncryptor.getEncryptedMap("success", timeStampLong, nonce);
        } catch (DingTalkEncryptException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        JSONObject json = new JSONObject();
        json.putAll(jsonMap);
        response.getWriter().append(json.toString());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
