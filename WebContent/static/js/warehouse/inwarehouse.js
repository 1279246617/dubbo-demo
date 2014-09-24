//第一次点击保存主单(无输入客户单号)
function clickEnterStep1(trackingNoStr,userLoginName) {
	// 检查跟踪号是否能找到唯一的入库订单
	$.getJSON(baseUrl
			+ '/warehouse/storage/checkFindInWarehouseOrder.do?trackingNo='
			+ trackingNoStr, function(msg) {
		if (msg.status == -1) {
			// 找不到订单, 请输入客户帐号
			parent.$.showDialogMessage(msg.message, null, null);
			userLoginName.removeAttr("readonly");
			// 标记为无主件,操作员可以手工取消标记无主件
			$("#unKnowCustomer").attr("checked", "checked");
			$('#unKnowCustomer').removeAttr("readonly");
		}
		if (msg.status == 2) {
			// 找到多条订单,请选择客户帐号
			parent.$.showDialogMessage(msg.message, null, null);
			userLoginName.hide();
			var loginNameSelect = $("#loginNameSelect");
			loginNameSelect.show();
			loginNameSelect.empty();
			loginNameSelect.append("<option></option>");
			$.each(msg.userList, function(i, n) {
				loginNameSelect.append("<option value='" + this.loginName + "'>"
						+ this.loginName + "</option>");
			});
		}
		if (msg.status == 1) {
			$('#userLoginName').val(msg.user.loginName);
			// 光标移至产品SKU
			$("#itemSku").focus();
		}
	});
}


//第二次点击保存主单(已输入客户单号)
function clickEnterStep2(trackingNoStr,userLoginNameStr,isUnKnowCustomer,remark) {
	$.getJSON(baseUrl+ '/warehouse/storage/saveInWarehouseRecord.do?trackingNo='
			+ trackingNoStr+'&userLoginName='+userLoginNameStr+'&isUnKnowCustomer='+isUnKnowCustomer+"&remark="+remark, function(msg) {
		if(msg.status == -1){
			parent.$.showDialogMessage(msg.message, null, null);
			return;
		}
		
		
		
	});
}
//用户名下拉组 赋值到 input
function loginNameSelectChange(){
	var loginNameSelect = 	$("#loginNameSelect").val();
	$("#userLoginName").val(loginNameSelect);
}