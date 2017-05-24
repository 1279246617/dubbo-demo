<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="../../meta.jsp"%>

<title>管理后台</title>
<style type="text/css">
.form-inline .form-group{
	margin-bottom:8px;
	}
}
.form-inline .input-group > .width100{width: 100px;}
.form-inline .input-group > .width150{width: 150px;}
.form-inline .input-group > .width200{width: 200px;}
.form-inline .input-group > .width400{width: 400px;}
.form-inline .input-group > .width800{width: 600px;}
.form-inline .input-group > .width800{width: 800px;}
</style>
</head>
<body style="padding:12px 12px 0 12px;overflow-y:scroll;">

	<form id="toolform" action="order" method="post" class="form-inline" >
		<div class="form-group">
			<button type="button" id="add" class="btn btn-primary" >新增</button>
		 	<button type="button" id="update" class="btn btn-primary">修改</button>
		    <button  type="button" id="delete" class="btn btn-primary">删除</button>
		    <button type="button" id="export" class="btn btn-primary">导出</button>
		    <button  type="button" id="get" class="btn btn-primary">查看</button>
		</div>
		<br/>
		<div class="input-group">
		    <span class="input-group-addon" id="sizing-addon3">供应商代码</span>
		    <input type="text" class="form-control" placeholder="供应商代码" aria-describedby="sizing-addon3" name="suppCode" id="suppCode">
		</div>
		<div class="input-group">
		    <span class="input-group-addon" id="sizing-addon3">供应商名称</span>
		    <input type="text" class="form-control" placeholder="供应商名称" aria-describedby="sizing-addon3" name="suppName" id="suppName">
		</div>
		<div class="input-group">
		    <span class="input-group-addon" id="sizing-addon3">所属站点</span>
		     <input type="text" class="form-control" placeholder="" aria-describedby="sizing-addon3" name="siteCode" id="siteCode" data-provide="typeahead"> 
		 
		</div>&emsp;
		<div class="input-group">
		    <span class="input-group-addon" id="sizing-addon3">对账员</span>
		    <input type="text" class="form-control" placeholder="" aria-describedby="sizing-addon3" name="financeCode" id="financeCode" data-provide="typeahead"> 
		</div>
		<div class="input-group">
		    <span class="input-group-addon" id="sizing-addon3">供应商状态</span>
		    <select style="" class="form-control width200" name="dictSuppStatus" id="dictSuppStatus"></select>
		</div>
		<div class="input-group">
		    <span class="input-group-addon" id="sizing-addon3">结算方式</span>
		    <select style="" class="form-control width200" name="dictClearingType" id="dictClearingType"></select>
		</div>
	    <div class="input-group input-group-sm">
		    <a href="javascript:;" id="submit_btn" class="btn btn-primary btn-sm">查询</a>
	    </div>
	    <div class="input-group input-group-sm">
		  <a href="javascript:;" id="clear" class="btn btn-primary btn-sm">清空</a>
		</div>
	</form>
<table id="list_body"></table>	
<script>
$(function () {
	mmList({
		url:url.path+'/wms-web-basicdata/basicdata/supplier/listSupplier.json',
	 	onLoadSuccess:function(data){}, 
		columns: [{checkbox: true}, 
			    {field: 'suppCode',title: '供应商代码'},
			    {field: 'suppName',title: '供应商名称'},
				{field: 'siteCode',title: '所属站点'},
				{field: 'dictClearingType',title: '结算方式'},
				{field: 'currency',title: '本位币'},
				{field: 'dictSuppStatus',title: '供应商状态'},
				{field: 'financeCode',title: '对账员代码'},
				{field: 'financeName',title: '对账员名称'},
				{field: 'remark',title: '建立人'},
				{field: 'createOperator',title: '建立人'},
				{field: 'createTime',title: '建立时间',formatter:function(value, row, index){
					return mmUtl.time.formatDate(row.createTime);
				}},
				{field: 'updateOperator',title: '最后修改人'},
				{field: 'updateTime',title:'最后修改时间',formatter:function(value, row, index){
					return mmUtl.time.formatDate(row.updateTime);
				}}
			]
	}); 
	//初始化下来列表
	initDict("dictClearingType","option","clearing_type");
	initDict("dictSuppStatus","option","supp_status");
	initFinanceCode();

	
	  mmUtl.ajax.postJson(url.path+"/wms-web-symgr/symgr/userMgr/getAllUserCode",function(data){
		    var resultVo=data.data;
		    $("#financeCode").append('<option value="">全部</option>');
	    	 $.each(resultVo,function(i,item){
	    		 $("#financeCode").append('<option value="'+item+'">'+item+'</option>');
	         });
		    
		 },{
	    });
	
	$("#delete").click(function(){
		var data=$("#list_body").mmuiTable("getSelections");
		if(data.length!=1){
			parent.mmui.alert("请选择一条要删除的数据",3);
			return false;
		}
		var id=data[0].id;
		parent.mmui.confirm("确认删除供应商资料"+data[0].suppName,function(delSite){
			parent.mmui.close(delSite);
			mmUtl.ajax.postJson(url.path+"/wms-web-basicdata/basicdata/supplier/deleteSupplier",function(data){
				   mmui.oper(data.msg,1); 
				   document.location.reload();
			    },{
				   id:id
			    });
			 },function(){
			 
		});
	});

	$("#add").click(function(){
		parent.mmgrid("${ctx}/jsp/admin/basicdata/supplier/add.jsp?method=add","addSupplier","新增供应商",true);
		
	});
	$("#update").click(function(){
		var data=$("#list_body").mmuiTable("getSelections");
		if(data.length!=1){
			parent.mmui.alert("请选择一条要修改的数据",3);
			return false;
		}
		var id=data[0].id;
		parent.mmgrid("${ctx}/jsp/admin/basicdata/supplier/add.jsp?method=update&id="+id,"updateSupplier","修改供应商",true);
	});
	$("#get").click(function(){
		var data=$("#list_body").mmuiTable("getSelections");
		if(data.length!=1){
			parent.mmui.alert("请选择一条要查看的数据",3);
			return false;
		}
		var id=data[0].id;
		parent.mmgrid("${ctx}/jsp/admin/basicdata/supplier/add.jsp?method=get&id="+id,"getSupplier","查看供应商",true);
		
	});

});
//初始化站点
function initSite(){
	mmUtl.ajax.getJson(url.path+"/wms-web-basicdata/basicdata/site/listALLSiteCode",function(data){
	    var resultVo=data.data;
	    $("#siteCode").typeahead({source : resultVo});
	  },{
		
    });
}

//初始化对账员
function initFinanceCode(){
	   mmUtl.ajax.postJsonSyn(url.path+"/wms-web-symgr/symgr/userMgr/getAllUserCode",function(data){
		    var resultVo=data.data;
	    	$("#financeCode").typeahead({source : resultVo}); 
		 },{
	    });
}
$("#clear").click(function(){
	clearForm("toolform");
});

</script>
		
</body>
</html>