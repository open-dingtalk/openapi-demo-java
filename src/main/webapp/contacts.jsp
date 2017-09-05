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
		width: calc(100% - 90px);
		display: inline-block;
		text-align: left;
		line-height: 70px;
		vertical-align: middle;
		} 
    </style>

<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">

<title>Contact</title>
<script type="text/javascript">
var _config = <%= com.alibaba.dingtalk.openapi.demo.auth.AuthHelper.getConfig(request) %>;
dd.config({});
dd.ready(function() {
	dd.biz.navigation.setMenu({
		backgroundColor : "#ADD8E6",
		items : [
			{
				id:"此处可以设置帮助",//字符串
			// "iconId":"file",//字符串，图标命名
			  text:"帮助"
			}
			,
			{
				"id":"2",
			"iconId":"photo",
			  "text":"我们"
			}
			,
			{
				"id":"3",
			"iconId":"file",
			  "text":"你们"
			}
			,
			{
				"id":"4",
			"iconId":"time",
			  "text":"他们"
			}
		],
		onSuccess: function(data) {
			alert(JSON.stringify(data));

		},
		onFail: function(err) {
			alert(JSON.stringify(err));
		}
	});
});
</script>
<script type="text/javascript" src="javascripts/zepto.min.js"></script>
<script type="text/javascript" src="http://g.alicdn.com/ilw/ding/0.7.3/scripts/dingtalk.js">
</script>
<script type="text/javascript" src="javascripts/logger.js">
</script>
<script type="text/javascript" src="javascripts/contacts.js">
 
</script>
<script>
function openLink(){
	dd.biz.util.openLink({
		url:'http://h5.m.laiwang.com/home/ding.html',
	    onSuccess : function(result) {
	    },
	    onFail : function(err) {
	    	alert(JSON.stringify(err));
	    }
	});
}
</script>

</head>

<body >
<h2 style="text-align:center">企业通讯录</h2>
<div id="contactId"></div>
</body>

</html>
