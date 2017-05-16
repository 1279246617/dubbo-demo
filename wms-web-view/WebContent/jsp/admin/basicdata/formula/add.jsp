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
<body style="padding-top: 12px; margin-left: 100px; overflow-y: scroll;">
	<div class="container" style="margin-left: 0;">
		<div style="font-size: 25px; line-height: 45px;" id="titelDetial">新增指定渠道</div>

		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">指定渠道资料明细</h3>
			</div>
			<div class="panel-body">
				<form class="form-inline" id="formulaForm">
                    <input type="hidden" name="id" id="id">
					<div class="input-group ">
						<span class="input-group-addon"><font color="red">公&nbsp;&nbsp;式&nbsp;&nbsp;代&nbsp;&nbsp;码</font></span>
						<input type="text" class="form-control width200" name="formulaCode" onblur="getCode();" readonly="readonly"
							placeholder="公式代码" id="formulaCode">
					</div>
					<br>
		
					<div class="input-group ">
						<span class="input-group-addon"><font color="red">对&nbsp;&nbsp;象&nbsp;&nbsp;类&nbsp;&nbsp;型</font></span>
						<select style="" class="form-control width200" name="dictObjectType" id="dictObjectType">
	                    </select>
						
					</div>
					<br>
                    <div class="input-group ">
						<span class="input-group-addon"><font color="red">公&nbsp;&nbsp;式&nbsp;&nbsp;类&nbsp;&nbsp;型</font></span>
						<select style="" class="form-control width200" name="dictSubjectType" id="dictSubjectType">
	                    </select>
					</div>
					<br>
					<div class="input-group ">
						<span class="input-group-addon"><font color="red">包含科目代码</font></span>
						<input type="text" class="form-control width400" name="includeSubjectCode" placeholder="" id="includeSubjectCode" readonly="readonly">
						<button type="button" id="choose" class="btn btn-primary" >选择</button>
					</div>
					<br>
					<div class="input-group ">
						<label style="width: 200px;"> <input type="checkbox"
							class="form-control width100" id="dictIsEstimate" name="dictIsEstimate"
							value="is_2" onclick="ban(this)"><font style="font-size:25px">余额预估</font>
						</label> <label style="width: 200px;"> <input type="checkbox"
							class="form-control width100" id="dictIsDelayaudit" name="dictIsDelayaudit"
							value="is_2" onclick="ban(this)" ><font style="font-size:25px">延期审核</font>
						</label>
						<br>
						<div class="input-group ">
							<span class="input-group-addon">延&nbsp;&nbsp;期&nbsp;&nbsp;月&nbsp;&nbsp;份</span>
							<input type="text" class="form-control width200" name="delayMonths" placeholder="" id="delayMonths">
						</div>
					</div>
					<br>
					<div class="input-group ">
						<span class="input-group-addon">&emsp;备&emsp;&emsp;注&emsp;</span>
						<input type="text" class="form-control width200"
							name="remark" placeholder="" id="remark">
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
	//初始化下来列表
	initDict("dictObjectType","option","object_type");
	initDict("dictSubjectType","option","subject_type");
	
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
	$("#titelDetial").html("查看账单公式");
	mmUtl.ajax.postJsonSyn(url.path+'/fcs-web-basicdata/basicdata/formula/getFormula', function(data){	
		var formula=data.data;
		initForm(formula);
	},{
		id:id
    });
};
//表单回显
function initForm(formula){
	$("#id").attr("value",id);
	$("#formulaCode").attr("value",formula.formulaCode);
	$("#dictObjectType>option").each(function(){
	   	  if($(this).attr("value")==formula.dictObjectType){
	   		 $(this).attr("selected","selected");
	   	  }
	});
	$("#dictSubjectType>option").each(function(){
		if($(this).attr("value")==formula.dictSubjectType){
			 $(this).attr("selected","selected");
		}
	});
	$("#includeSubjectCode").attr("value",formula.includeSubjectCode);
	$("#dictIsEstimate").attr("value",formula.dictIsEstimate);
	$("#dictIsDelayaudit").attr("value",formula.dictIsDelayaudit)
	$("#delayMonths").attr("value",formula.delayMonths);
	$("#remark").attr("value",formula.remark);
	$("input[value='is_1']").attr("checked","checked");
};
function add(){
	$("#titelDetial").html("新增账单公式");
	$("#formulaCode").removeAttr("readonly");
	$("#submit").click(function(){
		if(!dataValid()){
			return;
		}
		 var data = $("#FormulaForm").serialize();
		 mmUtl.ajax.postJsonAnt(url.path+'/fcs-web-basicdata/basicdata/formula/addFormula', function(data){
			mmui.oper(data.msg,1);
			clearForm("formulaForm");
		}, mmUtl.ajax.getArgs($("#formulaForm")))
	
	});
};

function update(){
	$("#titelDetial").html("修改账单公式");
	$("#formulaCode").removeAttr("onblur");
	$("#submit").click(function(){
		if(!dataValid()){
			return;
		}
		 var data = $("#FormulaForm").serialize();
		 mmUtl.ajax.postJsonAnt(url.path+'/fcs-web-basicdata/basicdata/formula/updateFormula', function(data){
			mmui.oper(data.msg,1); 
		}, mmUtl.ajax.getArgs($("#formulaForm")))
	
	});
};

function ban(obj){
	var name= obj.name;
	if(obj.checked){
		$("input[name="+name+"]").attr("value","is_1");
	}else{
		$("input[name="+name+"]").attr("value","is_2");
	}
};
$("#choose").click(function(){
	var subjectType=$("#dictSubjectType").find("option:selected").attr("value");
	 mmui.open({
		  type: 2,
		  title: '科目代码选择',
		  shadeClose: false,
		  area: ['900px', '600px'],
		  content: '${ctx}/jsp/admin/basicdata/formula/subjectList.jsp?subjectType='+subjectType,
		  btn: ['确认', '取消'],
		  yes: function(index,obj){	
			  var win= mmUtl.iframe.getWin(obj.find("iframe"));
			  var data=win.table.mmuiTable("getSelections");
			  var includeSubjectCode='';
			  for(var i=0;i<data.length;i++){
			  	var next = data[i].subjectCode;
			  	if(i==0) {
			  		includeSubjectCode=next;
			  	}else{
			  		includeSubjectCode+=","+next;
			  	}
			  }
			  $("#includeSubjectCode").attr("value", includeSubjectCode); 
			  mmui.close(index);
		  },cancel: function(index){},

		}); 
});

function dataValid(){
	var formulaCode = $.trim($("#formulaCode").val());
	var dictObjectType = $.trim($("#dictObjectType option:selected").val());
	var dictSubjectType = $.trim($("#dictSubjectType option:selected").val());
	var includeSubjectCode = $.trim($("#includeSubjectCode").val());
	var delayMonths = $.trim($("#delayMonths").val());
	if(""==formulaCode||""==dictObjectType||""==dictSubjectType||""==includeSubjectCode){
		mmui.oper("红色字体标注的为必填项",1);
		return false;
	}
	var b=mmUtl.valid.isInteger(delayMonths);
	if(""!=delayMonths && !b){
		return false;
	}
	return true;
};
function getCode(){
	var formulaCode = $.trim($("#formulaCode").val());
	if(formulaCode == ""){
		setTimeout(function(){$("#formulaCode").focus();},1);
		mmui.oper("客户代码不能为空",1); 
		return;
	}
	mmUtl.ajax.postJsonSyn(url.path+'/fcs-web-basicdata/basicdata/formula/getFormulaByCode.json', function(data){
		if(data.code == 0){
			mmui.oper("客户代码重复",1); 
			setTimeout(function(){$("#formulaCode").focus();},1);
		}
	},{
		"formulaCode":formulaCode
    });
};
</script>
</body>
</html>