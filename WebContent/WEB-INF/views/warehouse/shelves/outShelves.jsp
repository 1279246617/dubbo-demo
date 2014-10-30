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
	<div class="pull-left" style="width:100%;margin-top: 1px;overflow-x: hidden;overflow-y: auto;height: 205px;" >
			<table class="table table-striped" style="width:100%;margin-bottom: 5px">
					<tr style="height:15px;">
							<td style="width:252px;">
									<span class="pull-left" style="width:66px;">客户订单号</span>
									<span class="pull-left" style="width:170px;">
										<!--  利用focus和blur 判断跟踪号是否有变化, 变化则把入库订单id清空-->
										<input type="text"  name="customerReferenceNo"  id="customerReferenceNo" t="1"   style="width:140px;"/>
									</span>
							</td>		
							<td>
								<span class="pull-left" style="width:55px;" ><b>操作提示:</b></span>
								<span class="pull-left" style="width:352px;color:red;" id="tips">请输入捡货单上右上角的订单号并按回车!</span>
							</td>
					</tr>
			</table>
			<div style="height:30px;">
			</div>
				
		<table class="table table-striped" style="width:1200px;margin-bottom: 0px">
			<tr>
					<td  >
						<span class="pull-left" style="width:52px;">货位</span>
						<span class="pull-left" style="width:160px;">
							<input type="text"  name="seatCode" t="2"  id="seatCode" style="width:130px;"/>
						</span>
						
						<span class="pull-left" style="width:52px;">产品SKU</span>
						<span class="pull-left" style="width:160px;">
							<input type="text"  name="sku" t="3"  id="sku" style="width:130px;"/>
						</span>
						
						<span class="pull-left" style="width:52px;">产品数量</span>
						<span class="pull-left" style="width:102px;">
							<input type="text"  name="quantity"  id="quantity" t="4" style="width:90px;" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
						</span>
						
						<span class="pull-left" style="width:145px;">
							<a class="btn  btn-primary" id="enterItem" onclick="saveOnShelvesItem();" style="cursor:pointer;"><i class="icon-ok icon-white"></i>继续下一货位</a>
						</span>
					</td>	
			</tr>	
		</table>
		
		<span class="pull-left" style="width:145px;height:50px; margin-top: 20px;margin-left: 7px;">
				<a class="btn  btn-primary" id="enterItem" onclick="submitOutShelf();" style="cursor:pointer;"><i class="icon-ok icon-white"></i>保存本订单下架</a>
		</span>
	</div>
	
	<div class="pull-left" style="width:100%; margin-top: 1px;overflow-x: hidden;overflow-y: auto;height: 345px;" >
		<table class="table table-striped" style="width:1200px;margin-bottom: 0px">
			<thead>
					<tr>
					<th>货位</th>
					<th>产品SKU</th>
					<th>产品数量</th>
				</tr>
			</thead>
			<tbody id="outShelfItemTbody">
				
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
	   $("#trackingNo").focus();
	   var focus = "1";
	    $(window).keydown(function(event){
	    	//回车事件
	    	if((event.keyCode   ==   13)) {
	    		clickEnter();
	    		return;
	    	}  
	    });
		
  	  $(function(){
	  		$("input").focus(function(){
	  			//当前获取焦点的文本框是 主单还是明细
	  			focus = $(this).attr("t");
	  			$(this).select();
	  		});
   		});
  	  	
  	  //回车事件
  	  function clickEnter(){
  		  	if(focus == '1'){
  		  		enterCustomerReferenceNoStep();
  		  		return false;
  		  	}
			if(focus == '2'){
				//输入货位后按回车,去到sku
				$("#sku").focus();
				focus = '3';
				return false;
			}
			if(focus == '3'){
				//输入sku后按回车,去到quantity
				$("#quantity").focus();
				focus = '4';
				return false;
			}
			if(focus == '4'){
				//输入数量,提交一次sku下架
				saveOutShelvesItem();
				return false;
			}
  	  }
	  	
  	  //输入客户参考号回车事件
  	 function enterCustomerReferenceNoStep(){
   		var customerReferenceNo  = $("#customerReferenceNo").val();
   		$("#outShelfItemTbody").html("");
   		outShelfItems = "";
   		// 检查客户订单号是否能找到唯一的出库订单
   		$.getJSON(baseUrl+ '/warehouse/shelves/checkFindOutWarehouseOrder.do?customerReferenceNo='+ customerReferenceNo, function(msg) {
   			if (msg.status == 1) {
   				//成功,并锁定输入框
   				$("#customerReferenceNo").attr("readonly","readonly");
   				$("#tips").html("请输入货位号并按回车!");
   				$("#seatCode").focus();
   				focus = '2';
   				return false;
   			}
   			if (msg.status == 0) {
   				parent.$.showDialogMessage(msg.message, null, null);
   				return false;
   			}
   		});
   	  }
	  
  	  
  	  var outShelfItems = "";//用于提交
  	  function saveOutShelvesItem(){
  			var customerReferenceNo  = $("#customerReferenceNo").val();
  			if(customerReferenceNo == null || customerReferenceNo ==''){
  				parent.$.showDialogMessage("请先输入客户订单号", null, null);
	  			return false;
  			}
	  		var sku = $("#sku").val();
	  		if(sku == null || sku ==''){
	  			parent.$.showShortMessage({msg:"请输入产品SKU",animate:true,left:"43%"});
	  			return false;
	  		}
	  		var quantity = $("#quantity").val();
	  		if(quantity == null || quantity ==''){
	  			parent.$.showShortMessage({msg:"请输入产品数量",animate:true,left:"43%"});
	  			return false;
	  		}
	  		var seatCode = $("#seatCode").val();
	  		if(seatCode == null || seatCode ==''){
	  			parent.$.showShortMessage({msg:"请输入货位",animate:true,left:"45%"});
	  			return false;
	  		}
	  		var tr = "<tr>";
		  		tr += "<td>"+seatCode+"</td>";
		  		tr += "<td>"+sku+"</td>";
		  		tr += "<td>"+quantity+"</td>";
		  		tr += "</tr>";
	  		$("#outShelfItemTbody").append(tr);
	  		outShelfItems +="seatCode:"+seatCode + ",sku:"+sku+",quantity:"+quantity+"||";
	  		//保存一个sku下架, 重置焦点,进入扫描货位
	  		$("#seatCode").focus();
  	  }
	
  	  function submitOutShelf(){
  		var customerReferenceNo  = $("#customerReferenceNo").val();
		if(customerReferenceNo == null || customerReferenceNo ==''){
			parent.$.showDialogMessage("请先输入客户订单号", null, null);
 			return false;
		}
  		$.post(baseUrl+ '/warehouse/shelves/submitOutShelfItems.do?customerReferenceNo='+customerReferenceNo, {
  			outShelfItems:outShelfItems
		}, function(map) {
			
			
		});
  	  }
  	  
    </script>	
</body>
</html>