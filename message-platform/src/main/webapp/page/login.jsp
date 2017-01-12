<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=basePath%>/static/thirdparty/jquery-2.1.0.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/login.js"></script>
<title>登录</title>
<style type="text/css">
body {
	margin: 0 auto;
	background-attachment: fixed;
	background-repeat: no-repeat;
	background-size: cover;
	-moz-background-size: cover;
	-webkit-background-size: cover;
	background-image: url(../../static/images/login_bg2.jpg);
}

#login-div {
	width: 360px;
	height: 345px;
	border: 1px gray solid;
	float: right;
	position: relative;
	z-index: 1000;
	top: 240px;
	right: 260px;
	background-color: #fff;
}

#login-top {
	height: 40px;
	background-color: #2d2d2d;
	color: #fff;
	line-height: 40px;
}

#login-form-div {
	height: 265px;
	margin-top: 10px;
}

.input-class {
	height: 25px;
	width: 220px;
}

#login-form-div td {
	padding: 10px;
}

#form-button-div {
	height: 100px;
	margin-top: 10px;
}

#login-notice {
	height: 30px;
	line-height: 30px;
	text-align: center;
	color: red;
	font-size: 14px;
}

#login-msg {
	height: 30px;
	line-height: 30px;
	text-align: center;
	color: red;
	font-size: 14px;
}

#submitBtn {
	width: 300px;
	height: 40px;
	background-color: #619428;
	color: #fff;
	margin-left: 25px;
}

#resetBtn {
	width: 300px;
	height: 40px;
	background-color: #016dd4;
	color: #fff;
	margin-left: 25px;
	margin-top: 10px;
}
</style>
</head>
<body>
	<div id="login-div">
		<div id="login-top">
			<span style="margin-left: 10px; font-weight:bold;">报文推送系统登录</span>
		</div>
		<div id="login-form-div">
			<form>
				<table>
					<tr>
						<td>账号:</td>
						<td><input class="input-class" type="text" name="userName" id="userName" /></td>
					</tr>
					<tr>
						<td>密码:</td>
						<td><input class="input-class" type="password" name="password" id="password"></td>
					</tr>
				</table>
			</form>
			<div id="form-button-div">
				<input type="button" id="submitBtn" value="登录" onclick="doLogin();"/>
				<input type="button" id="resetBtn" value="重置" onclick="reSet();"/>
			</div>
			<div id="login-notice"></div>
			<div id="login-msg"></div>
		</div>
	</div>
</body>
</html>