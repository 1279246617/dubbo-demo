<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/thirdparty/jquery-easyui-1.5/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/thirdparty/jquery-easyui-1.5/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/thirdparty/jquery-easyui-1.5/themes/color.css" />
<script src="<%=basePath%>/static/thirdparty/jquery-easyui-1.5/jquery.min.js" charset="utf-8"></script>
<script src="<%=basePath%>/static/thirdparty/jquery-easyui-1.5/jquery.easyui.min.js" charset="utf-8"></script>
<script src="<%=basePath%>/static/thirdparty/jquery-easyui-1.5/locale/easyui-lang-zh_CN.js"></script>
<style type="text/css">
body {
	margin: 0;
	paddin: 0;
	background: #fff;
}

ul li {
	list-style: none;
	float: left;
}

#body-top {
	height: 50px;
}

#nav-top {
	height: 30px;
	background: #609dd2;
	color: #fff;
	line-height: 30px;
	font-size: 16px;
	font-weight: bold;
}

#menu-ul li {
	margin-left: 30px;
}

#menu-ul a {
	color: #fff;
	text-decoration: none;
}
</style>
</head>
<body class="easyui-layout">
	<div region="north" class="easyui-layout" style="height: 110px; border: none;">
		<div region="north" id="body-top">
			<img src="<%=basePath%>/static/images/logo.png" style="height: 30px;" />
			<ul style="float: right;">
				<li style="margin-right: 60px;">欢迎用户：<span id="userName" style="color: blue;">${loginUser.userName}</span>
			</ul>
		</div>
		<div region="center" id="nav-top">
			<ul id="menu-ul">
				<li><a href="<%=basePath%>/loginUser/toIndex.do" style="font-size:20px;color:lime;">首页</a></li>
				<li><a href="<%=basePath%>/message/toMsgList.do">报文列表</a></li>
			</ul>
		</div>
	</div>
	<div region="center" style="text-align: center; font-size: 40px; color: #337ab7;">
		<marquee width="330" direction="right" behavior="alternate" scrollamount="2" style="margin-top: 300px;"> 报文推送系统 </marquee>
	</div>
	<div region="south" style="height: 40px; line-height: 40px; text-align: center;">深圳秀驿国际物流 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
</body>
</html>