<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" ctx="${ctx}">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="${ctx}/3rd/mm-3.3/skin/mmui.css">
    <link href="${ctx}/3rd/bootstrap-3.3.6/css/bootstrap.css" rel="stylesheet">
	<link href="${ctx}/3rd/bootstrap-3.3.6/css/bootstrap-theme.min.css" rel="stylesheet">
	<link href="${ctx}/3rd/font-awesome-4.2.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	 <link href="${ctx}/3rd/bootstrap-table/src/bootstrap-table.css" rel="stylesheet">
	
    <script src="${ctx}/3rd/jquery/jquery.min.js"></script>
    <script src="${ctx}/3rd/bootstrap-3.3.6/js/bootstrap.min.js"></script>
    
	<script type="text/javascript" src="${ctx}/3rd/mm-3.3/mm-base.js"  ></script>
	<script type="text/javascript" src="${ctx}/3rd/mm-3.3//mm-alert.js"  ></script>
<%-- 	<script type="text/javascript" src="${ctx}/3rd/mm-3.3//mm-table.js"  ></script> --%>
<%-- 	<script type="text/javascript" src="${ctx}/3rd/mm-3.3/mm-table-extend.js"  ></script> --%>


    <!--[if lt IE 9]>
        <script src="${ctx}/3rd/js/html5shiv.js"></script>
        <script src="${ctx}/3rd/js/respond.min.js"></script>
    <![endif]-->
    
    <!--  时间插件  -->
	<link href="${ctx}/3rd/datetimepicker/css/bootstrap-datetimepicker.css" rel="stylesheet">
	<script src="${ctx}/3rd/datetimepicker/js/bootstrap-datetimepicker.js"></script>
	<script src="${ctx}/3rd/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
	
	<script type="text/javascript"  src="${ctx}/3rd/bootstrap-table/src/bootstrap-table.js?xx=13"></script>
	
	<script type="text/javascript"  src="${ctx}/3rd/bootstrap-table/src/locale/bootstrap-table-zh-CN.js"></script>
	
	<script type="text/javascript" src="${ctx}/3rd/jquery-cookie/src/jquery.cookie.js"></script>
	
	<script type="text/javascript" src="${ctx}/3rd/template-web/template-web.js"></script>
	
	<script type="text/javascript" src="${ctx}/js/config.js?xx=9"></script>
	<script type="text/javascript" src="${ctx}/js/common.js?xx=7"></script>
	<script type="text/javascript" src="${ctx}/js/diylqg.js?xx=17"></script>
	
	
	<script type="text/javascript">
	
	document.domain="coewms.com";
	
	$(function(){
		getButton();
		loadConfig();
		ctx="${ctx}"
		
	})
	//
	</script>
