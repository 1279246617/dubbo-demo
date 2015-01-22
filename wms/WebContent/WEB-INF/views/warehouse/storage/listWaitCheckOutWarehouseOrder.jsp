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
           <form action="${baseUrl}/warehouse/storage/getOutWarehouseOrderData.do" id="searchform" name="searchform" method="post">
          	<input type="text"  name="nos"  id="nos"   style="display:none;"/>
			<input type="text"  name="noType"  id="noType"   style="display:none;"/>
                <div class="pull-right searchContent">
               		<span class="pull-left" style="width:140px;">
               			仓库
               			<select style="width:93px;" id="warehouseId" name="warehouseId">
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
               		<span class="pull-left" style="width:140px;">
               			客户
               			<input type="text" name="userLoginName" data-provide="typeahead"  id="userLoginName" style="width:93px;" title="请输入客户登录名" />
               		</span>
               		
					<span class="pull-left" style="width:175px;">
						客户订单号
						<input type="text"  name="customerReferenceNo"  id="customerReferenceNo"   style="width:105px;"/>
					</span>
               		
               		<span class="pull-left" style="width:175px;">
               			创建时间
	               		<input type="text"   style="width:120px;" name="createdTimeStart" id="createdTimeStart"  title="起始创建时间">
               		</span>
               		
               		<span class="pull-left" style="width:180px;">
               			至	
               			<input type="text"   style="width:120px;" name="createdTimeEnd"  id="createdTimeEnd"  title="终止创建时间">
               		</span>
               		
               		<span class="pull-left" style="width:55px;">
               			<a class="btn btn-primary btn-small" id="btn_search"><i class="icon-search icon-white"></i>搜索</a>
               			<input style=" visibility:hidden;">
               		</span>
               		<span class="pull-left" style="width:100px;">
               			<a class="btn btn-primary btn-small" id="advancedSearch"><i class="icon-search icon-white"></i>批量单号搜索</a>
               			<input style=" visibility:hidden;">
               		</span>
               </div>
               <div class="pull-left">
           			<span class="pull-left" style="width:55px;">
			       		<a class="btn btn-primary btn-small" onclick="checkOrder()" title="审核出库订单">
			           		 <i class="icon-eye-open"></i>审核
			       	 	</a>
			       	 	<input style=" visibility:hidden;">
		       	 	</span>
		       	 	<span class="pull-left" style="width:105px;">
			       		<a class="btn btn-primary btn-small" onclick="exportOrder('WWC')" title="导出单品订单">
			           		 <i class="icon-file"></i>导出单品订单
			       	 	</a>
			       	 	<input style=" visibility:hidden;">
		       	 </span>   
		    	</div> 
           </form>
	</div>
	<div id="maingrid" class="pull-left" style="width:100%;"></div>
	
	
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/bootstrap/bootstrap-typeahead.js"></script>
    
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
   				$("#noType").val("");//清空高级搜索隐藏框的内容
   				$("#nos").val("");
   				btnSearch("#searchform",grid);
   			});
   		//高级搜索
   			$("#advancedSearch").click(function(){
   				advancedSearch();
   			});
   		});
   		  
   	 	 var grid = null;
	     function initGrid() {
	    	 grid = $("#maingrid").ligerGrid({
	                columns: [
	                    { display: '客户帐号', name: 'userNameOfCustomer', align: 'center',type:'float',width:'9%'},
	  		          	{ display: '客户订单号', name: 'customerReferenceNo', align: 'center', type: 'float',width:'14%'},
		                { display: '仓库', name: 'warehouse', align: 'center', type: 'float',width:'8%'},
		                { display: '发货渠道', name: 'shipwayCode', align: 'center', type: 'float',width:'8%'},
	  		          	{ display: '跟踪单号', name: 'trackingNo', align: 'center', type: 'float',width:'12%'},
		                { display: '条码数/商品数', name: 'bpQuantity', align: 'center', type: 'float',width:'9%'},
		                { display: '商品预览', isSort: false, align: 'center', type: 'float',width:'15%',render: function(row) {
		            		var skus = "";
		            		if (!row._editing) {
		            			skus += '<a href="javascript:listOutWarehouseOrderItem(' + row.id + ')">'+row.items+'</a> ';
		            		}
		            		return skus;
	  		          	}},
	  		          	{ display: '出库类型', name: 'orderType', align: 'center', type: 'float',width:'7%'},
	  		          	{ display: '状态', name: 'status', align: 'center', type: 'float',width:'8%'},
		                { display: '收件人名', name: 'receiverName', align: 'center', type: 'float',width:'8%'},
		                { display: '收件人街道1', name: 'receiverAddressLine1', align: 'center', type: 'float',width:'12%'},
		                { display: '收件人街道2', name: 'receiverAddressLine2', align: 'center', type: 'float',width:'8%'},
		                { display: '收件人县区', name: 'receiverCounty', align: 'center', type: 'float',width:'8%'},
		                { display: '收件人城市', name: 'receiverCity', align: 'center', type: 'float',width:'8%'},
		                { display: '收件人州省', name: 'receiverStateOrProvince', align: 'center', type: 'float',width:'8%'},
		                { display: '收件人国家', name: 'receiverCountryName', align: 'center', type: 'float',width:'8%'},
		                { display: '收件人邮编', name: 'receiverPostalCode', align: 'center', type: 'float',width:'8%'},
		                { display: '收件人手机', name: 'receiverPhoneNumber', align: 'center', type: 'float',width:'8%'},
		                { display: '收件人电话', name: 'receiverMobileNumber', align: 'center', type: 'float',width:'8%'},
		                { display: '发件人名', name: 'senderName', align: 'center', type: 'float',width:'8%'},
		                { display: '备注', name: 'remark', align: 'center', type: 'float',width:'15%'},
		                { display: '创建时间', name: 'createdTime', align: 'center', type: 'float',width:'12%'},
		                {display: '操作',isSort: false,width: '9%',render: function(row) {
		            		var  h = '<a href="javascript:checkSingleOrder(' + row.id + ')">审核</a> ';
// 		            				h += '<a href="javascript:deleteOutWarehouseOrder(' + row.id + ')">删除</a>';
		            		return h;
		            	}
		            }
	                ],  
	                dataAction: 'server',
	                url: baseUrl+'/warehouse/storage/getOutWarehouseOrderData.do?status=WWC',
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
	
	<script type="text/javascript" src="${baseUrl}/static/js/warehouse/listOutWarehouseOrder.js"></script>
</body>
</html>