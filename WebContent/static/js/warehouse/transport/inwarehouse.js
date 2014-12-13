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
		$("#littlePackageId").val(littlePackageRadio.attr("orderId"));
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
				tr+="<td style='width:25px;text-align:center;'><input type='radio' t='1' orderId='"+n.littlePackageId+"' name='littlePackageRadio' value='radiobutton' checked></td>";	
			}else{
				tr+="<td style='width:25px;text-align:center;'><input type='radio' t='1' orderId='"+n.littlePackageId+"' name='littlePackageRadio' value='radiobutton'></td>";
			}
			tr+="<td style='width:155px;text-align:center;'>"+n.userLoginName+"</td>";
			tr+="<td style='width:225px;text-align:center;'>"+n.trackingNo+"</td>";
			tr+="<td style='width:205px;text-align:center;'>"+n.carrierCode+"</td>";
			tr+="<td style='width:205px;text-align:center;'>"+n.status+"</td>";
			tr+="<td style='width:205px;text-align:center;'>"+n.createdTime+"</td>";
			tr+="</tr>";
			$("#littlePackagebody").append(tr);
			//最高200px
			if($("#littlePackageDiv").height()+25<=190){
				$("#littlePackageDiv").css('height', $("#littlePackageDiv").height()+25);	
			}
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
	var littlePackageId = $("#littlePackageId").val();//保存转运订单入库
	$.post(baseUrl+ '/warehouse/transport/submitInWarehouse.do?trackingNo='+ trackingNoStr
		+'&warehouseId='+warehouseId+'&littlePackageId='+littlePackageId+'&remark='+remark, function(msg) {
		if(msg.status == 0){
			parent.$.showShortMessage({msg:msg.message,animate:false,left:"45%"});
			return;
		}
		if(msg.status == 1){
			//锁定输入主单信息,直到点击提交才解锁.
			lockTrackingNo();
			
			$("#tips").html(msg.message);
			
			// 光标移至商品SKU
			$("#itemSku").focus();
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
		$("#littlePackageId").val("");
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

function nextInWarehouseRecord(){
	unLockTrackingNo();
	$("#trackingNo").val("");
	$("#orderRemark").val("");
	$("#littlePackageId").val("");
	$("#littlePackagebody").html("");
	parent.$.showShortMessage({msg:"请继续下一批收货",animate:false,left:"45%"});
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
