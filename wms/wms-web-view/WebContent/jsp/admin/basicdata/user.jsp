<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../meta.jsp"%>
<title>管理后台</title>
<style type="text/css">
.form-inline .form-group{
	margin-bottom:8px;
}
</style>
</head>
<body style="padding:12px 12px 0 12px;overflow-y:scroll;">

<form id="toolform" action="order" method="post" class="form-inline" >

<div class="form-group">
	<button type="button" id="export" class="btn btn-primary btn-sm" >批量查询</button>
</div>
<div class="form-group">
	<button type="button" id="export" class="btn btn-primary btn-sm" >添加</button>
</div>
<div class="form-group">
	<button type="button" id="export" class="btn btn-primary btn-sm" >导入</button>
</div>
<div class="form-group">
	<button type="button" id="export" class="btn btn-primary btn-sm" >导出</button>
</div>
<div class="form-group">
	<button type="button" id="export" class="btn btn-primary btn-sm" >审核</button>
</div>

<br/>

<div class="input-group input-group-sm">
  <span class="input-group-addon" id="sizing-addon3">客户账号</span>
  <input type="text" class="form-control" placeholder="Username" aria-describedby="sizing-addon3">
</div>

<div class="input-group input-group-sm">
  <span class="input-group-addon" id="sizing-addon3">证件号</span>
  <input type="text" class="form-control" placeholder="Username" aria-describedby="sizing-addon3">
</div>

<div class="input-group input-group-sm">
  <span class="input-group-addon" id="sizing-addon3">上传时间</span>
  <input type="text" class="form-control" placeholder="Username" aria-describedby="sizing-addon3">
</div>

<div class="input-group input-group-sm">
  <input type="text" class="form-control" placeholder="Username" aria-describedby="sizing-addon3">
</div>

<div class="input-group input-group-sm">
  <span class="input-group-addon" id="sizing-addon3">审核状态</span>
     <select style="width:100px;" class="form-control" id="shipway" name="shipway">
   	     <option value="">请选择</option>
   	     <option value="N">未审核</option>
   	     <option value="Y">审核失败</option>
   	     <option value="Y">审核通过</option>
   </select>
</div>

<div class="input-group input-group-sm">
  <span class="input-group-addon" id="sizing-addon3">证件图片</span>
     <select style="width:100px;" class="form-control" id="shipway" name="shipway">
   	     <option value="">请选择</option>
	   	 <option value="Y">有</option>
	   	 <option value="N">没有</option>
   </select>
</div>

<div class="input-group input-group-sm">
  <span class="input-group-addon" id="sizing-addon3">是否共用</span>
     <select style="width:100px;" class="form-control" id="shipway" name="shipway">
   	     <option value="">请选择</option>
	   	 <option value="2">是</option>
	   	 <option value="0">否</option>
   </select>
</div>


<div class="input-group input-group-sm">
  <a href="javascript:;" id="submit_btn" class="btn btn-primary btn-sm">查询</a>
</div>
<div class="input-group input-group-sm">
  <a href="javascript:;" id="submit_btn" class="btn btn-primary btn-sm">清空</a>
</div>

</form>

<table id="list_body" ></table>	
	
<script>
$(function () {
	mmList({
		url:'${ctx}/admin/cards/listJson',
		striped: true,
		onLoadSuccess:function(data){
			$("[itemOrderId]").click(function(){
				var orderId=$(this).attr("itemOrderId");
	        	parent.mmgrid("${ctx}/admin/cards/item?id="+orderId,"cardsitemview","证件明细",true);
			});
		},
		columns: [
			{checkbox: true},
			{field: '',title: '操作',formatter:function(value, row, index){
				return '<a href="javascript:;" itemOrderId="'+row.id+'">明细</a>';
			}}, 
			{field: 'cardNo',title: '证件号'},
			{field: 'cardName',title: '证件名'},
			{field: 'cardType',title: '证件类型',formatter:function(value, row, index){
				if(row.cardType=='A'){return "身份证";}else{return "护照";}
			}},
			{field: 'cardSex',title: '性别',formatter:function(value, row, index){
				if(row.cardSex=='1'){return "男";}else{return "女";}
			}},
			{field: 'hasValid',title: '有效状态',formatter:function(value, row, index){
				if(row.hasValid=='Y'){return "有效";}else{return "无效";}
			}},
			{field: 'hasItem',title: '公共图片',formatter:function(value, row, index){
					if(row.hasValid=='Y'){return "有";}else{return "无";}
			}},
			{field: 'cardCountry',title: '证件国家'},
			{field: 'cardProvince',title: '证件省份'},
			{field: 'cardCity',title: '证件市'}, 
			{field: 'cardAddress',title: '证件详细地址'},
			{field: 'cardExpire',title: '到期时间'},
			{field: 'cardSigning',title: '签发机关'},
			{field: 'bornDate',title: '生日'},
			{field: 'cardRace',title: '种族'},
			{field: 'zipCode',title: '邮编'}
		]
	}); 
});
</script>
		
</body>
</html>
