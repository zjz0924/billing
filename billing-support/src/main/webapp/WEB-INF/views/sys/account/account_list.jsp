<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/page/taglibs.jsp"%>
<%@include file="/page/NavPageBar.jsp" %>

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
	<title>用户列表</title>
</head>

<body>
	<form id="queryForm" name="queryForm" action="${ctx}/accountlist" method="post">
		<div class="page-container">
			<div class="text-c"> 
				用户名：<input type="text" class="input-text" style="width:150px" placeholder="用户名" id="userName" name="userName" value="${userName}">&nbsp;&nbsp;&nbsp;&nbsp;
				昵称：<input type="text" class="input-text" style="width:150px" placeholder="昵称" id="nickName" name="nickName" value="${nickName}">&nbsp;&nbsp;&nbsp;&nbsp;
			            创建时间：
				<input type="text" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'endCreateTime\')||\'%y-%M-%d\'}' })" id="startCreateTime" name="startCreateTime" class="input-text Wdate" style="width:120px;" value="${startCreateTime}">
				-
				<input type="text" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'startCreateTime\')}',maxDate:'%y-%M-%d' })" id="endCreateTime" name="endCreateTime" class="input-text Wdate" style="width:120px;" value="${endCreateTime}">&nbsp;&nbsp;&nbsp;&nbsp;
				
				<button type="button" class="btn btn-success" onclick="searchData();"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
				<button type="button" class="btn btn-danger" onclick="resetData();"><i class="Hui-iconfont">&#xe665;</i> 重置</button>
			</div>
			
			<div class="cl pd-5 bg-1 bk-gray mt-20"> 
				<span class="l" style="float: right !important;margin-right: 10px;">
					<a href="javascript:void(0);" onclick="addOrUpdateAccount()" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加用户</a>
				</span> 
			</div>
			
			<table class="table table-border table-bordered table-bg">
				<thead>
					<tr class="text-c">
						<th width="40">序号</th>
						<th width="80">用户名</th>
						<th width="80">昵称</th>
						<th width="120">电话</th>
						<th width="130">邮箱</th>
						<th width="60">是否锁定</th>
						<th width="100">创建时间</th>
						<th width="140">备注</th>
						<th width="100">操作</th>
					</tr> 
				</thead>
				
				<tbody>
					<c:forEach items="${dataList}" var="vo" varStatus="var">
						<tr class="text-c">
							<td>${var.index + 1}</td>
							<td>${vo.userName}</td>
							<td>${vo.nickName}</td>
							<td>${vo.mobile}</td>
							<td>${vo.email}</td>
							<td class="td-status"><c:if test="${vo.lock == 'Y' }"><span class="label radius">是</c:if><c:if test="${ vo.lock == 'N' }"><span class="label label-success radius">否</c:if></td>
							<td><fmt:formatDate value='${vo.createTime }' type="date" pattern="yyyy-MM-dd"/>-<fmt:formatDate value='${vo.createTime}' type="date" pattern="yyyy-MM-dd"/></td>
							<td>${vo.remark}</td>
							<td class="td-manage">
								
								<c:choose>
									<c:when test="${currentAccount.userName == 'admin' }">
										<a title="编辑" href="javascript:void(0)" onclick="addOrUpdateAccount(${vo.id})" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a> &nbsp;
									</c:when>
									<c:otherwise>
										<c:if test="${vo.userName != 'admin'}">
											<a title="编辑" href="javascript:void(0)" onclick="addOrUpdateAccount(${vo.id})" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a> &nbsp;
										</c:if>
									</c:otherwise>
								</c:choose>
								
								<c:if test="${currentAccount.userName == 'admin' }">
									<c:if test="${ vo.lock == 'N' }">
										<a style="text-decoration:none" onClick="lockOrUnlock(${vo.id}, 'Y')" href="javascript:void(0)" title="锁定">
											<i class="Hui-iconfont">&#xe631;</i>
										</a> 
									</c:if>
									<c:if test="${vo.lock == 'Y' }">
										<a style="text-decoration:none" onClick="lockOrUnlock(${vo.id}, 'N')" href="javascript:void(0)" title="解除">
											<i class="Hui-iconfont">&#xe615;</i>
										</a>
									</c:if>
									
									<a title="删除" href="javascript:void(0)" onclick="deleteAccount(${vo.id})" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
								
									<a title="重置密码" href="javascript:void(0)" onclick="resetPwd(${vo.id})" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe68f;</i></a> 
								</c:if>
								
								
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<pagination:pagebar startRow="${dataList.getStartRow()}" id="queryForm" pageSize="${dataList.getPageSize()}"  totalSize="${dataList.getTotal()}"   showbar="true"  showdetail="true"/>
		</div>
	</form>
	
	<!--_footer 作为公共模版分离出去-->
	<script type="text/javascript" src="${ctx}/resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/layer/2.4/layer.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/H-ui.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/H-ui.admin.js"></script>
	<!--/_footer 作为公共模版分离出去-->
	
	<script type="text/javascript" src="${ctx}/resources/js/My97DatePicker/4.8/WdatePicker.js"></script> 
	<script type="text/javascript" src="${ctx}/resources/js/laypage/1.2/laypage.js"></script>
	<script type="text/javascript">
		$(function(){
			 document.onkeydown = function (event) {
		        var e = event || window.event;
		        if (e && e.keyCode == 13) { //回车键的键值为13
		        	searchData();
		        }
		     }; 
		});
	
		function searchData(){
			document.getElementById("queryForm").submit();
		} 
		
		function resetData(){
			$("#userName").val("");
			$("#nickName").val("");
			$("#startCreateTime").val("");
			$("#endCreateTime").val("");
			document.getElementById("queryForm").submit();
		}
		
		function addOrUpdateAccount(id){
			/*
				参数解释：
				title	标题
				url		请求的url
				id		需要操作的数据id
				w		弹出层宽度（缺省调默认值）
				h		弹出层高度（缺省调默认值）
			*/
			var url = "${ctx}/account/detail";
			if(id != null){
				url += "?id=" + id
			}
			
			layer_show("添加用户", url, '600', '500');
		}
		
		function lockOrUnlock(id, type){
			var message = "此操作将解锁该用户，您确定要继续吗？";
			if(type == "Y"){
				message = "此操作将锁定该用户，您确定要继续吗？";
			}
			
			layer.confirm(message, function(index){
				 $.ajax({
                  	url: "${ctx}/account/lock",
                  	data: {
                  		id: id,
                  		lock: type
                  	},
                  	success: function(data){
                  		if(data.success){
                  			layer.msg(data.msg, {icon: 6, time:1000}, function(){
                  				window.location.reload();
                  			});
          				 }else {
          					layer.msg(msg, {icon: 5,time:1000});
          				 }
                  	}
                 });
				 
				 
				 
			});
		}
		
		function deleteAccount(id){
			layer.confirm("此操作将删除该用户，您确定要继续吗？", function(index){
				 $.ajax({
                 	url: "${ctx}/account/delete",
                 	data: {
                 		id: id,
                 	},
                 	success: function(data){
                 		if(data.success){
                 			layer.msg(data.msg, {icon: 6,time:1000}, function(){
                 				window.location.reload();
                 			});
         				}else{
         					layer.msg(data.msg, {icon: 5,time:1000});
         				}
                 	}
                 });
			});
		}
		
		function resetPwd(id){
			layer.confirm("此操作将重置该用户密码，您确定要继续吗？", function(index) {
				$.ajax({
	            	url: "${ctx}/account/resetPwd",
	            	data: {
	            		id: id,
	            	},
	            	success: function(data){
	            		if(data.success){
	            			layer.msg(data.msg, {icon: 6, time:2000});
	    				}else{
	    					layer.msg(data.msg, {icon: 5, time:2000});
	    				}
	            	}
	            });
			});
		}
		
	</script>
	
</body>
</html>
