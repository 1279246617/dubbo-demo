<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link href="${baseUrl}/static/css/common.css" rel="stylesheet" type="text/css" />
<link href="${baseUrl}/static/bootstrap/bootstrap.min.css" rel="stylesheet"type="text/css" />
<link href="${baseUrl}/static/bootstrap/common.css" rel="stylesheet" type="text/css"/>
<link href="${baseUrl}/static/lhgdialog/prettify/common.css" type="text/css" rel="stylesheet" />
<link href="${baseUrl}/static/lhgdialog/prettify/prettify.css" type="text/css" rel="stylesheet" />
<link href="${baseUrl}/static/calendar/prettify.css" rel="stylesheet" type="text/css" />
<link href="${baseUrl}/static/calendar/lhgcalendar.css" rel="stylesheet" type="text/css" />
<link href="${baseUrl}/static/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />

<title>COE</title>
</head>
<body>

	<div class="pull-left" style="width:44%;height:140px; margin-top: 1px;" >
		扫捡货单上清单号(顺丰海淘交易id)
		扫SKU和输入数量 与 出库订单对比,复核
	</div>
	
	
	<div class="pull-right" style="width:55%;height:140px; margin-top: 1px;" >
		
		称总重,打出货运单
	</div>
	
		
	 <script type="text/javascript" src="${baseUrl}/static/jquery/jquery.js"></script>
    <script type="text/javascript" src="${baseUrl}/static/bootstrap/bootstrap-typeahead.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.showMessage.js"></script>
	<script  type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/core/base.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligeruiPatch.js"></script>
    <script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligerTab.js"></script>
    <script  type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligerTree.js" ></script>
    
    <script type="text/javascript">
	   var baseUrl = "${baseUrl}";
	   //进入页面,焦点跟踪单号
	   $("#trackingNo").focus();
	    $(window).keydown(function(event){
	    	//回车事件
	    	if((event.keyCode   ==   13)) {
	    		clickEnter();
	    		return;
	    	}  
	    	//空格
			if(event.keyCode == 32 && !$(event.target).is('input,textarea')){
				return;
			}    	
	    	if((event.keyCode   ==   13) &&   (event.ctrlKey)) {
	    	}
	    });
 
  	 	 //回车事件
  	  	function clickEnter(){
  	 
  		}
    </script>	
</body>
</html>