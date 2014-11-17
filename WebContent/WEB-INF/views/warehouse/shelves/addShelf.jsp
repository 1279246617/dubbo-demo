<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${baseUrl}/static/css/common.css" rel="stylesheet" type="text/css" />
<link href="${baseUrl}/static/bootstrap/bootstrap.min.css" rel="stylesheet"type="text/css" />
<link href="${baseUrl}/static/bootstrap/common.css" rel="stylesheet" type="text/css"/>
<title>COE</title>
</head>
<body >
	<div id="step1">
		<div class="pull-left" style="width:100px;">
				<span class="badge badge-success">1</span>货架类型:	
		</div>
		<div class="pull-left" style="width:100px;">
			<input type="radio" name="shelfType">地面货架
		</div>
		<div class="pull-left" style="width:100px;">	
			<input  type="radio" name="shelfType">立体货架
		</div>
	</div>	
		
	<div id="step2" style="margin-top: 2mm;">
		<!-- 地面货架  display: none;-->
		<table style="width:100%;" class="table">
			<tr>
				<th><span class="badge badge-success">2</span>货位设置:</th>
				<th>
					货位起始:<input type="text" name="start" style="width:40px;">
				</th>
				<th>
					货位截止:<input type="text" name="end"  style="width:40px;">
				</th>
			</tr>
		</table>		
		<!-- 立体货架 -->
		<table style="width:100%;" class="table">
			<tr>
				<th><span class="badge badge-success">2</span>货位设置:</th>
				<th>
					层数:<input type="text" name="rows" style="width:40px;">
				</th>
				<th>
					列数:<input type="text" name="cols"  style="width:40px;">
				</th>
			</tr>
		</table>
	</div>	
	
	<div id="step3">
	
		
	</div>
	
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.js"></script>
    <script type="text/javascript" src="${baseUrl}/static/bootstrap/bootstrap-typeahead.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.showMessage.js"></script>
    <script type="text/javascript">
			   
    </script>	
</body>
</html>