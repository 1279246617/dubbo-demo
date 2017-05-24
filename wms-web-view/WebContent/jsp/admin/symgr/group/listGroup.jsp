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
 	<script src="${ctx}/3rd/zTree_v3-master/js/jquery.ztree.exedit.js"></script>
 	<script src="${ctx}/3rd/group/js/handlebars-v4.0.5.js"></script>
 	<script type="text/javascript" src="${ctx}/3rd/mm-3.2/mmui.js"  ></script>
 	<script src="${ctx}/js/config.js"></script>
 	<script src="${ctx}/js/common.js"></script>
 	<link rel="stylesheet" href="${ctx}/3rd/zTree_v3-master/css/zTreeStyle/zTreeStyle.css" type="text/css">
 	<link rel="stylesheet" href="${ctx}/3rd/group/css/style.css" type="text/css">
 	<link rel="stylesheet" href="${ctx}/3rd/group/css/common.css" type="text/css">
</head>

<body>


<div class="wrap">

	<div class="operation" >
		<button type="button" id="add" class="btn btn-primary btn-sm">新增</button>
		<button type="button" id="upd" class="btn btn-primary btn-sm">修改</button>
		<button type="button" id="del" class="btn btn-primary btn-sm">删除</button>
		<button type="button" id="copy" class="btn btn-primary btn-sm">复制</button>
		<button type="button" id="save" class="btn btn-primary btn-sm">保存</button>
	</div>

	<div class="userGroup">
		<fieldset>
			<legend>用户组</legend>
			<div class="userGroupContent" >
				<div class="contentTitle">
					用户组: <span class="menuName" id="userGroup"></span>
				</div>
				<div class="contentMain">
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
	

	<div class="groupMember">
		<fieldset>
			<legend>组成员</legend>
			<div class="groupMemberContent">
				<div class="contentTitle">
					用户组: <span class="menuName" id="groupMember"></span>
				</div>
				<div class="contentMain">
					<div class="tableTree">
					<table class="ztitle">  
					<thead>
					 <tr>  
						<th class="td">用户ID</th>  
						<th class="td">用户姓名</th>  
						<th class="td">所属站点</th>  
						<th class="td">职位状态</th>  
					 </tr> 
					</thead>
					<tbody id="user" class= "subTable">
								
					</tbody>
					</table>  
				</div>
				</div>
				
			</div>
		</fieldset>
	</div>

	
	<div class="left">
		<fieldset>
			<legend>菜单级权限</legend>
			<div class="leftContent">
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

	<!-- <div class="right">
		<fieldset>
			<legend>二级权限</legend>
			<div class="rightContent">
				<div class="contentTitle">
					权限名: <span class="menuName" id="menuName"></span>
				</div>
				<div class="contentMain">
					<div class="tableTree">
						<table class="ztitle">
							<thead>
								<tr>
									<th >二级设置</th>
									<th class="td5">启用</th>
								</tr>
							</thead>
							<tbody id="subMenu" class= "subTable">
								
							</tbody>
						</table>
					</div>
				</div>
				
			</div>
		</fieldset>
	</div> -->
</div>
<div class="box" style="background-color: #ffffff;">
<div id="dialogBg"></div>
<div id="dialog" class="animated">
	<img class="dialogIco" width="50" height="50" src="${ctx}/3rd/group/img/ico.png" alt="" />
	<div class="dialogTop">
		<a href="javascript:;" class="claseDialogBtn">关闭</a>
	</div>
	<form action="" method="post" id="editForm">
		<ul class="editInfos">
			<input type="hidden" name="gCode" id="gCode"/>
			<input type="hidden" name="groupCode" id="groupCode"/>
			<li><label><font color="#ff0000">* </font>新用户组名：<input type="text" name="groupName" required value="" class="ipt" id="groupName"/></label></li>
			<li><label><font color="#ff0000">* </font>描 述 信 息  ：<input type="text" name="discript" required value="" class="ipt" id="discript"/></label></li>
			<hr style="filter:alpha(opacity=100,finishopacity=0,style=3) ;width:"80%"; color:#987cb9">
			<li><label><font color="#ff0000">* </font>复制用户组：<input type="text" name="" disabled="disabled" value="" class="ipt" id="last" /></label></li>
			<li><input type="button" value="确认提交" class="submitBtn" id="submitBtn" /></li>
		</ul>
	</form>
</div>
</div> 

<!-- 模板1-->
<script type="text/x-handlebars-template" id="ztreeTPL">  
<table class="ztitle">  

 <tr>  
    <td class="td1"></td>  
    <td class="td2">
	{{#isNull this.checked}}
		<input type="checkbox" checked id="check_{{this.id}}" idNum="{{this.id}}" pId="{{this.pId}}" leafSum="{{this.sum}}" name="enableMenu" value="{{this.menuCode}}" />
	{{else}}
		<input type="checkbox" id="check_{{this.id}}" idNum="{{this.id}}" pId="{{this.pId}}" leafSum="{{this.sum}}" name="enableMenu" value="{{this.menuCode}}" />
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
	
		<tr  onclick="getDetail('{{id}}','{{groupCode}}','{{groupName}}')">
			<td>
				<input type="checkbox" name="btSelectItem" value="{{groupCode}}" />
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
 
  <!-- 组成员模板-->
  <script type="text/x-handlebars-template" id="userTPL"> 
	{{#each this}}
		<tr>
			<td >{{userCode}}</td>
			<td >{{userName}}</td>
			<td >{{siteCode}}</td>
			<td >{{posStatus}}</td>
		</tr>
		
 	{{/each}}	
 </script> 
 
   <!-- 子权限模板-->
  <script type="text/x-handlebars-template" id="subMenuTPL"> 
	{{#each this}}
		<tr>
			<td >{{name}}</td>
			<td class="td5">
  				{{#isNull this.checked}}
				<input type="checkbox" name="subCheckbox"  checked id="{{id}}" pId = "{{pId}}" />
				{{else}}
				<input type="checkbox" name="subCheckbox" id="{{id}}" pId = "{{pId}}" />
				{{/isNull}}
			</td>
		</tr>
 	{{/each}}	
 </script> 
 
</body>

<script type = "text/javascript" >
		$(function(){
			mmUtl.ajax.postJson(url.path+"/wms-web-symgr/symgr/group/listGroup.json",function(data){
				if(data.code == 0){
					console.info(data);
					/* adapterList(data.data,groupTPL,group); */
					adapterGroup(data.data); 
				}else{
					mmui.oper(data.msg,1); 
				}
    		},{
    			//测试用, 从缓存中取
    			"userCode":"admin"
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
				beforeExpand: beforeExpand,
				onExpand: onExpand,
				onClick: zTreeOnClick
			},
			 showIcon:0,  
			 showTitle:0,  
             showLine:0,  
             selectedMulti:0,  
             txtSelectedEnable:0  
		};
          
	    function adapterGroup(groupList){
		  var html = $("#groupTPL").html();
		  Handlebars.registerHelper("isNull", isNull);
		  html = Handlebars.compile(html)(groupList); 
		  $("#group").html(html);
	    }
	    
	    function adapterUser(userList){
		  var html = $("#userTPL").html();
		  Handlebars.registerHelper("isNull", isNull);
		  html = Handlebars.compile(html)(userList);  
		  $("#user").html(html);
	    }
	    
	    function adapterButton(buttonList){
	    	var html = $("#subMenuTPL").html();
			Handlebars.registerHelper("isNull", isNull);
			html = Handlebars.compile(html)(buttonList);  
			$("#subMenu").html(html);
	    }
	    var gCode = "";
	    var gid = "";
	    var gName="";
		/*
			获取用户组下的用户以及权限
		*/
		function getDetail(id,groupCode,groupName){
			gCode = groupCode;
			gid=id;
			gName = groupName;
			//点击事件改变导航栏的头部
			$("#userGroup").html(groupName);
			$("#groupMember").html(groupName);
			$("#groupRight").html(groupName);
			//异步请求该用户组下的用户
			mmUtl.ajax.postJson(url.path+"/wms-web-symgr/symgr/userMgr/listUserNotPage.json",function(data){
				if(data.code == 0){
					console.info(data);
					adapterUser(data.data);
				}else{
					mmui.oper(data.msg,1); 
				}
    		},{
				"groupCode":groupCode
    		});
			//异步请求该用户组下的权限
			mmUtl.ajax.postJson(url.path+"/wms-web-symgr/symgr/menu/listMenuRightTree.json",function(data){
				if(data.code == 0){
					console.info(data);
					var nodes = data.data;
					var tree = $.fn.zTree.init($("#tree"), setting, nodes);
					tree.expandAll();
				}else{
					mmui.oper(data.msg,1); 
				}
    		},{
    			"groupCode":groupCode,
    			//测试使用，用户code到时候从缓存中取
    			"userCode":"admin",
    			//menu_type_1是菜单类型，menu_type_2是按钮类型
    			"menuType":"menu_type_1"
    		});
			
			
		}
        
        function addDiyDom(rootId,nodeObj) {  
 			var padding = 18;
 			var html = $('#ztreeTPL').html();  
			var dom = $("#" + nodeObj.tId);
			
			var ul = dom.find("ul");
			var ulHtml = "";
			
			Handlebars.registerHelper("isNull", isNull);
			html = Handlebars.compile(html)(nodeObj);  
			
			
			
			var originalHtml = dom.html();
			 
			if(ul.size() > 0 ) {
 			    ulHtml = ul.prop('outerHTML');
			    ul.remove();
				originalHtml = dom.html();
			}  
			$("#" + nodeObj.tId).html(html); 
			$("#" + nodeObj.tId).find('.td1').html(originalHtml);
			$("#" + nodeObj.tId).find('.td1').find("span").eq(0).css("margin-left", padding*nodeObj.level+"px")
			$("#" + nodeObj.tId).append(ulHtml)
        }
		
        var curExpandNode = null;
        function beforeExpand(treeId, treeNode) {
			var pNode = curExpandNode ? curExpandNode.getParentNode():null;
			var treeNodeP = treeNode.parentTId ? treeNode.getParentNode():null;
			var zTree = $.fn.zTree.getZTreeObj("tree");
			for(var i=0, l=!treeNodeP ? 0:treeNodeP.children.length; i<l; i++ ) {
				if (treeNode !== treeNodeP.children[i]) {
					zTree.expandNode(treeNodeP.children[i], false);
				}
			}
			while (pNode) {
				if (pNode === treeNode) {
					break;
				}
				pNode = pNode.getParentNode();
			}
			if (!pNode) {
				singlePath(treeNode);
			}

		}
        function onExpand(event, treeId, treeNode) {
			curExpandNode = treeNode;
		} 
        function singlePath(newNode) {
			if (newNode === curExpandNode) return;

            var zTree = $.fn.zTree.getZTreeObj("tree"),
                    rootNodes, tmpRoot, tmpTId, i, j, n;

            if (!curExpandNode) {
                tmpRoot = newNode;
                while (tmpRoot) {
                    tmpTId = tmpRoot.tId;
                    tmpRoot = tmpRoot.getParentNode();
                }
                rootNodes = zTree.getNodes();
                for (i=0, j=rootNodes.length; i<j; i++) {
                    n = rootNodes[i];
                    if (n.tId != tmpTId) {
                        zTree.expandNode(n, false);
                    }
                }
            } else if (curExpandNode && curExpandNode.open) {
				if (newNode.parentTId === curExpandNode.parentTId) {
					zTree.expandNode(curExpandNode, false);
				} else {
					var newParents = [];
					while (newNode) {
						newNode = newNode.getParentNode();
						if (newNode === curExpandNode) {
							newParents = null;
							break;
						} else if (newNode) {
							newParents.push(newNode);
						}
					}
					if (newParents!=null) {
						var oldNode = curExpandNode;
						var oldParents = [];
						while (oldNode) {
							oldNode = oldNode.getParentNode();
							if (oldNode) {
								oldParents.push(oldNode);
							}
						}
						if (newParents.length>0) {
							zTree.expandNode(oldParents[Math.abs(oldParents.length-newParents.length)-1], false);
						} else {
							zTree.expandNode(oldParents[oldParents.length-1], false);
						}
					}
				}
			}
			curExpandNode = newNode;
		}
		
		function zTreeOnClick(event, treeId, treeNode){
			var id = treeNode.id;
			$("#menuName").html(treeNode.name);
			getSubMenu(id);
		}
		
		function getSubMenu(id){
			//异步请求该用户组下的权限
			mmUtl.ajax.postJson(url.path+"/wms-web-symgr/symgr/menu/listMenuRightTree.json",function(data){
				if(data.code == 0){
					console.info(data);
					var nodes = data.data;
					adapterButton(nodes);
				}else{
					mmui.oper(data.msg,1); 
				}
    		},{
    			"groupCode":gCode,
    			//测试使用，用户code到时候从缓存中取
    			"userCode":"admin",
    			//menu_type_1是菜单类型，menu_type_2是按钮类型
    			"menuType":"menu_type_2",
    			"pId":id
    		});
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
		
		
		$("input[name='enableMenu']").live('click',function(){	
			var attrchk = $(this).attr("checked");
			calcul($(this),attrchk === 'checked');
		});
		
		function calcul(el, isAdd) {
			
			var pId = el.attr("pId");
 			var id = el.attr("idNum");
			var leafSum = el.attr("leafSum");
			
			//父亲拥有数
			/* var hasNumTd = $("#sumRight_"+id); */
			//当前拥有数
			var hasSumNumTd = $("#sumRight_"+pId);
			
			/* var hasNum = parseInt(hasNumTd.text()); */
			var hasSumNum = parseInt(hasSumNumTd.text());
			if(leafSum == 0) {//叶子节点点击
				loop(id);
				/* hasNum = isAdd? ++hasNum :(--hasNum < 0 ? 0 : hasNum); */
				hasSumNum = isAdd? ++hasSumNum :(--hasSumNum < 0 ? 0 : hasSumNum);
				/* hasNumTd.html(hasNum); */
				hasSumNumTd.html(hasSumNum);
			} else {//非叶子节点点击
				
			}
			
		}
		
		function loop(id){
			
		}
		
		 $("#add").click(function(){
			 parent.mmgrid("${ctx}/jsp/admin/symgr/group/addGroup.jsp?method=add","groupAdd","新增用户组",true);
	    });
		 
		 $("#del").click(function(){
			 var length = $("input[name='btSelectItem']:checked").length;
		    	
		    	if(length!=1){
		    		parent.mmui.alert("请选择一条要删除的数据",3);
		    		return false;
		    	}
		    	var groupCode = "";
		    	$("input[name='btSelectItem']:checked").each(function(){
		    		groupCode = $(this).val();
		    	});
		    	parent.mmui.confirm("确定删除用户组 ?",function(delType){
		    		parent.mmui.close(delType);
		    		
		    		mmUtl.ajax.postJson(url.path+"/wms-web-symgr/symgr/group/delGroup",function(data){
		    			mmui.oper(data.msg,1); 
		    			document.location.reload();
		    		},{
		    			groupCode:groupCode
		    		});
				},function(){
					 
				});
		    });
		 $("#copy").click(function(){
			 var length = $("input[name='btSelectItem']:checked").length;
		    	
		    	if(length!=1){
		    		parent.mmui.alert("请选择一条要复制的数据",3);
		    		return false;
		    	}
		    	$("#last").val(gName);
		    	
		    	//显示弹框
	    		className = $(this).attr('class');
	    		$('#dialogBg').fadeIn(300);
	    		$('#dialog').removeAttr('class').addClass('animated '+className+'').fadeIn();
		    	
		    	
		 });
		//关闭弹窗
    	$('.claseDialogBtn').click(function(){
    		$('#dialogBg').fadeOut(300,function(){
    			$('#dialog').addClass('bounceOutUp').fadeOut();
    		});
    	});
		
		$("#submitBtn").click(function(){
			var groupName = $("#groupName").val();
			var dis = $("#discript").val();
			/* var url=url.path+'/wms-web-symgr/symgr/group/copyGroup';
			$("#editForm").attr("action",url);
			$("#gCode").val(gCode);
			$("#groupCode").val(groupCode); */
			mmUtl.ajax.postJsonAnt(url.path+'/wms-web-symgr/symgr/group/copyGroup', function(data){
				$('#dialog').addClass('bounceOutUp').fadeOut();
				document.location.reload();
				mmui.oper(data.msg,1);
			},{
				"groupName":groupName,
				"groupCode":gCode+Math.random() * 100,
				"gCode":gCode,
				"discript":dis
		    });
			
		});
		 
		 $("#upd").click(function(){
			 var length = $("input[name='btSelectItem']:checked").length;
		    	
		    	if(length!=1){
		    		parent.mmui.alert("请选择一条要修改的数据",3);
		    		return false;
		    	}
		    	/* var groupCode = "";
		    	$("input[name='btSelectItem']:checked").each(function(){
		    		groupCode = $(this).val();
		    	}); */
		    	parent.mmgrid("${ctx}/jsp/admin/symgr/group/addGroup.jsp?method=update&groupCode="+gCode+"&id="+gid,"groupUpd","修改用户组",true);
		    });
		 
		 $("#save").click(function(){
			 var dataJson=[];
				if($('input[name="enableMenu"]:checked').length == 0){
					//执行删除user下的userGroup
					mmUtl.ajax.postJsonAnt(url.path+'/wms-web-symgr/symgr/menuGoup/delMenuGroup', function(data){
						mmui.oper(data.msg,1); 
					},{
						"groupCode":gCode
					})
					
				}else{
					//执行给user分配userGroup
					$('input[name="enableMenu"]:checked').each(function(){
						dataJson.push({"groupCode":gCode,"menuCode":$(this).val()});
					
					});
					mmUtl.ajax.postJsonAnt(url.path+'/wms-web-symgr/symgr/menuGoup/addMenuGroupBatch', function(data){
						mmui.oper(data.msg,1); 
					},
						JSON.stringify(dataJson)
					)
				}
		   });
 </script> 
</html>