<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>COE</title>
<link href="${baseUrl}/static/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${baseUrl}/static/css/main.css" rel="stylesheet" type="text/css" />
</head>
<body onkeypress="clickButton()" onload="onPageload();">	
	<div style="width: 400px; margin-left: auto; margin-right: auto; margin-top: 120px">
		<div id="header" class="navbar-top" style="height: 60px; margin-top: 60px"">
			<div class="navbar-inner_top">
				<div class="navbar">
					<div class="container">
						<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> 
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
						</a> <a href="#" style="float: left; margin-top: 5px"></a>
						<div class="nav-collapse">
						<img src="${baseUrl}/static/img/logo-index.png">
					</div>
				</div>
			</div>
		</div>
	</div>
		
	<div style="margin-top: 25px">
		<form action="${baseUrl}/user/loginCheck.do" method="post"
			class="form-horizontal" id="form">
			<fieldset>
				<div class="control-group">
					<label class="control-label" for="loginName">帐号：</label>
					<div class="controls">
						<input type="text" class="input-small" id="loginName" name="loginName" value="${loginName}">
						<span class="help-inline " style="color:red" >${message}</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="loginPassword">密码：</label>
					<div class="controls">
						<input type="password"  class="input-small"  id="loginPassword" name="loginPassword" value="${loginPassword}">
					</div>
				</div>
				<div class="control-group">
					<label for="rememberMe" class="pull-left" style="margin-left: 100px">
							<input class="pull-left" type="checkbox" name="rememberMe" id="rememberMe" value="Y" 
								<c:if test="${rememberMe == 'Y'}">
									   		 checked
								</c:if>
							/>
						记住我
					</label>
				</div>
				<div style="margin-left: 160px">
					<button type="submit" class="btn btn-primary" style="width: 96px;height: 33px;">登录</button>
				</div>
				<div class="form-actions" >
					 
				</div>	
			</fieldset>
		</form>
	</div>
</body>
<script type="text/javascript">
	function clickButton() {
		if (event.keyCode == 13) {
			document.getElementById('form').submit();
		}
	}
</script>
</html>