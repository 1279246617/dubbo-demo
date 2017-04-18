<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html lang="en" ctx="${ctx}">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="${ctx}/static/jquery/jquery.min.js"></script>
    
    <script type="text/javascript" src="${ctx}/static/mm-3.2/mmui.js"  ></script>
    <link rel="stylesheet" href="${ctx}/static/mm-3.2/skin/mmui.css">
    
    <!--  bootstrap  -->
	<link href="${ctx}/static/bootstrap-3.3.6/css/bootstrap.css" rel="stylesheet">
	<link href="${ctx}/static/bootstrap-3.3.6/css/bootstrap-theme.min.css" rel="stylesheet">
	<script src="${ctx}/static/bootstrap-3.3.6/js/bootstrap.min.js"></script>
	
	<!--  图标插件  -->
    <link href="${ctx}/static/font-awesome-4.2.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!--[if lt IE 9]>
        <script src="${ctx}/static/js/html5shiv.js"></script>
        <script src="${ctx}/static/js/respond.min.js"></script>
    <![endif]-->
    
    <!--  时间插件  -->
	<link href="${ctx}/static/datetimepicker/css/bootstrap-datetimepicker.css" rel="stylesheet">
	<script src="${ctx}/static/datetimepicker/js/bootstrap-datetimepicker.js"></script>
	<script src="${ctx}/static/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>

	<!-- form 表单验证插件 -->
	<%-- <script src="${ctx}/3rd/validator/jquery.validator.min.js"></script>
	<link href="${ctx}/3rd/validator/jquery.validator.css" rel="stylesheet" type="text/css"> --%>
	
	<!-- 引入common -->
	<script src="${ctx}/js/common.js"></script>