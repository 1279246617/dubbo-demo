<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../meta.jsp"%>
<title>管理后台</title>
<style type="text/css">
.form-inline .form-group{
	margin-bottom:8px;
	}
.form-inline .input-group > .width100{width: 100px;}
.form-inline .input-group > .width150{width: 150px;}
.form-inline .input-group > .width200{width: 200px;}
.form-inline .input-group > .width400{width: 400px;}
.form-inline .input-group > .width800{width: 600px;}
.form-inline .input-group > .width800{width: 800px;}

</style>
</head>
<body style="padding:12px 12px 0 12px;overflow-y:scroll;">

<form id="toolform" action="order" method="post" class="form-inline" >
	<div class="form-group">
	  <button type="button" id="add_btn" class="btn btn-primary">新增</button>
	  <button type="button" id="update_btn" class="btn btn-primary">修改</button>
	  <button type="button" id="del_btn" class="btn btn-primary">删除</button>
	  <button type="button" id="out_btn" class="btn btn-primary">导出</button>
	  <button type="button" id="get_btn" class="btn btn-primary">查看</button>
	  <button type="button" id="bat_modify" class="btn btn-primary btn-sm" >批量更新</button>
	  <button type="button" id="file_btn" class="btn btn-primary">附件</button>
	</div>
	<br/>
	<div class="input-group">
	  <span class="input-group-addon" id="sizing-addon3">客户代码</span>
	  <input type="text" class="form-control" placeholder="客户代码" aria-describedby="sizing-addon3" name="custCode" id="custCode">
	</div>
	<div class="input-group">
	  <span class="input-group-addon" id="sizing-addon3">结算方式</span>
	    <select style="" class="form-control width200" name="dictClearingMethod" id="dictClearingMethod" >
	    </select>
	</div>
	<div class="input-group">
	  <span class="input-group-addon" id="sizing-addon3">客户状态</span>
	   <select style="" class="form-control width200" name="dictCustStatus" id="dictCustStatus">
	   </select>
	</div>
	&emsp;&emsp;&emsp;&emsp;
	<div id="partner_type" class="input-group"></div>
	<br/>
	<div class="input-group">
	  <span class="input-group-addon" id="sizing-addon3">客户名称</span>
	  <input type="text" class="form-control" placeholder="客户名称" aria-describedby="sizing-addon3" name="custName" id="custName">
	</div>
	<div class="input-group">
	  <span class="input-group-addon" id="sizing-addon3">对&nbsp;账&nbsp;&nbsp;员</span>
	  <input type="text" class="form-control" placeholder="对账员" aria-describedby="sizing-addon3" name="financeCode" id="financeCode" data-provide="typeahead"> 
	</div>
	<div class="input-group">
	  <span class="input-group-addon" id="sizing-addon3">所属站点</span>
	  <input type="text" class="form-control" placeholder="" aria-describedby="sizing-addon3" name="siteCode" id="siteCode" data-provide="typeahead"> 
	</div>
	&emsp;&emsp;&emsp;&emsp;
	<div class="input-group">
	  <input type="checkbox" name="dictIsSs" value="is_1" id="dictIsSs" />自打单
	</div>
	<div class="input-group">
	  <a href="javascript:;" id="submit_btn" class="btn btn-primary btn-sm">查询</a>
	</div>
</form>
<table id="list_body" >
</table>	
<script>
$(function () {
		mmList({
		    url:url.path+'/fcs-web-basicdata/basicdata/customer/listCustomer.json',
			onLoadSuccess:function(data){
			
			},  
			columns: [
				    {checkbox: true},
					{field: 'dictPartnerType',title: '是否同行'},
					{field: 'custCode',title: '客户代码'}, 
					{field: 'custName',title: '客户名称'},
					{field: 'dictClearingMethod',title: '结算方式'},
					{field: 'siteCode',title: '所属站点代码'},
					{field: 'siteName',title: '所属站点名称'},
					{field: 'dictIsSs',title: '自打单',formatter:function(value, row, index){
					    if(row.dictIsSs=='是') return '<input type="checkbox" checked="checked" disabled="disabled"/>';
						return '<input type="checkbox" disabled="disabled"/>';
					}},
					{field: 'fileCount',title: '附件',formatter:function(value, row, index){
					    if(row.fileCount > 0) return '<input type="checkbox" checked="checked" disabled="disabled"/>';
						return '<input type="checkbox" disabled="disabled"/>';
					}},
					{field: 'invoiceHeader',title: '发票抬头'},
					{field: 'invoiceAddr',title: '发票地址'},
					{field: 'custAddr',title: '客户地址'},
				    {field: 'financeCode',title: '对账员代码'},
					{field: 'userName',title: '对账员姓名'},
					{field: 'userPhone',title: '对账员电话'},
					{field: 'userEmail',title: '对账员email'},
					{field: 'dictCustStatus',title: '客户状态'},
					{field: 'agreeTime',title: '签约日期',formatter:function(value, row, index){
						return mmUtl.time.formatDate(row.agreeTime);
					}},
					{field: 'stopTime',title: '停止日期',formatter:function(value, row, index){
						return mmUtl.time.formatDate(row.agreeTime);
					}},
					{field: 'salesareaCode',title: '销售区域代码'},
					{field: 'salesareaName',title: '销售区域名称'},
					{field: 'salesCode',title: '销售员代码'},
					{field: 'salesName',title: '销售员姓名'},
					{field: 'maintainCode',title: '维护员代码'},
					{field: 'maintainName',title: '维护员姓名'},
					{field: 'dictIsExcelBill',title: 'Excel账单',formatter:function(value, row, index){
					    if(row.dictIsExcelBill=='是') return '<input type="checkbox" checked="checked" disabled="disabled"/>';
						return '<input type="checkbox" disabled="disabled"/>';
					}},
					{field: 'dictIsPdfBill',title: 'FDK账单',formatter:function(value, row, index){
					    if(row.dictIsPdfBill=='是') return '<input type="checkbox" checked="checked" disabled="disabled"/>';
						return '<input type="checkbox" disabled="disabled"/>';
					}},
					{field: 'dictIsInvoice',title: '发票',formatter:function(value, row, index){
					    if(row.dictIsInvoice=='是') return '<input type="checkbox" checked="checked" disabled="disabled"/>';
						return '<input type="checkbox" disabled="disabled"/>';
					}},
					{field: 'billEmail',title: '账单Email'},
					{field: 'dictIsDisallow',title: '网站限制查件',formatter:function(value, row, index){
					    if(row.dictIsDisallow=='是') return '<input type="checkbox" checked="checked" disabled="disabled"/>';
						return '<input type="checkbox" disabled="disabled"/>';
					}}, 
					{field: 'contactsName',title: '财务联络人'},
					{field: 'contactsPhone',title: '财务电话'},
					{field: 'contactsEmail',title: '财务Email'},
					{field: 'remark',title: '备注'},
					{field: 'remarkOther',title: '其他备注'},
					{field: 'createOperator',title: '建立人'},
					{field: 'createTime',title: '建立时间',formatter:function(value, row, index){
						return mmUtl.time.formatDate(row.createTime);
					}},
					{field: 'updateOperator',title: '最后修改人'},
					{field: 'updateTime',title:'最后修改时间',formatter:function(value, row, index){
						return mmUtl.time.formatDate(row.updateTime);
					}}
				]
		}); 
    //初始化站点下拉列表
	initSiteOption();
	//初始化对账员下拉列表
	inintfinanceCode();
	//获取字典表中信息
	initDict("partner_type","radio","partner_type","dictPartnerType");
	initDict("dictCustStatus","option","cust_status");
	initDict("dictClearingMethod","option","clearing_method");
	var formData=new Object();
    $("#submit_btn").click(function(){  	
    	formData = mmUtl.ajax.getArgs($("#toolform"));
    })
	//批量更新
	$("#bat_modify").click(function(){
		var data=$("#list_body").mmuiTable("getSelections");
	    var idGrop='';
	    for(var i=0;i<data.length;i++){
	    	var nextId = data[i].id;
	    	if(i==0) {
	    		idGrop=nextId;
	    	}else{
	    		idGrop+=","+nextId;
	    	}
	    }
	    var idList;
		mmUtl.ajax.postJsonAntSyn(url.path+'/fcs-web-basicdata/basicdata/customer/listCustomerId', function(data){
			idList=data.data;
			}, formData);
		
		
	    parent.mmgrid("${ctx}/jsp/admin/basicdata/customer/batchModify.jsp?idList="+idList+"&idGrop="+idGrop,"batchModifyCustomers","批量修改",true);  
	});
	//删除
	$("#del_btn").click(function(){
		var data=$("#list_body").mmuiTable("getSelections");
		if(data.length!=1){
			parent.mmui.alert("请选择一条要删除的数据",3);
			return false;
		}
		var id=data[0].id;
		parent.mmui.confirm("删除客户"+data[0].custName,function(deleteCustomer){
			parent.mmui.close(deleteCustomer);
			mmUtl.ajax.postJson(url.path+"/fcs-web-basicdata/basicdata/customer/deleteCustomer",function(data){
				mmui.oper(data.msg,1); 
				document.location.reload();
			},{
				id:id
			});
		},function(){
			 
		});
	});
	//新增客户资料
	$("#add_btn").click(function(){
		parent.mmgrid("${ctx}/jsp/admin/basicdata/customer/add.jsp?method=add","addCustomerCards","新增客户",true);
	}); 
	 
	//修改客户资料
	$("#update_btn").click(function(){
	 	var data=$("#list_body").mmuiTable("getSelections");
		if(data.length!=1){
			parent.mmui.alert("请选择一条要修改的数据",3);
			return false;
		}
	    var id=data[0].id;
	    parent.mmgrid("${ctx}/jsp/admin/basicdata/customer/add.jsp?method=update&id="+id,"updateCustomerCards","修改客户",true);  
	});
	
	//查询客户资料单个明细
	$("#get_btn").click(function(){
	 	var data=$("#list_body").mmuiTable("getSelections");
		if(data.length!=1){
			parent.mmui.alert("请选择一条要查看的数据",3);
			return false;
		}
	   var id=data[0].id;
	    parent.mmgrid("${ctx}/jsp/admin/basicdata/customer/add.jsp?method=get&id="+id,"getCustomerCards","查看客户",true);  
	});
});

function initSiteOption(){
	  mmUtl.ajax.postJson(url.path+"/fcs-web-basicdata/basicdata/site/listALLSiteCode ",function(data){
		    var resultVo=data.data;
		    $('#siteCode').typeahead({source: resultVo});
		 },{
			
	   });
};
function inintfinanceCode(){
	 mmUtl.ajax.postJson(url.path+"/fcs-web-symgr/symgr/userMgr/getAllUserCode",function(data){
		    var resultVo=data.data;
	        $('#financeCode').typeahead({source: resultVo});
		 },{
	 });
};

</script>
		
</body>
</html>
