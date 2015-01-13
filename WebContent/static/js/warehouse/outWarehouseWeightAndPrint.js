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
	$("#shipwayCode").val("");
	$("#trackingNo").val("");
	$("#skusTbody").html("");
	$("#weightOk").hide();
	$("#outWarehouseOrderId").val("");
	$("#status").val("");
	$.post(baseUrl+ '/warehouse/storage/outWarehouseSubmitCustomerReferenceNo.do?customerReferenceNo='+ customerReferenceNo, function(msg) {
			if(msg.status == 0){
				parent.$.showShortMessage({msg:msg.message,animate:false,left:"45%"});
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
				tr+="<td style='width:205px;text-align:left;'>"+n.sku+"</td>";
				tr+="<td style='width:205px;text-align:left;'>"+n.quantity+"</td>";
				tr+="<td style='width:205px;text-align:left;'><input readonly='readonly' type='text'  id='sku_"+n.sku+"' value='0'></td>";
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
	var quantity = $("#quantity").val();
	var oldQuantity = $("#sku_"+sku).val();
	
	if(oldQuantity!=undefined){
		$("#sku_"+sku).val(parseInt(oldQuantity) + parseInt(quantity));	
	}
}
 
 //保存重量
 function saveweight(){
	var weight = $("#weight").val();
	var customerReferenceNo  = $("#customerReferenceNo").val();
	if(customerReferenceNo == null||customerReferenceNo==''){
		parent.$.showShortMessage({msg:"请先输入客户订单号",animate:false,left:"45%"});
		return false;
	}
	if(weight ==null || weight == ''){
		parent.$.showShortMessage({msg:"请先输入出库订单总重量",animate:false,left:"45%"});
		return false;
	}
	$.post(baseUrl+ '/warehouse/storage/outWarehouseSubmitWeight.do?customerReferenceNo='+ customerReferenceNo+'&outWarehouseOrderWeight='+weight, function(msg) {
			if(msg.status == 0){
				parent.$.showShortMessage({msg:msg.message,animate:false,left:"45%"});
				$("#weightOk").hide();
				return;
			}
			if(msg.status == 1){
				parent.$.showShortMessage({msg:"保存出库订单总重量成功",animate:false,left:"45%"});
				$("#weightOk").show();
			}
	},"json");
 }
 	
 //打印出货运单标签
 function printShipLabel(){
 		var customerReferenceNo  = $("#customerReferenceNo").val();
 		var outWarehouseOrderId  = $("#outWarehouseOrderId").val();
 		if(customerReferenceNo=='' || outWarehouseOrderId ==''){
 			parent.$.showDialogMessage("请先输入正确客户订单号", null, null);
			return;
 		}
 		$.ajaxSetup({
 			async : false
 		});
 		var isCanPrint = false;
 		$.post(baseUrl+ '/warehouse/storage/checkOutWarehouseOrderPrintShipLabel.do?outWarehouseOrderId='+ outWarehouseOrderId, function(msg) {
			if(msg.status == 0){
				parent.$.showDialogMessage("<font style='font-size: 18px;font-weight: bold;color: red;'>"+msg.message+"</b>", null, null);
				return;
			}
			if(msg.status == 1){
				isCanPrint = true;
			}
 		 },"json");
 		if(isCanPrint){
 			var url = baseUrl+'/warehouse/print/printShipLabel.do?orderIds='+outWarehouseOrderId;
			 window.open(url); 	
 		}
 }
 
 	 	 
 	 	 