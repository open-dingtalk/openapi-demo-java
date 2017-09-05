<!DOCTYPE html>
<%@ page language="java" import="java.util.*" contentType="text/html;charset=utf-8"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta content="yes" name="apple-mobile-web-app-capable"/>
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection"/>
<meta content="yes" name="apple-touch-fullscreen"/>
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" />

<title>哈雷</title>

<style>
  html,body{
    width: 100%;
    height: 100%;
    padding: 0;
    overflow: hidden;
  }
  body{
    height: 100%;
    max-width: 500px;
    margin: 0 auto;
    background-repeat: no-repeat;
    background-size: cover;
    padding-bottom: 50px;
    box-sizing: border-box;
    overflow-x: auto;
  }
  #main{
    width: 100%;
    height: 100%;
    min-width: 270px;
    display: -webkit-box;
    -webkit-box-align: center;
    /*-webkit-box-pack: center;*/
    box-sizing: border-box;
    min-height: 470px;
  }
  #contentBox{
    width: 100%;
    box-sizing: border-box;
    border-radius: 6px;
    background: #fff;
  }
  #boxTitle{
    text-align: center;
    font-size: 17px;
  }
  .role{
    text-align: center;
    border-radius: 8px;
    overflow: hidden;
    background-color: #f0eff5;
    vertical-align:middle;
  }
  .role>img{
    height:100%;
  }
  #roleText{
    vertical-align: middle;
    text-align: center;
  }
  #roleTitle{
    display: inline-block;
    height:100%; 
    vertical-align:middle; 
  }
  #videoImg>img{
    z-index: 10;
    position: absolute;
    top: 0;
    left: 0;
    vertical-align: middle;
    animation: rotate 1s linear infinite;
    -webkit-animation: rotate 1.75s linear infinite;
  }*/
  #circle{
    width: 14px;
    height: 14px;
    vertical-align: middle;
    background-image: url(https://gw.alicdn.com/tps/TB1RghPJFXXXXcHXpXXXXXXXXXX-28-27.png);
    background-size: 14px 14px;
    animation: rotate 1s linear infinite;
    -webkit-animation: rotate 1.75s linear infinite;
  }
  @-webkit-keyframes rotate{
    0%{
      -webkit-transform: rotate(0deg);
    }
    100%{
      -webkit-transform: rotate(360deg);
    }
  }
  @keyframes rotate{
    0%{
      transform: rotate(0deg);
    }
    100%{
      transform: rotate(360deg);
    }
  }
  #footer{
    position: fixed;
    left: 0;
    bottom: 20px;
    width: 100%;
    height: 30px;
    text-align: center;
  }
  @media screen and (min-height: 700px){
    .role{
      height: 120px;
      margin-top: 30px;
    }
  }
  
  @media screen and (max-height: 700px) and (min-height: 500px){
    .role{
      height: 100px;
      margin-top: 20px;
    }
  }
  
 @media screen and (max-height: 500px){
    .role{
      height: 80px;
      margin-top: 20px;
    }
  }
  
  
  @media screen and (min-width: 500px){
    #main{
      padding: 0 45px;
    }
    #contentBox{
      padding: 19px 23px 17px;
    }
  }
  
  @media screen and (max-width: 500px) and (min-width: 360px){
    body{
    	background-color:rgb(61, 66, 106);
    }
    #main{
      padding: 0 40px;
    }
    #contentBox{
      padding: 19px 23px 17px;
    }
  }
  @media screen and (max-width: 360px) and (min-width: 320px){
    body{
    	background-color:rgb(61, 66, 106);
    }
    #main{
      padding: 0 30px;
    }
    #contentBox{
      padding: 19px 18px 17px;
    }
  }
  
 @media screen and (max-width: 320px){
    body{
    	background-color:rgb(61, 66, 106);
    }
    #main{
      padding: 0 20px;
    }
    #contentBox{
      padding: 19px 15px 17px;
    }
  }
  
  @media screen and (max-height: 500px) and (min-width: 320px){
    html{
      overflow: auto;
    }
    body{
      overflow: auto;
      max-width:420px;
      padding-bottom: 0px;
    }
    #footer{
      position: relative;
      margin-top: 30px;
      background-attachment: scroll;
    }
   
  }
</style>

<script type="text/javascript" src="javascripts/zepto.min.js"></script>
<script type="text/javascript" src="http://g.alicdn.com/ilw/ding/0.7.3/scripts/dingtalk.js"></script>
</script>

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
<script type="text/javascript">
function openCompLink(){
	/* alert('config:'+_config.corpId+' appid:'+_config.appid); */
	window.location='index.jsp?appid='+_config.appid+'&dd_nav_bgcolor=FF5E97F6&corpid='+_config.corpId;
}
</script>
</head>
<body >
<div style="display:none;">
</div>
<div id="lw-share-data" style="display:none;">
  <div class="lws-link"></div>
</div>

<div id="main">
  <div id="contentBox">
    <div class="role" id="compPanel" onclick="openCompLink()">
      <img src='./pic/comp.png' style="text-align:left"> 
      <div id="roleTitle" >&nbsp;我是企业开发者</div>
    </div>
  </div>
  
</div>
<div id="footer">
	<img src="http://gtms04.alicdn.com/tps/i4/TB1x3olJXXXXXXpXFXXjI7xNFXX-338-67.png" height=34>
</div>

  
</script>
</body>

</html>