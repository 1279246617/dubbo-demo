<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link href="${baseUrl}/static/bootstrap/bootstrap.min.css" rel="stylesheet"type="text/css" />
<link href="${baseUrl}/static/bootstrap/common.css" rel="stylesheet" type="text/css"/>
<link href="${baseUrl}/static/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${baseUrl}/static/lhgdialog/prettify/common.css" type="text/css" rel="stylesheet" />
<link href="${baseUrl}/static/lhgdialog/prettify/prettify.css" type="text/css" rel="stylesheet" />
<link href="${baseUrl}/static/calendar/prettify.css" rel="stylesheet" type="text/css" />
<link href="${baseUrl}/static/calendar/lhgcalendar.css" rel="stylesheet" type="text/css" />
<title>COE</title>
</head>
<body>
	<div class="pull-left" style="margin-left: 10px;width:100%;height:140px; margin-top: 10px;" >
		<form id="form1" name="form1" method="post">
			<div>
				<span>运单号</span>
          			<input type="text"  name="trackingNo"  id="trackingNo" style="width:220px;"/>
           		<!-- 根据运单号 查找预报入库订单信息,  如果查不到信息或查到多条订单, 要求操作员输入客户帐号(登录名)-->		
           		<span style="margin-left: 10px;">客户帐号</span>
           		<input type="text" name="trackingNo"  id="trackingNo" style="width:160px;" readonly="readonly"/>
           		<img class="tips" id="customerNoTips" msg="根据运单号找不到唯一的入库订单时,将要求输入客户帐号" src="${baseUrl}/static/img/help.gif">
			</div>					
      </form>
      	
	 	<form id="form2" name="form2" method="post">
			<div>
				<span>SKU</span>
          		<input type="text"  name="trackingNo"  id="trackingNo" style="width:220px;"/>
           		<span style="margin-left: 10px;">产品数量</span>
           		<input type="text" name="trackingNo"  id="trackingNo" style="width:160px;"/>
			</div>					
      </form>
          
	</div>
		
	 <div id="maingrid" class="pull-left" style="width:100%;"></div> 
	 
	 <script type="text/javascript" src="${baseUrl}/static/jquery/jquery.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.showMessage.js"></script>
	<script  type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/core/base.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/ligerui.all.js"></script>
    <script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligerTab.js"></script>
    <script  type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligerTree.js" ></script>
	<script type="text/javascript">
		var baseUrl = "${baseUrl}";
	    $(function(){
	    	$(".tips").each(function(i,e){
	                var $img = $(e);
	                var msg = $img.attr("msg");
	                var id =  $img.attr("id");
	                $.showTips(id,msg);
	        });
	    	
	       	initGrid();
	    });
	   	 var grid = null;
	   	var data = { Rows: [{ "ProductID": 1, "ProductName": "Chai", "SupplierID": 1, "CategoryID": 1, "QuantityPerUnit": "10 boxes x 20 bags", "UnitPrice": 18, "UnitsInStock": 39, "UnitsOnOrder": 0, "ReorderLevel": 10, "Discontinued": false, "EAN13": "070684900001" }, { "ProductID": 2, "ProductName": "Chang", "SupplierID": 1, "CategoryID": 1, "QuantityPerUnit": "24 - 12 oz bottles", "UnitPrice": 19, "UnitsInStock": 17, "UnitsOnOrder": 40, "ReorderLevel": 25, "Discontinued": false, "EAN13": "070684900002" }, { "ProductID": 3, "ProductName": "Aniseed Syrup", "SupplierID": 1, "CategoryID": 2, "QuantityPerUnit": "12 - 550 ml bottles", "UnitPrice": 10, "UnitsInStock": 13, "UnitsOnOrder": 70, "ReorderLevel": 25, "Discontinued": false, "EAN13": "070684900003" }, { "ProductID": 4, "ProductName": "Chef Anton's Cajun Seasoning", "SupplierID": 2, "CategoryID": 2, "QuantityPerUnit": "48 - 6 oz jars", "UnitPrice": 22, "UnitsInStock": 53, "UnitsOnOrder": 0, "ReorderLevel": 0, "Discontinued": false, "EAN13": "070684900004" }, { "ProductID": 5, "ProductName": "Chef Anton's Gumbo Mix", "SupplierID": 2, "CategoryID": 2, "QuantityPerUnit": "36 boxes", "UnitPrice": 21.35, "UnitsInStock": 0, "UnitsOnOrder": 0, "ReorderLevel": 0, "Discontinued": true, "EAN13": "070684900005" }, { "ProductID": 6, "ProductName": "Grandma's Boysenberry Spread", "SupplierID": 3, "CategoryID": 2, "QuantityPerUnit": "12 - 8 oz jars", "UnitPrice": 25, "UnitsInStock": 120, "UnitsOnOrder": 0, "ReorderLevel": 25, "Discontinued": false, "EAN13": "070684900006" }, { "ProductID": 7, "ProductName": "Uncle Bob's Organic Dried Pears", "SupplierID": 3, "CategoryID": 7, "QuantityPerUnit": "12 - 1 lb pkgs.", "UnitPrice": 30, "UnitsInStock": 15, "UnitsOnOrder": 0, "ReorderLevel": 10, "Discontinued": false, "EAN13": "070684900007" }, { "ProductID": 8, "ProductName": "Northwoods Cranberry Sauce", "SupplierID": 3, "CategoryID": 2, "QuantityPerUnit": "12 - 12 oz jars", "UnitPrice": 40, "UnitsInStock": 6, "UnitsOnOrder": 0, "ReorderLevel": 0, "Discontinued": false, "EAN13": "070684900008" }, { "ProductID": 9, "ProductName": "Mishi Kobe Niku", "SupplierID": 4, "CategoryID": 6, "QuantityPerUnit": "18 - 500 g pkgs.", "UnitPrice": 97, "UnitsInStock": 29, "UnitsOnOrder": 0, "ReorderLevel": 0, "Discontinued": true, "EAN13": "070684900009" }], Total: 77 };
	     function initGrid() {
	    	   grid = $("#maingrid").ligerGrid({
	                columns: [
		                { display: '主键', name: 'ProductID', type: 'int', width:'25%'},
		                
		                { display: '产品名', name: 'ProductName', align: 'left',width:'25%'},
		                
		                { display: '单价', name: 'UnitPrice', align: 'right',type:'float',width:'24%'},
		                
		                { display: '仓库数量', name: 'UnitsInStock', align: 'right', type: 'float',width:'20%'}
	                ],  
	                isScroll: true,
	                data: data,
	                pageSize: 20, 
	                usePager: 'true',
	                sortName: 'ProductID',
	                width: '100%',
	                height: '99%',
	                checkbox: true,
	                rownumbers:true,
	                enabledEdit: true,
	                clickToEdit: true
	            });
	        };	
	  
	</script>
</body>
</html>