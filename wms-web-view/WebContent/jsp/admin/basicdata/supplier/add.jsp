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
		<div style="font-size: 25px; line-height: 45px;" id="titelDetial">新增供应商</div>

		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">供应商资料明细</h3>
			</div>
			<div class="panel-body">

				<form class="form-inline" id="supplierForm">
                    <input type="hidden" name="id" id="id">
					<div class="input-group ">
						<span class="input-group-addon"><font color="red">供应商代码</font></span>
						<input type="text" class="form-control width200" name="suppCode" onblur="getCode();" disabled="disabled"
							placeholder="供应商代码" id="suppCode">
					</div>

					<div class="input-group ">
						<span class="input-group-addon"><font color="red">供应商名称</font></span>
						<input type="text" class="form-control width200" name="suppName"
							placeholder="供应商名称" id="suppName">
					</div>

					<div class="input-group ">
						<span class="input-group-addon"><font color="red">所属站点</font></span>
						<input type="text" class="form-control width200"
							name="siteCode" placeholder="所属站点" id="siteCode">
					</div>
					<div class="input-group ">
						<span class="input-group-addon">结算方式</span> 
					    <select style="" class="form-control width200" name="dictClearingType" id="dictClearingType"></select>
					</div>
					<div class="input-group">
					    <span class="input-group-addon" id="sizing-addon3"><font color="red">供应商状态</font></span>
					    <select style="" class="form-control width200" name="dictSuppStatus" id="dictSuppStatus"></select>
					</div>
					<div class="input-group">
					    <span class="input-group-addon" id="sizing-addon3"><font color="red">对账员</font></span>
					    <input type="text" class="form-control width200" name="financeCode" placeholder="对账员" id="financeCode" data-provide="typeahead">
					    <input type="hidden" name="financeName" id="financeName">
					</div>
					<div class="input-group ">
						<span class="input-group-addon">备注</span> <input type="text"
							class="form-control width200" name="remark" placeholder="备注" id="remark">
					</div>
					<div class="input-group">
					    <span class="input-group-addon" id="sizing-addon3"><font color="red">本位币</font></span>
					    <input type="text" class="form-control width200" name="currency" placeholder="本位币" id="currency" data-provide="typeahead">
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
	initDict("dictClearingType","option","clearing_type");
	initDict("dictSuppStatus","option","supp_status");
	initFinanceCode();
	initCurrency();
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
var id= getParam("id");
function get(){
	$("#titelDetial").html("查看供应商");
	mmUtl.ajax.getJson(url.path+'/wms-web-basicdata/basicdata/supplier/getSupplier', function(data){
		var supplier=data.data;
		initForm(supplier);
	},{
		id:id
    });
};
function initForm(supplier){
	$("#id").attr("value",id);
	$("#suppCode").attr("value",supplier.suppCode);
	$("#suppName").attr("value",supplier.suppName);
	$("#siteCode").attr("value",supplier.siteCode);
	$("#currency").attr("value",supplier.currency);
	$("#financeCode").attr("value",supplier.financeCode);
	$("#dictClearingType").attr("value",supplier.dictClearingType);
	$("#remark").attr("value",supplier.remark);
	$("#dictClearingType>option").each(function(){
	   	  if($(this).attr("value")==supplier.dictClearingType){
	   		 $(this).attr("selected","selected");
	   	  }
	});
	$("#dictSuppStatus>option").each(function(){
	   	  if($(this).attr("value")==supplier.dictSuppStatus){
	   		 $(this).attr("selected","selected");
	   	  }
	});
};
function add(){
	$("#titelDetial").html("新增供应商");
	$("#suppCode").removeAttr("disabled");
	$("#submit").click(function(){
		 if(!dataValid()){
			return false;
		 }
		 if(!initFinanceName()){
			 return false;
		 }
		 var data = $("#supplierForm").serialize();
		 mmUtl.ajax.postJsonAnt(url.path+'/wms-web-basicdata/basicdata/supplier/addSupplier', function(data){
			mmui.oper(data.msg,1); 
			clearForm("siteForm");
		}, mmUtl.ajax.getArgs($("#supplierForm")))
	
	});
};

function update(){
	$("#titelDetial").html("修改供应商");
	$("#suppCode").removeAttr("onblur");
	$("#submit").click(function(){
		 if(!dataValid()){
			return false;
		 }
		 if(!initFinanceName()){
			 return false;
		 }
		 var data = $("#supplierForm").serialize();
		 mmUtl.ajax.postJsonAnt(url.path+'/wms-web-basicdata/basicdata/supplier/updateSupplier', function(data){
			mmui.oper(data.msg,1); 
		}, mmUtl.ajax.getArgs($("#supplierForm")))
	
	});
};
 function initFinanceName(){
	 var dataCode;
	 var financeCode=$.trim($("#financeCode").val());
		mmUtl.ajax.postJsonSyn(url.path+'/wms-web-symgr/symgr/userMgr/getUserByUserCode', function(data){
			var user=data.data;
			dataCode=data.code;
			if(0!=data.code){
				parent.mmui.alert("该对账员不存在",3);
				return false;
			}
			$("#financeName").attr("value",user.userName);
		
		},{
			userCode:financeCode
	    });
		return dataCode==0;
};

function initFinanceCode(){
	 mmUtl.ajax.postJsonSyn(url.path+"/wms-web-symgr/symgr/userMgr/getAllUserCode",function(data){
		    var resultVo=data.data;
	    	$("#financeCode").typeahead({source: resultVo});
		 },{
	    });
};

function initCurrency(){
	 mmUtl.ajax.postJsonSyn(url.path+"/wms-web-basicdata/basicdata/currency/getAllCyCode",function(data){
		    var resultVo=data.data;
	    	$("#currency").typeahead({source: resultVo});
		 },{
	  });
};
//初始化站点下拉列表
function initSite(){
    mmUtl.ajax.postJsonSyn(url.path+"/wms-web-basicdata/basicdata/site/listALLSiteCode",function(data){
	    var resultVo=data.data;
	    $("#siteCode").typeahead({source: resultVo});
	    
	 },{
		
    });

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
function financeIsExits(userCode){
	var code;
	mmUtl.ajax.postJsonSyn(url.path+'/wms-web-symgr/symgr/userMgr/getUserByUserCode', function(data){
		code=data.code;
	},{
		userCode:userCode
    });
	if(0!=code){
		return false;
	}
	return true;
}
function dataValid(){
	var suppCode = $.trim($("#suppCode").val());
	var suppName = $.trim($("#suppName").val());
	var siteCode = $.trim($("#siteCode").val());
	var currency = $.trim($("#currency").val());
	var financeCode = $.trim($("#financeCode").val());
	var dictSuppStatus = $.trim($("#dictSuppStatus").val());
	if(suppCode==""||suppName==""||siteCode==""||currency==""||financeCode==""||dictSuppStatus==""){
		mmui.oper("红色字体标注的为必填项",1);
		return false;
	}
	if(!siteIsExits(siteCode)){
		mmui.oper("该站点不存在，请重新输入",1);
		return false;
	}
	if(!financeIsExits(userCode)){
		mmui.oper("该对账员不存在，请重新输入",1);
		return false;
	}
	return true;
};
function getCode(){
	var suppCode = $.trim($("#suppCode").val());
	if(suppCode == ""){
		setTimeout(function(){$("#suppCode").focus();},1);
		mmui.oper("供应商代码不能为空",1); 
		return;
	}
	mmUtl.ajax.postJsonSyn(url.path+'/wms-web-basicdata/basicdata/supplier/getSupplierByCode.json', function(data){
		if(data.code == 0){
			mmui.oper("供应商代码重复",1); 
			setTimeout(function(){$("#suppCode").focus();},1);
		}
	},{
		"suppCode":suppCode
    });
};
</script>
</body>
</html>