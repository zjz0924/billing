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
	<form id="queryForm" name="queryForm" action="${ctx}/statistic/income" method="post">
		<div class="page-container">
			<div class="text-c" style="text-align: left;"> 
			             结算时间：
				<input type="text" onfocus="WdatePicker()" id="startCutoffDate" name="startCutoffDate" class="input-text Wdate" style="width:120px;" value="${startCutoffDate}">
				-
				<input type="text" onfocus="WdatePicker()" id="endCutoffDate" name="endCutoffDate" class="input-text Wdate" style="width:120px;" value="${endCutoffDate}">&nbsp;&nbsp;&nbsp;&nbsp;
				
				过期时间：
				<input type="text" onfocus="WdatePicker()" id="startExpireDate" name="startExpireDate" class="input-text Wdate" style="width:120px;" value="${startExpireDate}">
				-
				<input type="text" onfocus="WdatePicker()" id="endExpireDate" name="endExpireDate" class="input-text Wdate" style="width:120px;" value="${endExpireDate}">&nbsp;&nbsp;&nbsp;&nbsp;
			
				 App名称：<input type="text" class="input-text" style="width:200px;" id="name" name="name" value="${name}">
			</div>
			
			<div class="text-c" style="text-align: left; margin-top: 10px">
				 创建时间：
				<input type="text" onfocus="WdatePicker()" id="startCreateTime" name="startCreateTime" class="input-text Wdate" style="width:120px;" value="${startCreateTime}">
				-
				<input type="text" onfocus="WdatePicker()" id="endCreateTime" name="endCreateTime" class="input-text Wdate" style="width:120px;" value="${endCreateTime}">&nbsp;&nbsp;&nbsp;&nbsp;
				
				类型：
				<select class="select input-text" id="type" name="type" style="width: 120px;margin-left: 28px;">
					<option value="">全部</option>
		          	<option value="1" <c:if test="${type == 1}">selected="selected"</c:if>>新增</option>
		          	<option value="2" <c:if test="${type == 2}">selected="selected"</c:if>>续费</option>
				</select>
			
			
				<button type="button" class="btn btn-success" onclick="searchData();"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
				<button type="button" class="btn btn-danger" onclick="resetData();"><i class="Hui-iconfont">&#xe665;</i> 重置</button>	
			</div>
			
			<div class="cl pd-5 bg-1 bk-gray mt-20"> 
				<span style="margin-left: 10px; display: inline-block;padding-top: 5px;padding-bottom: 5px;">
					<span>总金额：</span>
					<span style="font-weight:bold; color:red;"><fmt:formatNumber type="number" value="${priceItem.total1 + priceItem.total2}" pattern="0.0" maxFractionDigits="1"/></span><span>&nbsp;&nbsp;元</span>
					<span style="margin-left: 40px;">我：</span>
					<span style="font-weight:bold; color:red;"><fmt:formatNumber type="number" value="${priceItem.total1 }" pattern="0.0" maxFractionDigits="1"/></span><span>&nbsp;&nbsp;元</span>
					<span style="margin-left: 40px;">其他：</span>
					<span style="font-weight:bold; color:red;"><fmt:formatNumber type="number" value="${priceItem.total2 }" pattern="0.0" maxFractionDigits="1"/></span><span>&nbsp;&nbsp;元 </span>
				</span>
				
				<span class="l" style="float: right !important;margin-right: 10px;">
					<a href="${ctx}/statistic/incomeExport" class="btn btn-success radius"><i class="Hui-iconfont">&#xe641;</i> 导出</a>
				</span>
			</div>
			
			<table class="table table-border table-bordered table-bg">
				<thead>
					<tr class="text-c">
						<th width="30">序号</th>
						<th width="120">App名称 </th>
						<th width="80">结算日期</th>
						<th width="80">过期时间</th>
						<th width="70">总金额/元</th>
						<th width="60">比例(%)</th>
						<th width="60">我的/元</th>
						<th width="60">其他/元</th>
						<th width="50">类型</th>
						<th width="30">创建时间</th>
						<th width="120">备注</th>
					</tr> 
				</thead>
				
				<tbody>
					<c:forEach items="${dataList}" var="vo" varStatus="var">
						<tr class="text-c">
							<td>${var.index + 1}</td>
							<td title="${vo.app.name }">${vo.app.name }</td>
							<td style="color:red; font-weight: bold;"><fmt:formatDate value='${vo.cutoffDate }' type="date" pattern="yyyy-MM-dd" /></td>
							<td><fmt:formatDate value='${vo.expireDate }' type="date" pattern="yyyy-MM-dd" /></td>
							<td title="${vo.combo.price}">${vo.combo.price}</td>
							<td title="${vo.scale.val }" style="color:red; font-weight: bold;">${vo.scale.val }</td>
							<td title="${vo.extract1 }" style="color:red; font-weight: bold;">${vo.extract1 }</td>
							<td title="${vo.extract2 }">${vo.extract2 }</td>
							<td>
								<c:if test="${vo.type == 1}">新增</c:if>
								<c:if test="${vo.type == 2}">续费</c:if>
							</td>
							<td><fmt:formatDate value='${vo.createTime }' type="date" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td title="${vo.remark}">${vo.remark}</td>
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
			$("#name").val("");
			$("#startExpireDate").val("");
			$("#endExpireDate").val("");
			$("#startCreateTime").val("");
			$("#endCreateTime").val("");
			$("#startCutoffDate").val("");
			$("#endCutoffDate").val("");
			$("#type").val("");
			document.getElementById("queryForm").submit();
		}
		
	</script>
	
</body>
</html>
