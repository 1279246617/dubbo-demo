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
	<div name="leftdiv" class="pull-left" style="width:50%">
		<div class="toolbar1" style="width:98%;">
			  	<div class="pull-left">
		           			<span class="pull-left" style="width:55px;">
					       		<a class="btn btn-primary btn-small" id="addButton" onclick="addShelf()" title="添加货架">
					           		 <i class="icon-folder-open"></i>添加货架
					       	 	</a>
					       	 	<a class="btn btn-primary btn-small" onclick="deleteShelf()" title="删除货架">
					           		 <i class="icon-folder-open"></i>删除货架
					       	 	</a>
					       	 	<input style=" visibility:hidden;">
				       	 	</span>
				  </div>    
		           <form action="${baseUrl}/warehouse/shelves/getShelfData.do" id="searchformShelf" name="searchformShelf" method="post">
		               <div class="pull-right searchContent">
		               		<span class="pull-left" style="width:145px;">
		               			仓库
		               			<select style="width:90px;" id="warehouseId" name="warehouseId">
		               				<option></option>
									<option value="1">1-香港仓</option>
								</select>
		               		</span>
							<span class="pull-left" style="width:195px;">
								货架号
								<input type="text"  name="shelfCode"  id="shelfCode"   style="width:120px;"/>
							</span>
		               		<span class="pull-left" style="width:55px;">
		               			<a class="btn btn-primary btn-small" id="btn_shelf_search"><i class="icon-search icon-white"></i>搜索</a>
		               			<input style=" visibility:hidden;">
		               		</span>
		               </div>
		           </form>
		</div>
		<div id="maingridShelf" class="pull-left" style="width:100%;"></div>
	</div>
		
	<div name="rightdiv" class="pull-right" style="width:49%">
			<div class="toolbar1" style="width:100%;">
			  	<div class="pull-left">
		           			<span class="pull-left" style="width:55px;">
					       		<a class="btn btn-primary btn-small" onclick="printBatch()" title="打印货位条码">
					           		 <i class="icon-folder-open"></i>打印货位条码
					       	 	</a>
					       	 	<input style=" visibility:hidden;">
				       	 	</span>
				  </div>    
		           <form action="${baseUrl}/warehouse/shelves/getSeatData.do" id="searchform" name="searchform" method="post">
		               <div class="pull-right searchContent">
		               		<span class="pull-left" style="width:140px;">
		               			仓库
		               			<select style="width:90px;" id="warehouseId" name="warehouseIdForSeat">
		               				<option></option>
									<option value="1">1-香港仓</option>
								</select>
		               		</span>
							<span class="pull-left" style="width:135px;">
								货架号
								<input type="text"  name="shelfCode"  id="shelfCodeForSeat"   style="width:70px;"/>
								<input type="text"  name="shelfId"  id="shelfId"   style="display: none;"/>
							</span>
							<span class="pull-left" style="width:145px;">
								货位号
								<input type="text"  name="seatCode"  id="seatCode"   style="width:80px;"/>
							</span>
							
		               		<span class="pull-left" style="width:55px;">
		               			<a class="btn btn-primary btn-small" id="btn_search"><i class="icon-search icon-white"></i>搜索</a>
		               			<input style=" visibility:hidden;">
		               		</span>
		               </div>
		           </form>
		</div>
		<div id="maingrid" class="pull-right" style="width:100%;"></div>
	</div>
	
	
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/bootstrap/bootstrap-typeahead.js"></script>
    
    <script type="text/javascript">
 		var baseUrl = "${baseUrl}";
	    //货架表格
	    var gridShelf = null;
	    function initShelfGrid() {
	    	gridShelf = $("#maingridShelf").ligerGrid({
	                columns: [
							{ display: '仓库', name: 'warehouse_id', type: 'float',width:'10%'},
	  	                  	{ display: '货架号', name: 'shelf_code', type: 'float',width:'10%'},
	  	                  	{ display: '货架类型', name: 'shelf_type', type: 'int', width:'10%'},
	  	                  	{ display: '业务类型', name: 'business_type', type: 'int', width:'10%'},
	  	                  	{ display: '行数/起始', name: 'rows', type: 'int', width:'11%'},
	  	                	{ display: '列数/终止', name: 'cols', type: 'int', width:'11%'},
			                { display: '备注', name: 'remark',type:'float',width:'10%'},
	  	                  	{display: '操作',isSort: false,width: '15%',render: function(row) {
			            		var h = "";
			            		if (!row._editing) {
			            			h += '<a href="javascript:updateShelf(' + row.id + ')">更新</a> ';
			            			h += '<a href="javascript:showSeat('+ row.id+ ')">显示货位</a> ';
			            		}
			            		return h;
			            	}}
		             ],   
	                dataAction: 'server',
	                url: baseUrl+'/warehouse/shelves/getShelfData.do',
	                pageSize: 100, 
	                pageSizeOptions:[50,100,150,200,500],
	                usePager: 'true',
	                sortName: 'id',
	                sortOrder:'desc',
	                width: '100%',
	                height: '75%',
	                checkbox: true,
	                rownumbers:true,
	                alternatingRow:false,
	                minColToggle:20,
	                isScroll: true,
	                enabledEdit: false,
	                clickToEdit: false,
	                enabledSort:true
	         });
	    };	
 		//货位表格
   	 	var grid = null;
	    function initGrid() {
	    	 grid = $("#maingrid").ligerGrid({
	                columns: [
							{ display: '仓库', name: 'warehouseId', type: 'float',width:'13%'},
	  	                  	{ display: '货架号', name: 'shelfCode', type: 'float',width:'13%'},
	  	                  	{ display: '货位号', name: 'seatCode', type: 'int', width:'15%'},
	  	                  	{ display: '状态', name: 'status', type: 'int', width:'9%'},
			            	{ display: '货物', isSort: false, align: 'center', type: 'float',width:'22%',render: function(row) {
			            		var skus = "";
			            		if (!row._editing) {
			            			skus += '<a href="javascript:listItemShelfInventory(' + row.id + ')">'+row.skus+'</a> ';
			            		}
			            		return skus;
		  		          	}},
	  	                    {display: '操作',isSort: false,width: '17%',render: function(row) {
			            		var h = "";
			            		if (!row._editing) {
			            			h += '<a href="javascript:print(' + row.id + ')">打印货位条码</a> ';
			            		}
			            		return h;
			            	}}
		             ],   
	                dataAction: 'server',
	                url: baseUrl+'/warehouse/shelves/getSeatData.do',
	                pageSize: 100, 
	                pageSizeOptions:[50,100,150,200,500],
	                usePager: 'true',
	                sortName: 'id',
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
	 
    	$(function(){
    		initShelfGrid();
    		initGrid();	    	
    	});
    	//btn_search
  		$("#btn_search").click(function(){
  				$("#shelfId").val("");
  				btnSearch("#searchform",grid);
  		});
    	
  		//btn_search
  		$("#btn_shelf_search").click(function(){
  				btnSearch("#searchformShelf",gridShelf);
  		});
   	</script>
   	<script type="text/javascript" src="${baseUrl}/static/js/warehouse/listSeat.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.showMessage.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/core/base.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligeruiPatch.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/calendar/lhgcalendar.min.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/calendar/prettify.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/lhgdialog/prettify/prettify.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/lhgdialog/prettify/lhgdialog.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/ligerui.all.js"></script>
</body>
</html>