### 软件依赖
* java version "1.7"
* maven3

## Getting Started

1. 将工程clone到本地：`git clone https://github.com/open-dingtalk/openapi-demo-java.git`
2. 使用IDE导入工程，比如eclipse点击`File->import`(推荐使用maven导入), IDEA点击`File->New->Project from Existing Sources...`, 文件编码都是UTF-8
3. 打开工程的Env.java文件，填入企业的CORP_ID，APP_KEY，APP_SECRET（参考文档：https://ding-doc.dingtalk.com/doc#/faquestions/dcubhu）
```
    public static final String CORP_ID = "your CORP_ID";
    public static final String APP_KEY = "your APP_KEY";
    public static final String APP_SECRET = "your SECRET";
``` 
4. 部署工程，建议使用mvn -DskipTests=true jetty:run运行或者IDE中的maven插件运行
5. OA后台创建微应用，并把工程的首页地址/index.jsp填到微应用**首页地址**中。
[如何创建微应用？](https://ding-doc.dingtalk.com/doc#/bgb96b/aw3h75)

## DEMO具体实现

#### 1. jsapi权限验证配置流程

请查看[文档](https://ding-doc.dingtalk.com/doc#/dev/uwa7vs)
- 前端文件:WebContent/index.jsp，WebContent/javascripts/demo.js
- 后端文件

#### 2.免登流程
请查看[文档](https://ding-doc.dingtalk.com/doc#/dev/ep7bpq)
- 前端文件:WebContent/javascripts/demo.js和
- 后端文件:[链接](https://github.com/open-dingtalk/openapi-demo-java/blob/master/src/com/alibaba/dingtalk/openapi/servlet/UserInfoServlet.java)


#### 3.企业内部应用-服务端API-通讯录管理-部门管理
请查看[文档](https://ding-doc.dingtalk.com/doc#/serverapi2/dubakq)
- 后端文件：[链接](https://github.com/open-dingtalk/openapi-demo-java/tree/master/src/main/java/com/alibaba/dingtalk/openapi/demo/department)

#### 4.企业内部应用-服务端API-通讯录管理-用户管理
请查看[文档](https://ding-doc.dingtalk.com/doc#/serverapi2/ege851)
- 后端文件：[链接](https://github.com/open-dingtalk/openapi-demo-java/tree/master/src/main/java/com/alibaba/dingtalk/openapi/demo/user)
