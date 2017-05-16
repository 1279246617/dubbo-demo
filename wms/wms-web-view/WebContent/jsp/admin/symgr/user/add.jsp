<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../meta.jsp"%>
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
<div style="font-size:25px;line-height:45px;" id="titelDetial">新增用户</div>

<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">用户信息</h3>
  </div>
  <div class="panel-body">

<form class="form-inline" id="addUserForm">


<div class="row" style="margin-left:0;margin-right:0;">

<input type="hidden" id="userId" name="id" >
 <div class="input-group ">
  <span class="input-group-addon"><font color="red">用户ID</font></span>
  <input type="text" class="form-control width200" 
  		name="userCode"  placeholder="用户ID" id="userCode" onblur="getCode();" >
</div>

<div class="input-group ">
  <span class="input-group-addon" id="sizing-addon3"><font color="red">用户姓名</font></span>
  <input type="text" class="form-control width200" 
  		placeholder="用户姓名" name="userName" id="userName" >
</div>

<div class="input-group ">
  <span class="input-group-addon" id="sizing-addon3"><font color="red">性别</font></span>
    <select style="" class="form-control width200" id="dictSex" name="dictSex" >
   	    
    </select>
</div>

 <div class="input-group ">
  <span class="input-group-addon"><font color="red">所属站点</font></span>
  <input type="text" class="form-control width200" 
  		placeholder="所属站点" name="siteCode" id="siteCode" >
</div>

  <div class="input-group ">
  <span class="input-group-addon" id="sizing-addon3"><font color="red">所属部门</font></span>
    <select style="" class="form-control width200" id="dictDept" name="dictDept" >
   	    
    </select>
</div>

  <div class="input-group ">
  <span class="input-group-addon" id="sizing-addon3"><font color="red">所属职位</font></span>
    <select style="" class="form-control width200" id="dictPosition" name="dictPosition" >
   	    
    </select>
</div>

  <div class="input-group ">
  <span class="input-group-addon" id="sizing-addon3"><font color="red">职位状态</font></span>
    <select style="" class="form-control width200" id="dictStatus" name="dictStatus" >
   	    
    </select>
</div>

 <div class="input-group ">
  <span class="input-group-addon" id="sizing-addon3">站点员工</span>
   <select class="form-control width200" id="isEmp" name="isEmp" 
  		style="background-color: #EEEEEE;" disabled="disabled">
   	     <option value="N">否</option>
   </select>
</div>
 
<div class="input-group " id="password">
  <span class="input-group-addon" id="sizing-addon3"><font color="red">密码</font></span>
  <input type="text" class="form-control width200" value="888888"
  		id="userPwd" name="userPwd" readonly="readonly" >
</div>
<div class="input-group " id="group">
  <span class="input-group-addon" id="sizing-addon3"><font color="red">用户组</font></span>
  <select name="userGroup" id="userGroup" class="form-control width200" >
  </select>
</div>
<div class="input-group " >
  <span class="input-group-addon" id="sizing-addon3">权限启用</span>
  <label style="width: 200px;">
  	<input type="checkbox" class="form-control" id="isEnableRight" name="isEnable">
  	<input type='hidden' name='dictIsEnableRight' value='is_2' id="is"/>
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
$(function(){
	var method=getParam("method");
	initFormData();
	if("add" == method){
		add();
	}
	if("update"==method){
		getUser();
		update();
	}
	if("get"==method){
		get();
	}
});
var initFormData=function(){
	mmUtl.ajax.postJson(url.path+"/fcs-web-symgr/symgr/group/listUserGroup.json",function(data){
		var resultVo=data.data;
   	 	$.each(resultVo,function(i,item){
        	$("#userGroup").append('<option value="'+item.groupCode+'">'+item.groupName+'</option>');
       });
	});
	initDict("dictDept","option","dept","dictDept");
    initDict("dictPosition","option","position","dictPosition");
    initDict("dictStatus","option","pos_status","dictStatus"); 
    initDict("dictSex","option","sex","dictSex");
};

function getCode(){
	var userCode = $.trim($("#userCode").val());
	if(userCode == ""){
		setTimeout(function(){$("#userCode").focus();},1);
		mmui.oper("用户code不能为空",1); 
		return;
	}
	mmUtl.ajax.postJsonSyn(url.path+'/fcs-web-symgr/symgr/userMgr/getUserByUserCode', function(data){
		if(data.code == 0){
			mmui.oper("用户code重复",1); 
			setTimeout(function(){$("#userCode").focus();},1);
		}
	}, {
		"userCode":userCode
	});
};
function add(){
	$("#titelDetial").html("新增用户");
	
	$("#submit").click(function(){
		var userCode = $("#userCode").val();
		var groupCode = $("#userGroup").val();
		var data = $("#addUserForm").serialize();
		if(dataValid()){
			mmUtl.ajax.postJsonAnt(url.path+'/fcs-web-symgr/symgr/userMgr/addUser', function(data){
				mmui.oper(data.msg,1); 
			}, mmUtl.ajax.getArgs($("#addUserForm")))
			
			mmUtl.ajax.postJsonAnt(url.path+'/fcs-web-symgr/symgr/userGoup/addUserGroup', function(data){
				mmui.oper(data.msg,1); 
			}, {
				userCode:userCode,
				groupCode:groupCode
			});
		}else{
			mmui.oper("红色字体标注的为必填项",1); 
		}
	})
};

function update(){
	$("#titelDetial").html("修改用户");
	$("#userCode").attr("readonly","readonly");
	$("#userCode").removeAttr("onblur");
	$("#group").remove();
	$("#password").hide();
	$("#submit").click(function(){
		if(dataValid()){
			mmUtl.ajax.postJsonAnt(url.path+'/fcs-web-symgr/symgr/userMgr/updUser', function(data){
				mmui.oper(data.msg,1);
			}, mmUtl.ajax.getArgs($("#addUserForm")))
		}else{
			mmui.oper("红色字体标注的为必填项",1); 
		}
	})
};
function get(){
	$("#titelDetial").html("查看用户");
	getUser();
	 $("#submit").attr("type","hidden");
};
//获取user数据并初始化表单
function getUser(){
	var id=getParam("id");
	mmUtl.ajax.postJson(url.path+'/fcs-web-symgr/symgr/userMgr/getUpdUser.json', function(data){
		var user=data.data;

		initForm(user);
	},{
		id:id
    });
}
//初始化表单
function initForm(user){
	$("#userId").attr("value",user.id);
	$("#userCode").attr("value",user.userCode);
	$("#userName").attr("value",user.userName);
	$("#siteCode").attr("value",user.siteCode);
	$("#userPwd").attr("value",user.userPwd);
	$("#isEnableRight").attr("value",user.isEnableRight);
	$('#is').attr("value",user.isEnableRight);
	$("#dictSex>option").each(function(){
   	  if($(this).attr("value")==user.sex){
   		 $(this).attr("selected","selected");
   	  }
    });
	$("#dictDept>option").each(function(){
   	  if($(this).attr("value")==user.dept){
   		 $(this).attr("selected","selected");
   	  }
    });
	$("#dictPosition>option").each(function(){
   	  if($(this).attr("value")==user.pos){
   		 $(this).attr("selected","selected");
   	  }
    });
	$("#dictStatus>option").each(function(){
	   	  if($(this).attr("value")==user.posStatus){
	   		 $(this).attr("selected","selected");
	   	  }
	    });
	$("input[value='is_1']").attr("checked","checked");	
	
}

$("#isEnableRight").click(function(){
	if($('input[name="isEnable"]:checked').length == 1){
		$('#is').attr("value","is_1");
	}else{
		$('#is').attr("value","is_2");
	}
})


var dataValid=function (){
	var userCode=$.trim($("#userCode").val());
	var userName=$.trim($("#userName").val());
	var siteCode=$.trim($("#siteCode").val());
	var userPwd=$.trim($("#userPwd").val());
	var dictSex=$.trim($("#dictSex option:selected").val());
	var dictStatus=$.trim($("#dictStatus option:selected").val());
	var dictPosition=$.trim($("#dictPosition option:selected").val());
	var dictDept=$.trim($("#dictDept option:selected").val());
	if(userCode==""||userName==""||dictSex==""||siteCode==""||userPwd==""||
			dictStatus==""||dictPosition==""||dictDept==""){
		return false;
	}
	return true;
};

</script>

</body>
</html>