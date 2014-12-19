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
<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.js"></script>
<script type="text/javascript" src="${baseUrl}/static/js/warehouse/outWarehouseCheckPackage.js"></script>
<title>COE</title>
</head>
<body style="font-size: 16px;">

	<div class="pull-left" style="width:49%;height:100%; margin-top: 5px;margin-left: 5px;'" >
		<table class="table">
			<tr>
				<td colspan="3" style="height:70px;">
					客户订单号(捡货单的右上角)&nbsp;&nbsp;
					<input type="text"  name="customerReferenceNo"   t="1" id="customerReferenceNo"  style="width:160px;"/>
					&nbsp;&nbsp;
					订单状态<input type="text"  name="status"   t="1" id="status"  style="width:60px;" readonly="readonly"/>
						
					<input type="text" name="outWarehouseOrderId" id="outWarehouseOrderId" style="display:none;">
				</td>
			</tr>
			
			<tr>
				<th colspan="3">复核SKU数量:请扫描商品条码(按回车,继续扫描下一个商品)</th>
			</tr>
			<tr>
				<td colspan="2" style="height:60px;">
					商品条码&nbsp;&nbsp;
					<input type="text"  name="sku"  id="sku"  t="2"  style="width:120px;"/>
				</td>
				<td>
					数量&nbsp;&nbsp;
					<input type="text"  name="quantity"  id="quantity"   t="2" value="1"  style="width:90px;"/>
				</td>
			</tr>
		</table>	
		
		
		<table class="table">
			<tr>
				<th colspan="3">称出库总重量</th>
			</tr>
			<tr>
				<td colspan="2" style="height:70px;">
					<span style="width:90px;height:30px;margin-top: 4mm;font-size: 5mm;" class="pull-left" >装箱重量</span>
<!-- 					<input type="text"  name="weight"  t="3"  id="weight"  style="width:130px;height:60px; font-size: 10mm;font-weight: bold;color:red;" class="pull-left" onkeyup="this.value=this.value.replace(/[^\d\.]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d\.]/g,'')"/> -->
					<input type="text"  name="weight"  t="3"  id="weight"  style="width:130px;height:60px; font-size: 10mm;font-weight: bold;color:red;" class="pull-left" readonly="readonly" onkeyup="this.value=this.value.replace(/[^\d\.]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d\.]/g,'')"/>
					<span style="width:50px;height:30px;margin-top: 4mm;font-size: 6mm;font-weight: bold;" class="pull-left" >KG</span>
					<span style="width:90px;height:30px;margin-top: 4mm;font-size: 4mm;" class="pull-left" >
						<input class="pull-left" name="auto" style="vertical-align: middle;" type="checkbox" checked="checked" id="auto">
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
			<tr>
				<th colspan="3">打印出货运单</th>
			</tr>
			<tr>
				<td colspan="1">
					出货渠道&nbsp;&nbsp;
					<input type="text"  name="shipwayCode"  id="shipwayCode"   t="4" style="width:90px;" readonly="readonly"/>
				</td>
				<td colspan="1">
					跟踪单号&nbsp;&nbsp;
					<input type="text"  name="trackingNo"  id="trackingNo"  t="4"  style="width:130px;" readonly="readonly"/>
				</td>
					
				<td colspan="1">
					<a class="btn  btn-primary"  onclick="printShipLabel();" style="cursor:pointer;">
						<i class="icon-ok icon-white"></i>打印出货运单
					</a>
				</td>
			</tr>
		</table>	
		
	</div>
		
		
	<div class="pull-right" style="width:50%;height:1000px; margin-top: 1px;border-left:1px solid  #333;" >
			<table class="table" id="skus">
				<thead>
					<tr>
						<th colspan="3">复核SKU数量和出库订单SKU数量</th>
					</tr>
					<tr>
						<th>商品条码</th>
						<th>出库订单数量</th>
						<th>实际出库数量</th>
					</tr>
				</thead>
				<tbody id="skusTbody">
				</tbody>
			</table>
	</div>
	 
    <script type="text/javascript" src="${baseUrl}/static/bootstrap/bootstrap-typeahead.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.showMessage.js"></script>
	<script  type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/core/base.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligeruiPatch.js"></script>
    <script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligerTab.js"></script>
    <script  type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligerTree.js" ></script>
	<script  type="text/javascript" src="${baseUrl}/static/js/warehouse/webSocketReadScales.js" ></script>    
    <script type="text/javascript">
	   var baseUrl = "${baseUrl}";
	   //进入页面,焦点跟踪单号
	   $("#customerReferenceNo").focus();
	   var focus= "1";
		var autoWeight;
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
 	  	 $(function(){
 		  		$("input").focus(function(){
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