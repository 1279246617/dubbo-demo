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
           <form action="#" id="searchform" name="searchform" method="post">
               <div class="pull-right searchContent">
               		<span class="pull-left" style="width:175px;">
               			收货仓库
               			<select style="width:100px;" id="warehouseId">
							<option value="1">1-香港仓</option>
							<option value="2">2-美国仓</option>
						</select>
               		</span>
               		
               		<span class="pull-left" style="width:175px;">
               			客户帐号
               			<input type="text"   style="width:100px;" name="sku" title="可输入客户登录名">
               		</span>
               		
               		<span class="pull-left" style="width:185px;">
               			入库批次号
               			 <input type="text"   style="width:100px;" name="sku" title="可输入入库批次号">
               		</span>
               		
               		<span class="pull-left" style="width:155px;">
               			创建时间
	               		<input type="text"   style="width:100px;" name="createdTimeStart" id="createdTimeStart" title="起始创建时间">
               		</span>
               		
               		<span class="pull-left" style="width:200px;">
               			至	
               			<input type="text"   style="width:100px;" name="createdTimeEnd" id="createdTimeEnd" title="终止创建时间">
               		</span>
               		
               		<span class="pull-left" style="width:65px;">
               			<a class="btn btn-primary btn-small" id="btn_search"><i class="icon-search icon-white"></i>搜索</a>
               			<input style=" visibility:hidden;">
               		</span>
               </div>
           </form>
	</div>
	<div id="maingrid" class="pull-left" style="width:100%;"></div>
	
    <script type="text/javascript">
 		var baseUrl = "${baseUrl}";
   		$(function(){
   			initGrid();  
   		});
   		  
   	 	 var grid = null;
	     function initGrid() {
	    	 grid = $("#maingrid").ligerGrid({
	                columns: [
	                    { display: '产品SKU', name: 'sku', align: 'right',type:'float',width:'13%'},
	  		            { display: '预报产品数量', name: 'quantity', align: 'right', type: 'float',width:'9%'},
	  		          	{ display: '实际收货数量', name: 'quantity', align: 'right', type: 'float',width:'9%'},
	  		          	{ display: '产品描述', name: 'productDescription', align: 'right', type: 'float',width:'10%'},
		                { display: '仓库', name: 'warehouse', align: 'right', type: 'float',width:'9%'},
		                { display: '货架', name: 'shelves', align: 'right', type: 'float',width:'9%'},
		                { display: '货位', name: 'seat', align: 'right', type: 'float',width:'10%'},
		                { display: '批次号', name: 'batchNo', type: 'int', width:'12%'},
		                {display: '操作',isSort: false,width: '9%',render: function(row) {
		            		var h = "";
		            		if (!row._editing) {
		            			h += '<a href="javascript:updateInWarehouseItem(' + row.id + ')">编辑</a> ';
		            			h += '<a href="javascript:deleteInWarehouseItem(' + row.id + ')">删除</a>';
		            		}
		            		return h;
		            	}
		            }
	                ],  
	                isScroll: true,
	                dataAction: 'server',
	                url: baseUrl+'/warehouse/storage/getInWarehouseOrder.do',
	                pageSize: 20, 
	                usePager: 'true',
	                sortName: 'sku',
	                width: '100%',
	                height: '99%',
	                checkbox: true,
	                rownumbers:true,
	                enabledEdit: true,
	                clickToEdit: true
	            });
	        };		
   	</script>
   	
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.js"></script>
	
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.showMessage.js"></script>
	
	<script  type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/core/base.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/ligerui.all.js"></script>
</body>
</html>