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

<div class="container" style="margin-left:0;">
	<div style="font-size:25px;line-height:45px;">新增条目</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">
		  <h3 class="panel-title">字典条目</h3>
		</div>
		<div class="panel-body">
			<form class="form-inline" id="addDictItemForm">
				<div class="row" style="margin-left:0;margin-right:0;">
					 <div class="input-group ">
						 <span class="input-group-addon">条目编码</span>
						 <input type="text" class="form-control width200" 
					  		name="itemCode"  placeholder="条目编码" id="itemCode" >
					</div>
					<div class="input-group ">
					  <span class="input-group-addon" id="sizing-addon3">条目值</span>
					  <input type="text" class="form-control width200" 
					  		placeholder="条目值" name="itemValue" id="itemValue" >
					</div>
					<div class="input-group ">
					  <span class="input-group-addon" id="sizing-addon3">字典类型</span>
					    <input type="text" class="form-control width200" 
					  		placeholder="字典类型" name="typeCode" id="typeCode" >
					</div>
					<div class="input-group ">
					  <span class="input-group-addon" id="sizing-addon3">排序</span>
					    <input type="text" class="form-control width200" 
					  		placeholder="排序" name="itemSort" id="itemSort" >
					</div>
				</div>
				<div class="row">
					<div class="col-md-2">
						<div class="form-group">
							<button type="button" id="submit" class="btn btn-primary btn-sm" >保存</button>
						</div>
					</div>
				 </div>  
			</form>
		</div>
	</div>
</div>
<script>

$("#submit").click(function(){
	var data = $("#addDictItemForm").serialize();
	mmUtl.ajax.postJsonAnt(url.path+'/fcs-web-symgr/symgr/dictItem/addDictItem', function(data){
		mmui.oper(data.msg,1);
		clearForm("addDictItemForm");
	}, mmUtl.ajax.getArgs($("#addDictItemForm")))

})

</script>

</body>
</html>