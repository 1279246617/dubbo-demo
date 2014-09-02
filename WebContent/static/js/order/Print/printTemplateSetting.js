/**
 * Created by mac on 14-4-3.
 */
//搜索
$("#btn_search").click(function(){
    btnSearch("#searchform",lhp.grid);
});

//添加
lhp.addPrintTemplate = function(){
    $.dialog({
    		lock: true,
    		max: false,
    		min: false,
    		title: '添加报关预设',
    		width: 360,
    		height: 240,
    		content: 'url:'+lhp.ctxpath+'/order.CustomsPresettings/addCustomsPresetting',
    		button: [{
    			name: '确认',
    			callback: function() {
                    var that = this;
                    //验证
                    var requiredOpt = this.content.document.getElementsByClassName("requiredOpt");
                    var flag=false;
                    $.each(requiredOpt,function(i,e){
                        var thisVal = $.trim($(e).val());
                        if(!thisVal){
                            var msg=$(e).attr("msgname");
                            parent.$.showShortMessage({msg:"请填写"+msg,animate:false,left:'45%',top:'40%'});
                            flag =true;
                            return false;
                        }
                    });
                    if(flag){
                        return false;
                    }
                    //单价、重量
                    var requiredNum = this.content.document.getElementsByClassName("requiredNum");
                    $.each(requiredNum,function(i,e){
                        var val = $(e).val();
                        if($.trim(val) != ""){
                            if(isNaN(val)){
                                var msg=$(e).attr('msgname');
                                parent.$.showShortMessage({msg:msg+"必需是数字",animate:false,left:"45%"});
                                flag=true;
                                return false;
                            }
                        }else{
                            flag=true;
                            return false;
                        }
                    });
                    if(flag){
                        return false;
                    }

                    //提交form
                    var form = this.content.document.getElementById("form1");
                    $(form).ajaxSubmit({
                        dateType:'text',
                        success:function(msg){
                            if(msg == 1){ //成功
                                lhp.grid.loadData()
                            }else{
                            	parent.$.showShortMessage({msg:""+msg,animate:false,left:'45%',top:'40%'});
                            	return false;
                            }
                            that.close();
                        }
                    });
                    return false;
    			}
    		},
    		{
    			name: '取消'
    		}]
    	})
};

//修改
lhp.updatePrintTemplate = function(skuCustomsId){
    $.dialog({
    		lock: true,
    		max: false,
    		min: false,
    		title: '修改报关预设',
    		width: 360,
    		height: 240,
    		content: 'url:'+lhp.ctxpath+'/order.CustomsPresettings/updateCustomsPresetting?skuCustomsId='+skuCustomsId,
    		button: [{
    			name: '确认',
    			callback: function() {
                    var that = this;
                    //验证
                    var requiredOpt = this.content.document.getElementsByClassName("requiredOpt");
                    var flag=false;
                    $.each(requiredOpt,function(i,e){
                        var thisVal = $.trim($(e).val());
                        if(!thisVal){
                            var msg=$(e).attr("msgname");
                            parent.$.showShortMessage({msg:"请填写"+msg,animate:false,left:'45%',top:'40%'});
                            flag =true;
                            return false;
                        }
                    });
                    if(flag){
                        return false;
                    }
                    //单价、重量
                    var requiredNum = this.content.document.getElementsByClassName("requiredNum");
                    $.each(requiredNum,function(i,e){
                        var val = $(e).val();
                        if($.trim(val) != ""){
                            if(isNaN(val)){
                                var msg=$(e).attr('msgname');
                                parent.$.showShortMessage({msg:msg+"必需是数字",animate:false,left:"45%"});
                                flag=true;
                                return false;
                            }
                        }else{
                            flag=true;
                            return false;
                        }
                    });
                    if(flag){
                        return false;
                    }
                    //提交form
                    var form = this.content.document.getElementById("form1");
                    $(form).ajaxSubmit({
                        dateType:'text',
                        success:function(msg){
                            if(msg == 1){ //成功
                                lhp.grid.loadData()
                            }else{
                            	parent.$.showShortMessage({msg:""+msg,animate:false,left:'45%',top:'40%'});
                            }
                            that.close();
                        }
                    });
                    return false;
    			}
    		},
    		{
    			name: '取消'
    		}]
    	})
};

//删除 单条数据
lhp.delSinglePrintTemplate = function(skuCustomsId){
    if(!skuCustomsId){
        parent.$.showShortMessage({msg:"请至少选择一项",animate:false});
        return false;
    }
    $.dialog({
    		lock: true,
    		max: false,
    		min: false,
    		title: '提示',
    		width: 260,
    		height: 60,
    		content: '<div style="margin:10px;">您确认删除1条记录吗？</div>',
    		button: [{
    			name: '确认',
    			callback: function() {
    				$.getJSON(lhp.ctxpath+"/order.CustomsPresettings/deleteCustomsPresetting?skuCustomsId="+skuCustomsId,function(msg) {
    					lhp.grid.loadData()
    				});
    			}
    		},
    		{
    			name: '取消'
    		}]
    	})
};


// 批量删除
lhp.delBatchPrintTemplate = function(){
    var row = lhp.grid.getSelectedRows();
    if(row.length < 1){
        parent.$.showShortMessage({msg:"请最少选择一条数据",animate:false});
        return false;
    }
    $.dialog({
    		lock: true,
    		max: false,
    		min: false,
    		title: '提示',
    		width: 260,
    		height: 60,
    		content: "<div style='margin:10px;'>您确认删除"+row.length+"条记录吗？</div>",
    		button: [{
    			name: '确认',
    			callback: function() {
    				for ( var i = 0; i < row.length; i++) {
                		$.post(lhp.ctxpath+'/order.CustomsPresettings/deleteCustomsPresetting',{skuCustomsId: row[i].id},function(msg){

                        });
                		lhp.grid.loadData()
    				}
    			}
    		},
    		{
    			name: '取消'
    		}]
    	})
};
