package com.alibaba.dingtalk.openapi.demo.user;

import com.alibaba.dingtalk.openapi.demo.Env;
import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.utils.FileUtils;
import com.alibaba.dingtalk.openapi.demo.utils.HttpHelper;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.open.client.ServiceFactory;
import com.dingtalk.open.client.api.model.corp.CorpUserBaseInfo;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;
import com.dingtalk.open.client.api.model.corp.CorpUserDetailList;
import com.dingtalk.open.client.api.model.corp.CorpUserList;
import com.dingtalk.open.client.api.service.corp.CorpUserService;

import java.util.List;
import java.util.Map;

/**
 * 通讯录成员相关的接口调用
 */
public class UserHelper {


    /**
     * 根据免登授权码查询免登用户userId
     *
     * @param accessToken
     * @param code
     * @return
     * @throws Exception
     */
    public static CorpUserBaseInfo getUserInfo(String accessToken, String code) throws Exception {
        CorpUserService corpUserService = ServiceFactory.getInstance().getOpenService(CorpUserService.class);
        return corpUserService.getUserinfo(accessToken, code);
    }

    /**
     * 创建企业成员
     * <p>
     * https://open-doc.dingtalk.com/docs/doc.htm?treeId=385&articleId=106816&docType=1#s1
     */
    public static String createUser(String accessToken, CorpUserDetail userDetail) throws Exception {
        CorpUserService corpUserService = ServiceFactory.getInstance().getOpenService(CorpUserService.class);
        JSONObject js = (JSONObject) JSONObject.parse(userDetail.getOrderInDepts());
        Map<Long, Long> orderInDepts = FileUtils.toHashMap(js);

        String userId = corpUserService.createCorpUser(accessToken, userDetail.getUserid(), userDetail.getName(), orderInDepts,
                userDetail.getDepartment(), userDetail.getPosition(), userDetail.getMobile(), userDetail.getTel(), userDetail.getWorkPlace(),
                userDetail.getRemark(), userDetail.getEmail(), userDetail.getJobnumber(),
                userDetail.getIsHide(), userDetail.getSenior(), userDetail.getExtattr());

        // 员工唯一标识ID
        return userId;
    }


    /**
     * 更新成员
     * <p>
     * https://open-doc.dingtalk.com/docs/doc.htm?treeId=385&articleId=106816&docType=1#s2
     */
    public static void updateUser(String accessToken, CorpUserDetail userDetail) throws Exception {
        CorpUserService corpUserService = ServiceFactory.getInstance().getOpenService(CorpUserService.class);
        JSONObject js = (JSONObject) JSONObject.parse(userDetail.getOrderInDepts());
        Map<Long, Long> orderInDepts = FileUtils.toHashMap(js);

        corpUserService.updateCorpUser(accessToken, userDetail.getUserid(), userDetail.getName(), orderInDepts,
                userDetail.getDepartment(), userDetail.getPosition(), userDetail.getMobile(), userDetail.getTel(), userDetail.getWorkPlace(),
                userDetail.getRemark(), userDetail.getEmail(), userDetail.getJobnumber(),
                userDetail.getIsHide(), userDetail.getSenior(), userDetail.getExtattr());
    }


    /**
     * 删除成员
     */
    public static void deleteUser(String accessToken, String userid) throws Exception {
        CorpUserService corpUserService = ServiceFactory.getInstance().getOpenService(CorpUserService.class);
        corpUserService.deleteCorpUser(accessToken, userid);
    }


    //获取成员
    public static CorpUserDetail getUser(String accessToken, String userid) throws Exception {

        CorpUserService corpUserService = ServiceFactory.getInstance().getOpenService(CorpUserService.class);
        return corpUserService.getCorpUser(accessToken, userid);
    }

    //批量删除成员
    public static void batchDeleteUser(String accessToken, List<String> useridlist)
            throws Exception {
        CorpUserService corpUserService = ServiceFactory.getInstance().getOpenService(CorpUserService.class);
        corpUserService.batchdeleteCorpUserListByUserids(accessToken, useridlist);

    }


    //获取部门成员
    public static CorpUserList getDepartmentUser(
            String accessToken,
            long departmentId,
            Long offset,
            Integer size,
            String order)
            throws Exception {

        CorpUserService corpUserService = ServiceFactory.getInstance().getOpenService(CorpUserService.class);
        return corpUserService.getCorpUserSimpleList(accessToken, departmentId,
                offset, size, order);
    }


    //获取部门成员（详情）
    public static CorpUserDetailList getUserDetails(
            String accessToken,
            long departmentId,
            Long offset,
            Integer size,
            String order)
            throws Exception {

        CorpUserService corpUserService = ServiceFactory.getInstance().getOpenService(CorpUserService.class);
        return corpUserService.getCorpUserList(accessToken, departmentId,
                offset, size, order);
    }


    /**
     * 管理后台免登时通过CODE换取微应用管理员的身份信息
     *
     * @param ssoToken
     * @param code
     * @return
     * @throws OApiException
     */
    public static JSONObject getAgentUserInfo(String ssoToken, String code) throws OApiException {
        String url = Env.OAPI_HOST + "/sso/getuserinfo?" + "access_token=" + ssoToken + "&code=" + code;
        JSONObject response = HttpHelper.httpGet(url);
        return response;
    }

}
