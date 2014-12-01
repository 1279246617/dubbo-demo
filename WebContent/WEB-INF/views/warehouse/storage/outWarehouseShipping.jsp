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
								<input id="coeTrackingNo" name="coeTrackingNo" value="${coeTrackingNo}"  t="1"  style="width:150px;"/>
						</td>
						<td colspan="1"  style="height:25px;">
							<a class="btn  btn-primary" id="submit" onclick="submit()" style="cursor:pointer;height:20px;"><i class="icon-ok icon-white"></i>
								完成出货
							</a>
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
    
    	
	<script type="text/javascript" src="${baseUrl}/static/lhgdialog/prettify/prettify.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/lhgdialog/prettify/lhgdialog.js"></script>
	
    <script type="text/javascript">
	   var baseUrl = "${baseUrl}";
	   $("#coeTrackingNo").focus();
	    $(window).keydown(function(event){
	    	if((event.keyCode   ==   13)) {
	    		submit();
	    	}  
	    });
 		 
  	 	 var isSubmintIng = 'N';//是否在提交过程中. 
  	 	 function submit(){
  	 		 if(isSubmintIng == "Y"){//防止重复点击
  	 			 return false;
  	 		 }
  	 		 $("#coeTrackingNo").attr("readonly","readonly");
  	 		 $("#submit").attr("disabled","disabled");
  	 		 isSubmintIng ="Y";
				  	 		 
  	 		 var coeTrackingNo = $("#coeTrackingNo").val();
	 		 if(coeTrackingNo == null || coeTrackingNo == ""){
		 			isSubmintIng = 'N';	
	  	 			$("#coeTrackingNo").removeAttr("readonly");
	  	 			$("#submit").removeAttr("disabled");
	 			 	
		 			parent.$.showDialogMessage("请输入有效的COE单号并按回车!", null, null);
		 			return false;
		 	}
  	 		$.post(baseUrl+ '/warehouse/storage/outWarehouseShippingConfirm.do',{
  	 			coeTrackingNo:coeTrackingNo
  	 		},function(msg) {
  	 			isSubmintIng = 'N';	
  	 			$("#coeTrackingNo").removeAttr("readonly");
  	 			$("#submit").removeAttr("disabled");
  	 			
  	 			if(msg.status == 0){
  	 				parent.$.showDialogMessage(msg.message, null, null);
	  	  			return false;
  	 			}
				if(msg.status >0){
					parent.$.showShortMessage({msg:msg.message, animate:false,left:"45%"});						
				}
  	 		},"json");
  	 	 }
    </script>	
</body>
</html>