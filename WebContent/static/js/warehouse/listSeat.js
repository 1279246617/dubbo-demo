 //批量打印
function printBatch(){
	  var contentArr = [];
	    contentArr.push('<div id="changeContent" style="padding:10px;width: 240px;">');
	    contentArr.push('   <div class="pull-left" style="width: 100%">');
	    contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" checked="checked" value="selected" id="selected">');
	    contentArr.push('       <label class="pull-left" style="margin-left: 5px" for="selected">打印选中</label>');
	    contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" value="all" id="all">');
	    contentArr.push('       <label class="pull-left" style="margin-left: 5px;" for="all">打印当前页</label>');
	    contentArr.push('   </div>');
	    contentArr.push('</div>');
	    contentArr.push('<div style="color: #ff0000;margin-left: 40px;">注：请使用大于等于100*60标签纸打印</div>');
	    var contentHtml = contentArr.join('');
		$.dialog({
	  		lock: true,
	  		max: false,
	  		min: false,
	  		title: '打印货位条码',
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
		            var seatIds = "";
	            	for ( var i = 0; i < row.length; i++) {
	            		seatIds += row[i].id+",";
					}
	            	if(seatIds!=""){
	    			    var url = baseUrl+'/warehouse/print/printSeatCode.do?seatIds='+seatIds;
	      			  	window.open(url);
	            	}
	  			}
	  		},
	  		{
	  			name: '取消'
	  		}]
	  	});
}
//打印
function print(id){
	  var url = baseUrl+'/warehouse/print/printSeatCode.do?seatIds='+id;
		  window.open(url);
}

function addShelf(){
	   $.dialog({
          lock: true,
          title: '添加货架',
          width: '550px',
          height: '430px',
          content: 'url:' + baseUrl + '/warehouse/shelves/addShelf.do',
          button: [{
            name: '确定',
            callback: function() {
 	             var warehouseId = this.content.$("#warehouseId").val();
 	             //货架类型 G/B
 	             var shelfType = this.content.$("#shelfType").val();
 	             var shelfTypeName = this.content.$("#shelfTypeName").val();
 	             var shelfNoStart = this.content.$("#shelfNoStart").val();
 	         	 var shelfNoEnd = this.content.$("#shelfNoEnd").val();
 	          	 var start = this.content.$("#start").val();
 	           	 var end = this.content.$("#end").val();
 	           	 var rows = this.content.$("#rows").val();
 	           	 var cols= this.content.$("#cols").val();
 	           	 var businessType = this.content.$("#businessType").val();
 	           	 
 	           	$("#addButton").attr("disabled","disabled");
 	         	 var remark = this.content.$("#remark").val();
 	             $.post(baseUrl + '/warehouse/shelves/saveAddShelf.do', {
 	            	warehouseId:warehouseId,
 	            	shelfTypeName:shelfTypeName,
 	            	shelfType:shelfType,
 	            	start:start,
 	            	end:end,
 	            	rows:rows,
 	            	cols:cols,
 	            	shelfNoStart:shelfNoStart,
 	            	shelfNoEnd:shelfNoEnd,
 	            	remark:remark,
 	            	businessType:businessType
 	             },
 	             function(msg) {
 	            	if(msg.status == '1'){
 	            		parent.$.showDialogMessage(msg.message,null,null);
 	            		gridShelf.loadData();	 //保存成功,重新加载货架,但不重新加载货位,避免打乱货位界面
 	            		
 	            	}
					if(msg.status =='0'){
						parent.$.showDialogMessage(msg.message,null,null);
					}
					$("#addButton").removeAttr("disabled");
 	             },"json");
            }
          }],
          cancel: true
        });
}


function deleteShelf(){
	alert("删除货架");
}
function updateShelf(id){
	alert("更新货架"+id);
}

//点击货架上的显示货位
function showSeat(shelfId){
	$("#shelfId").val(shelfId);
	btnSearch("#searchform",grid);
}

//显示货物
function listItemShelfInventory(id){
	var contentArr = [];
	contentArr.push('<div style="height:340px;overflow:auto; ">');
	contentArr.push('<table class="table" style="width:749px">');
	contentArr.push('<tr><th>商品SKU</th><th>商品条码</th><th>商品名称</th><th>实际数量</th><th>可用数量</th></tr>');
	$.ajax({ 
        type : "post", 
        url :baseUrl + '/warehouse/shelves/getSeatItemInventory.do', 
        data : "seatId="+id, 
        async : false, 
        success : function(msg){ 
        	msg = eval("(" + msg + ")");
			$.each(msg,function(i,e){
			  	contentArr.push('<tr>');
			  	contentArr.push('<td>'+e.skuNo+'</td>');	
			  	contentArr.push('<td>'+e.sku+'</td>');
        		contentArr.push('<td>'+e.skuName+'</td>');
        		contentArr.push('<td>'+e.quantity+'</td>');
        		contentArr.push('<td>'+e.availableQuantity+'</td>');
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
  		title: '货位的货物详情',
  		width: 750,
  		height: 350,
  		content: contentHtml,
  		button: [{
  			name: '关闭',
  			callback: function() {
				
  			}
  		}]
  	});
}