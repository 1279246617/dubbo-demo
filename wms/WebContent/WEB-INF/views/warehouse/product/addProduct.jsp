<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//Dth HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dth">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${baseUrl}/static/css/common.css" rel="stylesheet"type="text/css" />
<link href="${baseUrl}/static/bootstrap/bootstrap.min.css"rel="stylesheet" type="text/css" />
<link href="${baseUrl}/static/bootstrap/common.css" rel="stylesheet" type="text/css" />
<title>COE</title>
</head>
<body>
	<table  class="table"  style="margin-top:15px;">
		<tr>
			<th style="width:70px;">商品SKU</th>
			<th>
				<input type="text" name="sku" id="sku" style="width: 120px;"/>
			</th>
			<th style="width:70px;">仓库SKU</th>
			<th>
				<input type="text" name="warehouseSku" id="warehouseSku" style="width: 120px;"/>
			</th>
		</tr>
		<tr>
			<th  >商品名称</th>
			<th>
				<input type="text" name="productName" id="productName" style="width: 120px;"/>
			</th>
			<th>商品类型</th>
			<th>
					<select style="width:125px;" id="productTypeId" name="productTypeId">
             				<option></option>
							<c:forEach items="${productTypeList}" var="p" >
		       	 					<option value="<c:out value='${p.id}'/>">
		       	 							<c:out value="${p.id}-${p.productTypeName}"/>
		       		 				</option>
		       		 		</c:forEach>
				</select>
			</th>
		</tr>
		<tr>
			<th>客户帐号</th>
			<th>
               	<input type="text" name="userLoginName" data-provide="typeahead"  id="userLoginName" style="width:120px;" title="请输入客户登录名" />
			</th>
			
			<th><span title="是否需要生产批次">批次管理</span></th>
			<th>
				<select style="width:125px;" id="isNeedBatchNo" name="isNeedBatchNo">
							<option value="N">N</option>
             				<option value="Y">Y</option>
				</select>
			</th>
		</tr>
		<tr>
			<th>规格型号</th>
			<th>
				<input type="text" name="model" id="model" style="width: 120px;"/>
			</th>
			<th>商品体积</th>
			<th>
				<input type="text" name="volume" id="volume" style="width: 120px;" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" />
			</th>
		</tr>
		<tr>
			<th>报关价值</th>
			<th>
				<input type="text" name="customsValue" id="customsValue" style="width: 120px;" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" />元
			</th>
			<th>价值币种</th>
			<th>
				<select style="width:125px;" id="currency" name="currency">
							<c:forEach items="${currencyList}" var="c" >
		       	 					<option value="<c:out value='${c.code}'/>">
		       	 							<c:out value="${c.code}-${c.cn}"/>
		       		 				</option>
		       		 		</c:forEach>
				</select>
			</th>
		</tr>
		<tr>
			<th>报关重量</th>
			<th>
				<input type="text" name="customsWeight" id="customsWeight" style="width: 120px;" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" />KG
			</th>
			<th>行邮税号</th>
			<th>
				<input type="text" name="taxCode" id="taxCode" style="width: 120px;">
			</th>
		</tr>
		<tr>
			<th>商品产地</th>
			<th>
				<input type="text" name="origin" id="origin" style="width: 120px;">
			</th>
			<th>商品条码</th>
			<th>
				<input type="text" name="barcode" id="barcode" style="width: 120px;">
			</th>
		</tr>
		<tr>
			<th>商品备注</th>
			<th colspan="3">
				<input type="text" name="remark" id="remark" style="width: 320px;">
			</th>
		</tr>
	</table>

	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/bootstrap/bootstrap-typeahead.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.showMessage.js"></script>
	<script type="text/javascript">
		var baseUrl = '${baseUrl}'; 
		$(function(){
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