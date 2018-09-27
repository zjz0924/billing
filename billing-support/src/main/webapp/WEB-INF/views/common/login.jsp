<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@include file="/page/taglibs.jsp"%>

<!DOCTYPE html>
<head>
	<meta charset="utf-8">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta http-equiv="Cache-Control" content="no-siteapp" />
	<!--[if lt IE 9]>
	<script type="text/javascript" src="lib/html5shiv.js"></script>
	<script type="text/javascript" src="lib/respond.min.js"></script>
	<![endif]-->
	<link href="${ctx}/resources/css/H-ui.min.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/resources/css/H-ui.login.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/resources/css/style.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/resources/css/iconfont.css" rel="stylesheet" type="text/css" />
	<!--[if IE 6]>
	<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
	<script>DD_belatedPNG.fix('*');</script>
	<![endif]-->
	<title>登录</title>
</head>

<body>
	<div class="header"></div>
	<div class="loginWraper">
	  <div id="loginform" class="loginBox">
		<form class="form form-horizontal" id="loginForm" action="${ctx}/login" method="post">
		  <div class="row cl">
			<label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>
			<div class="formControls col-xs-8">
			  <input id="username" name="username" type="text" placeholder="用户名" class="input-text size-L">
			</div>
		  </div>
		  <div class="row cl">
			<label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
			<div class="formControls col-xs-8">
			  <input name="password" id="password" type="password" placeholder="密码" class="input-text size-L">
			</div>
		  </div>
		  <div class="row cl">
			<div class="formControls col-xs-8 col-xs-offset-3">
			  <input name="vcode" id="vcode" maxLength="4" class="input-text size-L" type="text" placeholder="验证码" onblur="if(this.value==''){this.value='验证码:'}" onclick="if(this.value=='验证码:'){this.value='';}" value="验证码:" style="width:150px;">
			  <img src="${ctx}/verifycode" id="verifyimg"> <a id="kanbuq" href="javascript:;" onclick="refreshVerify('verifyimg')">看不清，换一张</a> </div>
		  </div>
		  <div class="row cl">
			<div class="formControls col-xs-8 col-xs-offset-3">
			  <p id="error_message" style="color:#d25; font-weight:bold;">${error}</p>
			  <input type="button" class="btn btn-success radius size-L" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;" onclick="doSubmit()">
			  <input name="" type="reset" class="btn btn-default radius size-L" value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;">
			</div>
		  </div>
		</form>
	  </div>
	</div>
	<div class="footer">Copyright z_0924 技术支持</div>
	<script type="text/javascript" src="${ctx}/resources/js/jquery.min.js"></script> 
	<script type="text/javascript" src="${ctx}/resources/js/H-ui.min.js"></script>
	
	<script type="text/javascript">
		$(function(){
			$("#verifyimg").click(function(){
				refreshVerify("verifyimg");
			});
			
			/**有父窗口则在父窗口打开*/  
	        if(self!=top){top.location=self.location;}  
		});
		
		document.onkeydown = function(event){
			var e = event || window.event || arguments.callee.caller.arguments[0];
			if(e && e.keyCode == 13){ // enter 键
				doSubmit();
			}
		}; 
		
		function checkVerify(verify) {
			var ret = false;
			$.ajax({
		        type: "GET",
		        cache: false,
		        url: "${ctx}/checkverify",
		        async: false,
		        data: "vcode="+verify,
		        success: function(msg) {
		        	ret = msg;
		        }
		    });
			
			return ret;
		}
		
		function refreshVerify(id){
			var imgurl = "${ctx}/verifycode?rnd="+new Date().getTime();
			$("#"+id).attr("src", imgurl);
		}

		function doSubmit(){
			var username = $("#username").val();
			var password = $("#password").val();
			var verifyCode = $("#vcode").val();
		
			if (username.length == 0){
				$("#error_message").html("请输入账号");
				$("#username").focus();
				return false;
			}else{
				$("#error_message").html("");
			}					
			
			if (password.length == 0){
				$("#error_message").html("请输入密码");
				$("#password").focus();
				return false;
			}
			
			if (verifyCode.length == 0){
				$("#error_message").html("请输入验证码");
				$("#vcode").focus();
				return false;
			}else {
				if (checkVerify(verifyCode) != true) {
		    		$("#error_message").html("验证码输入错误，看不清？点击图片换一个");
		    		$("#vcode").focus();
		    		
		    		//刷新验证码
		    		refreshVerify("verifyimg");
		    		return false;
				}else{
					$("#error_message").html("");
				}
			}
			
			$('#loginForm').submit();
		}
	</script>
	
</body>
</html>