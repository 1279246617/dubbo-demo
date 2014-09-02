var adjustHeight = function() {
	var wh = $(window).height();
	var hh = $("#header").height();
	$("#framecenter").ligerTab({height : wh - hh});
	var tab = $("#framecenter").ligerGetTabManager();
	tab.setHeight(wh - hh);
    //订单分页高度
    $("iframe[name^='status']").each(function(i,e){
        var orderList = e.contentWindow.document.getElementById("orderList");
        if(orderList){
            orderList.style.height = parseInt(wh)-190+"px";
        }
    });
};

//浏览器窗口高度 变化时 高度自适应
$(window).resize(function() {
	setTimeout(adjustHeight,0);
});

//init
$(function(){
	//布局
	$("#layoutCenter").ligerLayout({ leftWidth: 200,heightDiff:-43});
	var height = $(".l-layout-center").height();
	//Tab
	$("#framecenter").ligerTab({ height: height });
	$("#leftMenu").css({width:"200px"});
	//TAB管理器
	tab = $("#framecenter").ligerGetTabManager();
	adjustHeight();
	refreshLeftMenu();
	leftMenuAddToTab();
	topMenuAddToTab();
	//设置 默认展开订单状态  orderStatusConfirm 来自 index.html-> 来自后台 需要改变默认展示订单只需修改后台
    openOrderStatus(orderStatusConfirm);
    
//	$.showMessage("欢迎光临！您在使用系统的过程如果有什么问题或好的建议，欢迎提出，谢谢！");
});

var tab = null;
var leftMenuAddToTab = function(){
    $("#leftMenu li").live('click', function(event) {
        var e = event || window.event;
        var target = e.target || e.srcElement;
        var className = target.className;
        //如果class包含i-toggle，则为展开操作，阻止触发
        if(className.indexOf("i-toggle") > -1){
            return false;
        }
        tab = $("#framecenter").ligerGetTabManager();
        var url=$(this).find("a").attr("href");
        if(url!=undefined){
            url=url.replace("#","");
            var fn=$(this).find("a").attr("fn");
            if(fn!=undefined){
                eval(fn+"()");
            }else{
                var tabId=$(this).find("a").attr("tab_id");
                $("#loading").show();
                var text = $(this).find("a").text();
                tab.removeTabItem(tabId);
                tab.addTabItem({ tabid : tabId,text: text, url: url });
            }
        }
    });
};

//首页上部分菜单事件
var topMenuAddToTab = function(){
	$("#topMenu li").live('click', function() {
	    var tab = $("#framecenter").ligerGetTabManager();
	    var url=$(this).find("a").attr("href");
	    if(url!=undefined){
	        url=url.replace("#","");
	        var fn=$(this).find("a").attr("fn");
	        if(fn!=undefined){
	            eval(fn+"()");
	        }else{
	            var tabId=$(this).find("a").attr("tab_id");
	            $("#loading").show();
	            var text = $(this).find("a").text();
	            tab.removeTabItem(tabId);
	            tab.addTabItem({ tabid : tabId,text: text, url: url });
	        }
	    }
	    return false;
	});
}
function f_addTab(tabid,text, url){ 
	tab.addTabItem({ tabid : tabid,text: text, url: url });
} 

//任何时候需要刷新菜单 只需要 调用 refreshLeftMenu(); 该方法会记住已经打开了的状态 会更新所有变化的状态 新增的状态
var refreshLeftMenu = function(){
    var statuss = "";
    $("#order_menu li").each(function(){
    	//获取每个订单状态
    	var status = $(this).attr("status");
   		if(status != undefined){
   			statuss += (status+",");	
   		}
    });
    if(statuss){
    	statuss = statuss.substring(0,statuss.length-1);
        //通过post 请求获取每个状态的订单数量
        $.post(ctxpath+"/order.Orders/refreshOrderQuantity?status="+statuss,function(result){
            if(result){
                fillOrderCount(result);
            }
        })
    }
};

//填充每个状态下面订单的数量
var fillOrderCount = function(quantityList){
    $.each(quantityList,function(){
        var status = this.status;
        var quantitys = this.quantitys;
        if(quantitys){
            // 用 -:- 代替 冒号
            var splitColon = "-:-";
            // 用-,- 代替 逗号
            var splitComma = "-,-";
            var quantityArray =  quantitys.split(splitComma);
            $("#ul_"+status).html("");
            for(var i=0;i<quantityArray.length;i++){
                var count;
                if(i==0){
                    //下标0 是当前状态的订单数量
                    var statusCount = quantityArray[i];
                    count = statusCount.split(splitColon)[1];
                    $("#span_quantity_"+status).html(count);
                }else{
                    //每个运输方式的订单数量
                    var shipwayCount = quantityArray[i];
                    var shipwayCode = shipwayCount.split(splitColon)[0];
                    var shipwayName = shipwayCount.split(splitColon)[1];
                    count = shipwayCount.split(splitColon)[2];
                    if(count != '0'){
                        //运输方式列表
                        $("#ul_"+status).html($("#ul_"+status).html()+
                            "<i class='i-toggle open'></i>"
                            +"<li class='side-item'>"
                            +"<a href='#"+ctxpath+"/order.Orders/listOrder?status="+status+"&shipwayCode="+shipwayCode+"' tab_id='status_"+status+"_shipway_"+shipwayCode+"'>"
                                +"<span class='status-shipWay-text' title='"+shipwayName+"' id='span_status_"+status+"_shipway_"+shipwayCode+"'>"+shipwayName+"</span>"
                            +"</a>"
                            +"<span id='span_quantity_status_"+status+"_shipway_"+shipwayCode+"'  class='count g'>"+count+"</span>"
                            +"</li>"
                        );
                    }
                }
            }
        }
    });
};

//订单状态被点击时 切换 开和关
function clickOrderStatus(status){
	//获取图标class 根据class 包含 open 或者 closed 判断 列表是打开还是关闭状态
	var text = $("#i_"+status).attr("class");
	if(text.indexOf("open")>=0){
		closeOrderStatus(status);
	}else{
		openOrderStatus(status);
	}
}

//展开某状态的 订单
function openOrderStatus(status){
	//显示运输方式
	$("#ul_"+status).show();
	//更换状态图标
	$("#i_"+status).removeClass("closed").addClass("open");
}
//关闭某状态的订单
function closeOrderStatus(status){
	//隐藏运输方式
	$("#ul_"+status).hide();
	//更换状态图标
	$("#i_"+status).removeClass("open").addClass("closed");
}
