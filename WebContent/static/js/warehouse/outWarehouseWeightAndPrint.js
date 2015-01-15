//回车事件
function clickEnter(){
	if(focus == "1"){
		submitCustomerReferenceNo();
	}
	if(focus == "2"){
		countSku();
	}
	if(focus == "3"){
		saveweight();
	}
}
 
function submitCustomerReferenceNo(){
	var customerReferenceNo  = $("#customerReferenceNo").val();
	if(customerReferenceNo==''){
		parent.$.showDialogMessage("请先输入客户订单号");
		$("#customerReferenceNo").removeAttr("readonly");
		$("#customerReferenceNo").focus();
		return false;
	}
	$("#shipwayCode").val("");
	$("#trackingNo").val("");
	$("#skusTbody").html("");
	$("#weightOk").hide();
	$("#outWarehouseOrderId").val("");
	$("#status").val("");
	$("#customerReferenceNo").attr("readonly","readonly");
	$.post(baseUrl+ '/warehouse/storage/outWarehouseSubmitCustomerReferenceNo.do?customerReferenceNo='+ customerReferenceNo, function(msg) {
			if(msg.status == 0){
				$("#customerReferenceNo").removeAttr("readonly");
				parent.$.showDialogMessage("<font style='font-size: 18px;font-weight: bold;color: red;'>"+msg.message+"</b>", null, null);
				return;
			}
			if(msg.status == 1){
			$("#shipwayCode").val(msg.shipwayCode);
			$("#trackingNo").val(msg.trackingNo);
			$("#outWarehouseOrderId").val(msg.outWarehouseOrderId);
			$("#status").val(msg.outWarehouseOrderStatus);
			var skus = msg.outWarehouseOrderItemList;
			$.each(skus, function(i, n) {
				var tr = "<tr>";
				tr+="<td style='text-align:left;'>"+n.sku+"</td>";
				tr+="<td style='text-align:left;'>"+n.quantity+"</td>";
				tr+="<td style='text-align:left;color:red;'><span  id='sku_"+n.sku+"'>"+n.quantity+"</span>";
				tr+="</tr>";
				$("#skusTbody").append(tr);
			});
			//切换焦点
			$("#weight").focus();
				focus = "3";
				return;
			}
		},"json");
 }

//复核SKU
function countSku(){
	var sku = $("#sku").val();
	var oldQuantity = $("#sku_"+sku).html();
	if(oldQuantity!=undefined){
		var newQuantity  = parseInt(oldQuantity) - 1;
		if(newQuantity<0){
			parent.$.showDialogMessage("注意!!此商品已经复核完成", null, null);
		}else{
			$("#sku_"+sku).html(newQuantity);	
		}
	}
	 $("#sku").val("");
}
 
 //保存重量
 function saveweight(){
	var weight = $("#weight").val();
	var customerReferenceNo  = $("#customerReferenceNo").val();
	var outWarehouseOrderId  = $("#outWarehouseOrderId").val();
	if(customerReferenceNo==''){
		parent.$.showDialogMessage("请先输入客户订单号");
		$("#customerReferenceNo").removeAttr("readonly");
		$("#customerReferenceNo").focus();
		return false;
	}
	if(outWarehouseOrderId ==''){
		parent.$.showDialogMessage("请先输入正确的客户订单号");
		$("#customerReferenceNo").removeAttr("readonly");
		$("#customerReferenceNo").focus();
		return false;
	}
	if(weight ==null || weight == ''){
		parent.$.showShortMessage({msg:"请先输入出库订单重量",animate:false,left:"45%"});
		$("#weight").focus();
		return false;
	}
	if(weight>1000){
		parent.$.showDialogMessage("出库订单重量不可以大于1000KG");
		$("#weight").focus();
		return false;
	}
	
	$.post(baseUrl+ '/warehouse/storage/outWarehouseSubmitWeight.do?customerReferenceNo='+ customerReferenceNo+'&outWarehouseOrderWeight='+weight, function(msg) {
			if(msg.status == 0){
				parent.$.showDialogMessage(msg.message);
				$("#weight").focus();
				return;
			}
			if(msg.status == 1){
				parent.$.showShortMessage({msg:"保存出库订单重量成功",animate:false,left:"45%"});
				if(continueWeight == 'N'){//清空重量
					$("#weight").val("");
				}
				$("#customerReferenceNo").val("");
				$("#customerReferenceNo").removeAttr("readonly");
				$("#customerReferenceNo").focus();
				 focus= "1";
			}
	},"json");
	
	//是否同时打印运单
	if($("#andPrint").attr("checked")=="checked"){
		printShipLabel(outWarehouseOrderId);
	}
 }
 	
 //打印出货运单标签
 function printShipLabel(outWarehouseOrderId){
	 var url = baseUrl+'/warehouse/directPrint/storageShipLabel.do?orderId='+outWarehouseOrderId;
	window.open(url);
	setTimeout(function(){ //IE6、7不会提示关闭
		$("#customerReferenceNo").focus();
	}, 1000);
 }
 
 	 	 
 	 	 