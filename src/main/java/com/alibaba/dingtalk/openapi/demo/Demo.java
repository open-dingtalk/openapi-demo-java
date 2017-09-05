package com.alibaba.dingtalk.openapi.demo;

import com.alibaba.dingtalk.openapi.demo.auth.AuthHelper;
import com.alibaba.dingtalk.openapi.demo.department.DepartmentHelper;
import com.alibaba.dingtalk.openapi.demo.media.MediaHelper;
import com.alibaba.dingtalk.openapi.demo.message.LightAppMessageDelivery;
import com.alibaba.dingtalk.openapi.demo.message.MessageHelper;
import com.alibaba.dingtalk.openapi.demo.user.UserHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.open.client.api.model.corp.*;
import com.dingtalk.open.client.api.model.corp.MessageBody.OABody.Body;
import com.dingtalk.open.client.api.model.corp.MessageBody.OABody.Body.Form;
import com.dingtalk.open.client.api.model.corp.MessageBody.OABody.Body.Rich;
import com.dingtalk.open.client.api.model.corp.MessageBody.OABody.Head;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 本地测试方法钉钉API
 */
public class Demo {

    public static String TO_USER = "";
    public static String TO_PARTY = "";
    public static String AGENT_ID = "";

    public static void main(String[] args) throws Exception {

        try {

            List<Department> departments = new ArrayList<Department>();
            departments = DepartmentHelper.listDepartments(AuthHelper.getAccessToken(), "1");
            JSONObject usersJSON = new JSONObject();

            System.out.println("depart num:" + departments.size());
            for (int i = 0; i < departments.size(); i++) {
                JSONObject userDepJSON = new JSONObject();
                System.out.println("dep:" + departments.get(i).toString());

                long offset = 0;
                int size = 5;
                CorpUserList corpUserList = new CorpUserList();
                while (true) {
                    corpUserList = UserHelper.getDepartmentUser(AuthHelper.getAccessToken(), Long.valueOf(departments.get(i).getId())
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
                    userDepJSON.put(j + "", JSONObject.parseObject(user, CorpUserDetail.class));

                }


                usersJSON.put(departments.get(i).getName(), userDepJSON);
                System.out.println("user:" + usersJSON.toString());
            }

            System.out.println("depart:" + usersJSON.toJSONString());


            // 获取access token
            String accessToken = AuthHelper.getAccessToken();
            log("成功获取access token: ", accessToken);

            // 获取jsapi ticket
            String ticket = AuthHelper.getJsapiTicket(accessToken);
            log("成功获取jsapi ticket: ", ticket);

            // 获取签名
            String nonceStr = "nonceStr";
            long timeStamp = System.currentTimeMillis();
            String url = "http://www.dingtalk.com";
            String signature = AuthHelper.sign(ticket, nonceStr, timeStamp, url);
            log("成功签名: ", signature);

            // 创建部门
            String name = "TestDept.34";
            String parentId = "1";
            String order = "1";
            boolean createDeptGroup = true;
            long departmentId = Long.parseLong(DepartmentHelper.createDepartment(accessToken, name, parentId, order, createDeptGroup));
            log("成功创建部门", name, " 部门id=", departmentId);

            // 获取部门列表
            List<Department> list = DepartmentHelper.listDepartments(accessToken, parentId);
            log("成功获取部门列表", list);

            // 更新部门
            name = "hahahaha";
            boolean autoAddUser = true;
            String deptManagerUseridList = null;
            boolean deptHiding = false;
            String deptPerimits = "aa|qq";
            DepartmentHelper.updateDepartment(accessToken, departmentId, name, parentId, order, createDeptGroup,
                    autoAddUser, deptManagerUseridList, deptHiding, deptPerimits, null,
                    null, null, null, null);


            log("成功更新部门", " 部门id=", departmentId);

            CorpUserDetail userDetail = new CorpUserDetail();
            userDetail.setUserid("id_yuhuan");
            userDetail.setName("name_yuhuan");
            userDetail.setEmail("yuhuan@abc.com");
            userDetail.setMobile("18612124567");
            userDetail.setDepartment(new ArrayList());
            userDetail.getDepartment().add(departmentId);


            UserHelper.createUser(accessToken, userDetail);
            log("成功创建成员", "成员信息=", userDetail);

            // 上传图片
            File file = new File("/Users/ian/Downloads/lALOAVYgbc0DIM0Bwg_450_800.png");
            UploadResult uploadResult = MediaHelper.upload(accessToken, MediaHelper.TYPE_IMAGE, file);
            log("成功上传图片", uploadResult);

            // 下载图片
            String fileDir = "/Users/ian/Desktop/";
            MediaHelper.download(accessToken, uploadResult.getMedia_id(), fileDir);
            log("成功下载图片");


            MessageBody.TextBody textBody = new MessageBody.TextBody();
            textBody.setContent("TextMessage");

            MessageBody.ImageBody imageBody = new MessageBody.ImageBody();
            imageBody.setMedia_id(uploadResult.getMedia_id());

            MessageBody.LinkBody linkBody = new MessageBody.LinkBody();
            linkBody.setMessageUrl("http://www.baidu.com");
            linkBody.setPicUrl("@lALOACZwe2Rk");
            linkBody.setTitle("Link Message");
            linkBody.setText("This is a link message");

            // 创建oa消息
            MessageBody.OABody oaBody = new MessageBody.OABody();
            oaBody.setMessage_url("message_url");
            Head head = new Head();
            head.setText("head.text");
            head.setBgcolor("FFBBBBBB");
            oaBody.setHead(head);

            Body body = new Body();
            body.setAuthor("author");
            body.setContent("content");
            body.setFile_count("file_count");
            body.setImage("@image");
            body.setTitle("body.title");
            Rich rich = new Rich();
            rich.setNum("num");
            rich.setUnit("unit");
            body.setRich(rich);
            List<Form> formList = new ArrayList<Form>();
            Form form = new Form();
            form.setKey("key");
            form.setValue("value");
            formList.add(form);
            body.setForm(formList);
            oaBody.setBody(body);

            // 发送微应用消息
            String toUsers = TO_USER;
            String toParties = TO_PARTY;
            String agentId = AGENT_ID;
            LightAppMessageDelivery lightAppMessageDelivery = new LightAppMessageDelivery(toUsers, toParties, agentId);

            lightAppMessageDelivery.withMessage(MessageType.TEXT, textBody);
            MessageHelper.send(accessToken, lightAppMessageDelivery);
            log("成功发送 微应用文本消息");
            lightAppMessageDelivery.withMessage(MessageType.IMAGE, imageBody);
            MessageHelper.send(accessToken, lightAppMessageDelivery);
            log("成功发送 微应用图片消息");
            lightAppMessageDelivery.withMessage(MessageType.LINK, linkBody);
            MessageHelper.send(accessToken, lightAppMessageDelivery);
            log("成功发送 微应用link消息");
            lightAppMessageDelivery.withMessage(MessageType.OA, oaBody);
            MessageHelper.send(accessToken, lightAppMessageDelivery);
            log("成功发送 微应用oa消息");

            // 发送会话消息
//			String sender = Vars.SENDER;
//			String cid = Vars.CID;//cid需要通过jsapi获取，具体详情请查看开放平台文档--->客户端文档--->会话

//			ConversationMessageDelivery conversationMessageDelivery = new ConversationMessageDelivery(sender, cid,
//					agentId);
//
//			conversationMessageDelivery.withMessage(MessageType.TEXT, textBody);
//			MessageHelper.send(accessToken, conversationMessageDelivery);
//			log("成功发送 会话文本消息");
//			conversationMessageDelivery.withMessage(MessageType.IMAGE, imageBody);
//			MessageHelper.send(accessToken, conversationMessageDelivery);
//			log("成功发送 会话图片消息");
//			conversationMessageDelivery.withMessage(MessageType.LINK, linkBody);
//			MessageHelper.send(accessToken, conversationMessageDelivery);
//			log("成功发送 会话link消息");

            // 更新成员
            userDetail.setMobile("11177776666");
            UserHelper.updateUser(accessToken, userDetail);
            log("成功更新成员", "成员信息=", userDetail);

            // 获取成员
            CorpUserDetail userDetail11 = UserHelper.getUser(accessToken, userDetail.getUserid());
            log("成功获取成员", "成员userid=", userDetail11.getUserid());

            // 获取部门成员
            CorpUserList userList = UserHelper.getDepartmentUser(accessToken, departmentId, null, null, null);
            log("成功获取部门成员", "部门成员user=", userList.getUserlist());

            // 获取部门成员（详情）
            CorpUserDetailList userList2 = UserHelper.getUserDetails(accessToken, departmentId, null, null, null);
            log("成功获取部门成员详情", "部门成员详情user=", userList2.getUserlist());

            // 批量删除成员
//			User user2 = new User("id_yuhuan2", "name_yuhuan2");
//			user2.email = "yuhua2n@abc.com";
//			user2.mobile = "18611111111";
//			user2.department = new ArrayList();
//			user2.department.add(departmentId);
//			UserHelper.createUser(accessToken, user2);

            CorpUserDetail userDetail2 = new CorpUserDetail();
            userDetail2.setUserid("id_yuhuan2");
            userDetail2.setName("name_yuhuan2");
            userDetail2.setEmail("yuhua2n@abc.com");
            userDetail2.setMobile("18612124926");
            userDetail2.setDepartment(new ArrayList());
            userDetail2.getDepartment().add(departmentId);
            UserHelper.createUser(accessToken, userDetail2);


            List<String> useridlist = new ArrayList();
            useridlist.add(userDetail.getUserid());
            useridlist.add(userDetail2.getUserid());
            UserHelper.batchDeleteUser(accessToken, useridlist);
            log("成功批量删除成员", "成员列表useridlist=", useridlist);

            // 删除成员
//			User user3 = new User("id_yuhuan3", "name_yuhuan3");
//			user3.email = "yuhua2n@abc.com";
//			user3.mobile = "18611111111";
//			user3.department = new ArrayList();
//			user3.department.add(departmentId);
            CorpUserDetail userDetail3 = new CorpUserDetail();
            userDetail3.setUserid("id_yuhuan3");
            userDetail3.setName("name_yuhuan3");
            userDetail3.setMobile("13146654734");
            userDetail3.setDepartment(new ArrayList());
            userDetail3.getDepartment().add(departmentId);


            UserHelper.createUser(accessToken, userDetail3);
            UserHelper.deleteUser(accessToken, userDetail3.getUserid());
            log("成功删除成员", "成员userid=", userDetail3.getUserid());

            // 删除部门
            DepartmentHelper.deleteDepartment(accessToken, departmentId);
            log("成功删除部门", " 部门id=", departmentId);

        } catch (OApiException e) {
            e.printStackTrace();
        }
    }

    private static void log(Object... msgs) {
        StringBuilder sb = new StringBuilder();
        for (Object o : msgs) {
            if (o != null) {
                sb.append(o.toString());
            }
        }
        System.out.println(sb.toString());
    }
}
