<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${baseUrl}/static/css/common.css" rel="stylesheet"
	type="text/css" />
<link href="${baseUrl}/static/bootstrap/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link href="${baseUrl}/static/bootstrap/common.css" rel="stylesheet"
	type="text/css" />
<title>COE</title>
</head>
<body>
	<form action="${baseUrl}/products/updateProductById.do" method="post">
		<table>
			<tr>
				<td>产品名称：</td>
				<td><input name="productName" id="productName"
					value="${product.productName}" type="text"> <input
					name="id" id="id" value="${product.id}" type="hidden"></td>
			</tr>
			<tr>
				<td>产品类型：</td>
				<td><input name="productTypeName" id="productTypeName"
					value="${productType.productTypeName}" type="text"></td>
			</tr>
			<tr>
				<td>所属客户：</td>
				<td><input name="userIdOfCustomer" id="userIdOfCustomer"
					value="${user.loginName}" type="text"></td>
			</tr>
			<tr>
				<td>是否需要生产批次 （Y/N）：</td>
				<td><input name="isNeedBatchNo" id="isNeedBatchNo"
					value="${product.isNeedBatchNo}" type="text"></td>
			</tr>
			<tr>
				<td>产品sku：</td>
				<td><input name="sku" id="sku" value="${product.sku}"
					type="text"></td>
			</tr>
			<tr>
				<td>仓库sku：</td>
				<td><input name="warehouseSku" id="warehouseSku"
					value="${product.warehouseSku}" type="text"></td>
			</tr>
			<tr>
				<td>规格型号：</td>
				<td><input name="model" id="model" value="${product.model}"
					type="text"></td>
			</tr>
			<tr>
				<td>体积：</td>
				<td><input name=volume id="volume" value="${product.volume}"
					type="text"></td>
			</tr>
			<tr>
				<td>报关重量（KG）：</td>
				<td><input name=customsWeight id="customsWeight"
					value="${product.customsWeight}" type="text"></td>
			</tr>
			<tr>
				<td>价值币种：</td>
				<td><input name=currency id="currency"
					value="${product.currency}" type="text"></td>
			</tr>
			<tr>
				<td>报关价值（元）：</td>
				<td><input name=customsValue id="customsValue"
					value="${product.customsValue}" type="text"></td>
			</tr>
			<tr>
				<td>行邮税号（ETK B2C渠道专用报关字段）：</td>
				<td><input name=taxCode id="taxCode" value="${product.taxCode}"
					type="text"></td>
			</tr>
			<tr>
				<td>原产地：</td>
				<td><input name=origin id="origin" value="${product.origin}"
					type="text"></td>
			</tr>
			<tr>
				<td>备注：</td>
				<td><input name=remark id="remark" value="${product.remark}"
					type="text"></td>
			</tr>
		</table>
	</form>

	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.js"></script>
	<script type="text/javascript"
		src="${baseUrl}/static/bootstrap/bootstrap-typeahead.js"></script>
	<script type="text/javascript"
		src="${baseUrl}/static/jquery/jquery.showMessage.js"></script>
</body>
</html>