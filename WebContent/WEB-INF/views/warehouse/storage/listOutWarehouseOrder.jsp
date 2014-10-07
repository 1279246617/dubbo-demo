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
               <div class="pull-right searchContent">
               		<span class="pull-left" style="width:175px;">
               			仓库
               			<select style="width:100px;" id="warehouseId" name="warehouseId">
               				<option></option>
							<option value="1">1-香港仓</option>
						</select>
               		</span>
               		
               		<span class="pull-left" style="width:175px;">
               			客户帐号
               			<input type="text" name="userLoginName" data-provide="typeahead"  id="userLoginName" style="width:100px;" title="请输入客户登录名" />
               		</span>
					<span class="pull-left" style="width:195px;">
						跟踪单号	
						<input type="text"  name="trackingNo"  id="trackingNo"   style="width:120px;"/>
					</span>
               		
               		<span class="pull-left" style="width:175px;">
               			创建时间
	               		<input type="text"   style="width:120px;" name="createdTimeStart" id="createdTimeStart" value="${todayStart}" title="起始创建时间">
               		</span>
               		
               		<span class="pull-left" style="width:200px;">
               			至	
               			<input type="text"   style="width:120px;" name="createdTimeEnd"  id="createdTimeEnd"  title="终止创建时间">
               		</span>
               		
               		<span class="pull-left" style="width:55px;">
               			<a class="btn btn-primary btn-small" id="btn_search"><i class="icon-search icon-white"></i>搜索</a>
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
   				btnSearch("#searchform",grid);
   			});
   			
   		});
   		  
   	 	 var grid = null;
	     function initGrid() {
	    	 grid = $("#maingrid").ligerGrid({
	                columns: [
	                    { display: '客户帐号', name: 'userNameOfCustomer', align: 'center',type:'float',width:'9%'},
	  		          	{ display: '客户单号', name: 'customerReferenceNo', align: 'center', type: 'float',width:'12%'},
		                { display: '仓库', name: 'warehouse', align: 'center', type: 'float',width:'8%'},
		                { display: '出库物品', name: 'items', align: 'center', type: 'float',width:'18%'},
		                { display: '状态', name: 'status', align: 'center', type: 'float',width:'8%'},
		                { display: '收件人名', name: 'receiverName', align: 'center', type: 'float',width:'8%'},
		                { display: '收件人街道1', name: 'receiverAddressLine1', align: 'center', type: 'float',width:'8%'},
		                { display: '收件人街道2', name: 'receiverAddressLine2', align: 'center', type: 'float',width:'8%'},
		                { display: '收件人县区', name: 'receiverCounty', align: 'center', type: 'float',width:'8%'},
		                { display: '收件人城市', name: 'receiverCity', align: 'center', type: 'float',width:'8%'},
		                { display: '收件人州省', name: 'receiverStateOrProvince', align: 'center', type: 'float',width:'8%'},
		                { display: '收件人国家', name: 'receiverCountryName', align: 'center', type: 'float',width:'8%'},
		                { display: '收件人邮编', name: 'receiverPostalCode', align: 'center', type: 'float',width:'8%'},
		                { display: '收件人手机', name: 'receiverPhoneNumber', align: 'center', type: 'float',width:'8%'},
		                { display: '收件人电话', name: 'receiverMobileNumber', align: 'center', type: 'float',width:'8%'},
		                { display: '备注', name: 'remark', align: 'center', type: 'float',width:'15%'},
		                { display: '创建时间', name: 'createdTime', align: 'center', type: 'float',width:'12%'},
		                {display: '操作',isSort: false,width: '10%',render: function(row) {
		            		var h = "";
		            		if (!row._editing) {
		            			h += '<a href="javascript:updateOutWarehouseItem(' + row.id + ')">编辑</a> ';
		            			h += '<a href="javascript:deleteOutWarehouseItem(' + row.id + ')">删除</a>';
		            		}
		            		return h;
		            	}
		            }
	                ],  
	                dataAction: 'server',
	                url: baseUrl+'/warehouse/storage/getOutWarehouseOrderData.do?createdTimeStart=${todayStart}',
	                pageSize: 50, 
	                pageSizeOptions:[10,50,100,500,1000],
	                usePager: 'true',
	                sortName: 'id',
	                width: '100%',
	                height: '99%',
	                checkbox: false,
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
	<script type="text/javascript" src="${baseUrl}/static/calendar/lhgcalendar.min.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/calendar/prettify.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/ligerui.all.js"></script>
</body>
</html>