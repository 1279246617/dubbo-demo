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
<body style="font-size: 16px;">
	 	<div class="pull-left" style="width:50%;height:95%; margin-top: 15px;margin-left: 35px;'" >
		<div class="pull-left" style="width:100%;">
				<div class="pull-left badge badge-success" style="width:20px; margin-left:-20px; font-weight: bold;">1</div>
				<div style="font-weight: bold;height:30px;">扫描跟踪单号</div>
			
				<span style="width:100px;" class="pull-left" >
					头程跟踪单号
				</span>
				<span style="width:200px;" class="pull-left" >
					<input class="pull-left"  type="text"  name="firstWaybillTrackingNo"   t="1" id="firstWaybillTrackingNo"  style="width:150px;" title="请输入捡货单上的客户订单号"/>
					<input  type="text" name="firstWaybillId" id="firstWaybillId" style="display:none;">
					<input  type="text" name="orderId" id="orderId" style="display:none;">
				</span>
				<span class="pull-left"  style="width:100px;">订单状态</span>
				<span style="width:100px;" class="pull-left" >
					<input class="pull-left"  type="text"  name="status"   t="1" id="status"  style="width:150px;" readonly="readonly"/>
				</span>	
		</div>
		
		<div class="pull-left" style="width:100%;margin-top: 10px;">
				<span style="width:100px;" class="pull-left" >
					出货渠道
				</span>
				<span style="width:200px;" class="pull-left" >
					<input type="text"  name="shipwayCode"  id="shipwayCode"   t="2" style="width:150px;" readonly="readonly"/>
				</span>
				
				<span class="pull-left"  style="width:100px;">出货跟踪单号</span>
				<span style="width:100px;" class="pull-left" >
					<input type="text"  name="trackingNo"  id="trackingNo"  t="2"  style="width:150px;" readonly="readonly"/>
				</span>
		</div>
				
		<div class="pull-left"  style="width:100%; margin-top: 50px;">
			<div class="pull-left badge badge-success" style="width:20px;font-weight: bold; margin-left:-20px;">2</div>
			<div style="font-weight: bold;height:30px;">称出库总重量</div>
			<span class="pull-left"  style="width:100px;">装箱重量</span>
			<span style="width:500px;" class="pull-left" >
				<input type="text"  name="weight"  t="2"  id="weight"  style="width:150px; font-size: 4mm;font-weight: bold;color:red;" class="pull-left" readonly="readonly" onkeyup="this.value=this.value.replace(/[^\d\.]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d\.]/g,'')"/>
				<span style="width:50px;height:30px; font-size: 5mm;font-weight: bold;" class="pull-left" >KG</span>
				<span style="width:90px;height:30px; font-size: 4mm;" class="pull-left" >
					<input class="pull-left" t="2" name="auto" style="vertical-align: middle;" type="checkbox" checked="checked" id="auto">
					自动读取
				</span>
			</span>
		</div>	
		
		<div class="pull-left"  style="width:100%; margin-top: 20px;">
			<span style="width:300px;height:30px;font-size: 5mm;" class="pull-left" >
					<a class="btn  btn-primary"  onclick="saveweight();"  style="cursor:pointer;"><i class="icon-ok icon-white"></i><b>完成装箱</b></a>
			</span>
			<span style="width:90px;height:30px; font-size: 4mm;" class="pull-left" >
					<input class="pull-left" t="2" name="andPrint" style="vertical-align: middle;" type="checkbox" checked="checked" id="andPrint">
					打印运单
			</span>
			<span style="width:90px;height:30px; font-size: 4mm;" class="pull-left" >
					<input class="pull-left" t="2" name="printWithPreview" style="vertical-align: middle;" type="checkbox"  id="printWithPreview">
					预览打印
			</span>
		</div>
		
</div>
<div class="pull-right" style="width:45%;height:95%; ">
	<div style="font-size:38mm; margin-top: 225px; height:76mm;width:140mm; ">
			<font color="red"  id="weight2"></font>
	</div>
</div>		
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.js"></script>
    <script type="text/javascript" src="${baseUrl}/static/bootstrap/bootstrap-typeahead.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.showMessage.js"></script>
	<script  type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/core/base.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligeruiPatch.js"></script>
    <script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligerTab.js"></script>
    <script  type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligerTree.js" ></script>
	
    <script type="text/javascript" src="${baseUrl}/static/calendar/lhgcalendar.min.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/lhgdialog/prettify/prettify.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/lhgdialog/prettify/lhgdialog.js"></script>
    
    <script  type="text/javascript" src="${baseUrl}/static/js/warehouse/webSocketReadScales.js" ></script>
    <script  type="text/javascript" src="${baseUrl}/static/js/warehouse/transport/directOrderWeightAndPrint.js" ></script>
    
    
    <script type="text/javascript">
	   var baseUrl = "${baseUrl}";
	   var baseUrl = "${baseUrl}";
	   //进入页面,焦点跟踪单号
	   $("#firstWaybillTrackingNo").focus();
	   var focus= "1";
	   var autoWeight;
	   $(window).keydown(function(event){
	    	//回车事件
	    	if((event.keyCode   ==   13)) {
	    		if(focus == "1"){
	    			submitFirstWaybillTrackingNo();
	    		}
	    		if(focus == "2"){
	    			saveweight();
	    		}
	    		return;
	    	}  
	    	//空格
			if(event.keyCode == 32 && !$(event.target).is('input,textarea')){
				return;
			}    	
	    	if((event.keyCode   ==   13) &&   (event.ctrlKey)) {
	    	}
	    });
 	  	 $(function(){
 		  		$("input").focus(function(){
 		  			focus = $(this).attr("t");
 		  		});
 		  		window.setInterval(function(){ 
 		  			$("#weight2").html($('#weight').val());
				}, 100);
 		  		
 		  		//加载页面时,启动读取电子秤
 		  		toggleConnection(ports[0]);
 		  		//启动读取电子秤
 		  		autoWeight = window.setInterval(function(){ 
 		  			ws.send("getweig");		 
				}, 200);
				//自动读取 		  	
 		  		$("#auto").click(function(){
 		  			if($("#auto").attr("checked")=="checked"){
 					 	onAuto();
 		 	  		 }else{
 		 	  			offAuto();
 		 	  		}
 		  		});
 	   	});
 	  	 	
 	  	 //开启读取
 	  	 function onAuto(){
 	  			$("#auto").attr("checked",true);
	  			$("#weight").attr("readonly","readonly");
				//启动读取电子秤
	 	  		autoWeight = window.setInterval(function(){ 
		  			ws.send("getweig");		 
				}, 300);
 	  	 }
 	  	 
  		//取消读取 	  	 
 	  	 function offAuto(){
 	  		$("#auto").attr("checked",false);
 	  		$("#weight").removeAttr("readonly");
 	  		 clearInterval(autoWeight);
 	  	 }
    </script>	
    
</body>
</html>