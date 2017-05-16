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
				<form class="form-inline" id="rateForm">
                    <input type="hidden" name="id" id="id">
                    <input type="hidden" name="currencyId" id="currencyId">
					<div class="input-group ">
						<span class="input-group-addon width150"><font color="red">币别代码</font></span>
						<select style="" class="form-control width150" name="cyCode" id="cyCode"></select>
					</div>
					<br>
					<div class="input-group ">
						<span class="input-group-addon width150"><font color="red">币别名称</font></span>
						<input type="text" class="form-control width150" name="cyName" placeholder="币别名称" id="cyName">
					</div>
					<br>
					<div class="input-group ">
						<span class="input-group-addon width150"><font color="red">汇率</font></span>
						<input type="text" class="form-control width150" name="exrate" placeholder="汇率" id="exrate">
					</div>
					<br>
					<div class="input-group ">
						<span class="input-group-addon width150"><font color="red">生效日期</font></span>
						<input type="text" class="form-control width150" name="usedTime" placeholder="生效日期" id="usedTime">
					</div>
					<br>
					<div class="input-group ">
						<span class="input-group-addon width150"><font color="red">失效日期</font></span>
						<input type="text" class="form-control width150" name="invalidTime" placeholder="失效日期" id="invalidTime">
					</div>
					<br>
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
	var method = getParam("method");
	toDate_("#usedTime");
	toDate_("#invalidTime");
	initCyCode();
	if("add"==method){
		add();
	}
	if("update"==method){
		
		get();
		update();
	}
	
});
var id= getParam("id");
var get=function(){
	mmUtl.ajax.getJson(url.path+'/fcs-web-basicdata/basicdata/rate/getRate', function(data){
		var rate=data.data;
		initForm(rate);
	},{
		id:id
    });
};
//表单回显
function initForm(rate){
	$("#id").attr("value",id);
	$("#currencyId").attr("value",rate.currencyId)
	$("#cyName").attr("value",rate.cyName);
	$("#exrate").attr("value",rate.exrate);
	$("#usedTime").attr("value",mmUtl.time.formatDate(rate.usedTime));
	$("#invalidTime").attr("value",mmUtl.time.formatDate(rate.invalidTime));
	$("#cyCode>option").each(function(){
	   	  if($(this).attr("value")==rate.cyCode){
	   		 $(this).attr("selected","selected");
	   	  }
	});

};
function add(){
	$("#titelDetial").html("新增指定渠道");
	$("#channelCode").removeAttr("disabled");
	$("#submit").click(function(){
		if(!dataValid()){
			return false;
		}
		 var data = $("#rateForm").serialize();
		 mmUtl.ajax.postJsonAnt(url.path+'/fcs-web-basicdata/basicdata/rate/addRate', function(data){
			mmui.oper(data.msg,1);
			clearForm("rateForm");
		}, mmUtl.ajax.getArgs($("#rateForm")))
		
	});
};

function update(){
	$("#titelDetial").html("修改指定渠道");
	$("#channelCode").removeAttr("onblur");
	$("#submit").click(function(){
		if(!dataValid()){
			return false;
		}
		 var data = $("#rateForm").serialize();
		 mmUtl.ajax.postJsonAnt(url.path+'/fcs-web-basicdata/basicdata/rate/updateRate', function(data){
			mmui.oper(data.msg,1); 
		}, mmUtl.ajax.getArgs($("#rateForm")))
		
	});
};
function initCyCode(){
	 mmUtl.ajax.postJsonSyn(url.path+"/fcs-web-basicdata/basicdata/currency/getAllCurrency",function(data){
		 $("#cyCode").append('<option value=""></option>');
		    if(data.code==0){
			    var resultVo=data.data;
		    	$.each(resultVo,function(i,item){
		    		$("#cyCode").append('<option cyName="'+item.cyName+'" id="'+item.id+'" value="'+item.cyCode+'">'+item.cyCode+'</option>');
		        });
		    }
		 },{
	 });
};
$("#cyCode").change(function(){
	var option = $("#cyCode option:selected");
	var cyName = option.attr("cyName");
	var currencyId = option.attr("id");   
	$("#cyName").val(cyName);
	$("#currencyId").val(currencyId);
});
$("#exrate").change(function(){
	var value=$.trim($(this).val());
	if(mmUtl.valid.isFloat_(value)){
		var returnVal=completionDecimal(value,4);
		$("#exrate").val(returnVal);
	}
});
function validSame(cyCode,usedTime){
	var code;
	 mmUtl.ajax.postJsonSyn(url.path+"/fcs-web-basicdata/basicdata/rate/getRateByCodeAndTime",function(data){
           code=data.code;
		 },{
			 cyCode : cyCode, 
			 usedTime : usedTime
	 });
	 if(0!=code){
		 return false;
	 }
	 return true;
}
function dataValid() {
	var cyCode = $.trim($("#cyCode option:selected").val());
	var cyName = $.trim($("#cyName").val());
	var exrate = $.trim($("#exrate").val());
	var usedTime = $.trim($("#usedTime").val());
	var invalidTime = $.trim($("#invalidTime").val());
	if (cyCode == "" || cyName == "" || exrate == "" || usedTime == ""
			|| invalidTime == "") {
		mmui.oper("红色字体标注的为必填项", 1);
		return false;
	}
	if (!mmUtl.valid.isFloat_(exrate)) {
		mmui.oper("汇率必须为数字", 1);
		return false;
	}
	var a = mmUtl.valid.isDate(usedTime);
	var b = mmUtl.valid.isDate(invalidTime);
	if (!a || !b) {
		mmui.oper("日期格式不正确", 1);
		return false;
	}
	var c = dateCompare(usedTime, invalidTime);
	if (!c) {
		mmui.oper("失效日期必须大于生效日期", 1);
		return false;
	}
	if(!validSame(cyCode,usedTime)){
		mmui.oper("同种币别的汇率有效期内不能有两种汇率", 1);
		return false;
	}
	return true;
};
</script>
</body>
</html>