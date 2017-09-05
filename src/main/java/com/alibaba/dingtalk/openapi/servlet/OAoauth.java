package com.alibaba.dingtalk.openapi.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.dingtalk.openapi.demo.Env;
import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.auth.AuthHelper;
import com.alibaba.dingtalk.openapi.demo.user.UserHelper;
import com.alibaba.fastjson.JSONObject;

/**
 * 应用管理后台免登流程
 */
public class OAoauth extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String code = request.getParameter("code");
		if(code != null){
			try {
				String ssoToken = AuthHelper.getSsoToken();
				response.getWriter().append(ssoToken);
				JSONObject js = UserHelper.getAgentUserInfo(ssoToken, code);
				response.getWriter().append(js.toString());
			} catch (OApiException e) {
				e.printStackTrace();
			}
		}else{
		    // 免登成功后会跳转到redirect_url
			String reurl = "https://oa.dingtalk.com/omp/api/micro_app/admin/landing?corpid=" + 
			Env.CORP_ID + "&redirect_url=" + Env.OA_BACKGROUND_URL;//配置的OA后台地址
			response.addHeader("location", reurl);
			response.setStatus(302);
		}
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
