<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>登陆</title>

<script src="${ctx}/3rd/jquery/jquery.min.js"></script>
<script src="${ctx}/static/bootstrap-3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${ctx}/3rd/jquery-cookie/src/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/js/config.js?xx=3"></script>
<script type="text/javascript" src="${ctx}/js/common.js?xx=4"></script>
<script type="text/javascript"
	src="${ctx}/3rd/template-web/template-web.js"></script>
<link href="${ctx}/static/bootstrap-3.3.6/css/bootstrap.min.css"
	rel="stylesheet">

<!--[if lt IE 9]>
        <script src="${ctx}/static/js/html5shiv.js"></script>
        <script src="${ctx}/static/js/respond.min.js"></script>
    <![endif]-->

</head>
<script type="text/javascript">
	$(function() {
		//获取仓库信息
		//globalUrl.symgmtPath
		$.ajax({
			url : globalUrl.symgmtPath
					+ "/symgmt/wareHouse/getAllParentWarehouse",
			success : function(data) {
				//loadWareHouse
				if (data.code != 0) {
					mmui.alert(data.msg, 2, 2000);
					return;
				}
				var html = template('loadWareHouse', data);
				$("#wareHouse").html(html)
			},
			dataType : 'json'
		})

	})

	//根据仓库加载配置
	function getMata() {
		//获取选中项
		var opSelected = $("#wareHouse  option:selected");
		var wareHouseId = $("#wareHouse").val();
		
		if (wareHouseId == -1) {
			return;
		}
		var wareHouseCode = opSelected.attr('code');
		
		refreshConfig(wareHouseCode)
		
		
		

	}
	//登录
	function loginSys() {
		var loginName = $("#loginNameId").val();
		var password = $("#passwordId").val();

		if (loginName == '' || password == '') {
			$("body")
					.append(
							'<p style="height:35px;line-height:35px;text-align:center;color: #f00;">用户名或密码不能为空</p>')
			return;
		}

		//获取仓库选中项
		var opSelected = $("#wareHouse  option:selected");
		var wareHouseId = $("#wareHouse").val()
		if (wareHouseId == -1) {
			$("body")
					.append(
							'<p style="height:35px;line-height:35px;text-align:center;color: #f00;">请选择仓库</p>')
			return;
		}
		var warehouseCode=opSelected.attr('code')
		//提交登录
		var reqParam='{"loginName":"'+loginName+'","password":"'+password+'","warehouseCode":"'+warehouseCode+'","warehouseId":"'+wareHouseId+'"}'
		$.ajax({
			url : globalUrl.symgmtPath+"/symgmt/admin/login",
			data:reqParam,
			dataType:'json',
			type:"POST",
			contentType:"application/json",
			success : function(data) {
                   if(data.code!=0){
                	   $("body").append(
   							'<p style="height:35px;line-height:35px;text-align:center;color: #f00;">'+data.data+'</p>')
   		              	return;
                   }else{
                	   location.href="${ctx}/jsp/admin/main.jsp"
                   }
			}
		});
		
		return false;
	}
</script>


<script id="loadWareHouse" type="text/html">  
                    <option value="-1" >请选择仓库</option>
                    {{each data as value index}}
                          <option value="{{value.id}}" code="{{value.whseCode}}" >{{value.whseName}}</option>
                    {{/each}}  
</script>

<body style="background-color: #f8f8f8;">

	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4" style="margin-top: 15%;">
				<img src="${ctx}/static/img/logo2.png" style="margin-bottom: 10px;">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">仓配系統</h3>
					</div>
					<div class="panel-body">
						<form method="post" action="/admin/login" id="formId" role="form"
							onsubmit="return loginSys()">
							<fieldset>


								<div class="form-group">
									<select class="form-control" id="wareHouse" name="warehouseId"
										onchange="getMata()">


									</select>
								</div>
								<div class="form-group">
									<input class="form-control" placeholder="login name"
										name="loginName" id="loginNameId" type="text" autofocus>
								</div>
								<div class="form-group">
									<input class="form-control" placeholder="login password"
										name="password" type="password" id="passwordId" value="">
								</div>
								<div class="checkbox">
									<label> <input name="remember" type="checkbox"
										value="Remember Me">Remember Me
									</label>
								</div>
								<!-- Change this to a button or input when using this as a form -->
								<input type="button" onclick="loginSys()" value="Login"
									class="btn btn-lg btn-success btn-block">
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%--     <c:if test="${not empty msg}"> --%>
	<%-- 		<p style="height:35px;line-height:35px;text-align:center;color: #f00;">${msg}</p> --%>
	<!-- 	</c:if> -->



</body>

</html>
