<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="${baseUrl}/static/ligerui/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
        body{ padding:20px; margin:0;}
        #accordion1{ width:160px;overflow:hidden;}
    </style>
<script type="text/javascript" src="${baseUrl}/static/ligerui/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/base.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerui.all.js"></script>
	
<%-- 	<script type="text/javascript" src="${baseUrl}/static/ligerui/jquery/jquery-1.3.2.min.js"></script>     --%>
<%--     <script type="text/javascript" src="${baseUrl}/static/ligerui/ligerTab.js"></script> --%>
<%--     <script type="text/javascript" src="${baseUrl}/static/jquery/jquery.cookie.js"></script> --%>
<%--     <script type="text/javascript" src="${baseUrl}/static/ligerui/json2.js"></script> --%>
    <script type="text/javascript" src="${baseUrl}/static/ligerui/ligerTree.js" ></script>
    <script type="text/javascript" src="${baseUrl}/static/ligerui/ligerAccordion.js" ></script>
	
<%--    	<script type="text/javascript" src="${baseUrl}/static/lhgdialog/prettify/prettify.js"></script> --%>
<%-- 	<script type="text/javascript" src="${baseUrl}/static/lhgdialog/prettify/lhgdialog.js"></script> --%>
	
	<script type="text/javascript">
		var baseUrl = "${baseUrl}",tab = null,manager=null;
		$(function() {
			$("#accordion1").ligerAccordion({
				height : 300
			});
		});
	</script>
</head>

<body>
	
	<div id="accordion1" class="l-accordion-panel" ligeruiid="accordion1"style="height: 300px;">
					<div class="l-accordion-header">
						<div class="l-accordion-toggle l-accordion-toggle-open"></div>
						<div class="l-accordion-header-inner">功能列表</div>
					</div>
					<div title="" class="l-accordion-content" style="height: 225px;">
						<ul>
							<li>列表一</li>
							<li>列表二</li>
							<li>列表三</li>
							<li>列表四</li>
							<li>列表五</li>
						</ul>
					</div>
					<div class="l-accordion-header l-accordion-header-downfirst">
						<div class="l-accordion-toggle l-accordion-toggle-close"></div>
						<div class="l-accordion-header-inner">列表</div>
					</div>
					<div title="" class="l-accordion-content" style="display: none; height: 225px;">
						<ul>
							<li>列表一</li>
							<li>列表二</li>
							<li>列表三</li>
							<li>列表四</li>
							<li>列表五</li>
						</ul>
					</div>
					<div class="l-accordion-header">
						<div class="l-accordion-toggle l-accordion-toggle-close"></div>
						<div class="l-accordion-header-inner">其他</div>
					</div>
					<div title="" style="padding: 10px; display: none; height: 225px;" class="l-accordion-content">其他内容</div>
				</div>
	
	
</body>
</html>