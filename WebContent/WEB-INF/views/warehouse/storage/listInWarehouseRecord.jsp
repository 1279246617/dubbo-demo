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
           <form action="${baseUrl}/warehouse/storage/getInWarehouseRecordData.do" id="searchform" name="searchform" method="post">
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
               		<span class="pull-left" style="width:185px;">
               			入库批次号
               			 <input type="text"   style="width:100px;" name="batchNo" title="可输入入库批次号">
               		</span>
					<span class="pull-left" style="width:195px;">
						跟踪单号	
						<input type="text"  name="trackingNo"  id="trackingNo"   style="width:120px;"/>
					</span>
               		<span class="pull-left" style="width:175px;">
               			创建时间
	               		<input type="text"   style="width:120px;" name="createdTimeStart" id="createdTimeStart"  title="起始创建时间">
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
							{ display: '客户帐号', name: 'userLoginNameOfCustomer',type:'float',width:'9%'},
							{ display: '仓库', name: 'warehouse', type: 'float',width:'9%'},
							{ display: '入库批次号', name: 'batchNo', type: 'int', width:'12%'},
							{ display: '客户订单号', name: 'customerReferenceNo',type:'float',width:'13%'},
	  	                    { display: '跟踪单号', name: 'trackingNo',type:'float',width:'13%'},
	  	                  	{ display: '入库订单Id', name: 'inWarehouseOrderId',type:'float',width:'13%'},
		  		            { display: '预报产品数量', name: 'quantity', type: 'float',width:'9%'},
		  		          	{ display: '本次收货数量', name: 'receivedQuantity', type: 'float',width:'9%'},
		  		        	{ display: 'SKU预览', isSort: false, align: 'center', type: 'float',width:'16%',render: function(row) {
			            		var skus = "";
			            		if (!row._editing) {
			            			skus += '<a href="javascript:listInWarehouseRecordItem(' + row.id + ')">'+row.skus+'</a> ';
			            		}
			            		return skus;
		  		          	}},
		  		          	{ display: '上架状态', name: 'status', width:'12%'},
			                { display: '入库备注', name: 'remark', width:'12%'},
			                { display: '操作员', name: 'userLoginNameOfOperator',type:'float',width:'9%'},
			                
			                { display: '回传入库状态', name: 'callbackIsSuccess',type:'float',width:'8%'},
			                { display: '创建时间', name: 'createdTime', align: 'center', type: 'float',width:'12%'},
			                {display: '操作',isSort: false,width: '8%',render: function(row) {
			            		if (!row._editing) {
			            			return "<a href=javascript:addRemark(" + row.id + ",'"+row.remark+"')>备注</a>";
			            		}
			            	}
			            }
		             ],   
	                dataAction: 'server',
	                url: baseUrl+'/warehouse/storage/getInWarehouseRecordData.do',
	                pageSize: 50, 
	                pageSizeOptions:[10,50,100,500,1000],
	                usePager: 'true',
	                sortName: 'id',
	                sortOrder: 'desc',
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
	        
	      //SKU
	        function listInWarehouseRecordItem(recordId){
	        	var contentArr = [];
	        	contentArr.push('<div style="height:340px;overflow:auto; ">');
	        	contentArr.push('<table class="table" style="width:649px">');
	        	contentArr.push('<tr><th>产品SKU</th><th>产品编号</th><th>产品名称</th><th>本次收货数量</th><th>预报数量</th></tr>');
	        	$.ajax({ 
	                type : "post", 
	                url :baseUrl + '/warehouse/storage/getInWarehouseRecordItemByRecordId.do', 
	                data : "recordId="+recordId, 
	                async : false, 
	                success : function(msg){ 
	                	msg = eval("(" + msg + ")");
	        			$.each(msg,function(i,e){
	        			  	contentArr.push('<tr>');
	        			  	contentArr.push('<td>'+e.sku+'</td>');
	        			  	contentArr.push('<td>'+e.skuNo+'</td>');
	    	        		contentArr.push('<td>'+e.skuName+'</td>');
	    	        		contentArr.push('<td>'+e.quantity+'</td>');
	    	        		contentArr.push('<td>'+e.orderQuantity+'</td>');
	        			  	contentArr.push('</tr>');
	        			});
	                } 
	           	});
	            contentArr.push('</table>');
	            contentArr.push('</div>');
	            var contentHtml = contentArr.join('');
	        	$.dialog({
	          		lock: true,
	          		max: false,
	          		min: false,
	          		title: '入库记录SKU详情',
	          		width: 650,
	          		height: 350,
	          		content: contentHtml,
	          		button: [{
	          			name: '关闭',
	          			callback: function() {
							
	          			}
	          		}]
	          	})
	        }
	      
	        function addRemark(id,remark){
	        	   $.dialog({
	        	          lock: true,
	        	          title: '备注',
	        	          width: '450px',
	        	          height: '290px',
	        	          content: 'url:' + baseUrl + '/warehouse/storage/editInWarehouseRecordRemark.do?id='+id+"&remark="+remark,
	        	          button: [{
	        	            name: '确定',
	        	            callback: function() {
	        	              var objRemark = this.content.document.getElementById("remark");
	        	              var remark = $(objRemark).val();
	        	              $.post(baseUrl + '/warehouse/storage/saveInWarehouseRecordRemark.do', {
	        	            	  remark:remark,
	        	            	  id:id
	        	              },
	        	              function(msg) {
	        	                	grid.loadData();
	        	              });
	        	            }
	        	          }],
	        	          cancel: true
	        	        });
	           }
   	</script>
   	
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