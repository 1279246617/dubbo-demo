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
									<span class="pull-left" style="width:172px;">客户单号(捡货单上的订单号)</span>
									<span class="pull-left" style="width:191px;">
										<input type="text"  name="trackingNo"  id="trackingNo" t="1" onfocus="trackingNoFocus()"   style="width:190px;"/>
									</span>
							</td>		
							<td>
								出库总重&nbsp;&nbsp;<input type="text" name="packageWeight"  t="1" data-provide="typeahead"  id="packageWeight" style="width:120px;"  />
								KG
												          	
		          				&nbsp;&nbsp;&nbsp;&nbsp;
		          				 出库备注&nbsp;&nbsp;<input type="text" t="1"  name="orderRemark"  id="orderRemark" style="width:220px;"/>
		          				 <!-- 入库主单记录id -->
		          				 <input type="text" name="inWarehouseRecordId" t="1" id="inWarehouseRecordId" style="display:none;">
		          				 
		          				 <a class="btn  btn-primary" id="enter" onclick="saveInWarehouseRecord()" style="cursor:pointer;"><i class="icon-ok icon-white"></i>保存称重</a>
		          				 &nbsp;&nbsp;
		          				 <button class="btn  btn-primary"   style="cursor:pointer;" type="reset"><i class="icon-ok icon-white"></i>清除</button>
		          				 &nbsp;&nbsp;&nbsp;&nbsp;
							</td>
					</tr>
					
					<tr style="height:15px;">
						<td>打印发货渠道标签</td>
					</tr>
			</table>
		</form>
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
 
  	 	 //回车事件
  	  	function clickEnter(){
  	 
  		}
    </script>	
</body>
</html>