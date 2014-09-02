//财务
var payOnlineWin = null;
function payOnline() {
	payOnlineWin = $.dialog({
				lock : true,
				title : '在线充值',
				width : 600,
				height : 350,
				content: 'url:'+ctxpath+'/finance.CustomerPayments/payOnline',
				button : [{
					name : '确定',
					callback : function() {
						var customerId = this.content.$("#customerId").val();
						var alipayAccountId = this.content.$("#alipayAccountId").val();
						var total = Number(this.content.$("#total").val());
						if(isNaN(total)){
							$.showShortMessage({msg:"充值金额必须是数字!",animate:false,left:"45%"});
							return false;
						}else{
							if(total<0.01){
								$.showShortMessage({msg:"充值金额不能小于0.01!",animate:false,left:"45%"});
								return false;
							}
						}
						$.post(ctxpath + '/finance.CustomerPayments/alipayTo', {
							customerId : customerId,
							alipayAccountId: alipayAccountId,
							total:total
	        			}, function(msg) {
	        				window.open(msg,'福步物流支付宝充值','height=600,width=800,top=60,left=200');
	        			});
					}
				},{
					name : '取消'
				}]
			});
}

var payOfflineWin = null;
function payOffline() {
	payOfflineWin = $.dialog({
				lock : true,
				title : '线下转账',
				width : 700,
				height : 550,
				content: 'url:'+ctxpath+'/finance.CustomerPayments/payOffline',
				button : [{
					name : '确定',
					callback : function() {
						var that = this;
						var thisContent = this.content;
	                    var offlinePaidwayCode = thisContent.$("#offlinePaidwayCode").val();
	                    var paidTime = thisContent.$("#paidTime").val();
	                    var payerAccount = thisContent.$("#payerAccount").val();
	                    var paidAmount = thisContent.$("#paidAmount").val();
	                    var currency = thisContent.$("#currency").val();
	                    var exchangeRate = thisContent.$("#exchangeRate").val();
	                    var rmbAmount = thisContent.$("#rmbAmount").val();
	                    var remark = thisContent.$("#remark").val();
	                    var bankAccountId = thisContent.$("#bankAccountId").val();
	        			$.post(ctxpath + '/finance.CustomerPayments/savePayOffline', {
	        				offlinePaidwayCode : offlinePaidwayCode,
	        				paidTime: paidTime,
	        				payerAccount:payerAccount,
	        				paidAmount:paidAmount,
	        				currency:currency,
	        				exchangeRate:exchangeRate,
	        				rmbAmount:rmbAmount,
	        				remark:remark,
	        				bankAccountId:bankAccountId
	        			}, function(msg) {
	        				if(msg == 1){ //成功
	        					that.close();
	        					parent.$.showShortMessage({msg : "添加线下转账成功,请等待审核!",animate : false,bgColor:"#008800",left:'40%',top:'40%'});
                             }else{//失败
                                 $.showShortMessage({msg:msg,animate:false,left:"45%"});
                             }
	        			});
	        			return  false;
					}
				},{
					name : '取消'
				}]
			});
}
