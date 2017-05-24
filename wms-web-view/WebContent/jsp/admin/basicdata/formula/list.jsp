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
		    <button type="button" id="delete" class="btn btn-primary">删除</button>
		    <button type="button" id="export" class="btn btn-primary">导出</button>
		    <button type="button" id="get" class="btn btn-primary">查看</button>
		</div>
		<br/>
		<div class="input-group">
		    <span class="input-group-addon" id="sizing-addon3">公式代码</span>
		    <input type="text" class="form-control" placeholder="公式代码" aria-describedby="sizing-addon3" name="formulaCode" id="formulaCode">
		</div>
		<div class="input-group">
		    <span class="input-group-addon" id="sizing-addon3">对象类型</span>
		    <select style="" class="form-control width200" name="dictObjectType" id="dictObjectType"></select>
		</div>
		<div class="input-group">
		    <span class="input-group-addon" id="sizing-addon3">公式类型</span>
		    <select style="" class="form-control width200" name="dictSubjectType" id="dictSubjectType"></select>
		</div>&emsp;
		<div class="input-group">
	        <input type="checkbox" name="dictIsEstimate" value="is_1" id="dictIsEstimate" />余额预估
	    </div>
	    <div class="input-group input-group-sm">
		    <a href="javascript:;" id="submit_btn" class="btn btn-primary btn-sm">查询</a>
	    </div>
	</form>
<table id="list_body"></table>	
<script>
$(function () {
	mmList({
		url:url.path+'/wms-web-basicdata/basicdata/formula/listFormula.json',
	 	onLoadSuccess:function(data){}, 
		columns: [{checkbox: true}, 
		    {field: 'id',title: 'ID'}, 
		    {field: 'formulaCode',title: '公式代码'},
		    {field: 'includeSubjectCode',title: '包含科目代码'},
		    {field: 'dictObjectType',title: '对象类型'},
		    {field: 'dictSubjectType',title: '公式类型'},
		    {field: 'dictIsEstimate',title: '余额预估',formatter:function(value, row, index){
			    if(row.dictIsEstimate=='是') return '<input type="checkbox" checked="checked" disabled="disabled"/>';
				return '<input type="checkbox" disabled="disabled"/>';
			}},
		    {field: 'dictIsDelayaudit',title: '延期审核',formatter:function(value, row, index){
			    if(row.dictIsDelayaudit=='是') return '<input type="checkbox" checked="checked" disabled="disabled"/>';
				return '<input type="checkbox" disabled="disabled"/>';
			}},
		    {field: 'delayMonths',title: '延期月份'},
		    {field: 'remark',title: '备注'},
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
	//初始化下拉列表
	initDict("dictObjectType","option","object_type");
	initDict("dictSubjectType","option","subject_type");
	
	$("#delete").click(function(){
		var data=$("#list_body").mmuiTable("getSelections");
		if(data.length!=1){
			parent.mmui.alert("请选择一条要删除的数据",3);
			return false;
		}
		var id=data[0].id;
		parent.mmui.confirm("确认删除公式"+data[0].formulaCode,function(delSite){
			parent.mmui.close(delSite);
			mmUtl.ajax.postJson(url.path+"/wms-web-basicdata/basicdata/formula/deleteFormula",function(data){
				   mmui.oper(data.msg,1); 
				   document.location.reload();
			    },{
				   id:id
			    });
			 },function(){
			 
		});
	});

	$("#add").click(function(){
		parent.mmgrid("${ctx}/jsp/admin/basicdata/formula/add.jsp?method=add","add","新增公式",true);
		
	});
	$("#update").click(function(){
		var data=$("#list_body").mmuiTable("getSelections");
		if(data.length!=1){
			parent.mmui.alert("请选择一条要修改的数据",3);
			return false;
		}
		var id=data[0].id;
		parent.mmgrid("${ctx}/jsp/admin/basicdata/formula/add.jsp?method=update&id="+id,"updateFormula","修改公式",true);
	});
	$("#get").click(function(){
		var data=$("#list_body").mmuiTable("getSelections");
		if(data.length!=1){
			parent.mmui.alert("请选择一条要查看的数据",3);
			return false;
		}
		var id=data[0].id;
		parent.mmgrid("${ctx}/jsp/admin/basicdata/formula/add.jsp?method=get&id="+id,"getFormula","查看公式",true);
		
	});

});

</script>
		
</body>
</html>