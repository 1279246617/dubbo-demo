//点击保存主单
function saveReceivedLittlePackage(){
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
		parent.$.showShortMessage({msg:"您输入的跟踪单号中包含空白字符已被忽略.",animate:false,left:"40%"});
		trackingNo.val($.trim(trackingNoStr));
	}
	//在判断跟踪号是否改变前,获取用户选择的入库订单
	var littlePackageRadio = $('input[name="littlePackageRadio"]').filter(':checked');
	if(littlePackageRadio.length){
		$("#littlePackageId").val(littlePackageRadio.attr("littlePackageId"));
		$("#bigPackageId").val(littlePackageRadio.attr("bigPackageId"));
		$("#seatCode").val(littlePackageRadio.attr("seatCode"));
		$("#shipwayCode").val(littlePackageRadio.attr("shipwayCode"));
		$("#outWarehouseTrackingNo").val(littlePackageRadio.attr("outWarehouseTrackingNo"));
	}
	//跟踪号失去焦点,判断跟踪号是否改变,若改变则清除littlePackageId
	trackingNoBlur();
	//跟踪号不为空,入库订单id为空调用clickEnterStep1();
	if($("#littlePackageId").val() == null || $("#littlePackageId").val() == ""){
		saveReceivedLittlePackageStep1(trackingNoStr,remark,warehouseId);	
	}else{
		//入库订单id 不为空,可能是查到多个入库订单,选择后,开始保存主单
		saveReceivedLittlePackageStep2(trackingNoStr,remark,warehouseId);
	}
}

//保存主单1(无输入客户单号)
function saveReceivedLittlePackageStep1(trackingNoStr,remark,warehouseId) {
	$("#littlePackagebody").html("");
	// 检查跟踪号是否能找到唯一的入库订单
	$.getJSON(baseUrl+ '/warehouse/transport/checkReceivedLittlePackage.do?trackingNo='+ trackingNoStr, function(msg) {
		if (msg.status == -1) {
			// 找不到订单, 请输入客户帐号
			parent.$.showDialogMessage(msg.message, null, null);
			return false;
		}
		var table = $("#littlePackagetable");
		table.show();
		$.each(msg.mapList, function(i, n) {
			var tr = "<tr>";
			if (msg.status == 1) {
				$("#littlePackageId").val(n.littlePackageId);
				$("#bigPackageId").val(n.bigPackageId);
				$("#seatCode").val(n.seatCode);
				$("#shipwayCode").val(n.shipwayCode);
				$("#outWarehouseTrackingNo").val(n.outWarehouseTrackingNo);
				
				tr+="<td style='width:25px;text-align:center;'><input type='radio' t='1' littlePackageId='"+n.littlePackageId+"'  bigPackageId='"+n.bigPackageId+"'  seatCode='"+n.seatCode+"' shipwayCode='"+n.shipwayCode+"' outWarehouseTrackingNo='"+n.outWarehouseTrackingNo+"' name='littlePackageRadio' value='radiobutton' checked></td>";	
			}else{
				tr+="<td style='width:25px;text-align:center;'><input type='radio' t='1' littlePackageId='"+n.littlePackageId+"'  bigPackageId='"+n.bigPackageId+"'  seatCode='"+n.seatCode+"' shipwayCode='"+n.shipwayCode+"' outWarehouseTrackingNo='"+n.outWarehouseTrackingNo+"' name='littlePackageRadio' value='radiobutton'></td>";
			}
			tr+="<td style='width:155px;text-align:center;'>"+n.userLoginName+"</td>";
			tr+="<td style='width:225px;text-align:center;'>"+n.trackingNo+"</td>";
			tr+="<td style='width:205px;text-align:center;'>"+n.carrierCode+"</td>";
			tr+="<td style='width:205px;text-align:center;'>"+n.status+"</td>";
			tr+="<td style='width:205px;text-align:center;'>"+n.createdTime+"</td>";
			tr+="</tr>";
			$("#littlePackagebody").append(tr);
		});
		if (msg.status == 2) {
			// 找到多条订
			$("#tips").html("请选择其中一个转运订单并按回车!");
			parent.$.showDialogMessage(msg.message, null, null);
			return false;
		}
		if (msg.status == 1) {
			//步骤1能得到唯一订单,直接调用步骤2
			saveReceivedLittlePackageStep2(trackingNoStr,remark,warehouseId);
		}
	});
}


//保存主单2(已输入客户单号)
function saveReceivedLittlePackageStep2(trackingNoStr,remark,warehouseId) {
	lockTrackingNo();
	var littlePackageId = $("#littlePackageId").val();//保存转运订单入库
	$.post(baseUrl+ '/warehouse/transport/submitInWarehouse.do?trackingNo='+ trackingNoStr
		+'&warehouseId='+warehouseId+'&littlePackageId='+littlePackageId+'&remark='+remark, function(msg) {
		if(msg.status == 0){
			parent.$.showDialogMessage(msg.message, null, null);
			unLockTrackingNo();
			return;
		}
		$("#tips").html(msg.message);
		
		$("#seatCode").val(msg.seatCode);
		$("#bigPackageId").val(msg.bigPackageId);
		$("#littlePackageId").val(msg.littlePackageId);
		if(msg.status == 1){//集货转运
			// 下一票
			nextInWarehouse(msg.message);
			focus = "1";
		}
		//---取消去直接转运称重打单代码 ---开始
		if(msg.status == 2){//直接转运
			//下一票
			nextInWarehouse(msg.message);
			focus = "1";
			btnSearch("#searchform",grid);
			return;
		}
		//---取消去直接转运称重打单代码 --- 结束
		
		if(msg.status == 2){//直接转运
			parent.$.showShortMessage({msg:msg.message,animate:false,left:"45%"});
			// 光标移至称重
//			$("#weight").removeAttr("readonly");
			$("#weight").focus();
			//显示出货渠道和单号
			$("#outWarehouseTrackingNo").val(msg.trackingNo);
			$("#shipwayCode").val(msg.shipwayCode);
			focus = "3";
		}
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
		$("#littlePackageId").val("");
		$("#bigPackageId").val("");
		$("#littlePackagebody").html("");
	}
	oldTrackingNo = newTrackingNo;
}

function lockTrackingNo(){
	$("#trackingNo").attr("readonly","readonly");
	$("#warehouseId").attr("readonly","readonly");
	$("#orderRemark").attr("readonly","readonly");
	 $("input[name='littlePackageRadio']").each(function(){
		  $(this).attr("readonly","readonly");
	 });
}

function unLockTrackingNo(){
	$("#trackingNo").removeAttr("readonly");
	$("#warehouseId").removeAttr("readonly");
	$("#orderRemark").removeAttr("readonly");
	$("input[name='littlePackageRadio']").each(function(){
		  $(this).removeAttr("readonly");
	 });
}

function nextInWarehouse(message){
	unLockTrackingNo();
	$("#trackingNo").val("");
	$("#orderRemark").val("");
	$("#littlePackageId").val("");
	$("#bigPackageId").val("");
	$("#littlePackagebody").html("");
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


function saveweight(){
		var weight = $("#weight").val();
		var bigPackageId = $("#bigPackageId").val();
		if(bigPackageId ==null || bigPackageId == ''){
			parent.$.showShortMessage({msg:"没有找到直接转运订单,刷新后重试",animate:false,left:"45%"});
			return false;
		}
		if(weight ==null || weight == ''){
			parent.$.showShortMessage({msg:"请先输入装箱重量",animate:false,left:"45%"});
			return false;
		}
		$.post(baseUrl+ '/warehouse/transport/bigPackageSubmitWeight.do?bigPackageId='+ bigPackageId+'&weight='+weight, function(msg) {
				if(msg.status == 0){
					parent.$.showShortMessage({msg:msg.message,animate:false,left:"45%"});
					$("#weightOk").hide();
					return;
				}
				if(msg.status == 1){
					parent.$.showShortMessage({msg:"保存转运订单装箱重量成功",animate:false,left:"45%"});
					$("#weightOk").show();
					focus = '4';
					$("#outWarehouseTrackingNo").focus();
					$("#outWarehouseTrackingNo").select();
				}
		},"json");
}

 //打印出货运单标签
 function printShipLabel(){
		var bigPackageId = $("#bigPackageId").val();
		if(bigPackageId ==null || bigPackageId == ''){
			parent.$.showShortMessage({msg:"没有找到直接转运订单,刷新后重试",animate:false,left:"45%"});
			return false;
		}
		var url = baseUrl+'/warehouse/print/printTransportShipLabel.do?bigPackageIds='+bigPackageId;
		 window.open(url);
 }
 
 
//打印订单
 function printOrder(){
		var bigPackageId = $("#bigPackageId").val();
		if(bigPackageId ==null || bigPackageId == ''){
			parent.$.showShortMessage({msg:"没有找到直接转运订单,刷新后重试",animate:false,left:"45%"});
			return false;
		}
		var url = baseUrl+'/warehouse/print/printTransportPackageList.do?bigPackageIds='+bigPackageId;
		window.open(url);
 }
