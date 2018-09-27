<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/page/taglibs.jsp"%>
<%@include file="/page/NavPageBar.jsp" %>

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
		<title>日志列表</title>		
	</head>
	
	<body>
		<form id="queryForm" name="queryForm" action="${ctx}/operationlog/list" method="post">
			<div class="page-container">
				<div class="text-c" style="margin-bottom:25px;"> 
					用户名：<input type="text" class="input-text" style="width:150px" placeholder="" id="userName" name="userName" value="${userName}">&nbsp;&nbsp;&nbsp;&nbsp;
				    
				           类型：<select class="select input-text" id="type" name="type" style="width:150px">
						<option value="">全部</option>
						<c:forEach items="${typeList}" var="vo">
							<option value="${vo}" <c:if test="${vo == type}">selected="selected"</c:if>>${vo}</option>
						</c:forEach>
					</select>&nbsp;&nbsp;&nbsp;&nbsp;
				          
				           创建时间：
					<input type="text" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'startTimeTo\')||\'%y-%M-%d\'}' })" id="startTimeFrom" name="startTimeFrom" class="input-text Wdate" style="width:120px;" value="${startTimeFrom}">
					-
					<input type="text" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'startTimeFrom\')}',maxDate:'%y-%M-%d' })" id="startTimeTo" name="startTimeTo" class="input-text Wdate" style="width:120px;" value="${startTimeTo}">&nbsp;&nbsp;&nbsp;&nbsp;
					
					<button type="button" class="btn btn-success" onclick="searchData();"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
					<button type="button" class="btn btn-danger" onclick="resetData();"><i class="Hui-iconfont">&#xe665;</i> 重置</button>
				</div>
				
				<table class="table table-border table-bordered table-bg">
					<thead>
						<tr class="text-c">
							<th width="40">序号</th>
							<th width="80">用户名</th>
							<th width="80">类型</th>
							<th width="80">操作</th>
							<th width="100">访问IP</th>
							<th>详情</th>
							<th width="130">创建时间</th>
							<th width="50">操作</th>
						</tr> 
					</thead>
					
					<tbody>
						<c:forEach items="${dataList}" var="vo" varStatus="var">
							<tr class="text-c">
								<td>${var.index + 1}</td>
								<td>${vo.userName}</td>
								<td>${vo.type}</td>
								<td>${vo.operation}</td>
								<td>${vo.clientIp}</td>
								<td><a href="javascript:void(0)" onclick="info(${vo.id})">${vo.detail}</a></td>
								<td><fmt:formatDate value='${vo.time }' type="date" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td><a title="详情" href="javascript:void(0)" onclick="info(${vo.id})" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe665;</i></a></td>
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
				$("#type").val("");
				$("#startTimeTo").val("");
				$("#startTimeFrom").val("");
				document.getElementById("queryForm").submit();
			}
		
			function info(id){
				layer_show("日志信息", "${ctx}/operationlog/detail?id=" + id, '700', '500');
			}
			
		</script>
		
	</body>
</html>
	
	
