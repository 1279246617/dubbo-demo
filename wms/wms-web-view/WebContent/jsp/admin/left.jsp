<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
	.side-menu{display:none;}
	.side-menu a{cursor:default;}
	.side-menu a.use:hover{cursor:pointer;color:#f80;}
	.user-info{height:31px;line-height:31px;border-bottom:1px solid #ccc;}
	.user-name{padding-left:15px;color:#337ab7}
	#hidden_left{display:block;position:absolute;top:0;right:0;width:36px;line-height:31px;color:#337ab7;text-align:center;cursor:pointer;}
	#hidden_left:hover{background-color:#eee;}
</style>

<div class="navbar-default sidebar" role="navigation" id="left_body" style="margin-top:0;width:200px;">

	<!-- 菜单头部 -->
	<div class="user-info">
		<span class="user-name"><i class="fa fa-home"></i>&nbsp;面板菜单</span>
		<span id="hidden_left" s="y" class="fa fa-angle-double-left"></span>
	</div>
	
	<!-- 菜单体 -->
	<div class="sidebar-nav navbar-collapse">
	     <!-- 首页 -->
	     <ul class="nav side-menu" mudo="index" style="display:block;">
	         <li>
	          	<a class="use" href="${ctx}/jsp/admin/help/help.jsp" pageuid="adminHelpHelp" tag=""><i class="fa fa-flag-o"></i>&nbsp;使用指导</a>
	         </li>
	         <li>
	          	<a class="use" href="${ctx}/jsp/admin/help/logs.jsp" pageuid="adminHelpLogs" tag=""><i class="fa fa-history"></i>&nbsp;升级日志</a>
	         </li>
	     </ul>
	     	
	     <!-- 基础资料模块 -->	
	     <ul class="nav side-menu" mudo="basicdata" >
	         <li>
	             <a href="#"><i class="fa fa fa-cogs fa-fw"></i>&nbsp;基本资料<span class="fa arrow"></span></a>
	             <ul class="nav nav-second-level  in">
	               <li>
	         	        <a class="use" href="${ctx}/jsp/admin/basicdata/customer_list.jsp" pageuid="customerCardsList" tag=""><i class="fa fa-search"></i>&nbsp;客户查询</a>
	               </li>
	             </ul>
	         </li>
	     </ul>
	     
	     <!-- 用户管理模块 -->
	     <ul class="nav side-menu" mudo="user" >
	         <li>
	         	<a class="use" href="${ctx}/jsp/admin/user/list.jsp" pageuid="userCardsList" tag=""><i class="fa fa-search"></i>&nbsp;用户查询</a>
	        </li>
	        <li>
	         	<a class="use" href="${ctx}/jsp/admin/user/add.jsp" pageuid="addUserCard" tag=""><i class="fa fa-search"></i>&nbsp;用户新增</a>
	        </li>
	     </ul>
	     
	</div>
</div>

<script>
$(function() {
	$('.side-menu').metisMenu();
	 
    $(window).bind("load resize", function() {
        topOffset = 50;
        width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        if (width < 768) {
            $('div.navbar-collapse').addClass('collapse');
            topOffset = 100;
        } else {
            $('div.navbar-collapse').removeClass('collapse');
        }
    });

    var element = $('ul.nav a').filter(function() {
		var tag=$(this).attr("tag");
		var text=$(this).text();
        return tag == text;
    }).addClass('active').parent().parent().addClass('in').parent();
    
    if (element.length>0&&element.is('li')) {
        element.addClass('active');
    }
   
	var hmbEles=$(".hmb-eles li");
	hmbEles.click(function(){
		hmbEles.each(function(){
			$(this).removeClass("sel");
		});
		$(this).addClass("sel");
		var mu=$(this).attr("mu");
		
		$("#left_body").find("[mudo]").hide();
		$("#left_body").find("[mudo='"+mu+"']").show();
		
	});
});
</script>
        
        