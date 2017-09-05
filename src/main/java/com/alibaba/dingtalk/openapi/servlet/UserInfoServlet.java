package com.alibaba.dingtalk.openapi.servlet;

import com.alibaba.dingtalk.openapi.demo.auth.AuthHelper;
import com.alibaba.dingtalk.openapi.demo.user.UserHelper;
import com.alibaba.fastjson.JSON;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 根据免登授权码查询免登用户信息
 */
public class UserInfoServlet extends HttpServlet {


    /**
     * 根据免登授权码获取免登用户userId
     *
     * @param request
     * @param response
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取免登授权码
        String code = request.getParameter("code");
        String corpId = request.getParameter("corpid");
        System.out.println("authCode:" + code + " corpid:" + corpId);
        try {
            response.setContentType("text/html; charset=utf-8");

            String accessToken = AuthHelper.getAccessToken();
            System.out.println("access token:" + accessToken);
            CorpUserDetail user = UserHelper.getUser(accessToken, UserHelper.getUserInfo(accessToken, code).getUserid());

            String userJson = JSON.toJSONString(user);
            response.getWriter().append(userJson);
            System.out.println("userjson:" + userJson);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().append(e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

}
