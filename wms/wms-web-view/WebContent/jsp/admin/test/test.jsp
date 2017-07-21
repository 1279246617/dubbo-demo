<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../meta.jsp"%>
<title>管理后台</title>
</head>
<body style="padding: 12px 12px 0 12px; overflow-y: scroll;">

	<form id="toolform" action="order" method="post" class="form-inline">


		<div class="form-group" id="buttonAll">
		</div>

		<br />
		<div class="input-group input-group-sm">
			<span class="input-group-addon">用户名字</span><input type="text"
				style="width: 100px;" class="form-control" id="user_name"
				name="userName" placeholder="员工帐号">
		</div>
		<div class="input-group input-group-sm">
			<span class="input-group-addon">用户登录名字</span> <input type="text"
				style="width: 100px;" class="form-control" id="login_name"
				name="loginName" placeholder="用户登录名字">
		</div>

		<div class="input-group input-group-sm">
			<a href="javascript:;" id="submit_btn" class="btn btn-primary btn-sm">查询</a>
		</div>



		<div class="input-group input-group-sm">
			<a href="javascript:;" id="clean_btn" class="btn btn-primary btn-sm">清空</a>
		</div>

		<input type="submit" style="display: none;" />

	</form>

	<div>
		<table id="list_body"</table>
	</div>

	<script>
	
	    
	
		$("#clean_btn").click(function() {
			clearForm("toolform")
		})

		$(function() {
			initTable();
		});

		function initTable() {
			$("#list_body").bootstrapTable({

				url : globalUrl.symgmtPath + "/symgmt/admin/getFromFront",
				striped : true, //表格显示条纹
				pagination : true, //启动分页
				pageSize : 15, //每页显示的记录数
				pageNumber : 1, //当前第几页
				pageList : [ 10, 15, 20, 25 ], //记录数可选列表
				search : false, //是否启用查询
				// 		        showColumns: true,  //显示下拉框勾选要显示的列
				// showRefresh: true,  //显示刷新按钮
				sidePagination : "server", //表示服务端请求
				clickToSelect : true,
				queryParamsType : "undefined",
				queryParams : function queryParams(params) { //设置查询参数
					var param = {
						//这里是在ajax发送请求的时候设置一些参数 params有什么东西，自己看看源码就知道了
						page : params.pageNumber,
						limit : params.pageSize
					};
					var userName = $("#user_name").val();
					var loginName = $("#login_name").val();
					param.userName = userName;
					param.loginName = loginName;
					return param;
				},
				onLoadError : function() { //加载失败时执行

				},
				// showToggle: true,                    //是否显示详细视图和列表视图的切换按钮  纵向视图跟横向
				cardView : false,
				columns : [ {
					field : 'state',
					checkbox : true,
					align : 'center',
					valign : 'middle'
				}, {
					title : 'ID',
					field : 'id',
					align : 'center',
					valign : 'middle'
				}, {
					field : 'userName',
					title : '用户名字',
					align : 'center'
				}, {
					field : 'loginName',
					title : '用户登录名称',
					align : 'center'
				} ]
			});
		}

		$("#submit_btn").click(function() {
			$("#table").bootstrapTable('refresh')
		});

		

		function form2Json(id) {
			var arr = $(id).serializeArray()
			var jsonStr = "";
			jsonStr += '{';
			for (var i = 0; i < arr.length; i++) {
				if (arr[i].value.trim() != '') {
					jsonStr += '"' + arr[i].name + '":"' + arr[i].value + '",'
				}
			}
			if (jsonStr.length > 3)
				jsonStr = jsonStr.substring(0, (jsonStr.length - 1));
			jsonStr += '}'
			var one = 0;
			var two = 0;
			var three = 0;
			while (true) {
				if ((one = jsonStr.indexOf("\n", one)) == -1
						&& (two = jsonStr.indexOf("\r", two)) == -1
						&& (three = jsonStr.indexOf("\\", three)) == -1) {
					break;
				}
				one += "\n".length
				two += "\r".length
				three += "\\".length + 2
				jsonStr = jsonStr.replace("\\", "\\\\");//对斜线的转义  
				jsonStr = jsonStr.replace("\n", "\\n");
				jsonStr = jsonStr.replace("\r", "\\r");
			}
			var json = (new Function("return " + jsonStr))();
			return json;
		}
	</script>

</body>
</html>
