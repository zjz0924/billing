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
	<form id="queryForm" name="queryForm" action="${ctx}/record/list?type=1" method="post">
		<div class="page-container">
			
			<table class="table table-border table-bordered table-bg">
				<thead>
					<tr class="text-c">
						<th width="30">类型</th>
						<th width="75">结算日期</th>
						<th width="70">过期时间</th>
						<th width="60">总金额/元</th>
						<th width="35">比例(%)</th>
						<th width="50">我的/元</th>
						<th width="50">其他/元</th>
						<th width="40">创建时间</th>
						<th width="150">备注</th>
					</tr> 
				</thead>
				
				<tbody>
					<c:forEach items="${dataList}" var="vo" varStatus="var">
						<tr class="text-c">
							<td><c:if test="${vo.type == 1 }">新增</c:if><c:if test="${vo.type == 2 }">续费</c:if></td>
							<td style="color:red; font-weight: bold;"><fmt:formatDate value='${vo.cutoffDate }' type="date" pattern="yyyy-MM-dd" /></td>
							<td><fmt:formatDate value='${vo.expireDate }' type="date" pattern="yyyy-MM-dd" /></td>
							<td title="${vo.combo.price}">${vo.combo.price}</td>
							<td title="${vo.scale.val }" style="color:red; font-weight: bold;">${vo.scale.val }</td>
							<td title="${vo.extract1 }" style="color:red; font-weight: bold;">${vo.extract1 }</td>
							<td title="${vo.extract2 }">${vo.extract2 }</td>
							<td><fmt:formatDate value='${vo.createTime }' type="date" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td title="${vo.remark}">${vo.remark}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div style="text-align: center;">
			<span style="margin-left: 10px; display: inline-block;padding-top: 5px;padding-bottom: 5px;">
				<span>总金额：</span>
				<span style="font-weight:bold; color:red;">${priceItem.total1 + priceItem.total2}</span><span>&nbsp;&nbsp;元</span>
				<span style="margin-left: 40px;">我：</span>
				<span style="font-weight:bold; color:red;">${priceItem.total1 }</span><span>&nbsp;&nbsp;元</span>
				<span style="margin-left: 40px;">其他：</span>
				<span style="font-weight:bold; color:red;">${priceItem.total2 }</span><span>&nbsp;&nbsp;元 </span>
			</span>
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
	
</body>
</html>
