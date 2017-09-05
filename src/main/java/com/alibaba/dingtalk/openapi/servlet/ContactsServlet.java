package com.alibaba.dingtalk.openapi.servlet;

import com.alibaba.dingtalk.openapi.demo.auth.AuthHelper;
import com.alibaba.dingtalk.openapi.demo.department.DepartmentHelper;
import com.alibaba.dingtalk.openapi.demo.user.UserHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;
import com.dingtalk.open.client.api.model.corp.CorpUserList;
import com.dingtalk.open.client.api.model.corp.Department;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询企业通讯录下的用户列表
 */
public class ContactsServlet extends HttpServlet {

    /**
     * 查看当前accessToken下的可以查询到的员工列表<br/>
     * <p>
     * 先查部门列表，然后再查部门下的所有人
     *
     * @param request
     * @param response
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            response.setContentType("text/html; charset=utf-8");
            String accessToken = AuthHelper.getAccessToken();

            List<Department> departments = new ArrayList<Department>();
            // 1表示部门根目录，如果获取accessToken的corpSecret设置了部门范围，需要更改成对应部门的id
            // 可以通过https://oapi.dingtalk.com/auth/scopes?access_token=ACCESS_TOKEN 查询部门id列表
            departments = DepartmentHelper.listDepartments(accessToken, "1");
            JSONObject json = new JSONObject();
            JSONArray usersArray = new JSONArray();

            System.out.println("depart num:" + departments.size());
            for (int i = 0; i < departments.size(); i++) {
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

                if (corpUserList.getUserlist().size() == 0) {
                    continue;
                }
                for (int j = 0; j < corpUserList.getUserlist().size(); j++) {
                    String user = JSON.toJSONString(corpUserList.getUserlist().get(j));
                    userArray.add(JSONObject.parseObject(user, CorpUserDetail.class));
                }
                System.out.println("user:" + userArray.toString());
                usersJSON.put("name", departments.get(i).getName());
                usersJSON.put("member", userArray);
                usersArray.add(usersJSON);
            }
            json.put("department", usersArray);
            System.out.println("depart:" + json.toJSONString());
            response.getWriter().append(json.toJSONString());

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().append(e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

}
