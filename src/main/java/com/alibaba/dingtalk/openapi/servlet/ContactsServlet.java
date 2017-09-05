package com.alibaba.dingtalk.openapi.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.auth.AuthHelper;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;
import com.dingtalk.open.client.api.model.corp.CorpUserList;
import com.dingtalk.open.client.api.model.corp.Department;
import com.alibaba.dingtalk.openapi.demo.department.DepartmentHelper;
import com.alibaba.dingtalk.openapi.demo.user.User;
import com.alibaba.dingtalk.openapi.demo.user.UserHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 查询企业的通讯录
 */
public class ContactsServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException  {

		try {
			response.setContentType("text/html; charset=utf-8"); 

			String accessToken = AuthHelper.getAccessToken();
			
			List<Department> departments = new ArrayList<Department>();
			departments = DepartmentHelper.listDepartments(accessToken, "1");
			JSONObject json = new JSONObject();
			JSONArray usersArray = new JSONArray();
			
			System.out.println("depart num:"+departments.size());
			for(int i = 0;i<departments.size();i++){
				JSONObject userDepJSON = new JSONObject();
				
				JSONObject usersJSON = new JSONObject();
				JSONArray userArray = new JSONArray();
				
	            long offset = 0;
	            int size = 5;
	            CorpUserList corpUserList = new CorpUserList();	           
	            while (true) {
	                corpUserList = UserHelper.getDepartmentUser(accessToken, Long.valueOf(departments.get(i).getId())
	                		, offset, size, null);
	                System.out.println(JSON.toJSONString(corpUserList));
	                if (Boolean.TRUE.equals(corpUserList.isHasMore())) {
	                    offset += size;
	                } else {
	                    break;
	                }
	            }

				
//				System.out.println("dep:"+departments.get(i).toString());
//				CorpUserList users = new CorpUserList();
//				users = UserHelper.getDepartmentUser(accessToken,Long.valueOf(departments.get(i).getId()),
//						null, null, null);
				if(corpUserList.getUserlist().size()==0){
					continue;
				}
				for(int j = 0;j<corpUserList.getUserlist().size();j++){
					String user = JSON.toJSONString(corpUserList.getUserlist().get(j));
					userArray.add(JSONObject.parseObject(user, CorpUserDetail.class));
				}
				System.out.println("user:"+userArray.toString());
				usersJSON.put("name", departments.get(i).getName());
				usersJSON.put("member", userArray);
				usersArray.add(usersJSON);
			}
			json.put("department", usersArray);
			System.out.println("depart:"+json.toJSONString());
			response.getWriter().append(json.toJSONString());

		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().append(e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws IOException{
		doGet(request, response);
	}

}
