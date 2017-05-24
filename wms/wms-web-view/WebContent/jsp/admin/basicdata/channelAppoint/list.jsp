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
		    <span class="input-group-addon" id="sizing-addon3">渠道代码</span>
		    <input type="text" class="form-control" placeholder="渠道代码" aria-describedby="sizing-addon3" name="channelCode" id="channelCode">
		</div>
		<div class="input-group">
		    <span class="input-group-addon" id="sizing-addon3">渠道名称</span>
		    <input type="text" class="form-control" placeholder="渠道名称" aria-describedby="sizing-addon3" name="channelName" id="channelName">
		</div>
		<div class="input-group">
		    <span class="input-group-addon" id="sizing-addon3">渠道分类</span>
		  <input type="text" class="form-control" placeholder="渠道分类" aria-describedby="sizing-addon3" name="channelType" id="channelType">
		</div>&emsp;
		<div class="input-group">
		    <span class="input-group-addon" id="sizing-addon3">状态</span>
		    <select style="" class="form-control width200" name="dictChannelStatus" id="dictChannelStatus"></select>
		</div>
	    <div class="input-group input-group-sm">
		    <a href="javascript:;" id="submit_btn" class="btn btn-primary btn-sm">查询</a>
	    </div>
	</form>
<table id="list_body"></table>	
<script>
$(function () {
	mmList({
		url:url.path+'/wms-web-basicdata/basicdata/channelAppoint/listChannelAppoint.json',
	 	onLoadSuccess:function(data){}, 
		columns: [{checkbox: true}, 
		    {field: 'channelCode',title: '渠道代码'},
		    {field: 'channelName',title: '渠道名称'},
		    {field: 'channelType',title: '渠道分类'},
		    {field: 'channelClass',title: '渠道大类'},
		    {field: 'channelLimit',title: '限定走货渠道'},
		    {field: 'dictBusinessType',title: '业务类型'},
		    {field: 'divSize',title: '材积基数'},
		    {field: 'dictIsPcsMaxWeig',title: '重量单件取大',formatter:function(value, row, index){
			    if(row.dictIsPcsMaxWeig=='是') return '<input type="checkbox" checked="checked" disabled="disabled"/>';
				return '<input type="checkbox" disabled="disabled"/>';
			}},
		    {field: 'dictIsPcsWeigCarry',title: '单件重量进位',formatter:function(value, row, index){
			    if(row.dictIsPcsWeigCarry=='是') return '<input type="checkbox" checked="checked" disabled="disabled"/>';
				return '<input type="checkbox" disabled="disabled"/>';
			}},
		    {field: 'beginWeight',title: '起始重量'},
		    {field: 'endWeight',title: '终止重量'},
		    {field: 'dictChannelStatus',title: '状态'},
		    {field: 'remark',title: '备注'},
		    {field: 'createOperator',title: '建立人'},
		    {field: 'createTime',title: '建立时间',formatter:function(value, row, index){
				return mmUtl.time.formatDate(row.createTime);
			}},
		    {field: 'updateOperator',title: '最后修改人'},
		    {field: 'updateTime',title:'最后修改时间',formatter:function(value, row, index){
				return mmUtl.time.formatDate(row.updateTime);
			}},
		  ]
	}); 
	//初始化下来列表
	initDict("dictChannelStatus","option","channel_status");
	
	$("#delete").click(function(){
		var data=$("#list_body").mmuiTable("getSelections");
		if(data.length!=1){
			parent.mmui.alert("请选择一条要删除的数据",3);
			return false;
		}
		var id=data[0].id;
		parent.mmui.confirm("确认删除指定渠道资料"+data[0].channelName,function(delSite){
			parent.mmui.close(delSite);
			mmUtl.ajax.postJson(url.path+"/wms-web-basicdata/basicdata/channelAppoint/deleteChannelAppoint",function(data){
				   mmui.oper(data.msg,1); 
				   document.location.reload();
			    },{
				   id:id
			    });
			 },function(){
			 
		});
	});

	$("#add").click(function(){
		parent.mmgrid("${ctx}/jsp/admin/basicdata/channelAppoint/add.jsp?method=add","addChannelAppoint","新增指定渠道",true);
		
	});
	$("#update").click(function(){
		var data=$("#list_body").mmuiTable("getSelections");
		if(data.length!=1){
			parent.mmui.alert("请选择一条要修改的数据",3);
			return false;
		}
		var id=data[0].id;
		parent.mmgrid("${ctx}/jsp/admin/basicdata/channelAppoint/add.jsp?method=update&id="+id,"updateChannelAppoint","修改指定渠道",true);
	});
	$("#get").click(function(){
		var data=$("#list_body").mmuiTable("getSelections");
		if(data.length!=1){
			parent.mmui.alert("请选择一条要查看的数据",3);
			return false;
		}
		var id=data[0].id;
		parent.mmgrid("${ctx}/jsp/admin/basicdata/channelAppoint/add.jsp?method=get&id="+id,"getChannelAppoint","查看指定渠道",true);
		
	});

});

</script>
		
</body>
</html>