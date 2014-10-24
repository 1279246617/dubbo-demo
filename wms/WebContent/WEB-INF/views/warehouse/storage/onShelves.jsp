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
<link href="${baseUrl}/static/ligerui/ligerUI/skins/dAqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />

<title>COE</title>
</head>
<body>
	<div class="pull-left" style="width:100%;height:280px; margin-top: 1px;" >
		 <form action="${baseUrl}/warehouse/storage/getInWarehouseRecordItemData.do" id="searchform" name="searchform" method="post">
			<table class="table table-striped" style="width:100%;margin-bottom: 5px">
					<tr style="height:15px;">
							<td style="width:232px;">
									<span class="pull-left" style="width:52px;">跟踪单号</span>
									<span class="pull-left" style="width:170px;">
										<!--  利用focus和blur 判断跟踪号是否有变化, 变化则把入库订单id清空-->
										<input type="text"  name="trackingNo"  id="trackingNo" t="1" onfocus="trackingNoFocus()"  style="width:140px;"/>
										<!-- 用户按回车时,当入库订单id 为空是第一次提交,后台返回id,或其他提示.  不为空 提示客户可输入SKU和数量进行收货 -->
										<input type="text"  name="inWarehouseOrderId"  id="inWarehouseOrderId" t="1"  style="display: none;"/>
										<input type="text"  name="inWarehouseRecordId"  id="inWarehouseRecordId" t="1"  style="display: none;"/>
									</span>
							</td>		
							<td style="width:212px;">
									<span class="pull-left" style="width:52px;">入库概要</span>
									<span class="pull-left" style="width:160px;">
										<input type="text" t="1"  name="orderRemark"  id="orderRemark" style="width:130px;"/>
									</span>
							</td>
							<td  style="width:160px;">
								<span class="pull-left" style="width:30px;">仓库</span>
			          			<span class="pull-left" style="width:110px;">
				          			<select style="width:80px;" id="warehouseId" name="warehouseId">
										<c:forEach items="${warehouseList}" var="w" >
						       	 			<option value="<c:out value='${w.id}'/>">
						       	 				<c:out value="${w.id}-${w.warehouseName}"/>
						       		 		</option>
						       			</c:forEach>
									</select>
		          				</span>							
							</td>
							<td>
								<span class="pull-left" style="width:55px;" ><b>操作提示:</b></span>
								<span class="pull-left" style="width:352px;color:red;" id="tips">请输入跟踪单号并按回车!</span>
							</td>
					</tr>
			</table>
			<table  class="table table-striped" style="width:100%;margin-bottom: 5px;display:none;" id="inWarehouseOrdertable" >
				<tr>
					<th style="width:25px;text-align:center;">选择</th>
					<th style="width:155px;text-align:center;">客户帐号</th>
					<th style="width:225px;text-align:center;">跟踪单号</th> 
					<th style="width:205px;text-align:center;">承运商</th>
					<th style="width:205px;text-align:center;">客户参考号</th> 
					<th style="width:205px;text-align:center;">创建时间</th>
				</tr>
				<tbody id="inWarehouseOrdertbody">
				</tbody>
			</table>
			<div style="height:30px;">
			</div>
		</form>
		
		<table class="table table-striped" style="width:1200px;margin-bottom: 0px">
			<tr>
					<td  >
						<span class="pull-left" style="width:52px;">产品SKU</span>
						<span class="pull-left" style="width:160px;">
							<input type="text"  name="itemSku" t="2"  id="itemSku" style="width:130px;"/>
						</span>
						
						<span class="pull-left" style="width:52px;">产品数量</span>
						<span class="pull-left" style="width:102px;">
							<input type="text"  name="itemQuantity"  id="itemQuantity" t="2" style="width:90px;" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
						</span>
						
						
						<span class="pull-left" style="width:52px;">明细备注</span>
						<span class="pull-left" style="width:180px;">
							<input type="text"  name="itemRemark"  id="itemRemark" t="2" style="width:160px;"/>
						</span>
						
						<span class="pull-left" style="width:105px;">
							<a class="btn  btn-primary" id="enterItem" onclick="saveInWarehouseRecordItem();" style="cursor:pointer;"><i class="icon-ok icon-white"></i>保存明细</a>
						</span>
					</td>	
			</tr>	
		</table>
	</div>
	
	 <script type="text/javascript" src="${baseUrl}/static/jquery/jquery.js"></script>
    <script type="text/javascript" src="${baseUrl}/static/bootstrap/bootstrap-typeahead.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.showMessage.js"></script>
	<script  type="text/javascript" src="${baseUrl}/static/js/warehouse/inwarehouse.js" ></script>
    
    <script type="text/javascript">
	   var baseUrl = "${baseUrl}";
	   //进入页面,焦点跟踪单号
	   $("#trackingNo").focus();
	   var focus = "1";
	    $(window).keydown(function(event){
	    	//回车事件
	    	if((event.keyCode   ==   13)) {
	    		clickEnter();
	    		return;
	    	}  
	    });
		
  	  $(function(){
	  		$("input").focus(function(){
	  			//当前获取焦点的文本框是 主单还是明细
	  			focus = $(this).attr("t");
	  		});
	    	 initGrid();
   		});
  	  	
  	  //回车事件
  	  function clickEnter(){
			
  		  
  	  }
    </script>	
</body>
</html>