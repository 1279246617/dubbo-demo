<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报文列表</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/thirdparty/jquery-easyui-1.5/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/thirdparty/jquery-easyui-1.5/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/thirdparty/jquery-easyui-1.5/themes/color.css" />
<script src="<%=basePath%>/static/thirdparty/jquery-easyui-1.5/jquery.min.js" charset="utf-8"></script>
<script src="<%=basePath%>/static/thirdparty/jquery-easyui-1.5/jquery.easyui.min.js" charset="utf-8"></script>
<script src="<%=basePath%>/static/thirdparty/jquery-easyui-1.5/locale/easyui-lang-zh_CN.js"></script>
<script src="<%=basePath%>/static/thirdparty/My97DatePicker/WdatePicker.js"></script>
<script src="<%=basePath%>/static/js/msgList.js"></script>
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

#search-top {
	width: 1380px;
	height: 35px;
	line-height: 35px;
	margin-left: 20px;
}

#request-msg-win {
	display: none;
	width: 430px;
	height: 400px;
	background-color: #fff;
}

#request-msg-win table {
	text-align: left;
	margin-top: 20px;
	margin-left: 10px;
	font-size: 14px;
}

#request-msg-win td {
	padding: 5px;
}
#resp-msg-new-win{
	width:380px;
	height:320px;
	display:none;
}
#resp-msg-new-win td{
	padding:5px;
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
				<li><a href="<%=basePath%>/loginUser/toIndex.do">首页</a></li>
				<li><a href="<%=basePath%>/message/toMsgList.do" style="font-size: 20px; color: lime;">报文列表</a></li>
			</ul>
		</div>
	</div>
	<div region="center" class="easyui-layout" style="overflow: auto;">
		<div region="north" style="height: 40px;" collapsible="false">
			<div id="search-top">
				<span>唯一编号:<input type="text" style="width: 120px;" id="requestId" /></span>
				<span>第一关键字:<input type="text" style="width: 120px;" id="keyword1" /></span>
				<span>第二关键字:<input type="text" style="width: 120px;" id="keyword2" />
				</span> <span>第三关键字:<input type="text" style="width: 120px;" id="keyword3" /></span>
				<span>接收时间： <input onClick="WdatePicker();" type="text" id="startTime" />—<input onClick="WdatePicker();" type="text" id="endTime" /></span>
				 <a href="javascript:queryDataList(false);" class="easyui-linkbutton" iconcls="icon-search" style="margin-left: 10px;">查询</a>
				 <a href="javascript:reloadData();" class="easyui-linkbutton" iconcls="icon-reload" style="margin-left: 5px;">刷新数据</a>
				 <a href="javascript:cleanInputData();"class="easyui-linkbutton" iconcls="icon-no" style="margin-left: 5px;">清空输入框</a>
			</div>
		</div>
		<div region="center">
			<table id="msgTable"></table>
		</div>
	</div>
	<div region="south" style="height: 40px; line-height: 40px; text-align: center;">深圳秀驿国际物流</div>
	<div id="request-msg-win">
		<table>
			<tr>
				<td>请求地址:</td>
				<td><input type="text" id="url" style="width: 260px;"></td>
			</tr>
			<tr>
				<td>请求body:</td>
				<td><input type="text" id="body" /></td>
			</tr>
			<tr>
				<td>请求头(header):</td>
				<td><input type="text" id="header-params" style="width: 260px;" /></td>
			</tr>
			<tr>
				<td>请求参数:</td>
				<td><input type="text" id="body-params" style="width: 260px;" /></td>
			</tr>
			<tr>
				<td>请求方式:</td>
				<td><input type="text" id="method" /></td>
			</tr>
			<tr>
				<td>读取超时时间(ms):</td>
				<td><input type="text" id="socket-time-out" /></td>
			</tr>
			<tr>
				<td>连接超时时间(ms):</td>
				<td><input type="text" id="connection-time-out" /></td>
			</tr>
			<tr>
				<td>回调地址:</td>
				<td><input type="text" id="callback-url" style="width: 260px;" /></td>
			</tr>
			<tr>
				<td>创建时间:</td>
				<td><input type="text" id="created-time" /></td>
			</tr>
		</table>
	</div>
	<div id="resp-msg-new-win">
		<table style="margin-top:10px;margin-left:10px;">
			<tr>
			   <td>http状态码</td>
			   <td><input type="text" id="http-status"/></td>
			</tr>
			<tr>
			   <td>状态码信息</td>
			   <td><input type="text" id="http-status-msg" style="width:260px;"/></td>
			</tr>
			<tr>
			   <td>响应头信息</td>
			   <td><input type="text" id="response-header" style="width:260px;"/></td>
			</tr>
			<tr>
			   <td>响应body信息</td>
			   <td><input type="text" id="response-body" style="width:260px;"/></td>
			</tr>
			<tr>
			   <td>发送起始时间</td>
			   <td><input type="text" id="send-begin-time"/></td>
			</tr>
			<tr>
			   <td>发送结束时间</td>
			   <td><input type="text" id="send-end-time"/></td>
			</tr>
			<tr>
				<td>耗时(毫秒ms)</td>
				<td><input type="text" id="used-time"/></td>
			</tr>
			<tr>
				<td>创建时间</td>
				<td><input type="text" id="created-time"/></td>
			</tr> 
		</table>
	</div>
</body>
</html>