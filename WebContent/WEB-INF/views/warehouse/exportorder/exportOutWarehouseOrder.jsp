<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   	<link href="${baseUrl}/static/bootstrap/bootstrap.min.css" rel="stylesheet"type="text/css" />
	<link href="${baseUrl}/static/bootstrap/common.css" rel="stylesheet" type="text/css"/>
	<link href="${baseUrl}/static/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${baseUrl}/static/lhgdialog/prettify/common.css" type="text/css" rel="stylesheet" />
	<link href="${baseUrl}/static/lhgdialog/prettify/prettify.css" type="text/css" rel="stylesheet" />
	<link href="${baseUrl}/static/calendar/prettify.css" rel="stylesheet" type="text/css" />
	<link href="${baseUrl}/static/calendar/lhgcalendar.css" rel="stylesheet" type="text/css" />
	<link href="${baseUrl}/static/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
</head>
<body>
			<table class="table" style="width:100%;height:100%;">
				<tr style="height: 50px;">
					<th colspan="2" style="color: red">
						注:单品订单指只有一个商品条码(不限商品数量)的出库订单
					</th>
				</tr>
				<tr>
					<th>
							仓库
					</th>
					<td>
		               	<select style="width:122px;" id="warehouseId" name="warehouseId">
               				<c:forEach items="${warehouseList}" var="w" >
				       	 		<option value="<c:out value='${w.id}'/>">
				       	 			<c:out value="${w.id}-${w.warehouseName}"/>
				       		 	</option>
				       		</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>
							状态
					</th>
					<td>
		               		<select style="width:122px;" id="status" name="status">
									<c:forEach items="${outWarehouseOrderStatusList}" var="status" >
						       	 		<option value="<c:out value='${status.code}'/>">
						       	 			<c:out value="${status.cn}"/>
						       		 	</option>
						       		 </c:forEach>
							</select>
					</td>
				</tr>
				<tr>
					<th>
							客户帐号
					</th>
					<td>
							<input type="text" name="userLoginName" data-provide="typeahead"  id="userLoginName" style="width:120px;" title="请输入客户登录名" />
					</td>
				</tr>
				<tr>
					<th>
							商品数量
					</th>
					<td>
							<input type="text" name="isQuantityOnly1"   id="isQuantityOnly1" value ='N' style="display: none;"/>
							<input type="checkbox"  style="margin-bottom: 0px;margin-top: 0px;"onclick="clickQuantityCheckBox()" id="quantityCheckBox" name="quantityCheckBox">
							&nbsp;&nbsp;只有一个商品数量
					</td>
				</tr>
				<tr>
					<th>
						创建时间
					</th>
					<td>
						<span class="pull-left" style="width:133px;">
               				<input type="text"   style="width:120px;" name="createdTimeStart" id="createdTimeStart"  title="起始创建时间">
               			</span>
	               		<span class="pull-left" style="width:180px;">
	               			至&nbsp;&nbsp;<input type="text"   style="width:120px;" name="createdTimeEnd"  id="createdTimeEnd"  title="终止创建时间">
	               		</span>
					</td>
				</tr>
				
			</table>
</body>
<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.js"></script>
<script type="text/javascript" src="${baseUrl}/static/bootstrap/bootstrap-typeahead.js"></script>
 <script type="text/javascript">
		 function clickQuantityCheckBox(){
			 var quantityCheckBox = $("#quantityCheckBox").attr("checked");
			 if(quantityCheckBox){
				 $("#isQuantityOnly1").val("Y");
			 }else{
				 $("#isQuantityOnly1").val("N");
			 }
		}
 		var baseUrl = "${baseUrl}";
   		$(function(){
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
	      $('#createdTimeStart,#createdTimeEnd').calendar({ format:'yyyy-MM-dd HH:mm:ss' });
   		});
   	</script>
   	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.showMessage.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/core/base.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligeruiPatch.js"></script>
	
	<script type="text/javascript" src="${baseUrl}/static/lhgdialog/prettify/prettify.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/lhgdialog/prettify/lhgdialog.js"></script>
	
	<script type="text/javascript" src="${baseUrl}/static/calendar/lhgcalendar.min.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/calendar/prettify.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/ligerui.all.js"></script>
</html>