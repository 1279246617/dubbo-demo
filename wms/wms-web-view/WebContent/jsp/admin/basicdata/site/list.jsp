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
		    <span class="input-group-addon" id="sizing-addon3">站点代码</span>
		    <input type="text" class="form-control" placeholder="站点代码" aria-describedby="sizing-addon3" name="siteCode" id="siteCode">
		</div>
		<div class="input-group">
		    <span class="input-group-addon" id="sizing-addon3">站点名称</span>
		    <input type="text" class="form-control" placeholder="站点名称" aria-describedby="sizing-addon3" name="siteName" id="siteName">
		</div>
		<div class="input-group">
		    <span class="input-group-addon" id="sizing-addon3">上级站点</span>
		    <input type="text" class="form-control" placeholder="上级站点" aria-describedby="sizing-addon3" name="parentCode" id="parentCode" data-provide="typeahead"> 
		    
		</div>
		<div class="input-group">
		    <span class="input-group-addon" id="sizing-addon3">所属站点</span>
		    <input type="text" class="form-control" placeholder="所属站点" aria-describedby="sizing-addon3" name="belongSite" id="belongSite" data-provide="typeahead">
		</div>
		&emsp;&emsp;
		<div class="input-group">
		    <input type="checkbox" name="dictIsAccount" id="dictIsAccount" value="is_1">独立核算
		</div>  
		&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
		<div class="input-group input-group-sm">
		    <a href="javascript:;" id="submit_btn" class="btn btn-primary btn-sm">查询</a>
	    </div>
	</form>
<table id="list_body"></table>	
<script>
$(function () {
	mmList({
		url:url.path+'/fcs-web-basicdata/basicdata/site/listSite.json',
	 	onLoadSuccess:function(data){}, 
		columns: [
			{checkbox: true}, 
			{field: 'id',title: '站点id'}, 
			{field: 'siteCode',title: '站点代码'},
		    {field: 'siteName',title: '站点名称'},
			{field: 'parentCode',title: '上级站点'},
			{field: 'belongSite',title: '所属站点'},
			{field: 'siteGrade',title: '级别'},
			{field: 'siteEsName',title: '站点英文名称'},
			{field: 'dictIsAccount',title: '独立核算',formatter:function(value, row, index){
			    if(row.dictIsAccount=='是') return '<input type="checkbox" checked="checked" disabled="disabled"/>';
				return '<input type="checkbox" disabled="disabled"/>';
			}},
			{field: 'cyCode',title: '本位币'},
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
	initSiteOption();
	$("#delete").click(function(){
		var data=$("#list_body").mmuiTable("getSelections");
		if(data.length!=1){
			parent.mmui.alert("请选择一条要删除的数据",3);
			return false;
		}
		var id=data[0].id;
		parent.mmui.confirm("确认删除站点资料"+data[0].siteName,function(delSite){
			parent.mmui.close(delSite);
			mmUtl.ajax.postJson(url.path+"/fcs-web-basicdata/basicdata/site/deleteSite",function(data){
				   mmui.oper(data.msg,1); 
				   document.location.reload();
			    },{
				   id:id
			    });
			 },function(){
			 
		});
	});

	$("#add").click(function(){
		parent.mmgrid("${ctx}/jsp/admin/basicdata/site/add.jsp?method=add","addSite","新增站点",true);
		
	});
	$("#update").click(function(){
		var data=$("#list_body").mmuiTable("getSelections");
		if(data.length!=1){
			parent.mmui.alert("请选择一条要修改的数据",3);
			return false;
		}
		var id=data[0].id;
		parent.mmgrid("${ctx}/jsp/admin/basicdata/site/add.jsp?method=update&id="+id,"updateSite","修改站点",true);
	});
	$("#get").click(function(){
		var data=$("#list_body").mmuiTable("getSelections");
		if(data.length!=1){
			parent.mmui.alert("请选择一条要查看的数据",3);
			return false;
		}
		var id=data[0].id;
		parent.mmgrid("${ctx}/jsp/admin/basicdata/site/add.jsp?method=get&id="+id,"getSite","查看站点",true);
		
	});

});
//初始化站点下拉框
function initSiteOption(){
	  mmUtl.ajax.postJsonSyn(url.path+"/fcs-web-basicdata/basicdata/site/listALLSiteCode",function(data){
		    var resultVo=data.data;
		    $("#parentCode").typeahead({source: resultVo});
		    $("#belongSite").typeahead({source: resultVo});
		 },{
			
	   });
}
</script>
		
</body>
</html>