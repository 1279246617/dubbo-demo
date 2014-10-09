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
		 <form action="${baseUrl}/warehouse/storage/getInWarehouseRecordItemData.do" id="searchform" name="searchform" method="post">
			<table class="table table-striped" style="width:1150px;margin-bottom: 5px">
					<tr style="height:15px;">
							<td>
									<span class="pull-left" style="width:52px;">跟踪单号</span>
									<span class="pull-left" style="width:191px;">
										<input type="text"  name="trackingNo"  id="trackingNo" t="1" onfocus="trackingNoFocus()"   style="width:190px;"/>
									</span>
							</td>		
							<td>
								客户帐号&nbsp;&nbsp;<input type="text" name="userLoginName"  t="1" data-provide="typeahead"  id="userLoginName" style="width:120px;"  />
								<select style="width:160px;display:none;" id="loginNameSelect" onchange="loginNameSelectChange()">
								</select>
		          				<img class="tips" id="customerNoTips" msg="根据运单号找不到唯一的入库订单时,将要求输入客户帐号" src="${baseUrl}/static/img/help.gif">
		          				&nbsp;&nbsp;
		          				 <input type="checkbox" name="unKnowCustomer" t="1" id="unKnowCustomer" />&nbsp;标记为无主件
		          				&nbsp;&nbsp;&nbsp;&nbsp;
		          				 主单备注&nbsp;&nbsp;<input type="text" t="1"  name="orderRemark"  id="orderRemark" style="width:220px;"/>
		          				 <!-- 入库主单记录id -->
		          				 <input type="text" name="inWarehouseRecordId" t="1" id="inWarehouseRecordId" style="display:none;">
		          				 
		          				 <a class="btn  btn-primary" id="enter" onclick="saveInWarehouseRecord()" style="cursor:pointer;"><i class="icon-ok icon-white"></i>保存主单</a>
		          				 &nbsp;&nbsp;
		          				 <button class="btn  btn-primary"   style="cursor:pointer;" type="reset"><i class="icon-ok icon-white"></i>清除</button>
		          				 &nbsp;&nbsp;&nbsp;&nbsp;
							</td>
					</tr>
			</table>
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
						
						
		          		<span class="pull-left" style="width:30px;">仓库</span>
		          		<span class="pull-left" style="width:110px;">
		          			<select style="width:80px;" id="warehouseId">
								<option value="1">1-香港仓</option>
							</select>
		          		</span>
						
						<span class="pull-left" style="width:30px;">货架</span>
						<span class="pull-left" style="width:140px;">
							<input type="text"  name="shelvesNo"  id="shelvesNo" t="2" style="width:70px;"/>
							<input type="checkbox" name="1" t="2" checked="checked" />自动
						</span>
						
						<span class="pull-left" style="width:30px;">货位</span>
						<span class="pull-left" style="width:130px;">
							<input type="text"  name="seatNo"  t="2" id="seatNo" style="width:70px;"/>
						 	<input type="checkbox" name="2" checked="checked"  t="2"/>自动
						</span>
						
					</td>	
			</tr>	
		</table>
	</div>
	
	<table  class="table table-striped" style="margin-bottom: 0px">
		<tr>
			<td>
				<div class="pull-left">
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
	   //进入页面,焦点跟踪单号
	   $("#trackingNo").focus();
	   var focus = "1";
	    $(window).keydown(function(event){
	    	//回车事件
	    	if((event.keyCode   ==   13)) {
	    		clickEnter();
	    		return;
	    	}  
	    	//空格
			if(event.keyCode == 32 && !$(event.target).is('input,textarea')){
				return;
			}    	
	    	if((event.keyCode   ==   13) &&   (event.ctrlKey)) {
	    	}
	    });
	
  	  $(function(){
	  		$("input").focus(function(){
	  			//当前获取焦点的文本框是 主单还是明细
	  			focus = $(this).attr("t");
	  		});
	  		
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
  	  	
  	  //回车事件
  	  function clickEnter(){
  		  	if(focus == '1'){
	  		  	saveInWarehouseRecord();	
  		  	}
    		//保存明细
			if(focus == '2'){
	      		saveInWarehouseRecordItem();
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
	                    { display: '产品SKU', name: 'sku', align: 'center',width:'13%'},
	  		          	{ display: '收货数量', name: 'quantity', align: 'center', type: 'int',width:'9%'},
		                { display: '仓库', name: 'warehouse', align: 'center', type: 'float',width:'9%'},
		                { display: '货架', name: 'shelvesNo', align: 'center', type: 'float',width:'9%'},
		                { display: '货位', name: 'seatNo', align: 'center', type: 'float',width:'10%'},
		                { display: '收货时间', name: 'createdTime', type: 'int', width:'12%'},
		                { display: '备注', name: 'remark', align: 'center', type: 'float',width:'13%'},
		                { display: '操作员', name: 'userLoginNameOfOperator',width:'10%'},
		                {display: '操作',isSort: false,width: '11%',render: function(row) {
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
	                url: baseUrl+'/warehouse/storage/getInWarehouseRecordItemData.do',
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