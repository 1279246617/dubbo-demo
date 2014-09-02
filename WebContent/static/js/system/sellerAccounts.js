// 添加卖家账号
function addSellerAccount() {
	$.dialog({
		lock : true,
		title : '添加账号',
		width : '700px',
		height : '475px',
		content : 'url:' + ctxpath + '/system.SellerAccounts/addSellerAccount',
		button : [{
			name : '确定',
			callback : function() {
				var that = this;
				$(this.content.document).find(".validate").each(function() {
					if ($(this).val().replace(/(^\s*)|(\s*$)/g, "") == "") {
						$(this).next("span").text($(this).attr("msg"));
					} else {
						$(this).next("span").text(" * ");
					}
				});
				var platform = this.content.document.getElementById("platform");
				var ebayNickName = this.content.document.getElementById("ebayNickName");
				var amazonNickName = this.content.document.getElementById("amazonNickName");
				var merchantId = this.content.document.getElementById("merchantId");
				var marketplaceId = this.content.document.getElementById("marketplaceId");
				var amazonSite = this.content.document.getElementById("amazonSite");
				var aliexpressNickName = this.content.document.getElementById("aliexpressNickName");
				
				//判断账号类型
				if($(platform).val() == ''){
					return false;
				}
				//校验卖家帐号昵称
				if($(platform).val() == 'EBAY'){
					if($(ebayNickName).val() == ''){
						return false;
					}
				}else if($(platform).val() == 'AMAZON'){
					if($(amazonSite).val() == ''||$(amazonNickName).val() == ''||$(merchantId).val() == ''||$(marketplaceId).val() == ''){
						return false;
					}
				}else if($(platform).val() == 'SMT'){
					if($(aliexpressNickName).val() == ''){
						return false;
					}
				}
				
				var submit = this.content.document.getElementById("submit");
				// 防止重复点击
				if($(submit).val() == "1"){
					return false;
				}else{
					$(submit).val("1");
				}
				$.post(ctxpath + '/system.SellerAccounts/saveSellerAccount', {
						platform : $(platform).val(),
						ebayNickName : $(ebayNickName).val(),
						amazonNickName : $(amazonNickName).val(),
						merchantId : $(merchantId).val(),
						marketplaceId : $(marketplaceId).val(),
						amazonSite : $(amazonSite).val(),
						aliexpressNickName : $(aliexpressNickName).val()
				}, function(msg) {
					if(msg == '1' || msg == ''){
						that.close();
						grid.loadData();
					}else{
						$(submit).val("0");
						parent.$.showShortMessage({msg:msg,animate:false});
					}
				});
				return false;
			}
		}],
		cancel : true
	});
}


//删除选择账号
function delSellerAccount(){
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
        content: '您确认删除 '+row.length+' 个卖家账号吗？',
        button:[{name: '确认',
            callback: function (){
            	for ( var i = 0; i < row.length; i++) {
            		$.post(ctxpath + '/system.SellerAccounts/delSellerAccount',{sellerAccountId: row[i].id},function(msg){
            			
                    });
				}
            	grid.loadData();
            }},{name: '取消'}]
    });
}


//单个删除
function delSellerAccountByRowId(accountId){
	$.dialog({
        lock: true,
        max:false,
        min:false,
        title:'提示',
        width:260,
        height:60,
        content: '您确认删除吗？',
        button:[{name: '确认',
            callback: function (){
                $.post(ctxpath + '/system.SellerAccounts/delSellerAccount',{sellerAccountId:accountId},function(msg){
                    if(msg == '1'){
                        grid.loadData();
                    }else{
                    	 parent.$.showShortMessage({msg:"删除失败!",animate:false});
                    }
                });
            }},{name: '取消'}]
    });
}

function reauthorization(sellerAccountId){
	$.dialog({
		lock : true,
		title : '重新授权',
		width : '700px',
		height : '475px',
		content : 'url:' + ctxpath + '/system.SellerAccounts/reauthorization?sellerAccountId='+sellerAccountId,
		button : [{
			name : '确定',
			callback : function() {
				var that = this;
				$(this.content.document).find(".validate").each(function() {
					if ($(this).val().replace(/(^\s*)|(\s*$)/g, "") == "") {
						$(this).next("span").text($(this).attr("msg"));
					} else {
						$(this).next("span").text(" * ");
					}
				});
				var platform = this.content.document.getElementById("platform");
				var merchantId = this.content.document.getElementById("merchantId");
				var marketplaceId = this.content.document.getElementById("marketplaceId");
				var amazonSite = this.content.document.getElementById("amazonSite");
				var ebayNickName = this.content.document.getElementById("ebayNickName");
				var amazonNickName = this.content.document.getElementById("amazonNickName");
				var aliexpressNickName = this.content.document.getElementById("aliexpressNickName");
				
				//判断账号类型
				if($(platform).val() == ''){
					return false;
				}
				//校验卖家帐号昵称
				if($(platform).val() == 'EBAY'){
					if($(ebayNickName).val() == ''){
						return false;
					}
				}else if($(platform).val() == 'AMAZON'){
					if($(amazonSite).val() == ''||$(amazonNickName).val() == ''||$(merchantId).val() == ''||$(marketplaceId).val() == ''){
						return false;
					}
				}else if($(platform).val() == 'SMT'){
					if($(aliexpressNickName).val() == ''){
						return false;
					}
				}
				$.post(ctxpath + '/system.SellerAccounts/saveReauthorization', {
						sellerAccountId:sellerAccountId,
						platform : $(platform).val(),
						ebayNickName : $(ebayNickName).val(),
						amazonNickName : $(amazonNickName).val(),
						merchantId : $(merchantId).val(),
						marketplaceId : $(marketplaceId).val(),
						amazonSite : $(amazonSite).val(),
						aliexpressNickName : $(aliexpressNickName).val()
				}, function(msg) {
					if(msg == '1' || msg == ''){
						that.close();
						grid.loadData();
					}else{
						parent.$.showShortMessage({msg:msg,animate:false});
					}
				});
				return false;
			}
		}],
		cancel : true
	});
}
