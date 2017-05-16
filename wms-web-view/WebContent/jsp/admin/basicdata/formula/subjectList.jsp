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
		<div class="input-group">
		    <span class="input-group-addon" id="sizing-addon3">科目代码</span>
		    <input type="text" class="form-control" placeholder="科目代码" aria-describedby="sizing-addon3" name="subjectCode" id="subjectCode">
		</div>
		<input type="hidden" name="dictSubjectType" id="dictSubjectType">
        <div class="input-group input-group-sm">
		    <a href="javascript:;" id="submit_btn" class="btn btn-primary btn-sm">查询</a>
	    </div>
	</form>
<table id="list_body"></table>	
<script>

$(function () {
	
	 var subjectType=getParam("subjectType");
	
	$("#dictSubjectType").attr("value",subjectType);
	mmList({
		url:url.path+'/fcs-web-basicdata/basicdata/subject/listChildSubject.json?subjectType='+subjectType,
	 	onLoadSuccess:function(data){}, 
		columns: [{checkbox: true},
		    {field: 'subjectCode',title: '科目代码'},
		    {field: 'subjectName',title: '科目名称'},
		]
	}); 
	 
});

var table=$("#list_body"); 


</script>
</body>
</html>