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
	<div style="font-size:25px;line-height:45px;" id="titelDetial">新增用户组</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">
		  <h3 class="panel-title">用户组信息</h3>
		</div>
		<div class="panel-body">
			<form class="form-inline" id="addGroupForm">
				<div class="row" style="margin-left:0;margin-right:0;">
					<input type="hidden" name="id"  id="gid" >
					 <div class="input-group ">
						 <span class="input-group-addon"><font color="red">用户组码</font></span>
						 <input type="text" class="form-control width200" 
					  		name="groupCode"  placeholder="用户组码" id="groupCode" onblur="getCode();">
					</div>
					<div class="input-group ">
					  <span class="input-group-addon" id="sizing-addon3"><font color="red">用户组名</font></span>
					  <input type="text" class="form-control width200" 
					  		placeholder="用户组名" name="groupName" id="groupName" >
					</div>
					<div class="input-group ">
					  <span class="input-group-addon" id="sizing-addon3"><font color="red">部门</font></span>
					    <select style="" class="form-control width200" name="dictDept" id="dictDept">
	  					</select>
					</div>
					<div class="input-group " >
					  <span class="input-group-addon" id="sizing-addon3">是否启用</span>
					    <input type="checkbox" class="form-control width200" 
					  		 name="dictIs" id="dictIsUse" value="is_2">
					  		 <input type='hidden' name='dictIsUse' value='is_2' id="is"/>
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
		getGroup();
		update();
	}
	if("get"==method){
		get();
	}
	
});

var initFormData=function(){
	initDict("dictDept","option","dept","dictDept");
}

function add(){
	$("#titelDetial").html("新增用户组");
	$("#submit").click(function(){
		var data = $("#addGroupForm").serialize();
		if(dataValid()){
			mmUtl.ajax.postJsonAnt(url.path+'/wms-web-symgr/symgr/group/addGroup', function(data){
				mmui.oper(data.msg,1);
				clearForm("addGroupForm");
			}, mmUtl.ajax.getArgs($("#addGroupForm")));
		}else{
			mmui.oper("红色字体标注的为必填项",1); 
		}
	});
	
	
}

function update(){
	$("#titelDetial").html("修改用户组");
	$("#groupCode").removeAttr("onblur");
	$("#submit").click(function(){
		if(dataValid()){
			mmUtl.ajax.postJsonAnt(url.path+'/wms-web-symgr/symgr/group/updGroup', function(data){
				mmui.oper(data.msg,1);
			}, mmUtl.ajax.getArgs($("#addGroupForm")));
		}else{
			mmui.oper("红色字体标注的为必填项",1); 
		}
	});
	
	
};
var id= getParam("id");
//获取group数据并初始化表单
function getGroup(){
	var groupCode = getParam("groupCode");
	mmUtl.ajax.postJson(url.path+'/wms-web-symgr/symgr/group/getGroupByCode.json', function(data){
		var group=data.data;
		console.info(data);
		initForm(group);
	},{
		"groupCode":groupCode
    });
}
//初始化表单
function initForm(group){
	console.info(group);
	$("#groupCode").attr("readonly",true); 
	$("#gid").attr("value",id);
	$("#groupCode").attr("value",group.groupCode);
	$("#groupName").attr("value",group.groupName);
	$("#dictIsUse").attr("value",group.dictIsUse);
	$("#is").attr("value",group.dictIsUse);
	$("#dictDept>option").each(function(){
   	  if($(this).attr("value")==group.dictDept){
   		 $(this).attr("selected","selected");
   	  }
    });
	$("input[value='is_1']").attr("checked","checked");	
}

$("#dictIsUse").click(function(){
	if($('input[name="dictIs"]:checked').length == 1){
		$("#is").attr("value","is_1");
	}else{
		$("#is").attr("value","is_2");
	}
})

function getCode(){
	var groupCode = $.trim($("#groupCode").val());
	if(groupCode == ""){
		setTimeout(function(){$("#groupCode").focus();},1);
		mmui.oper("用户组code不能为空",1); 
		return;
	}
	mmUtl.ajax.postJson(url.path+'/wms-web-symgr/symgr/group/getGroupByCode.json', function(data){
		if(data.code == 0){
			mmui.oper("用户组code重复",1); 
			setTimeout(function(){$("#groupCode").focus();},1);
		}
	},{
		"groupCode":groupCode
    });
};

var dataValid=function (){
	var groupCode=$.trim($("#groupCode").val());
	var groupName=$.trim($("#groupName").val());
	var dictDept=$.trim($("#dictDept option:selected").val());
	if(groupCode==""||groupName==""||dictDept==""){
		return false;
	}
	return true;
};

</script>

</body>
</html>