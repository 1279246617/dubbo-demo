//点击保存主单
function saveReceivedFirstWaybill(){
	var trackingNo = $("#trackingNo");
	var trackingNoStr = trackingNo.val();
	var remark = $("#orderRemark").val();
	var warehouseId = $("#warehouseId").val(); 
	if($.trim(trackingNoStr) ==""){
		parent.$.showDialogMessage("请输入头程运单跟踪单号",null,null);
		//焦点回到跟踪单号
		return;
	}
	if(trackingNoStr.indexOf(" ")> -1 && $.trim(trackingNoStr) !=""){
		parent.$.showShortMessage({msg:"您输入的跟踪单号中包含空白字符已被忽略",animate:false,left:"40%"});
		trackingNo.val($.trim(trackingNoStr));
	}
	//在判断跟踪号是否改变前,获取用户选择的入库订单
	var firstWaybillRadio = $('input[name="firstWaybillRadio"]').filter(':checked');
	if(firstWaybillRadio.length){
		$("#firstWaybillId").val(firstWaybillRadio.attr("firstWaybillId"));
	}
	//跟踪号失去焦点,判断跟踪号是否改变,若改变则清除firstWaybillId
	trackingNoBlur();
	
	if($("#firstWaybillId").val() == null || $("#firstWaybillId").val() == ""){
		saveReceivedFirstWaybillStep1(trackingNoStr,remark,warehouseId);	
	}else{
		saveReceivedFirstWaybillStep2(trackingNoStr,remark,warehouseId);
	}
}

function saveReceivedFirstWaybillStep1(trackingNoStr,remark,warehouseId) {
	$("#firstWaybillbody").html("");
	// 检查跟踪号是否能找到唯一的入库订单
	$.getJSON(baseUrl+ '/warehouse/transport/checkReceivedFirstWaybill.do?trackingNo='+ trackingNoStr, function(msg) {
		if (msg.status == -1) {
			parent.$.showDialogMessage(msg.message, null, null);
			return false;
		}
		var table = $("#firstWaybilltable");
		table.show();
		$.each(msg.mapList, function(i, n) {
			var tr = "<tr>";
			if (msg.status == 1) {
				$("#firstWaybillId").val(n.firstWaybillId);
				tr+="<td style='width:25px;text-align:center;'><input type='radio' firstWaybillId='"+n.firstWaybillId+"'  name='firstWaybillRadio' value='radiobutton' checked></td>";	
			}else{
				tr+="<td style='width:25px;text-align:center;'><input type='radio' firstWaybillId='"+n.firstWaybillId+"'  name='firstWaybillRadio' value='radiobutton'></td>";
			}
			tr+="<td style='width:155px;text-align:center;'>"+n.userLoginName+"</td>";
			tr+="<td style='width:225px;text-align:center;'>"+n.trackingNo+"</td>";
			tr+="<td style='width:205px;text-align:center;'>"+n.carrierCode+"</td>";
			tr+="<td style='width:205px;text-align:center;'>"+n.status+"</td>";
			tr+="<td style='width:205px;text-align:center;'>"+n.createdTime+"</td>";
			tr+="</tr>";
			$("#firstWaybillbody").append(tr);
		});
		
		if (msg.status == 2) {
			$("#tips").html(msg.message);
			parent.$.showDialogMessage(msg.message, null, null);
			return false;
		}
		if (msg.status == 1) {
			//步骤1能得到唯一订单,直接调用步骤2
			saveReceivedFirstWaybillStep2(trackingNoStr,remark,warehouseId);
		}
	});
}

function saveReceivedFirstWaybillStep2(trackingNoStr,remark,warehouseId) {
	lockTrackingNo();
	var firstWaybillId = $("#firstWaybillId").val();//保存转运订单入库
	$.post(baseUrl+ '/warehouse/transport/submitInWarehouse.do?trackingNo='+ trackingNoStr
		+'&warehouseId='+warehouseId+'&firstWaybillId='+firstWaybillId+'&remark='+remark, function(msg) {
		if(msg.status == 0){//收货失败
			$("#tips").html(msg.message);
			parent.$.showDialogMessage(msg.message, null, null);
			unLockTrackingNo();
			$("#trackingNo").select();
			return;
		}
		if(msg.status == 2){//大包收货成功
			$("#trackingNo").val("");
			$("#orderRemark").val("");
			$("#firstWaybillId").val("");
			$("#firstWaybillbody").html("");
			unLockTrackingNo();
			$("#trackingNo").focus();
			parent.$.showDialogMessage(msg.message, null, null);
			return;
		}
		//订单收货成功
		$("#tips").html(msg.message);
		$("#seatCode").html(msg.seatCode);
		$("#firstWaybillId").val(msg.firstWaybillId);
		nextInWarehouse(msg.message);
		btnSearch("#searchform",grid);	
	},"json");
}

var oldTrackingNo = "";
function trackingNoFocus(){
	oldTrackingNo = $("#trackingNo").val();
	$("#trackingNo").select();
	$("#tips").html("请输入跟踪单号并按回车!");
}

function trackingNoBlur(){
	var newTrackingNo = $("#trackingNo").val();
	if(oldTrackingNo!=newTrackingNo){
		$("#firstWaybillId").val("");
		$("#firstWaybillbody").html("");
		$("#seatCode").html("");
	}
	oldTrackingNo = newTrackingNo;
}

function lockTrackingNo(){
	$("#trackingNo").attr("readonly","readonly");
	$("#warehouseId").attr("readonly","readonly");
	$("#orderRemark").attr("readonly","readonly");
	 $("input[name='firstWaybillRadio']").each(function(){
		  $(this).attr("readonly","readonly");
	 });
}

function unLockTrackingNo(){
	$("#trackingNo").removeAttr("readonly");
	$("#warehouseId").removeAttr("readonly");
	$("#orderRemark").removeAttr("readonly");
	$("input[name='firstWaybillRadio']").each(function(){
		  $(this).removeAttr("readonly");
	 });
}
 

function nextInWarehouse(message){
	unLockTrackingNo();
	$("#trackingNo").val("");
	$("#orderRemark").val("");
	$("#firstWaybillId").val("");
	$("#firstWaybillbody").html("");
	parent.$.showShortMessage({msg:message,animate:false,left:"45%"});
	$("#tips").html("请输入新的跟踪单号并按回车!");
	$("#trackingNo").focus();
}

//刷新小包收货记录列表
function refresh(){
	//如果操作员搜索指定单号, 不主动刷新列表
	if($("#searchFormTrackingNo").val()!=''){
		return;  				
	}
	btnSearch("#searchform",grid);
}
