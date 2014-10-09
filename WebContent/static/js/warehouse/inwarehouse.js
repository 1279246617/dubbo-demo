//点击保存主单
function saveInWarehouseRecord(){
	var trackingNo = $("#trackingNo");
	var trackingNoStr = trackingNo.val();
	var userLoginName = $('#userLoginName');
	var userLoginNameStr = userLoginName.val();
	var unKnowCustomer = $("#unKnowCustomer");
	var isUnKnowCustomer = unKnowCustomer.is(':checked'); //true | false
	var remark = $("#orderRemark").val();
	if($.trim(trackingNoStr) ==""){
		parent.$.showDialogMessage("请输入跟踪单号.",null,null);
		//焦点回到跟踪单号
		return;
	}
	if(trackingNoStr.indexOf(" ")> -1 && $.trim(trackingNoStr) !=""){
		parent.$.showShortMessage({msg:"您输入的跟踪单号中包含空白字符已被忽略.",animate:true,left:"40%"});
		trackingNo.val($.trim(trackingNoStr));
	}    	
		
	//跟踪号不为空,客户帐号为空调用clickEnterStep1();
	if($.trim(userLoginNameStr) == ''){
		saveInWarehouseRecordStep1(trackingNoStr,userLoginName,remark);		    			
	}
	// 若根据跟踪号 顺利找到唯一的客户帐号 自动调用clickEnterStep2
	//跟踪号不为空,客户帐号不为空,将提交保存
	if($.trim(userLoginNameStr) != ''){
		saveInWarehouseRecordStep2(trackingNoStr,userLoginNameStr,isUnKnowCustomer,remark);
	}
}

//保存主单1(无输入客户单号)
function saveInWarehouseRecordStep1(trackingNoStr,userLoginName,remark) {
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
			saveInWarehouseRecordStep2(trackingNoStr,msg.user.loginName,null,remark);
		}
	});
}


//保存主单2(已输入客户单号)
function saveInWarehouseRecordStep2(trackingNoStr,userLoginNameStr,isUnKnowCustomer,remark) {
	$.post(baseUrl+ '/warehouse/storage/saveInWarehouseRecord.do?trackingNo='
			+ trackingNoStr+'&userLoginName='+userLoginNameStr+'&isUnKnowCustomer='+isUnKnowCustomer+"&remark="+remark, function(msg) {
		//赋值入库记录id 到隐藏input
		$("#inWarehouseRecordId").val(msg.id);
		
		if(msg.status == 0){
			parent.$.showShortMessage({msg:msg.message,animate:true,left:"45%"});
			// 光标移至产品SKU
			$("#itemSku").focus();
			focus = "2";
			return;
		}
		
		if(msg.status == 1){
			parent.$.showShortMessage({msg:"保存主单成功.",animate:true,left:"45%"});
			// 光标移至产品SKU
			$("#itemSku").focus();
			focus = "2";
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
			focus = "2";
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