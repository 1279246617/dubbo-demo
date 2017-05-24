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
		<button type="button" id="add" class="btn btn-primary btn-sm" >新增</button>
		<button type="button" id="del" class="btn btn-primary btn-sm" >删除</button>
	</div>
	<br>
	
	<div class="input-group input-group-sm">
	  <span class="input-group-addon width100" id="sizing-addon3 ">字典类型码</span>
	  <input type="text" id="typeCode" name="typeCode" class="form-control width150" placeholder="字典类型名称" >
	  <span class="input-group-addon width100" id="sizing-addon3 ">字典类型名称</span>
	  <input type="text" id="dictName" name="dictName" class="form-control width150" placeholder="字典类型名称" >
	</div>
	
	<div class="input-group input-group-sm">
	  <a href="javascript:;" id="submit_btn" class="btn btn-primary btn-sm">查询</a>
	</div>
	<div class="input-group input-group-sm">
	  <a href="javascript:;" id="clear" class="btn btn-primary btn-sm">清空</a>
	</div>

</form>

<table id="list_body" ></table>	
	
<script>
$(function () {
	mmList({
		url:url.path+'/wms-web-symgr/symgr/menu/listMenuRight.json',
		onLoadSuccess:function(data){},
		columns: [
			{checkbox: true},
			{field: 'menuCode',title: '菜单权限码'},
			{field: 'menuName',title: '菜单权限名'},
			{field: 'parentName',title: '父菜单名'},
			{field: 'menuEvent',title: '菜单事件URL'},
			{field: 'menuSortNo',title: '菜单权限排序'},
			{field: 'menuStatus',title: '菜单权限状态'},
			{field: 'isCommon',title: '是否是公共资源'},
			{field: 'menuType',title: '菜单类型'},
			{field: 'createOperator',title: '创建人'},
			{field: 'createTime',title: '创建时间'},
			{field: 'updateOperator',title: '修改人'},
			{field: 'updateTime',title: '修改时间'}
		]
	}); 
     
    $("#add").click(function(){
    	addMenuRight();
    });
    
    $("#del").click(function(){
    	var data=$("#list_body").mmuiTable("getSelections");
    	if(data.length!=1){
    		parent.mmui.alert("请选择一条要修改的数据",3);
    		return false;
    	}
    	var id=data[0].id;
    	
    	parent.mmui.confirm("删除菜单权限 '"+data[0].menuName+"' 以及子权限",function(delType){
    		parent.mmui.close(delType);
    		
    		mmUtl.ajax.postJson(url.path+"/wms-web-symgr/symgr/menu/delMenuRight",function(data){
    			mmui.oper(data.msg,1); 
    			document.location.reload();
    		},{
    			id:id
    		});
		},function(){
			 
		});
    });
    
    function addMenuRight(){
    	parent.mmgrid("${ctx}/jsp/admin/symgr/menuRight/addMenuRight.jsp","addMenuRight","新增菜单权限",true);
    }
    
    $("#clear").click(function(){
    	clearForm("toolform");
    });
    
    
});
</script>
		
</body>
</html>
