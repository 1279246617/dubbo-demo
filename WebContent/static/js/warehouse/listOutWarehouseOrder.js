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
  
	contentArr.push('<div class="pull-left" style="width: 100%;margin-top: 5px;text-align: center;line-height: 20px;">');
    contentArr.push('   <input class="pull-left" style="vertical-align: middle;margin-left:91px;" id="printed" type="checkbox" checked>');
    contentArr.push('   <label class="pull-left" style="margin:0 0 0 5px;line-height:20px;vertical-align: middle;display: inline-block;" for="printed">打印捡货单</label>');
    contentArr.push('</div>');
	  
    contentArr.push('</div>');
    var contentHtml = contentArr.join('');
	$.dialog({
  		lock: true,
  		max: false,
  		min: false,
  		title: '提示',
  		width: 360,
  		height: 80,
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
	                parent.$.showShortMessage({msg:"请最少选择一条数据",animate:true,left:"45%"});
	                return false;
	            }
                var checkResult = parent.$("#checkSuccess").attr("checked")?1:2;//1是审核通过 2是审核不通过
                var orderIds = "";
            	for ( var i = 0; i < row.length; i++) {
            		orderIds += row[i].id+",";
				}
        		$.post(baseUrl + '/warehouse/storage/checkOutWarehouseOrder.do',{orderIds:orderIds,checkResult:checkResult},function(msg){
        			
                });
        		
	            //打印捡货单,新建标签页
				if(parent.$("#printed").attr("checked")){
				    var url = baseUrl+'/warehouse/storage/printOutWarehouseOrderItem.do?orderIds='+orderIds;
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