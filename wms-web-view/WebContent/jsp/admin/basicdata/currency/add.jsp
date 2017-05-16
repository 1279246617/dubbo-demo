<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../meta.jsp"%><title>管理后台</title>
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
<div class="container" style="margin-left: 0;">
	<div style="font-size: 25px; line-height: 45px;" id="titelDetail">新增币别</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">单个币别资料明细</h3>
		</div>
		<div class="panel-body">

			<form class="form-inline" id="currencyForm">
				<input type="hidden" name="id" id="id">
				<div class="input-group ">
					<span class="input-group-addon"><font color="red">币种代码</font></span>
					<input type="text" class="form-control width200" name="cyCode" onblur="getCode();" disabled="disabled"
						placeholder="币种代码" id="cyCode">
				</div>

				<div class="input-group ">
					<span class="input-group-addon"><font color="red">币种名称</font></span>
					<input type="text" class="form-control width200" name="cyName"
						placeholder="币种名称" id="cyName">
				</div>
				<div class="input-group ">
					<span class="input-group-addon">小数位数</span> <input type="text"
						class="form-control width200" name="decimalNum"
						placeholder="小数位数" id="decimalNum">
				</div>

				<div class="row" align="center">
					<div class="col-md-2">

						<div class="form-group">

							<button type="button" id="submit" class="btn btn-primary btn-sm">保存</button>
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
	if("add"==method){
		add();
	}
	if("update"==method){
		
		get();
		update();
	}

});
var id= getParam("id");
function get(){
	$("#titelDetail").html("查看币别");
	mmUtl.ajax.getJson(url.path+'/fcs-web-basicdata/basicdata/currency/getCurrency', function(data){
		var currency=data.data;
		initForm(currency);
	},{
		id:id
    });
};
function initForm(currency){
	$("#id").attr("value",id);
	$("#cyCode").attr("value",currency.cyCode);
	$("#cyName").attr("value",currency.cyName);
	$("#decimalNum").attr("value",currency.decimalNum);
};
function add(){
	$("#titelDetail").html("新增币别");
	$("#cyCode").removeAttr("disabled");
	$("#submit").click(function(){
		if(!dataValid()||!decimalNumIsNum()){
			return false;
		}
	
		 var data = $("#currencyForm").serialize();
		 mmUtl.ajax.postJsonAnt(url.path+'/fcs-web-basicdata/basicdata/currency/addCurrency', function(data){
			mmui.oper(data.msg,1);
			clearForm("currencyForm");
		}, mmUtl.ajax.getArgs($("#currencyForm")))
	});
};

function update(){
	$("#titelDetail").html("修改币别");
	$("#cyCode").removeAttr("onblur");
	$("#submit").click(function(){
		if(!dataValid()||!decimalNumIsNum()){
			return false;
		}
		 var data = $("#currencyForm").serialize();
		 mmUtl.ajax.postJsonAnt(url.path+'/fcs-web-basicdata/basicdata/currency/updateCurrency', function(data){
			mmui.oper(data.msg,1); 
		}, mmUtl.ajax.getArgs($("#currencyForm")))
	
	});
};
function dataValid (){
	var cycode = $.trim($("#cyCode").val());
	var cyName = $.trim($("#cyName").val());
	if(cycode==""||cyName==""){
		mmui.oper("红色字体标注的为必填项",1);
		return false;
	}
	return true;
};
function decimalNumIsNum(){
	var decimalNum = $.trim($("#decimalNum").val());
	if(decimalNum==""){
		return true;
	}
	if(!mmUtl.valid.isInteger(decimalNum)){
		mmui.oper("小数位数必须为正整数",1);
		return false;
	}
	return true;
};
function getCode(){
	var cyCode = $.trim($("#cyCode").val());
	if(cyCode == ""){
		setTimeout(function(){$("#cyCode").focus();},1);
		mmui.oper("币别代码不能为空",1); 
		return;
	}
	mmUtl.ajax.postJsonSyn(url.path+'/fcs-web-basicdata/basicdata/currency/getCyNameByCyCode.json', function(data){
		if(data.code == 0){
			mmui.oper("币别代码重复",1); 
			setTimeout(function(){$("#cyCode").focus();},1);
		}
	},{
		"cyCode":cyCode
    });
};
</script>
</body>
</html>