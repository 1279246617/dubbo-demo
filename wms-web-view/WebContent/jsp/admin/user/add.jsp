<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../meta.jsp"%>
<title>管理后台</title>
<style type="text/css">
.upload-btn{ font-size: 18px;text-align: center;
    background: #00b7ee;
    border-radius: 3px;
    line-height: 44px;
    padding: 0 30px;
    color: #fff;
    display: inline-block;
    margin: 0 auto 20px;
    cursor: pointer;
    box-shadow: 0 1px 1px rgba(0,0,0,.1);}
.upload-btn:hover{ background: #00a2d4;}    
.form-inline .input-group .input-group-addon{width: 100px;}
.form-inline .input-group > .width100{width: 100px;}
.form-inline .input-group > .width200{width: 200px;}
.form-inline .input-group > .width400{width: 400px;}
.form-inline .input-group > .width800{width: 600px;}
.form-inline .input-group > .width800{width: 800px;}
</style>
</head>
<body style="padding-top:12px;overflow-y:scroll;">

<div class="container" style="margin-left:0;">
<div style="font-size:25px;line-height:45px;">新增用户</div>

<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">用户信息</h3>
  </div>
  <div class="panel-body">

<form class="form-inline" id="addUserForm">

<div class="row" style="margin-left:0;margin-right:0;">

 <div class="input-group ">
  <span class="input-group-addon">用户ID</span>
  <input type="text" class="form-control width200" 
  		name="userCode"  placeholder="用户ID" id="userCode" >
</div>

<div class="input-group ">
  <span class="input-group-addon" id="sizing-addon3">用户姓名</span>
  <input type="text" class="form-control width200" 
  		placeholder="用户姓名" name="userName" id="userName" >
</div>

<div class="input-group ">
  <span class="input-group-addon" id="sizing-addon3">性别</span>
    <select style="" class="form-control width200" id="dictSex" name="dictSex" >
   	     <option value="">请选择</option>
   	     <option value="1">男</option>
   	     <option value="2">女</option>
   </select>
</div>

 <div class="input-group ">
  <span class="input-group-addon">所属站点</span>
  <input type="text" class="form-control width200" 
  		id="siteCode" name="siteCode" placeholder="所属站点" >
</div>

  <div class="input-group ">
  <span class="input-group-addon" id="sizing-addon3">所属部门</span>
  <input type="text" class="form-control width200" 
  		id="dictDept" name="dictDept" placeholder="所属部门"  >
</div>

  <div class="input-group ">
  <span class="input-group-addon" id="sizing-addon3">所属职位</span>
  <input type="text" class="form-control width200" 
  		placeholder="所属职位" id="dictPosition" name="dictPosition" >
</div>

  <div class="input-group ">
  <span class="input-group-addon" id="sizing-addon3">职位状态</span>
  <input type="text" class="form-control width200" 
  		placeholder="职位状态" id="dictStatus" name="dictStatus" >
</div>

<!--  <div class="input-group ">
  <span class="input-group-addon" id="sizing-addon3">用户组</span>
  <input type="text" class="form-control width200" 
  		placeholder="用户组" id="userGroup" name="userGroup" >
</div> -->

 <div class="input-group ">
  <span class="input-group-addon" id="sizing-addon3">站点员工</span>
  <select class="form-control width200" id="isEmp" name="isEmp" 
  		style="background-color: #EEEEEE;" disabled="disabled">
   	     <option value="N">否</option>
   </select>
</div>
 
<div class="input-group ">
  <span class="input-group-addon" id="sizing-addon3">密码</span>
  <input type="text" class="form-control width200" 
  		id="password" name="password" readonly="readonly" >
</div>
<div class="input-group ">
  <span class="input-group-addon" id="sizing-addon3">权限启用</span>
  <label style="width: 200px;">
  	<input type="checkbox" class="form-control"  id="isEnableRight" name="isEnableRight">
  </label>
</div>

</div>

<div class="row">
<div class="col-md-2">
<div class="form-group">
	<button type="button" id="submit" class="btn btn-primary btn-sm" >保存</button>
</div>
 </div>
 </div>  
 
</form>

  </div>
</div>

</div>
<script>

$("#submit").click(function(){
	console.info('------come in-------', $("#addUserForm").size());
	var data = $("#addUserForm").serialize();
	console.info('-------------->data:', data);
	console.info('-------------->mmmdata:', mmUtl.ajax.getArgs($("#addUserForm")));
	mmUtl.ajax.postJsonAnt('http://localhost:8080/fcs-web-user/user/userMgr/addUser', function(){
		console.info('--------->success:', arguments);
	}, mmUtl.ajax.getArgs($("#addUserForm")))

})

</script>

</body>
</html>