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
<body>
	<div class="pull-left" style="width:100%;height:280px; margin-top: 1px;" >
			<table class="table table-striped" style="width:100%;margin-bottom: 5px">
					<tr style="height:15px;">
							<td style="width:232px;">
									<span class="pull-left" style="width:52px;">跟踪单号</span>
									<span class="pull-left" style="width:170px;">
										<!--  利用focus和blur 判断跟踪号是否有变化, 变化则把入库订单id清空-->
										<input type="text"  name="trackingNo"  id="trackingNo" t="1"   style="width:140px;"/>
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
							<td>&nbsp;</td>
					</tr>
			</table>
			<table  class="table table-striped" style="width:100%;margin-bottom: 5px;display:none;" id="inWarehouseRecordtable" >
				<tr>
					<th style="width:25px;text-align:center;">选择</th>
					<th style="width:155px;text-align:center;">客户帐号</th>
					<th style="width:225px;text-align:center;">跟踪单号</th> 
					<th style="width:205px;text-align:center;">批次号</th>
					<th style="width:205px;text-align:center;">收货操作员</th> 
					<th style="width:205px;text-align:center;">收货时间</th>
				</tr>
				<tbody id="inWarehouseRecordtbody">
				</tbody>
			</table>
			<div style="height:30px;">
			</div>
				
		<table class="table table-striped" style="width:1200px;margin-bottom: 0px">
			<tr>
					<td  >
						<span class="pull-left" style="width:52px;">库位</span>
						<span class="pull-left" style="width:160px;">
							<input type="text"  name="seatCode" t="2"  id="seatCode" style="width:130px;"/>
						</span>
						
						<span class="pull-left" style="width:52px;">产品SKU</span>
						<span class="pull-left" style="width:160px;">
							<input type="text"  name="itemSku" t="2"  id="itemSku" style="width:130px;"/>
						</span>
						
						<span class="pull-left" style="width:52px;">产品数量</span>
						<span class="pull-left" style="width:102px;">
							<input type="text"  name="itemQuantity"  id="itemQuantity" t="2" style="width:90px;" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
						</span>
						
						<span class="pull-left" style="width:105px;">
							<a class="btn  btn-primary" id="enterItem" onclick="saveOnShelvesItem();" style="cursor:pointer;"><i class="icon-ok icon-white"></i>保存上架</a>
						</span>
					</td>	
			</tr>	
		</table>
	</div>
	
	 <script type="text/javascript" src="${baseUrl}/static/jquery/jquery.js"></script>
    <script type="text/javascript" src="${baseUrl}/static/bootstrap/bootstrap-typeahead.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.showMessage.js"></script>
	<script  type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/core/base.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligeruiPatch.js"></script>
    <script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligerTab.js"></script>
    <script  type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligerTree.js" ></script>
    
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
	  			$(this).select();
	  		});
   		});
  	  	
  	  //回车事件
  	  function clickEnter(){
  		  	if(focus == '1'){
  		  		enterTrackingNoStep();	
  		  	}
    		//保存明细
			if(focus == '2'){
				saveOnShelvesItem();
			}
  	  }
	  	 
  	 function enterTrackingNoStep(){
   		$("#inWarehouseRecordtbody").html("");
   		var trackingNoStr  = $("#trackingNo").val();
   		// 检查跟踪号是否能找到唯一的入库订单
   		$.getJSON(baseUrl+ '/warehouse/shelves/checkFindInWarehouseRecord.do?trackingNo='+ trackingNoStr, function(msg) {
   			if (msg.status == -1) {
   				parent.$.showDialogMessage(msg.message, null, null);
   				return false;
   			}
   			var table = $("#inWarehouseRecordtable");
   			table.show();
   			$.each(msg.mapList, function(i, n) {
   				var tr = "<tr>";
   				if (msg.status == 1) {
   					tr+="<td style='width:25px;text-align:center;'><input type='radio' t='1' recordId='"+n.recordId+"' name='inWarehouseOrderRadio' value='radiobutton' checked></td>";	
   				}else{
   					tr+="<td style='width:25px;text-align:center;'><input type='radio' t='1' recordId='"+n.recordId+"' name='inWarehouseOrderRadio' value='radiobutton'></td>";
   				}
   				tr+="<td style='width:155px;text-align:center;'>"+n.userLoginName+"</td>";
   				tr+="<td style='width:225px;text-align:center;'>"+n.trackingNo+"</td>";
   				tr+="<td style='width:205px;text-align:center;'>"+n.batchNo+"</td>";
   				tr+="<td style='width:205px;text-align:center;'>"+n.userLoginNameOfOperator+"</td>";
   				tr+="<td style='width:205px;text-align:center;'>"+n.createdTime+"</td>";
   				tr+="</tr>";
   				$("#inWarehouseRecordtbody").append(tr);
   			});
   			if (msg.status == 2) {
   				parent.$.showDialogMessage(msg.message, null, null);
   				return false;
   			}
   		});
   	  }
	  	  
  	  function saveOnShelvesItem(){
	  		//在判断跟踪号是否改变前,获取用户选择的入库订单
	  		var inWarehouseOrderRadio = $('input[name="inWarehouseOrderRadio"]').filter(':checked');
	  		var recordId = '';
	  		if(inWarehouseOrderRadio.length){
	  			recordId = inWarehouseOrderRadio.attr("recordId");
	  		}
	  		if(recordId == ''){
	  			parent.$.showDialogMessage("请输入正确跟踪单号,并选择一个收货记录", null, null);
	  			return false;
	  		}
	  		var itemSku = $("#itemSku").val();
	  		if(itemSku == null || itemSku ==''){
	  			parent.$.showShortMessage({msg:"请输入产品SKU",animate:true,left:"40%"});
	  			return false;
	  		}
	  		var itemQuantity = $("#itemQuantity").val();
	  		if(itemQuantity == null || itemQuantity ==''){
	  			parent.$.showShortMessage({msg:"请输入产品数量",animate:true,left:"40%"});
	  			return false;
	  		}
	  		var seatCode = $("#seatCode").val();
	  		if(seatCode == null || seatCode ==''){
	  			parent.$.showShortMessage({msg:"请输入库位",animate:true,left:"40%"});
	  			return false;
	  		}
	  		
	  		
  	  }
    </script>	
</body>
</html>