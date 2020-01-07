### 软件依赖
* java version "1.7"
* maven3

## Getting Started

1. 将工程clone到本地：`git clone https://github.com/open-dingtalk/openapi-demo-java.git`
2. 使用IDE导入工程，比如eclipse点击`File->import`(推荐使用maven导入), IDEA点击`File->New->Project from Existing Sources...`, 文件编码都是UTF-8
3. 打开工程的Env.java文件，填入企业的CORP_ID，APP_KEY，APP_SECRET（参考文档：https://open-doc.dingtalk.com/microapp/serverapi2/eev437）
```

    public static final String CORP_ID = "your CORP_ID";
    public static final String APP_KEY = "your APP_KEY";
    public static final String APP_SECRET = "your SECRET";
``` 
4. 部署工程，建议使用mvn -DskipTests=true jetty:run运行或者IDE中的maven插件运行
5. OA后台创建微应用，并把工程的首页地址/index.jsp填到微应用**首页地址**中。
[如何创建微应用？](http://ddtalk.github.io/dingTalkDoc/#step-2-创建微应用)


## DEMO具体实现

#### 1. jsapi权限验证配置流程

请查看[文档](http://ddtalk.github.io/dingTalkDoc/#页面引入js文件)
- 前端文件:WebContent/index.jsp，WebContent/javascripts/demo.js
- 后端文件

2.免登流程

请查看[文档](http://ddtalk.github.io/dingTalkDoc/#手机客户端微应用中调用免登)
- 前端文件:WebContent/javascripts/demo.js和
- 后端文件:[链接](https://github.com/open-dingtalk/openapi-demo-java/blob/master/src/com/alibaba/dingtalk/openapi/servlet/UserInfoServlet.java)


3.部门的操作
请查看[文档](http://ddtalk.github.io/dingTalkDoc/#管理通讯录)
- 后端文件：[链接](https://github.com/open-dingtalk/openapi-demo-java/blob/master/src/com/alibaba/dingtalk/openapi/demo/department)

4.员工的操作
请查看[文档](http://ddtalk.github.io/dingTalkDoc/#管理通讯录)
- 后端文件：[链接](https://github.com/open-dingtalk/openapi-demo-java/blob/master/src/com/alibaba/dingtalk/openapi/demo/user)

5.通讯录事件（比如用户的离职，部门的删除）回调
请查看[文档](http://ddtalk.github.io/dingTalkDoc/#通讯录及群会话变更事件回调接口)
- 后端文件：[链接](https://github.com/open-dingtalk/openapi-demo-java/blob/master/src/com/alibaba/dingtalk/openapi/servlet/EventChangeReceiveServlet.java)

6.发送消息
请查看[文档](http://ddtalk.github.io/dingTalkDoc/#发送普通会话消息)
- 后端文件：[链接](https://github.com/open-dingtalk/openapi-demo-java/blob/master/src/com/alibaba/dingtalk/openapi/demo/message)

