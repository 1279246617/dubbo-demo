//添加产品
function addProduct(){
		$.dialog({
          lock: true,
          title: '添加产品',
          width: '650px',
          height: '400px',
          content: 'url:' + baseUrl + '/products/addProduct.do',
          button: [{
            name: '确定',
            callback: function() {
            		var productName = this.content.$("#productName").val();
            		var productTypeName = this.content.$("#productTypeName").val();
            		var userIdOfCustomer = this.content.$("#userIdOfCustomer").val();
            		var isNeedBatchNo = this.content.$("#isNeedBatchNo").val();
            		var sku = this.content.$("#sku").val();
            		var warehouseSku = this.content.$("#warehouseSku").val();
            		var model = this.content.$("#model").val();
            		var volume = this.content.$("#volume").val();
            		var customsWeight = this.content.$("#customsWeight").val();
            		var currency = this.content.$("#currency").val();
            		var customsValue = this.content.$("#customsValue").val();
            		var taxCode = this.content.$("#taxCode").val();
            		var origin = this.content.$("#origin").val();
            		var remark = this.content.$("#remark").val();
            		var that = this; 
     	             $.post(baseUrl + '/products/saveAddProduct.do', {
     	            	productName:productName,
     	            	productTypeName:productTypeName,
     	            	userIdOfCustomer:userIdOfCustomer,
     	            	isNeedBatchNo:isNeedBatchNo,
     	            	sku:sku,
     	            	warehouseSku:warehouseSku,
     	            	model:model,
     	            	volume:volume,
     	            	customsWeight:customsWeight,
     	            	currency:currency,
     	            	customsValue:customsValue,
     	            	taxCode:taxCode,
     	            	origin:origin,
     	            	remark:remark
     	             },
     	             function(msg) {
     	            	if(msg.status == '1'){
     	            		parent.$.showShortMessage({msg:msg.message,animate:false,left:"43%"});
     	            		that.close();
     	            		grid.loadData();	 //保存成功,重新加载产品
     	            		
     	            	}
						if(msg.status =='0'){
							parent.$.showShortMessage({msg:msg.message,animate:false,left:"43%"});
					
						}
     	            },"json");
     	    		return false; 
            }
          }],
          cancel: true
        });   			
}
//删除单个产品
function deleteProduct(id){
	$.dialog({
  		lock: true,
  		max: false,
  		min: false,
  		title:'提示',
	    width:260,
	    height:60,
	    content: '您确认删除 1个产品吗？',
  		button: [{
  			name: '确认',
  			callback: function() {
  				$.post(baseUrl + '/products/deleteProductById.do',{id:id},function(msg){
        			if(msg.status == "1"){
        				parent.$.showShortMessage({msg:msg.message,animate:false,left:"43%"});
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

//批量删除产品
function deleteProductBatch(){
 var row = grid.getSelectedRows();
    if(row.length < 1){
        parent.$.showShortMessage({msg:"请最少选择一条数据",animate:false});
        return false;
    }
    $.dialog({
        lock: true,
        max:false,
        min:false,
        title:'提示',
        width:260,
        height:60,
        content: '您确认删除 '+row.length+' 个产品吗？',
        button:[{name: '确认',
            callback: function (){
            	var ids = "";
            	for ( var i = 0; i < row.length; i++) {
            		ids += row[i].id+",";
				}
            	$.post(baseUrl + '/products/deleteProductByIds.do',{ids:ids},function(msg){
//    		            		parent.$.showShortMessage({msg:msg.message,animate:false,left:"43%"});
// 								grid.loadData();
            		if(msg.status == "1"){
        				parent.$.showShortMessage({msg:msg.message,animate:false,left:"43%"});
        				grid.loadData();	
        			}else{
        				parent.$.showShortMessage({msg:msg.message,animate:false,left:"43%"});
        			}
            		
	            },"json");
            }},{name: '取消'}]
    });
}
//更新产品			   		
function updateProduct(id){
	$.dialog({
          lock: true,
          title: '更新产品',
          width: '800px',
          height: '600px',
          content: 'url:' + baseUrl + '/products/getProductById.do?id='+id,
          button: [{
            name: '确定',
            callback: function() {
            		var id = this.content.$("#id").val();
            		var productName = this.content.$("#productName").val();
            		var productTypeName = this.content.$("#productTypeName").val();
            		var userIdOfCustomer = this.content.$("#userIdOfCustomer").val();
            		var isNeedBatchNo = this.content.$("#isNeedBatchNo").val();
            		var sku = this.content.$("#sku").val();
            		var warehouseSku = this.content.$("#warehouseSku").val();
            		var model = this.content.$("#model").val();
            		var volume = this.content.$("#volume").val();
            		var customsWeight = this.content.$("#customsWeight").val();
            		var currency = this.content.$("#currency").val();
            		var customsValue = this.content.$("#customsValue").val();
            		var taxCode = this.content.$("#taxCode").val();
            		var origin = this.content.$("#origin").val();
            		var remark = this.content.$("#remark").val();
            		var that = this; 
 	             $.post(baseUrl + '/products/updateProductById.do', {
 	            	id:id,
 	            	productName:productName,
 	            	productTypeName:productTypeName,
 	            	userIdOfCustomer:userIdOfCustomer,
 	            	isNeedBatchNo:isNeedBatchNo,
 	            	sku:sku,
 	            	warehouseSku:warehouseSku,
 	            	model:model,
 	            	volume:volume,
 	            	customsWeight:customsWeight,
 	            	currency:currency,
 	            	customsValue:customsValue,
 	            	taxCode:taxCode,
 	            	origin:origin,
 	            	remark:remark
 	             },
 	             function(msg) {
 	            	if(msg.status == '1'){
 	            		parent.$.showShortMessage({msg:msg.message,animate:false,left:"43%"});
 	            		that.close();
 	            		grid.loadData();	 //保存成功,重新加载产品
 	            		
 	            	}
					if(msg.status =='0'){
						parent.$.showShortMessage({msg:msg.message,animate:false,left:"43%"});
				
					}
 	            },"json");
 	    		return false; 
            }
          }],
          cancel: true
        });  
}
   
//打印列表sku
function printListSkuBarcode(){
    var contentArr = [];
    contentArr.push('<div id="changeContent" style="padding:10px;width: 300px;">');
    contentArr.push('   <div class="pull-left" style="width: 100%">');
    contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 15px;" type="radio" checked="checked" value="selected" id="selected">');
    contentArr.push('       <label class="pull-left" style="margin-left: 5px" for="selected">打印选中</label>');
    contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" value="all" id="all">');
    contentArr.push('       <label class="pull-left" style="margin-left: 5px;" for="all">打印当前页</label>');
    contentArr.push('   </div>');
    contentArr.push('   <div class="pull-left" style="width: 100%;margin-top:10px;height:35px;">');
    contentArr.push('       <label class="pull-left" style="margin-left: 15px;" for="all">每个SKU打印份数</label>');
    contentArr.push('       <input class="pull-left" name="quantity" value="1"  style="margin-left: 10px;height:15px;width:80px" type="text" id="quantity">');
    contentArr.push('   </div>');
    contentArr.push('</div>');
    contentArr.push('<div style="color: #ff0000;margin-left: 25px;margin-top:5px;">注：请使用大于等于80*25的标签纸</div>');
    var contentHtml = contentArr.join('');
	$.dialog({
  		lock: true,
  		max: false,
  		min: false,
  		title: '打印SKU条码',
  	     width: 340,
         height: 120,
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
	            var ids = "";
            	for ( var i = 0; i < row.length; i++) {
            		ids += row[i].id+",";
				}
            	var quantity =  parent.$("#quantity").val();
            	if(ids!=""){
            		//打印SKU,新建标签页
    			    var url = baseUrl+'/products/print/printSkuBarcode.do?ids='+ids+'&quantity='+quantity;
      			  	window.open(url);
            	}
  			}
  		},
  		{
  			name: '取消'
  		}]
  	})
}

//打印输入的sku
function printSkuBarcode(){
    var contentArr = [];
    contentArr.push('<div id="changeContent" style="padding:10px;width: 340px;">');
    contentArr.push('   <div class="pull-left" style="width: 100%;margin-top:5px;line-height:10px;">');
    contentArr.push('       <label class="pull-left" style="margin-left: 15px;" for="all">打印指定SKU条码</label>');
    contentArr.push('       <input class="pull-left" name="sku" style="margin-left: 10px;width:120px;height:15px;" type="text" id="sku">');
    contentArr.push('   </div>');
    contentArr.push('   <div class="pull-left" style="width: 100%;margin-top:10px;height:35px;">');
    contentArr.push('       <label class="pull-left" style="margin-left: 15px;" for="all">每个SKU打印份数</label>');
    contentArr.push('       <input class="pull-left" name="quantity" value="1" style="margin-left: 10px;height:15px;width:80px" type="text" id="quantity">');
    contentArr.push('   </div>');
    contentArr.push('</div>');
    contentArr.push('<div style="color: #ff0000;margin-left: 25px;margin-top:5px;">注：请使用大于等于80*25的标签纸</div>');
    var contentHtml = contentArr.join('');
	$.dialog({
  		lock: true,
  		max: false,
  		min: false,
  		title: '打印SKU条码',
  	     width: 380,
         height: 120,
  		content: contentHtml,
  		button: [{
  			name: '确认',
  			callback: function() {
                var sku = parent.$("#sku").val();
	            if(sku == null || sku ==""){
	                parent.$.showShortMessage({msg:"请输入产品SKU",animate:false,left:"45%"});
	                return false;
	            }
	            var quantity =  parent.$("#quantity").val();
            	//打印SKU,新建标签页
			    var url = baseUrl+'/warehouse/print/printSkuBarcode.do?sku='+sku+'&quantity='+quantity;
  			  	window.open(url);
  			}
  		},
  		{
  			name: '取消'
  		}]
  	})
}