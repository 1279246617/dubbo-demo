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
				<span class="badge badge-success">1</span>所属仓库:	
		</div>
		<div class="pull-left" style="width:200px;">
             <select style="width:100px;" id="warehouseId" name="warehouseId">
					<c:forEach items="${warehouseList}" var="w" >
	       	 			<option value="<c:out value='${w.id}'/>">
	       	 				<c:out value="${w.id}-${w.warehouseName}"/>
	       		 		</option>
	       			</c:forEach>
			 </select>
		</div>
	</div>	
	
	<div id="step2" style="margin-top: 6mm;width:100%;" class="pull-left">
		<div class="pull-left" style="width:100px;margin-left: 2mm;" >
				<span class="badge badge-success">2</span>货架类型:	
				<input style="display: none;" id="shelfType" type="text" value='G'>
		</div>
		<div class="pull-left" style="width:100px;">
			<input type="radio" name="shelfType" id="typeG" checked="checked" onclick="changeType()">地面货架
		</div>
		<div class="pull-left" style="width:100px;">	
			<input  type="radio" name="shelfType"  id="typeB" onclick="changeType()">立体货架
		</div>
	</div>	
	
	<div style="margin-top: 6mm;width:100%;" class="pull-left">
		<div class="pull-left" style="width:100px;margin-left: 2mm;" >
				<span class="badge badge-success">3</span>业务类型:	
				<input style="display: none;" id="businessType" type="text" value='S'>
		</div>
		<div class="pull-left" style="width:100px;">
			<input type="radio" name="businessType" id="typeS" checked="checked" onclick="changeType()">仓配业务
		</div>
		<div class="pull-left" style="width:100px;">	
			<input  type="radio" name="businessType"  id="typeZ" onclick="changeBusinessType()">直接转运
		</div>
		<div class="pull-left" style="width:100px;">	
			<input  type="radio" name="businessType"  id="typeJ" onclick="changeBusinessType()">集货转运
		</div>
	</div>	
	
	<div id="step5" style="margin-top: 6mm;width:100%;" class="pull-left">
		<div class="pull-left" style="width:100px;margin-left: 2mm;" >
						<span class="badge badge-success">4</span>货架编号:
		</div>
		<div class="pull-left" style="width:400px;">
			<input name="shelfTypeName" id="shelfTypeName" style="width:40px;" type="text" value="G" readonly="readonly">
			货架起始:<input type="text" name="shelfNoStart" id="shelfNoStart" style="width:70px;" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  >
			货架截止:<input type="text" name="shelfNoEnd" id="shelfNoEnd" style="width:70px;"  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" >
		</div>
	</div>	
	
		
	<div id="step3" style="margin-top:6mm;width:100%;" class="pull-left">
		<div class="pull-left" style="width:100px;margin-left: 2mm;" >
				<span class="badge badge-success">5</span>货位设置:
		</div>
		<div class="pull-left" style="width:180px;">
			货位起始:<input type="text" name="start" id="start" style="width:80px;"  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" >
		</div>
		<div class="pull-left" style="width:200px;">	
			货位截止:<input type="text" name="end"  id="end" style="width:80px;"  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" >
		</div>
	</div>	
	
	<div id="step4" style="margin-top:6mm;width:100%;display: none;" class="pull-left">
		<div class="pull-left" style="width:100px;margin-left: 2mm;" >
				<span class="badge badge-success">5</span>货位设置:
		</div>
		<div class="pull-left" style="width:180px;">
			货位层数:<input type="text" name="rows" id="rows" style="width:80px;"  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" >
		</div>
		<div class="pull-left" style="width:200px;">	
			货位列数:<input type="text" name="cols" id="cols" style="width:80px;"  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" >
		</div>
	</div>
	
	<div id="step6" style="margin-top: 6mm;width:100%;" class="pull-left">
		<div class="pull-left" style="width:100px;margin-left: 2mm;" >
				<span class="badge badge-success">6</span>货架备注:
		</div>
		<div class="pull-left" style="width:200px;">
			<input type="text" name="remark" id="remark" style="width:100px;">
		</div>
	</div>	
	
	<div id="step7" style="margin-top: 10mm;width:100%;display: none;" class="pull-left" >
		<div class="pull-left" style="width:95%;margin-left: 2mm;color: red;font-weight: bold;line-height: 6mm;" >
				<table style="color: red;" class="table">
					<tr>
						<th>注:</th>
						<th>货架编号:B + 大于0小于999的数字</th>
					</tr>
					<tr>
						<th>&nbsp;</th>
						<th>列数:请填写大于0,小于99的数字;层数:请填写大于0小于26的数字</th>
					</tr>
				</table>			
		</div>
	</div>	
	
	<div id="step8" style="margin-top: 10mm;width:100%;" class="pull-left">
		<div class="pull-left" style="width:95%;margin-left: 2mm;color: red;font-weight: bold;line-height: 6mm;" >
				<table style="color: red;" class="table">
					<tr>
						<th>注:</th>
						<th>货架编号:G + 大于0小于9的数字</th>
					</tr>
					<tr>
						<th>&nbsp;</th>
						<th>起始:请填写大于0,小于999的数字;截止:请填写大于0,小于999的数字</th>
					</tr>
				</table>		
		</div>
	</div>	
	. 


	
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.js"></script>
    <script type="text/javascript" src="${baseUrl}/static/bootstrap/bootstrap-typeahead.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.showMessage.js"></script>
    <script type="text/javascript">
			   function changeType(){
				   if($('#typeG').is(':checked')){
						//选中地面货架
						$("#step4").hide();
					   $("#step3").show();
					   $("#step7").hide();
					   $("#step8").show();
					   $("#shelfType").val("G");
					   $("#shelfTypeName").val("G");
					}else{
						//选中立体货架
						$("#step3").hide();
						$("#step4").show();
						$("#step8").hide();
						$("#step7").show();
						$("#shelfType").val("B");
						 $("#shelfTypeName").val("B");
					}
			   }
			   function changeBusinessType(){
				   if($('#typeS').is(':checked')){
					   $("#businessType").val("S");
					}else if($('#typeZ').is(':checked')){
						$("#businessType").val("Z");
					}else{
						$("#businessType").val("J");
					}
			   }
    </script>	
</body>
</html>