<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="meta.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>登陆</title>

    <link href="${ctx}/3rd/bootstrap-3.3.6/css/bootstrap.min.css" rel="stylesheet">

    
</head>

<body style="background-color: #f8f8f8;">
<div class="btn-group" style="float:right">
	 <button class="btn btn-default">简体中文</button> <button data-toggle="dropdown" class="btn btn-default dropdown-toggle"><span class="caret"></span></button>
	<ul class="dropdown-menu">
		<li>
			 <a href="#">简体中文</a>
		</li>
		<li class="disabled">
			 <a href="#">繁体中文</a>
		</li>
		<!-- <li class="divider">
		</li> -->
		<li>
			 <a href="#">English</a>
		</li>
	</ul>
</div>
	<input type="hidden" id="successPath" value="${ctx}/jsp/admin/main.jsp">
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4" style="margin-top:5%;">
            <img src="${ctx}/3rd/img/logo2.png" style="margin-bottom:10px;">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">财务结算系统</h3>
                    </div>
                    <div class="panel-body">
                        <form method="post" action="" role="form" id="login">
                            <fieldset>
                           		<div class="form-group">
                                    <input class="form-control" placeholder="所属站点" name="siteCode" type="text" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="用户ID" name="userCode" type="text">
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="密码" name="password" type="password" value="">
                                </div>
                                <div class="checkbox">
                                    <label>
                                        <input name="remember" type="checkbox" value="Remember Me" >记住我
                                    </label>
                                </div>
                                <!-- Change this to a button or input when using this as a form -->
                                <input type="submit" id="submit" value="Login" class="btn btn-lg btn-success btn-block">
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
	<p style="height:35px;line-height:35px;text-align:center;color: #f00;"></p>
	
    <script src="${ctx}/3rd/jquery/jquery.min.js"></script>
    <script src="${ctx}/3rd/bootstrap-3.3.6/js/bootstrap.min.js"></script>
    <script type="text/javascript">
    	$("#submit").click(function(){
    		
    		var urlPath = url.path+'/fcs-web-symgr/symgr/login/loginAction.json';
    		mmUtl.ajax.postJsonSyn(urlPath, function(data){
    			if(data.code==0){
	    			window.location.href = $("#successPath").val();
    			}
    		}, mmUtl.ajax.getArgs($("#login")))
    		
    		return false;
    	});
    </script>
</body>

</html>
