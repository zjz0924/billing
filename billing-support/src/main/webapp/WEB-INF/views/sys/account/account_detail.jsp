<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/page/taglibs.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
	<meta charset="utf-8">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta http-equiv="Cache-Control" content="no-siteapp" />
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/H-ui.min.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/H-ui.admin.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/iconfont.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/skin/default/skin.css" id="skin" />
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
	<title>添加用户</title>
</head>

<body>
	<article class="page-container">
		<form class="form form-horizontal" id="form-admin-add">
			<input type="hidden" id="id" name="id" value="${facadeBean.id}"/>
			
			<div class="row cl">
				<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>用户名：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${facadeBean.userName}" id="userName" name="userName" <c:if test="${not empty facadeBean.id}">disabled</c:if>>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>昵称：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${facadeBean.nickName}" id="nickName" name="nickName">
				</div>
			</div>
			<c:if test="${empty facadeBean.id }"> 
				<div class="row cl">
					<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>初始密码：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="password" class="input-text" autocomplete="off" value="" placeholder="密码" id="password" name="password">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>确认密码：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="password" class="input-text" autocomplete="off"  placeholder="确认新密码" id="password2" name="password2">
					</div>
				</div>
			</c:if>
			<div class="row cl">
				<label class="form-label col-xs-3 col-sm-2">手机：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" id="phone" name="phone" value="${facadeBean.mobile}">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-3 col-sm-2">邮箱：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" name="email" id="email" value="${facadeBean.email}">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-3 col-sm-2">备注：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<textarea name="remark" cols="" rows="2" class="textarea" style="height: 70px;">${facadeBean.remark }</textarea>
				</div>
			</div>
			<div class="row cl">
				<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
					<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
				</div>
			</div>
		</form>
	</article>

	<!--_footer 作为公共模版分离出去--> 
	<script type="text/javascript" src="${ctx}/resources/js/jquery.min.js"></script> 
	<script type="text/javascript" src="${ctx}/resources/js/layer/2.4/layer.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/H-ui.min.js"></script> 
	<script type="text/javascript" src="${ctx}/resources/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->
	
	<!--请在下方写此页面业务相关的脚本-->
	<script type="text/javascript" src="${ctx}/resources/js/jquery.validation/1.14.0/jquery.validate.js"></script> 
	<script type="text/javascript" src="${ctx}/resources/js/jquery.validation/1.14.0/validate-methods.js"></script> 
	<script type="text/javascript" src="${ctx}/resources/js/jquery.validation/1.14.0/messages_zh.js"></script> 
	<script type="text/javascript">
		$(function(){
			$("#form-admin-add").validate({
				rules:{
					userName:{
						required:true
					},
					nickName:{
						required:true
					},
					password:{
						required:true,
					},
					password2:{
						required:true,
						equalTo: "#password"
					},
					phone:{
						required:false,
						isPhone:true,
					},
					email:{
						required:false,
						email:true,
					}
				},
				onkeyup:false,
				focusCleanup:true,
				success:"valid",
				submitHandler: function(form){
					$(form).ajaxSubmit({
						type: 'post',
						url: "${ctx}/account/save?time=" + new Date() ,
						success: function(data){
							if(data.success){
								layer.msg(data.msg, {icon:6,time:1000}, function(){
									parent.window.location.reload();
								});
							}else{
								layer.msg(data.msg, {icon:5, time:1000});
							}
						},
		                error: function(XmlHttpRequest, textStatus, errorThrown){
							layer.msg('error!',{icon:5,time:1000});
						}
					});
				}
			});
		});
	</script> 
	<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>