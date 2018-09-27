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
	<title>日志信息</title>
</head>

<body>
	<div style="margin-left:10px;">
		<div class="data-row">
			<span class="data-title">类型：</span>
			<span class="data-content">${opeartionLog.type}</span>
		</div>
		
		<div class="data-row">
			<span class="data-title">操作：</span>
			<span class="data-content">${opeartionLog.operation}</span>
		</div>
		
		<div class="data-row">
			<span class="data-title">用户：</span>
			<span class="data-content">${opeartionLog.userName}</span>
		</div>
		
		<div class="data-row">
			<span class="data-title">Client IP：</span>
			<span class="data-content">${opeartionLog.clientIp}</span>
		</div>
		
		<div class="data-row">
			<span class="data-title">User Agent：</span>
			<span class="data-content">${opeartionLog.userAgent}</span>
		</div>
		
		<div class="data-row">
			<span class="data-title">时间：</span>
			<span class="data-content"><fmt:formatDate value='${opeartionLog.time}' type="date" pattern="yyyy-MM-dd HH:mm:ss" /></span>
		</div>
	</div>	
	
	<div style="margin-top:10px;">
		<div style="font-weight:bold;margin-left:10px;">详细信息</div>
		
		<c:choose>
			<c:when test="${not empty dataList}">
				<table class="table table-border table-bordered table-bg table-hover table-sort" style="width:90%; margin-left: 10px; margin-top: 10px;margin-bottom: 20px;">
					<thead>
						<tr class="text-c">
							<th style="width:100px">属性</th>
							<th>当前值</th>
							
							<c:if test="${operation == '编辑'}">
								<th>原值</th>
							</c:if>
						</tr>
					</thead>
					
					<c:forEach items="${dataList}" var="vo" > 
						<tr>
							<td>${vo.name}</td>
							<c:choose>
								<c:when test="${opeartionLog.operation == '编辑'}">
									<td>${vo.newValue}</td>
									<td>${vo.oldValue}</td>
								</c:when>
								<c:when test="${opeartionLog.operation == '新建'}">
									<td>${vo.newValue}</td>
								</c:when>
								<c:otherwise>
									<td>${vo.oldValue}</td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach> 
				</table>
			</c:when>
			<c:otherwise>
				<div style="margin-left: 10px;margin-top: 5px;">${opeartionLog.detail }</div>
			</c:otherwise>
		</c:choose>
	
	</div>
	
	<style type="text/css">
		.data-row{
			line-height: 30px;
		}
		
		.data-title{
			display:inline-block;
			width: 120px;
		}
	</style>
	

</body>
