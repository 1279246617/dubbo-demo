<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

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
	<div class="pull-left" style="width:100%;height:140px; margin-top: 10px;" >
		 <form action="${baseUrl}/warehouse/storage/getInWarehouseOrder.do" id="searchform" name="searchform" method="post">
			<table class="table table-striped" style="width:850px;">
					<tr>
							<td>
									跟踪单号&nbsp;&nbsp;<input type="text"  name="trackingNo"  id="trackingNo" style="width:200px;"/>
							</td>		
							<td>
								客户帐号&nbsp;&nbsp;<input type="text" name="userLoginName"  id="userLoginName" style="width:160px;" readonly="readonly"/>
		          				<img class="tips" id="customerNoTips" msg="根据运单号找不到唯一的入库订单时,将要求输入客户帐号" src="${baseUrl}/static/img/help.gif">
		          				&nbsp;&nbsp;
		          				 <input type="checkbox" name="unKnowCustomer" id="unKnowCustomer" readonly="readonly"/>&nbsp;标记为无主件
		          				&nbsp;&nbsp;&nbsp;&nbsp;
		          				 <a class="btn  btn-primary" id="enter" onclick="clickEnter()" style="cursor:pointer;">确认提交</a>
		          				 &nbsp;&nbsp;&nbsp;&nbsp;
		          				 <a class="btn  btn-primary" style="cursor:pointer;">摘要</a>
							</td>
					</tr>
			</table>
		</form>
		
		<table class="table table-striped" style="width:1150px;">
			<tr>
					<td>产品SKU&nbsp;&nbsp;<input type="text"  name="itemSku"  id="itemSku" style="width:130px;"/></td>		
					<td>
						产品数量&nbsp;&nbsp;<input type="text"  name="itemQuantity"  id="itemQuantity" style="width:90px;"/>
						&nbsp;&nbsp; 
						<a class="btn  btn-primary" id="enterItem" style="cursor:pointer;">确认提交</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
		          		<a class="btn  btn-primary" style="cursor:pointer;">备注</a>
					</td>	
					<td>
						<div style="float: left;">仓库&nbsp;&nbsp;</div>
						<div  style="float: left;">
								<select style="width:100px;" id="warehouseId">
									<option value="1">1-香港仓</option>
									<option value="2">2-美国仓</option>
									<option value="3">3-英国仓</option>
								</select>
						</div>
					</td>
					<td>
							货架&nbsp;&nbsp;<input type="text"  name="itemQuantity"  id="itemQuantity" style="width:90px;"/>
							<input type="checkbox" name="1" checked="checked" />自动
					</td>
					<td>
							货位&nbsp;&nbsp;<input type="text"  name="itemQuantity"  id="itemQuantity" style="width:90px;"/>
							 <input type="checkbox" name="2" checked="checked" />自动
					</td>			
			</tr>	
		</table>
	</div>
	<div id="maingrid" class="pull-left" style="width:100%;">
				
	</div> 
	 <script type="text/javascript" src="${baseUrl}/static/jquery/jquery.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.showMessage.js"></script>
	<script  type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/core/base.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligeruiPatch.js"></script>
    <script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligerTab.js"></script>
    <script  type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligerTree.js" ></script>
    
   <script src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
    <script src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
    <script type="text/javascript">
	   var baseUrl = "${baseUrl}";
  	  $(function(){
	    	$(".tips").each(function(i,e){
	                var $img = $(e);
	                var msg = $img.attr("msg");
	                var id =  $img.attr("id");
	                $.showTips(id,msg);
	        });
	       	//输入物品信息
	     	$("#enterItem").click(function(){
	     			
	     	});
	    	initGrid();
   		});
  	  
  	  //点击提交跟踪号
  	  function clickEnter(){
    		var trackingNo = $("#trackingNo");
      		var trackingNoStr = trackingNo.val();
      		if($.trim(trackingNoStr) ==""){
      			parent.$.showDialogMessage("请输入跟踪单号.",null,null);
      			return;
      		}
    		if(trackingNoStr.indexOf(" ")> -1 && $.trim(trackingNoStr) !=""){
    			parent.$.showDialogMessage("您输入的跟踪单号中包含空白字符已被忽略.",null,null);
    			trackingNo.val($.trim(trackingNoStr));
    		}    	
    		//检查跟踪号是否能找到唯一的入库订单
   		   $.getJSON(baseUrl+'/warehouse/storage/checkFindInWarehouseOrder.do?trackingNo='+trackingNoStr,function(msg) {
             	 if (msg.status != 1) {
             	 		parent.$.showDialogMessage(msg.message,null,null);           
             	 		//找不到唯一的订单,解开客户帐号
             	 		$('#userLoginName').removeAttr("readonly");
             	 		//若不输入客户帐号,标记为无主件
             	 		$('#unKnowCustomer').removeAttr("readonly");
                   }
			});
    		   
    		
    		
    		
    		//刷新Grid				
    		btnSearch("#searchform",grid);
  	  }
    </script>	
    
    
	<script type="text/javascript">
	   	 var grid = null;
	     function initGrid() {
	    	   grid = $("#maingrid").ligerGrid({
	                columns: [
	                    { display: '产品SKU', name: 'sku', align: 'right',type:'float',width:'13%'},
	  		            { display: '预报产品数量', name: 'quantity', align: 'right', type: 'float',width:'9%'},
	  		          	{ display: '实际收货数量', name: 'quantity', align: 'right', type: 'float',width:'9%'},
	  		          	{ display: '产品描述', name: 'productDescription', align: 'right', type: 'float',width:'10%'},
		                { display: '仓库', name: 'warehouse', align: 'right', type: 'float',width:'9%'},
		                { display: '货架', name: 'shelves', align: 'right', type: 'float',width:'9%'},
		                { display: '货位', name: 'seat', align: 'right', type: 'float',width:'10%'},
		                { display: '批次号', name: 'batchNo', type: 'int', width:'12%'},
		                {display: '操作',isSort: false,width: '9%',render: function(row) {
		            		var h = "";
		            		if (!row._editing) {
		            			h += '<a href="javascript:updateInWarehouseItem(' + row.id + ')">编辑</a> ';
		            			h += '<a href="javascript:deleteInWarehouseItem(' + row.id + ')">删除</a>';
		            		}
		            		return h;
		            	}
		            }
	                ],  
	                isScroll: true,
	                dataAction: 'server',
	                url: baseUrl+'/warehouse/storage/getInWarehouseOrder.do',
	                pageSize: 20, 
	                usePager: 'true',
	                sortName: 'ProductID',
	                width: '100%',
	                height: '99%',
	                checkbox: true,
	                rownumbers:true,
	                enabledEdit: true,
	                clickToEdit: true
	            });
	        };	
	</script>
</body>
</html>