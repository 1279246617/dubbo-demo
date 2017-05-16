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
<body style="padding:12px 12px 0 12px;overflow-y:scroll;">
	<form id="toolform" action="order" method="post" class="form-inline" >
	    <input type="hidden" name="idGroup" id="idGroup">
		<div class="container" style="margin-left:0;">
			<div style="font-size:25px;line-height:45px;" id="titelDetial">批量修改</div>
			<div class="input-group input-group-sm">
			  <input type="radio" class="form-control width100" name="idGroup" id="nowPage"><font style="font-size:25px">当前页所选数据</font>
			</div>
			<br>
			<div class="input-group">
			  <input type="radio" class="form-control width100" name="idGroup" checked="checked" id="allPage"><font style="font-size:25px">所有页数据</font>
			</div>
			<br><br>
			<div class="input-group">
			  <input type="checkbox" class="form-control width100" id="isDictPartnerType">
			  <span class="input-group-addon" id="sizing-addon3">客户类型</span>  
			  <select class="form-control" name="dictPartnerType" id="dictPartnerType"></select>
			</div>
			<br><br>
			<div class="input-group">
			   <input type="checkbox" class="form-control width100" id="isSalesCode">
			   <span class="input-group-addon" id="sizing-addon3">销售员代码</span>
			   <input type="text" class="form-control" placeholder="" aria-describedby="sizing-addon3" name="salesCode" id="salesCode" data-provide="typeahead">
			</div>
			<br><br>
		    <div class="input-group">
			   <input type="checkbox" class="form-control width100" id="isMaintainCode">
			   <span class="input-group-addon" id="sizing-addon3">维护员代码</span>
			   <input type="text" class="form-control" placeholder="" aria-describedby="sizing-addon3" name="maintainCode" id="maintainCode" data-provide="typeahead">
			</div>
			<br><br>
			<div class="input-group">
			   <input type="checkbox" class="form-control width100" id="isFinanceCode">
			   <span class="input-group-addon" id="sizing-addon3">对账员代码</span>
			   <input type="text" class="form-control" placeholder="" aria-describedby="sizing-addon3" name="financeCode" id="financeCode" data-provide="typeahead">
			</div>
			<br><br>
			&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
			<div class="input-group input-group-sm">
			  <a href="javascript:;" id="submit_btn" class="btn btn-primary btn-sm">保存</a>
			</div>
		</div>
		<input type="hidden"  id="salesName" name="salesName">
		<input type="hidden" id="maintainName" name="maintainName">
	</form>
<script>
$(function(){

   mmUtl.ajax.postJson(url.path+"/fcs-web-symgr/symgr/dictItem/getAllItemByCode",function(data){
	    var resultVo=data.data;
    	 $.each(resultVo,function(i,item){
    		 $("#dictPartnerType").append('<option value="'+item.itemCode+'">'+item.itemValue+'</option>');
         });
    	
	 },{
		code:'partner_type',
		codeType:'aaa'
    });
    mmUtl.ajax.postJson(url.path+"/fcs-web-symgr/symgr/userMgr/getAllUserCode",function(data){
	    var resultVo=data.data;
        $('#salesCode').typeahead({source: resultVo});
        $('#maintainCode').typeahead({source: resultVo});
        $('#financeCode').typeahead({source: resultVo});

	 },{
    });
});
var idGroup=getParam("idGrop");
var idList=getParam("idList");
$("#nowPage").attr("value",idGroup);
$("#allPage").attr("value",idList);
$("#submit_btn").click(function(){
	cancelUnchecked();
	var data = $("#toolform").serialize();
	mmUtl.ajax.postJson(url.path+'/fcs-web-basicdata/basicdata/customer/batchmodify', function(data){
		mmui.oper(data.msg,1); 
		location.reload();
	}, mmUtl.ajax.getArgs($("#toolform")));

});


//未选中的复选框禁止提交
function cancelUnchecked(){
	if(!$("#isDictPartnerType").prop("checked")){
		$("#dictPartnerType").attr("disabled","disabled");
	}
	if(!$("#isSalesCode").prop("checked")){
		
		$("#salesCode").attr("disabled","disabled");
		$("#salesName").attr("disabled","disabled");
	}else{
		var salesCode=$.trim($("#salesCode").val());
		if(""!=salesCode && null!=salesCode){
			initSalesName(salesCode);
		}
	}
	if(!$("#isMaintainCode").prop("checked")){
		$("#maintainCode").attr("disabled","disabled");
		$("#maintainName").attr("disabled","disabled");
	}else{
		var maintainCode=$.trim($("#maintainCode").val());
		if(""!=maintainCode && null != maintainCode){
			initMaintainName(maintainCode);
		}
	}
	
	if(!$("#isFinanceCode").prop("checked")){
		$("#financeCode").attr("disabled","disabled");
	}
	
};
function initSalesName(salesCode){
	mmUtl.ajax.postJsonSyn(url.path+'/fcs-web-symgr/symgr/userMgr/getUserByUserCode', function(data){
		var user=data.data;
		
		if(0!=data.code){
		parent.mmui.alert("该销售员不存在",3);
		return false;
		}
		$("#salesName").attr("value",user.userName);
	
	},{
		userCode:salesCode
    });
	
};
function initMaintainName(maintainCode){
	mmUtl.ajax.postJsonSyn(url.path+'/fcs-web-symgr/symgr/userMgr/getUserByUserCode', function(data){
		var user=data.data;
		
		if(0!=data.code){
		parent.mmui.alert("该维护员不存在",3);
		return false;
		}
		$("#maintainName").attr("value",user.userName);
	
	},{
		userCode:maintainCode
    });
	
};
</script>
</body>