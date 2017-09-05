/**
 * Created by liqiao on 12/28/15.
 */
(function(win) {
  'use strict';
  //客户端事件
  var EVENTS = [
    'backbutton',
    'online',
    'offline',
    'pause',
    'resume',
    'swipeRefresh' //0.0.5
  ];
  //方法列表
  var METHODS = [
    'device.notification.alert',
    'device.notification.confirm',
    'device.notification.prompt',
    'device.notification.vibrate',
    'device.accelerometer.watchShake',
    'device.accelerometer.clearShake',
    'biz.util.open',
    'biz.util.openLink',
    'biz.util.share',
    'biz.util.ut',
    'biz.util.datepicker', //android
    'biz.util.timepicker', //android
    'biz.user.get',
    'biz.navigation.setLeft',
    'biz.navigation.setRight',
    'biz.navigation.setTitle',
    'biz.navigation.back',
    // v0.0.1
    'device.notification.toast',
    'device.notification.showPreloader',
    'device.notification.hidePreloader',
    'device.geolocation.get',
    'biz.util.uploadImage',
    'biz.util.previewImage',
    //v0.0.2
  /**
   * ios日期选择器
   * 'biz.util.textarea' 改为 ui.input.plain
   **/
    'ui.input.plain',
    'device.notification.actionSheet',
    'biz.util.qrcode',
    'device.connection.getNetworkType',
    'runtime.info',
    //v0.0.3
  /**
   * 发钉 biz.ding.post
   * 打电话 biz.telephone.call
   * 选群组 biz.chat.chooseConversation
   **/
    'biz.ding.post',
    'biz.telephone.call',
    'biz.chat.chooseConversation',
    //v0.0.4
  /**
   * 拉起联系人添加页面 biz.util.open  contactAdd
   * 消息会话+号企业群聊天 biz.contact.createGroup
   * select组件 biz.util.chosen
   * 日期+时间 biz.util.datetimepicker
   **/
    'biz.contact.createGroup',
    //'biz.util.chosen', //有bug，0.0.5版开启
    'biz.util.datetimepicker',
    //v0.0.5
  /**
   * 下拉组件iOS bugfix，上一版废弃
   * 获取通用唯一识别码
   * 获取热点接入信息
   * 检测应用是否安装
   * 启动第三方app
   * 设置导航进度条颜色
   * 新增事件 swipeRefresh
   * 请求授权码，免登改造用
   * 启用下拉刷新功能
   * 收起下拉刷新控件
   * 禁用下拉刷新功能
   * 启用webview下拉弹性效果
   * 禁用webview下拉弹性效果
   **/
    'biz.util.chosen', //下拉组件
    'device.base.getUUID', //获取通用唯一识别码
    'device.base.getInterface', //获取热点接入信息
    'device.launcher.checkInstalledApps', //检测应用是否安装
    'device.launcher.launchApp', //启动第三方app

    'ui.progressBar.setColors', //设置顶部进度条颜色
    'runtime.permission.requestAuthCode', //请求授权码，免登改造用
    'runtime.permission.requestJsApis', //轻轻jsapi，隐藏方法，只限sdk内部调用，TODO: 上线时注释掉

    'ui.pullToRefresh.enable', //启用下拉刷新功能
    'ui.pullToRefresh.stop', //收起下拉刷新控件
    'ui.pullToRefresh.disable', //禁用下拉刷新功能
    'ui.webViewBounce.enable', //启用webview下拉弹性效果
    'ui.webViewBounce.disable', //禁用webview下拉弹性效果

    //v0.0.6
  /**
   * 获取会话信息
   * 地图搜索
   * 地图定位
   * 扫码
   * 修改企业通讯录选人
   * 企业通讯录同时选人，选部门
   **/
    'biz.chat.getConversationInfo', //获取会话信息
    'biz.map.search', //地图搜索
    'biz.map.locate', //地图定位
    'biz.util.scan', //扫码
    'biz.contact.choose', //修改企业通讯录选人
    'biz.contact.complexChoose', //企业通讯录同时选人，选部门
    'util.localStorage.getItem', //本地存储读
    'util.localStorage.setItem', //本地存储写
    'util.localStorage.removeItem', //本地存储移除操作
    'biz.navigation.createEditor', //创建通用组件
    'biz.navigation.finishEditor', //销毁通用组件

    //v0.0.7
  /**
   **/
    'biz.chat.pickConversation', //选群组

    //0.0.8
    'device.notification.modal', //弹浮层
    'biz.navigation.setIcon', //设置导航icon
    'biz.navigation.close', //关闭webview
    'biz.util.uploadImageFromCamera',

    //0.0.9
    'internal.lwp.call',//lwp接口
    //0.1.0
    'device.geolocation.openGps',//打开设置，只有android有效
    'biz.util.test', //测试接口
    'internal.microapp.checkInstalled',//检测微应用是否安装
    'internal.user.getRole', //获取角色

    //0.1.1
    'device.notification.extendModal', //谈层，支持多张图片
    'internal.request.lwp', //lwp通道
    'biz.user.secretID', //获取用户登录唯一标识
    'internal.util.encryData', //参数加密生成key
    'biz.customContact.choose', //自定义选人组件
    'biz.customContact.multipleChoose', //自定义选人组件（多选）
    'biz.util.pageClick', //页面打点

    //0.1.2
    'biz.chat.chooseConversationByCorpId', //选择企业会话v2.6新增
    'biz.chat.toConversation', //跳转至对应的会话v2.6新增
    'biz.chat.open',//根据pickConversation获取的cid打开对应的聊天会话
    'device.base.getSettings', //获取手机设置待定
    'internal.log.upload', //上传日志v2.6新增

    'biz.navigation.goBack',//nav回退

    //nav导航
    'ui.nav.preload',
    'ui.nav.go',
    'ui.nav.recycle',
    'ui.nav.close',
    'ui.nav.getCurrentId',

    //消息
    'runtime.message.post',
    'runtime.message.fetch',

    'biz.navigation.setMenu',//设置导航菜单按钮

    //侧拉面板
    'ui.drawer.init',
    'ui.drawer.config',
    'ui.drawer.enable',
    'ui.drawer.disable',
    'ui.drawer.open',
    'ui.drawer.close',

    'biz.util.uploadAttachment',
    'biz.cspace.preview',
    'internal.hpm.get',
    'internal.hpm.update',
    'preRelease.appLink.open',
    'internal.request.getSecurityToken', //lwp通道
    'biz.clipboardData.setData',

    'ui.tab.init',
    'ui.tab.start',
    'ui.tab.select',
    'ui.tab.config'
  ];
  var JSSDK_VERSION = "1.0.0";
  var ua = win.navigator.userAgent;
  var matches = ua.match(/AliApp\(\w+\/([a-zA-Z0-9.-]+)\)/);
  //android兼容处理
  if (matches === null) {
    matches = ua.match(/DingTalk\/([a-zA-Z0-9.-]+)/);
  }
  var version = matches && matches[1];
  var authorised = false; //是否已校验通过
  var already = false; //是否已初始化
  var config = null; //缓存config参数
  var authMethod = 'runtime.permission.requestJsApis'; //权限校验方法名
  var errorHandle = null; //缓存error回调
  var bridgeReady = false;
  var dd = {
    ios: (/iPhone|iPad|iPod/i).test(ua),
    android: (/Android/i).test(ua),
    version: version,
    support: version === '1.2.2' || version === '1.3.2',
    ability: '', //空为初始值，具体值从客户端读取，格式为x.x.x
    config: function(obj) {
      //这里对用户传进来的参数进行过滤
      if (!obj) {
        return;
      }
      //TODO: 参数名待确认
      config = {
        corpId: obj.corpId,
        appId:obj.appId||-1,
        timeStamp: obj.timeStamp,
        nonceStr: obj.nonceStr,
        signature: obj.signature,
        jsApiList: obj.jsApiList
      };
      if(obj.agentId){
        config.agentId = obj.agentId;
      }
    },
    error: function(fn) {
      errorHandle = fn;
    },
    ready: function(callback) {
      //总控
      var fn = function(bridge) {
        if (!bridge) {
          return console.log('bridge初始化失败')
        }
        //callback(bridge);
        //TODO: 判断config，进行权限校验
        if (config === null||!config.signature) {
          //console.log('没有配置dd.config')
          callback(bridge);
        } else {
          //console.log('配置了dd.config', config)
          //权限校验
          if (dd.ios) {
            bridge.callHandler(authMethod, config, function(response) {
              var data = response || {};
              var code = data.errorCode;
              var msg = data.errorMessage || '';
              var result = data.result;
              if (code === '0') {
                callback(bridge);
              } else {
                setTimeout(function() {
                  errorHandle && errorHandle({
                    message: '权限校验失败 ' + msg,
                    errorCode: 3
                  });
                });
              }
            });
          } else if (dd.android) {
            var arr = authMethod.split('.');
            var suff = arr.pop();
            var pre = arr.join('.');
            bridge(function() {
              callback(bridge);
            }, function(err) {
              setTimeout(function() {
                var msg = err&&err.errorMessage || '';
                errorHandle && errorHandle({
                  message: '权限校验失败 ' + msg,
                  errorCode: 3
                });
              });
            }, pre, suff, config);
          }
        }
        //callback(bridge);
        //第一次初始化后要做的事情
        if (already === false) {
          already = true;
          //自定义事件
          EVENTS.forEach(function(evt) {
            if (dd.ios) {
              bridge.registerHandler(evt, function(data, responseCallback) {
                //console.log('注册事件默认回调', data, responseCallback);
                var e = document.createEvent('HTMLEvents');
                e.data = data;
                e.initEvent(evt);
                document.dispatchEvent(e);
                responseCallback && responseCallback({
                  errorCode: '0',
                  errorMessage: '成功'
                })
              });
            }
          });

          if (config === null) {
            var conf = {
              url: encodeURIComponent(window.location.href),
              js: JSSDK_VERSION,
              cid: config && config.corpId || ''
            };
            //打点
            dd.biz.util.ut({
              key: 'dd_open_js_monitor',
              value: JSON.stringify(conf),
              onSuccess: function(res) {
                //console.log('dd_open_js_monitor ut打点成功', res);
              }
            });
          }
        }
      };
      //已经完成初始化的情况
      if (dd.ios && win.WebViewJavascriptBridge) {
        //防止ready延迟导致的问题
        //init后，register的方法才能收到回调，重现方法：首次触发dd.ready延时
        try {
          WebViewJavascriptBridge.init(function(data, responseCallback) {
            //客户端send
            //console.log('WebViewJavascriptBridge init: ', data, responseCallback);
          });
        } catch (e) {
          console.log(e.message);
        }
        return fn(WebViewJavascriptBridge);
      } else if (dd.android && win.WebViewJavascriptBridgeAndroid) {
        return fn(WebViewJavascriptBridgeAndroid);
      }
      //初始化主流程
      if (dd.ios) {
        //console.log('开始监听WebViewJavascriptBridgeReady事件');
        document.addEventListener('WebViewJavascriptBridgeReady', function() {
          if (typeof WebViewJavascriptBridge === 'undefined') {
            return console.log('WebViewJavascriptBridge 未定义');
          }
          try {
            WebViewJavascriptBridge.init(function(data, responseCallback) {
              //客户端send
              //console.log('WebViewJavascriptBridge init: ', data, responseCallback);
            });
          } catch (e) {
            console.log(e.message);
          }
          bridgeReady = true;
          fn(WebViewJavascriptBridge);

        }, false);
      } else if (dd.android) {
        var _run = function() {
          try {
            win.WebViewJavascriptBridgeAndroid = win.nuva.require();
            bridgeReady = true;
            fn(WebViewJavascriptBridgeAndroid);
          } catch (e) {
            console.log('window.nuva.require出错', e.message);
            fn(null);
          }
        };
        //兼容
        if (win.nuva) {
          _run();
        } else {
          document.addEventListener('runtimeready', function() {
            _run();
          }, false);
        }
        //
      } else {
        return console.log('很抱歉，尚未支持您所持设备');
      }
    },
    type:function(obj){
      //"Array", "Boolean", "Date", "Number", "Object", "RegExp", "String", "Window" ,"Constructor"
      return Object.prototype.toString.call(obj).match(/^\[object\s(.*)\]$/)[1];
    },
    //版本号对比方法，比如判断1.5.0和1.11.0的大小
    /**
     * oldVersion 老版本
     * newVersion 新版本
     * containEqual 是否包含相等的情况
     * result: newVersion >[=] oldVersion
     **/
    compareVersion: function(oldVersion, newVersion, containEqual) {
      if (typeof oldVersion !== 'string' || typeof newVersion !== 'string') {
        return false;
      }
      //分割字符串为['1', '0', '1']格式
      var oldArray = oldVersion.split('.');
      var newArray = newVersion.split('.');
      var o, n;
      //从左向右对比值，值相同则跳过，不同则返回不同的值
      while (o === n && newArray.length > 0) {
        o = oldArray.shift();
        n = newArray.shift();
      }
      //返回不同值的比较结果
      if (containEqual) {
        return (n | 0) >= (o | 0);
      } else {
        return (n | 0) > (o | 0);
      }
    }
  };
  //注册命名空间,"device.notification.alert"生成dd.device.notification.alert
  var ns = function(method, fn) {
    var arr = method.split('.');
    var namespace = dd;
    for (var i = 0, k = arr.length; i < k; i++) {
      if (i === k - 1) {
        namespace[arr[i]] = fn;
      }
      if (typeof namespace[arr[i]] === 'undefined') {
        namespace[arr[i]] = {};
      }
      namespace = namespace[arr[i]];
    }
  };
  //设置默认属性
  function setDefaultValue(obj, defaults,flag) {
    for (var i in defaults) {
      if(flag){
        obj[i] = defaults[i];
      }else{
        obj[i] = obj[i] !== undefined ? obj[i] : defaults[i];
      }
    }
  }
  //生成器，处理传参、回调以及对特定方法特殊处理
  function generator(method, param) {
    //门神位置
    if (typeof WebViewJavascriptBridge === 'undefined') {
      return console.log('WebViewJavascriptBridge未定义，请在钉钉app打开该页面');
    }
    //开始干活
    //console.log('调用方法：', method, '传参：', param);
    var p = param || {};
    var successCallback = function(res) {
      console.log('默认成功回调', method, res);
    };
    var failCallback = function(err) {
      console.log('默认失败回调', method, err)
    };
    var cancelCallback = function() {};
    if (p.onSuccess) {
      successCallback = p.onSuccess;
      delete p.onSuccess;
    }
    if (p.onFail) {
      failCallback = p.onFail;
      delete p.onFail;
    }
    if (p.onCancel) {
      cancelCallback = p.onCancel;
      delete p.onCancel;
    }
    //统一回调处理
    var callback = function(response) {
      //console.log('统一响应：', response);
      var data = response || {};
      var code = data.errorCode;
      var result = data.result;
      if (code === '0') {
        successCallback && successCallback.call(null, result);
      } else if (code === '-1') {
        cancelCallback && cancelCallback.call(null, result);
      } else {
        failCallback && failCallback.call(null, result,code);
      }
    };
    var watch = false; //是否为监听操作， 如果是监听操作，后面要注册事件
    //前端包装
    switch (method) {
      case 'device.notification.alert':
        setDefaultValue(p, {
          title: '',
          buttonName: '确定'
        });
        break;
      case 'device.notification.confirm':
      case 'device.notification.prompt':
        setDefaultValue(p, {
          title: '',
          buttonLabels: ['确定', '取消']
        });
        break;
      case 'device.notification.vibrate':
        setDefaultValue(p, {
          duration: 300
        });
        break;
      //监听类方法加watch标记，和iOS客户端约定通过js注册事件的方式实现
      case 'device.accelerometer.watchShake':
        if (dd.ios) {
          watch = true;
          p.sensitivity = 3.2; //ios计算的值有偏差，特殊处理
        }
        break;
      case 'biz.util.openLink':
        setDefaultValue(p, {
          credible: true,
          showMenuBar: true
        });
        break;
      case 'biz.contact.choose':
        setDefaultValue(p, {
          multiple: true,
          startWithDepartmentId: 0,
          users: [],
          corpId: (config && config.corpId) || ''
        });
        break;
      case 'biz.contact.complexChoose':
        setDefaultValue(p, {
          startWithDepartmentId: 0,
          selectedUsers: [],
          selectedDepartments: [],
          corpId: (config && config.corpId) || ''
        });
        break;
      case 'biz.navigation.setLeft':
      case 'biz.navigation.setRight':
        if (dd.ios) {
          watch = true;
        }
        //默认值
        setDefaultValue(p, {
          show: true,
          control: false,
          showIcon: true,
          text: ''
        });
        break;
      case 'ui.pullToRefresh.enable':
        if (dd.ios) {
          watch = true;
        }
        break;
      case 'device.notification.toast':
        setDefaultValue(p, {
          text: 'toast',
          duration: (dd.android ? ((dd.duration - 3 > 0) + 0) : 3), //android设备只有大于3s和小于等于3s两种选择; iOS默认3s
          delay: 0
        });
        break;
      case 'device.notification.showPreloader':
        setDefaultValue(p, {
          text: '加载中...',
          showIcon: true
        });
        break;
      case 'biz.util.uploadImage':
        setDefaultValue(p, {
          multiple: false
        });
        break;
      case 'biz.util.scan':
        setDefaultValue(p, {
          type: 'qrCode'
        });
        break;
      case 'biz.map.search':
        setDefaultValue(p, {
          scope: 500,
        });
        break;
      case 'biz.util.ut':
        var tempValue = p.value;
        var tempStr=[];
        if(tempValue&&dd.type(tempValue)=='Object'&&window.JSON){
          if(dd.ios){
            tempValue = JSON.stringify(tempValue);
          }else if(dd.android){
            for(var i in tempValue){
              tempStr.push(i+"="+tempValue[i]);
            }
            tempValue = tempStr.join(',');
          }
        }else if(!window.JSON){
          tempValue = '';
        }


        setDefaultValue(p, {
          value: tempValue
        },true);
        break;
      case 'internal.util.encryData':
        var str = p.data;
        var tempStr=[];
        if(dd.type(str)=='Object'){
          for(var i in str){
            tempStr.push(i+"="+str[i]);
          }
          str = tempStr.join('&');
        }
        setDefaultValue(p, {
          data: str
        },true);
        break;
      case 'internal.request.lwp':
        var str = p.body;
        str = JSON.stringify(str);

        setDefaultValue(p, {
          body: str
        },true);
        break;
      case 'biz.navigation.setIcon':
        if (dd.ios) {
          watch = true;
        }
        //默认值
        setDefaultValue(p, {
          showIcon: true,
          iconIndex:'1'
        });
        break;
      case 'biz.customContact.multipleChoose':
      case 'biz.customContact.choose':
        //默认值
        setDefaultValue(p, {
          isShowCompanyName: false
        });
        break;
      case 'biz.navigation.setMenu':
        if (dd.ios) {
          watch = true;
        }
        break;
    }

    //消息接入：android和iOS区分处理
    if (dd.android) {
      var arr = method.split('.');
      var suff = arr.pop();
      var pre = arr.join('.');
      //console.log('Android对接：', pre, suff, p);
      //WebViewJavascriptBridgeAndroid(successCallback, failCallback, pre, suff, p);
      //console.log(successCallback, failCallback, pre, suff, p);

      var params = p || {};
      params.onSuccess = successCallback;
      params.onFail = failCallback;
      try {
        window.nuva.require(pre)[suff](params);
      } catch (e) {
        console.log(e);
      }
    } else if (dd.ios) {
      //console.log(method, p, callback)
      if (watch) {
        WebViewJavascriptBridge.registerHandler(method, function(data, responseCallback) {
          callback({
            errorCode: '0',
            errorMessage: '成功',
            result: data
          });
          //回传给客户端，可选
          responseCallback && responseCallback({
            errorCode: '0',
            errorMessage: '成功'
          });
        });
        WebViewJavascriptBridge.callHandler(method, p);
      } else {
        WebViewJavascriptBridge.callHandler(method, p, callback);
      }
    }
  }
  //动态生成api
  METHODS.forEach(function(method) {
    ns(method, function(param) {
      generator(method, param);
    });
  });

  dd.__ns = ns;

  dd.biz.util.pageClick = function(obj){
    var k = 'open_micro_log_record_client';
    var visitTime = +new Date();
    var corpId = obj.corpId;
    var agentId = obj.agentId;
    if(!corpId){
      corpId = (config&&config.corpId)||'';
    }
    if (!agentId) {
      agentId = (config&&config.agentId)||'';
    };

    var defaultObj = {
      visitTime:visitTime,
      clickType:2,
      clickButton:obj.clickButton||'',
      url:location.href,
      corpId:corpId,
      agentId:agentId
    };
    dd.biz.util.ut({
      key:k,
      value:defaultObj
    });
  }

  win.dd = dd;

  //国际范儿
  if (typeof module === 'object' && module && typeof module.exports === 'object') {
    module.exports = dd;
  } else if (typeof define === 'function' && (define.amd || define.cmd)) {
    define(function() {
      return dd;
    })
  }
}(this));