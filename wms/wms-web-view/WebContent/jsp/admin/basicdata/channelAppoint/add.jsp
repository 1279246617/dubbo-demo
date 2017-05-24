<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../meta.jsp"%>
<title>管理后台</title>
<style type="text/css">
.upload-btn {
	font-size: 18px;
	text-align: center;
	background: #00b7ee;
	border-radius: 3px;
	line-height: 44px;
	padding: 0 30px;
	color: #fff;
	display: inline-block;
	margin: 0 auto 20px;
	cursor: pointer;
	box-shadow: 0 1px 1px rgba(0, 0, 0, .1);
}
.upload-btn:hover {background: #00a2d4;}
.form-inline .input-group .input-group-addon {width: 100px;}
.form-inline .input-group>.width100 {width: 100px;}
.form-inline .input-group>.width150 {width: 150px;}
.form-inline .input-group>.width200 {width: 200px;}
.form-inline .input-group>.width400 {width: 400px;}
.form-inline .input-group>.width800 {width: 600px;}
.form-inline .input-group>.width800 {width: 800px;}
</style>
</head>
<body style="padding-top:12px;overflow-y:scroll;">
	<div class="container" style="margin-left: 0;">
		<div style="font-size: 25px; line-height: 45px;" id="titelDetial">新增指定渠道</div>

		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">指定渠道资料明细</h3>
			</div>
			<div class="panel-body">
				<form class="form-inline" id="channelAppointForm">
                    <input type="hidden" name="id" id="id">
					<div class="input-group ">
						<span class="input-group-addon width150"><font color="red">渠道代码</font></span>
						<input type="text" class="form-control width150" name="channelCode" onblur="getCode();" disabled="disabled"
							placeholder="渠道代码" id="channelCode">
					</div>
					<div class="input-group ">
						<span class="input-group-addon width150"><font color="red">渠道名称</font></span>
						<input type="text" class="form-control width150" name="channelName"
							placeholder="渠道名称" id="channelName">
					</div>
					<div class="input-group ">
						<span class="input-group-addon width150"><font color="red">渠道分类</font></span>
						<input type="text" class="form-control width150"
							name="channelType" placeholder="渠道分类" id="channelType">
					</div>
					<div class="input-group ">
						<span class="input-group-addon width150"><font color="red">业务类型</font></span>
						<select style="" class="form-control width150" name="dictBusinessType" id="dictBusinessType"></select>
					</div>
					<div class="input-group ">
						<span class="input-group-addon width150">限定走货渠道</span>
						<input type="text" class="form-control width150"
							name="channelLimit" placeholder="限定走货渠道" id="channelLimit">
					</div>
					<div class="input-group ">
						<span class="input-group-addon width150"><font color="red">渠道大类</font></span>
						<input type="text" class="form-control width150"
							name="channelClass" placeholder="渠道大类" id="channelClass">
					</div>
					<div class="input-group ">
						<span class="input-group-addon width150">起始重量</span>
						<input type="text" class="form-control width150"
							name="beginWeight" placeholder="起始重量" id="beginWeight">
					</div>
					<div class="input-group ">
						<span class="input-group-addon width150">终止重量</span>
						<input type="text" class="form-control width150"
							name="endWeight" placeholder="终止重量" id="endWeight">
					</div>
					<div class="input-group ">
						<span class="input-group-addon width150">材积基数</span>
						<input type="text" class="form-control width150"
							name="divSize" placeholder="材积基数" id="divSize">
					</div>
					<div class="input-group">
					    <span class="input-group-addon width150" id="sizing-addon3"><font color="red">状态</font></span>
					    <select style="" class="form-control width150" name="dictChannelStatus" id="dictChannelStatus"></select>
					</div>
					<div class="input-group ">
						<span class="input-group-addon width150">备注</span>
						<input type="text" class="form-control width150"
							name="remark" placeholder="" id="remark">
					</div>
					<div class="input-group">
					    <span class="input-group-addon width150" id="sizing-addon3">步进值</span>
					    <select style="" class="form-control width150" name="dictStepByStep" id="dictStepByStep"></select>
					</div>
					<div class="input-group">
					    <span class="input-group-addon width150" id="sizing-addon3">重量单件取大</span>
					    <input type="checkbox"
							class="form-control width150" id="dictIsPcsMaxWeig" name="dictIsPcsMaxWeig"
							value="is_2" onclick="ban(this)">
					</div>
					<div class="input-group">
					    <span class="input-group-addon width150" id="sizing-addon3">单件重量进位</span>
						<input type="checkbox"
							class="form-control width150" id="dictIsPcsWeigCarry" name="dictIsPcsWeigCarry"
							value="is_2" onclick="ban(this)" >
					</div>
					<!-- <div class="input-group ">
						<label style="width: 200px;"> <input type="checkbox"
							class="form-control width100" id="dictIsPcsMaxWeig" name="dictIsPcsMaxWeig"
							value="is_2" onclick="ban(this)"><font style="font-size:25px">重量单件取大</font>
						</label> <label style="width: 200px;"> <input type="checkbox"
							class="form-control width100" id="dictIsPcsWeigCarry" name="dictIsPcsWeigCarry"
							value="is_2" onclick="ban(this)" ><font style="font-size:25px">单件重量进位</font>
						</label>
					</div> -->
					
					<br>
					<br>
					<p><font color="red">步进值: （仅用于操作重量计算）</font><br>
					        &emsp;&emsp;&emsp;&emsp;①0.5000说明:0.0001~0.4999进0.5000; 0.5001~0.9999进1.0000<br>
					        &emsp;&emsp;&emsp;&emsp;②1.0000说明:0.0001~0.9999进1.0000
					</p>
					<input type="hidden" name="dictIsPcsMaxWeig" value="is_2">
					<input type="hidden" name="dictIsPcsWeigCarry" value="is_2">
					<div class="row" align="center">
						<div class="col-md-2">
							<div class="form-group">
								<input type="button" id="submit" class="btn btn-primary btn-sm" value="保存" />
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
<script>
$(function(){
	//初始化下来列表
	initDict("dictBusinessType","option","business_type");
	initDict("dictChannelStatus","option","channel_status");
	initDict("dictStepByStep","option","step_by_step");
	
	var method = getParam("method");
	if("add"==method){
		add();
	}
	if("update"==method){
		
		get();
		update();
	}
	if("get"==method){
		$("#submit").attr("type","hidden");
		get();
	}
	
});
var id= getParam("id");
var get=function(){
	$("#titelDetial").html("查看指定渠道");
	mmUtl.ajax.getJson(url.path+'/wms-web-basicdata/basicdata/channelAppoint/getChannelAppoint', function(data){
		
		var channelAppoint=data.data;
		initForm(channelAppoint);
	},{
		id:id
    });
};
//表单回显
function initForm(channelAppoint){
	$("#id").attr("value",id);
	$("#channelCode").attr("value",channelAppoint.channelCode);
	$("#channelName").attr("value",channelAppoint.channelName);
	$("#channelType").attr("value",channelAppoint.channelType);
	$("#channelLimit").attr("value",channelAppoint.channelLimit);
	$("#channelClass").attr("value",channelAppoint.channelClass);
	$("#beginWeight").attr("value",channelAppoint.beginWeight);
	$("#endWeight").attr("value",channelAppoint.endWeight);
	$("#divSize").attr("value",channelAppoint.divSize);
	$("#dictChannelStatus>option").each(function(){
	   	  if($(this).attr("value")==channelAppoint.dictChannelStatus){
	   		 $(this).attr("selected","selected");
	   	  }
	});
	$("#dictBusinessType>option").each(function(){
	   	  if($(this).attr("value")==channelAppoint.dictBusinessType){
	   		 $(this).attr("selected","selected");
	   	  }
	});
	
	$("#dictStepByStep>option").each(function(){
	   	  if($(this).attr("value")==channelAppoint.dictStepByStep){
	   		 $(this).attr("selected","selected");
	   	  }
	});
	$("#remark").attr("value",channelAppoint.remark);
	$("input[name=dictIsPcsMaxWeig]").attr("value",channelAppoint.dictIsPcsMaxWeig);
	$("input[name=dictIsPcsWeigCarry]").attr("value",channelAppoint.dictIsPcsWeigCarry);
	$("input[value='is_1']").attr("checked","checked");
	forbidCommit();
};
function add(){
	$("#titelDetial").html("新增指定渠道");
	forbidCommit();
	$("#channelCode").removeAttr("disabled");
	$("#submit").click(function(){
		if(!dataValid()||!beginWeightIsNum()||!endWeightIsNum()||!divSizeIsNum()){
			return false;
		}
		 var data = $("#channelAppointForm").serialize();
		 mmUtl.ajax.postJsonAnt(url.path+'/wms-web-basicdata/basicdata/channelAppoint/addChannelAppoint', function(data){
			mmui.oper(data.msg,1);
			clearForm("channelAppointForm");
		}, mmUtl.ajax.getArgs($("#channelAppointForm")))
		
	});
};

function update(){
	$("#titelDetial").html("修改指定渠道");
	forbidCommit();
	$("#channelCode").removeAttr("onblur");
	$("#submit").click(function(){
		if(!dataValid()||!beginWeightIsNum()||!endWeightIsNum()||!divSizeIsNum()){
			return false;
		}
			
		 var data = $("#channelAppointForm").serialize();
		 mmUtl.ajax.postJsonAnt(url.path+'/wms-web-basicdata/basicdata/channelAppoint/updateChannelAppoint', function(data){
			mmui.oper(data.msg,1); 
		}, mmUtl.ajax.getArgs($("#channelAppointForm")))
		
	});
};

function ban(obj){
	var name= obj.name;
	if(obj.checked){
		$("input[name="+name+"]").attr("value","is_1");
	}else{
		$("input[name="+name+"]").attr("value","is_2");
	}
	forbidCommit();
};
function forbidCommit(){
	var value=$("#dictIsPcsWeigCarry").attr("value");
	if("is_1"==value){
	   $("#dictStepByStep").removeAttr("disabled");
	}else{
	   $("#dictStepByStep").attr("disabled","disabled");
	}

};

function dataValid(){
	var channelCode=$.trim($("#channelCode").val());
	var channelName=$.trim($("#channelName").val());
	var channelType=$.trim($("#channelType").val());
	var channelClass=$.trim($("#channelClass").val());
	var dictChannelStatus=$.trim($("#dictChannelStatus").val());
	var dictBusinessType=$.trim($("#dictBusinessType").val());
	if(channelCode==""||channelName==""||channelType==""||channelClass==""||beginWeight==""||endWeight==""||dictChannelStatus==""||dictBusinessType==""){
		mmui.oper("红色字体标注的为必填项",1);
		return false;
	}

	return true;
};
function beginWeightIsNum(){
	var beginWeight=$.trim($("#beginWeight").val());
	if(beginWeight==""){
		return true;
	}
	var b=mmUtl.valid.isFloat_(beginWeight);
	if(!b){
		mmui.oper("起始重量必须为数字",1);
		return false;
	}
	return true;
};
function endWeightIsNum(){
	var endWeight=$.trim($("#endWeight").val());
	if(endWeight==""){
		return true;
	}
	var b=mmUtl.valid.isFloat_(endWeight);
	if(!b){
		mmui.oper("终止重量必须为数字",1);
		return false;
	}
	return true;
};
function divSizeIsNum(){
	var divSize=$.trim($("#divSize").val());
	if(divSize==""){
		return true;
	}
	var b=mmUtl.valid.isInteger(divSize);
	if(!b){
		mmui.oper("材积基数必须为正整数",1);
		return false;
	}
	return true;
};
function getCode(){
	var channelCode = $.trim($("#channelCode").val());
	if(channelCode == ""){
		setTimeout(function(){$("#channelCode").focus();},1);
		mmui.oper("指定渠道代码不能为空",1); 
		return;
	}
	mmUtl.ajax.postJsonSyn(url.path+'/wms-web-basicdata/basicdata/channelAppoint/getChannelAppointByCode', function(data){
		if(data.code == 0){
			mmui.oper("指定渠道代码重复",1); 
			setTimeout(function(){$("#channelCode").focus();},1);
		}
	},{
		"channelCode":channelCode
    });
};
</script>
</body>
</html>