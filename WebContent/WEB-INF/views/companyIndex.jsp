<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>COE</title>
    <link href="${baseUrl}/static/css/main.css" rel="stylesheet" type="text/css" />
    <link href="${baseUrl}/static/lhgdialog/prettify/prettify.css" type="text/css" rel="stylesheet" />
    <link href="${baseUrl}/static/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="${baseUrl}/static/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <style type="text/css"> 
 	</style>
</head>
<body>
	 <div id="header" class="navbar-top">
		<div class="navbar-inner_top" style="height:45px">
        <div class="navbar">
            <div>
                <div class="container">
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </a>
                    <a href="" style="float:left;margin-top:1px;margin-right: 30px"><img src="${baseUrl}/static/img/index.png"></a>
                    <div class="nav-collapse" id="topMenu">
                        <ul class="nav pull-left">
 				
                        </ul>
                        <ul class="nav pull-right">
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon-cog icon-white"></i>系统设置 <b class="caret"></b></a>
                                <ul class="dropdown-menu">
									<li><a href="#http://baidu.com" tab_id="listRegister">注册信息</a></li>
                                </ul>
                            </li>
                            <li class="divider-vertical"></li>
                            <li id="btn_head_link"><a href="${baseUrl}/logout.do" fn="logout"><i class="icon-off icon-white"></i> 退出</a></li>
                        </ul>
                    </div><!-- /.nav-collapse -->
                </div>
            </div><!-- /navbar-inner -->
        </div>
   	 </div>
	</div>
	
	<div id="layoutCenter">
		<!-- 左侧菜单 -->
	    <div position="left" id="leftMenu" class="leftMenu">
	    	<!-- 面板 -->
	    	<div id="accordion1"> 
		         <div title="功能列表">
		              <ul>
		                    <li>列表一</li>
		                    <li>列表二</li>
		                    <li>列表三</li>
		                    <li>列表四</li>
		                    <li>列表五</li>
		                </ul>
		        </div>
		         <div title="列表">
		                <ul>
		                    <li>列表一</li>
		                    <li>列表二</li>
		                    <li>列表三</li>
		                    <li>列表四</li>
		                    <li>列表五</li>
		                </ul>
		        </div> 
		        <div title="其他" style="padding:10px"> 
		     			   其他内容
				</div>
    	</div>  
	</div>
	    
   	<!-- 页面中心内容 -->	
    <div position="center" id="framecenter">
    	<!-- 默认加载页面 -->
    	<div tabid="dashboard" title="主页">
                <iframe frameborder="0" tab_id="dashboard" name="dashboard" id="dashboard" src="${baseUrl}/dashboard/main.do"></iframe>
        </div> 
    </div>  
	</div>
	 
	 <script type="text/javascript" src="${baseUrl}/static/jquery/jquery.js"></script>
    <script type="text/javascript" src="${baseUrl}/static/jquery/jquery.form.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.showMessage.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/bootstrap/bootstrap.min.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.cookie.js"></script>
	<script  type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/core/base.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/ligerui.all.js"></script>
    <script type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligerTab.js"></script>
    <script  type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligerTree.js" ></script>
    <script  type="text/javascript" src="${baseUrl}/static/ligerui/ligerUI/js/plugins/ligerAccordion.js"></script>
   	<script type="text/javascript" src="${baseUrl}/static/lhgdialog/prettify/prettify.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/lhgdialog/prettify/lhgdialog.js"></script>
	<script type="text/javascript">
		var baseUrl = "${baseUrl}",tab = null,manager=null;
		$(function() {
			$("#accordion1").ligerAccordion({
				height : 300
			});
		});
	</script>
	<script type="text/javascript" src="${baseUrl}/static/js/index/companyIndex/main.js"></script>
</body>
</html>