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
	<div class="sidebar-nav navbar-collapse" id="menuAll">
	     <!-- 首页 -->
	     <ul class="nav side-menu" mudo="index" style="display:block;">
	         <li>
	          	<a class="use" href="${ctx}/jsp/admin/help/help.jsp" pageuid="adminHelpHelp" tag=""><i class="fa fa-flag-o"></i>&nbsp;使用指导</a>
	         </li>
	         <li>
	          	<a class="use" href="${ctx}/jsp/admin/help/logs.jsp" pageuid="adminHelpLogs" tag=""><i class="fa fa-history"></i>&nbsp;升级日志</a>
	         </li>
	     </ul>
	</div>
</div>