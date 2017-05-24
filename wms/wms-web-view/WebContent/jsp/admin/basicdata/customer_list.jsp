<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../meta.jsp"%>
<title>管理后台</title>
<style type="text/css">
.form-inline .form-group{
	margin-bottom:8px;
	}



</style>
</head>
<body style="padding:12px 12px 0 12px;overflow-y:scroll;">

<form id="toolform" action="" method="post" class="form-inline" >

<div class="form-group">
  <a href="<%-- ${ctx}/jsp/admin/customer/addCustomer.jsp --%>javascript:; " id="submit_btn1" class="btn btn-primary">新增</a>
</div>
<div class="form-group">
  <a href="javascript:;" id="btn2" class="btn btn-primary">修改</a>
</div>
<div class="form-group">
  <a href="javascript:;" id="submit_btn" class="btn btn-primary">删除</a>
</div>
<div class="form-group">
  <a href="javascript:;" id="submit_btn" class="btn btn-primary">导出</a>
</div>
<div class="form-group">
  <a href="javascript:;" id="subm_btn" class="btn btn-primary">查看</a>
</div>
<div class="form-group">
  <a href="javascript:;" id="bat_query" class="btn btn-primary btn-sm" >批量查询</a>
</div>
<div class="form-group">
  <a href="javascript:;" id="submit_btn" class="btn btn-primary">附件</a>
</div>
<br/>
<div class="input-group input-group-sm">
  <span class="input-group-addon" id="sizing-addon3">客户代码</span>
  <input type="text" class="form-control" placeholder="客户代码" aria-describedby="sizing-addon3">
</div>
<div class="input-group input-group-sm">
  <span class="input-group-addon" id="sizing-addon3">结算方式</span>
  <input type="text" class="form-control" placeholder="结算方式" aria-describedby="sizing-addon3">
</div>
<div class="input-group input-group-sm">
  <span class="input-group-addon" id="sizing-addon3">客户状态</span>
  <input type="text" class="form-control" placeholder="客户状态" aria-describedby="sizing-addon3">
</div>
<br/>

<div class="input-group input-group-sm">
  <span class="input-group-addon" id="sizing-addon3">客户名称</span>
  <input type="text" class="form-control" placeholder="客户名称" aria-describedby="sizing-addon3">
</div>
<div class="input-group input-group-sm">
  <span class="input-group-addon" id="sizing-addon3">对&nbsp;账&nbsp;&nbsp;员</span>
  <input type="text" class="form-control" placeholder="对账员" aria-describedby="sizing-addon3">
</div>
<div class="input-group input-group-sm">
  <span class="input-group-addon" id="sizing-addon3">所属站点</span>
  <input type="text" class="form-control" placeholder="所属站点" aria-describedby="sizing-addon3">
</div>
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
<div class="form-group">
  <a href="javascript:;" id="su_btn" class="btn btn-primary">查询</a>
</div>

</form>

<table id="list_body" >
</table>	
<script>
$(function () {
	
	mmList({
	    url:url.path+'/wms-web-basicdata/customer/getCustomerList.json',
		onLoadSuccess:function(data){
		
		},  
		
		
		columns: [{
				checkbox: true},{
				field: 'dictPartnerType',title: '是否同行',},{
				field: 'custCode',title: '客户代码'}, {
				field: 'custName',title: '客户名称'}   ,{
				field: 'dictClearingMethod',title: '结算方式'},{ 
				field: 'siteCode',title: '所属站点代码'},{
				field: 'siteName',title: '所属站点名称'},{
				field: 'dictIsSs',title: '自打单'},{
				field: 'fileCount',title: '附件'},{
				field: 'invoiceHeader',title: '发票抬头'},{
				field: 'invoiceAddr',title: '发票地址'},{
			    field: 'custAddr',title: '客户地址'},{
				field: 'financeCode',title: '对账员代码'},{
				field: 'userName',title: '对账员姓名'},{
				field: 'userPhone',title: '对账员电话'},{
				field: 'userEmail',title: '对账员email'},{
				field: 'dictCustStatus',title: '客户状态'},{
				field: 'agreeTimee',title: '签约日期'},{
				field: 'stopTime',title: '停止日期'},{
				field: 'salesAreaCode',title: '销售区域代码'},{	
				field: 'salesAreaName',title: '销售区域名称'},{
				field: 'salesCode',title: '销售员代码'},{
				field: 'salesName',title: '销售员姓名'},{
				field: 'maintainCode',title: '维护员代码'},{
				field: 'maintainName',title: '维护员姓名'},{
				field: 'hasExcelBill',title: 'Excel账单'},{
				field: 'hasPdfBill',title: 'FDK账单'},{
				field: 'hasInvoice',title: '发票'},{	
				field: 'billEmail',title: '账单Email'},{
				field: 'isDisallow',title: '网站限制查件'}, {
				field: 'list',title: '财务联络人'},{
				field: 'list',title: '财务电话',formatter:function(value, row, index){
					var obj = eval('(' + value + ')');
					alert(obj.contactsName);
   				    return value;
				}},{
				field: 'listContacts',title: '财务Email',formatter:function(value, row, index){
					return value;
				}} ,{
				field: 'remark',title: '备注'},{
				field: 'remarkOther',title: '其他备注'},{
				field: 'createOperator',title: '建立人'},{	
				field: 'createTime',title: '建立时间'},{
				field: 'updateOperator',title: '最后修改人'},{			
				field: 'updateTime',title:'最后修改时间'} ]
			
	}); 

//批量查询
$("#bat_query").click(function(){
	var data=$("#cardnos").attr("data");
	if(!data){
		data="";
	}else{
		data = data.replace(/,/g, "\n");
	}
	parent.mmui.open({
		  type: 1,
		  title: "批量查询",
		  shadeClose: false,
		  area: ['1000px', '600px'],
		
/* 		  content: '<textarea style="margin-left:8px;margin-top:8px;width:800px;height:400px;" >'+data+'</textarea>',
 */		 
		  yes: function(index,obj){	
			var text=obj.find("textarea").val();
			  text=mmUtl.utl.text2Str(text);
	
			  $("#cardnos").attr("data",text).val(text);
			  $("#submit_btn").trigger('click');
			  
			  parent.mmui.close(index);
		  },cancel: function(index){},
		  afterFn:function(index,obj){
			  obj.find("textarea").focus();
		  }
		}); 
});


//新增客户资料
$("#submit_btn1").click(function(){
	var data=$("#cardnos").attr("data");
	if(!data){
		data="";
	}else{
		data = data.replace(/,/g, "\n");
	}
	parent.mmui.open({
		  type: 1,
		  title: "新增客户资料",
		  
		  shadeClose: false,
		  area: ['1000px', '600px'],
		  content:'<font color="red">&emsp;客户状态</font><input type="text" style="margin-top:10px;width:200px;"><font color="red">&emsp;&emsp;&emsp;&emsp;签约日期</font><input type="text" style="margin-top:10px;width:200px;">&emsp;&emsp;&emsp;&emsp;<font color="red">所属站点</font><input type="text" style="margin-top:10px;width:200px;"></br><font color="red">&emsp;客户代码</font><input type="text" style="margin-top:10px;width:200px;">&emsp;&emsp;&emsp;&nbsp;&nbsp;账单Email<input type="text" style="margin-top:10px;width:200px;">&emsp;&emsp;&emsp;&nbsp;&nbsp;&nbsp;<font color="red">结算方式</font><input type="text" style="margin-top:10px;width:200px;"></br>&nbsp;&nbsp;&nbsp;<font color="red">客户名称</font><input type="text"style="margin-top:10px;width:517px;"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&emsp;&nbsp;&nbsp;&nbsp;<font color="red">销售区域</font><input type="text" style="margin-top:10px;width:200px;"></br>&nbsp;&nbsp;&nbsp;发票抬头<input type="text"style="margin-top:10px;width:376px;"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;公司地址<input type="text"style="margin-top:10px;width:376px;"/></br>&nbsp;&nbsp;&nbsp;发票地址<input type="text"style="margin-top:10px;width:376px;"/></br>&emsp;&emsp;<input type="checkbox" value="Excel账单"style="margin-top:10px;"/>Excel账单&emsp;&emsp;<input type="checkbox" value="PDK账单"style="margin-top:10px;"/>PDK账单&emsp;&emsp;<input type="checkbox" value="发票"style="margin-top:10px;"/>发票&emsp;&emsp;<input type="checkbox" value="同行"style="margin-top:10px;"/>同行&emsp;&emsp;<input type="checkbox" value="自打单"style="margin-top:10px;"/>自打单</br>&emsp;其他备注<input type="text" style="margin-top:10px;width:500px;"></br><font color="red">&emsp;客户联系人信息</font>',
		  /* content: '<textarea style="margin-left:8px;margin-top:8px;width:800px;height:400px;" >'+data+'</textarea>', */
		  btn:  ['保存'],
		  yes: function(index,obj){	
			var text=obj.find("textarea").val();
			  text=mmUtl.utl.text2Str(text);
	
			  $("#cardnos").attr("data",text).val(text);
			  $("#submit_btn").trigger('click');
			  
			  parent.mmui.close(index);
		  },cancel: function(index){},
		  afterFn:function(index,obj){
			  obj.find("textarea").focus();
		  }
		}); 
}); 

//修改客户资料
$("#btn2").click(function(){
	var data=$("#cardnos").attr("data");
	if(!data){
		data="";
	}else{
		data = data.replace(/,/g, "\n");
	}
	parent.mmui.open({
		  type: 1,
		  title: "修改客户资料",
		  shadeClose: false,
		  area: ['1000px', '600px'],
		  content:'<font color="red">&emsp;客户状态</font><input type="text" style="margin-top:10px;width:200px;"><font color="red">&emsp;&emsp;&emsp;&emsp;签约日期</font><input type="text" style="margin-top:10px;width:200px;">&emsp;&emsp;&emsp;&emsp;<font color="red">所属站点</font><input type="text" style="margin-top:10px;width:200px;"></br><font color="red">&emsp;客户代码</font><input type="text" style="margin-top:10px;width:200px;">&emsp;&emsp;&emsp;&nbsp;&nbsp;账单Email<input type="text" style="margin-top:10px;width:200px;">&emsp;&emsp;&emsp;&nbsp;&nbsp;&nbsp;<font color="red">结算方式</font><input type="text" style="margin-top:10px;width:200px;"></br>&nbsp;&nbsp;&nbsp;<font color="red">客户名称</font><input type="text"style="margin-top:10px;width:517px;"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&emsp;&nbsp;&nbsp;&nbsp;<font color="red">销售区域</font><input type="text" style="margin-top:10px;width:200px;"></br>&nbsp;&nbsp;&nbsp;发票抬头<input type="text"style="margin-top:10px;width:376px;"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;公司地址<input type="text"style="margin-top:10px;width:376px;"/></br>&nbsp;&nbsp;&nbsp;发票地址<input type="text"style="margin-top:10px;width:376px;"/></br>&emsp;&emsp;<input type="checkbox" value="Excel账单"style="margin-top:10px;"/>Excel账单&emsp;&emsp;<input type="checkbox" value="PDK账单"style="margin-top:10px;"/>PDK账单&emsp;&emsp;<input type="checkbox" value="发票"style="margin-top:10px;"/>发票&emsp;&emsp;<input type="checkbox" value="同行"style="margin-top:10px;"/>同行&emsp;&emsp;<input type="checkbox" value="自打单"style="margin-top:10px;"/>自打单</br>&emsp;其他备注<input type="text" style="margin-top:10px;width:500px;">',

	/* 	  content: '<textarea style="margin-left:8px;margin-top:8px;width:800px;height:400px;" >'+data+'</textarea>', */
		  btn: ['保存'],
		  yes: function(index,obj){	
			var text=obj.find("textarea").val();
			  text=mmUtl.utl.text2Str(text);
	
			  $("#cardnos").attr("data",text).val(text);
			  $("#submit_btn").trigger('click');
			  
			  parent.mmui.close(index);
		  },cancel: function(index){},
		  afterFn:function(index,obj){
			  obj.find("textarea").focus();
		  }
		}); 
});
//查看客户资料
$("#subm_btn").click(function(){
	var data=$("#cardnos").attr("data");
	if(!data){
		data="";
	}else{
		data = data.replace(/,/g, "\n");
	}
	parent.mmui.open({
		  type: 1,
		  title: "查看客户资料",
		  shadeClose: false,
		  area: ['1000px', '600px'],
		
/* 		  content: '<textarea style="margin-left:8px;margin-top:8px;width:800px;height:400px;" >'+data+'</textarea>',
 */		 
		  yes: function(index,obj){	
			var text=obj.find("textarea").val();
			  text=mmUtl.utl.text2Str(text);
	
			  $("#cardnos").attr("data",text).val(text);
			  $("#submit_btn").trigger('click');
			  
			  parent.mmui.close(index);
		  },cancel: function(index){},
		  afterFn:function(index,obj){
			  obj.find("textarea").focus();
		  }
		}); 
});
//查询客户资料单个明细
$("#su_btn").click(function(){
	var data=$("#cardnos").attr("data");
	if(!data){
		data="";
	}else{
		data = data.replace(/,/g, "\n");
	}
	parent.mmui.open({
		  type: 1,
		  title: "查看客户资料",
		  shadeClose: false,
		  area: ['1000px', '600px'],
		
/* 		  content: '<textarea style="margin-left:8px;margin-top:8px;width:1000px;height:600px;" >'+data+'</textarea>',
 */		 
		  yes: function(index,obj){	
			var text=obj.find("textarea").val();
			  text=mmUtl.utl.text2Str(text);
	
			  $("#cardnos").attr("data",text).val(text);
			  $("#submit_btn").trigger('click');
			  
			  parent.mmui.close(index);
		  },cancel: function(index){},
		  afterFn:function(index,obj){
			  obj.find("textarea").focus();
		  }
		}); 
});
    
   
});
</script>
		
</body>
</html>
