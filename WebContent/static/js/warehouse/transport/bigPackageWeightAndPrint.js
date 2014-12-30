var littlePackageTrackingNoQuantity = 0;//每个客户参考号下,对应的小包跟踪号个数

function submitCustomerReferenceNo(){
 		var customerReferenceNo  = $("#customerReferenceNo").val();
 		//清空运单信息
 		$("#bigPackageId").val("");
		$("#status").val("");
		//清空重量出货渠道
 		$("#shipwayCode").val("");
		$("#outWarehouseTrackingNo").val("");
		$("#weightOk").hide();
		littlePackageTrackingNoQuantity = 0;
 		$.post(baseUrl+ '/warehouse/transport/bigPackageWeightSubmitCustomerReferenceNo.do',{
 			customerReferenceNo:customerReferenceNo
 		}, function(msg) {
 				if(msg.status == 0){
 					parent.$.showShortMessage({msg:msg.message,animate:false,left:"43%"});
 					return;
 				}
 				if(msg.status ==1){
 					$("#status").val(msg.bigPackageStatus);
 					$("#bigPackageId").val(msg.bigPackageId);
 					$("#outWarehouseTrackingNo").val(msg.trackingNo);
 					$("#shipwayCode").val(msg.shipwayCode);
 					var trackingNos = msg.trackingNos;
 					//按,号分开多个跟踪号
 					var trackingNosArray = trackingNos.split(",");
 					$("#littlePackageTrackingNos").html("");
 					$("#scanTrackingNos").html("");
                    $(trackingNosArray).each(function(i,e){
                    	if(e!=null && e!=''){
                    		var tr = "<tr id='"+e+"'>";
                    		tr+="<td>"+e+"</td>";
                    		tr+="</tr>";	
                    		$("#littlePackageTrackingNos").append(tr);
                    		littlePackageTrackingNoQuantity++;
                    	}
                    });
                    //进入复核
                    focus = '2';
 					$("#trackingNo").focus();
 					$("#trackingNo").select();
 				}
 		},"json");
}
 				
//复核小包
function checkLittlePackage(){
		var trackingNo = $("#trackingNo").val();
		if(trackingNo ==null || trackingNo == ''){
			parent.$.showShortMessage({msg:"请先输入小包跟踪号码",animate:false,left:"45%"});
			return false;
		}
		var bigPackageId = $("#bigPackageId").val();
		if(bigPackageId ==null || bigPackageId == ''){
			parent.$.showShortMessage({msg:"没有找到转运订单,刷新后重试",animate:false,left:"45%"});
			return false;
		}
		$.post(baseUrl+ '/warehouse/transport/checkLittlePackage.do?bigPackageId='+ bigPackageId+'&trackingNo='+trackingNo, function(msg) {
			if(msg.status == 0){
				parent.$.showShortMessage({msg:msg.message,animate:false,left:"45%"});
				$("#trackingNo").focus();
				return;
			}
			if(msg.status == 1){
				if($("#"+trackingNo).html() ==''){//重复扫描
					parent.$.showShortMessage({msg:"该小包重复扫描,请扫描下一个小包",animate:false,left:"45%"});
					$("#trackingNo").val("");
					$("#trackingNo").focus();
					return;
				}
				if(littlePackageTrackingNoQuantity ==0){
					focus = '3';
					$("#weight").focus();
					$("#weight").select();
				}
				parent.$.showShortMessage({msg:"复核成功,请继续下一个小包",animate:false,left:"45%"});
				littlePackageTrackingNoQuantity--;
				$("#"+trackingNo).html("");//左侧减
				var tr = "<tr><td>"+trackingNo+"</td></tr>";//右侧加
				$("#scanTrackingNos").append(tr);
				//复核成功,请继续下一个小包
				$("#trackingNo").val("");
				$("#trackingNo").focus();
				
				if(littlePackageTrackingNoQuantity ==0){//如果左边的跟踪号已经全部减完,进入称重
					parent.$.showShortMessage({msg:"复核完成,请开始称重和打单",animate:false,left:"45%"});
					focus = '3';
					$("#weight").focus();
					$("#weight").select();
					return;
				}
			}
		},"json");
}

//称重
function saveweight(){
	if(littlePackageTrackingNoQuantity != 0){
		parent.$.showDialogMessage("需要复核完成,才能称重", null, null);
		return false;
	}
	var weight = $("#weight").val();
	var bigPackageId = $("#bigPackageId").val();
	if(bigPackageId ==null || bigPackageId == ''){
		parent.$.showShortMessage({msg:"没有找到转运订单,刷新后重试",animate:false,left:"45%"});
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
				
				focus = '2';
				$("#outWarehouseTrackingNo").focus();
				$("#outWarehouseTrackingNo").select();
			}
	},"json");
}

//打印出货运单标签
function printShipLabel(){
	var bigPackageId = $("#bigPackageId").val();
	if(bigPackageId ==null || bigPackageId == ''){
		parent.$.showShortMessage({msg:"没有找到转运订单,刷新后重试",animate:false,left:"45%"});
		return false;
	}
	var url = baseUrl+'/warehouse/print/printTransportShipLabel.do?bigPackageIds='+bigPackageId;
	 window.open(url);
}

