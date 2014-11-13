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
	<div class="pull-left" style="width:50%;height:80px; margin-top: 1px;" >
			<table class="table table-striped" style="width:100%;">
					<tr>
						<td colspan="2" style="height:28px;">
								<span style="width:100px;" class="pull-left">COE交接单号</span>
								<input id="coeTrackingNo" name="coeTrackingNo" value="${coeTrackingNo}"  t="1"  style="width:150px;" readonly="readonly"/>
								<input id="coeTrackingNoId" name="coeTrackingNoId" value="${coeTrackingNoId}"  t="1" style="display: none;"/>
								<a class="btn  btn-primary" id="enter" onclick="enClick()" style="cursor:pointer;height:20px;"><i class="icon-ok icon-white"></i>手动输入交接单号</a>
								
						</td>
					</tr>
					<tr style="height:65px;">
							<td>
								<span style="width:100px;" class="pull-left" >出货跟踪单号</span>
								<input type="text"  name="trackingNo"  id="trackingNo"    t="2"  style="width:150px;" title="扫描出库装箱时打印的运单上的条码"/>
								 <input   style="margin-left: 30px;" id="add"  name="addOrSub"  t="2" type="radio" checked>加
								 <input   style="margin-left: 30px;" id="sub" name="addOrSub"  t="2" type="radio">减
							</td>		
							
							<td>
<!-- 		          				 <a class="btn  btn-primary" id="enter" onclick="next()" style="cursor:pointer;"><i class="icon-ok icon-white"></i>继续下一票</a> -->
							</td>
					</tr>
					<tr>
						<td colspan="1" rowspan="2" style="height:25px;">
							<a class="btn  btn-primary" id="enter" onclick="submitAll(1)" style="cursor:pointer;height:20px;"><i class="icon-ok icon-white"></i>
								完成出货
							</a>
						</td>
					</tr>
			</table>
			
			
	</div>
	
	<div class="pull-right" style="width:50%;margin-top: 1px;" >
			<table class="table table-striped" style="width:100%;">
					<tr>
							<td colspan="1" style="height:28px;text-align: center">已扫描的出货跟踪单号记录, 数量:<span id="total" style="margin-left: 5px;">0</span></td>
					</tr>
					<tbody id="trackingNos">
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
    
    	
	<script type="text/javascript" src="${baseUrl}/static/lhgdialog/prettify/prettify.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/lhgdialog/prettify/lhgdialog.js"></script>
	
    <script type="text/javascript">
	   var baseUrl = "${baseUrl}";
	   var orderIds = "";
	   //进入页面,焦点跟踪单号
	   $("#trackingNo").focus();
	   var focus ="";
		$(function(){
		  		$("input").focus(function(){
		  			//当前获取焦点的文本框是 主单还是明细
		  			focus = $(this).attr("t");
		  		});
	   	});
	   
	    $(window).keydown(function(event){
	    	if((event.keyCode   ==   13) &&   (event.ctrlKey)) {
	    		submitAll();
	    		return;
	    	}
	    	//回车事件
	    	if((event.keyCode   ==   13)) {
	    		if(focus =='1'){
	    			enterCoeTrackingNo();
	    		}
				if(focus =='2'){
					next();	
	    		}
	    		return;
	    	}  
	    });
 		//手动输入coe单号
	    function enterCoeTrackingNo(){
	    	var coeTrackingNo = $("#coeTrackingNo").val();
			$("#total").html(0);
			$("#coeTrackingNoId").val("");
			$("#trackingNo").val("");
			$("#trackingNos").html("");
			$.post(baseUrl+ '/warehouse/storage/outWarehouseShippingEnterCoeTrackingNo.do?&coeTrackingNo='+coeTrackingNo, function(msg) {
	  	  		if(msg.status == 0){
	  	  			parent.$.showDialogMessage(msg.message, null, null);
	  	  			return false;
	  	  		}
	  	  		if(msg.status == 1){
	  	  			var coeTrackingNoId = msg.coeTrackingNo.id;
	  	  			$("#coeTrackingNoId").val(coeTrackingNoId);
		  	  		$.each(msg.outWarehouseShippingList,function(i,e){
			  			//添加从数据库查出来的运单
						var tr = "<tr style='height:25px;' id="+e.id+">";
				  		tr += "<td style='text-align: center'>"+e.ourWarehouseOrderTrackingNo+"</td>";
				  		tr += "</tr>";
			  			$("#trackingNos").append(tr);
			  			$("#total").html( parseInt($("#total").html()) + 1);
			  			orderIds +=e.outWarehouseOrderId+"||";
	    			});
	  	  			// 光标移至跟踪号
	  	  			$("#trackingNo").focus();
	  	  			$("#trackingNo").select();
	  	  			return false;
	  	  		}
	  	  	},"json");
	    }
	    
  	 	 //回车事件
  	  	function next(){
	 		var coeTrackingNo = $("#coeTrackingNo").val();
	 		var coeTrackingNoId = $("#coeTrackingNoId").val();
	 		 if(coeTrackingNo == null || coeTrackingNo == ""){
	 			parent.$.showDialogMessage("请输入有效的COE单号并按回车!", null, null);
	 			return false;
	 		 }
	 		 if(coeTrackingNoId ==""){
	 			parent.$.showDialogMessage("请输入有效的COE单号并按回车!", null, null);
	 			return false;
	 		 }
  	 		 var trackingNo = $("#trackingNo").val();
  	 		 if(trackingNo == null || trackingNo==''){
  	 			parent.$.showShortMessage({msg:"请输入出货跟踪单号再按回车!",animate:false,left:"43%"});
  	 			return false;	 
  	 		 }
 	 		var addOrSub = 1; //1标识加,2表示减
  	 		if(!$("#add").attr("checked")){
  	 			addOrSub = 2;
  	 		}
 	 		
	  	  	$.post(baseUrl+ '/warehouse/storage/checkOutWarehouseShipping.do?trackingNo='+ trackingNo+'&coeTrackingNoId='+coeTrackingNoId+'&coeTrackingNo='+coeTrackingNo+'&addOrSub='+addOrSub+"&orderIds="+orderIds, function(msg) {
	  	  		if(msg.status == 0){
	  	  			parent.$.showShortMessage({msg:msg.message,animate:false,left:"42%"});
	  	  			return false;
	  	  		}
	  	  		if(msg.status == 1){
	  	  			//添加一行运单
					var tr = "<tr style='height:25px;' id="+msg.outWarehouseShippingId+">";
			  		tr += "<td style='text-align: center'>"+trackingNo+"</td>";
			  		tr += "</tr>";
		  			$("#trackingNos").append(tr);
		  			$("#total").html( parseInt($("#total").html()) + 1);
		  			
		  			orderIds +=msg.orderId+"||";
		  			
	  	  			// 光标移至跟踪号
	  	  			$("#trackingNo").focus();
	  	  			$("#trackingNo").select();
	  	  			return false;
	  	  		}
	  	  		if(msg.status ==2){
	  	  			//删除
	  	  			$(msg.deleteShippingIds).html("");
	  	  			$("#total").html( parseInt($("#total").html()) - msg.sub);
	  	  			//后台处理 需要删除的orderId
	  	  			orderIds = msg.orderIds;
	  	  		}
	  	  	},"json");
  		}
  	 	 
  	 	 function submitAll(isPrint){
  	 		 if(trackingNos == null || trackingNos ==''){
  	 			parent.$.showShortMessage({msg:"请输入出货跟踪单号再按完成出货总单!",animate:false,left:"43%"});
  	 			return false;
  	 		 }
  	 		 var coeTrackingNoId = $("#coeTrackingNoId").val();
  	 		 var coeTrackingNo = $("#coeTrackingNo").val();
	 		 if(coeTrackingNo == null || coeTrackingNo == ""){
		 			parent.$.showDialogMessage("请输入有效的COE单号并按回车!", null, null);
		 			return false;
		 	}
	 		if(coeTrackingNoId ==""){
	 			parent.$.showDialogMessage("请输入有效的COE单号并按回车!", null, null);
	 			return false;
	 		}
  	 		 
  	 		$.post(baseUrl+ '/warehouse/storage/submitOutWarehouseShipping.do?orderIds='+ orderIds+'&coeTrackingNo='+coeTrackingNo+"&coeTrackingNoId="+coeTrackingNoId, function(msg) {
  	 			if(msg.status == 0){
  	 				parent.$.showDialogMessage(msg.message, null, null);
	  	  			return false;
  	 			}
				if(msg.status >0){
	  	  			//是否打印出货交接单
	  	  			if(isPrint ==1){
	  	  			 	 var contentArr = [];
		  	  		    contentArr.push('<div id="changeContent" style="padding:10px;width: 240px;">');
		  	  		    contentArr.push('   <div class="pull-left" style="width: 100%"><b>');
		  	  		    contentArr.push(msg.message);
		  	  		    contentArr.push('  </b></div>');
		  	  		    contentArr.push('</div>');
		  	  		    var contentHtml = contentArr.join('');
		  	  			$.dialog({
		  	  		  		lock: true,
		  	  		  		max: false,
		  	  		  		min: false,
		  	  		  		title: '是否打印出货交接单',
		  	  		  	     width: 260,
		  	  		         height: 60,
		  	  		  		content: contentHtml,
		  	  		  		button: [{
		  	  		  			name: '打印',
		  	  		  			callback: function() {
		  	  		            	var url = baseUrl+'/warehouse/print/printOutWarehouseEIR.do?coeTrackingNo='+coeTrackingNo+'&coeTrackingNoId='+coeTrackingNoId;
		  	     			  		window.open(url);
		  	  		  			}
		  	  		  		},{name: '取消'}]
		  	  		  	});
	  	 			}	
	  	  			if(isPrint!=1){
						if(msg.status == 2){
							parent.$.showDialogMessage(msg.message, null, null);	
						}else{
							parent.$.showShortMessage({msg:msg.message,animate:false,left:"43%"});
						}
	  	  			}
	  	  			orderIds = "";
	  	  			//成功,清空输入,进入下一批,重新分配COE单号
					$("#coeTrackingNo").val(msg.coeTrackingNo);
					$("#coeTrackingNoId").val(msg.coeTrackingNoId);
					$("#total").html(0);
					$("#trackingNo").val("");
					$("#trackingNos").html("");
					// 光标移至跟踪号
	  	  			$("#trackingNo").focus();
	  	  			$("#trackingNo").select();
					return false;
				}
  	 		},"json");
  	 	 }
	  	 	 
  	 	 function enClick(){
  	 		 $("#coeTrackingNo").removeAttr("readonly");
  	 	 }
    </script>	
</body>
</html>