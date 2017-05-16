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
<input type="hidden" id="ctxPath" value="${ctx}/jsp/admin/">

<form id="toolform" action="order" method="post" class="form-inline" >
	<div class="form-group" id="buttonAll">
<!-- 		<button type="button" id="add" class="btn btn-primary btn-sm" url="symgr/user/add.jsp" pageuid="addUserCard" title="新增用户" onclick="addMethod(this)" >新增</button> -->
<!-- 		<button type="button" id="upd" class="btn btn-primary btn-sm" url="symgr/user/add.jsp" pageuid="addUserCard" title="修改用户" onclick="updMethod(this,userCode)">修改</button> -->
<!-- 		<button type="button" id="del" class="btn btn-primary btn-sm" url="/fcs-web-symgr/symgr/userMgr/delUser" onclick="delMethod(this,userCode)">删除</button> -->
<!-- 		<button type="button" id="export" class="btn btn-primary btn-sm" >导出</button> -->
<!-- 		<button type="button" id="searchRight" class="btn btn-primary btn-sm" onclick="searchRight()">查看权限</button> -->
<!-- 		<button type="button" id="allotGroup" class="btn btn-primary btn-sm" onclick="allotUserGroup();" >分配用户组</button> -->
	</div>
	<br>
	<div class="input-group input-group-sm">
	  <span class="input-group-addon width100" id="sizing-addon3 ">用户ID</span>
	  <input type="text" id="userCode" name="userCode" class="form-control width150" placeholder="用户ID" >
	  
	  <span class="input-group-addon width100" id="sizing-addon3">用户姓名</span>
	  <input type="text" id="userName" name="userName" class="form-control width150" placeholder="用户姓名" >
	  
	  <span class="input-group-addon width100" id="sizing-addon3">所属站点</span>
	  <input type="text" id="siteCode" name="siteCode" class="form-control width150" placeholder="所属站点" >
	  
	  <span class="input-group-addon width100" id="sizing-addon3">所属部门</span>
	  <select style="" class="form-control width150" name="dictDept" id="dictDept">
	  </select>
	</div>
	<div class="input-group input-group-sm">
	  <span class="input-group-addon width100" id="sizing-addon3">所属职位</span>
	  <select style="" class="form-control width150" name="dictPosition" id="dictPosition">
	  </select>
	  
	  <span class="input-group-addon width100" id="sizing-addon3">职位状态</span>
	  <select style="" class="form-control width150" name="dictStatus" id="dictStatus">
	  </select>
	  
	  <span class="input-group-addon width100" id="sizing-addon3">权限启用</span>
	  <select style="" class="form-control width150" name="dictIsEnableRight" id="dictIsEnableRight">
	  </select>
	  
	  <span class="input-group-addon width100" id="sizing-addon3">用户组</span>
	  <select style="" class="form-control width150" name="groupCode" id="groupCode">
	  </select>
	</div>
	
	<div class="input-group input-group-sm">
	  <a href="javascript:;" id="submit_btn" class="btn btn-primary btn-sm">查询</a>
	</div>
	<div class="input-group input-group-sm">
	  <a href="javascript:;" id="clear" class="btn btn-primary btn-sm" onclick="clearForm('toolform')">清空</a>
	</div>

</form>

<table id="list_body" ></table>	
	
<script>
$(function () {
	
	mmList({
		url:url.path+'/fcs-web-symgr/symgr/userMgr/listUser.json',
		onLoadSuccess:function(data){
		},
		columns: [
			{checkbox: true},
			{field: 'isEnableRight',title: '权限启用',formatter:function(value, row, index){
			    if(row.isEnableRight=='是') return '<input type="checkbox" checked="checked" disabled="disabled"/>';
				return '<input type="checkbox" disabled="disabled"/>';
			}}, 
			{field: 'userCode',title: '用户ID'},
			{field: 'userName',title: '用户姓名'},
			{field: 'isEmp',title: '公司员工',formatter:function(value, row, index){
			    if(row.isEmp=='是') return '<input type="checkbox" checked="checked" disabled="disabled"/>';
				return '<input type="checkbox" disabled="disabled"/>';
			}}, 
			{field: 'sex',title: '性别'},
			{field: 'posStatus',title: '职位状态'},
			{field: 'siteCode',title: '所属站点'},
			{field: 'dept',title: '所属部门'},
			{field: 'pos',title: '所属职位'},
			{field: 'groupName',title: '用户组'},
			{field: 'createOperator',title: '创建人'},
			{field: 'createTime',title: '创建时间',formatter:function(value, row, index){
				return mmUtl.time.formatDate(row.createTime,'yyyy-MM-dd hh:mm:ss');
			}},
			{field: 'updateOperator',title: '最后修改人'},
			{field: 'updateTime',title: '最后修改时间',formatter:function(value, row, index){
				return mmUtl.time.formatDate(row.createTime,'yyyy-MM-dd hh:mm:ss');
			}}
		]
	}); 
	//获取用户的button
	getButton();
	
//     $("#add").click(function(){
//     	parent.mmgrid("${ctx}/jsp/admin/symgr/user/add.jsp?method=add","addUserCard","新增用户",true);
//     });
    
//     var selectData = function getSelectData(){
//     	var data=$("#list_body").mmuiTable("getSelections");
//     	if(data.length!=1){
//     		parent.mmui.alert("请选择一条要修改的数据",3);
//     		return null;
//     	}
//     	return data;
//     }
    
//     $("#upd").click(function(){
//        	data = selectData();
//        	if(data == null){
//        		return false;
//        	}
//     	var id = data[0].id
//     	/* parent.window.ssss=data; */
    	
//     	parent.mmgrid("${ctx}/jsp/admin/symgr/user/add.jsp?method=update&id="+id,"addUserCard","修改用户",true);
//     });
//     $("#del").click(function(){
//     	data = selectData();
//        	if(data == null){
//        		return false;
//        	}
//     	var id=data[0].id;
//     	var userCode=data[0].userCode;
    	
//     	parent.mmui.confirm("删除用户"+data[0].userName,function(delUser){
//     		parent.mmui.close(delUser);
    		
//     		mmUtl.ajax.postJson(url.path+"/fcs-web-symgr/symgr/userMgr/delUser",function(data){
//     			mmui.oper(data.msg,1);
//     			document.location.reload();
//     		},{
//     			id:id,
//     			"userCode":userCode
//     		});
// 		},function(){
			 
// 		});
//     });
    
    
//     $("#searchRight").click(function(){
//     	data = selectData();
//        	if(data == null){
//        		return false;
//        	}
//     	var userCode = data[0].userCode;
    	
//     	parent.mmgrid("${ctx}/jsp/admin/symgr/user/searchRight.jsp?userCode="+userCode,"addUserCard","查看权限",true);
//     }); 
    
//     $("#allotGroup").click(function(){
//     	data = selectData();
//        	if(data == null){
//        		return false;
//        	}
//     	var userCode = data[0].userCode;
//     	/* parent.window.ssss=data; */
    	
//     	parent.mmgrid("${ctx}/jsp/admin/symgr/user/allotGroup.jsp?userCode="+userCode,"allotGroup","分配用户组",true);
//     });
    
    //初始化字典信息
    initDict("dictDept","option","dept","dictDept");
    initDict("dictPosition","option","position","dictPosition");
    initDict("dictStatus","option","pos_status","dictStatus"); 
    initDict("dictIsEnableRight","option","is","dictIsEnableRight");
    //初始化用户组信息
    mmUtl.ajax.postJson(url.path+"/fcs-web-symgr/symgr/group/listUserGroup.json",function(data){
		var resultVo=data.data;
		$("#groupCode").append('<option value="">全部</option>');
   	 	$.each(resultVo,function(i,item){
        	$("#groupCode").append('<option value="'+item.groupCode+'">'+item.groupName+'</option>');
       });
	});
    
});

function checkRight(){
	data = selectData();
   	if(data == null){
   		return false;
   	}
	var userCode = data[0].userCode;
	/* parent.window.ssss=data; */
	
	parent.mmgrid("${ctx}/jsp/admin/symgr/user/searchRight.jsp?userCode="+userCode,"addUserCard","查看权限",true);
};
function allotUserGroup(){
	data = selectData();
   	if(data == null){
   		return false;
   	}
	var userCode = data[0].userCode;
	/* parent.window.ssss=data; */
	
	parent.mmgrid("${ctx}/jsp/admin/symgr/user/allotGroup.jsp?userCode="+userCode,"allotGroup","分配用户组",true);
}


</script>
		
</body>
</html>
