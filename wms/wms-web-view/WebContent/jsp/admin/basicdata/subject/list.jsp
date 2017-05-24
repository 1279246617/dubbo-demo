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
		    <button type="button" id="export" class="btn btn-primary">导出</button>
		    <button  type="button" id="get" class="btn btn-primary">查看</button>
		</div>
		<br/>
		<div class="input-group">
		    <span class="input-group-addon" id="sizing-addon3">科目代码</span>
		    <input type="text" class="form-control" placeholder="科目代码" aria-describedby="sizing-addon3" name="subjectCode" id="subjectCode">
		</div>
		<div class="input-group">
		    <span class="input-group-addon" id="sizing-addon3">科目名称</span>
		    <input type="text" class="form-control" placeholder="科目名称" aria-describedby="sizing-addon3" name="subjectName" id="subjectName">
		</div>
		<div class="input-group">
		    <span class="input-group-addon" id="sizing-addon3">状态</span>
		    <select style="" class="form-control width200" name="dictSubjectStatus" id="dictSubjectStatus"></select>
		</div>
		<div class="input-group">
	        <input type="checkbox" name="dictIsCheck" value="is_1" id="dictIsCheck" />核算科目
	    </div>
		<div class="input-group">
	        <input type="checkbox" name="dictIsEstimate" value="is_1" id="dictIsEstimate" />余额预估
	    </div>
		<div class="input-group">
	        <input type="checkbox" name="dictIsAuto" value="is_1" id="dictIsAuto" />自动生成
	    </div>
		<div class="input-group">
	        <input type="checkbox" name="dictIsPrice" value="is_1" id="dictIsPrice" />批价
	    </div>
	    <div class="input-group input-group-sm">
		    <a href="javascript:;" id="submit_btn" class="btn btn-primary btn-sm">查询</a>
	    </div>
	</form>
<table id="list_body"></table>	
<script>
$(function () {
	mmList({
		url:url.path+'/wms-web-basicdata/basicdata/subject/listSubject.json',
	 	onLoadSuccess:function(data){}, 
		columns: [{checkbox: true}, 
		    {field: 'subjectCode',title: '科目代码'},
		    {field: 'subjectName',title: '科目名称'},
		    {field: 'dictIsCheck',title: '核算科目',formatter:function(value, row, index){
			    if(row.dictIsCheck=='是') return '<input type="checkbox" checked="checked" disabled="disabled"/>';
				return '<input type="checkbox" disabled="disabled"/>';
			}},
		    {field: 'dictSubjectType',title: '收支类别'}, 
		    {field: 'dictIsEstimate',title: '余额预估',formatter:function(value, row, index){
			    if(row.dictIsEstimate=='是') return '<input type="checkbox" checked="checked" disabled="disabled"/>';
				return '<input type="checkbox" disabled="disabled"/>';
			}},
		    {field: 'dictIsAuto',title: '自动生成',formatter:function(value, row, index){
			    if(row.dictIsAuto=='是') return '<input type="checkbox" checked="checked" disabled="disabled"/>';
				return '<input type="checkbox" disabled="disabled"/>';
			}},
		    {field: 'dictIsPrice',title: '批价',formatter:function(value, row, index){
			    if(row.dictIsPrice=='是') return '<input type="checkbox" checked="checked" disabled="disabled"/>';
				return '<input type="checkbox" disabled="disabled"/>';
			}},
		    {field: 'remark',title: '备注'},
		    {field: 'dictSubjectStatus',title: '状态'},
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
	//初始化下来列表
	initDict("dictSubjectStatus","option","subject_status");
	$("#delete").click(function(){
		var data=$("#list_body").mmuiTable("getSelections");
		if(data.length!=1){
			parent.mmui.alert("请选择一条要删除的数据",3);
			return false;
		}
		var subjectCode=data[0].subjectCode;
		parent.mmui.confirm("确认删除结算科目资料"+data[0].subjectName,function(delSite){
			parent.mmui.close(delSite);
			mmUtl.ajax.postJson(url.path+"/wms-web-basicdata/basicdata/subject/deleteSubject",function(data){
				   mmui.oper(data.msg,1); 
				   document.location.reload();
			    },{
			    	subjectCode:subjectCode
			    });
			 },function(){
			 
		});
	});

	$("#add").click(function(){
		parent.mmgrid("${ctx}/jsp/admin/basicdata/subject/add.jsp?method=add","addSubject","新增结算科目",true);
		
	});
	$("#update").click(function(){
		var data=$("#list_body").mmuiTable("getSelections");
		if(data.length!=1){
			parent.mmui.alert("请选择一条要修改的数据",3);
			return false;
		}
		var id=data[0].id;
		parent.mmgrid("${ctx}/jsp/admin/basicdata/subject/add.jsp?method=update&id="+id,"updateSubject","修改结算科目",true);
	});
	$("#get").click(function(){
		var data=$("#list_body").mmuiTable("getSelections");
		if(data.length!=1){
			parent.mmui.alert("请选择一条要查看的数据",3);
			return false;
		}
		var id=data[0].id;
		parent.mmgrid("${ctx}/jsp/admin/basicdata/subject/add.jsp?method=get&id="+id,"getSubject","查看结算科目",true);
		
	});

});

</script>
		
</body>
</html>