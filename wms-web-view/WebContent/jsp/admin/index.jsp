<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="meta.jsp"%>
<title>管理后台</title>

<style>
.panel-heading {
    padding: 10px 15px;
    border-bottom: 1px solid transparent;
    border-top-left-radius: 3px;
    border-top-right-radius: 3px;
}

.panel-yellow {
    border-color: #f0ad4e;
}
.panel-yellow .panel-heading {
    border-color: #f0ad4e;
    color: #fff;
    background-color: #f0ad4e;
}

.panel-red {
    border-color: #d9534f;
}
.panel-red .panel-heading{    border-color: #d9534f;
    color: #fff;
    background-color: #d9534f;}
.panel:hover{filter:alpha(opacity=80);opacity:0.8;}    
</style>
</head>
<body>
<div class="container" style="min-height: 500px;padding-top:50px;">
               
<div class="row">

    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
    <a href="/Order/OrderList?pageIndex=1&amp;orderStatus=Unverified">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-3">
                        <i class="fa fa-codepen fa-5x"></i>
                    </div>
                    <div class="col-xs-9 text-right">
                        <div class="huge">195</div>
                        <div style="font-size:20px;">未审核证件</div>
                    </div>
                </div>
            </div>
                <div class="panel-footer">
                    <span class="pull-left">查看</span>
                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                    <div class="clearfix"></div>
                </div>
        </div>
        </a>
    </div>
    
        <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
    <a href="/Order/OrderList?pageIndex=1">
        <div class="panel panel-info">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-3">
                        <i class="fa fa-table fa-5x"></i>
                    </div>
                    <div class="col-xs-9 text-right">
                        <div class="huge">18876</div>
                        <div style="font-size:20px;">证件总数量</div>
                    </div>
                </div>
            </div>
                <div class="panel-footer">
                    <span class="pull-left">查看</span>
                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                    <div class="clearfix"></div>
                </div>
        </div>
         </a>
    </div>
    
    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
     <a href="/Order/OrderList?pageIndex=1&amp;paymentStatus=Unpaid">
        <div class="panel panel-yellow" >
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-3">
                        <i class="fa fa-info-circle fa-5x"></i>
                    </div>
                    <div class="col-xs-9 text-right">
                        <div class="huge">12383</div>
                        <div style="font-size:20px;">客户费用</div>
                    </div>
                </div>
            </div>
                <div class="panel-footer">
                    <span class="pull-left">查看</span>
                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                    <div class="clearfix"></div>
                </div>
        </div>
        </a>
    </div>
    
    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
    <a href="/Order/OrderList?pageIndex=1&amp;exceptional=true">
        <div class="panel panel-red">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-3">
                        <i class="fa fa-support fa-5x"></i>
                    </div>
                    <div class="col-xs-9 text-right">
                        <div class="huge">187</div>
                        <div style="font-size:20px;">邮政费用</div>
                    </div>
                </div>
            </div>
                <div class="panel-footer">
                    <span class="pull-left">查看</span>
                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                    <div class="clearfix"></div>
                </div>
        </div>
        </a>
    </div>
</div>





<!-- /#page-wrapper -->

            </div>

</body>
</html>
