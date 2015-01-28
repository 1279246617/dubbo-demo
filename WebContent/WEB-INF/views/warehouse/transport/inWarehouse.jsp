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

<title>COE</title>
</head>
<body style="font-size: 16px;">
	<table class="table" style="width:100%;background-color:#f5f5f5;margin-bottom: 1px;">
			<tr style="height:13px;">
					<td style="width:290px;">
							<span class="pull-left" style="width:75px;">跟踪单号</span>
							<span class="pull-left" style="width:200px;">
								<input type="text"  name="trackingNo"  id="trackingNo"   onfocus="trackingNoFocus()"  onblur="trackingNoBlur()" style="width:170px;"/>
								<input type="text"  name="firstWaybillId"  id="firstWaybillId"   style="display: none;"/>
							</span>
					</td>		
					<td style="width:290px;">
							<span class="pull-left" style="width:75px;">入库概要</span>
							<span class="pull-left" style="width:200px;">
								<input type="text" t="1"  name="orderRemark"  id="orderRemark" style="width:170px;"/>
							</span>
					</td>
					<td  style="width:180px;">
						<span class="pull-left" style="width:50px;">仓库</span>
	          			<span class="pull-left" style="width:120px;">
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
						<span class="pull-left" style="width:70px;" ><b>操作提示:</b></span>
						<span class="pull-left" style="width:260px;color:red;" id="tips">请输入跟踪单号并按回车!</span>
					</td>
			</tr>
	</table>
	
	<div style="height:180px;width:100%;overflow:auto;margin-bottom: 2px;" id="firstWaybillDiv">
			<table   class="table"  style="width:100%;background-color:#f5f5f5;" id="firstWaybilltable" >
				<tr>
					<th style="width:50px;text-align:center;">选择</th>
					<th style="width:155px;text-align:center;">客户帐号</th>
					<th style="width:225px;text-align:center;">跟踪单号</th> 
					<th style="width:205px;text-align:center;">承运商</th>
					<th style="width:205px;text-align:center;">头程运单状态</th> 
					<th style="width:205px;text-align:center;">创建时间</th>
				</tr>
				<tbody id="firstWaybillbody" style="color:#555555;">
					
				</tbody>
		</table>
	</div>
	
	<div style="height:38px;width:100%;;margin-bottom: 2px; overflow: hidden;">
			<table  class="table"  style="width:100%;background-color:#f5f5f5;" >
				<tr>
					<th style="width:200px;height:55px;font-size: 6mm;margin-top: 11mm;">
						分配货位
					</th>
					<th style="text-align:left;font-size: 8mm;">
						<span id="seatCode" style="color:red;font-weight: bold;"></span>
					</th>
				</tr>
			</table>
		</div>
	
	<table  class="table table-striped" style="margin-bottom: 0px;height: 10px;">
		<tr style="height: 10px;">
			<td>
              <form action="${baseUrl}/warehouse/transport/getFirstWaybillData.do?isReceived=Y" id="searchform" name="searchform" method="post">
                  <div class="pull-right searchContent">
                          跟踪单号&nbsp;<input type="text"  name="trackingNo" id="searchFormTrackingNo" title="跟踪单号">
                          <a class="btn btn-primary btn-small" id="btn_search"><i class="icon-search icon-white"></i>搜索</a>
                  </div>
              </form>
			</td>
		</tr>
	</table>	
	
	<div id="maingrid" class="pull-left" style="width:100%;height:14%;">
	</div> 
	
	 <script type="text/javascript" src="${baseUrl}/static/jquery/jquery.js"></script>
	     
    <script type="text/javascript" src="${baseUrl}/static/bootstrap/bootstrap-typeahead.js"></script>
    
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.showMessage.js"></script>
	<script  type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/core/base.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligeruiPatch.js"></script>
    <script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligerTab.js"></script>
    <script  type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligerTree.js" ></script>
	
    <script type="text/javascript" src="${baseUrl}/static/calendar/lhgcalendar.min.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/lhgdialog/prettify/prettify.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/lhgdialog/prettify/lhgdialog.js"></script>
    <script  type="text/javascript" src="${baseUrl}/static/js/warehouse/webSocketReadScales.js" ></script>
    <script  type="text/javascript" src="${baseUrl}/static/js/warehouse/transport/inwarehouse.js" ></script>
    
    <script type="text/javascript">
	   var baseUrl = "${baseUrl}";
	   //进入页面,焦点跟踪单号
	   $("#trackingNo").focus();
	    $(window).keydown(function(event){
	    	//回车加ctrl 下一单
	    	if((event.keyCode   ==   13) &&   (event.ctrlKey)) {
	    		nextInWarehouseRecord();
	    		return;
	    	}
	    	//回车事件
	    	if((event.keyCode   ==   13)) {
		  		saveReceivedFirstWaybill();
	    		return;
	    	}  
	    });
  	  	$(function(){
	    	 initGrid();
   		});
  		//btn_search
		$("#btn_search").click(function(){
			btnSearch("#searchform",grid);
		});
    </script>	
    
	<script type="text/javascript">
	   	 var grid = null;
	     function initGrid() {
	    	   grid = $("#maingrid").ligerGrid({
	                columns: [
	                    { display: '客户帐号', name: 'userNameOfCustomer', align: 'center',width:'9%'},
	                    { display: '仓库', name: 'warehouse', align: 'center', type: 'float',width:'10%'},
	                    { display: '转运类型', name: 'transportType', align: 'center', type: 'float',width:'9%'},
	                    { display: '头程跟踪单号', name: 'trackingNo', align: 'center', type: 'int',width:'13%'},
	                    { display: '头程运单状态', name: 'status', align: 'center', type: 'int',width:'9%'},
	                    { display: '分配货位', name: 'seatCode', align: 'center', type: 'int',width:'10%'},
	                    { display: '订单状态', name: 'orderStatus', align: 'center', type: 'int',width:'9%'},
		                { display: '回传收货状态', name: 'callbackIsSuccess', align: 'center', type: 'float',width:'8%'},
		                { display: '操作员', name: 'userNameOfOperator',width:'9%'},
		                { display: '收货时间', name: 'receivedTime', align: 'center', type: 'float',width:'12%'},
	                ],  
	                isScroll: true,
	                dataAction: 'server',
	                url: baseUrl+'/warehouse/transport/getFirstWaybillData.do?isReceived=Y',
	                pageSize: 5, 
	                usePager: 'true',
	                sortName: 'received_time',
	                sortOrder: 'desc',
	                width: '100%',
	                height: '99%',
	                title:"头程运单收货记录",
	                checkbox: false,
	                rownumbers:true,
	                enabledSort:false
	            });
	        };	
	</script>
    
</body>
</html>