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

	<form id="toolform" action="order" method="post" class="form-inline" >
		<div class="form-group">
			<button type="button" id="add" class="btn btn-primary" >新增</button>
		 	<button type="button" id="update" class="btn btn-primary">修改</button>
		    <button  type="button" id="delete" class="btn btn-primary">删除</button>
		</div>
	</form>
<table id="list_body"></table>	
<script>
$(function () {
	mmList({
		url:url.path+'/wms-web-basicdata/basicdata/rate/listRate.json',
	 	onLoadSuccess:function(data){}, 
		columns: [{checkbox: true}, 
		    {field: 'cyCode',title: '币别代码'},
		    {field: 'cyName',title: '币别名称'},
		    {field: 'exrate',title: '汇率'},
		    {field: 'usedTime',title: '生效日期',formatter:function(value, row, index){
				return mmUtl.time.formatDate_(row.usedTime);
			}},
		    {field: 'invalidTime',title: '失效日期',formatter:function(value, row, index){
				return mmUtl.time.formatDate_(row.invalidTime);
			}}
		  ]
	}); 
	$("#delete").click(function(){
		var data=$("#list_body").mmuiTable("getSelections");
		if(data.length!=1){
			parent.mmui.alert("请选择一条要删除的数据",3);
			return false;
		}
		var id=data[0].id;
		parent.mmui.confirm("确认删除汇率资料",function(delSite){
			parent.mmui.close(delSite);
			mmUtl.ajax.postJson(url.path+"/wms-web-basicdata/basicdata/rate/deleteRate",function(data){
				   mmui.oper(data.msg,1); 
				   document.location.reload();
			    },{
				   id:id
			    });
			 },function(){
			 
		});
	});

	$("#add").click(function(){
		parent.mmgrid("${ctx}/jsp/admin/basicdata/rate/add.jsp?method=add","addRate","新增汇率",true);
		
	});
	$("#update").click(function(){
		var data=$("#list_body").mmuiTable("getSelections");
		if(data.length!=1){
			parent.mmui.alert("请选择一条要修改的数据",3);
			return false;
		}
		var id=data[0].id;
		parent.mmgrid("${ctx}/jsp/admin/basicdata/rate/add.jsp?method=update&id="+id,"updateRate","修改汇率",true);
	});
});

</script>
		
</body>
</html>