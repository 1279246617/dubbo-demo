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
	<script type="text/javascript" src="${baseUrl}/static/js/index/companyIndex/main.js"></script>
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
                    <a href="" style="float:left;margin-top:1px;margin-left: 5px"><img src="${baseUrl}/static/img/index.png"></a>
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
	    <div position="left" id="leftMenu"  >
	    	<!-- 面板 -->
	    	<div id="accordion1" class="  well main-menu"  > 
		         <div title="订单管理" class=" nav-collapse sidebar-nav">
		         		<div class="sep" >
		                	<div style="font-weight: bold;color: black;margin:5px 0px  0px  9px;  ">
		                		仓配订单管理
		                	</div>
						</div>	
		              <ul class="nav nav-tabs nav-stacked " style="margin-bottom:0px;">	
		              		<li><a href="#${baseUrl}/warehouse/storage/listInWarehouseOrder.do" tab_id="findInWarehouseOrder"><i class="icon-list"></i><span class="hidden-tablet">入库订单</span></a></li>
		              		<!-- 出库订单查询中,需要具备审核功能,反审核 -->
		              		<li><a href="#${baseUrl}/warehouse/storage/listOutWarehouseOrder.do" tab_id="findOutWarehouseOrder"><i class="icon-list"></i><span class="hidden-tablet">出库订单</span></a></li>
		              		<!-- 待审核出库订单,只显示未审核出库订单,方便审核的人快速操作 -->
		              		<li><a href=#${baseUrl}/warehouse/storage/listWaitCheckOutWarehouseOrder.do  tab_id="waitCheckOutWarehouseOrder"><i class="icon-eye-open"></i><span class="hidden-tablet">待审核出库订单</span></a></li>
		                </ul>
		                <div class="sep">
		                	<div style="font-weight: bold;color: #006400;margin:5px 0px  0px  9px;  ">
		                		转运订单管理
		                	</div>
						</div>
						<ul class="nav nav-tabs nav-stacked" style="margin-bottom:0px;">
							<li><a href="#${baseUrl}/warehouse/transport/listOrderPackage.do" tab_id="findOrderPackage"><i class="icon-list"></i><span class="hidden-tablet" style="color:#006400">转运大包</span></a></li>	
		              		<li><a href="#${baseUrl}/warehouse/transport/listOrder.do" tab_id="findOrder"><i class="icon-list"></i><span class="hidden-tablet" style="color:#006400">转运订单</span></a></li>
		              		<li><a href=#${baseUrl}/warehouse/transport/listWaitCheckOrder.do  tab_id="waitCheckOrder"><i class="icon-eye-open"></i><span class="hidden-tablet" style="color:#006400">待审核转运订单</span></a></li>
		              		<li><a href=#${baseUrl}/warehouse/transport/listWaiPrintOrder.do  tab_id="waitCheckOrder2"><i class="icon-list"></i><span class="hidden-tablet" style="color:#006400">待打印捡货订单</span></a></li>
		              		<li><a href="#${baseUrl}/warehouse/transport/listFirstWaybill.do" tab_id="findFirstWaybill"><i class="icon-list"></i><span class="hidden-tablet" style="color:#006400">转运订单小包</span></a></li>
		                </ul>
		        </div>
		        
		        <div title="仓配管理" class=" nav-collapse sidebar-nav">
		              <ul class="nav nav-tabs nav-stacked ">	
		              		<li><a href="#${baseUrl}/warehouse/storage/inWarehouse.do" tab_id="doInWarehouse"><i class="icon-arrow-right"></i><span class="hidden-tablet">入库订单收货</span></a></li>
		              		<!-- 入库主单查询 条件: 客户帐号,创建时间段,仓库,入库批次号 -->
		              		<li><a href="#${baseUrl}/warehouse/storage/listInWarehouseRecord.do" tab_id="findInWarehouseOrderRecord"><i class="icon-list"></i><span class="hidden-tablet">入库收货记录</span></a></li>
		              		<!-- 入库明细查询 条件: SKU, 数量, 主单号,批次号,客户帐号,时间,商品描述 -->
		              		<li><a href="#${baseUrl}/warehouse/storage/listInWarehouseRecordItem.do"tab_id="findInWarehouseItemRecordItem"><i class="icon-list"></i><span class="hidden-tablet">入库收货明细记录</span></a></li>
		              		<li><a href="#${baseUrl}/warehouse/shelves/onShelves.do" tab_id="doOnShelves"><i class="icon-arrow-up"></i><span class="hidden-tablet">入库订单上架</span></a></li>
		              		<li><a href="#${baseUrl}/warehouse/shelves/listOnShelves.do" tab_id="listOnShelves"><i class="icon-list"></i><span class="hidden-tablet">入库订单上架记录</span></a></li>
			              	<li><a href="#${baseUrl}/warehouse/shelves/outShelves.do" tab_id="doOutShelves"><i class="icon-arrow-down"></i><span class="hidden-tablet">出库订单下架</span></a></li>
		              		<li><a href="#${baseUrl}/warehouse/shelves/listOutShelves.do" tab_id="listOutShelves"><i class="icon-list"></i><span class="hidden-tablet">出库订单下架记录</span></a></li>
			              	<!-- (扫SKU和数量 是否和出库订单一样) -->	
		              		<li><a href="#${baseUrl}/warehouse/storage/outWarehouseWeightAndPrint.do" tab_id="doOutWarehouse"><i class="icon-inbox"></i><span class="hidden-tablet">装箱称重打单</span></a></li>
		              		<li><a href="#${baseUrl}/warehouse/storage/outWarehousePackage.do" tab_id="outWarehousePackage"><i class="icon-th"></i><span class="hidden-tablet">扫描建包</span></a></li>
		              		<li><a href="#${baseUrl}/warehouse/storage/outWarehouseShipping.do" tab_id="outWarehouseShipping"><i class="icon-plane"></i><span class="hidden-tablet">扫描发货</span></a></li>
		              		<li><a href="#${baseUrl}/warehouse/storage/listOutWarehousePackage.do" tab_id="findOutWarehousePackage"><i class="icon-list"></i><span class="hidden-tablet">出库建包记录</span></a></li>
<!-- 		              		<li><a href="#${baseUrl}/warehouse/storage/listOutWarehouseRecord.do" tab_id="findOutWarehouseRecord"><i class="icon-list"></i><span class="hidden-tablet">发货记录</span></a></li> -->
		              		<li><a href="#${baseUrl}/warehouse/inventory/listInventory.do" tab_id="findItemInventory"><i class="icon-zoom-out"></i><span class="hidden-tablet">商品批次库存</span></a></li>
		              		<li><a href="#${baseUrl}/warehouse/inventory/listItemShelfInventory.do" tab_id="findItemShelfInventory"><i class="icon-zoom-out"></i><span class="hidden-tablet">商品货位库存</span></a></li>
		                </ul>
		        </div>
		        
		        <div title="转运管理" class=" nav-collapse sidebar-nav">
		              <ul class="nav nav-tabs nav-stacked ">
		              		<li><a href="#${baseUrl}/warehouse/transport/inWarehouse.do" tab_id="doTransportInWarehouse"><i class="icon-arrow-right"></i><span class="hidden-tablet">转运收货</span></a></li>
		              		<li><a href="#${baseUrl}/warehouse/transport/listReceivedFirstWaybill.do" tab_id="findtransportInWarehouseOrderRecord"><i class="icon-list"></i><span class="hidden-tablet">转运收货记录</span></a></li>
		              		<li><a href="#${baseUrl}/warehouse/transport/firstWaybillOnShelf.do" tab_id="dotransportOutWarehouse"><i class="icon-arrow-up"></i><span class="hidden-tablet">转运上架</span></a></li>
		              		<li><a href="#${baseUrl}/warehouse/transport/listFirstWaybillOnShelf.do" tab_id="findtransportInWarehouseOrderRecord"><i class="icon-list"></i><span class="hidden-tablet">转运上架记录</span></a></li>
		              		
		              		<li><a href="#${baseUrl}/warehouse/transport/orderWeightAndPrint.do" tab_id="dotransportOutWarehouse"><i class="icon-inbox"></i><span class="hidden-tablet">称重打单</span></a></li>
		              		<li><a href="#${baseUrl}/warehouse/transport/outWarehousePackage.do" tab_id="dotransportOutWarehouse2"><i class="icon-inbox"></i><span class="hidden-tablet">扫描建包</span></a></li>
		              		<li><a href="#${baseUrl}/warehouse/transport/outWarehouseShipping.do" tab_id="outtransportWarehouseShipping1"><i class="icon-plane"></i><span class="hidden-tablet">扫描大包发货</span></a></li>
		              		<li><a href="#${baseUrl}/warehouse/transport/listOutWarehousePackage.do" tab_id="findtransportOutWarehousePackage2"><i class="icon-list"></i><span class="hidden-tablet">建包发货记录</span></a></li>
		                </ul>
		        </div>
		         <div title="用户管理" class=" nav-collapse sidebar-nav">
<!-- 		                <ul class="nav nav-tabs nav-stacked"> -->
<!-- 		                	<li><a href=#><i class="icon-plus-sign"></i><span class="hidden-tablet">新建用户</span></a></li> -->
<!-- 		              		<li><a href=#><i class="icon-th-large"></i><span class="hidden-tablet">用户管理</span></a></li> -->
<!-- 		              		<li><a href=#><i class="icon-th-large"></i><span class="hidden-tablet">角色管理</span></a></li> -->
<!-- 		              		<li><a href=#><i class="icon-th-large"></i><span class="hidden-tablet">权限管理</span></a></li> -->
<!-- 		                </ul> -->
		        </div> 
		        <div title="财务管理" class=" nav-collapse sidebar-nav"> 
		     			<ul class="nav nav-tabs nav-stacked">
<!-- 		                	<li><a href=#><i class="icon-check"></i><span class="hidden-tablet">现金收款</span></a></li> -->
<!-- 		                	<li><a href=#><i class="icon-check"></i><span class="hidden-tablet">入账</span></a></li> -->
<!-- 		              		<li><a href=#><i class="icon-check"></i><span class="hidden-tablet">出账审核</span></a></li> -->
<!-- 		              		<li><a href=#><i class="icon-check"></i><span class="hidden-tablet">出账</span></a></li> -->
		                </ul>
				</div>
				
				<div title="基础资料" class=" nav-collapse sidebar-nav">
						<ul class="nav nav-tabs nav-stacked">
<!-- 		                	<li><a href=#><i class="icon-cog"></i><span class="hidden-tablet">渠道管理</span></a></li> -->
<!-- 		              		<li><a href=#><i class="icon-check"></i><span class="hidden-tablet">报价管理</span></a></li> -->
<!-- 		              		<li><a href=#><i class="icon-cog"></i><span class="hidden-tablet">仓库设置</span></a></li> -->
<!-- 		              		<li><a href=#><i class="icon-cog"></i><span class="hidden-tablet">货架类型</span></a></li> -->
		              		<li><a href="#${baseUrl}/warehouse/shelves/listSeat.do" tab_id="findSeat"><i class="icon-zoom-out"></i><span class="hidden-tablet">货架管理</span></a></li>
		              		<li><a href="#${baseUrl}/products/listProduct.do"><i class="icon-cog"></i><span class="hidden-tablet">商品管理</span></a></li>
		                </ul>
		        </div> 
		        
		         <div title="报表管理" class=" nav-collapse sidebar-nav">
		                <ul class="nav nav-tabs nav-stacked">
		              			<li><a href="#${baseUrl}/warehouse/report/listReport.do" tab_id="listReport"><i class="icon-file"></i><span class="hidden-tablet">报表文件下载</span></a></li>
<!-- 		              			<li><a href="#" tab_id=""><i class="icon-th-large"></i><span class="hidden-tablet">入库记录报表</span></a></li> -->
<!-- 		              			<li><a href="#" tab_id=""><i class="icon-th-large"></i><span class="hidden-tablet">出库记录报表</span></a></li> -->
<!-- 		              			<li><a href="#" tab_id=""><i class="icon-th-large"></i><span class="hidden-tablet">库存日结报表</span></a></li> -->
		                </ul>
		        </div>
    	</div>  
	</div>
	    
   	<!-- 页面中心内容 -->	
    <div position="center" id="framecenter">
    	<!-- 默认加载页面 -->
    	
    	<div tab_id="dashboard" title="主页">
                <iframe frameborder="0" tab_id="dashboard" name="dashboard" id="dashboard" src="${baseUrl}/dashboard/main.do"></iframe>
        </div> 
    </div>  
	</div>

	<script type="text/javascript">
		var baseUrl = "${baseUrl}",tab = null,manager=null;
		var height = document.body.clientHeight - 110;
		$(function() {
			$("#accordion1").ligerAccordion({
				height :height
			});
			/*     转运管理特殊颜色处理代码 ---------开始*/
			var titles = $(".l-accordion-header-inner");
			$.each(titles, function (key, val) {
				if($(val).text() =='转运管理'){
					$(val).css("color","#006400");
				}
	        });
			/*     转运管理特殊颜色处理代码 ---------结束*/
			//左侧菜单 鼠标 hover
			$('div.main-menu li:not(.nav-header)').hover(function(){
				$(this).animate({'margin-left':'+=5'},300);
			},
			function(){
				$(this).animate({'margin-left':'-=5'},300);
			});
		});
	</script>
</body>
</html>