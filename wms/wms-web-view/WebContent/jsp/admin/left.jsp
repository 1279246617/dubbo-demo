<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<div class="navbar-default sidebar" role="navigation" id="left_body" style="margin-top:0;width:200px;">

<div class="user-info">
<span class="user-name"><i class="fa fa-home"></i>&nbsp;面板菜单</span>
<span id="hidden_left" s="y" class="fa fa-angle-double-left"></span>
</div>

  <div class="sidebar-nav navbar-collapse" id="leftMenuId">
      
      <ul class="nav side-menu" mudo="index" style="display:block;">
          <li>
           	<a class="use" href="${ctx}/jsp/admin/test/test.jsp" pageuid="adminHelpHelp" tag=""><i class="fa fa-flag-o"></i>&nbsp;使用指导</a>
          </li>
          <li>
           	<a class="use" href="${ctx}/admin/help/logs" pageuid="adminHelpLogs" tag=""><i class="fa fa-history"></i>&nbsp;升级日志</a>
          </li>
      </ul>
      
      
      
      
      <ul class="nav side-menu" mudo="cards" >
          <li>
          	<a class="use" href="${ctx}/admin/cards/list" pageuid="adminCardsList" tag=""><i class="fa fa-search"></i>&nbsp;证件查询</a>
         </li>
         <li>
          	<a class="use" href="${ctx}/admin/cards/add" pageuid="adminCardsAddCard" tag=""><i class="fa fa-cloud-upload"></i>&nbsp;添加证件</a>
         </li>
      </ul>
      
      <ul class="nav side-menu" mudo="customer" >
       	<li>
         	<a class="use" href="${ctx}/admin/customer/list" pageuid="adminCustomerList" tag="">客户查询</a>
        </li>
      </ul>
      
      <ul class="nav side-menu" mudo="balance" >
         <li>
           	<a class="use" href="${ctx}/admin/balance/customerList" pageuid="adminBalanceCustomerList" tag="">客户结算</a>
          </li>
          <li>
           	<a class="use" href="${ctx}/admin/balance/policeList" pageuid="adminBalancePoliceList" tag="">公安结算</a>
          </li>
      </ul>
      
      <ul class="nav side-menu" mudo="analysis" >
         <li>
          	<a class="use" href="${ctx}/admin/statis/downList" pageuid="adminStatisDownList" tag="">调用统计</a>
         </li>
        	<li>
          	<a class="use" href="${ctx}/admin/statis/policeList" pageuid="adminStatisPoliceList" tag="">公安统计</a>
         </li>
         <li>
          	<a class="use" href="${ctx}/admin/statis/upList" pageuid="adminStatisUpList" tag="">上传统计</a>
         </li>
         <li>
          	<a class="use" href="${ctx}/admin/statis/statisList" pageuid="adminStatisStatisList" tag="">审核统计</a>
         </li>
      </ul>
      
      <ul class="nav side-menu" mudo="base" >
          <li>
              <a href="#"><i class="fa fa fa-cogs fa-fw"></i>&nbsp;基本资料<span class="fa arrow"></span></a>
              <ul class="nav nav-second-level  in">
               	<li>
                 	<a class="use" href="${ctx}/admin/base/list" pageuid="" tag=""><i class="fa fa-shopping-cart"></i>&nbsp;费用管理</a>
                </li>
                <li>
                 	<a class="use" href="${ctx}" pageuid="" tag=""><i class="fa fa-tags"></i>&nbsp;价格管理</a>
                </li>
              </ul>
          </li>
          <li>
              <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i>系统用户<span class="fa arrow"></span></a>
              <ul class="nav nav-second-level">
               	<li>
                 	<a class="use" href="${ctx}" pageuid="" tag=""><i class="fa fa-puzzle-piece"></i>&nbsp;权限管理</a>
                </li>
                <li>
                 	<a class="use" href="${ctx}/admin/base/user" pageuid="" tag=""><i class="fa fa-user"></i>&nbsp;用户管理</a>
                </li>
              </ul>
          </li>
          <li>
              <a href="#"><i class="fa fa-cubes"></i>系统状态<span class="fa arrow"></span></a>
              <ul class="nav nav-second-level">
               	<li>
                 	<a href="#"><i class="fa fa-database"></i>&nbsp;文件容量<span class="fa arrow"></span></a>
                 	    <ul class="nav nav-third-level">
		               	<li>
		                 	<a class="use" href="${ctx}/admin/base/user" pageuid="" tag=""><i class="fa fa-building-o"></i>&nbsp;证件总数</a>
		                </li>
		                <li>
		                 	<a class="use" href="${ctx}/admin/base/user" pageuid="" tag=""><i class="fa fa-file-image-o"></i>&nbsp;证件图片数</a>
		                </li>
		              </ul>
                </li>
                <li>
                 	<a class="use" href="${ctx}/admin/base/user" pageuid="" tag=""><i class="fa fa-pie-chart"></i>&nbsp;参数时效</a>
                </li>
              </ul>
          </li>
      </ul>
      
      
  </div>
</div>

        