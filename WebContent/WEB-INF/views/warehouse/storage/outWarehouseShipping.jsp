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
	<div class="pull-left" style="width:100%;height:80px; margin-top: 1px;" >
			<table class="table table-striped" style="width:100%;margin-bottom: 5px">
					<tr style="height:15px;">
							<td>
									<span class="pull-left" style="width:72px;">出货运单号</span>
									<span class="pull-left" style="width:191px;">
										<input type="text"  name="trackingNo"  id="trackingNo"   style="width:190px;"/>
									</span>
									<span class="pull-left" style="margin-left: 10px;">
										扫描出库装箱时打印的运单上的条码
									</span>
							</td>		
							<td>
		          				 <a class="btn  btn-primary" id="enter" onclick="clickEnter()" style="cursor:pointer;"><i class="icon-ok icon-white"></i>确认出库</a>
							</td>
					</tr>
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
  	 		 var trackingNoStr = $("#trackingNo").val();
	  	  	$.post(baseUrl+ '/warehouse/storage/outWarehouseShippingConfirm.do?trackingNo='+ trackingNoStr, function(msg) {
	  	  		if(msg.status == 0){
	  	  			parent.$.showShortMessage({msg:msg.message,animate:true,left:"45%"});
	  	  			return false;
	  	  		}
	  	  		if(msg.status == 1){
	  	  			parent.$.showShortMessage({msg:"确认出库成功.",animate:true,left:"45%"});
	  	  			// 光标移至跟踪号
	  	  			$("#trackingNo").focus();
	  	  			$("#trackingNo").select();
	  	  			return false;
	  	  		}
	  	  	},"json");
  		}
    </script>	
</body>
</html>