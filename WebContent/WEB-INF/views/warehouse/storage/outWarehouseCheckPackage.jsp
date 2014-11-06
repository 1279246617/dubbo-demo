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

	<div class="pull-left" style="width:49%;height:100%; margin-top: 5px;margin-left: 5px;'" >
		<table class="table">
			<tr>
				<td colspan="3" style="height:70px;">
					客户订单号(捡货单的订单号)&nbsp;&nbsp;
					<input type="text"  name="customerReferenceNo"   t="1" id="customerReferenceNo"  style="width:160px;"/>
					&nbsp;&nbsp;
					订单状态<input type="text"  name="status"   t="1" id="status"  style="width:60px;" readonly="readonly"/>
						
					<input type="text" name="outWarehouseOrderId" id="outWarehouseOrderId" style="display:none;">
				</td>
			</tr>
			
			<tr>
				<th colspan="3">复核SKU数量:请扫描产品SKU(按回车,继续扫描下一个产品)</th>
			</tr>
			<tr>
				<td colspan="2" style="height:60px;">
					产品SKU&nbsp;&nbsp;
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
					装箱重量&nbsp;&nbsp;
					<input type="text"  name="outWarehouseOrderWeight"  t="3"  id="outWarehouseOrderWeight"  style="width:90px;"/>KG
					<i  id="weightOk" style="display:none;" class="icon-ok icon-blue">
				</td>
				<td colspan="1">
					<a class="btn  btn-primary"  onclick="saveOutWarehouseOrderWeight();"  style="cursor:pointer;">
						<i class="icon-ok icon-white"></i>保存重量
					</a>
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
						<th>产品SKU</th>
						<th>出库订单数量</th>
						<th>实际出库数量</th>
					</tr>
				</thead>
				<tbody id="skusTbody">
				</tbody>
			</table>
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
	   $("#customerReferenceNo").focus();
	   var focus= "1";
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
  	 		if(focus == "1"){
				submitCustomerReferenceNo();
  	 		}
  	 		if(focus == "2"){
  	 			countSku();
  	 		}
  	 		if(focus == "3"){
  	 			saveOutWarehouseOrderWeight();
  	 		}
  		}
  	 	 
 	 	function submitCustomerReferenceNo(){
 	 		var customerReferenceNo  = $("#customerReferenceNo").val();
 	 		$("#shipwayCode").val("");
			$("#trackingNo").val("");
			$("#skusTbody").html("");
			$("#weightOk").hide();
			$("#outWarehouseOrderId").val("");
			$("#status").val("");
 	 		$.post(baseUrl+ '/warehouse/storage/outWarehouseSubmitCustomerReferenceNo.do?customerReferenceNo='+ customerReferenceNo, function(msg) {
 	 				if(msg.status == 0){
 	 					parent.$.showShortMessage({msg:msg.message,animate:false,left:"45%"});
 	 					return;
 	 				}
 	 				if(msg.status == 1){
					$("#shipwayCode").val(msg.shipwayCode);
					$("#trackingNo").val(msg.trackingNo);
					$("#outWarehouseOrderId").val(msg.outWarehouseOrderId);
					$("#status").val(msg.outWarehouseOrderStatus);
					var skus = msg.outWarehouseOrderItemList;
					$.each(skus, function(i, n) {
						var tr = "<tr>";
						tr+="<td style='width:205px;text-align:left;'>"+n.sku+"</td>";
						tr+="<td style='width:205px;text-align:left;'>"+n.quantity+"</td>";
						tr+="<td style='width:205px;text-align:left;'><input readonly='readonly' type='text'  id='sku_"+n.sku+"' value='0'></td>";
						tr+="</tr>";
						$("#skusTbody").append(tr);
					});
 	 				return;
 	 				}
 	 			},"json");
 	 		
 	 	 }
 	 	
 	 	//复核SKU
 	 	function countSku(){
 	 		var sku = $("#sku").val();
 	 		var quantity = $("#quantity").val();
 	 		var oldQuantity = $("#sku_"+sku).val();
 	 		
 	 		if(oldQuantity!=undefined){
 	 			$("#sku_"+sku).val(parseInt(oldQuantity) + parseInt(quantity));	
 	 		}
 	 	}
 	 	 
 	 	 //保存重量
 	 	 function saveOutWarehouseOrderWeight(){
 	 		var outWarehouseOrderWeight = $("#outWarehouseOrderWeight").val();
 	 		var customerReferenceNo  = $("#customerReferenceNo").val();
 	 		$.post(baseUrl+ '/warehouse/storage/outWarehouseSubmitWeight.do?customerReferenceNo='+ customerReferenceNo+'&outWarehouseOrderWeight='+outWarehouseOrderWeight, function(msg) {
	 				if(msg.status == 0){
	 					parent.$.showShortMessage({msg:msg.message,animate:false,left:"45%"});
	 					$("#weightOk").hide();
	 					return;
	 				}
	 				if(msg.status == 1){
	 					parent.$.showShortMessage({msg:"保存出库订单总重量成功",animate:false,left:"45%"});
	 					$("#weightOk").show();
	 				}
	 		},"json");
 	 	 }
  	 	 	
 	 	 //打印出货运单标签
 	 	 function printShipLabel(){
 	 		var customerReferenceNo  = $("#customerReferenceNo").val();
 	 		var outWarehouseOrderId  = $("#outWarehouseOrderId").val();
 	 		if(customerReferenceNo!='' &&  outWarehouseOrderId !=''){
		 		 var url = baseUrl+'/warehouse/print/printShipLabel.do?orderIds='+outWarehouseOrderId;
				 window.open(url); 	 			
 	 		}
 	 	 }
 	 	 
 	  	 $(function(){
 		  		$("input").focus(function(){
 		  			focus = $(this).attr("t");
 		  		});
 	   	});
    </script>	
</body>
</html>