//点击保存主单
function saveInWarehouseRecord(){
	var trackingNo = $("#trackingNo");
	var trackingNoStr = trackingNo.val();
	var remark = $("#orderRemark").val();
	var warehouseId = $("#warehouseId").val(); 
	if($.trim(trackingNoStr) ==""){
		parent.$.showDialogMessage("请输入跟踪单号.",null,null);
		//焦点回到跟踪单号
		return;
	}
	if(trackingNoStr.indexOf(" ")> -1 && $.trim(trackingNoStr) !=""){
		parent.$.showShortMessage({msg:"您输入的跟踪单号中包含空白字符已被忽略.",animate:true,left:"40%"});
		trackingNo.val($.trim(trackingNoStr));
	}
	//在判断跟踪号是否改变前,获取用户选择的入库订单
	var inWarehouseOrderRadio = $('input[name="inWarehouseOrderRadio"]').filter(':checked');
	if(inWarehouseOrderRadio.length){
		$("#inWarehouseOrderId").val(inWarehouseOrderRadio.attr("orderId"));
	}
	//跟踪号失去焦点,判断跟踪号是否改变,若改变则清除inWarehouseOrderId
	trackingNoBlur();
	//跟踪号不为空,入库订单id为空调用clickEnterStep1();
	if($("#inWarehouseOrderId").val() == null || $("#inWarehouseOrderId").val() == ""){
		saveInWarehouseRecordStep1(trackingNoStr,remark,warehouseId);	
	}else{
		//入库订单id 不为空,可能是查到多个入库订单,选择后,开始保存主单
		saveInWarehouseRecordStep2(trackingNoStr,remark,warehouseId);
	}
}

//保存主单1(无输入客户单号)
function saveInWarehouseRecordStep1(trackingNoStr,remark,warehouseId) {
	$("#inWarehouseOrdertbody").html("");
	// 检查跟踪号是否能找到唯一的入库订单
	$.getJSON(baseUrl+ '/warehouse/storage/checkFindInWarehouseOrder.do?trackingNo='+ trackingNoStr, function(msg) {
		if (msg.status == -1) {
			// 找不到订单, 请输入客户帐号
			parent.$.showDialogMessage(msg.message, null, null);
			return false;
		}
		var table = $("#inWarehouseOrdertable");
		table.show();
		$.each(msg.mapList, function(i, n) {
			var tr = "<tr>";
			if (msg.status == 1) {
				$("#inWarehouseOrderId").val(n.inWarehouseOrderId);
				tr+="<td style='width:25px;text-align:center;'><input type='radio' t='1' orderId='"+n.inWarehouseOrderId+"' name='inWarehouseOrderRadio' value='radiobutton' checked></td>";	
			}else{
				tr+="<td style='width:25px;text-align:center;'><input type='radio' t='1' orderId='"+n.inWarehouseOrderId+"' name='inWarehouseOrderRadio' value='radiobutton'></td>";
			}
			tr+="<td style='width:155px;text-align:center;'>"+n.userLoginName+"</td>";
			tr+="<td style='width:225px;text-align:center;'>"+n.trackingNo+"</td>";
			tr+="<td style='width:205px;text-align:center;'>"+n.carrierCode+"</td>";
			tr+="<td style='width:205px;text-align:center;'>"+n.customerReferenceNo+"</td>";
			tr+="<td style='width:205px;text-align:center;'>"+n.createdTime+"</td>";
			tr+="</tr>";
			$("#inWarehouseOrdertbody").append(tr);
		});
		if (msg.status == 2) {
			// 找到多条订
			$("#tips").html("请选择其中一个入库订单并按回车!");
			parent.$.showDialogMessage(msg.message, null, null);
			return false;
		}
		if (msg.status == 1) {
			//步骤1能得到唯一订单,直接调用步骤2
			saveInWarehouseRecordStep2(trackingNoStr,remark,warehouseId);
		}
	});
}


//保存主单2(已输入客户单号)
function saveInWarehouseRecordStep2(trackingNoStr,remark,warehouseId) {
	var inWarehouseOrderId = $("#inWarehouseOrderId").val();
	$.post(baseUrl+ '/warehouse/storage/saveInWarehouseRecord.do?trackingNo='+ trackingNoStr
		+'&warehouseId='+warehouseId+'&inWarehouseOrderId='+inWarehouseOrderId+'&remark='+remark, function(msg) {
		//赋值入库记录id 到隐藏input
		$("#inWarehouseRecordId").val(msg.id);
		if(msg.status == 0){
			parent.$.showShortMessage({msg:msg.message,animate:true,left:"45%"});
			return;
		}
		if(msg.status == 1){
			parent.$.showShortMessage({msg:"保存主单成功.",animate:true,left:"45%"});
			$("#tips").html("请输入SKU和数量并按回车!");
			// 光标移至产品SKU
			$("#itemSku").focus();
			focus = "2";
			btnSearch("#searchform",grid);
			return;
		}
	},"json");
}

//回车事件3 , 保存明细
function saveInWarehouseRecordItem() {
	//物品明细
	var itemSku = $("#itemSku").val();
	var itemQuantity = $("#itemQuantity").val();
	var itemRemark = $("#itemRemark").val();
	var warehouseId = $("#warehouseId").val();
	var shelvesNo = $("#shelvesNo").val();
	var seatNo = $("#seatNo").val();
	//入库主单id
	var inWarehouseRecordId = $("#inWarehouseRecordId").val();
	if(inWarehouseRecordId==''){
		parent.$.showShortMessage({msg:"无主单id,不能保存明细",animate:true,left:"45%"});
		return;
	}
	if(itemSku == ''){
		parent.$.showShortMessage({msg:"请输入产品SKU",animate:true,left:"45%"});
		$("#itemSku").focus();
		return;
	}
	if(itemQuantity==''){
		$("#itemQuantity").focus();
		return;
	}
	$.post(baseUrl+ '/warehouse/storage/saveInWarehouseRecordItem.do?itemSku='
			+ itemSku+'&itemQuantity='+itemQuantity+'&itemRemark='+itemRemark+"&warehouseId="
			+warehouseId+"&shelvesNo="+shelvesNo+"&seatNo="+seatNo+"&inWarehouseRecordId="+inWarehouseRecordId, function(msg) {
		if(msg.status == 0){
			//保存失败,显示提示
			parent.$.showShortMessage({msg:msg.message,animate:true,left:"45%"});
			// 光标移至产品SKU
			$("#itemSku").focus();
			focus = "2";
			return;
		}
		if(msg.status == 1){
			parent.$.showShortMessage({msg:"保存明细成功.",animate:true,left:"45%"});
			// 光标移至产品SKU
			$("#itemSku").focus();
			$("#itemSku").select();
			$("#tips").html("请继续输入SKU和数量,或者输入新的跟踪单号并按回车!");
			focus = "2";
			
			btnSearch("#searchform",grid);
			return;
		}
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
		$("#inWarehouseOrderId").val("");
		$("#inWarehouseRecordId").val("");
	}
	oldTrackingNo = newTrackingNo;
}