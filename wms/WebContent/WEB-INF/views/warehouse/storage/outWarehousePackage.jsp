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
	<div class="pull-left" style="width:50%;height:100%; margin-top: 1px;" >
			<table class="table table-striped" style="width:100%;height:150px;">
					<tr>
						<td colspan="2" style="height:28px;">
								<span style="width:100px;" class="pull-left">COE交接单号</span>
								<input id="coeTrackingNo" name="coeTrackingNo" value="${coeTrackingNo}"  t="1"  style="width:150px;" readonly="readonly"/>
								<input id="coeTrackingNoId" name="coeTrackingNoId" value="${coeTrackingNoId}"  t="1" style="display: none;"/>
								<a class="btn  btn-primary" id="enter" onclick="enClick()" style="cursor:pointer;height:20px;"><i class="icon-ok icon-white"></i>手动输入交接单号</a>
								
						</td>
					</tr>
					<tr style="height:65px;">
							<td>
								<span style="width:100px;" class="pull-left" >出货跟踪单号</span>
								<input type="text"  name="trackingNo"  id="trackingNo"    t="2"  style="width:150px;" title="扫描出库装箱时打印的运单上的条码"/>
								<button onclick="batchTrackingNo()">批量</button>
								 <input   style="margin-left: 30px;" id="add"  name="addOrSub"  t="2" type="radio" checked>绑定
								 <input   style="margin-left: 30px;" id="sub" name="addOrSub"  t="2" type="radio">解绑
							</td>		
							
							<td>
<!-- 		          				 <a class="btn  btn-primary" id="enter" onclick="next()" style="cursor:pointer;"><i class="icon-ok icon-white"></i>继续下一票</a> -->
							</td>
					</tr>
					<tr>
						<td colspan="2" rowspan="2" style="height:25px;">
							<a class="btn  btn-primary" id="submitAll" onclick="submitAll()" style="cursor:pointer;height:20px;"><i class="icon-ok icon-white"></i>
								完成建包
							</a>
						</td>
					</tr>
			</table>
		<div class="pull-left" style="width:100%;margin-top: 3px;height:60px;;overflow: hidden;" >
			<table class="table table-striped" style="width:100%;">
					<tr><th colspan="1" style="height:28px;text-align: left">操作(绑定和解除绑定)失败的出货跟踪单号记录, 数量:<span id="totalFail" style="margin-left: 5px;">0</span></th></tr>
			</table>
		</div>
		
		<div class="pull-left" style="width:100%;margin-top: 1px;overflow: auto" id="resultDiv" >
			<table class="table table-striped" style="width:100%;text-align: left">
					<tbody id="results">
						
					</tbody>
			</table>
		</div>
			
	</div>
	
	<div class="pull-right" style="width:50%;margin-top: 1px;height:60px;;overflow: hidden;" >
		<table class="table table-striped" style="width:100%;">
				<tr><th colspan="1" style="height:28px;text-align: center">绑定成功的出货跟踪单号记录, 数量:<span id="total" style="margin-left: 5px;">0</span></th></tr>
		</table>
	</div>
	<div class="pull-right" style="width:50%;margin-top: 1px;overflow: auto" id="trackingNosDiv" >
			<table class="table table-striped" style="width:100%;">
					<tbody id="trackingNos">
						
					</tbody>
			</table>
	</div>
	 
	  
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/js/warehouse/outWarehousePackage.js"></script>
   	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.showMessage.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/core/base.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligeruiPatch.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/calendar/lhgcalendar.min.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/calendar/prettify.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/lhgdialog/prettify/prettify.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/lhgdialog/prettify/lhgdialog.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/lhgdialog/prettify/prettify.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/lhgdialog/prettify/lhgdialog.js"></script>
	
    <script type="text/javascript">
	   var baseUrl = "${baseUrl}";
	   //进入页面,焦点跟踪单号
	   $("#trackingNo").focus();
	   var focus ="2";
		$(function(){
		  		$("input").focus(function(){
		  			//当前获取焦点的文本框是 主单还是明细
		  			focus = $(this).attr("t");
		  		});
		  		var wh = $(window).height();
		  		$("#trackingNosDiv").css("height", wh - 80);
		  		$("#resultDiv").css("height", wh - 240);
	   	});
	    $(window).keydown(function(event){
	    	if((event.keyCode   ==   13) &&   (event.ctrlKey)) {
	    		submitAll();
	    		return;
	    	}
	    	//回车事件
	    	if((event.keyCode   ==   13)) {
	    		if(focus =='1'){
	    			enterCoeTrackingNo();
	    		}
				if(focus =='2'){
					next();	
	    		}
	    		return;
	    	}  
	    });
    </script>	
</body>
</html>