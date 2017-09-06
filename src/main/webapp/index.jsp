<!DOCTYPE html>
<%@ page language="java" import="java.util.*" contentType="text/html;charset=utf-8"%>
<html>
<head>
<meta http-equiv=Content-Type content="text/html;charset=utf-8">
<meta charset="gbk">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta content="yes" name="apple-mobile-web-app-capable"/>
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection"/>
<meta content="yes" name="apple-touch-fullscreen"/>
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" />
<link type="text/css" rel="stylesheet" href="stylesheets/style.css" />
    <style>
        body {
            background-color: white;
        }
        *{
		padding: 0;
		margin: 0;
		}
		ul {
			list-style: none
		}
		li{
			height: 70px;
			padding: 10px;
			border-bottom: 1px solid #ccc;
			vertical-align: middle;
		}
        
        .icon img {
		height: 70px;
		width: 70px;
		}
		.icon {
			display: inline-block;
			vertical-align: middle;
			/*border: 1px solid #00ff00;*/
		}
		.text {
		margin-left: 10px;
		width: calc(100% - 50px);
		display: inline-block;
		text-align: left;
		line-height: 70px;
		vertical-align: middle;
		} 
    </style>

<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">

<title>企业开发者首页</title>
<script type="text/javascript">

//在此拿到jsAPI权限验证配置所需要的信息
var _config = <%= com.alibaba.dingtalk.openapi.demo.auth.AuthHelper.getConfig(request) %>;

</script>
<script type="text/javascript" src="javascripts/zepto.min.js"></script>
<script type="text/javascript" src="http://g.alicdn.com/dingding/open-develop/1.6.9/dingtalk.js">
</script>
<script type="text/javascript" src="javascripts/logger.js">
</script>
// 免登相关代码
<script type="text/javascript" src="javascripts/demo.js">
 
</script>
<script>


</script>

</head>

<body >
	<div align="center">
		<img id ="userImg" alt="头像" src="./nav/default.png">
	</div>
	<div align="center">
		<span>UserName:</span>
		<div id="userName" style="display:inline-block"></div>
	</div >
	<div align="center">
		<span>UserId:</span>
		<div id="userId" style="display:inline-block"></div>
	</div>
	<br>
	<!-- <div style="padding-left:10px;">&nbsp;&nbsp;&nbsp;&nbsp;欢迎您：<div id="userName" style="display:inline-block;font-weight:bold"></div>&nbsp;成为钉钉开发者，您当前在钉钉的<code>userId</code>为：
		<div id="userId" style="display:inline-block;font-weight:bold"></div> 。</div> -->
	<div style="padding-left:10px;">&nbsp;&nbsp;&nbsp;&nbsp;我们为您提供了文档＋<code>api</code>帮助您快速的开发微应用并接入钉钉。</div>
	<br>
 <ul>
<li>
	<div class="icon"><img src="list/num11.png" style="width: 25px; height: 25px"></div>
	<div class="text">企业接入指南</div>
</li>
<!-- <li>
	<div class="icon"><img src="list/heart2.png"></div>
	<div class="text">企业授权</div>
</li>
<li>
	<div class="icon"><img src="list/heart3.png"></div>
	<div class="text">企业解授权</div>
</li> -->
<li>
	<div class="icon"><img src="list/num2.png" style="width: 25px; height: 25px"></div>
	<div class="text">使用JSAPI</div>
</li>
<li>
	<div class="icon"><img src="list/num3.png" style="width: 25px; height: 25px"></div>
    <div class="text">List展示（当前仅支持Android）</div>
</li>
<li>
	<div class="icon"><img src="list/num4.png" style="width: 25px; height: 25px"></div>
    <div class="text">侧拉展现（当前仅支持Android）</div>
</li>
<li>
  <div class="icon"><img src="list/num5.png" style="width: 25px; height: 25px"></div>
  <div class="text">Tab页面（当前仅支持Android）</div>
</li>
<li>
  <div class="icon"><img src="list/num6.png" style="width: 25px; height: 25px"></div>
  <div class="text">企业通讯录</div>
</li>
</ul>
 <script type="text/javascript">
    window.addEventListener('load', function() {
        setTimeout(function(){
        }, 500);
    });

    function openLink(url){
        dd.biz.util.openLink({
            url:url,
            onSuccess : function(result) {
            },
            onFail : function(err) {
                alert(JSON.stringify(err));
            }
        });
    }

	var items = document.querySelectorAll('li');
	items[0].addEventListener('click',function(){
 		openLink('http://ddtalk.github.io/dingTalkDoc/#企业接入指南');
	});
	items[1].addEventListener('click',function(){
		 window.location='./nav/1.html';
	});
	items[2].addEventListener('click', function(){
 		window.location = './list/list.html'; 
	});
	
	items[3].addEventListener('click',function(){
 	 	window.location='./drawer/index.html'; 
	});
	items[4].addEventListener('click',function(){
 		 window.location='./tab/index.html'; 
	});
	items[5].addEventListener('click',function(){
 		 window.location='./contacts.jsp?corpid='+_config.corpId; 
	});	
	
</script>
 
</body>

</html>
