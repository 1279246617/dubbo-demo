<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <title>用户组列表</title>
 	<!-- 用户组页面 -->
 	<script src="${ctx}/3rd/group/js/jquery-1.7.2.min.js"></script>
	<script src="${ctx}/3rd/zTree_v3-master/js/jquery.ztree.core.js"></script>
 	<script src="${ctx}/3rd/zTree_v3-master/js/jquery.ztree.excheck.js"></script>
 	<script src="${ctx}/3rd/group/js/handlebars-v4.0.5.js"></script>
 	<script type="text/javascript" src="${ctx}/3rd/mm-3.2/mmui.js"  ></script>
 	<script src="${ctx}/js/config.js"></script>
 	<script src="${ctx}/js/common.js"></script>
 	<link rel="stylesheet" href="${ctx}/3rd/zTree_v3-master/css/zTreeStyle/zTreeStyle.css" type="text/css">
 	<link rel="stylesheet" href="${ctx}/3rd/group/css/style.css" type="text/css">
</head>

<body>


<div class="wrap">
	<div class="operation" >
		<button type="button" id="save" class="btn btn-primary btn-sm">保存</button>
	</div>


	<div class="group1" style="margin:0px; height:90%; float:left; width:50%">
		<fieldset>
			<legend>用户组</legend>
			<div class="userGroupContent" style="width:100%;height:100%;position:relative">
				<div class="contentTitle">
					用户组: <span class="menuName" id="userGroup"></span>
				</div>
				<div class="contentMain" >
					<div class="tableTree">
						<table class="ztitle" id="list_body">  
						<thead>
						 <tr>  
						 	<th class="td">选择</th>
							<th class="td">启用</th>  
							<th class="td">用户组</th>  
							<th class="td">所属部门</th>  
							<th class="td">用户数</th> 
							<th class="td">修改人</th>  
							<th class="td">修改时间</th>							
						 </tr> 
						</thead>
						<tbody id="group" class= "subTable">
							
						</tbody>
						</table>  
					</div>
				</div>
				
			</div>
		</fieldset>
	</div>
	
	<div class="tree1" style="margin:0px; height:90%; float:right; width:50%">
		<fieldset>
			<legend>菜单级权限</legend>
			<div class="leftContent" style="width:100%;height:100%;position:relative">
				<div class="contentTitle">
					用户组: <span class="menuName" id="groupRight"></span>
				</div>
				<div class="contentMain">
					<div class="tableTree">
					<table class="ztitle">  
					<thead>
					 <tr>  
						<th class="td1">菜单名称</th>  
						<th class="td2">启用</th>  
						<th class="td3">下级权限数</th>  
						<th class="td4">拥有数</th>  
					 </tr> 
					</thead>
					</table>  
					<ul id="tree" class="ztree"></ul>
				</div>
				</div>
				
			</div>
		</fieldset>
	</div>

</div>

<!-- 模板1-->
<script type="text/x-handlebars-template" id="ztreeTPL">  
<table class="ztitle">  

 <tr>  
    <td class="td1"></td>  
    <td class="td2">
	{{#isNull this.checked}}
		<input type="checkbox" checked disabled />
	{{else}}
		<input type="checkbox" disabled />
	{{/isNull}}
		 
 	</td>  
    <td class="td3">{{this.sum}}</td>  
    <td class="td4" id="sumRight_{{this.id}}">{{this.hasRight}}</td>  
  
</table>	
</table>	
 </script> 
 
  <!-- 用户组模板-->
  <script type="text/x-handlebars-template" id="groupTPL"> 
	{{#each this}}
	
		<tr  onclick="getDetail('{{groupCode}}','{{groupName}}')">
			<td>
				{{#isNull this.checked}}
				<input type="checkbox" name="ck" value="{{groupCode}}" checked/>
				{{else}}
				<input type="checkbox" name="ck" value="{{groupCode}}"/>
				{{/isNull}}
				
			</td>
			<td >
				{{#isNull this.isUse}}
				<input type="checkbox" name="isUse"  checked disabled/>
				{{else}}
				<input type="checkbox" name="isUse" disabled/>
				{{/isNull}}
			</td>		
			<td >{{groupName}}</td>
			<td >{{dept}}</td>
			<td >{{userNum}}</td>
			<td >{{updateOperator}}</td>
			<td >{{updateTime}}</td>
		</tr>
 	{{/each}}	
 </script> 
  
</body>

<script type = "text/javascript" >
	var userCode=getParam("userCode");
	$(function(){
		/* userCode = window.location.search.substring(1).replace('userCode=',''); */
		mmUtl.ajax.postJson(url.path+"/fcs-web-symgr/symgr/group/listUserGroup.json",function(data){
			if(data.code == 0){
				console.info(data);
				/* adapterList(data.data,groupTPL,group); */
				adapterGroup(data.data); 
			}else{
				mmui.oper(data.msg,1); 
			}
		},{
			"userCode":userCode
		});
	});
	
	function adapterGroup(groupList){
		  var html = $("#groupTPL").html();
		  Handlebars.registerHelper("isNull", isNull);
		  html = Handlebars.compile(html)(groupList); 
		  $("#group").html(html);
	    }
	
	function getDetail(groupCode,groupName){
		//点击事件改变导航栏的头部
		$("#userGroup").html(groupName);
		$("#groupRight").html(groupName);
		
		//异步请求该用户组下的权限
		mmUtl.ajax.postJson(url.path+"/fcs-web-symgr/symgr/menu/listMenuRightTree.json",function(data){
			if(data.code == 0){
				console.info(data);
				var nodes = data.data;
				$.fn.zTree.init($("#tree"), setting, nodes);
			}else{
				mmui.oper(data.msg,1); 
			}
		},{
			"groupCode":groupCode
		});
		
	}
		  
          var setting = {
			check: {
				enable: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			
			view: {
				addDiyDom:addDiyDom
			},
			
			callback: {
			},
			 showIcon:0,  
			 showTitle:0,  
             showLine:0,  
             selectedMulti:0,  
             txtSelectedEnable:0  
		};
         
		
        function addDiyDom(rootId,nodeObj) {  
			var padding = 18;
 			var html = $('#ztreeTPL').html();  
			var originalHtml = $("#" + nodeObj.tId).html();
 			Handlebars.registerHelper("isNull", isNull);
			html = Handlebars.compile(html)(nodeObj);  
			
 			$("#" + nodeObj.tId).html(html);  
			$("#" + nodeObj.tId).find('.td1').html(originalHtml);
 			$("#" + nodeObj.tId).find('.td1').find("span").eq(0).css("margin-left", padding*nodeObj.level+"px")
        }
		
    	
		
		function isNull(v1, options){
			if(v1 == true || v1 == "true" || v1 == "是" || v1 == 1){
				//满足添加继续执行
				return options.fn(this);
			}else{
				//不满足条件执行{{else}}部分
				return options.inverse(this);
			}
		}
		
		$("#save").click(function(){
			var dataJson=[];
			if($('input[name="ck"]:checked').length == 0){
				//执行删除user下的userGroup
				mmUtl.ajax.postJsonAnt(url.path+'/fcs-web-symgr/symgr/userGoup/delUserGroup', function(data){
					mmui.oper(data.msg,1); 
				},{
					"userCode":userCode
				})
				
			}else{
				//执行给user分配userGroup
				$('input[name="ck"]:checked').each(function(){
					dataJson.push({"userCode":userCode,"groupCode":$(this).val()});
				
				});
				mmUtl.ajax.postJsonAnt(url.path+'/fcs-web-symgr/symgr/userGoup/addUserGroupBatch', function(data){
					alert(data.msg);
					mmui.oper(data.msg,1); 
				},
					JSON.stringify(dataJson)
				)
			}
		})
		
	
 </script> 
</html>