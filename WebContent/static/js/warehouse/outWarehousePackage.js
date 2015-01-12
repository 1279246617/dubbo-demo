//手动输入coe单号
function enterCoeTrackingNo(){
	var coeTrackingNo = $("#coeTrackingNo").val();
	$.post(baseUrl+ '/warehouse/storage/outWarehouseShippingEnterCoeTrackingNo.do?&coeTrackingNo='+coeTrackingNo, function(msg) {
		//清空
		$("#total").html(0);
		$("#coeTrackingNoId").val("");
		$("#trackingNo").val("");
		$("#trackingNos").html("");
		
  		if(msg.status == 0){
  			parent.$.showDialogMessage(msg.message, null, null);
  			return false;
  		}
  		
  		if(msg.status == 1){
  			var coeTrackingNoId = msg.coeTrackingNo.id;
  			$("#coeTrackingNoId").val(coeTrackingNoId);
  	  		$.each(msg.outWarehouseShippingList,function(i,e){
	  			//添加从数据库查出来的运单
				var tr = "<tr style='height:25px;' id="+e.id+">";
		  		tr += "<td style='text-align: center'>"+e.outWarehouseOrderTrackingNo+"</td>";
		  		tr += "</tr>";
	  			$("#trackingNos").append(tr);
	  			$("#total").html( parseInt($("#total").html()) + 1);
	  			orderIds +=e.outWarehouseOrderId+"||";
			});
  	  		focus = '2';
  			// 光标移至跟踪号
  			$("#trackingNo").focus();
  			$("#trackingNo").select();
  			return false;
  		}
  	},"json");
}

var submitTrackingNoResult = 'true';
var submitTrackingNoSuccessQuantity = 0;
var submitTrackingNoFailQuantity = 0;
 //回车事件
function next(){
	var coeTrackingNo = $("#coeTrackingNo").val();
	var coeTrackingNoId = $("#coeTrackingNoId").val();
	 if(coeTrackingNo == null || coeTrackingNo == ""){
		parent.$.showDialogMessage("请输入有效的COE单号并按回车!", null, null);
		return false;
	 }
	 if(coeTrackingNoId ==""){
		parent.$.showDialogMessage("请输入有效的COE单号并按回车!", null, null);
		return false;
	 }
	 var trackingNo = $("#trackingNo").val();
	 if(trackingNo == null || trackingNo==''){
		parent.$.showShortMessage({msg:"请输入出货跟踪单号再按回车!",animate:false,left:"43%"});
		return false;	 
	 }
	var addOrSub = 1; //1标识加,2表示减
	if(!$("#add").attr("checked")){
		addOrSub = 2;
	}
	if(trackingNo.indexOf(",")>=0){
		submitTrackingNoSuccessQuantity= 0;//成功数
 		submitTrackingNoFailQuantity = 0;//失败数
		var failTrackingNos = "";
 		var trackingNoSplit = trackingNo.split(",");
		$.each(trackingNoSplit,function(i,e){
			submitSingleTrackingNo(e, coeTrackingNoId, coeTrackingNo, addOrSub,"Y");	
			if(submitTrackingNoResult =='false'){
				submitTrackingNoFailQuantity++;
				failTrackingNos += (e+',');
			}else{
				submitTrackingNoSuccessQuantity++;
			}
		});	
		if(failTrackingNos.length>0){
			failTrackingNos = failTrackingNos.substring(0, failTrackingNos.length-1);
		}
		if(submitTrackingNoFailQuantity >0){
			batchSubmitResults(submitTrackingNoSuccessQuantity,submitTrackingNoFailQuantity,failTrackingNos);//批量时显示提示
		}else{
			parent.$.showShortMessage({msg:"批量提交跟踪单号全部处理成功",animate:false,left:"43%"});
		}
	}else{
		submitSingleTrackingNo(trackingNo, coeTrackingNoId, coeTrackingNo, addOrSub,"N");	
	}
}

 //isBatch是否批量提交, 如果批量提交不显示每个详细的提示
function submitSingleTrackingNo(trackingNo,coeTrackingNoId,coeTrackingNo,addOrSub,isBatch){
  	$.post(baseUrl+ '/warehouse/storage/checkOutWarehouseShipping.do?trackingNo='+ trackingNo+'&coeTrackingNoId='+coeTrackingNoId+'&coeTrackingNo='+coeTrackingNo+'&addOrSub='+addOrSub+"&orderIds="+orderIds, function(msg) {
  		if(msg.status ==0){
  			submitTrackingNoResult  = 'false';
  		}
  		if(msg.status == 0 && isBatch=='N'){
  			parent.$.showDialogMessage(msg.message, null, null);
  			return false;
  		}
  		if(msg.status == 1){//绑定成功
  			submitTrackingNoResult  = 'true';
  			//添加一行运单
			var tr = "<tr style='height:25px;' id="+msg.outWarehouseShippingId+">";
	  		tr += "<td style='text-align: center'>"+trackingNo+"</td>";
	  		tr += "</tr>";
  			$("#trackingNos").append(tr);
  			$("#total").html( parseInt($("#total").html()) + 1);
  			orderIds +=msg.orderId+"||";
  			// 光标移至跟踪号
  			$("#trackingNo").focus();
  			$("#trackingNo").select();
  			return false;
  		}
  		if(msg.status ==2){//解除绑定成功
  			submitTrackingNoResult  = 'true';
  			$(msg.deleteShippingIds).html("");
  			$("#total").html( parseInt($("#total").html()) - msg.sub);
  			orderIds = msg.orderIds;	//后台处理 需要删除的orderId
  			return false;
  		}
  	},"json");
} 
 
 //提交全部单号
 var isSubmintIng = 'N';//是否在提交过程中. 
 function submitAll(){
	 if(isSubmintIng == "Y"){//防止重复点击
		 return false;
	 }
	isSubmintIng ="Y";
	$("#submitAll").attr("disabled","disabled");
	 if(trackingNos == null || trackingNos ==''){
		if($("#trackingNo").val()!=""){
			parent.$.showShortMessage({msg:"请输入出货跟踪单号再按完成出货建包!",animate:false,left:"43%"});	
		}else{
			parent.$.showShortMessage({msg:"您需要按回车提交出库跟踪单号或缺少有效的出库跟踪单号!",animate:false,left:"43%"});  	 				
		}
		isSubmintIng = 'N';
		$("#submitAll").removeAttr("disabled");
		return false;
	 }
	 var coeTrackingNoId = $("#coeTrackingNoId").val();
	 var coeTrackingNo = $("#coeTrackingNo").val();
	 if(coeTrackingNo == null || coeTrackingNo == ""){
 			parent.$.showDialogMessage("请输入有效的COE单号并按回车!", null, null);
 			isSubmintIng = 'N';
 			$("#submitAll").removeAttr("disabled");
 			return false;
 	}
	if(coeTrackingNoId ==""){
		parent.$.showDialogMessage("请输入有效的COE单号并按回车!", null, null);
		isSubmintIng = 'N';
		$("#submitAll").removeAttr("disabled");
		return false;
	}
	$.post(baseUrl+ '/warehouse/storage/outWarehousePackageConfirm.do',{
		orderIds:orderIds,
		coeTrackingNo:coeTrackingNo,
		coeTrackingNoId:coeTrackingNoId
	},function(msg) {
		if(msg.status == 0){
			parent.$.showDialogMessage(msg.message, null, null);
			isSubmintIng = 'N';
			$("#submitAll").removeAttr("disabled");
  			return false;
		}
		if(msg.status >0){
			isSubmintIng = 'N';
			$("#submitAll").removeAttr("disabled");
  			//是否打印出货交接单
		 	 var contentArr = [];
  		    contentArr.push('<div id="changeContent" style="padding:10px;width: 380px;">');
  	  		contentArr.push('   <div class="pull-left" style="width: 100%height:40px;"><b>');
  		    contentArr.push(msg.message);
  		    contentArr.push('  </b></div><br/>');
  		    
           	contentArr.push('<div class="pull-left" style="margin-top:5px;text-align:center;line-height:20px;height:50px;width: 100%">');
          	contentArr.push('   <span class="pull-left">打印内容：</span>');
          	contentArr.push('   <input class="pull-left" style="margin-left:30px;vertical-align: middle;" id="printLabel" type="checkbox" checked>');
          	contentArr.push('   <label class="pull-left" style="margin-left: 5px;line-height:20px;vertical-align: middle;" for="printLabel">COE运单标签</label>');
          	contentArr.push('   <input class="pull-left" style="margin-left:30px;vertical-align: middle;" id="printCustoms" type="checkbox" checked>');
          	contentArr.push('   <label class="pull-left" style="margin-left: 5px;line-height:20px;vertical-align: middle;" for="printCustoms">出货交接单</label>');
          	contentArr.push('</div>');
          	contentArr.push('<div style="color: #ff0000;margin-left: 0px;">注：在建包记录查询页面也可以打印</div>');
  		    contentArr.push('</div>');
  		    var contentHtml = contentArr.join('');
  			$.dialog({
  		  		lock: true,
  		  		max: false,
  		  		min: false,
  		  		title: '是否打印',
  		  	     width: 380,
  		         height: 160,
  		  		content: contentHtml,
  		  		button: [{
  		  			name: '打印',
  		  			callback: function() {
  		  	      		//打印COE运单
                    	var isPrintLabel = parent.$("#printLabel").attr("checked")=="checked"?true:false;
						if(isPrintLabel){
							var url = baseUrl+'/warehouse/print/printCoeLabel.do?coeTrackingNoId='+coeTrackingNoId;
  	     			  		window.open(url);
                      	}
                      	//打印出货交接单
                   	   	var isPrintCustoms = parent.$("#printCustoms").attr("checked")=="checked"?true:false;
                      	if(isPrintCustoms){
  	  		            	var url = baseUrl+'/warehouse/print/printOutWarehouseEIR.do?coeTrackingNo='+coeTrackingNo+'&coeTrackingNoId='+coeTrackingNoId;
  	     			  		window.open(url);
                      	}
  		  			}
  		  		},{name: '取消'}]
  		  	});
  			orderIds = "";
  			//成功,清空输入,进入下一批,重新分配COE单号
			$("#coeTrackingNo").val(msg.coeTrackingNo);
			$("#coeTrackingNoId").val(msg.coeTrackingNoId);
			$("#total").html(0);
			$("#trackingNo").val("");
			$("#trackingNos").html("");
			// 光标移至跟踪号
  			$("#trackingNo").focus();
  			$("#trackingNo").select();
  			isSubmintIng = 'N';
  		 	$("#submitAll").removeAttr("disabled");
			return false;
		}
	},"json");
}
 
function batchTrackingNo(){
   var trackingNo = $("#trackingNo").val();
   $.dialog({
          lock: true,
          title: '批量',
          width: '450px',
          height: '290px',
          content: 'url:' + baseUrl + '/warehouse/storage/outWarehousePackageBatchTrackingNo.do?trackingNo='+trackingNo,
          button: [{
            name: '确定',
            callback: function() {
              var objTrackingNos = this.content.document.getElementById("trackingNos");
              var trackingNos = $(objTrackingNos).val();
              //替换所有空白字符为单个,
              var reg = /\s+/g;
              trackingNos = trackingNos.replace(reg,",");
			  $("#trackingNo").val(trackingNos); 
            }
          }],
          cancel: true
        });
}

function enClick (){
	$("#coeTrackingNo").removeAttr("readonly");
}

function batchSubmitResults(submitTrackingNoSuccessQuantity,submitTrackingNoFailQuantity,failTrackingNos){
 	  var  str = ('处理成功'+submitTrackingNoSuccessQuantity+'个跟踪单号,' +submitTrackingNoFailQuantity+ '个跟踪单号处理失败,它们是:<br/>'+failTrackingNos+'.');
	  if(str.length>50){
		 var contentArr = [];
		 contentArr.push('<p style="width:500px;height:100%;margin-left:2mm;margin-top:2mm;word-break:break-all;">');
		 contentArr.push('<b>'+str+'</b>');
	     contentArr.push('</p>');
	     var contentHtml = contentArr.join('');
		 $.dialog({
	  		lock: true,
	  		max: false,
	  		min: false,
	  		title: '提示',
	  		width: 550,
	  		height: 350,
	  		content: contentHtml,
	  		button: [{
	  			name: '确认',
	  			callback: function() {
	  				
	  			}
	  		}]
		  });
	  }else{
		  parent.$.showDialogMessage(str, null, null);
	  }
 }
