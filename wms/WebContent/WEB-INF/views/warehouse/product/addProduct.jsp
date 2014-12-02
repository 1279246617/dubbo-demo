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
			<th style="width:70px;">产品SKU</th>
			<th>
				<input type="text" name="sku" id="sku" style="width: 120px;"/>
			</th>
			<th style="width:70px;">仓库SKU</th>
			<th>
				<input type="text" name="warehouseSku" id="warehouseSku" style="width: 120px;"/>
			</th>
		</tr>
		<tr>
			<th>产品名称</th>
			<th>
				<input type="text" name="productName" id="productName" style="width: 120px;"/>
			</th>
			<th>产品类型</th>
			<th>
				<input type="text" name="productTypeName" id="productTypeName" style="width: 120px;"/>
			</th>
		</tr>
		<tr>
			<th>所属客户</th>
			<th>
				<input type="text" name="userIdOfCustomer" id="userIdOfCustomer" style="width: 120px;"/>
			</th>
			<th><span title="是否需要生产批次">生产批次..</span></th>
			<th>
				<input type="text" name="isNeedBatchNo"id="isNeedBatchNo" style="width: 120px;"/>
			</th>
		</tr>
		<tr>
			<th>规格型号</th>
			<th>
				<input type="text" name="model" id="model" style="width: 120px;"/>
			</th>
			<th>产品体积</th>
			<th>
				<input type="text" name="volume" id="volume" style="width: 120px;"/>
			</th>
		</tr>
		<tr>
			<th>报关重量</th>
			<th>
				<input type="text" name="customsWeight" id="customsWeight" style="width: 120px;"/>
			</th>
			<th>价值币种</th>
			<th>
				<input type="text" name="currency" id="currency" style="width: 120px;"/>
			</th>
		</tr>
		<tr>
			<th>报关价值</th>
			<th>
				<input type="text" name="customsValue" id="customsValue" style="width: 120px;"/>
			</th>
			<th>行邮税号</th>
			<th>
				<input type="text" name="taxCode" id="taxCode" style="width: 120px;">
			</th>
		</tr>
		<tr>
			<th>产品产地</th>
			<th>
				<input type="text" name="origin" id="origin" style="width: 120px;">
			</th>
			<th>产品备注</th>
			<th>
				<input type="text" name="remark" id="remark" style="width: 120px;">
			</th>
		</tr>
	</table>

	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/bootstrap/bootstrap-typeahead.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.showMessage.js"></script>
	<script type="text/javascript">
		
	</script>
</body>
</html>