<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="meta.jsp"%> 

<title>管理后台</title>
<meta name="description" content="">
<meta name="author" content="">

<link href="${ctx}/3rd/metisMenu/metisMenu.min.css" rel="stylesheet">
<script src="${ctx}/3rd/metisMenu/metisMenu.min.js"></script>
<script src="${ctx}/js/common.js"></script>
<link href="${ctx}/3rd/admin/css/main.css?v=<%=Math.random() %>" rel="stylesheet">
<script type="text/javascript">

function getCookie(c_name){
	console.info("cookie",document.cookie);
	if (document.cookie.length>0){
		var c_start=document.cookie.indexOf(c_name + "=");
		alert(c_start);
		if (c_start!=-1){
			c_start=c_start + c_name.length+1;
			alert(c_start);
			var c_end=document.cookie.indexOf(";",c_start);
			alert(c_end);
			if (c_end==-1){
				c_end=document.cookie.length;
			}
			alert(c_end);
			return unescape(document.cookie.substring(c_start,c_end));
		}
	}
	return "";
};

//样式数组
var classMap = ["side-menu","nav-second-level","nav-third-level"]
function build(d, pId, level,pCode){
	var rs = []
 	for(var i=0, len = d.length; i<len; i++){
		if(d[i].pId === pId && d[i].menuType === '菜单'){
			var sub = build(d, d[i].id, level+1);
			var menuEvent = d[i].menuEvent || "#";
			if(menuEvent != "#"){
				menuEvent = $("#ctxPath").val()+menuEvent+"?id="+d[i].id;
			}
			var html = ['<li>', '<a class="use" href="',menuEvent,'" pageuid="', d[i].menuCode, '" tag="" >', d[i].name];
			if(sub && sub.length>0){
				html.push('<span class="fa arrow"></span>');
			}
			
			html.push('</a>');
			html = html.concat(sub);
			html.push("</li>");
			 
			rs.push(html.join(''))
		}
	}
	if(rs.length >0 ){
	console.info('level:', level)
		var cls = classMap[level];
		if(cls != "side-menu"){
			rs.splice(0,0,'<ul class="nav '+cls+'">');
		}else{
			rs.splice(0,0,'<ul class="nav '+cls+'" mudo="'+pCode+'">');
		}
		rs.push("</ul>");
	}
 	return rs;
 }

function addMenuEvent(){
	$('.side-menu').metisMenu();
	 
    $(window).bind("load resize", function() {
        topOffset = 50;
        width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        if (width < 768) {
            $('div.navbar-collapse').addClass('collapse');
            topOffset = 100;
        } else {
            $('div.navbar-collapse').removeClass('collapse');
        }
    });

    var element = $('ul.nav a').filter(function() {
		var tag=$(this).attr("tag");
		var text=$(this).text();
        return tag == text;
    }).addClass('active').parent().parent().addClass('in').parent();
    
    if (element.length>0&&element.is('li')) {
        element.addClass('active');
    }
   
	var hmbEles=$(".hmb-eles li");
	hmbEles.click(function(){
		hmbEles.each(function(){
			$(this).removeClass("sel");
		});
		$(this).addClass("sel");
		var mu=$(this).attr("mu");
		
		$("#left_body").find("[mudo]").hide();
		$("#left_body").find("[mudo='"+mu+"']").show();
		
	});
}

$(function(){
	var session1=getCookie('SESSION_ID');
	var session2=getCookie('SESSION_TOKEN');
	console.info($("#addMenuOne li:first"));
	/* if(session1=="" || session2==""){
		window.location.href="login.jsp";
	} */
	function addDiv(menuList){
		var lis = "";
		$.each(menuList, function (n, value) {
			if(value.pId == null){
				var liOne = "";
		        liOne += "<li mu='"+value.menuCode+"'>"+value.name+"</li>";
		        lis += liOne;
		        var rs = build(menuList, value.id, 0,value.menuCode);
		        $("#menuAll").append(rs.join(''));
				console.info("====================",rs.join(''))
			}
	    });
	    $("#addMenuOne").append(lis);
	    addMenuEvent();
	    headerEvent();
	    initMain();
	}
	
	/* var menuList; */
	mmUtl.ajax.postJson(url.path+"/wms-web-symgr/symgr/menu/getMnuRightTree.json",function(data){
		
		if(data.code == 0){
			menuList = data.data;
			addDiv(menuList);
		}else{
			mmui.oper(data.msg,1); 
			/* addDiv(menuList); */
		}
	});
	
});

</script>
</head>

<body>
	<input type="hidden" id="ctxPath" value="${ctx}/jsp/admin/">
    <div id="wrapper">
        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" id="navigation" style="margin-bottom: 0;border:0;min-height: 25px;">
			<%@ include  file="header.jsp"%>
           	<%@ include  file="left.jsp"%>
        </nav>

        <div id="page-wrapper" style="overflow:hidden;margin-left:200px;">
       	 	<%@ include  file="nav.jsp"%>
        	<div id="page_body">
        		<iframe iframeuid="adminMain" style="width:100%;height:100%;border: 0;" src="${ctx}/jsp/admin/index.jsp" ></iframe>
        	</div>
        </div>
    </div>

</body>
</html>

