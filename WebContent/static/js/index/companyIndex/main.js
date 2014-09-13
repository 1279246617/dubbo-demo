var adjustHeight = function() {
	var wh = $(window).height();
	var hh = $("#header").height();
	$("#framecenter").ligerTab({height : wh - hh});
	var tab = $("#framecenter").ligerGetTabManager();
	tab.setHeight(wh - hh);
};

//浏览器窗口高度 变化时 高度自适应
$(window).resize(function() {
	adjustHeight();
});

//init
$(function(){
	//布局
	$("#layoutCenter").ligerLayout({ leftWidth: 160,heightDiff:-13});
	var height = $(".l-layout-center").height();
	//Tab
	$("#framecenter").ligerTab({ height: height });
	//TAB管理器
	tab = $("#framecenter").ligerGetTabManager();
	adjustHeight();
	topMenuAddToTab();
	leftMenuAddToTab();
});

var tab = null;
//左侧菜单事件
var leftMenuAddToTab = function(){
    $("#leftMenu li").live('click', function() {
        tab = $("#framecenter").ligerGetTabManager();
        var url=$(this).find("a").attr("href");
        url=url.replace("#","");
        var fn=$(this).find("a").attr("fn");
        var tabId=$(this).find("a").attr("tab_id");
        if(url!=undefined && url!=''){
            if(fn!=undefined){
                eval(fn+"()");
            }else{
                $("#loading").show();
                var text = $(this).find("a").text();
                tab.removeTabItem(tabId);
                tab.addTabItem({ tabid : tabId,text: text, url: url });
            }
        }
    });
}

//首页上部分菜单事件
var topMenuAddToTab = function(){
	$("#topMenu li").live('click', function() {
	    var tab = $("#framecenter").ligerGetTabManager();
	    var url=$(this).find("a").attr("href");
	    var fn=$(this).find("a").attr("fn");
        var tabId=$(this).find("a").attr("tab_id");
	    url=url.replace("#","");
	    if (url != undefined && url !='') {
	         if(fn!=undefined){
	             eval(fn+"()");
	         }else{
	             $("#loading").show();
	             var text = $(this).find("a").text();
	             tab.removeTabItem(tabId);
	             tab.addTabItem({ tabid : tabId,text: text, url: url });
	         }
		}
	});
}

function f_addTab(tabid,text, url){ 
	tab.addTabItem({ tabid : tabid,text: text, url: url });
} 
