<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="${baseUrl}/static/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${baseUrl}/static/bootstrap/bootstrap.min.css" rel="stylesheet"type="text/css" />
	<link href="${baseUrl}/static/bootstrap/common.css" rel="stylesheet" type="text/css"/>
</head>
<body>
	<form id="batchimport" enctype="multipart/form-data" action="${baseUrl}/warehouse/storage/executeImportInWarehouseOrder.do" method="post">
		<table style="text-align: left;margin-top: 5px;margin-left: 5px;" >
				<tr style="height: 40px;">
					<th>客户帐号</th>
					<th>
						<input type="text" name="userLoginName" data-provide="typeahead"  id="userLoginName" style="width:130px;" title="请输入客户登录名" />
					</th>
				</tr>
				
				<tr style="height: 40px;">
					<th>到货仓库</th>
					<th>
						<select style="width:135px;" id="warehouseId" name="warehouseId">
               				<option></option>
							<c:forEach items="${warehouseList}" var="w" >
				       	 		<option value="<c:out value='${w.id}'/>">
				       	 			<c:out value="${w.id}-${w.warehouseName}"/>
				       		 	</option>
				       		 </c:forEach>
						</select>
					</th>
				</tr>
				
				<tr style="height: 50px;">
					<th colspan="2">
						<input type="file" name="file" />   
					</th>
					<th>
						<a class="btn btn-primary btn-small" onclick="submit()" title="确认上传">
			           		 <i class="icon-check"></i>确认上传
			       	 	</a>
					</th>
				</tr>
		</table>
	</form>

</body>
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
   		});
		
		function submit(){
			var $form = $("#batchimport");
			$form.ajaxSubmit({
				dataType : 'text',
				success : function(msg) {
					alert(msg);
					msg = msg.substring(msg.indexOf(">")+1,msg.lastIndexOf("<"));
					alert(msg);
					var obj = JSON.parse(msg);
					
					
					return false;
				}
			});
		}
	 </script>
</html>