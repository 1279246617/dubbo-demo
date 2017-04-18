<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
#header_menu_body{height:45px;width:100%;background:#337ab7;clear:both;line-height:45px;color:#fff;}
#header_menu_body .hmb-logo{float:left;width:200px;position:relative;}
#header_menu_body .hmb-left{float:left;overflow:hidden;}
#header_menu_body .hmb-eles{width:10000px;}
#header_menu_body .hmb-right{float:right;width:40px;margin-right:10px;text-align:center;cursor:pointer;}
#header_menu_body .hmb-right:hover{background-color:#609dd2;}
#header_menu_body .hmb-eles,#header_menu_body li{margin:0;padding:0;}
#header_menu_body .hmb-eles li{width:150px;float:left;list-style:none;text-align:center;cursor:pointer;}
#header_menu_body .hmb-eles li:hover{background:#609dd2;}
#header_menu_body .hmb-eles li.sel{background:#609dd2;}

#header_menu_body .dropdown{    width: 100px;
    float: right;
    list-style: none;
    text-align: center;
    cursor: pointer;}
#header_menu_body .dropdown:hover{background-color:#609dd2;}
</style>

<div id="header_body">
	<div id="header_menu_body">
	
	<div class="hmb-logo">
		<img src="${ctx}/static/img/logo.png" style="width:200px;"/>
	</div>
	
	<div class="hmb-left">
		<ul class="hmb-eles">
			<li class="sel" mu="index">首页</li>
			<li mu="basicdata">基本资料</li>
			<li mu="user">用户管理</li>
		</ul>
	</div>
	
	<div class="dropdown">
	  <div class="dropdown-toggle" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
	    <i class="fa fa-gear"></i>&nbsp;系统设置
	    <span class="caret"></span>
	  </div>
	  <ul class="dropdown-menu" aria-labelledby="dropdownMenu1" style="right:0;left:auto;min-width:100px;">
	    <li><a href="#"><i class="fa fa-language"></i>&nbsp;英语</a></li>
	    <li><a href="#"><i class="fa fa-language"></i>&nbsp;简体</a></li>
	    <li role="separator" class="divider"></li>
	    <li><a href="#"> <i class="fa fa-sign-out"></i>&nbsp;退出</a></li>
	  </ul>
	</div>
	
	<div class="hmb-right">
	<i class="fa fa-forward"></i>
	</div>
	
	</div>
</div>

<script>
$(function(){
	var headerBody=$("#header_body");
	var navBody=$("#nav_body");
	var navBox=$("#nav_box");
	var jmenuTabs=navBody.find(".J_menuTabs");
	var sz=200;
	var totalL=$(window).width()-200-sz;
	
	
	headerBody.find("a[sitename]").click(function(){
		var siteCode=$(this).attr("sitecode");
		var siteName=$(this).attr("sitename");
		
 		mmUtl.ajax.postJson("/admin/user/updateSeat", function(result){
			if(result.msg!='success'){
				parent.mmui.msg(result.msg,{icon:3,time:2000,shift:6,offset:0});
			}else{
				headerBody.find("[ja='showsitename']").html(siteName);
			}
 		},{siteCode:siteCode,siteName:siteName})
	});
	
	var headerMenuBody=$("#header_menu_body");
	
	var winW=$(window).width();
	var menuW=winW-360;
	headerMenuBody.find(".hmb-left").width(menuW);
	
	var hmbEles=headerMenuBody.find(".hmb-eles");
	var hmblen=hmbEles.find("li").length;
	var totalLen=hmblen*150;
	hmbEles.width(totalLen);
	
	headerMenuBody.find(".hmb-right").click(function(){
		var ml=hmbEles.css("marginLeft");
		ml=ml.replace("px","");
		ml=Number(ml);
		
		if(totalLen-menuW+ml>20){
			hmbEles.css("marginLeft",ml-150);
		}
	});
	
	$("#hidden_left").click(function(){
		var s=$(this).attr("s");
		if("y"==s){
			$("#left_body").css("marginLeft","-170px");
			$("#page-wrapper").css("marginLeft","30px");
			$(this).removeClass("fa-angle-double-left");
			$(this).addClass("fa-angle-double-right");
			$(this).attr("s","n");
		}else{
			$("#left_body").css("marginLeft","0");
			$("#page-wrapper").css("marginLeft","200px");
			$(this).removeClass("fa-angle-double-right");
			$(this).addClass("fa-angle-double-left");
			$(this).attr("s","y");
		}
	})
	
	function getw(){
		totalL=$(window).width()-200-sz;
		var w=0;
		navBox.find("a").each(function(i){
			w+=$(this).outerWidth();
		});
		return w;
	}
	
	function getnw(ml){
		var w=0,ww=0;
		var as=navBox.find("a");
		as.each(function(i){
			w+=$(this).outerWidth();
			if(w>-ml+80){
				ww=$(this).outerWidth();
				return false
			}
		});
		return ww;
	}
	
	navBody.find(".leftbtn").click(function(){
		var ml=jmenuTabs.css("marginLeft");
		ml=ml.replace("px","");
		ml=Number(ml);
		var w=getw();
		if(totalL-w-ml+80<0){
			jmenuTabs.css("marginLeft",ml-getnw(ml));
		}
	});
	
	navBody.find(".rightbtn").click(function(){
		var ml=jmenuTabs.css("marginLeft");
		ml=ml.replace("px","");
		ml=Number(ml);
		if(ml<80){
			jmenuTabs.css("marginLeft",ml+getnw(ml));
		}
	});
	
})
</script>
