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
    <style type="text/css"> 
 	</style>
</head>
<body>
	  <div class="toolbar1">
           <form action="${baseUrl}/warehouse/transport/getOrderData.do" id="searchform" name="searchform" method="post">
               <div class="pull-right searchContent">
               		<span class="pull-left" style="width:120px;">
               			仓库
               			<select style="width:80px;" id="warehouseId" name="warehouseId">
               				<option></option>
							<c:forEach items="${warehouseList}" var="w" >
				       	 		<option value="<c:out value='${w.id}'/>">
				       	 			<c:out value="${w.id}-${w.warehouseName}"/>
				       		 	</option>
				       		</c:forEach>
						</select>
               		</span>
               		
               		<span class="pull-left" style="width:145px;">
               			运输方式
               			<select style="width:80px;" id="shipway" name="shipway">
               				<option></option>
							<c:forEach items="${shipwayList}" var="shipway" >
				       	 		<option value="<c:out value='${shipway.code}'/>">
				       	 			<c:out value="${shipway.cn}"/>
				       		 	</option>
				       		 </c:forEach>
						</select>
               		</span>
               		
               		<span class="pull-left" style="width:120px;">
               			状态
               			<select style="width:80px;" id="status" name="status">
               				<option></option>
							<c:forEach items="${orderStatusList}" var="status" >
				       	 		<option value="<c:out value='${status.code}'/>">
				       	 			<c:out value="${status.cn}"/>
				       		 	</option>
				       		 </c:forEach>
						</select>
               		</span>
               		
               		<span class="pull-left" style="width:130px;">
               			客户
               			<input type="text" name="userLoginName" data-provide="typeahead"  id="userLoginName" style="width:85px;" title="请输入客户登录名" />
               		</span>
               		
					<span class="pull-left" style="width:170px;">
						客户订单号
						<input type="text"  name="customerReferenceNo"  id="customerReferenceNo"   style="width:90px;"/>
						<input type="text"  name="nos"  id="nos"   style="display:none;"/>
						<input type="text"  name="noType"  id="noType"   style="display:none;"/>
					</span>
               		
               		<span class="pull-left" style="width:175px;">
               			创建时间
	               		<input type="text"   style="width:120px;" name="createdTimeStart" id="createdTimeStart" title="起始创建时间">
               		</span>
               		
               		<span class="pull-left" style="width:170px;">
               			至	
               			<input type="text"   style="width:120px;" name="createdTimeEnd"  id="createdTimeEnd"  title="终止创建时间">
               		</span>
               		<span class="pull-left" style="width:55px;">
               			<a class="btn btn-primary btn-small" id="btn_search"><i class="icon-search icon-white"></i>搜索</a>
               			<input style=" visibility:hidden;">
               		</span>
               </div>
               
               <div class="pull-left">
               		<span class="pull-left" style="width:82px;">
			       		<a class="btn btn-primary btn-small" onclick="applyTrackingNo()" title="申请出库跟踪单号">
			           		 <i class="icon-folder-open"></i>申请单号
			       	 	</a>
			       	 	<input style=" visibility:hidden;">
		       	 	</span>
		       	 	
           			<span class="pull-left" style="width:60px;">
			       		<a class="btn btn-primary btn-small" onclick="checkOrder()" title="审核出库订单">
			           		 <i class="icon-eye-open"></i>审核
			       	 	</a>
			       	 	<input style=" visibility:hidden;">
		       	 	</span>
		       	 	<span class="pull-left" style="width:95px;">
			       		<a class="btn btn-primary btn-small" onclick="printOrder()" title="打印捡货单">
			           		 <i class="icon-folder-open"></i>打印捡货单
			       	 	</a>
			       	 	<input style=" visibility:hidden;">
		       	 	</span>
		       	 	<span class="pull-left" style="width:80px;">
			       		<a class="btn btn-primary btn-small" onclick="printShipLabel()" title="打印运单">
			           		 <i class="icon-folder-open"></i>打印运单
			       	 	</a>
			       	 	<input style=" visibility:hidden;">
		       	 	</span>
		       	 	
					<input type="text" name="trackingNoIsNull" id="trackingNoIsNull" style="display:none;">
													       	 	
		       	 	<span class="pull-left" style="width:85px;">
			       		<a class="btn btn-primary btn-small"  title="显示所有缺少跟踪单号的订单">
							<input type="checkbox"  style="margin-bottom: 0px;margin-top: 0px;"onclick="clickTrackingNoCheckBox()" id="trackingNoCheckBox" name="trackingNoCheckBox">无跟踪单号				       			
			       	 	</a>
			       	 	<input style=" visibility:hidden;">
		       	 	</span>
		       	 	
		    	</div>    
           </form>
	</div>
	<div id="maingrid" class="pull-left" style="width:100%;"></div>
	
	
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/bootstrap/bootstrap-typeahead.js"></script>
    	
   	<script type="text/javascript" src="${baseUrl}/static/js/warehouse/transport/listOrder.js"></script>
    <script type="text/javascript">
 		var baseUrl = "${baseUrl}";
   		$(function(){
   			//客户帐号自动完成
	        $("#userLoginName").typeahead({
	            source: function (query, process) {
	            	var userResult = "";
	                $.ajax({
	          	      type : "post",
	          	      url : baseUrl+'/user/searchUser.do?keyword='+query,
	          	      async : false,
	          	      success : function(data){
	          	       	 data = eval("(" + data + ")");
	          	      	userResult = data;
	          	      }
	          	   });
	                return userResult;
	            },
	            highlighter: function(item) {
	            	var arr = item.split("-");
	  	          	var userId = arr[0];
	  	          	var loginName = arr[1];
	  	          	var keyword = arr[2];
	  	          	var newKeyword = ("<font color='red'>"+keyword+"</font>");
	  	          	var newItem = userId.replace(keyword,newKeyword)+"-"+loginName.replace(keyword,newKeyword);
	  	          	return newItem;
	            },
	            updater: function(item) {
            	 var itemArr = item.split("-");
  	              return itemArr[1];
	     	 }
	      });
   			
	        $('#createdTimeStart,#createdTimeEnd').calendar({ format:'yyyy-MM-dd HH:mm:ss' });
   			initGrid();  
   			
   			//btn_search
   			$("#btn_search").click(function(){
   				$("#trackingNoIsNull").val("N");
	   			$("#trackingNoCheckBox").removeAttr("checked");
   				btnSearch("#searchform",grid);
   			});
   		});
   		  
   	 	 var grid = null;
	     function initGrid() {
	    	 grid = $("#maingrid").ligerGrid({
	                columns: [
	                    { display: '客户帐号', name: 'userNameOfCustomer', align: 'center',type:'float',width:'9%'},
	  		          	{ display: '客户订单号', name: 'customerReferenceNo', align: 'center', type: 'float',width:'12%'},
	  		          	{ display: '客户订单类型', name: 'tradeType', align: 'center', type: 'float',width:'10%'},
		                { display: '仓库', name: 'warehouse', align: 'center', type: 'float',width:'8%'},
		                { display: '状态', name: 'status', align: 'center', type: 'float',width:'8%'},
		                { display: '转运类型', name: 'transportType', align: 'center', type: 'float',width:'8%'},
		                { display: '发货渠道', name: 'shipwayCode', align: 'center', type: 'float',width:'8%'},
		                { display: '发货跟踪单号', name: 'trackingNo', align: 'center', type: 'float',width:'12%'},
		                { display: '头程运单数量', name: 'firstWayBillQuantity', align: 'center', type: 'float',width:'9%'},
		               	{ display: '头程运单预览', isSort: false, align: 'center', type: 'float',width:'12%',render: function(row) {
		               		return '<a href="javascript:listFirstWaybills(' + row.id + ')">'+row.firstWaybills+'</a> ';
	  		          	}},
	  		          	{ display: '创建时间', name: 'createdTime', align: 'center', type: 'float',width:'12%'},
	  		         	{ display: '收件人名', isSort: false, align: 'center', type: 'float',width:'8%',render: function(row) {
	  		          		return '<a href="javascript:listOrderReceiver(' + row.id + ')">'+row.receiverName+'</a> ';
	  		          	}},
		  		      	{ display: '发件人名', isSort: false, align: 'center', type: 'float',width:'8%',render: function(row) {
	  		          		return '<a href="javascript:listOrderSender(' + row.id + ')">'+row.senderName+'</a> ';
	  		          	}},
		                { display: '回传审核状态', name: 'callbackSendCheckIsSuccess', align: 'center', type: 'float',width:'8%'},
		                { display: '仓库审核结果', name: 'checkResult', align: 'center', type: 'float',width:'9%'},
		                { display: '回传称重状态', name: 'callbackSendWeightIsSuccess', align: 'center', type: 'float',width:'8%'},
		                { display: '回传出库状态', name: 'callbackSendStatusIsSuccess', align: 'center', type: 'float',width:'8%'},
		                { display: '备注', name: 'remark', align: 'center', type: 'float',width:'13%'},
		                {display: '操作',isSort: false,width: '11%',render: function(row) {
		            		var  h = '<a href="javascript:checkSingleOrder(' + row.id + ')">审核</a> &nbsp;';
		            		h += '<a href="javascript:printSingleOrder(' + row.id + ')">捡货单</a>&nbsp; ';
		            		h += '<a href="javascript:printSingleShipLabel(' + row.id + ')">运单</a> ';
		            		return h;
		            	}
		            }
	                ],  
	                dataAction: 'server',
	                url: baseUrl+'/warehouse/transport/getOrderData.do',
	                pageSize: 100, 
	                pageSizeOptions:[50,100,150,200,500],
	                usePager: 'true',
	                sortName: 'id',
	                sortOrder: 'desc',
	                width: '100%',
	                height: '99%',
	                checkbox: true,
	                rownumbers:true,
	                alternatingRow:true,
	                minColToggle:20,
	                isScroll: true,
	                enabledEdit: false,
	                clickToEdit: false,
	                enabledSort:false
	            });
	        };		
	        
	        
   	</script>
   
   	
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.showMessage.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/core/base.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligeruiPatch.js"></script>
	
	<script type="text/javascript" src="${baseUrl}/static/lhgdialog/prettify/prettify.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/lhgdialog/prettify/lhgdialog.js"></script>
	
	<script type="text/javascript" src="${baseUrl}/static/calendar/lhgcalendar.min.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/calendar/prettify.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/ligerui.all.js"></script>
</body>
</html>