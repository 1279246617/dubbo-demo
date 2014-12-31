//SKU
function listFirstWaybillsItem(firstWaybillId){
	var contentArr = [];
	contentArr.push('<div style="height:340px;overflow:auto; ">');
	contentArr.push('<table class="table table-condensed" style="width:649px">');
	$.ajax({ 
        type : "post", 
        url :baseUrl + '/warehouse/transport/getFirstWaybillItemByfirstWaybillId.do', 
        data : "firstWaybillId="+firstWaybillId, 
        async : false, 
        success : function(msg){ 
        	msg = eval("(" + msg + ")");
			  	contentArr.push('<tr  class="warning"><th>商品SKU</th><th>商品名称</th><th>商品单价(元)</th><th>规格型号</th><th>净重KG</th><th>数量</th></tr>');
			  	$.each(msg,function(j,ei){
			  		contentArr.push('<tr>');
				  	contentArr.push('<td>'+ei.sku+'</td>');
				  	contentArr.push('<td>'+ei.skuName+'</td>');
	        		contentArr.push('<td>'+(ei.skuUnitPrice/100)+'</td>');
	        		contentArr.push('<td>'+ei.specification+'</td>');
	        		contentArr.push('<td>'+(ei.skuNetWeight/1000)+'</td>');
	        		contentArr.push('<td>'+ei.quantity+'</td>');
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
  		title: '转运小包商品详情',
  		width: 720,
  		height: 340,
  		content: contentHtml,
  		button: [{
  			name: '关闭',
  			callback: function() {
				
  			}
  		}]
  	})
}
