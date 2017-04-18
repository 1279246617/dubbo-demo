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

    <link href="${ctx}/static/bootstrap-3.3.6/css/bootstrap.min.css" rel="stylesheet">

    
</head>

<body style="background-color: #f8f8f8;">

    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4" style="margin-top:15%;">
            <img src="${ctx}/static/img/logo2.png" style="margin-bottom:10px;">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">转运系統</h3>
                    </div>
                    <div class="panel-body">
                        <form method="post" action="${ctx}/jsp/admin/main.jsp" role="form">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="login name" name="userName" type="text" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="login password" name="passWord" type="password" value="">
                                </div>
                                <div class="checkbox">
                                    <label>
                                        <input name="remember" type="checkbox" value="Remember Me" >Remember Me
                                    </label>
                                </div>
                                <!-- Change this to a button or input when using this as a form -->
                                <input type="submit" value="Login" class="btn btn-lg btn-success btn-block">
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
	<p style="height:35px;line-height:35px;text-align:center;color: #f00;"></p>
	
    <script src="${ctx}/static/jquery/jquery.min.js"></script>
    <script src="${ctx}/static/bootstrap-3.3.6/js/bootstrap.min.js"></script>
</body>

</html>
