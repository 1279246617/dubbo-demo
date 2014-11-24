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
           <form action="${baseUrl}/warehouse/report/getListReportData.do" id="searchform" name="searchform" method="post">
           		<div class="pull-left">
           			<span class="pull-left" style="width:55px;">
			       		<a class="btn btn-primary btn-small" onclick="downloadBatch()" title="下载报表">
			           		 <i class="icon-folder-open"></i>下载报表
			       	 	</a>
			       	 	<input style=" visibility:hidden;">
		       	 	</span>
		    	</div>   
               <div class="pull-right searchContent">
               		<span class="pull-left" style="width:145px;">
               			仓库
               			<select style="width:90px;" id="warehouseId" name="warehouseId">
               				<option></option>
							<c:forEach items="${warehouseList}" var="w" >
				       	 		<option value="<c:out value='${w.id}'/>">
				       	 			<c:out value="${w.id}-${w.warehouseName}"/>
				       		 	</option>
				       		 </c:forEach>
						</select>
               		</span>
               		<span class="pull-left" style="width:160px;">
               			客户帐号
               			<input type="text" name="userLoginName" data-provide="typeahead"  id="userLoginName" style="width:90px;" title="请输入客户登录名" />
               		</span>
               		<span class="pull-left" style="width:160px;">
               			报表类型
               			 <select style="width:90px;" id="reportType" name="reportType">
               				<option></option>
							<c:forEach items="${reportTypeList}" var="r" >
				       	 		<option value="<c:out value='${r.code}'/>">
				       	 			<c:out value="${r.cn}"/>
				       		 	</option>
				       		 </c:forEach>
						</select>
               		</span>
               		
               		<span class="pull-left" style="width:170px;">
               			报表名称
               			 <input type="text"   style="width:100px;" name="reportName">
               		</span>
               		
               		<span class="pull-left" style="width:175px;">
               			创建时间
	               		<input type="text"   style="width:120px;" name="createdTimeStart" id="timeStart" value="${sevenDaysAgoStart}" >
               		</span>
               		
               		<span class="pull-left" style="width:200px;">
               			至	
               			<input type="text"   style="width:120px;" name="createdTimeEnd"  id="timeEnd" >
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
   			
	        $('#timeStart,#timeEnd').calendar({ format:'yyyy-MM-dd HH:mm:ss' });
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
							{ display: '客户帐号', name: 'userLoginNameOfCustomer',type:'float',width:'10%'},
							{ display: '仓库', name: 'warehouse', type: 'float',width:'12%'},
							{ display: '报表名称', name: 'reportName', type: 'int', width:'23%'},
	  	                  	{ display: '报表类型', name: 'reportType',type:'float',width:'13%'},
	  	                  	{ display: '创建时间', name: 'createdTime',type:'float',width:'13%'},
	  	                	{ display: '备注', name: 'remark',type:'float',width:'14%'},
	  	                  	{display: '操作',isSort: false,width: '12%',render: function(row) {
	  	                  		var h = "";
			            		if (!row._editing) {
			            			h+="<a href=javascript:download(" + row.id +")>下载文件</a>&nbsp;&nbsp;";
			            			h+="<a href=javascript:addRemark(" + row.id + ",'"+row.remark+"')>备注</a>";
			            		}
			            		return h;
			            	}}
		             ],   
	                dataAction: 'server',
	                url: baseUrl+'/warehouse/report/getListReportData.do',
	                pageSize: 50, 
	                pageSizeOptions:[10,50,100,500,1000],
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
   	</script>
   	<script type="text/javascript">
   		function addRemark(id,remark){
//    		   $.dialog({
//  	          lock: true,
//  	          title: '备注',
//  	          width: '450px',
//  	          height: '290px',
//  	          content: 'url:' + baseUrl + '/warehouse/storage/editOutWarehouseRecordRemark.do?id='+id+"&remark="+remark,
//  	          button: [{
//  	            name: '确定',
//  	            callback: function() {
//  	              var objRemark = this.content.document.getElementById("remark");
//  	              var remark = $(objRemark).val();
//  	              $.post(baseUrl + '/warehouse/storage/saveOutWarehouseRecordRemark.do', {
//  	            	  remark:remark,
//  	            	  id:id
//  	              },
//  	              function(msg) {
//  	                	grid.loadData();
//  	              });
//  	            }
//  	          }],
//  	          cancel: true
//  	        });
   		}
   		function downloadBatch(){
   			
   		}
   		
   		//下载
   		function download(id){
   			var url = baseUrl + '/warehouse/report/downloadReport.do?reportId='+id;
   			window.open(url);
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