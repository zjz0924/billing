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
	<form id="queryForm" name="queryForm" action="${ctx}/record/list?type=0" method="post">
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
			
				  名称：<input type="text" class="input-text" style="width:200px;" id="name" name="name" value="${name}">
			</div>
			
			<div class="text-c" style="text-align: left; margin-top: 10px">
				 创建时间：
				<input type="text" onfocus="WdatePicker()" id="startCreateTime" name="startCreateTime" class="input-text Wdate" style="width:120px;" value="${startCreateTime}">
				-
				<input type="text" onfocus="WdatePicker()" id="endCreateTime" name="endCreateTime" class="input-text Wdate" style="width:120px;" value="${endCreateTime}">&nbsp;&nbsp;&nbsp;&nbsp;
				
				 更新时间：
				<input type="text" onfocus="WdatePicker()" id="startUpdateTime" name="startUpdateTime" class="input-text Wdate" style="width:120px;" value="${startUpdateTime}">
				-
				<input type="text" onfocus="WdatePicker()" id="endUpdateTime" name="endUpdateTime" class="input-text Wdate" style="width:120px;" value="${endUpdateTime}">&nbsp;&nbsp;&nbsp;&nbsp;
				
				<button type="button" class="btn btn-success" onclick="searchData();"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
				<button type="button" class="btn btn-danger" onclick="resetData();"><i class="Hui-iconfont">&#xe665;</i> 重置</button>	
			</div>
			
			<div class="cl pd-5 bg-1 bk-gray mt-20">
				<span style="margin-left: 10px; display: inline-block;padding-top: 5px;">
					<span>总金额：</span>
					<span style="font-weight:bold; color:red;">${priceItem.total1 + priceItem.total2}</span><span>&nbsp;&nbsp;元</span>
					<span style="margin-left: 40px;">我：</span>
					<span style="font-weight:bold; color:red;">${priceItem.total1 }</span><span>&nbsp;&nbsp;元</span>
					<span style="margin-left: 40px;">其他：</span>
					<span style="font-weight:bold; color:red;">${priceItem.total2 }</span><span>&nbsp;&nbsp;元 </span>
				</span>
				
				<span class="l" style="float: right !important;margin-right: 10px;">
					<a href="javascript:void(0);" onclick="addOrUpdate()" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加记录</a>&nbsp;&nbsp;
					<a href="javascript:void(0);" onclick="cutOff()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe615;</i> 结算</a>&nbsp;&nbsp;
					<a href="${ctx}/record/exportList?type=0" class="btn btn-success radius"><i class="Hui-iconfont">&#xe641;</i> 导出</a>
				</span> 
			</div>
			
			<table class="table table-border table-bordered table-bg">
				<thead>
					<tr class="text-c">
						<th width="30"><input type="checkbox" value="" name=""></th>
						<th width="30">序号</th>
						<th width="75">结算日期</th>
						<th width="80">名称</th>
						<th width="75">过期时间</th>
						<th width="60">总金额/元</th>
						<th width="35">比例(%)</th>
						<th width="60">我的/元</th>
						<th width="60">其他/元</th>
						<th width="30">创建时间</th>
						<th width="30">更新时间</th>
						<th width="120">备注</th>
						<th width="50">操作</th>
					</tr> 
				</thead>
				
				<tbody>
					<c:forEach items="${dataList}" var="vo" varStatus="var">
						<tr class="text-c">
							<td><input type="checkbox" value="${vo.id}" name="recordcheck"></td>
							<td>${var.index + 1}</td>
							<td style="color:red; font-weight: bold;"><fmt:formatDate value='${vo.cutoffDate }' type="date" pattern="yyyy-MM-dd" /></td>
							<td title="${vo.name}">${vo.name}</td>
							<td><fmt:formatDate value='${vo.expireDate }' type="date" pattern="yyyy-MM-dd" /></td>
							<td title="${vo.combo.price}">${vo.combo.price}</td>
							<td title="${vo.scale.val }" style="color:red; font-weight: bold;">${vo.scale.val }</td>
							<td title="${vo.extract1 }" style="color:red; font-weight: bold;">${vo.extract1 }</td>
							<td title="${vo.extract2 }">${vo.extract2 }</td>
							<td><fmt:formatDate value='${vo.createTime }' type="date" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td><fmt:formatDate value='${vo.updateTime }' type="date" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td title="${vo.remark}">${vo.remark}</td>
							<td class="td-manage">
								<a title="编辑" href="javascript:void(0)" onclick="addOrUpdate(${vo.id})" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a>
								<a title="删除" href="javascript:void(0)" onclick="deleteCert(${vo.id})" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
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
			$("#name").val("");
			$("#startExpireDate").val("");
			$("#endExpireDate").val("");
			$("#startCreateTime").val("");
			$("#endCreateTime").val("");
			$("#startUpdateTime").val("");
			$("#endUpdateTime").val("");
			$("#startCutoffDate").val("");
			$("#endCutoffDate").val("");
			document.getElementById("queryForm").submit();
		}
		
		function addOrUpdate(id){
			var url = "${ctx}/record/detail";
			if(id != null){
				url += "?id=" + id
			}
			
			layer_show("记录信息", url, '600', '500');
		}
		
		function deleteCert(id){
			layer.confirm("此操作将删除该记录，您确定要继续吗？", function(index){
				 $.ajax({
                 	url: "${ctx}/record/delete",
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
		
		// 结算
		function cutOff(){
			var objs = document.getElementsByName("recordcheck");
		    var check_val = [];
		    
		    for(k in objs){
		        if(objs[k].checked){
		        	check_val.push(objs[k].value);
		        }
		    }
		    
		    if(check_val == null || check_val.length < 1 || check_val == undefined){
		    	layer.msg("请先选择要结算的记录", {icon: 5,time:1000});
		    	return false;
		    }
		    
		    layer.confirm("是否确定进行结算，结算后将不能修改？", function(index){
		    	 $.ajax({
	             	url: "${ctx}/record/cutOff",
	             	data: {
	             		ids: check_val
	             	},
	             	success: function(data){
	             		if(data.success){
	             			layer.msg(data.msg, {icon: 6, time:1000}, function(){
	             				window.location.reload();
	             			});
	     				}else{
	     					layer.msg(data.msg, {icon: 5,time:1000});
	     				}
	             	}
	             });
		    });
		}
	</script>
	
</body>
</html>
