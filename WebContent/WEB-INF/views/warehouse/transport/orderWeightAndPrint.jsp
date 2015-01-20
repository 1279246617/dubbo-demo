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
	<table class="table" style="width:100%;background-color:#f5f5f5;margin-bottom: 1px;">
			<tr>
				<td colspan="3" style="height:40px;">
					客户订单号&nbsp;&nbsp;
					<input type="text"  name="customerReferenceNo"   t="1" id="customerReferenceNo"  style="width:160px;"/>
					&nbsp;&nbsp;
					订单状态<input type="text"  name="status"   t="1" id="status"  style="width:120px;" readonly="readonly"/>
					<input type="text"  name="orderId"  id="orderId" t="1"  style="display: none;"/>
				</td>
			</tr>
	</table>
	
	<table class="table" style="width:100%;background-color:#f5f5f5;margin-bottom: 0px;">
				<tr>
					<th>
							扫描跟踪号码复核小包:&nbsp;&nbsp;&nbsp;<input style="width:200px;" id="trackingNo"    t="2" name="trackingNo">
					</th>
				</tr>
	</table>	
	<div style="width:100%;height:200px;overflow:auto;">
			<div style="width:9%;height:200px;margin-left:10px; float: left; ">
				<div style="margin-top: 10px;">
					<b>待扫描小包:</b>
				</div>	
			</div>			
			<table class="table" style="width:25%;margin-bottom: 1px;float: left;">
				<tbody  id="firstWaybillTrackingNos"></tbody>
			</table>
			
			<div style="width:9%;height:200px;margin-left:30px; float: left;">
				<div style="margin-top: 10px;">	
					<b>已扫描小包:</b>
				</div>	
			</div>
			<table class="table" style="width:25%;margin-bottom: 1px;float: left;">
				<tbody  id="scanTrackingNos"></tbody>
			</table>	  
	</div>
	  
	<table class="table" style="background-color:#f5f5f5;margin-bottom: 2px;"  >
			<tr>
				<th>转运订单称重与打出货运单</th>
			</tr>
			<tr  style="color:#555555;">
				<td colspan="2" style="height:50px;">
					<span style="width:90px;height:30px;margin-top: 4mm;font-size: 5mm;" class="pull-left" >装箱重量</span>
					<input type="text"  name="weight"  t="3"  id="weight"  style="width:130px;height:45px; font-size: 10mm;font-weight: bold;color:red;" class="pull-left" readonly="readonly" onkeyup="this.value=this.value.replace(/[^\d\.]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d\.]/g,'')"/>
					<span style="width:50px;height:30px;margin-top: 4mm;font-size: 6mm;font-weight: bold;" class="pull-left" >KG</span>
					<span style="width:90px;height:30px;margin-top: 4mm;font-size: 4mm;" class="pull-left" >
						<input class="pull-left"  t="3" name="auto" style="vertical-align: middle;" type="checkbox" checked="checked" id="auto">
						自动读取
					</span>
					<span style="height:30px;margin-top: 0mm;font-size: 8mm;" class="pull-left" >
						<img id="weightOk" src="${baseUrl}/static/img/nike.png" style="display:none;width:15mm;height:15mm;" >
					</span>
				</td>
				<td colspan="1">
					<span style="width:90px;height:30px;margin-top: 4mm;font-size: 5mm;" class="pull-left" >
							<a class="btn  btn-primary"  onclick="saveweight();"  style="cursor:pointer;"><i class="icon-ok icon-white"></i>保存重量</a>
					</span>
				</td>
			</tr>
			<tr  >
				<td colspan="1" style="width:280px;">
					出货渠道&nbsp;&nbsp;
					<input type="text"  name="shipwayCode"  id="shipwayCode"   t="4" style="width:130px;" readonly="readonly"/>
				</td>
				<td colspan="1" style="width:330px;">
					跟踪单号&nbsp;&nbsp;
					<input type="text"  name="outWarehouseTrackingNo"  id="outWarehouseTrackingNo"  t="4"  style="width:150px;" readonly="readonly"/>
				</td>
				<td colspan="1" >
					<a class="btn  btn-primary"  onclick="printShipLabel();" style="cursor:pointer;">
						<i class="icon-ok icon-white"></i>打印出货运单
					</a>
				</td>
			</tr>
	</table>			
	
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
    <script  type="text/javascript" src="${baseUrl}/static/js/warehouse/transport/orderWeightAndPrint.js" ></script>
    
    
    <script type="text/javascript">
	   var baseUrl = "${baseUrl}";
	   //进入页面,焦点跟踪单号
	   $("#customerReferenceNo").focus();
	   var focus = "1";
	    $(window).keydown(function(event){
	    	//回车加ctrl 下一单
	    	if((event.keyCode   ==   13) &&   (event.ctrlKey)) {
	    		nextInWarehouseRecord();
	    		return;
	    	}
	    	//回车事件
	    	if((event.keyCode   ==   13)) {
	    		if(focus == '1'){
	    			submitCustomerReferenceNo();
		  		  	return false;
	  		  	}
	    		//复核小包
				if(focus == '2'){
					checkFirstWaybill();
		      		return false;
				}
				//保存重量
				if(focus == '3'){
					saveweight();
		      		return false;
				}
				//打印运单
				if(focus == '4'){
					printShipLabel();
		      		return false;
				}
	    		return;
	    	}  
	    });
  	  	$(function(){
	  		$("input").focus(function(){
	  			//当前获取焦点的文本框是 主单还是明细
	  			focus = $(this).attr("t");
	  		});
	  		//加载页面时,启动读取电子秤
	  		toggleConnection(ports[0]);
	  		//启动读取电子秤
	  		autoWeight = window.setInterval(function(){ 
	  			ws.send("getweig");		 
			}, 300);
			 		  	
	  		$("#auto").click(function(){
	  			if($("#auto").attr("checked")=="checked"){
	  				//自动获取电子称数据
	 	  			$("#weight").attr("readonly","readonly");
	  				//启动读取电子秤
		 	  		autoWeight = window.setInterval(function(){ 
	 		  			ws.send("getweig");		 
					}, 300);
	 	  		 }else{
	 	  			 $("#weight").removeAttr("readonly");
	 	  		 	clearInterval(autoWeight);//取消读取
	 	  		 }
	  		});
   		});
    </script>	
    
</body>
</html>