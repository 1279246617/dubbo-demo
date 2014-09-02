/**
 * Created by JetBrains WebStorm.
 * User: lihaipeng
 * Date: 14-1-1
 * Time: 下午11:30
 * To change this template use File | Settings | File Templates.
 */
//init
$(init);
function init(){
    //国家自动完成
    $("#country").typeahead({
        source: function(query, callback){
            $.get(orderAdd.ctxpath+'/system.Countrys/search', {
                keyword: query
            }, function(data){
                callback(data);
            });
        },
        updater:function(item){
            var itemArr = item.split("-");
            $("#countryCode").val(itemArr[0]);
            return itemArr[2];
        }
    });
    
    //渠道自动完成
    $("#shipwayName").typeahead({
        source: function(query, callback){
            $.get(orderAdd.ctxpath+'/order.Orders/searchShipway', {
                keyword: query
            }, function(data){
                callback(data);
            });
        },
        items: 10,
        highlighter:function(item){
        	var itemArr = item.split("-");
        	
        	var s0 = itemArr[0]
        	var s1 = itemArr[1]
        	var s2 = itemArr[2];
        	
        	var newS2 = ("<font color='red'>"+s2+"</font>");
        	var newItem = s0.replace(s2,newS2)+"-"+s1.replace(s2,newS2);
        	return newItem;
        },
        updater:function(item){
            var itemArr = item.split("-");
            $("#shipwayCode").val(itemArr[0]);
            return itemArr[1];
        }
    });
    
    //报关信息自动完成
    orderAdd.skuAutoCompOpt = {
        source: function(query, callback){
            $.get(orderAdd.ctxpath+'/order.OrderCustomss/searchSku', {
                keyword: query
            }, function(data){
                callback(data);
            });
        },
        updater:function(item){
            var thisDom = this.$element[0];
            var itemArr = item.split("--");
            orderAdd.fillCustomsInfo(thisDom,itemArr[0]);
            return itemArr[0];
        }
    };
    $(".sku").typeahead(orderAdd.skuAutoCompOpt);
    
    //用户是否设置了默认发件人
    if("" != orderAdd.defaultSenderId){
        $("#senderId").val(orderAdd.defaultSenderId);
    }

    //
    $(".salesOrderNo").inputInsideTip(null);
    //给报关类型默认设为其它
    $("#customsType").val(5);
}

//用户在按回车键的时候实现Tab键切换输入焦点
$("input[type='text']").live("keydown",function(event){
    var e = event || window.event;
    if(e.keyCode == 13 && !this.readonly && !this.disabled){
        var $allInp=$("input[type='text']");
        var nextIndex = $allInp.index(this)+1;
        $("input[type='text']:eq("+nextIndex+")").focus();
    }
});

//sku的blur事件
orderAdd.skuBlurEven = function(thisDom){
    var sku = $(this).val();
    if($.trim(sku) == ""){
        return ;
    }
    orderAdd.fillCustomsInfo(thisDom,sku);
};
//填充报关信息
orderAdd.fillCustomsInfo = function(thisDom,sku){
   $.post(orderAdd.ctxpath+'/order.OrderCustomss/getCustomsBySku',
        {
            sku:sku
        },function(result){
            if(result.status == 1){
                var $parentDom = $(thisDom).parents(".customs-per-content");
                $parentDom.find(".customsDescription").val(result.customsDescription);
                $parentDom.find(".customsValue").val(result.customsValue).attr("title",result.customsValue);
                $parentDom.find(".customsWeight").val(result.customsWeight);
            }
       });
};

//给国家名称自动大写
orderAdd.changeToUpperCase = function(){
    $("#country").val($("#country").val().toUpperCase());
};

//添加一行申报信息
orderAdd.addCustoms = function(thisDom){
    var $parent = $(thisDom).parents(".customs-per");
    var customsContentHtml = $parent.find(".customs-per-content").html();
    var newHtmlArr = [];
    newHtmlArr.push('<div class="pull-left mt5 customs-per">');
    newHtmlArr.push('   <div class="pull-left customs-per-content">');
    newHtmlArr.push('       '+customsContentHtml);
    newHtmlArr.push('   </div>');
    newHtmlArr.push('   <div class="pull-left ml5 customs-per-btn">');
    newHtmlArr.push('       <a class="btn btn-small btn-danger btn-del" onclick="orderAdd.delCustoms(this);" title="删除">-</a>');
    newHtmlArr.push('   </div>');
    newHtmlArr.push('</div>');

    $parent.parents(".customs-all").append(newHtmlArr.join(''));

    $(".sku").typeahead(orderAdd.skuAutoCompOpt);
};

//删除一行报关信息
orderAdd.delCustoms = function(thisDom){
    $(thisDom).parents(".customs-per").remove();
};

//验证邮箱
$("#email").on("blur",function(){
    if($.trim(this.value)==""){
        return false;
    }
    if(!isEmail(this.value)){
        parent.$.showShortMessage({msg:"邮箱格式不正确",animate:false,left:"45%"});
    }
});

//绑定申报数量和单价事件，计算总价格
$("#customsQuantity").bind("blur",function(){
    //必须为数字
    var qua = $(this).val();
    if($.trim(qua) != ""){
        if(!isInteger(qua)){
           parent.$.showShortMessage({msg:"数量必需是正整数",animate:false,left:"45%"});
           return false;
        }
    }else{
        return false;
    }
    //单价
    var val = $("#customsValue").val();
    if($.trim(val) != ""){
        if(isNaN(val)){
            parent.$.showShortMessage({msg:"单价必需是数字",animate:false,left:"45%"});
            return false;
        }
    }else{
        return false;
    }
    val = getPrecision(val,2);
    $("#customsValue").text(val);

    var totalValue = qua * (100*val)/100;
    $("#customsValueTotal").text(totalValue);
});
$("#customsValue").bind("blur",function(){
    //必须为数字
    var val = $(this).val();
    if($.trim(val) != ""){
        if(isNaN(val)){
            parent.$.showShortMessage({msg:"单价必需是数字",animate:false,left:"45%"});
            return false;
        }
    }else{
        return false;
    }
    val = getPrecision(val,2);
    $(this).val(val);
    //数量
    var qua = $("#customsQuantity").val();
    if($.trim(qua) != ""){
        if(!isInteger(qua)){
           parent.$.showShortMessage({msg:"数量必需是正整数",animate:false,left:"45%"});
           return false;
        }
    }else{
        return false;
    }

    var totalValue = qua * (100*val)/100;
    $("#customsValueTotal").text(totalValue);
});

//

//是否是正整数
var isInteger = function(num){
    return /^[1-9]+[0-9]*$/.test(num);
};
//邮箱格式是否正确
var isEmail = function(email){
    return /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(email);
};

//取精度  num需要取精度的数字  n精度（小数点后几位）
var getPrecision = function(num,n){
    return Math.round(num*Math.pow(10,n))/Math.pow(10,n);
};

$(function(){
    $("#selectSender").click(function(){
        if($("#sender").css("display")=='none'){
            $("#sender").slideDown();
            $("#selectSender i").attr("class","icon-chevron-up");
        }
        else{
            $("#sender").slideUp();
            $("#selectSender i").attr("class","icon-chevron-down");
        }
    });
});

function shipwayChange(){
	var shipwayCode = $("#shipwayCode").val();
	//判断是否有退货和保险服务标识
    $.ajax({
        type:'post',
        url:orderAdd.ctxpath+'/order.Orders/getShipwayisCanCancelAndInSureByCode',
        async:false,
        data:{shipwayCode:shipwayCode},
        success:function(shipway){
        	if(shipway.status =='1'){
        		var inSure = shipway.inSure;
            	var isCanCancel = shipway.isCanCancel;
            	if(inSure == "Y"){
            		$("#inSure").removeAttr("disabled"); 
            	}else{
            		$("#inSure").attr("disabled","disabled"); 
            	}
            	if(isCanCancel == "Y"){
            		$("#cancel").removeAttr("disabled"); 
            	}else{
            		$("#cancel").attr("disabled","disabled"); 
            	}
        	}
        }
    });
    
	if(shipwayCode != "EAU"){
		$("#city").removeClass("wid152");
		$("#city").addClass("wid200");
		$("#serarchAupost").hide();
		//国家清除只能到澳洲
		$("#country").removeAttr("disabled");
		//报关信息清除必填
		$("#f_customsInfo input").each(function(){$(this).removeClass("required");});
		$("#f_customsInfo .requiredMark").each(function(){$(this).hide();});
		return;				
	}
	$("#city").removeClass("wid200");
	$("#city").addClass("wid152");
	$("#serarchAupost").show();
	//国家只能到澳洲
	$("#countryCode").val("AU");
	$("#country").val("AUSTRALIA");
	$("#country").attr("disabled","disabled")
	//报关信息变成必填
	$("#f_customsInfo input").each(function(){
		if($(this).attr("class").indexOf("sku")<0 && $(this).attr("class").indexOf("hsCode")<0){
			$(this).addClass("required");	
		};
	});
	$("#f_customsInfo .requiredMark").each(function(){$(this).show();});
}

function shipwayCode1Change(){
	 $("#shipwayCode").val($("#shipwayCode1").val());
	 $("#shipwayName").val("");
	 shipwayChange();
}
	
//澳洲邮政搜索
function searchAuPost(){
	 $.dialog({
 		lock : false,
 		title : '查询',
 		width : '460px',
 		height : '330px',
 		zIndex:2999,
 		content : 'url:' + orderAdd.ctxpath+"/order.Orders/searchAupost",
 		button : [{
 			name : '确定',
 			callback : function() {
                var postalCode = this.content.document.getElementById("postalCode");
                var city = this.content.document.getElementById("city");	
                var state = this.content.document.getElementById("state");	
                var portCode = this.content.document.getElementById("portCode");
                if(postalCode == null){
                	return true;
                }
                //赋值到本页面
                $("#postcode").val($(postalCode).val());
                $("#city").val($(city).val());
                $("#province").val($(state).val());
 			}
 		}],
 		cancel : true
 	});
}

function selectClick(){
	if($("#shipwayName").is(":visible")){
		$("#shipwayName").hide();
		$("#shipwayCode1").show();
		$("#selectChange").html("search");
	}else{
		$("#shipwayName").show();
		$("#shipwayCode1").hide();
		$("#selectChange").html("select");
	}
}

