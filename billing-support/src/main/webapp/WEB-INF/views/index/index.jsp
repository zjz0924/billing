<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/page/taglibs.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
	<title>首页</title>
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
</head>

<body>
	<header class="navbar-wrapper">
		<div class="navbar navbar-fixed-top">
			<div class="container-fluid cl">
				<span style="font-size: 16px;line-height: 44px;margin-right: 10px;height: 44px;color: #fff;">记账管理系统</span>
				<a aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs" href="javascript:;">&#xe667;</a>
				
				<nav id="Hui-userbar"
					class="nav navbar-nav navbar-userbar hidden-xs">
					<ul class="cl">
						<li class="dropDown dropDown_hover">
							<a href="#" class="dropDown_A">${currentAccount.userName} <i class="Hui-iconfont">&#xe6d5;</i></a>
							<ul class="dropDown-menu menu radius box-shadow">
								<li><a href="javascript:;" onClick="myselfinfo()">个人信息</a></li>
								<li><a href="javascript:;" onClick="changePwd()">修改密码</a></li>
								<li><a href="${ctx}/loginout">退出</a></li>
							</ul>
						</li>
						
						<li id="Hui-skin" class="dropDown right dropDown_hover">
							<a href="javascript:;" class="dropDown_A" title="换肤"><i class="Hui-iconfont" style="font-size: 18px">&#xe62a;</i></a>
							<ul class="dropDown-menu menu radius box-shadow">
								<li><a href="javascript:;" data-val="default" title="默认（黑色）">默认（黑色）</a></li>
								<li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
								<li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
								<li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
								<li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
								<li><a href="javascript:;" data-val="orange" title="橙色">橙色</a></li>
							</ul>
						</li>
					</ul>
				</nav>
			</div>
		</div>
	</header>
	
	<aside class="Hui-aside">
		<div class="menu_dropdown bk_2">
			<dl id="menu-article">
				<dt class="selected">
					<i class="Hui-iconfont">&#xe616;</i> 签名管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd style="display: block;">
					<ul>
						<li><a data-href="${ctx}/app/newList" data-title="新增App" href="javascript:void(0)">新增App</a></li>
						<li><a data-href="${ctx}/app/list" data-title="APP管理" href="javascript:void(0)">App管理</a></li>
						<li><a data-href="${ctx}/statistic/expire" data-title="即将到期" href="javascript:void(0)">即将到期</a></li>
						<li><a data-href="${ctx}/combo/list" data-title="套餐管理" href="javascript:void(0)">套餐管理</a></li>
						<li><a data-href="${ctx}/scale/list" data-title="分成管理" href="javascript:void(0)">分成管理</a></li>
					</ul>
				</dd>
			</dl>
			
			<dl id="menu-tongji">
				<dt>
					<i class="Hui-iconfont">&#xe61a;</i> 统计<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd>
					<ul>
						<li><a data-href="${ctx}/statistic/income" data-title="收入记录" href="javascript:void(0)">收入记录</a></li>
						<li><a data-href="${ctx}/statistic/monthStatistic" data-title="每天收入详情" href="javascript:void(0)">每天收入详情</a></li>
					</ul>
				</dd>
			</dl>
			
			<dl id="menu-system">
				<dt>
					<i class="Hui-iconfont">&#xe62e;</i> 系统管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd>
					<ul>
						<li><a data-href="${ctx}/accountlist" data-title="用户管理" href="javascript:void(0)">用户管理</a></li>
						<li><a data-href="${ctx}/operationlog/list" data-title="系统日志" href="javascript:void(0)">系统日志</a></li>
					</ul>
				</dd>
			</dl>
		</div>
	</aside>
	
	<div class="dislpayArrow hidden-xs">
		<a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a>
	</div>
	
	<section class="Hui-article-box">
		<div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
			<div class="Hui-tabNav-wp">
				<ul id="min_title_list" class="acrossTab cl">
					<li class="active">
						<span title="首页" data-href="${ctx}/home">首页</span>
						<em></em>
					</li>
				</ul>
			</div>
			<div class="Hui-tabNav-more btn-group">
				<a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;">
					<i class="Hui-iconfont">&#xe6d4;</i>
				</a>
				<a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;">
					<i class="Hui-iconfont">&#xe6d7;</i>
				</a>
			</div>
		</div>
		<div id="iframe_box" class="Hui-article">
			<div class="show_iframe">
				<div style="display: none" class="loading"></div>
				<iframe scrolling="yes" frameborder="0" src="${ctx}/home"></iframe>
			</div>
		</div>
	</section>

	<div class="contextMenu" id="Huiadminmenu">
		<ul>
			<li id="closethis">关闭当前</li>
			<li id="closeall">关闭全部</li>
		</ul>
	</div>
	
	<!--_footer 作为公共模版分离出去-->
	<script type="text/javascript" src="${ctx}/resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/layer/2.4/layer.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/H-ui.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/H-ui.admin.js"></script>
	<!--/_footer 作为公共模版分离出去-->

	<!--请在下方写此页面业务相关的脚本-->
	<script type="text/javascript" src="${ctx}/resources/js/jquery.contextmenu/jquery.contextmenu.r2.js"></script>
	<script type="text/javascript">
		/*个人信息*/
		function myselfinfo() {
			layer_show("个人信息", "${account}/account/info", '600', '400');
		}
		
		function changePwd(){
			layer_show("修改密码", "${account}/account/changePwd", '500', '300');
		}
	</script>


</body>
</html>