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
.form-inline .input-group > .width150{width: 150px;}
.form-inline .input-group > .width200{width: 200px;}
.form-inline .input-group > .width400{width: 400px;}
.form-inline .input-group > .width800{width: 600px;}
.form-inline .input-group > .width800{width: 800px;}
</style>
</head>
<body style="padding-top:12px;overflow-y:scroll;">

<div class="container" style="margin-left:0;">
	<div style="font-size:25px;line-height:45px;">新增菜单权限</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">
		  <h3 class="panel-title">菜单权限信息</h3>
		</div>
		<div class="panel-body">
			<form class="form-inline" id="addMenuRightForm">
				<div class="row" style="margin-left:0;margin-right:0;">
					 <div class="input-group ">
						 <span class="input-group-addon width150">菜单权限编码</span>
						 <input type="text" class="form-control width200" 
					  		name="menuCode"  placeholder="菜单权限编码" id="menuCode" >
					</div>
					<div class="input-group ">
					  <span class="input-group-addon width150" id="sizing-addon3">菜单权限名称</span>
					  <input type="text" class="form-control width200" 
					  		placeholder="菜单权限名称" name="menuName" id="menuName" >
					</div>
					<div class="input-group ">
					  <span class="input-group-addon width150" id="sizing-addon3">菜单URL</span>
					    <input type="text" class="form-control width200" 
					  		placeholder="菜单URL" name="menuEvent" id="menuEvent" >
					</div>
					<div class="input-group ">
					  <span class="input-group-addon width150" id="sizing-addon3">父菜单</span>
					    <input type="text" class="form-control width200" 
					  		placeholder="父菜单" name="parentId" id="parentId" >
					</div>
					<div class="input-group ">
					  <span class="input-group-addon width150" id="sizing-addon3">排序号</span>
					    <input type="text" class="form-control width200" 
					  		placeholder="排序号" name="menuSortNo" id="menuSortNo" >
					</div>
					<div class="input-group ">
					  <span class="input-group-addon width150" id="sizing-addon3">是否生效</span>
					    <input type="text" class="form-control width200" 
					  		placeholder="是否生效" name="dictMenuStatus" id="dictMenuStatus" >
					</div>
					<div class="input-group ">
					  <span class="input-group-addon width150" id="sizing-addon3">公共资源</span>
					    <input type="text" class="form-control width200" 
					  		placeholder="公共资源" name="dictIsCommon" id="dictIsCommon" >
					</div>
					<div class="input-group ">
					  <span class="input-group-addon width150" id="sizing-addon3">菜单类型</span>
					    <input type="text" class="form-control width200" value="menu_type_1"
					  		placeholder="菜单类型" name="dictMenuType" id="dictMenuType" >
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
	var data = $("#addMenuRightForm").serialize();
	mmUtl.ajax.postJsonAnt(url.path+'/wms-web-symgr/symgr/menu/addMenuRight', function(data){
		mmui.oper(data.msg,1);
		/* clearForm("addMenuRightForm"); */
	}, mmUtl.ajax.getArgs($("#addMenuRightForm")))

})

</script>

</body>
</html>