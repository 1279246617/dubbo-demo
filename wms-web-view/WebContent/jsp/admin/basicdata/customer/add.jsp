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
	<div class="container" style="margin-left: 0;">
		<div style="font-size: 25px; line-height: 45px;" id="titelDetial">新增用户</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">单个客户资料</h3>
			</div>
			<div class="panel-body">
				<form class="form-inline" id="customerForm">
                    <input type="hidden" name="id" id="id">
					<div class="row" style="margin-left: 0; margin-right: 0;">
						<div class="input-group ">
							<span class="input-group-addon"><font color="red">客户状态</font></span> <select style=""
								class="form-control width200" id="dictCustStatus"
								name="dictCustStatus">
							</select>
						</div>
						<div class="input-group ">
							<span class="input-group-addon" id="sizing-addon3"><font color="red">签约日期</font></span> <input
								type="text" class="form-control width200" placeholder="签约日期"
								name="agreeTime" id="agreeTime">
						</div>
						<div class="input-group ">
							<span class="input-group-addon" id="sizing-addon3"><font color="red">所属站点</font></span> <input
								type="text" class="form-control width200" placeholder=""
								id="siteCode" name="siteCode" data-provide="typeahead">
						</div>
						<div class="input-group ">
							<span class="input-group-addon" id="sizing-addon3"><font color="red">客户代码</font></span> <input
								type="text" class="form-control width200" id="custCode" onblur="getCode();" readonly="readonly"
								name="custCode" placeholder="客户代码">
						</div>
						<div class="input-group ">
							<span class="input-group-addon" id="sizing-addon3">账单Email</span>
							<input type="text" class="form-control width200"
								placeholder="账单Email" id="billEmail" name="billEmail">
						</div>
						<div class="input-group ">
							<span class="input-group-addon"><font color="red">结算方式</font></span> <select style=""
								class="form-control width200" id="dictClearingMethod"
								name="dictClearingMethod"></select>
						</div>
						<div class="input-group ">
							<span class="input-group-addon" id="sizing-addon3"><font color="red">客户名称</font></span> <input
								type="text" class="form-control width200" placeholder="客户名称"
								id="custName" name="custName">
						</div>

						<div class="input-group ">
							<span class="input-group-addon" id="sizing-addon3">销售区域</span> <input
								type="text" class="form-control width200" placeholder="销售区域" id="salesareaName"
								name="salesareaName">
						</div>
						<div class="input-group ">
							<span class="input-group-addon" id="sizing-addon3">发票抬头</span> <input
								type="text" class="form-control width200" placeholder="发票抬头" id="invoiceHeader"
								name="invoiceHeader">
						</div>
						<div class="input-group ">
							<span class="input-group-addon" id="sizing-addon3">公司地址</span> <input
								type="text" class="form-control width200" placeholder="公司地址" id="custAddr"
								name="custAddr">
						</div>
						<div class="input-group ">
							<span class="input-group-addon" id="sizing-addon3">发票地址</span> <input
								type="text" class="form-control width200" placeholder="发票地址" id="invoiceAddr"
								name="invoiceAddr">
						</div>
						<br>
						<div class="input-group ">
							<label style="width: 200px;"> <input type="checkbox"
								class="form-control width100" id="dictIsExcelBill" name="isExcelBill"
								value="is_2" onclick="ban(this)"><font style="font-size:25px">Excel账单</font>
							</label> <label style="width: 200px;"> <input type="checkbox"
								class="form-control width100" id="dictIsPdfBill" name="isPdfBill"
								value="is_2" onclick="ban(this)"><font style="font-size:25px">PDF账单</font>
							</label> <label style="width: 200px;"> <input type="checkbox"
								class="form-control width100" id="dictIsInvoice" name="isInvoice"
								value="is_2" onclick="ban(this)"><font style="font-size:25px">发票</font>
							</label> <label style="width: 200px;"> <input type="checkbox"
								class="form-control width100" id="dictPartnerType" name="partnerType"
								value="partner_type_2"><font style="font-size:25px">同行</font>
							</label> <label style="width: 200px;"> <input type="checkbox"
								class="form-control width100" id="dictIsSs" name="isSs" value="is_2"
								onclick="ban(this)"><font style="font-size:25px">自打单</font>
							</label>
						</div>
						<input type="hidden" name="dictIsExcelBill" value="is_2" id="isExcelBill">
						<input type="hidden" name="dictIsPdfBill" value="is_2" id="isPdfBill">
						<input type="hidden" name="dictIsInvoice" value="is_2"id="isInvoice">
						<input type="hidden" name="dictPartnerType" value="partner_type_2" id="partnerType">
						<input type="hidden" name="dictIsSs" value="is_2" id="isSs">
						<div class="input-group ">
							<span class="input-group-addon" id="sizing-addon3">其它备注</span> <input
								type="text" class="form-control width800" id="remarkOther"
								name="remarkOther">
						</div>

						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title"><font color="red">客户联系人信息</font></h3>
							</div>
							<div class="panel-body"></div>
							<div>
								<table>
									<thead>
										<tr>
											<th colspan="2"><font color="red">联系</font>人</th>
											<th><font color="red">客户部门</font></th>
											<th>客户联系电话</th>
											<th>客户Email</th>
											<th>备注1</th>
										</tr>
									</thead>
									<tbody id="contacts">
										<tr>
											<td><input name="contactsId0" type="hidden" /></td>
											<td><input name="contactsName0" type="text" /></td>
											<td><input name="contactsDept0" type="text" /></td>
											<td><input name="contactsPhone0" type="text" /></td>
											<td><input name="contactsEmail0" type="text" /></td>
											<td><input name="remark0" type="text" /></td>
										</tr>
										<tr>
											<td><input name="contactsId1" type="hidden" /></td>
											<td><input name="contactsName1" type="text" /></td>
											<td><input name="contactsDept1" type="text" /></td>
											<td><input name="contactsPhone1" type="text" /></td>
											<td><input name="contactsEmail1" type="text" /></td>
											<td><input name="remark1" type="text" /></td>
										</tr>
										<tr>
											<td><input name="contactsId2" type="hidden" /></td>
											<td><input name="contactsName2" type="text" /></td>
											<td><input name="contactsDept2" type="text" /></td>
											<td><input name="contactsPhone2" type="text" /></td>
											<td><input name="contactsEmail2" type="text" /></td>
											<td><input name="remark2" type="text" /></td>
										</tr>
									</tbody>
								</table>
							</div>

							<div class="panel panel-default">
								<div class="panel-heading">
									<h3 class="panel-title">公司联系人信息</h3>
								</div>
								<div class="panel-body"></div>
								<div>
									<table>
										<tr>
											<th><font color="red">对账员代码</font></th>
											<th>对账员姓名</th>
											<th>部门</th>
											<th>联系电话</th>
											<th>Email</th>
										</tr>
										<tr>
											<td><input name="financeCode" id="financeCode"
												type="text" data-provide="typeahead"/></td>
											<td><input name="userName" id="userName" type="text"
												readonly="readonly" /></td>
											<td><input name="dictDept" id="dictDept" type="text"
												readonly="readonly" /></td>
											<td><input name="userPhone" id="userPhone" type="text"
												readonly="readonly" /></td>
											<td><input name="userEmail" id="userEmail" type="text"
												readonly="readonly" /></td>
										</tr>
										<tr>
											<th><font color="red">销售员代码</font></th>
											<th>销售员姓名</th>
											<th>维护员代码</th>
											<th>维护员姓名</th>
										</tr>
										<tr>
											<td><input name="salesCode" id="salesCode" type="text" data-provide="typeahead"/></td>
											<td><input name="salesName" id="salesName" type="text"
												readonly="readonly" /></td>
											<td><input name="maintainCode" id="maintainCode"
												type="text" data-provide="typeahead"/></td>
											<td><input name="maintainName" id="maintainName"
												type="text" readonly="readonly" /></td>

										</tr>
									</table>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-2">
								<div class="form-group">
									<input type="button" id="submit" class="btn btn-primary btn-sm"
										value="保存" />
								</div>
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
	
	initDict("dictCustStatus","option","cust_status");
	initDict("dictClearingMethod","option","clearing_method");
	initSite();
	initUserCode();
	toDate("#agreeTime");
	if("add" == method){
		add();
	}
	if("update"==method){
		getCustomer();
		update();
	}
	if("get"==method){
		get();
	}
	
	
	
});

var initUserCode=function(){
	mmUtl.ajax.postJson(url.path+"/fcs-web-symgr/symgr/userMgr/getAllUserCode",function(data){
	    var resultVo=data.data;
	    $('#salesCode').typeahead({source: resultVo});
	    $('#maintainCode').typeahead({source: resultVo});
	    $('#financeCode').typeahead({source: resultVo});
	
	 },{
	});
	
};
//初始化站点下拉列表
function initSite(){
	
    mmUtl.ajax.postJsonSyn(url.path+"/fcs-web-basicdata/basicdata/site/listALLSiteCode",function(data){
	    var resultVo=data.data;
	    $("#siteCode").typeahead({source: resultVo});
	    
	 },{
		
    });

};

function ban(obj){
	var name= obj.name;
	if($('input[name='+name+']:checked').length == 1){
		$("#"+name).attr("value","is_1");
	}else{
		$("#"+name).attr("value","is_2");
	}
};
$("#dictPartnerType").click(function(){
	
	if($('input[name=partnerType]:checked').length == 1){
		$("#partnerType").attr("value","partner_type_1");
	}else{
		$("#partnerType").attr("value","partner_type_2");
	}
});

function add(){
	$("#titelDetial").html("新增客户");
	$("#custCode").removeAttr("readonly");
	$("#submit").click(function(){
		if(!dataValid()){
			return false;
		}
		var data = $("#customerForm").serialize();
		mmUtl.ajax.postJsonAnt(url.path+'/fcs-web-basicdata/basicdata/customer/addCustomer', function(data){
			mmui.oper(data.msg,1); 
			if(data.code==0){
				clearForm("customerForm");
			}
		}, mmUtl.ajax.getArgs($("#customerForm")));
			
			

    });
};
function update(){
	$("#titelDetial").html("修改客户");
	$("#custCode").removeAttr("onblur");
	$("#submit").click(function(){
		if(!dataValid()){
			return false;
		}
		var data = $("#customerForm").serialize();
		mmUtl.ajax.postJsonAnt(url.path+'/fcs-web-basicdata/basicdata/customer/updateCustomer', function(data){
			mmui.oper(data.msg,1); 

			$("#submit").unbind();
			
		}, mmUtl.ajax.getArgs($("#customerForm")));
		
	});
};
function get(){
	$("#titelDetial").html("查看客户");
	 getCustomer();
	 $("#submit").attr("type","hidden");
};
var id=getParam("id");
//获取customer数据并初始化表单
function getCustomer(){
	$("#titelDetial").html("查看客户");
	mmUtl.ajax.postJsonSyn(url.path+'/fcs-web-basicdata/basicdata/customer/getCustomer', function(data){
		 
        var customer=data.data;
	    initForm(customer);
	    },{
		id:id
    });
}

//初始化表单
function initForm(customer){
	$("#id").attr("value",id);
	$("#dictCustStatus").attr("value",customer.dictCustStatus);
	$("#agreeTime").attr("value",mmUtl.time.formatDate(customer.agreeTime));
	$("#custCode").attr("value",customer.custCode);
	$("#billEmail").attr("value",customer.billEmail);
	$("#custName").attr("value",customer.custName);
	$("#salesareaName").attr("value",customer.salesareaName);
	$("#invoiceHeader").attr("value",customer.invoiceHeader);
	$("#custAddr").attr("value",customer.custAddr);
	$("#invoiceAddr").attr("value",customer.invoiceAddr);
	$("#dictIsExcelBill").attr("value",customer.dictIsExcelBill);
	$("#dictIsPdfBill").attr("value",customer.dictIsPdfBill);
	$("#dictIsInvoice").attr("value",customer.dictIsInvoice);
	$("#dictIsSs").attr("value",customer.dictIsSs);
	$("#siteCode").attr("value",customer.siteCode);
	$("#financeCode").attr("value",customer.financeCode);
	$("#userName").attr("value",customer.userName);
	$("#dictDept").attr("value",customer.dictDept);
	$("#userPhone").attr("value",customer.userPhone);
	$("#userEmail").attr("value",customer.userEmail);
	$("#salesCode").attr("value",customer.salesCode);
	$("#salesName").attr("value",customer.salesName);
	$("#maintainCode").attr("value",customer.maintainCode);
	$("#maintainName").attr("value",customer.maintainName);
	$("#dictPartnerType").attr("value",customer.dictPartnerType);
	$("#dictClearingMethod>option").each(function(){
   	  if($(this).attr("value")==customer.dictClearingMethod){
   		 $(this).attr("selected","selected");
   	  }
    });
  	$("#dictCustStatus>option").each(function(){
   	  if($(this).attr("value")==customer.dictCustStatus){
   		 $(this).attr("selected","selected");
   	  }
    });
	$("input[value='is_1']").attr("checked","checked");
	$("input[value='partner_type_1']").attr("checked","checked");
	var contactsList=customer.contactsList;
	if(null!=contactsList && contactsList.length>0){
		for(var i=0;i<contactsList.length;i++){
			if(contactsList[i] != null){
				
				$("input[name='contactsId"+i+"']").attr("value",contactsList[i].id);
				$("input[name='contactsName"+i+"']").attr("value",contactsList[i].contactsName);
				$("input[name='contactsDept"+i+"']").attr("value",contactsList[i].contactsDept);
				$("input[name='contactsPhone"+i+"']").attr("value",contactsList[i].contactsPhone);
				$("input[name='contactsEmail"+i+"']").attr("value",contactsList[i].contactsEmail);
				$("input[name='remark"+i+"']").attr("value",contactsList[i].remark);
			}
		}
	}
};

//根据userCode查询详细信息
function getFinance(){
    var userCode=$("#financeCode").val();
    var code;
	mmUtl.ajax.postJsonSyn(url.path+'/fcs-web-symgr/symgr/userMgr/getUserByUserCode', function(data){
		var user=data.data;
		code=data.code;
		if(0!=code){
			mmui.oper("该对账员不存在",1);
			return false;
			}
		$("#userName").attr("value",user.userName);
		$("#dictDept").attr("value",user.dept);
		$("#userPhone").attr("value",user.userPhone);
		$("#userEmail").attr("value",user.userEmail);
	},{
		userCode:userCode
    });
	if(0!=code){
		return false;
	}
	return true;
};
function getSales(){
	var code;
    var userCode=$("#salesCode").val();
	mmUtl.ajax.postJsonSyn(url.path+'/fcs-web-symgr/symgr/userMgr/getUserByUserCode', function(data){
		var user=data.data;
		code=data.code;
		if(0!=code){
			mmui.oper("该销售员不存在,请重新输入",1);
		return false;
		}
		$("#salesName").attr("value",user.userName);
	
	},{
		userCode:userCode
    });
	if(0!=code){
		return false;
	}
	return true;
};
function getMaintain(){
	var userCode=$("#maintainCode").val();
	var code;
	mmUtl.ajax.postJsonSyn(url.path+'/fcs-web-symgr/symgr/userMgr/getUserByUserCode', function(data){
		var user=data.data;
		code=data.code;
		if(0!=code){
			mmui.oper("该维护员不存在,请重新输入",1);
			return false;
			}
		$("#maintainName").attr("value",user.userName);
	
	}, {
		userCode:userCode
    });
	if(0!=code){
		return false;
	}
	return true;
};

$("#financeCode").keydown(function(e){
	if(e.keyCode==13){
		getFinance();
	}
	
});
$("#financeCode").change(function(){
	
	getFinance();
});
$("#salesCode").keydown(function(e){
	if(e.keyCode==13){
		getSales();
	}
	
});
$("#salesCode").change(function(){
	getSales();
});
$("#maintainCode").keydown(function(e){
	if(e.keyCode==13){
		getMaintain();
	}
	
});
$("#maintainCode").change(function(){
	getMaintain();
});
function siteIsExits(siteCode){
	var code;
	mmUtl.ajax.postJsonSyn(url.path+'/fcs-web-basicdata/basicdata/site/getSiteByCode.json', function(data){
		code= data.code
	},{
		"siteCode":siteCode
    });
	if(0 != code){
        return false;
	}
	return true;
};
function dataValid(){
	var dictCustStatus=$.trim($("#dictCustStatus option:selected").val());
	var agreeTime=$.trim($("#agreeTime").val());
	var custCode=$.trim($("#custCode").val());
	var siteCode=$.trim($("#siteCode").val());
	var custName=$.trim($("#custName").val());
	var dictClearingMethod=$.trim($("#dictClearingMethod option:selected").val());
	var financeCode=$.trim($("#financeCode").val());
	var salesCode=$.trim($("#salesCode").val());
	var contactsName0 = $.trim($("input[name='contactsName0']").val());
	var contactsDept0 = $.trim($("input[name='contactsDept0']").val());
	if(dictCustStatus==""||agreeTime==""||custCode==""||siteCode==""||custName==""
			||dictClearingMethod==""||financeCode==""||salesCode==""||contactsName0==""||contactsDept0==""){
		mmui.oper("红色字体标注的为必填项",1);
		return false;
	}
	if(!mmUtl.valid.isDateTime(agreeTime)){
		mmui.oper("日期格式不正确",1);
		return false;
	}
	if(!siteIsExits(siteCode)){
		mmui.oper("该站点不存在，请重新输入",1);
		return false;
	}
	if(!getFinance()){
		return false;
	}
	if(!getSales()){
		return false;
	}
	if(!getMaintain()){
		return false;
	}
	return true;
};
function getCode(){
	var custCode = $.trim($("#custCode").val());
	if(custCode == ""){
		setTimeout(function(){$("#custCode").focus();},1);
		mmui.oper("客户代码不能为空",1); 
		return;
	}
	mmUtl.ajax.postJsonSyn(url.path+'/fcs-web-basicdata/basicdata/customer/getCustomerByCode.json', function(data){
		if(data.code == 0){
			mmui.oper("客户代码重复",1); 
			setTimeout(function(){$("#custCode").focus();},1);
		}
	},{
		"custCode":custCode
    });
};
</script>
</body>
</html>