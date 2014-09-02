//添加现金收款
function addCashPayment() {
	$.dialog({
				lock : true,
				title : '现金收款',
				width : 700,
				height : 550,
				content: 'url:'+ctxpath+'/finance.CompanyPayments/addCashPay',
				button : [{
					name : '确定',
					callback : function() {
						var that = this;
	                    var form = this.content.document.getElementById("paymentForm");
	                    $(form).ajaxSubmit({
	                        dateType:'text',
	                        success:function(msg){
	                        	parent.$.showShortMessage({msg:msg.message,animate:false,left:'45%',top:'40%'});
	                            if(msg.status != 1){  
	                            	return false;
	                            }
	                            that.close();
	                        }
	                    });
	        			return  false;
					}
				},{
					name : '取消',
					callback : function() {
						
					}
				}],
			});
}

//导入成本价格
function inportCostPrice() {
	$.dialog({
				lock : true,
				title : '导入成本价',
				width : 400,
				height : 350,
				content: 'url:'+ctxpath+'/sale.CostPriceManage/inportCostPrice',
				button : [{
					name : '确定',
					callback : function() {
						
					}
				},{
					name : '取消',
					callback : function() {
						
					}
				}],
			});
}
