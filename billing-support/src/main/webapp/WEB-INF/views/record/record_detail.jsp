<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/page/taglibs.jsp"%>

<!DOCTYPE html>
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
		<title>添加证书</title>
	</head>

<body>
	<article class="page-container">
		<form class="form form-horizontal" id="form-admin-add">
			<input type="hidden" id="id" name="id" value="${facadeBean.id}" />

			<div class="row cl">
				<dl class="permission-list">
					<dd>
						<div class="row cl">
							<div class="row cl">
								<label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>名称：</label>
								<div class="formControls col-xs-8 col-sm-9">
									<input type="text" class="input-text" value="${facadeBean.name}" id="name" name="name">
								</div>
							</div>

							<div class="row cl">
								<label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>过期日期：</label>
								<div class="formControls col-xs-8 col-sm-9">
									<input type="text" onfocus="WdatePicker()" id="expireDate" name="expireDate" class="input-text Wdate" value="<fmt:formatDate value='${facadeBean.expireDate}' type="date" pattern="yyyy-MM-dd" />">
								</div>
							</div>

							<div class="row cl">
								<label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>金额：</label>
								<div class="formControls col-xs-8 col-sm-9">
									<input type="text" class="input-text" value="${facadeBean.price}" id="price" name="price">
								</div>
							</div>

							<div class="row cl">
								<label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>比例（整数）：</label>
								<div class="formControls col-xs-8 col-sm-9">
									<input type="text" class="input-text" value="${facadeBean.scale}" id="scale" name="scale">
								</div>
							</div>

							<div class="row cl">
								<label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>结算日期：</label>
								<div class="formControls col-xs-8 col-sm-9">
									<input type="text" onfocus="WdatePicker()" id="cutoffDate" name="cutoffDate" class="input-text Wdate" value="<fmt:formatDate value='${cutoffDate}' type="date" pattern="yyyy-MM-dd" />">
								</div>
							</div>

							<div class="row cl">
								<label class="form-label col-xs-3 col-sm-3">备注：</label>
								<div class="formControls col-xs-8 col-sm-9">
									<input type="text" class="input-text" value="${facadeBean.remark}" id="remark" name="remark">
								</div>
							</div>
						</div>

						<div class="row cl">
							<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
								<p id="loadingDiv" style="display: none;">
									<img src="${ctx}/resources/img/timg.gif" style="width: 50px; height: 50px;" />提交中，请稍等...
								</p>
								<p id="submitDiv">
									<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;" id="submitBtn">
								</p>
							</div>
						</div>
					</dd>
				</dl>
			</div>
		</form>
	</article>

	<!--_footer 作为公共模版分离出去-->
	<script type="text/javascript" src="${ctx}/resources/js/jquery.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/layer/2.4/layer.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/H-ui.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/H-ui.admin.js"></script>
	<!--/_footer 作为公共模版分离出去-->

	<!--请在下方写此页面业务相关的脚本-->
	<script type="text/javascript" src="${ctx}/resources/js/jquery.validation/1.14.0/jquery.validate.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/jquery.validation/1.14.0/validate-methods.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/jquery.validation/1.14.0/messages_zh.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/My97DatePicker/4.8/WdatePicker.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#form-admin-add").validate({
				onkeyup : false,
				focusCleanup : true,
				success : "valid",
				submitHandler : function(form) {
					$(form).ajaxSubmit({
						type : 'post',
						dataType : 'json',
						url : "${ctx}/record/save?time=" + new Date(),
						beforeSubmit : function() {
							// 名称
							var name = $("#name").val();
							if(name == null || name == "" || name == undefined){
								layer.msg('请输入名称', { icon : 5, time : 1000 });
								$("#name").focus();
								return false;
							}
							
							// 过期日期
							var expireDate = $("#expireDate").val();
							if(expireDate == null || expireDate == "" || expireDate == undefined){
								layer.msg('请选择过期日期', { icon : 5, time : 1000 });
								return false;
							}
							
							// 价钱
							var price = $("#price").val();
							if(price == null || price == "" || price == undefined){
								layer.msg('请填写金额', { icon : 5, time : 1000 });
								$("#price").val("");
								$("#price").focus();
								return false;
							}else{
								if(isNaN(price)){
									layer.msg('金额只能是数字', { icon : 5, time : 1000 });
									$("#price").val("");
									$("#price").focus();
									return false;	
								}
							}
							
							// 比例
							var scale = $("#scale").val();
							if(scale == null || scale == "" || scale == undefined){
								layer.msg('请填写比例', { icon : 5, time : 1000 });
								$("#scale").val("");
								$("#scale").focus();
								return false;
							}else{
								var type="^[0-9]*[1-9][0-9]*$"; 
								var r = new RegExp(type); 
								var flag = r.test(scale);
								
								if(!flag){
									layer.msg('比例只能是正整数', { icon : 5, time : 1000 });
									$("#scale").val("");
									$("#scale").focus();
									return false;	
								}
							}
							
							// 结算日期
							var cutoffDate = $("#cutoffDate").val();
							if(cutoffDate == null || cutoffDate == "" || cutoffDate == undefined){
								layer.msg('请选择结算日期', { icon : 5, time : 1000 });
								return false;
							}
							
							$("#loadingDiv").show();
							$("#submitDiv").hide();
						},
						success : function(data) {
							if (data.success) {
								layer.msg(data.msg, {icon : 6, time : 1000 }, function() {
									parent.window.location.reload();
								});
							} else {
								layer.msg(data.msg, { icon : 5, time : 1000});
								$("#loadingDiv").hide();
								$("#submitDiv").show();
							}
						},
						error : function(XmlHttpRequest, textStatus, errorThrown) {
							layer.msg('error!', { icon : 5, time : 1000 });
							$("#loadingDiv").hide();
							$("#submitDiv").show();
						}
					});
				}
			});
		});
	</script>

</body>
</html>
