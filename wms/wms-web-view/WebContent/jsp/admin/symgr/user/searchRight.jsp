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


	
	<div class="tree">
		<fieldset>
			<legend>菜单级权限</legend>
			<div class="leftContent">
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
 
  
</body>

<script type = "text/javascript" >
		$(function(){
			var userCode = getParam("userCode");
			//var userCode = window.location.search.substring(1).replace('userCode=','');
			mmUtl.ajax.postJson(url.path+"/wms-web-symgr/symgr/menu/listMenuRightTree.json",function(data){
				if(data.code == 0){
					console.info(data);
					$.fn.zTree.init($("#tree"), setting, data.data);
				}else{
					mmui.oper(data.msg,1); 
				}
    		},{
    			"userCode":userCode
    		});
		}); 
		  
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
		
	
 </script> 
</html>