/**
 * ligerUI 一些BUG补丁式修复或者其它补充
 */
	//表格高度跟着窗体变化而变
    $(window).resize(function(){
    	$(".l-panel-bwarp,.l-panel-body,.l-grid-body").css("height",$(window).height()-116+"px");
    });