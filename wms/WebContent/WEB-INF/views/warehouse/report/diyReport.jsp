<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${baseUrl}/static/css/common.css" rel="stylesheet" type="text/css" />
<link href="${baseUrl}/static/bootstrap/bootstrap.min.css" rel="stylesheet"type="text/css" />
<link href="${baseUrl}/static/bootstrap/common.css" rel="stylesheet" type="text/css"/>
<title>COE</title>
</head>
<body >
	<div id="step1" style="margin-top: 3mm;width:100%;" class="pull-left">
		<div class="pull-left" style="width:100px;margin-left: 2mm;" >
				<span class="badge badge-success">1</span>报表名称:	
		</div>
		<div class="pull-left" style="width:200px;">
			<input type="text" name="reportName"  id="reportName" style="width:115px;" title="报表名称" />
		</div>
	</div>	
	
	<div id="step2" style="margin-top: 3mm;width:100%;" class="pull-left">
		<div class="pull-left" style="width:100px;margin-left: 2mm;" >
				<span class="badge badge-success">2</span>报表类型:	
		</div>
		<div class="pull-left" style="width:200px;">
			 <select style="width:120px;" id="reportType" name="reportType">
             		<option></option>
					<c:forEach items="${reportTypeList}" var="r" >
		       	 		<option value="<c:out value='${r.code}'/>">
		       	 			<c:out value="${r.cn}"/>
		       		 	</option>
		       		 </c:forEach>
				</select>
		</div>
	</div>	
		
	<div id="step3" style="margin-top: 3mm;width:100%;" class="pull-left">
		<div class="pull-left" style="width:100px;margin-left: 2mm;" >
				<span class="badge badge-success">3</span>所属仓库:	
		</div>
		<div class="pull-left" style="width:200px;">
             <select style="width:120px;" id="warehouseId" name="warehouseId">
					<c:forEach items="${warehouseList}" var="w" >
	       	 			<option value="<c:out value='${w.id}'/>">
	       	 				<c:out value="${w.id}-${w.warehouseName}"/>
	       		 		</option>
	       			</c:forEach>
			 </select>
		</div>
	</div>	
	
	
	<div id="step4" style="margin-top: 3mm;width:100%;" class="pull-left">
		<div class="pull-left" style="width:100px;margin-left: 2mm;" >
				<span class="badge badge-success">4</span>客户帐号:	
		</div>
		<div class="pull-left" style="width:200px;">
			<input type="text" name="userLoginName" data-provide="typeahead"  id="userLoginName" style="width:115px;" title="请输入客户登录名" />
		</div>
	</div>	
	
	<div id="step5" style="margin-top: 3mm;width:100%;" class="pull-left">
		<div class="pull-left" style="width:100px;margin-left: 2mm;" >
				<span class="badge badge-success">4</span>收货时间:	
		</div>
		<div class="pull-left" style="width:200px;">
			<input type="text" name="inWarehouseTimeStart"id="inWarehouseTimeStart" style="width:115px;"/>
		</div>
		<div class="pull-left" style="width:200px;">
			<input type="text" name="inWarehouseTimeEnd"id="inWarehouseTimeEnd" style="width:115px;"/>
		</div>
	</div>	
	
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.js"></script>
    <script type="text/javascript" src="${baseUrl}/static/bootstrap/bootstrap-typeahead.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.showMessage.js"></script>
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
		});
    </script>	
</body>
</html>