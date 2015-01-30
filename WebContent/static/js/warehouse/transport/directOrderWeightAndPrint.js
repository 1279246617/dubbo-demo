function submitFirstWaybillTrackingNo(){
 		var firstWaybillTrackingNo  = $("#firstWaybillTrackingNo").val();
 		if(firstWaybillTrackingNo == null || firstWaybillTrackingNo == ''){
 			parent.$.showDialogMessage("请输入头程跟踪单号",null,null);
 			return;
 		}
 		//清空运单信息
 		$("#orderId").val("");
 		$("#firstWaybillId").val("");
		$("#status").val("");
 		$("#shipwayCode").val("");
 		$("#weight").val("");
		$("#trackingNo").val("");
 		$.post(baseUrl+ '/warehouse/transport/orderWeightSubmitFirstWaybillTrackingNo.do',{
 			firstWaybillTrackingNo:firstWaybillTrackingNo
 		}, function(msg) {
 				if(msg.status == 0){
 					parent.$.showDialogMessage(msg.message,null,null);
 					return;
 				}
 				if(msg.status ==1){
 					$("#status").val(msg.orderStatus);
 					$("#orderId").val(msg.orderId);
 					$("#firstWaybillId").val(msg.firstWaybillId);
 					$("#trackingNo").val(msg.trackingNo);
 					$("#shipwayCode").val(msg.shipwayCode);
 					$("#weight").focus();//焦点转至称重
 				}
 		},"json");
}

//称重
function saveweight(){
	var weight = $("#weight").val();
	var orderId = $("#orderId").val();
	var shipwayCode = $("#shipwayCode").val();
	var firstWaybillTrackingNo  = $("#firstWaybillTrackingNo").val();
	if(firstWaybillTrackingNo == null || firstWaybillTrackingNo == ''){
		parent.$.showDialogMessage("请输入头程运单跟踪单号",null,null);
		return false;
	}
	if(orderId ==null || orderId == ''){
		parent.$.showShortMessage({msg:"没有找到转运订单,刷新后重试",animate:false,left:"45%"});
		return false;
	}
	if(weight ==null || weight == ''){
		parent.$.showShortMessage({msg:"请先输入装箱重量",animate:false,left:"45%"});
		$("#weight").focus();
		return false;
	}
	if(weight>1000){
		parent.$.showDialogMessage("装箱重量不可以大于1000KG");
		$("#weight").focus();
		return false;
	}
	$.post(baseUrl+ '/warehouse/transport/orderSubmitWeight.do?orderId='+ orderId+'&weight='+weight, function(msg) {
			if(msg.status == 0){
				parent.$.showShortMessage({msg:msg.message,animate:false,left:"45%"});
				return;
			}
			if(msg.status == 1){
				parent.$.showShortMessage({msg:"保存转运订单装箱重量成功",animate:false,left:"45%"});
				next();	
			}
	},"json");
	if($("#andPrint").attr("checked")=="checked"){
		printShipLabel(orderId,shipwayCode);	//同时打印运单
	}
}

function next(){
	$("#orderId").val("");
	$("#firstWaybillId").val("");
	$("#status").val("");
	$("#shipwayCode").val("");
	$("#weight").val("");
	$("#trackingNo").val("");
	focus='1';
	$("#firstWaybillTrackingNo").val("");
	$("#firstWaybillTrackingNo").focus();
}

//打印出货运单标签
function printShipLabel(orderId,shipwayCode){
	//无预览打印
	var url = baseUrl+'/warehouse/directPrint/transportShipLabel.do?orderId='+orderId+'&shipwayCode='+shipwayCode;
	 //有预览打印
	if($("#printWithPreview").attr("checked")=="checked"){
		url = baseUrl+'/warehouse/print/printTransportShipLabel.do?orderIds='+orderId;
	}
	window.open(url);
	setTimeout(function(){
		$("#firstWaybillTrackingNo").focus();
	}, 1500);
}

