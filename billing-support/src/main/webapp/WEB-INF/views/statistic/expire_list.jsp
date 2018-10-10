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
	<title>APP列表</title>
</head>

<body>
	<form id="queryForm" name="queryForm" action="${ctx}/statistic/expire" method="post">
		<div class="page-container">
			<div class="text-c"> 
			          类型：<select class="select input-text" id="type" name="type" style="width: 120px;">
						<option value="1" <c:if test="${type == 1}">selected="selected"</c:if>>5天后</option>
						<option value="2" <c:if test="${type == 2}">selected="selected"</c:if>>3天后</option>
						<option value="3" <c:if test="${type == 3}">selected="selected"</c:if>>1天后</option>
					</select>&nbsp;&nbsp;&nbsp;&nbsp;
					
				<button type="button" class="btn btn-success" onclick="searchData();"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
				<button type="button" class="btn btn-danger" onclick="resetData();"><i class="Hui-iconfont">&#xe665;</i> 重置</button>	
			</div>
			
			<table class="table table-border table-bordered table-bg" style="margin-top: 30px;">
				<thead>
					<tr class="text-c">
						<th width="30">序号</th>
						<th width="140">名称</th>
						<th width="120">过期时间</th>
						<th width="30">创建时间</th>
						<th width="30">更新时间</th>
						<th width="150">备注</th>
						<th width="70">操作</th>
					</tr> 
				</thead>
				
				<tbody>
					<c:forEach items="${dataList}" var="vo" varStatus="var">
						<tr class="text-c">
							<td>${var.index + 1}</td>
							<td title="${vo.name}">${vo.name}</td>
							<td style="color:red; font-weight: bold;"><fmt:formatDate value='${vo.expireDate }' type="date" pattern="yyyy-MM-dd" /></td>
							<td><fmt:formatDate value='${vo.createTime }' type="date" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td><fmt:formatDate value='${vo.updateTime }' type="date" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td title="${vo.remark}">${vo.remark}</td>
							<td class="td-manage">
								<c:if test="${vo.isCut == 0 }">
									<a title="结算" href="javascript:void(0)" onclick="renew(${vo.id}, 1)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe60c;</i></a>
								</c:if>
								<c:if test="${vo.isCut == 1 }">
									<a title="续费" href="javascript:void(0)" onclick="renew(${vo.id}, 2)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe60c;</i></a>
								</c:if>
								<a title="记录" href="javascript:void(0)" onclick="recordList(${vo.id})" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe616;</i></a>
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
			$("#type").val("");
			document.getElementById("queryForm").submit();
		}
		
		function recordList(appId){
			var url = "${ctx}/record/list?appId=" + appId
			layer_show("记录", url, '830', '500');
		}
		
		function renew(appId, type){
			var url = "${ctx}/app/renewDetail?appId=" + appId + "&type=" + type
			var title = "结算";
					
			if(type == 2){
				title = "续费";
			}
			layer_show(title, url, '600', '450');
		}
	</script>
	
</body>
</html>
