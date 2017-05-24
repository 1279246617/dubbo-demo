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
.form-inline .input-group>.width200 {width: 200px;}
.form-inline .input-group>.width400 {width: 400px;}
.form-inline .input-group>.width800 {width: 600px;}
.form-inline .input-group>.width800 {width: 800px;}
</style>
</head>
<body style="padding-top:12px;overflow-y:scroll;">
	<div class="container" style="margin-left: 0;">
		<div style="font-size: 25px; line-height: 45px;" id="titelDetial">新增走货渠道</div>

		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">走货渠道资料明细</h3>
			</div>
			<div class="panel-body">

				<form class="form-inline" id="channelDeliverForm">
                    <input type="hidden" name="id" id="id">
					<div class="input-group ">
						<span class="input-group-addon"><font color="red">渠道代码</font></span>
						<input type="text" class="form-control width200" name="channelCode" onblur="getCode();" readonly="readonly"
							placeholder="渠道代码" id="channelCode">
					</div>
					<div class="input-group ">
						<span class="input-group-addon"><font color="red">渠道名称</font></span>
						<input type="text" class="form-control width200" name="channelName"
							placeholder="渠道名称" id="channelName">
					</div>
					<div class="input-group ">
						<span class="input-group-addon"><font color="red">渠道分类</font></span>
						<input type="text" class="form-control width200"
							name="channelType" placeholder="渠道分类" id="channelType">
					</div>
					<br>
					<div class="input-group ">
						<span class="input-group-addon"><font color="red">业务类型</font></span>
						<select style="" class="form-control width200" name="dictBusinessType" id="dictBusinessType"></select>
		
					</div>
					<div class="input-group ">
						<span class="input-group-addon">供应商代码</span>
						<input type="text" class="form-control width200" name="suppCode"
							placeholder="供应商代码" id="suppCode">
					</div>
					<div class="input-group ">
						<span class="input-group-addon"><font color="red">渠道大类</font></span>
						<input type="text" class="form-control width200"
							name="channelClass" placeholder="渠道大类" id="channelClass">
					</div>
					<br>
					<div class="input-group ">
						<span class="input-group-addon"><font color="red">所属站点</font></span>
						<input type="text" class="form-control width200" name="siteCode"
							placeholder="所属站点" id="siteCode" data-provide="typeahead">
					</div>
					<div class="input-group ">
						<span class="input-group-addon">供应商帐号</span>
						<input type="text" class="form-control width200"
							name="suppNum" placeholder="供应商帐号" id="suppNum">
					</div>
					<div class="input-group">
					    <span class="input-group-addon" id="sizing-addon3"><font color="red">状态</font></span>
					    <select style="" class="form-control width200" name="dictChannelStatus" id="dictChannelStatus"></select>
					</div>
					<br>
					<div class="input-group ">
						<span class="input-group-addon">材积基数</span>
						<input type="text" class="form-control width200"
							name="divSize" placeholder="材积基数" id="divSize">
					</div>
					<div class="input-group ">
						<span class="input-group-addon">备注</span>
						<input type="text" class="form-control width200"
							name="remark" placeholder="" id="remark">
					</div>
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
	initSite();
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
//初始化站点下拉列表
function initSite(){
    mmUtl.ajax.postJsonSyn(url.path+"/wms-web-basicdata/basicdata/site/listALLSiteCode",function(data){
	    var resultVo=data.data;
	    $("#siteCode").typeahead({source: resultVo});
	    
	 },{
		
    });

};
var id= getParam("id");
function get(){
	$("#titelDetial").html("查看走货渠道");
	mmUtl.ajax.getJson(url.path+'/wms-web-basicdata/basicdata/channelDeliver/getChannelDeliver.json', function(data){
		var channelDeliver=data.data;
		initForm(channelDeliver);
	},{
		id:id
    });
};
//表单回显
function initForm(channelDeliver){
	$("#id").attr("value",id);
	$("#channelCode").attr("value",channelDeliver.channelCode);
	$("#channelName").attr("value",channelDeliver.channelName);
	$("#channelType").attr("value",channelDeliver.channelType);
	$("#channelClass").attr("value",channelDeliver.channelClass);
	$("#divSize").attr("value",channelDeliver.divSize);
	$("#suppCode").attr("value",channelDeliver.suppCode);
	$("#siteCode").attr("value",channelDeliver.siteCode);
	$("#suppNum").attr("value",channelDeliver.suppNum);
	$("#remark").attr("value",channelDeliver.remark);
	$("#dictChannelStatus>option").each(function(){
	   	  if($(this).attr("value")==channelDeliver.dictChannelStatus){
	   		 $(this).attr("selected","selected");
	   	  }
	});
	$("#dictBusinessType>option").each(function(){
	   	  if($(this).attr("value")==channelDeliver.dictBusinessType){
	   		 $(this).attr("selected","selected");
	   	  }
	});
	
};
function add(){
	$("#titelDetial").html("新增走货渠道");
	$("#channelCode").removeAttr("readonly");
	$("#submit").click(function(){
		if(!dataValid()||!divSizeIsNum()){
			return false;
		}
	
		 var data = $("#channelDeliverForm").serialize();
		 mmUtl.ajax.postJsonAnt(url.path+'/wms-web-basicdata/basicdata/channelDeliver/addChannelDeliver', function(data){
			mmui.oper(data.msg,1); 
			clearForm("channelDeliverForm");
		}, mmUtl.ajax.getArgs($("#channelDeliverForm")))
	
	});
};

function update(){
	$("#titelDetial").html("修改走货渠道");
	$("#channelCode").removeAttr("onblur");
	$("#submit").click(function(){
		if(!dataValid()||!divSizeIsNum()){
			return false;
		}
	
		 var data = $("#channelDeliverForm").serialize();
		 mmUtl.ajax.postJsonAnt(url.path+'/wms-web-basicdata/basicdata/channelDeliver/updateChannelDeliver', function(data){
			mmui.oper(data.msg,1); 
			
		}, mmUtl.ajax.getArgs($("#channelDeliverForm")))
	
	});
};
function dataValid(){
	var channelCode = $.trim($("#channelCode").val());
	var channelName = $.trim($("#channelName").val());
	var channelType = $.trim($("#channelType").val());
	var channelClass = $.trim($("#channelClass").val());
	var dictBusinessType = $.trim($("#dictBusinessType").val());
	var siteCode = $.trim($("#siteCode").val());
	var dictChannelStatus = $.trim($("#dictChannelStatus").val());
	if(channelCode==""||channelName==""||channelType==""||channelClass==""||dictBusinessType==""||siteCode==""||dictChannelStatus==""){
		mmui.oper("红色字体标注的为必填项",1);
		return false;
	}
	if(!siteIsExits(siteCode)){
		mmui.oper("该站点不存在，请重新输入",1);
		return false;
	}
	return true;
};
function divSizeIsNum(){
	var divSize = $.trim($("#divSize").val());
	if(divSize==""){
		return true;
	}
	if(!mmUtl.valid.isInteger(divSize)){
		mmui.oper("材积基数必须为数字",1);
		return false;
	}
	return true;
};

function siteIsExits(siteCode){
	var code;
	mmUtl.ajax.postJsonSyn(url.path+'/wms-web-basicdata/basicdata/site/getSiteByCode.json', function(data){
		code=data.code
	},{
		"siteCode":siteCode
    });
	if(0!=siteCode){
		return false;
	}
	return true;
};
function getCode(){
	var channelCode = $.trim($("#channelCode").val());
	if(channelCode == ""){
		setTimeout(function(){$("#channelCode").focus();},1);
		mmui.oper("走货渠道代码不能为空",1); 
		return;
	}
	mmUtl.ajax.postJsonSyn(url.path+'/wms-web-basicdata/basicdata/channelDeliver/getChannelDeliverByCode.json', function(data){
		if(data.code == 0){
			mmui.oper("走货渠道代码重复",1); 
			setTimeout(function(){$("#channelCode").focus();},1);
		}
	},{
		"channelCode":channelCode
    });
};
</script>
</body>
</html>