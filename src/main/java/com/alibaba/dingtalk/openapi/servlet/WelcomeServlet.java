package com.alibaba.dingtalk.openapi.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: xiaoqian
 * @Date: 2019/3/22 下午3:35
 * @Description:
 */
public class WelcomeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.setContentType("text/html; charset=utf-8");
            response.getWriter().append("welcome!");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().append(e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
}
