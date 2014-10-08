//第一次点击保存主单(无输入客户单号)
function clickEnterStep1(trackingNoStr,userLoginName,remark) {
	var loginNameSelect = $("#loginNameSelect");
	// 检查跟踪号是否能找到唯一的入库订单
	$.getJSON(baseUrl
			+ '/warehouse/storage/checkFindInWarehouseOrder.do?trackingNo='
			+ trackingNoStr, function(msg) {
		if (msg.status == -1) {
			// 找不到订单, 请输入客户帐号
			parent.$.showDialogMessage(msg.message, null, null);
			loginNameSelect.hide();
			userLoginName.show();
			// 标记为无主件,操作员可以手工取消标记无主件
			$("#unKnowCustomer").attr("checked", "checked");
		}
		if (msg.status == 2) {
			// 找到多条订单,请选择客户帐号
			parent.$.showDialogMessage(msg.message, null, null);
			userLoginName.hide();
			loginNameSelect.show();
			loginNameSelect.empty();
			loginNameSelect.append("<option></option>");
			$.each(msg.userList, function(i, n) {
				loginNameSelect.append("<option value='" + this.loginName + "'>"
						+ this.loginName + "</option>");
			});
		}
		if (msg.status == 1) {
			loginNameSelect.hide();
			userLoginName.show();
			$('#userLoginName').val(msg.user.loginName);
			//步骤1能得到用户名,直接调用步骤2
			clickEnterStep2(trackingNoStr,msg.user.loginName,null,remark);
		}
	});
}


//第二次点击保存主单(已输入客户单号)
function clickEnterStep2(trackingNoStr,userLoginNameStr,isUnKnowCustomer,remark) {
	$.post(baseUrl+ '/warehouse/storage/saveInWarehouseRecord.do?trackingNo='
			+ trackingNoStr+'&userLoginName='+userLoginNameStr+'&isUnKnowCustomer='+isUnKnowCustomer+"&remark="+remark, function(msg) {
		//赋值入库记录id 到隐藏input
		$("#inWarehouseRecordId").val(msg.id);
		
		if(msg.status == 0){
			parent.$.showShortMessage({msg:msg.message,animate:true,left:"45%"});
			// 光标移至产品SKU
			$("#itemSku").focus();
			return;
		}
		
		if(msg.status == 1){
			parent.$.showShortMessage({msg:"保存主单成功.",animate:true,left:"45%"});
			// 光标移至产品SKU
			$("#itemSku").focus();
			return;
		}
		
	},"json");
}
//用户名下拉组 赋值到 input
function loginNameSelectChange(){
	var loginNameSelect = 	$("#loginNameSelect").val();
	$("#userLoginName").val(loginNameSelect);
}

//跟踪号改变, 清除用户名
function trackingNoFocus(){
	$("#userLoginName").val('');
	$("#trackingNo").select();
}