 //审核订单
function checkOrder(){
    var contentArr = [];
    contentArr.push('<div class="pull-left" style="width: 100%;text-align:center;line-height:20px;margin-top: 5px;">');
    contentArr.push('   <span class="pull-left">审核范围：</span>');
    contentArr.push('   <input class="pull-left" name="chooseOption" style="margin-left:30px;vertical-align: middle;" type="radio" checked="checked" value="selected" id="selected">');
    contentArr.push('   <label class="pull-left" style="margin-left: 5px;line-height:20px;vertical-align: middle;" for="selected">审核选中</label>');
    contentArr.push('   <input class="pull-left" name="chooseOption" style="margin-left:56px;vertical-align: middle;" type="radio" value="all" id="all">');
    contentArr.push('   <label class="pull-left" style="margin-left: 5px;line-height:20px;vertical-align: middle;" for="all">审核当前页</label>');
    contentArr.push('</div>');

    contentArr.push('<div class="pull-left" style="width: 100%;text-align:center;line-height:20px;">');
    contentArr.push('   <span class="pull-left">审核操作：</span>');
    contentArr.push('   <input class="pull-left" name="checkResult" style="margin-left:30px;vertical-align: middle;" type="radio" checked="checked" id="checkSuccess">');
    contentArr.push('   <label class="pull-left" style="margin-left: 5px;line-height:20px;vertical-align: middle;color: green;" for="checkSuccess">审核通过</label>');
    contentArr.push('   <input class="pull-left" name="checkResult" style="margin-left:56px;vertical-align: middle;" type="radio" id="checkFail">');
    contentArr.push('   <label class="pull-left" style="margin-left: 5px;line-height:20px;vertical-align: middle;color: red;" for="checkFail">审核不通过</label>');
    contentArr.push('</div>');

    contentArr.push('<div style="color: #ff0000;margin-left: 30px;">注: 非待审核状态的订单不受审核影响</div>');
    contentArr.push('</div>');
    var contentHtml = contentArr.join('');
	$.dialog({
  		lock: true,
  		max: false,
  		min: false,
  		title: '提示',
  		width: 360,
  		height: 85,
  		content: contentHtml,
  		button: [{
  			name: '确认',
  			callback: function() {
  				var  row = grid.getSelectedRows();
                var all = parent.$("#all").attr("checked");
                if(all){
                	row = grid.getRows();	 
                }
	            if(row.length < 1){
	                parent.$.showShortMessage({msg:"请最少选择一条数据",animate:false,left:"45%"});
	                return false;
	            }
                var checkResult = parent.$("#checkSuccess").attr("checked")?1:2;//1是审核通过 2是审核不通过
                var orderIds = "";
            	for ( var i = 0; i < row.length; i++) {
            		orderIds += row[i].id+",";
				}
        		$.post(baseUrl + '/warehouse/storage/checkOutWarehouseOrder.do',{orderIds:orderIds,checkResult:checkResult},function(msg){
        			if(msg.status == "1"){
        				grid.loadData();	
        				parent.$.showDialogMessage(msg.message,null,null);
        			}else{
        				parent.$.showDialogMessage(msg.message,null,null);
        			}
                },"json");
  			}
  		},
  		{
  			name: '取消'
  		}]
  	})
}

//审核单个订单
function checkSingleOrder(id){
    var contentArr = [];
    contentArr.push('<div class="pull-left" style="width: 100%;text-align:center;line-height:20px;">');
    contentArr.push('   <span class="pull-left">审核操作：</span>');
    contentArr.push('   <input class="pull-left" name="checkResult" style="margin-left:30px;vertical-align: middle;" type="radio" checked="checked" id="checkSuccess">');
    contentArr.push('   <label class="pull-left" style="margin-left: 5px;line-height:20px;vertical-align: middle;color: green;" for="checkSuccess">审核通过</label>');
    contentArr.push('   <input class="pull-left" name="checkResult" style="margin-left:56px;vertical-align: middle;" type="radio" id="checkFail">');
    contentArr.push('   <label class="pull-left" style="margin-left: 5px;line-height:20px;vertical-align: middle;color: red;" for="checkFail">审核不通过</label>');
    contentArr.push('</div>');

    contentArr.push('<div style="color: #ff0000;margin-left: 30px;">注: 非待审核状态的订单不受审核影响</div>');
    contentArr.push('</div>');
    var contentHtml = contentArr.join('');
	$.dialog({
  		lock: true,
  		max: false,
  		min: false,
  		title: '提示',
  		width: 360,
  		height: 85,
  		content: contentHtml,
  		button: [{
  			name: '确认',
  			callback: function() {
                var checkResult = parent.$("#checkSuccess").attr("checked")?1:2;//1是审核通过 2是审核不通过
        		$.post(baseUrl + '/warehouse/storage/checkOutWarehouseOrder.do',{orderIds:id,checkResult:checkResult},function(msg){
        			if(msg.status == "1"){
        				grid.loadData();	
        				parent.$.showDialogMessage(msg.message,null,null);
        			}else{
        				parent.$.showDialogMessage(msg.message,null,null);
        			}
                },"json");
  			}
  		},
  		{
  			name: '取消'
  		}]
  	})
}

//打印订单
function printOrder(){
    var contentArr = [];
    contentArr.push('<div id="changeContent" style="padding:10px;width: 240px;">');
    contentArr.push('   <div class="pull-left" style="width: 100%">');
    contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" checked="checked" value="selected" id="selected">');
    contentArr.push('       <label class="pull-left" style="margin-left: 5px" for="selected">打印选中</label>');
    contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" value="all" id="all">');
    contentArr.push('       <label class="pull-left" style="margin-left: 5px;" for="all">打印当前页</label>');
    contentArr.push('   </div>');
    contentArr.push('</div>');
    contentArr.push('<div style="color: #ff0000;margin-left: 40px;">注：待仓库审核的订单不能打印捡货单</div>');
    var contentHtml = contentArr.join('');
	$.dialog({
  		lock: true,
  		max: false,
  		min: false,
  		title: '打印捡货单',
  	     width: 260,
         height: 60,
  		content: contentHtml,
  		button: [{
  			name: '确认',
  			callback: function() {
  				var  row = grid.getSelectedRows();
                var all = parent.$("#all").attr("checked");
                if(all){
                	row = grid.getRows();	 
                }
	            if(row.length < 1){
	                parent.$.showShortMessage({msg:"请最少选择一条数据",animate:false,left:"45%"});
	                return false;
	            }
	            var orderIds = "";
            	for ( var i = 0; i < row.length; i++) {
            		orderIds += row[i].id+",";
				}
            	if(orderIds!=""){
            		//打印捡货单,新建标签页
    			    var url = baseUrl+'/warehouse/print/printPackageList.do?orderIds='+orderIds;
      			  	window.open(url);
            	}
  			}
  		},
  		{
  			name: '取消'
  		}]
  	})
}

//待打印订单界面 专用
function printWaitPrintOrder(){
    var contentArr = [];
    contentArr.push('<div id="changeContent" style="padding:10px;width: 240px;">');
    contentArr.push('   <div class="pull-left" style="width: 100%">');
    contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" checked="checked" value="selected" id="selected">');
    contentArr.push('       <label class="pull-left" style="margin-left: 5px" for="selected">打印选中</label>');
    contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" value="all" id="all">');
    contentArr.push('       <label class="pull-left" style="margin-left: 5px;" for="all">打印当前页</label>');
    contentArr.push('   </div>');
    contentArr.push('</div>');
    var contentHtml = contentArr.join('');
	$.dialog({
  		lock: true,
  		max: false,
  		min: false,
  		title: '打印捡货单',
  	     width: 260,
         height: 60,
  		content: contentHtml,
  		button: [{
  			name: '确认',
  			callback: function() {
  				var  row = grid.getSelectedRows();
                var all = parent.$("#all").attr("checked");
                if(all){
                	row = grid.getRows();	 
                }
	            if(row.length < 1){
	                parent.$.showShortMessage({msg:"请最少选择一条数据",animate:false,left:"45%"});
	                return false;
	            }
	            var orderIds = "";
            	for ( var i = 0; i < row.length; i++) {
            		orderIds += row[i].id+",";
				}
            	if(orderIds!=""){
            		//打印捡货单,新建标签页
    			    var url = baseUrl+'/warehouse/print/printPackageList.do?orderIds='+orderIds;
      			  	window.open(url);
            	}
            	grid.loadData();
  			}
  		},
  		{
  			name: '取消'
  		}]
  	})
}

//打印订单
function printSinleOrder(orderId){
	//打印捡货单,新建标签页
    var url = baseUrl+'/warehouse/print/printPackageList.do?orderIds='+orderId;
	window.open(url);
	grid.loadData();
}

//SKU
function listOutWarehouseOrderItem(orderId){
	var contentArr = [];
	contentArr.push('<div style="height:340px;overflow:auto; ">');
	contentArr.push('<table class="table" style="width:749px">');
	contentArr.push('<tr><th>商品条码</th><th>商品SKU</th><th>商品名称</th><th>商品规格</th><th>出库数量</th><th>单价和币种</th><th>单件重量</th></tr>');
	$.ajax({ 
        type : "post", 
        url :baseUrl + '/warehouse/storage/getOutWarehouseOrderItemByOrderId.do', 
        data : "orderId="+orderId, 
        async : false, 
        success : function(msg){ 
        	msg = eval("(" + msg + ")");
			$.each(msg,function(i,e){
			  	contentArr.push('<tr>');
			  	contentArr.push('<td>'+e.sku+'</td>');
			  	contentArr.push('<td>'+e.skuNo+'</td>');
        		contentArr.push('<td>'+e.skuName+'</td>');
        		contentArr.push('<td>'+e.specification+'</td>');
        		contentArr.push('<td>'+e.quantity+'</td>');
        		contentArr.push('<td>'+(e.skuUnitPrice +" "+e.skuPriceCurrency)+'</td>');
        		contentArr.push('<td>'+e.skuNetWeight+' G(克)</td>');
			  	contentArr.push('</tr>');
			});
        } 
   	});
    contentArr.push('</table>');
    contentArr.push('</div>');
    var contentHtml = contentArr.join('');
	$.dialog({
  		lock: true,
  		max: false,
  		min: false,
  		title: '出库订单SKU详情',
  		width: 750,
  		height: 350,
  		content: contentHtml,
  		button: [{
  			name: '关闭',
  			callback: function() {
				
  			}
  		}]
  	})
}

function listOutWarehouseOrderReceiver(orderId){
	var contentArr = [];
	contentArr.push('<div style="height:340px;overflow:auto; ">');
	contentArr.push('<table class="table" style="width:749px">');
	$.ajax({ 
        type : "post", 
        url :baseUrl + '/warehouse/storage/getOutWarehouseOrderReceiverByOrderId.do', 
        data : "orderId="+orderId, 
        async : false, 
        success : function(msg){ 
        	msg = eval("(" + msg + ")");
        	var name = (msg.name == undefined?"": msg.name);
        	var addressLine1 = (msg.addressLine1 == undefined?"": msg.addressLine1);
        	var addressLine2 = (msg.addressLine2 == undefined?"": msg.addressLine2);
        	var county = (msg.county == undefined?"": msg.county);
        	var city = (msg.city == undefined?"": msg.city);
        	var stateOrProvince = (msg.stateOrProvince == undefined?"": msg.stateOrProvince);
        	var countryName = (msg.countryName == undefined?"": msg.countryName);
        	var postalCode = (msg.postalCode == undefined?"": msg.postalCode);
        	var mobileNumber = (msg.mobileNumber == undefined?"": msg.mobileNumber);
        	var phoneNumber = (msg.phoneNumber == undefined?"": msg.phoneNumber);
        	contentArr.push('<tr><th>名字</th> <td>'+name+'</td></tr>');
        	contentArr.push('<tr><th>街道</th> <td>'+addressLine1+' '+addressLine2+'</td></tr>');
        	contentArr.push('<tr><th>县</th> <td>'+county+'</td></tr>');
        	contentArr.push('<tr><th>城市</th> <td>'+city+'</td></tr>');
        	contentArr.push('<tr><th>省/州</th> <td>'+stateOrProvince+'</td></tr>');
        	contentArr.push('<tr><th>国家</th> <td>'+countryName+'</td></tr>');
        	contentArr.push('<tr><th>邮编</th> <td>'+postalCode+'</td></tr>');
        	contentArr.push('<tr><th>手机号码</th> <td>'+mobileNumber+'</td></tr>');
        	contentArr.push('<tr><th>电话号码</th> <td>'+phoneNumber+'</td></tr>');
        } 
   	});
    contentArr.push('</table>');
    contentArr.push('</div>');
    var contentHtml = contentArr.join('');
	$.dialog({
  		lock: true,
  		max: false,
  		min: false,
  		title: '出库订单收件人',
  		width: 750,
  		height: 350,
  		content: contentHtml,
  		button: [{
  			name: '关闭',
  			callback: function() {
				
  			}
  		}]
  	})
}

function listOutWarehouseOrderSender(orderId){
	var contentArr = [];
	contentArr.push('<div style="height:340px;overflow:auto; ">');
	contentArr.push('<table class="table" style="width:749px">');
	$.ajax({ 
        type : "post", 
        url :baseUrl + '/warehouse/storage/getOutWarehouseOrderSenderByOrderId.do', 
        data : "orderId="+orderId, 
        async : false, 
        success : function(msg){ 
        	msg = eval("(" + msg + ")");
        	var name = (msg.name == undefined?"": msg.name);
        	var addressLine1 = (msg.addressLine1 == undefined?"": msg.addressLine1);
        	var addressLine2 = (msg.addressLine2 == undefined?"": msg.addressLine2);
        	var county = (msg.county == undefined?"": msg.county);
        	var city = (msg.city == undefined?"": msg.city);
        	var stateOrProvince = (msg.stateOrProvince == undefined?"": msg.stateOrProvince);
        	var countryName = (msg.countryName == undefined?"": msg.countryName);
        	var postalCode = (msg.postalCode == undefined?"": msg.postalCode);
        	var mobileNumber = (msg.mobileNumber == undefined?"": msg.mobileNumber);
        	var phoneNumber = (msg.phoneNumber == undefined?"": msg.phoneNumber);
        	contentArr.push('<tr><th>名字</th> <td>'+name+'</td></tr>');
        	contentArr.push('<tr><th>街道</th> <td>'+addressLine1+' '+addressLine2+'</td></tr>');
        	contentArr.push('<tr><th>县</th> <td>'+county+'</td></tr>');
        	contentArr.push('<tr><th>城市</th> <td>'+city+'</td></tr>');
        	contentArr.push('<tr><th>省/州</th> <td>'+stateOrProvince+'</td></tr>');
        	contentArr.push('<tr><th>国家</th> <td>'+countryName+'</td></tr>');
        	contentArr.push('<tr><th>邮编</th> <td>'+postalCode+'</td></tr>');
        	contentArr.push('<tr><th>手机号码</th> <td>'+mobileNumber+'</td></tr>');
        	contentArr.push('<tr><th>电话号码</th> <td>'+phoneNumber+'</td></tr>');
        } 
   	});
    contentArr.push('</table>');
    contentArr.push('</div>');
    var contentHtml = contentArr.join('');
	$.dialog({
  		lock: true,
  		max: false,
  		min: false,
  		title: '出库订单发件人',
  		width: 750,
  		height: 350,
  		content: contentHtml,
  		button: [{
  			name: '关闭',
  			callback: function() {
				
  			}
  		}]
  	})
}

function advancedSearch(){
	   $.dialog({
	          lock: true,
	          title: '批量单号搜索',
	          width: '600px',
	          height: '200px',
	          content: 'url:' + baseUrl + '/warehouse/storage/searchOutWarehouseOrder.do',
	          button: [{
	            name: '确定',
	            callback: function() {
	              var nos = this.content.$("#nos").val();
	              var noType = this.content.$("#noType").val();
	              //执行查询,返回不能查到出库订单的 客户订单号
	              $.post(baseUrl + '/warehouse/storage/executeSearchOutWarehouseOrder.do', {
	            	  nos:nos,
	            	  noType:noType
	              },
	              function(msg) {
	            	  var  allNos = msg.allNos;
	            	  if(msg.status =='0'){
	            			parent.$.showDialogMessage(msg.message, null, null);
	            	  }
	            	  if(msg.status =='1'){
	            		  if(msg.unAbleNoCount !='0'){
	            			  var  str = ('一共查询到'+msg.orderCount+'个订单,' +msg.unAbleNoCount+ '个单号不能查询到订单,它们是:<br/>'+msg.unAbleNos+'.');
	            			  if(str.length>100){
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
	            		  if(msg.unAbleNoCount == '0'){
	            			  var str = ('一共查询到'+msg.orderCount+'个订单,没有单号查询不到订单.');
	            			  parent.$.showShortMessage({msg:str,animate:false,left:"45%"});
	            		  }
	            		  //对 searchfrom 赋值, 执行查询
	            		  $("#noType").val(noType);
	            		  $("#nos").val(allNos);
	            		  btnSearch("#searchform",grid);
	            		  //查询完成后把隐藏的条件去掉
	            		  $("#noType").val("");
	            		  $("#nos").val("");
	            	 }
	              },"json");
	            }
	          }],
	          cancel: true
	        });
}

function inportOrder(){
	   $.dialog({
	          lock: true,
	          title: '导入订单',
	          width: '720px',
	          height: '450px',
	          content: 'url:' + baseUrl + '/warehouse/importorder/importOutWarehouseOrder.do',
	          button: [{
	            name: '关闭',
	            callback: function() {
	            	grid.loadData();
	            }
	          }]
	        });
 }

function exportOrder(status){
	$.dialog({
        lock: true,
        title: '导出单品订单',
        width: '550px',
        height: '270px',
        content: 'url:' + baseUrl + '/warehouse/exportOrder/exportOutWarehouseOrder.do?status='+status,
        button: [{
          name: '确认',
          callback: function() {
        	  var warehouseId = this.content.$("#warehouseId").val();
        	  var status = this.content.$("#status").val();
        	  var userLoginName = this.content.$("#userLoginName").val();
        	  var createdTimeStart = this.content.$("#createdTimeStart").val();
        	  var createdTimeEnd = this.content.$("#createdTimeEnd").val();
        	  var isQuantityOnly1 =  this.content.$("#isQuantityOnly1").val();
        	  var url = baseUrl+'/warehouse/exportOrder/executeExportOrder.do?warehouseId='+warehouseId+'&status='+status
        	  +'&userLoginName='+userLoginName+'&createdTimeStart='+createdTimeStart+'&createdTimeEnd='+createdTimeEnd+'&isQuantityOnly1='+isQuantityOnly1;
     		  window.open(url);
          }
        },
  		{
  			name: '取消'
  		}]
      });
}

function clickTrackingNoCheckBox(){
	 var trackingNoCheckBox = $("#trackingNoCheckBox").attr("checked");
	 if(trackingNoCheckBox){
		 $("#trackingNoIsNull").val("Y");
	 }else{
		 $("#trackingNoIsNull").val("N");
	 }
	 btnSearch("#searchform",grid);
}

//打印运单
function printShipLabel(){
    var contentArr = [];
    contentArr.push('<div id="changeContent" style="padding:10px;width: 240px;">');
    contentArr.push('   <div class="pull-left" style="width: 100%">');
    contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" checked="checked" value="selected" id="selected">');
    contentArr.push('       <label class="pull-left" style="margin-left: 5px" for="selected">打印选中</label>');
    contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" value="all" id="all">');
    contentArr.push('       <label class="pull-left" style="margin-left: 5px;" for="all">打印当前页</label>');
    contentArr.push('   </div>');
    contentArr.push('</div>');
    contentArr.push('<div style="color: #ff0000;margin-left: 40px;">注：未完成称重的订单不能打印出货运单</div>');
    var contentHtml = contentArr.join('');
	$.dialog({
  		lock: true,
  		max: false,
  		min: false,
  		title: '打印出货运单',
  	     width: 260,
         height: 60,
  		content: contentHtml,
  		button: [{
  			name: '确认',
  			callback: function() {
  				var orderIds = "";
				var  row = grid.getSelectedRows();
                var all = parent.$("#all").attr("checked");
                if(all){
                	row = grid.getRows();	 
                }
	            if(row.length < 1){
	                parent.$.showShortMessage({msg:"请最少选择一条数据",animate:false,left:"45%"});
	                return false;
	            }
            	for ( var i = 0; i < row.length; i++) {
            		orderIds += row[i].id+",";
				}
            	if(orderIds!=""){
            		var url = baseUrl+'/warehouse/print/printShipLabel.do?orderIds='+orderIds;
            		 window.open(url);
            	}
  			}
  		},
  		{
  			name: '取消'
  		}]
  	});
}

//申请单号
function applyTrackingNo(){
	  var contentArr = [];
      contentArr.push('  <div class="pull-left" style="width:660px;height:30px;">');
      contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 10px;" type="radio" checked="checked" value="selected" id="selected">');
      contentArr.push('       <label class="pull-left" style="margin-left: 5px" for="selected">申请选中</label>');
      contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" value="all" id="all">');
      contentArr.push('       <label class="pull-left" style="margin-left: 5px;" for="all">申请当前页</label>');
      contentArr.push('  </div>');
      
      contentArr.push('  <table class="table table-striped" style="margin-bottom:6px;">');
      contentArr.push(' <tr>');
      contentArr.push(' <th style="color:red">注:已经有跟踪号码的订单不能重复申请单号,此操作对已经有跟踪单号的订单无影响</th>');
      contentArr.push(' </tr>');
      contentArr.push('  </table>');
      
      contentArr.push('  <table class="table table-striped" style="margin-top:0px;margin-bottom:2px;">');
      contentArr.push(' <tr>');
      contentArr.push(' <th>申请单号总数</th>');
      contentArr.push(' <th><span id="registerTotal"></span></th>');
      contentArr.push(' </tr>');
      contentArr.push('  </table>');
      
      contentArr.push('  <div  class="pull-left" style="width:660px;height:340px;overflow:auto;">');
      contentArr.push('  <table class="table table-striped" id="registerResult" style="margin-top:0px;">');
      contentArr.push(' <tr>');
      contentArr.push(' <th>序号</th>');
      contentArr.push(' <th>状态</th>');
      contentArr.push(' <th>结果</th>');
      contentArr.push(' </tr>');
      contentArr.push('  </table>');
      contentArr.push('  </div>');
      var isEnableClick= true; 
      var contentHtml = contentArr.join('');
        $.dialog({
                lock: true,
                max: false,
                min: false,
                title: '申请跟踪单号',
                width: 660,
                height: 420,
                content:contentHtml ,
                button: [{
					name: '确认申请',
					callback: function() {
						if(!isEnableClick){
		  					return false;
		  				}
		  				isEnableClick = false;
		  				
						var that = this;
						var orderIds = "";
						var  row = grid.getSelectedRows();
		                var all = parent.$("#all").attr("checked");
		                if(all){
		                	row = grid.getRows();	 
		                }
			            if(row.length < 1){
			                parent.$.showShortMessage({msg:"请最少选择一条数据",animate:false,left:"43%"});
			                return false;
			            }
		            	for ( var i = 0; i < row.length; i++) {
		            		orderIds += row[i].id+",";
						}
	                    var registerTotal = 0;  //预报的总数
	                    var orderIdArray = orderIds.split(",");
	                    $(orderIdArray).each(function(i,e){
	                    	if(e!=null && e!=''){
	                    		registerTotal++;
	                    	}
	                    });
	                    parent.$("#registerTotal").html(registerTotal);
	                    $.ajaxSettings.async = false; 
	                    $(orderIdArray).each(function(i,e){
	                    	if(e!=null && e!=''){
	                    	    $.getJSON(baseUrl + '/warehouse/storage/applyTrackingNo.do',{orderId:e},function(msg) {
		                    		var tr = "<tr>";
	                    			tr+=("<td>"+(i+1)+"</td>");
	                    			if(msg.status == 1){
	                    				tr+=("<td>成功</td>");
	                    			}else{
	                    				tr+=("<td>失败</td>");
	                    			}
	                    			tr+=("<td>"+msg.message+"</td>");
	                    			tr+="<tr/>";
	                    			parent.$("#registerResult tr:last").after(tr);
		                    		if(i == registerTotal-1){//到最后一条,开始刷新订单,关闭界面
		                    			grid.loadData();	
		        	                    setTimeout(function(){
		        	                    	that.close();	
		        	                    }, 60000);	
		                    		}
		    					});
	                    	}
	                    });
	                    return false;
					}
				},
				{
					name: '关闭'
		        }]
	    });
};

