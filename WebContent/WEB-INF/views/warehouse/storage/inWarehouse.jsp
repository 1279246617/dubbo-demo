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
			<table class="table table-striped" style="width:1050px;">
					<tr>
							<td>
									跟踪单号&nbsp;&nbsp;<input type="text"  name="trackingNo"  id="trackingNo" style="width:180px;"/>
							</td>		
							<td>
								客户帐号&nbsp;&nbsp;<input type="text" name="userLoginName" data-provide="typeahead"  id="userLoginName" style="width:120px;"  readonly="readonly" />
								<select style="width:160px;display:none;" id="userIdSelect"></select>
								<input name="userId" id="userId" style="display:none;">
		          				<img class="tips" id="customerNoTips" msg="根据运单号找不到唯一的入库订单时,将要求输入客户帐号" src="${baseUrl}/static/img/help.gif">
		          				&nbsp;&nbsp;
		          				 <input type="checkbox" name="unKnowCustomer" id="unKnowCustomer" readonly="readonly"/>&nbsp;标记为无主件
		          				&nbsp;&nbsp;&nbsp;&nbsp;
		          				 <a class="btn  btn-primary" id="enter" onclick="clickEnter()" style="cursor:pointer;">保存入库主单</a>
		          				 &nbsp;&nbsp;&nbsp;&nbsp;
		          				 入库主单摘要&nbsp;&nbsp;<input type="text"  name="orderRemark"  id="orderRemark" style="width:220px;"/>
							</td>
					</tr>
			</table>
		</form>
		
		<table class="table table-striped" style="width:1150px;">
			<tr>
					<td colspan="2">产品SKU&nbsp;&nbsp;<input type="text"  name="itemSku"  id="itemSku" style="width:130px;"/></td>		
					<td  >
						产品数量&nbsp;&nbsp;<input type="text"  name="itemQuantity"  id="itemQuantity" style="width:90px;"/>
						&nbsp;&nbsp; 
						<a class="btn  btn-primary" id="enterItem" style="cursor:pointer;">保存入库明细</a>
					</td>	
			</tr>	
			<tr>
				<td>
						 仓库&nbsp;&nbsp;
						<select style="width:100px;" id="warehouseId">
							<option value="1">1-香港仓</option>
							<option value="2">2-美国仓</option>
							<option value="3">3-英国仓</option>
						</select>
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
	     
    <script type="text/javascript" src="${baseUrl}/static/bootstrap/bootstrap-typeahead.js"></script>
    
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.showMessage.js"></script>
	<script  type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/core/base.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligeruiPatch.js"></script>
    <script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligerTab.js"></script>
    <script  type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligerTree.js" ></script>

    
    <script type="text/javascript">
	   var baseUrl = "${baseUrl}";
		//回车事件
	    $(window).keydown(function(event){
	    	if((event.keyCode   ==   13)) {
	    		clickEnter();
	    		return;
	    	}  
	    	if((event.keyCode   ==   13) &&   (event.ctrlKey)) {
	    		//提交一次收货
	    		
	    	}
	    });
		
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
	    	//客户帐号自动完成
	        $("#userLoginName").typeahead({
	            source: function (query, process) {
	            	var userResult = "";
	                $.ajax({
	          	      type : "post",
	          	      url : baseUrl+'/user/searchUser.do?keyword='+query,
	          	      async : false,
	          	      success : function(data){
	          	       	 data = eval("(" + data + ")");
	          	      	userResult = data;
	          	      }
	          	   });
	                return userResult;
	            },
	            highlighter: function(item) {
	            	var arr = item.split("-");
	  	          	var userId = arr[0];
	  	          	var loginName = arr[1];
	  	          	var keyword = arr[2];
	  	          	var newKeyword = ("<font color='red'>"+keyword+"</font>");
	  	          	var newItem = userId.replace(keyword,newKeyword)+"-"+loginName.replace(keyword,newKeyword);
	  	          	return newItem;
	            },
	            updater: function(item) {
            	 var itemArr = item.split("-");
  	              $("#userId").val(itemArr[0]);
  	              return itemArr[1];
	     	 }
	      });
	    	
	    	
	     initGrid();
   		});
  	  
  	  //点击提交跟踪号
  	  //1. 跟踪号为空,要求输入跟踪号
  	  //2.跟踪号不为空,客户帐号为空, 请求后台获取运单预报用户数据, 提示输入客户帐号,标记无主件
  	  //3.跟踪号不为空,客户帐号为空,但已经标记无主件
  	  function clickEnter(){
    		var trackingNo = $("#trackingNo");
      		var trackingNoStr = trackingNo.val();
      		var userLoginName = $('#userLoginName');
      		if($.trim(trackingNoStr) ==""){
      			parent.$.showDialogMessage("请输入跟踪单号.",null,null);
      			return;
      		}
    		if(trackingNoStr.indexOf(" ")> -1 && $.trim(trackingNoStr) !=""){
    			parent.$.showDialogMessage("您输入的跟踪单号中包含空白字符已被忽略.",null,null);
    			trackingNo.val($.trim(trackingNoStr));
    		}    	
    		
    		if(userLoginName.val() == ''){
    			
    		}
    		//检查跟踪号是否能找到唯一的入库订单
   		   $.getJSON(baseUrl+'/warehouse/storage/checkFindInWarehouseOrder.do?trackingNo='+trackingNoStr,function(msg) {
	   			if (msg.status == -1) {
	   				 //找不到订单,请输入客户帐号
	   				parent.$.showDialogMessage(msg.message,null,null);
	   				userLoginName.removeAttr("readonly");
	   				//标记为无主件,操作员可以手工取消标记无主件
	   				$("#unKnowCustomer").attr("checked","checked");
	     	 		$('#unKnowCustomer').removeAttr("readonly");
	   			 }
	   			if (msg.status == 2) {
	   				 //找到多条订单,请选择客户帐号
	   				parent.$.showDialogMessage(msg.message,null,null);
	   				userLoginName.hide();
	   				var userIdSelect = $("#userIdSelect");
	   				userIdSelect.show();
	   				userIdSelect.empty();  
	   				$.each(msg.userList, function(i, n){
	   					userIdSelect.append("<option value='"+this.id+"'>"+this.loginName+"</option>");  
	   				});
	   			 }
	   			
	   			if (msg.status == 1) {
	   				$('#userLoginName').val(msg.user.loginName);
	   				$('#userId').val(msg.user.id);
	   				//光标移至产品SKU
					$("#itemSku").focus();	   				
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
	                sortName: 'sku',
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