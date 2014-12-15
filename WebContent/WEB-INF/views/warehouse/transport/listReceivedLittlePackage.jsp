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
           <form action="${baseUrl}/warehouse/transport/getLittlePackageData.do" id="searchform" name="searchform" method="post">
               <div class="pull-right searchContent">
               		<span class="pull-left" style="width:125px;">
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
               		<span class="pull-left" style="width:130px;">
               			客户
               			<input type="text" name="userLoginName" data-provide="typeahead"  id="userLoginName" style="width:85px;" title="请输入客户登录名" />
               		</span>
               		
					<span class="pull-left" style="width:170px;">
						跟踪单号
						<input type="text"  name="trackingNo"  id="trackingNo"   style="width:90px;"/>
						<input type="text"  name="nos"  id="nos"   style="display:none;"/>
						<input type="text"  name="noType"  id="noType"   style="display:none;"/>
					</span>
					               		
               		<span class="pull-left" style="width:175px;">
               			收货时间
	               		<input type="text"   style="width:120px;" name="receivedTimeStart" id="receivedTimeStart" title="起始收货时间">
               		</span>
               		<span class="pull-left" style="width:180px;">
               			至	
               			<input type="text"   style="width:120px;" name="receivedTimeEnd"  id="receivedTimeEnd"  title="终止收货时间">
               		</span>
               		<span class="pull-left" style="width:55px;">
               			<a class="btn btn-primary btn-small" id="btn_search"><i class="icon-search icon-white"></i>搜索</a>
               			<input style=" visibility:hidden;">
               		</span>
               		<span class="pull-left" style="width:100px;display: none;">
               			<a class="btn btn-primary btn-small" id="advancedSearch"><i class="icon-search icon-white"></i>批量单号搜索</a>
               			<input style=" visibility:hidden;">
               		</span>
               </div>
           </form>
	</div>
	<div id="maingrid" class="pull-left" style="width:100%;"></div>
	
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/bootstrap/bootstrap-typeahead.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/js/warehouse/transport/listLittlePackage.js"></script>
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
   			
	        $('#receivedTimeStart,#receivedTimeEnd').calendar({ format:'yyyy-MM-dd HH:mm:ss' });
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
	                    { display: '仓库', name: 'warehouse', align: 'center', type: 'float',width:'8%'},
	                    { display: '状态', name: 'status', align: 'center', type: 'float',width:'8%'},
	                    { display: '转运类型', name: 'transportType', align: 'center', type: 'float',width:'9%'},
	  		          	{ display: '到货跟踪单号', name: 'trackingNo', align: 'center', type: 'float',width:'12%'},
	  		          	{ display: '承运商', name: 'carrierCode', align: 'center', type: 'float',width:'10%'},
	  		          	{ display: '商品预览', isSort: false, align: 'center', type: 'float',width:'12%',render: function(row) {
		            		var skus = "";
		            		if (!row._editing) {
		            			skus += '<a href="javascript:listLittlePackagesItem(' + row.id + ')">'+row.items+'</a> ';
		            		}
		            		return skus;
	  		          	}},
	  		          	{ display: '收货时间', name: 'receivedTime', align: 'center', type: 'float',width:'12%'},
		                { display: '回传收货状态', name: 'callbackIsSuccess', align: 'center', type: 'float',width:'8%'},
		                { display: '操作员', name: 'userNameOfOperator',width:'8%'},
		                { display: '备注', name: 'remark', align: 'center', type: 'float',width:'12%'},
	                ],  
	                dataAction: 'server',
	                url: baseUrl+'/warehouse/transport/getLittlePackageData.do?isReceived=Y',
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