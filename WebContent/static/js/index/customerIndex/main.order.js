//添加单个订单
var addOrder = function(){
    $.dialog({
        lock:true,
		title: '添加订单',
		width: 1000,
		height: 550,
		content: 'url:'+ctxpath+'/order.Orders/addOrder',
		button: [{
					name: '保存并继续',
					callback: function() {
                        var thisContent = this.content;
                        //验证收件人必填项
                        var isSubmit = true;
                        thisContent.$("#f_addressInfo .required").each(function(){
                            var $that = $(this);
                            if(!$.trim($that.val())){
                                var txt = $that.parent().prev().text().replace(":","");
                                parent.$.showShortMessage({msg:"请填写"+txt,animate:false,left:"45%"});
                                isSubmit= false;
                            }
                        });
                        if(!isSubmit){
                        	return false;
                        }
                        thisContent.$("#f_customsInfo .required").each(function(){
                            var $that = $(this);
                            if(!$.trim($that.val())){
                                var txt = $that.parent().prev().text().replace(":","");
                                parent.$.showShortMessage({msg:"请填写"+txt,animate:false,left:"45%"});
                                isSubmit= false;
                            }
                        });
                        if(!isSubmit){
                        	return false;
                        }
                        //验证国家，如果code的input框无值，则不允许提交
                        var countryCode = thisContent.$("#countryCode").val();
                        if(!countryCode){
                            $.showShortMessage({msg:"国家填写有误",animate:false,left:"45%"});
                            return false;
                        }
                        //验证海关申报信息
                        if(thisContent.$("#tb_customs tr").length==1){
                            $.showShortMessage({msg:"请填写海关申报信息",animate:false,left:"45%"});
                            return false;
                        }
                        var that = this;
                        var $form = this.content.$("#form1");
                        $form.ajaxSubmit({
                             dataType: 'JSON',
                             success: function(msg){
                                  if(msg.status == '1'){ //成功
                                      $.showShortMessage({msg:"保存成功",animate:false,bgColor:"#008800",left:"45%"});
                                      $($form)[0].reset()
                                      refreshLeftMenu();//刷新左侧菜单数量
                                  }else{//失败
                                      $.showShortMessage({msg:msg.message,animate:false,left:"45%"});
                                  }
                             }
                        });
                        
                        return false;
					}
				},
				{
					name: '保存并关闭',
					callback: function() {
                        var thisContent = this.content;
                        //验证收件人必填项
                        var isSubmit = true;
                        thisContent.$("#f_addressInfo .required").each(function(){
                            var $that = $(this);
                            if(!$.trim($that.val())){
                                var txt = $that.parent().prev().text().replace(":","");
                                parent.$.showShortMessage({msg:"请填写"+txt,animate:false,left:"45%"});
                                isSubmit= false;
                            }
                        });
                        if(!isSubmit){
                        	return false;
                        }
                        thisContent.$("#f_customsInfo .required").each(function(){
                            var $that = $(this);
                            if(!$.trim($that.val())){
                                var txt = $that.parent().prev().text().replace(":","");
                                parent.$.showShortMessage({msg:"请填写"+txt,animate:false,left:"45%"});
                                isSubmit= false;
                            }
                        });
                        if(!isSubmit){
                        	return false;
                        }
                        //验证国家，如果code的input框无值，则不允许提交
                        var countryCode = thisContent.$("#countryCode").val();
                        if(!countryCode){
                            $.showShortMessage({msg:"国家填写有误",animate:false,left:"45%"});
                            return false;
                        }
                        //验证海关申报信息
                        if(thisContent.$("#tb_customs tr").length==1){
                            $.showShortMessage({msg:"请填写海关申报信息",animate:false,left:"45%"});
                            return false;
                        }
                        var that = this;
                        var $form = this.content.$("#form1");
                        $form.ajaxSubmit({
                             dataType: 'JSON',
                             success: function(msg){
                                  if(msg.status == '1'){ //成功
                                      that.close();
                                      $.showShortMessage({msg:"保存成功",animate:false,bgColor:"#008800",left:"45%"});
                                      refreshLeftMenu();//刷新左侧菜单数量
                                  }else{//失败
                                      $.showShortMessage({msg:msg.message,animate:false,left:"45%"});
                                  }
                             }
                        });
                        return false;
					}
				},
				{
					name: '取消'
				}]
	});
};


//订单同步
var syncOrder = function(){
    $.dialog({
        lock:true,
		title: '订单同步',
		width: 750,
		height: 520,
		content: 'url:'+ctxpath+'/order.Orders/syncOrder',
		button: [{
					name: '确定',
					callback: function() {
                        var thisContent = this.content;
						var sellerAccountId = thisContent.$("#sellerAccountId");
						var timeFrom = thisContent.$("#timeFrom");
						var timeTo = thisContent.$("#timeTo");
						if($(timeFrom).val() == null || $(timeFrom).val() == ''){
							parent.$.showShortMessage({msg:"请选择订单的起始时间!",animate:false});
							return false;
						}
						$.post(ctxpath + '/order.Orders/syncAction', {
							sellerAccountId : $(sellerAccountId).val(),
							timeFrom : $(timeFrom).val(),
							timeTo : $(timeTo).val()
						}, function(msg) {
							if(msg == '1' || msg == ''){
								parent.$.showShortMessage({msg : "正在后台同步订单,请您继续其他操作!",animate : false,bgColor:"#008800",left:'40%',top:'40%'});
							}else{
								parent.$.showShortMessage({msg:msg,animate:false});
							}
						});
						
					}			
				},
				{
					name: '取消'
				}]
	});
};

//订单导入
var batchImportOrderView = null;
var batchImportOrder = function (){
	batchImportOrderView = $.dialog({
		lock:true,
		title: '订单导入',
		width: 750,
		height: 520,
		content: 'url:'+ctxpath+'/order.OrderImport/batchImportOrder',
		button: [{
					name: '确定',
					callback: function() {
						 refreshLeftMenu();//刷新左侧菜单数量
					}			
				},
				{
					name: '取消',
					callback: function() {
					 
					}	
				}]
	});
};
//添加模板
function addTemplate(){
	if(batchImportOrderView!=null){
		batchImportOrderView.close();
	}
	$.dialog({
		lock: true,
        title: '添加模版',
        width: '750px',
        height: '520px',
        content: 'url:'+ctxpath+'/order.OrderImport/addImportTemplate',
        button: [{
			name: '确定',
           	callback: function() {	
	           	var thisContent = this.content;
	           	var addStandardTemplateSpan = thisContent.document.getElementsByName("addStandardTemplateSpan");
				var mapping = "";
				for ( var i = 0; i < addStandardTemplateSpan.length; i++) {
					var span = $(addStandardTemplateSpan[i]);
					if (span.text()==null || span.text()=="") {
						continue;
					}
					//循环获取客户模版值
					var customer = "";
					var ccids = span.text().split(" ");
					for (var j = 0; j < ccids.length; j++) {
						var checkbox = thisContent.$("#c_checkbox_" + ccids[j]); 
						var ccvalue = $(checkbox).val();
						if (ccvalue == null|| ccvalue == '') {
							continue;
						}
						customer += (ccvalue + ";");
					}
					if(customer!=null && customer!=''){
						mapping += "["+customer+"="+span.attr("value")+"]";
					}
				}
				//获取模版名称
				var templateId = thisContent.$("#templateId").text();
				$.post(ctxpath + '/order.OrderImport/saveAddImportTempate', {
					templateId : templateId,
					mapping : mapping
				}, function(msg) {
					if(msg.status == '1'){
						parent.$.showShortMessage({msg : msg.message,animate : false,bgColor:"#008800",left:'45%',top:'40%'});
					}
				});
				this.close();
           		batchImportOrder();
           		return false;
			}
       },{
			name: '返回',
           	callback: function() {	
           		this.close();
           		batchImportOrder();
           		return false;
           	}
        }],
       cancel: true
	});
}

//修改订单模板
function modTemplate(){
	if(batchImportOrderView!=null){
		batchImportOrderView.close();
	}	
 	$.dialog({
		lock: true,
      	title: '修改模版',
      	width: '750px',
      	height: '520px',
      	content: 'url:'+ctxpath+'/order.OrderImport/modImportTemplate',
      	button: [{
			name: '确定',
        	callback: function() {	
        		var thisContent = this.content;
	           	var modStandardTemplateSpan = thisContent.document.getElementsByName("modStandardTemplateSpan");
				var mapping = "";
				for ( var i = 0; i < modStandardTemplateSpan.length; i++) {
					var span = $(modStandardTemplateSpan[i]);
					if (span.text()==null || span.text()=="") {
						continue;
					}
					//循环获取客户模版值
					var customer = "";
					var ccids = span.text().split(" ");
					for (var j = 0; j < ccids.length; j++) {
						var checkbox = thisContent.$("#mod_c_checkbox_" + ccids[j]); 
						var ccvalue = $(checkbox).val();
						if (ccvalue == null|| ccvalue == '') {
							continue;
						}
						customer += (ccvalue + ";");
					}
					if(customer!=null && customer!=''){
						mapping += "["+customer+"="+span.attr("value")+"]";
					}
				}
				//获取模版id
				var templateId = thisContent.$("#modTemplateId").val();
				if(templateId==null || templateId == ''){
					this.close();
	           		batchImportOrder();
	           		return false;
				}
				//保存更改
				$.post(ctxpath + '/order.OrderImport/saveModImportTempate', {
					templateId : templateId,
					mapping : mapping
				}, function(msg) {
					if(msg!=null && msg.message!=null){
						parent.$.showShortMessage({msg : msg.message,animate : false,bgColor:"#008800",left:'45%',top:'40%'});	
					}
				});
				this.close();
           		batchImportOrder();
           		return false;
        	}
      	},{
			name: '返回',
           	callback: function() {	
           		this.close();
           		batchImportOrder();
           		return false;
           	}
        }],
       	cancel: true
	});
}
