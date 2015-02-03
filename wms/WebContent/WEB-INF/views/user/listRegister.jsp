<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>COE</title>
<link href="${baseUrl}/static/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${baseUrl}/static/css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.js"></script>
</head>
<body>
	<div class="toolbar">
    	<span class="pull-left" style="width:105px;position:relative;left:160px;">
     		<span class="pull-left" style="width:260px;color:red;"  id="noticeUpdateTips"></span>
    	</span>
    </div>
        <br>
	<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<form action="#${baseUrl}/user/saveUpdateListRegister.do" method="post" class="form-horizontal">
				<div class="control-group">
					<label class="control-label" for="loginName"><span class="hasError">*</span>帐号：</label>
					<div class="controls">
						<input type="text" class="input-medium" readonly="readonly" id="userId"
							name="loginName" value="${userId}" style="display:none;">
						<input type="text" class="input-medium" readonly="readonly" id="loginName"
							name="loginName" value="${loginName}"> <span
							class="help-inline ${field.errorClass}">${field.error}</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="userName"><span class="hasError">*</span>姓名：</label>
					<div class="controls">
						<input type="text" id="userName" name="userName"
							class="input-medium" value="${userName}"> 
						<span class="help-inline ${field.errorClass}">${field.error}</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="phoneNumber"><span class="hasError">*</span>电话：</label>
					<div class="controls">
						<input type="text" id="phoneNumber" name="phoneNumber"
							class="input-medium" value="${phoneNumber}"> <span
							class="help-inline ${field.errorClass}">${field.error}</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="email"><span class="hasError">*</span>邮箱：</label>
					<div class="controls">
						<input type="text" id="email" name="email"
							class="input-medium" value="${email}"> <span
							class="help-inline ${field.errorClass}">${field.error}</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="oldPassword"><span class="hasError">*</span>原密码：</label>
					<div class="controls">
						<input type="password" id="oldPassword" name="oldPassword"
							class="input-medium"> <span
							class="help-inline ${field.errorClass}">${field.error}</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="newPassword">新密码：</label>
					<div class="controls">
						<input type="password" id="newPassword" name="newPassword"
							class="input-medium"> <span
							class="help-inline ${field.errorClass}">${field.error}</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="newPasswordcheck">确认密码：</label>
					<div class="controls">
						<input type="password" id="newPasswordcheck" name="newPasswordcheck"
							class="input-medium"> <span
							class="help-inline ${field.errorClass}">${field.error}</span>
					</div>
				</div>
				<div class="form-actions">
					<span class="pull-left" style="width:105px;">
							<a class="btn  btn-primary" id="btnSave" onclick="saveUpdateListRegister();" style="cursor:pointer;"><i class="icon-ok icon-white"></i>提交</a>
					</span>
				</div>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript">
	function saveUpdateListRegister(){
		var baseUrl = "${baseUrl}";
		var userId = $("#userId").val();
		var loginName = $("#loginName").val();
		var userName = $("#userName").val();
		var phoneNumber = $("#phoneNumber").val();
		var email = $("#email").val();
		var oldPassword = $("#oldPassword").val();
		var newPassword = $("#newPassword").val();
		var newPasswordcheck = $("#newPasswordcheck").val();
		
		if (oldPassword == null || oldPassword == ""){
			$("#oldPassword").val("");
			$("#newPassword").val("");
			$("#newPasswordcheck").val("");
			$("#noticeUpdateTips").html("注册信息修改无效！");
			parent.$.showShortMessage({msg:"请输入原始密码！",animate:false,left:"40%"});
			$("#oldPassword").focus();
			return;
		}
		if (newPassword != null && newPassword !="" && newPassword != newPasswordcheck){
			$("#oldPassword").val("");
			$("#newPassword").val("");
			$("#newPasswordcheck").val("");
			$("#noticeUpdateTips").html("注册信息修改无效！");
			parent.$.showShortMessage({msg:"两次输入新密码不一致！",animate:false,left:"40%"});
			$("#oldPassword").focus();
			return;
		}
		
		$.post(baseUrl+ '/user/saveUpdateListRegister.do',{
			userId:userId,
			loginName:loginName,
			userName:userName,
			phoneNumber:phoneNumber,
			email:email,
			oldPassword:oldPassword,
			newPassword:newPassword
		},function(msg) {
			if(msg.status == 0){	//保存失败,显示提示
				$("#oldPassword").val("");
				$("#newPassword").val("");
				$("#newPasswordcheck").val("");
				$("#noticeUpdateTips").html("注册信息修改无效！");
				parent.$.showShortMessage({msg:msg.message,animate:false,left:"40%"});
				$("#oldPassword").focus();
				return;
			}
			if(msg.status == 1){	//保存失败,显示提示
				$("#oldPassword").val("");
				$("#newPassword").val("");
				$("#newPasswordcheck").val("");
				$("#noticeUpdateTips").html("注册信息修改成功！");
				parent.$.showShortMessage({msg:"注册信息修改成功！",animate:false,left:"40%"});
				return;
			}
		},"json");
	}
</script>
</body>
</html>