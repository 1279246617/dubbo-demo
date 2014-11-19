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
        				parent.$.showShortMessage({msg:msg.message,animate:false,left:"38%"});
        				grid.loadData();	
        			}else{
        				parent.$.showShortMessage({msg:msg.message,animate:false,left:"43%"});
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


//SKU
function listOutWarehouseOrderItem(orderId){
	var contentArr = [];
	contentArr.push('<table class="table" style="width:549px">');
	contentArr.push('<tr><th>产品SKU</th><th>产品名称</th><th>出库数量</th><th>单价和币种</th><th>单件重量</th></tr>');
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
        		contentArr.push('<td>'+e.skuName+'</td>');
        		contentArr.push('<td>'+e.quantity+'</td>');
        		contentArr.push('<td>'+(e.skuUnitPrice +" "+e.skuPriceCurrency)+'</td>');
        		contentArr.push('<td>'+e.skuNetWeight+' G(克)</td>');
			  	contentArr.push('</tr>');
			});
        } 
   	});
    contentArr.push('</table>');
    var contentHtml = contentArr.join('');
	$.dialog({
  		lock: true,
  		max: false,
  		min: false,
  		title: '出库订单SKU详情',
  		width: 550,
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
	          title: '高级搜索',
	          width: '600px',
	          height: '400px',
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
	            	  if(msg.status =='0'){
	            			parent.$.showDialogMessage(msg.message, null, null);
	            	  }
	            	  if(msg.status =='1'){
	            		  var str = '';
	            		  if(msg.unAbleNoCount !='0'){
	            			  str = ('一共查询到'+msg.orderCount+'个订单,' +msg.unAbleNoCount+ '个单号不能查询到订单,它们是:'+msg.unAbleNos+'.');
	            		  }
	            		  if(msg.unAbleNoCount == '0'){
	            			  str = ('一共查询到'+msg.orderCount+'个订单,没有单号查询不到订单.');
	            		  }
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
	            		  
	            		  //对 searchfrom 赋值, 执行查询
	            		  $("#noType").val(noType);
	            		  $("#nos").val(nos);
	            		  btnSearch("#searchform",grid);
	            	 }
	              },"json");
	            }
	          }],
	          cancel: true
	        });
}

