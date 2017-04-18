<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../meta.jsp"%>
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
	</div>
	<div class="form-group">
		<button type="button" id="upd" class="btn btn-primary btn-sm" >修改</button>
	</div>
	<div class="form-group">
		<button type="button" id="del" class="btn btn-primary btn-sm" >删除</button>
	</div>
	<div class="form-group">
		<button type="button" id="export" class="btn btn-primary btn-sm" >导出</button>
	</div>
	<div class="form-group">
		<button type="button" id="searchRight" class="btn btn-primary btn-sm" >查看权限</button>
	</div>
	
	<div class="input-group input-group-sm">
	  <span class="input-group-addon width100" id="sizing-addon3 ">用户ID</span>
	  <input type="text" id="userCode" name="userCode" class="form-control width150" placeholder="用户ID" >
	  
	  <span class="input-group-addon width100" id="sizing-addon3">用户姓名</span>
	  <input type="text" id="userName" name="userName" class="form-control width150" placeholder="用户姓名" >
	  
	  <span class="input-group-addon width100" id="sizing-addon3">所属站点</span>
	  <input type="text" id="siteCode" name="siteCode" class="form-control width150" placeholder="所属站点" >
	  
	  <span class="input-group-addon width100" id="sizing-addon3">所属部门</span>
	  <input type="text" id="dictDept" name="dictDept" class="form-control width150" placeholder="所属部门" >
	</div>
	<div class="input-group input-group-sm">
	  <span class="input-group-addon width100" id="sizing-addon3">所属职位</span>
	  <input type="text" id="dictPosition"  name="dictPosition" class="form-control width150" placeholder="所属职位" >
	  
	  <span class="input-group-addon width100" id="sizing-addon3">职位状态</span>
	  <input type="text" id="dictStatus" name="dictStatus" class="form-control width150" placeholder="职位状态" >
	  
	  <span class="input-group-addon width100" id="sizing-addon3">权限启用</span>
	  <input type="text" id="dictIsEnableRight" name="dictIsEnableRight" class="form-control width150" placeholder="权限启用" >
	  
	  <span class="input-group-addon width100" id="sizing-addon3">用户组</span>
	  <input type="text" id="groupCode" name="groupCode" class="form-control width150" placeholder="用户组" >
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
		url:'http://localhost:8080/fcs-web-user/user/userMgr/listUser.json',
		onLoadSuccess:function(data){},
		columns: [
			{checkbox: true},
			{field: 'isEnableRight',title: '权限启用',formatter:function(value, row, index){
			    if(row.cusCharge=='1') return '<input type="checkbox" checked="checked" disabled="disabled"/>';
				return '<input type="checkbox" disabled="disabled"/>';
			}}, 
			{field: 'id',title: 'ID'},
			{field: 'userCode',title: '用户ID'},
			{field: 'userName',title: '用户姓名'},
			{field: 'isEmp',title: '公司员工',formatter:function(value, row, index){
			    if(row.cusCharge=='是') return '<input type="checkbox" checked="checked" disabled="disabled"/>';
				return '<input type="checkbox" disabled="disabled"/>';
			}}, 
			{field: 'sex',title: '性别'},
			{field: 'posStatus',title: '职位状态'},
			{field: 'siteCode',title: '所属站点'},
			{field: 'dept',title: '所属部门'},
			{field: 'pos',title: '所属职位'},
			{field: 'userGroup',title: '用户组'},
			{field: 'createOperator',title: '创建人'},
			{field: 'createTime',title: '创建时间'},
			{field: 'updateOperator',title: '最后修改人'},
			{field: 'updateTime',title: '最后修改时间'}
		]
	}); 
     
    $("#add").click(function(){
    	addUser();
    });
    
    $("#del").click(function(){
    	var data=$("#list_body").mmuiTable("getSelections");
    	if(data.length!=1){
    		parent.mmui.alert("请选择一条要修改的数据",3);
    		return false;
    	}
    	var id=data[0].id;
    	
    	parent.mmui.confirm("删除用户"+data[0].userName,function(delUser){
    		parent.mmui.close(delUser);
    		
    		mmUtl.ajax.postJson("http://localhost:8080/fcs-web-user/user/userMgr/delUser",function(data){
    			mmui.oper(data.msg,1); 
    			document.location.reload();
    		},{
    			id:id
    		});
		},function(){
			 
		});
    });
    function addUser(){
    	parent.mmgrid("${ctx}/jsp/admin/user/add.jsp","addUserCard","新增用户",true);
    };
    
    $("#clear").click(function(){
    	clearForm("toolform");
    });
    
});
</script>
		
</body>
</html>
