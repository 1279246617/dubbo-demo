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
	<div class="pull-left" style="width:100%;height:140px; margin-top: 1px;" >
		 <form action="${baseUrl}/warehouse/storage/getInWarehouseOrder.do" id="searchform" name="searchform" method="post">
			<table class="table table-striped" style="width:1050px;margin-bottom: 5px">
					<tr style="height:15px;">
							<td>
									<span class="pull-left" style="width:52px;">跟踪单号</span>
									<span class="pull-left" style="width:191px;">
										<input type="text"  name="trackingNo"  id="trackingNo" style="width:190px;"/>
									</span>
							</td>		
							<td>
								客户帐号&nbsp;&nbsp;<input type="text" name="userLoginName" data-provide="typeahead"  id="userLoginName" style="width:120px;"  readonly="readonly" />
								<select style="width:160px;display:none;" id="userIdSelect"></select>
		          				<img class="tips" id="customerNoTips" msg="根据运单号找不到唯一的入库订单时,将要求输入客户帐号" src="${baseUrl}/static/img/help.gif">
		          				&nbsp;&nbsp;
		          				 <input type="checkbox" name="unKnowCustomer" id="unKnowCustomer" readonly="readonly"/>&nbsp;标记为无主件
		          				&nbsp;&nbsp;&nbsp;&nbsp;
		          				 <a class="btn  btn-primary" id="enter" onclick="clickEnter()" style="cursor:pointer;"><i class="icon-ok icon-white"></i>保存主单</a>
		          				 &nbsp;&nbsp;&nbsp;&nbsp;
		          				 主单摘要&nbsp;&nbsp;<input type="text"  name="orderRemark"  id="orderRemark" style="width:220px;"/>
							</td>
					</tr>
			</table>
		</form>
		<table class="table table-striped" style="width:1200px;margin-bottom: 0px">
			<tr>
					<td  >
						<span class="pull-left" style="width:52px;">产品SKU</span>
						<span class="pull-left" style="width:160px;">
							<input type="text"  name="itemSku"  id="itemSku" style="width:130px;"/>
						</span>
						
						<span class="pull-left" style="width:52px;">产品数量</span>
						<span class="pull-left" style="width:102px;">
							<input type="text"  name="itemQuantity"  id="itemQuantity" style="width:90px;"/>
						</span>
						
						<span class="pull-left" style="width:105px;">
							<a class="btn  btn-primary" id="enterItem" style="cursor:pointer;"><i class="icon-ok icon-white"></i>保存明细</a>
						</span>
						
						<span class="pull-left" style="width:52px;">明细备注</span>
						<span class="pull-left" style="width:180px;">
							<input type="text"  name="itemRemark"  id="itemRemark" style="width:160px;"/>
						</span>
						
		          		<span class="pull-left" style="width:30px;">仓库</span>
		          		<span class="pull-left" style="width:110px;">
		          			<select style="width:80px;" id="warehouseId">
								<option value="1">1-香港仓</option>
								<option value="2">2-美国仓</option>
								<option value="3">3-英国仓</option>
							</select>
		          		</span>
						
						<span class="pull-left" style="width:30px;">货架</span>
						<span class="pull-left" style="width:140px;">
							<input type="text"  name="itemQuantity"  id="itemQuantity" style="width:70px;"/>
							<input type="checkbox" name="1" checked="checked" />自动
						</span>
						
						<span class="pull-left" style="width:30px;">货位</span>
						<span class="pull-left" style="width:130px;">
							<input type="text"  name="itemQuantity"  id="itemQuantity" style="width:70px;"/>
						 	<input type="checkbox" name="2" checked="checked" />自动
						</span>
						
					</td>	
			</tr>	
		</table>
	</div>
	
	<table  class="table table-striped" style="margin-bottom: 0px">
		<tr>
			<td>
				<div class="pull-left">
					<a class="btn  btn-small btn-danger" onclick="" title="批量设置为已经收货"><i class="icon-th-large icon-white"></i>批量收货</a>
					&nbsp;
                   <a class="btn btn-small btn-primary" onclick=""><i class="icon-cog icon-white"></i>设置仓库</a>
                   &nbsp;
                   <a class="btn btn-small btn-primary" onclick=""><i class="icon-cog icon-white"></i>设置货架</a>
                   &nbsp;
                   <a class="btn btn-small btn-primary" onclick=""><i class="icon-cog icon-white"></i>设置货位</a>
              </div>
              <form action="#" id="searchform2" name="searchform2" method="post">
                  <div class="pull-right searchContent">
                          SKU&nbsp;<input type="text"  name="sku" title="可输入sku搜索">
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
	<script  type="text/javascript" src="${baseUrl}/static/js/warehouse/inwarehouse.js" ></script>
    
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
      		var userLoginNameStr = userLoginName.val();
      		
      		var unKnowCustomer = $("#unKnowCustomer");
      		var isUnKnowCustomer = unKnowCustomer.is(':checked'); //true | false
      		
      		var remark = $("#orderRemark").val();
      		
      		if($.trim(trackingNoStr) ==""){
      			parent.$.showDialogMessage("请输入跟踪单号.",null,null);
      			return;
      		}
    		if(trackingNoStr.indexOf(" ")> -1 && $.trim(trackingNoStr) !=""){
    			parent.$.showShortMessage({msg:"您输入的跟踪单号中包含空白字符已被忽略.",animate:true,left:"40%"});
    			trackingNo.val($.trim(trackingNoStr));
    		}    	
    		//跟踪号不为空,客户帐号为空调用clickEnterStep1();
    		if($.trim(userLoginNameStr) == ''){
    			clickEnterStep1(trackingNoStr,userLoginName);		    			
    		}
			
    		//跟踪号不为空,客户帐号不为空,将提交保存
    		if($.trim(userLoginNameStr) != ''){
    			clickEnterStep2(trackingNoStr,userLoginNameStr,isUnKnowCustomer,remark);
    		}
    		
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