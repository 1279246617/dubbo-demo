<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="meta.jsp"%>

<title>管理后台</title>
<meta name="description" content="">
<meta name="author" content="">

<link href="${ctx}/3rd/mm-3.3/skin/header.css" rel="stylesheet">
<link href="${ctx}/3rd/mm-3.3/skin/main.css" rel="stylesheet">
<script src="${ctx}/3rd/mm-3.3/mm-main.js?xx=2"></script>

</head>

<body id="Allbody">

	<div id="wrapper">
		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			id="navigation"
			style="margin-bottom: 0; border: 0; min-height: 25px;">
			<%@ include file="header.jsp"%>
			<%@ include file="left.jsp"%>
		</nav>


		<script id="loadHeaderMenu" type="text/html">  
                    {{each data as value index}}
                          <li  mu="{{value.menuCode}}"><i class="fa fa-home"></i>&nbsp;{{value.menuName}}</li>
                     {{/each}}  
</script>


		<script type="text/javascript">
			ctx = "${ctx}"
			//递归封装菜单
			function packageChildMenu(menuList) {

				var str = "";

				$(menuList)
						.each(
								function() {
									if (this.dictMenuType == 'menu_type_1') {
										str += '<li><a href="#"><i class="fa fa-bar-chart-o fa-fw"></i>'
												+ this.menuName
												+ '<span class="fa arrow"></span></a>';
										str += '<ul class="nav nav-second-level in">';

										if (this.childrenMenuVo != null
												&& this.childrenMenuVo.length != 0) {
											var strRes = packageChildMenu(this.childrenMenuVo);
											str += strRes;
										}

										str += '</ul>';
									} else {
										str += '<li><a class="use" href="'
												+ ctx + this.menuEvent
												+ '?menuId=' + this.id
												+ '" pageuid="' + this.id
												+ '" tag="">' + this.menuName
												+ '</a></li>';
									}

								})
				return str;

			}

			function showTips(msg, status, timeOut) {
				mmui.alert(msg, status, timeOut);
			}

			$(function() {
				loadConfig();
				//加载页面前加载主菜单
				doGet(globalUrl.symgmtPath + "/symgmt/menu/getHeaderMenu",
						function(data) {
							if (data.code != 0) {
								showTips(data.msg, 2, 2000)
								if (data.code == 2) {
									reloadNewUri("${ctx}/jsp/admin/login.jsp",
											2000)
								}
								return;
							} else {
								var html = template('loadHeaderMenu', data);
								$("#headerMenue").html(html);

								//加载左侧菜单
								loadMenuLeft();
							}
						})

			})
			/**
			加载左侧菜单
			 */
			function loadMenuLeft() {

				//远程加载左侧菜单
				doGet(
						globalUrl.symgmtPath + "/symgmt/menu/getAllMenu",
						function(data) {
							if (data.code != 0) {
								showTips(data.msg, 2, 2000)
								if (data.code == 2) {
									reloadNewUri("${ctx}/jsp/admin/login.jsp",
											2000)
								}

								return;
							}

							var leftStr = "";

							var list = data.data;

							var allStr = "";

							$(list)
									.each(
											function() {
												var leftStr = '<ul class="nav side-menu" mudo="'
														+ this.menuCode
														+ '">';
												if (this.childrenMenuVo != null
														&& this.childrenMenuVo.length != 0) {
													//递归封装菜单
													var strRes = packageChildMenu(this.childrenMenuVo);
													leftStr += strRes;
												}

												leftStr += '</ul>';
												allStr += leftStr;
											})

							$("#leftMenuId").html(allStr)

							
							loadHead();
							bindMuenEvent();
							initMain();
							
						})

			}
		</script>


		<div id="page-wrapper" style="overflow: hidden; margin-left: 200px;">
			<%@ include file="nav.jsp"%>
			<div id="page_body">
				<iframe iframeuid="adminMain"
					style="width: 100%; height: 100%; border: 0;"
					src="${ctx}/admin/index"></iframe>
			</div>
		</div>
	</div>


	<script type="text/javascript">
		//绑定菜单事件
		function bindMuenEvent() {
			$('.side-menu').metisMenu();

			$(window)
					.bind(
							"load resize",
							function() {
								topOffset = 50;
								width = (this.window.innerWidth > 0) ? this.window.innerWidth
										: this.screen.width;
								if (width < 768) {
									$('div.navbar-collapse').addClass(
											'collapse');
									topOffset = 100;
								} else {
									$('div.navbar-collapse').removeClass(
											'collapse');
								}
							});

			var element = $('ul.nav a').filter(function() {
				var tag = $(this).attr("tag");
				var text = $(this).text();
				return tag == text;
			}).addClass('active').parent().parent().addClass('in').parent();

			if (element.length > 0 && element.is('li')) {
				element.addClass('active');
			}

			var hmbEles = $(".hmb-eles li");
			hmbEles.click(function() {
				hmbEles.each(function() {
					$(this).removeClass("sel");
				});
				$(this).addClass("sel");
				var mu = $(this).attr("mu");

				$("#left_body").find("[mudo]").hide();
				$("#left_body").find("[mudo='" + mu + "']").show();

			});
		}

		//加载头部
		function loadHead() {
			var headerBody = $("#header_body");
			var navBody = $("#nav_body");
			var navBox = $("#nav_box");
			var jmenuTabs = navBody.find(".J_menuTabs");
			var sz = 200;
			var totalL = $(window).width() - 200 - sz;

			/*
			headerBody.find("a[sitename]").click(function(){
				var siteCode=$(this).attr("sitecode");
				var siteName=$(this).attr("sitename");
				
				mmUtl.ajax.postJson("/admin/user/updateSeat", function(result){
					if(result.msg!='success'){
						parent.mmui.msg(result.msg,{icon:3,time:2000,shift:6,offset:0});
					}else{
						headerBody.find("[ja='showsitename']").html(siteName);
					}
				},{siteCode:siteCode,siteName:siteName})
			});
			 */
			var headerMenuBody = $("#header_menu_body");

			var winW = $(window).width();
			var menuW = winW - 360;
			headerMenuBody.find(".hmb-left").width(menuW);

			var hmbEles = headerMenuBody.find(".hmb-eles");
			var hmblen = hmbEles.find("li").length;
			var totalLen = hmblen * 150;
			hmbEles.width(totalLen);

			headerMenuBody.find(".hmb-right").click(function() {
				var ml = hmbEles.css("marginLeft");
				ml = ml.replace("px", "");
				ml = Number(ml);

				if (totalLen - menuW + ml > 20) {
					hmbEles.css("marginLeft", ml - 150);
				}
			});

			$("#hidden_left").click(function() {
				var s = $(this).attr("s");
				if ("y" == s) {
					$("#left_body").css("marginLeft", "-170px");
					$("#page-wrapper").css("marginLeft", "30px");
					$(this).removeClass("fa-angle-double-left");
					$(this).addClass("fa-angle-double-right");
					$(this).attr("s", "n");
				} else {
					$("#left_body").css("marginLeft", "0");
					$("#page-wrapper").css("marginLeft", "200px");
					$(this).removeClass("fa-angle-double-right");
					$(this).addClass("fa-angle-double-left");
					$(this).attr("s", "y");
				}
			})

			function getw() {
				totalL = $(window).width() - 200 - sz;
				var w = 0;
				navBox.find("a").each(function(i) {
					w += $(this).outerWidth();
				});
				return w;
			}

			function getnw(ml) {
				var w = 0, ww = 0;
				var as = navBox.find("a");
				as.each(function(i) {
					w += $(this).outerWidth();
					if (w > -ml + 80) {
						ww = $(this).outerWidth();
						return false
					}
				});
				return ww;
			}

			navBody.find(".leftbtn").click(function() {
				var ml = jmenuTabs.css("marginLeft");
				ml = ml.replace("px", "");
				ml = Number(ml);
				var w = getw();
				if (totalL - w - ml + 80 < 0) {
					jmenuTabs.css("marginLeft", ml - getnw(ml));
				}
			});

			navBody.find(".rightbtn").click(function() {
				var ml = jmenuTabs.css("marginLeft");
				ml = ml.replace("px", "");
				ml = Number(ml);
				if (ml < 80) {
					jmenuTabs.css("marginLeft", ml + getnw(ml));
				}
			});

		}
	</script>


</body>

</html>


