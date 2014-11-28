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
		<div class="pull-left" style="width:200px;">
			产品名称:<input type="text" name="productName" id="productName" style="width:100px;">
		</div>
		<div class="pull-left" style="width:200px;">
			产品类型：<input type="text" name="productTypeId" id="productTypeId" style="width:100px;">
		</div>
	</div>
	
	<div id="step2" style="margin-top: 6mm;width:100%;" class="pull-left">
		<div class="pull-left" style="width:200px;">
			所属客户：<input type="text" name="userIdOfCustomer" id="userIdOfCustomer" style="width:100px;">
		</div>
		<div class="pull-left" style="width:200px;">
			是否需要生产批次 （Y/N）：<input type="text" name="isNeedBatchNo" id="isNeedBatchNo" style="width:100px;">
		</div>
	</div>	
	
	<div id="step3" style="margin-top: 6mm;width:100%;" class="pull-left">
		<div class="pull-left" style="width:200px;">
			产品sku：<input type="text" name="sku" id="sku" style="width:100px;">
		</div>
		<div class="pull-left" style="width:200px;">
			仓库sku：<input type="text" name="warehouseSku" id="warehouseSku" style="width:100px;">
		</div>
	</div>
	
	<div id="step4" style="margin-top: 6mm;width:100%;" class="pull-left">
		<div class="pull-left" style="width:200px;">
			规格型号：<input type="text" name="model" id="model" style="width:100px;">
		</div>
	</div>
	
	<div id="step5" style="margin-top: 6mm;width:100%;" class="pull-left">
		<div class="pull-left" style="width:200px;">
			体积：<input type="text" name="volume" id="volume" style="width:100px;">
		</div>
		<div class="pull-left" style="width:200px;">
			报关重量（KG）：<input type="text" name="customsWeight" id="customsWeight" style="width:100px;">
		</div>
	</div>	
	
	<div id="step6" style="margin-top: 6mm;width:100%;" class="pull-left">
		<div class="pull-left" style="width:200px;">
			价值币种：<input type="text" name="currency" id="currency" style="width:100px;">
		</div>
		<div class="pull-left" style="width:200px;">
			报关价值（元）：<input type="text" name="customsValue" id="customsValue" style="width:100px;">
		</div>
	</div>	
	
	<div id="step7" style="margin-top: 6mm;width:100%;" class="pull-left">
		<div class="pull-left" style="width:200px;">
			行邮税号（ETK B2C渠道专用报关字段）：<input type="text" name="taxCode" id="taxCode" style="width:100px;">
		</div>
		<div class="pull-left" style="width:200px;">
			原产地：<input type="text" name="origin" id="origin" style="width:100px;">
		</div>
	</div>
	
	<div id="step8" style="margin-top: 6mm;width:100%;" class="pull-left">
		<div class="pull-left" style="width:200px;">
			备注：<input type="text" name="remark" id="remark" style="width:100px;">
		</div>
	</div>
	
	
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.js"></script>
    <script type="text/javascript" src="${baseUrl}/static/bootstrap/bootstrap-typeahead.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.showMessage.js"></script>
    <script type="text/javascript">
    </script>
</body>
</html>