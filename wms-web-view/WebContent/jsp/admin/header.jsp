<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<div id="header_body">
<div id="header_menu_body">
<div class="hmb-logo">
<img src="${ctx}/static/img/logo.png" style="width:200px;"/>
</div>
<div class="hmb-left">
<ul class="hmb-eles" id="headerMenue">
   <li class="sel" mu="index"><i class="fa fa-home"></i>&nbsp;首页</li>
   <li mu="cards"><i class="fa fa-home"></i>&nbsp;身份证管理</li>
   <li mu="customer"><i class="fa fa-home"></i>&nbsp;客户管理</li>
   <li mu="balance"><i class="fa fa-home"></i>&nbsp;结算管理</li>
   <li mu="analysis"><i class="fa fa-home"></i>&nbsp;统计分析</li>
   <li mu="base"><i class="fa fa-home"></i>&nbsp;基础管理</li>
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


