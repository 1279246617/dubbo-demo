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
		<div style="font-size: 25px; line-height: 45px;" id="titelDetial">新增站点</div>

		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">站点资料明细</h3>
			</div>
			<div class="panel-body">

				<form class="form-inline" id="siteForm">
                    <input type="hidden" name="id" id="id">
					<div class="input-group ">
						<span class="input-group-addon"><font color="red">站点代码</font></span>
						<input type="text" class="form-control width200" name="siteCode" onblur="getCode();" readonly="readonly"
							placeholder="站点代码" id="siteCode">
					</div>

					<div class="input-group ">
						<span class="input-group-addon"><font color="red">站点名称</font></span>
						<input type="text" class="form-control width200" name="siteName"
							placeholder="站点名称" id="siteName">
					</div>

					<div class="input-group ">
						<span class="input-group-addon"><font color="red">所属站点</font></span>
						<input type="text" class="form-control width200"
							name="belongSite" placeholder="所属站点" id="belongSite">
					</div>

					<div class="input-group ">
						<span class="input-group-addon">上级站点</span> <input type="text"
							class="form-control width200" name="parentCode"
							placeholder="上级站点" id="parentCode">
					</div>
					<div class="input-group ">
						<span class="input-group-addon">英文名称</span> <input type="text"
							class="form-control width200" name="siteEsName"
							placeholder="英文名称" id="siteEsName">
					</div>
					<div class="input-group ">
						<span class="input-group-addon"><font color="red">本位币</font></span> 
						<select style="" class="form-control width200" name="cyCode" id="cyCode" >
	                    </select>
	                    <input type="hidden" name="currencyId" id="currencyId" >
					</div>
					<div class="input-group ">
						<span class="input-group-addon"><font color="red">独立核算</font></span> 
						<input type="checkbox"class="form-control width200" id="isAccount" name="isAccount"
									value="is_2" onclick="ban(this)">
						<input type="hidden" name="dictIsAccount" value="is_2">
					</div>
					<!-- <div class="input-group ">
						<label style="width: 200px;"> 
						    <input type="checkbox"class="form-control width100" id="isAccount" name="isAccount"
									value="is_2" onclick="ban(this)"><font style="font-size:25px">独立核算</font>
						</label>
						<input type="hidden" name="dictIsAccount">
					</div> -->
					<div class="input-group ">
						<span class="input-group-addon">备注</span> <input type="text"
							class="form-control width200" name="remark" placeholder="备注" id="remark">
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
	var method = getParam("method");
	initCurrency();
	
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
function get(){
	$("#titelDetial").html("查看站点");
	mmUtl.ajax.getJson(url.path+'/fcs-web-basicdata/basicdata/site/getSite', function(data){
		var site=data.data;
		initForm(site);
	},{
		id:id
    });
};
function initForm(site){
	$("#id").attr("value",id);
	$("#siteCode").attr("value",site.siteCode);
	$("#siteName").attr("value",site.siteName);
	$("#belongSite").attr("value",site.belongSite);
	$("#isAccount").attr("value",site.dictIsAccount);
	$("#cyCode").attr("value",site.cyCode);
	$("#currencyId").attr("value",site.currencyId);
	$("#parentCode").attr("value",site.parentCode);
	$("#remark").attr("value",site.remark);
	$("#cyCode>option").each(function(){
	   	  if($(this).attr("value")==site.cyCode){
	   		 $(this).attr("selected","selected");
	   	  }
	});
	$("input[value='is_1']").attr("checked","checked");
};

function ban(obj){
	if(obj.checked){
		$("input[name='dictIsAccount']").attr("value","is_1");
	}else{
		$("input[name='dictIsAccount']").attr("value","is_2");
	}
};
$("#cyCode").bind("change",function(){
	var id=$(this).find("option:selected").attr("id");
	$("#currencyId").attr("value",id);
});
function add(){
	$("#titelDetial").html("新增站点");
	$("#siteCode").removeAttr("readonly");

	$("#submit").click(function(){
		if(!dataValid()){
			return false;
		}
		 var data = $("#siteForm").serialize();
		 mmUtl.ajax.postJsonAnt(url.path+'/fcs-web-basicdata/basicdata/site/addSite', function(data){
			mmui.oper(data.msg,1); 
			clearForm("siteForm");
		}, mmUtl.ajax.getArgs($("#siteForm")))
	
	});
};

function update(){
	$("#titelDetial").html("修改站点");
	$("#siteCode").removeAttr("onblur");
	$("#submit").click(function(){
		if(!dataValid()){
			return false;
		}
		 var data = $("#siteForm").serialize();
		 mmUtl.ajax.postJsonAnt(url.path+'/fcs-web-basicdata/basicdata/site/updateSite', function(data){
			mmui.oper(data.msg,1); 
			
		}, mmUtl.ajax.getArgs($("#siteForm")));
	
	});
};

//初始化本位币
function initCurrency(){
	  mmUtl.ajax.postJsonSyn(url.path+"/fcs-web-basicdata/basicdata/currency/getAllCurrency",function(data){
		    var resultVo=data.data;
	    	$("#cyCode").append('<option value=""></option>');
	    	 $.each(resultVo,function(i,item){
	              $("#cyCode").append('<option id="'+item.id+'" value="'+item.cyCode+'">'+item.cyCode+'</option>');
	        });
		 
		 },{
			
	   });
};

function dataValid(){
	var siteCode = $.trim($("#siteCode").val());
	var siteName = $.trim($("#siteName").val());
	var belongSite = $.trim($("#belongSite").val());
	var cyCode = $.trim($("#cyCode").val());
	if(siteCode==""||siteName==""||belongSite==""||cyCode==""){
		mmui.oper("红色字体标注的为必填项",1);
		return false;
	}
	return true;
};
function getCode(){
	var siteCode = $.trim($("#siteCode").val());
	if(siteCode == ""){
		setTimeout(function(){$("#siteCode").focus();},1);
		mmui.oper("站点代码不能为空",1); 
		return;
	}
	mmUtl.ajax.postJsonSyn(url.path+'/fcs-web-basicdata/basicdata/site/getSiteByCode.json', function(data){
		if(data.code == 0){
			mmui.oper("站点代码重复",1); 
			setTimeout(function(){$("#siteCode").focus();},1);
		}
	},{
		"siteCode":siteCode
    });
};
</script>
</body>
</html>