<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../meta.jsp"%>
<title>管理后台</title>
<style type="text/css">
.form-inline .form-group{
	margin-bottom:8px;
	}
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

<form id="toolform" action="" method="post" class="form-inline" >

<div class="form-group">
  	<button type="button" id="addCurrency" class="btn btn-primary btn-sm" >新增</button>
 	<button type="button" id="updateCurrency" class="btn btn-primary btn-sm">修改</button>
    <button  type="button" id="delCurrency" class="btn btn-primary">删除</button>
</div>



</form>

<table id="list_body" ></table>	
<script>

$(function () {
	
	mmList({
		url:url.path+'/wms-web-basicdata/basicdata/currency/listCurrency.json',
	 	onLoadSuccess:function(data){}, 
		columns: [{checkbox: true},
			{field: 'cyCode',title: '币种代码'},
		    {field: 'cyName',title: '币种名称'},
			{field: 'decimalNum',title: '小数位数'},
			{field: 'createOperator',title: '建立人'},
			{field: 'createTime',title: '建立时间',formatter:function(value, row, index){
				return mmUtl.time.formatDate(row.createTime);
			}},
			{field: 'updateOperator',title: '最后修改人'},
			{field: 'updateTime',title: '最后修改时间',formatter:function(value, row, index){
				return mmUtl.time.formatDate(row.updateTime);
			}}
		]
		
	}); 
	
	$("#addCurrency").click(function(){

		parent.mmgrid("${ctx}/jsp/admin/basicdata/currency/add.jsp?method=add","addCurrency","新增币别",true);
		 
	}); 
	$("#updateCurrency").click(function(){
		var data=$("#list_body").mmuiTable("getSelections");
		
		if(data.length!=1){
			parent.mmui.alert("请选择一条要修改的数据",3);
			return false;
		}
		var id=data[0].id;
		parent.mmgrid("${ctx}/jsp/admin/basicdata/currency/add.jsp?method=update&id="+id,"updateCurrency","修改币别",true);
		
	});
	
	$("#delCurrency").click(function(){
		var data=$("#list_body").mmuiTable("getSelections");
		
		if(data.length!=1){
			parent.mmui.alert("请选择一条要删除的数据",3);
			return false;
		}
		var id=data[0].id;
		
		parent.mmui.confirm("确认删除站点资料"+data[0].cyName,function(delCurrency){
			parent.mmui.close(delCurrency);
			mmUtl.ajax.postJson(url.path+"/wms-web-basicdata/basicdata/currency/deleteCurrency",function(data){
					mmui.oper(data.msg,1); 
					
					document.location.reload(); 
			},{id:id});
			},function(){
			 
		});
	  
	});

});
</script>
		
</body>
</html>
