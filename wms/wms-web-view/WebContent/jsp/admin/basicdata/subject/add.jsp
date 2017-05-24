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
		<div style="font-size: 25px; line-height: 45px;" id="titelDetial">新增结算科目</div>

		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">科目资料明细</h3>
			</div>
			<div class="panel-body">

				<form class="form-inline" id="subjectForm">
                    <input type="hidden" name="id" id="id">
					<div class="input-group ">
						<span class="input-group-addon"><font color="red">科目代码</font></span>
						<input type="text" class="form-control width200" name="subjectCode" onblur="getCode();" readonly="readonly"
							placeholder="科目代码" id="subjectCode">
					</div>
					<div class="input-group ">
						<span class="input-group-addon"><font color="red">科目名称</font></span>
						<input type="text" class="form-control width200" name="subjectName"
							placeholder="科目名称" id="subjectName">
					</div>
					<div class="input-group">
					    <span class="input-group-addon" id="sizing-addon3"><font color="red">状态</font></span>
					    <select style="" class="form-control width200" name="dictSubjectStatus" id="dictSubjectStatus"></select>
					</div>
					<div class="input-group ">
						<span class="input-group-addon"><font color="red">核算科目</font></span>
						<input type="checkbox"
							class="form-control width200" id="dictIsCheck" name="isCheck"
							value="is_2" onclick="banChange(this)">
					</div>
					<div class="input-group ">
						<span class="input-group-addon"><font color="red">余额预估</font></span>
						<input type="checkbox"
							class="form-control width200" id="dictIsEstimate" name="isEstimate"
							value="is_2" onclick="banChange(this)">
					</div>
					<div class="input-group ">
						<span class="input-group-addon"><font color="red">自动生成</font></span>
						<input type="checkbox"
							class="form-control width200" id="dictIsAuto" name="isAuto"
							value="is_2" onclick="banChange(this)">
					</div>
					<div class="input-group ">
						<span class="input-group-addon"><font color="red">批价</font></span>
						<input type="checkbox"
							class="form-control width200" id="dictIsPrice" name="isPrice"
							value="is_2" onclick="banChange(this)">
					</div>
					<div class="input-group ">
						<span class="input-group-addon">备注</span>
						<input type="text" class="form-control width200"
							name="remark" placeholder="" id="remark">
					</div>
					<input type="hidden" name="dictIsCheck" value="is_2" id="isCheck"/>
					<input type="hidden" name="dictIsEstimate" value="is_2" id="isEstimate">
					<input type="hidden" name="dictIsAuto" value="is_2" id="isAuto">
					<input type="hidden" name="dictIsPrice" value="is_2" id="isPrice">
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
	initDict("dictSubjectStatus","option","subject_status");
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
var id = getParam("id");
var get=function(){
	$("#titelDetial").html("查看结算科目");
	mmUtl.ajax.getJson(url.path+'/wms-web-basicdata/basicdata/subject/getSubject', function(data){
		
		var subject=data.data;
		initForm(subject);
	},{
		id:id
    });
};
//表单回显
function initForm(subject){
	$("#id").attr("value",id);
	$("#subjectCode").attr("value",subject.subjectCode);
	$("#subjectName").attr("value",subject.subjectName);
	$("#dictIsCheck").attr("value",subject.dictIsCheck);
	$("#dictIsEstimate").attr("value",subject.dictIsEstimate);
	$("#dictIsAuto").attr("value",subject.dictIsAuto);
	$("#dictIsPrice").attr("value",subject.dictIsPrice);
	$("#isCheck").attr("value",subject.dictIsCheck);
	$("#isEstimate").attr("value",subject.dictIsEstimate);
	$("#isAuto").attr("value",subject.dictIsAuto);
	$("#isPrice").attr("value",subject.dictIsPrice);
	$("#remark").attr("value",subject.remark);
	$("#dictSubjectStatus>option").each(function(){
	   	  if($(this).attr("value")==subject.dictSubjectStatus){
	   		 $(this).attr("selected","selected");
	   	  }
	});
	$("input[value='is_1']").attr("checked","checked");

	
};
function add(){
	$("#titelDetial").html("新增结算科目");
	$("#subjectCode").removeAttr("readonly");
	$("#submit").click(function(){
		 concat();
		 if(!dataValid()){
			return false;
		 }
		 var data = $("#subjectForm").serialize();
		 mmUtl.ajax.postJsonAnt(url.path+'/wms-web-basicdata/basicdata/subject/addSubject', function(data){
			mmui.oper(data.msg,1);
			clearForm("subjectForm");
		}, mmUtl.ajax.getArgs($("#subjectForm")))
	
	});
};

function update(){
	$("#titelDetial").html("修改结算科目");
	$("#subjectCode").removeAttr("onblur");
	$("#submit").click(function(){
		 concat();
		 if(!dataValid()){
			return false;
		 }
		 var data = $("#subjectForm").serialize();
		 mmUtl.ajax.postJsonAnt(url.path+'/wms-web-basicdata/basicdata/subject/updateSubject', function(data){
			mmui.oper(data.msg,1); 
		}, mmUtl.ajax.getArgs($("#subjectForm")))
	
	});
};

function banChange(obj){
	var name= obj.name;
	if($('input[name='+name+']:checked').length == 1){
		$("#"+name).attr("value","is_1");
	}else{
		$("#"+name).attr("value","is_2");
	}
};
//拼接复选框的value值
function concat(){
	mmUtl.ajax.postJsonSyn(url.path+"/wms-web-symgr/symgr/dictItem/getAllItemByCode",function(data){
	    var resultVo=data.data;
	    var dictSubjectType;
	   
	    	 $.each(resultVo,function(i,item){
	    		 if(i==0){
	    			 
	    			 dictSubjectType=item.itemCode;
	    		 }else{
	    			 dictSubjectType+=","+item.itemCode;
	    		 }
	        });
	    	 $("input[name='dictSubjectType']").attr("value",dictSubjectType);
	   
	 },{
		code:'subject_type',
		codeType:'aaa'
		
	});
} ;
function dataValid(){
	var subjectCode = $.trim($("#subjectCode").val());
	var subjectName = $.trim($("#subjectName").val());
	var dictSubjectStatus = $.trim($("#dictSubjectStatus").val());
	
	if(subjectCode==""||subjectName==""||dictSubjectStatus==""){
		mmui.oper("红色字体标注的为必填项",1); 
		return false;
	}
	return true;
};
function getCode(){
	var subjectCode = $.trim($("#subjectCode").val());
	if(subjectCode == ""){
		setTimeout(function(){$("#subjectCode").focus();},1);
		mmui.oper("科目代码不能为空",1); 
		return;
	}
	mmUtl.ajax.postJsonSyn(url.path+'/wms-web-basicdata/basicdata/subject/getSubjectByCode.json', function(data){
		if(data.code == 0){
			mmui.oper("科目代码重复",1); 
			setTimeout(function(){$("#subjectCode").focus();},1);
		}
	},{
		"subjectCode":subjectCode
    });
};

</script>
</body>
</html>