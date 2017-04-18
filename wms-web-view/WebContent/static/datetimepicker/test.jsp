<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en" ctx="${ctx}">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
<title>首页</title>
    <script src="${ctx}/static/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/mm/mmui.js"  ></script>
	<link href="${ctx}/static/bootstrap-3.3.6/css/bootstrap.css" rel="stylesheet">
	<link href="${ctx}/static/bootstrap-3.3.6/css/bootstrap-theme.min.css" rel="stylesheet">
	
	<script src="${ctx}/static/bootstrap-3.3.6/js/bootstrap.min.js"></script>
	
	<link href="${ctx}/static/datetimepicker2/css/bootstrap-datetimepicker.css" rel="stylesheet">
	<script src="${ctx}/static/datetimepicker2/js/bootstrap-datetimepicker.js"></script>
	<script src="${ctx}/static/datetimepicker2/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
	
	<link href="${ctx}/static/calendar/lhgcalendar.css" rel="stylesheet">
	<script src="${ctx}/static/calendar/lhgcalendar.js"></script>
</head>
<body>

<div style="width:100%;height:200px;text-align:center;line-height:200px;font-size:22px;">欢迎</div>

				<div style="width:200px;margin-left:300px;" class="input-group date form_date">
					<input class="form-control" size="16" type="text" value="">
					<span class="input-group-addon"> 
						<span class="glyphicon glyphicon-calendar"></span>
					</span>
				</div>
				
<script type="text/javascript">
mmUtl.time.setDateTime(".form_date");
</script> 





</body>
</html>