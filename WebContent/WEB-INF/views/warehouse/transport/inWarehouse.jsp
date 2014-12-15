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
								<input type="text"  name="trackingNo"  id="trackingNo" t="1" onfocus="trackingNoFocus()"  onblur="trackingNoBlur()" style="width:170px;"/>
								<!-- 用户按回车时,当入库订单id 为空是第一次提交,后台返回id,或其他提示.  不为空 提示客户可输入SKU和数量进行收货 -->
								<input type="text"  name="littlePackageId"  id="littlePackageId" t="1"  style="display: none;"/>
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
						<span class="pull-left" style="width:300px;color:red;" id="tips">请输入跟踪单号并按回车!</span>
					</td>
			</tr>
	</table>
	
	<div style="height:140px;width:100%;overflow:auto;margin-bottom: 2px;" id="littlePackageDiv">
			<table   class="table"  style="width:100%;background-color:#f5f5f5;" id="littlePackagetable" >
				<tr>
					<th style="width:50px;text-align:center;">选择</th>
					<th style="width:155px;text-align:center;">客户帐号</th>
					<th style="width:225px;text-align:center;">跟踪单号</th> 
					<th style="width:205px;text-align:center;">承运商</th>
					<th style="width:205px;text-align:center;">状态</th> 
					<th style="width:205px;text-align:center;">创建时间</th>
				</tr>
				<tbody id="littlePackagebody" style="color:#555555;">
					
				</tbody>
		</table>
	</div>
	
		<table class="table" style="background-color:#f5f5f5;margin-bottom: 2px;"  >
			<tr>
				<th>直接转运订单称重与打出货运单</th>
			</tr>
			<tr  style="color:#555555;">
				<td colspan="2" style="height:50px;">
					<span style="width:90px;height:30px;margin-top: 4mm;font-size: 5mm;" class="pull-left" >装箱重量</span>
					<input type="text"  name="weight"  t="3"  id="weight"  style="width:130px;height:45px; font-size: 10mm;font-weight: bold;color:red;" class="pull-left" readonly="readonly" onkeyup="this.value=this.value.replace(/[^\d\.]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d\.]/g,'')"/>
					<span style="width:50px;height:30px;margin-top: 4mm;font-size: 6mm;font-weight: bold;" class="pull-left" >KG</span>
					<span style="width:90px;height:30px;margin-top: 4mm;font-size: 4mm;" class="pull-left" >
						<input class="pull-left" name="auto" style="vertical-align: middle;" type="checkbox" checked="checked" id="auto">
						自动读取
					</span>
					<span style="height:30px;margin-top: 0mm;font-size: 8mm;" class="pull-left" >
						<img id="weightOk" src="${baseUrl}/static/img/nike.png" style="display:none;width:15mm;height:15mm;" >
					</span>
				</td>
				<td colspan="1">
					<span style="width:90px;height:30px;margin-top: 4mm;font-size: 5mm;" class="pull-left" >
							<a class="btn  btn-primary"  onclick="saveweight();"  style="cursor:pointer;"><i class="icon-ok icon-white"></i>保存重量</a>
					</span>
				</td>
			</tr>
			<tr  >
				<td colspan="1" style="width:300px;">
					出货渠道&nbsp;&nbsp;
					<input type="text"  name="shipwayCode"  id="shipwayCode"   t="4" style="width:130px;" readonly="readonly"/>
				</td>
				<td colspan="1" style="width:350px;">
					跟踪单号&nbsp;&nbsp;
					<input type="text"  name="trackingNo"  id="trackingNo"  t="4"  style="width:150px;" readonly="readonly"/>
				</td>
				
				<td colspan="1" >
					<a class="btn  btn-primary"  onclick="printShipLabel();" style="cursor:pointer;">
						<i class="icon-ok icon-white"></i>打印出货运单
					</a>
				</td>
			</tr>
	</table>			
	<table  class="table table-striped" style="margin-bottom: 0px;height: 10px;">
		<tr style="height: 10px;">
			<td>
              <form action="${baseUrl}/warehouse/transport/getLittlePackageData.do?isReceived=Y" id="searchform" name="searchform" method="post">
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
    
    <script  type="text/javascript" src="${baseUrl}/static/js/warehouse/transport/inwarehouse.js" ></script>
    
    <script type="text/javascript">
	   var baseUrl = "${baseUrl}";
	   //进入页面,焦点跟踪单号
	   $("#trackingNo").focus();
	   var focus = "1";
	    $(window).keydown(function(event){
	    	//回车加ctrl 下一单
	    	if((event.keyCode   ==   13) &&   (event.ctrlKey)) {
	    		nextInWarehouseRecord();
	    		return;
	    	}
	    	//回车事件
	    	if((event.keyCode   ==   13)) {
	    		if(focus == '1'){
		  		  	saveReceivedLittlePackage();
		  		  	return false;
	  		  	}
	    		//sku焦点,回车,切换quantity焦点
				if(focus == '2'){
					$("#itemQuantity").val("");
					$("#itemQuantity").focus();
					return false;
				}
				//quantity焦点,回车,提交保存明细
				if(focus == '3'){
					saveInWarehouseRecordItem('N');
		      		return false;
				}
				//remark 焦点,回车,提交保存明细
				if(focus == '4'){
					saveInWarehouseRecordItem('N');
		      		return false;
				}
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
	                    { display: '到货跟踪单号', name: 'trackingNo', align: 'center', type: 'int',width:'13%'},
	                    { display: '承运商', name: 'carrierCode', align: 'center', type: 'int',width:'10%'},
	                    { display: '状态', name: 'status', align: 'center', type: 'int',width:'9%'},
		                { display: '收货时间', name: 'receivedTime', align: 'center', type: 'float',width:'12%'},
		                { display: '回传收货状态', name: 'callbackIsSuccess', align: 'center', type: 'float',width:'9%'},
		                { display: '操作员', name: 'userNameOfOperator',width:'8%'},
		                { display: '备注', name: 'remark', align: 'center', type: 'float',width:'9%'}
	                ],  
	                isScroll: true,
	                dataAction: 'server',
	                url: baseUrl+'/warehouse/transport/getLittlePackageData.do?isReceived=Y',
	                pageSize: 5, 
	                usePager: 'true',
	                sortName: 'received_time',
	                sortOrder: 'desc',
	                width: '100%',
	                height: '99%',
	                title:"小包收货记录(最新)",
	                checkbox: false,
	                rownumbers:true,
	                enabledSort:false
	            });
	        };	
	</script>
</body>
</html>