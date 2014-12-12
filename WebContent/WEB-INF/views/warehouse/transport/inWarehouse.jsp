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
	<div class="pull-left" style="width:100%;height:280px; margin-top: 1px;" >
		<table class="table table-striped" style="width:100%;margin-bottom: 5px">
				<tr style="height:15px;">
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
							<span class="pull-left" style="width:70px;" ><b>操作提示:</b></span>
							<span class="pull-left" style="width:300px;color:red;" id="tips">请输入跟踪单号并按回车!</span>
						</td>
				</tr>
		</table>
		<div style="height:100px;width:100%;overflow:auto;" id="littlePackageDiv">
				<table  class="table table-striped" style="width:100%;margin-bottom: 5px;display:none;" id="littlePackagetable" >
					<tr>
						<th style="width:25px;text-align:center;">选择</th>
						<th style="width:155px;text-align:center;">客户帐号</th>
						<th style="width:225px;text-align:center;">跟踪单号</th> 
						<th style="width:205px;text-align:center;">承运商</th>
						<th style="width:205px;text-align:center;">状态</th> 
						<th style="width:205px;text-align:center;">创建时间</th>
					</tr>
					<tbody id="littlePackagebody">
					</tbody>
			</table>
		</div>
		<table class="table table-striped" style="width:1200px;margin-bottom: 0px">
			<tr>
					<td  >
						<span class="pull-left" style="width:75px;">商品条码</span>
						<span class="pull-left" style="width:170px;">
							<input type="text"  name="itemSku" t="2"  id="itemSku" style="width:130px;"/>
						</span>
						<span class="pull-left" style="width:75px;">商品数量</span>
						<span class="pull-left" style="width:112px;">
							<input type="text"  name="itemQuantity"  id="itemQuantity" t="3" style="width:90px;" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d]/g,'')"/>
						</span>
						
						<span class="pull-left" style="width:75px;">明细备注</span>
						<span class="pull-left" style="width:180px;">
							<input type="text"  name="itemRemark"  id="itemRemark" t="4" style="width:160px;"/>
						</span>
						
						<span class="pull-left" style="width:105px;">
							<a class="btn  btn-primary" id="enterItem" onclick="saveInWarehouseRecordItem('N');" style="cursor:pointer;"><i class="icon-ok icon-white"></i>保存明细</a>
						</span>
						<span class="pull-left" style="width:135px;">
							<a class="btn  btn-primary" id="enterItem" onclick="nextInWarehouseRecord();" style="cursor:pointer;height: 15px;"><i class="icon-ok icon-white"></i>完成本次收货</a>
						</span>
					</td>	
			</tr>	
		</table>
	</div>
	
	<table  class="table table-striped" style="margin-bottom: 0px">
		<tr>
			<td>
				<div class="pull-left">
				<!-- 工具栏 -->
               </div>
              
              <form action="${baseUrl}/warehouse/transport/getLittlePackageData.do?isReceived=Y" id="searchform" name="searchform" method="post">
                  <div class="pull-right searchContent">
                          跟踪单号&nbsp;<input type="text"  name="trackingNo" id="searchFormTrackingNo" title="跟踪单号">
                          <a class="btn btn-primary btn-small" id="btn_search"><i class="icon-search icon-white"></i>搜索</a>
                  </div>
              </form>
			</td>
		</tr>
	</table>	
	<div id="maingrid" class="pull-left" style="width:100%;">
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
	                    { display: '客户帐号', name: 'userNameOfCustomer', align: 'center',width:'10%'},
	                    { display: '仓库', name: 'warehouse', align: 'center', type: 'float',width:'10%'},
	                    { display: '跟踪单号', name: 'trackingNo', align: 'center', type: 'int',width:'13%'},
	                    { display: '承运商', name: 'carrierCode', align: 'center', type: 'int',width:'10%'},
	                    { display: '状态', name: 'status', align: 'center', type: 'int',width:'10%'},
		                { display: '收货时间', name: 'receivedTime', align: 'center', type: 'float',width:'13%'},
		                { display: '回传收货状态', name: 'callbackIsSuccess', align: 'center', type: 'float',width:'10%'},
		                { display: '操作员', name: 'userNameOfOperator',width:'9%'},
		                { display: '备注', name: 'remark', align: 'center', type: 'float',width:'13%'}
	                ],  
	                isScroll: true,
	                dataAction: 'server',
	                url: baseUrl+'/warehouse/transport/getLittlePackageData.do?isReceived=Y',
	                pageSize: 10, 
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