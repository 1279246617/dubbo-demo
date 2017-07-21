/**
 * 后端页面整体骨架
 * 需要先引入：mm-base.js
 * version：3.3
 * LastDate: 2017-07-10
 */
!function(window, jQuery) {
	"use strict";
	if(window.initMain)return ;
	
	var $ = jQuery,
	win = $(window);
	
	/*************************************定义左侧菜单对象****************************************/
	
   function Plugin(element, options) {
       this.element = $(element);
       this._defaults = {toggle: true,doubleTapToGo: false};
       this.settings = $.extend({}, this._defaults, options);
       this._name = 'metisMenu';
       this.init();
   }

   Plugin.prototype = {
       init: function() {

           var $this = this.element,
               $toggle = this.settings.toggle,
               obj = this;

           if (this.isIE() <= 9) {
               $this.find("li.active").has("ul").children("ul").collapse("show");
               $this.find("li").not(".active").has("ul").children("ul").collapse("hide");
           } else {
               $this.find("li.active").has("ul").children("ul").addClass("collapse in");
               $this.find("li").not(".active").has("ul").children("ul").addClass("collapse");
           }

           if (obj.settings.doubleTapToGo) {
               $this.find("li.active").has("ul").children("a").addClass("doubleTapToGo");
           }

           $this.find("li").has("ul").children("a").on("click" + ".metisMenu", function(e) {
               e.preventDefault();

               if (obj.settings.doubleTapToGo) {

                   if (obj.doubleTapToGo($(this)) && $(this).attr("href") !== "#" && $(this).attr("href") !== "") {
                       e.stopPropagation();
                       document.location = $(this).attr("href");
                       return;
                   }
               }

               $(this).parent("li").toggleClass("active").children("ul").collapse("toggle");

               if ($toggle) {
                   $(this).parent("li").siblings().removeClass("active").children("ul.in").collapse("hide");
               }

           });
       },

       isIE: function() {
           var undef,
               v = 3,
               div = document.createElement("div"),
               all = div.getElementsByTagName("i");

           while (
               div.innerHTML = "<!--[if gt IE " + (++v) + "]><i></i><![endif]-->",
               all[0]
           ) {
               return v > 4 ? v : undef;
           }
       },
       doubleTapToGo: function(elem) {
           var $this = this.element;

           if (elem.hasClass("doubleTapToGo")) {
               elem.removeClass("doubleTapToGo");
               return true;
           }

           if (elem.parent().children("ul").length) {
               $this.find(".doubleTapToGo").removeClass("doubleTapToGo");
               elem.addClass("doubleTapToGo");
               return false;
           }
       },

       remove: function() {
           this.element.off(".metisMenu");
           this.element.removeData('metisMenu');
       }
   };
   
	//jquery插件
	$.fn.metisMenu = function(options) {
      this.each(function () {
          var el = $(this);
          if (el.data('metisMenu')) {
              el.data('metisMenu').remove();
          }
          el.data('metisMenu', new Plugin(this, options));
      });
      return this;
	};
   
   /****************************************页面布局--小菜单****************************************/
   
		function initView(){
			var wh=$(window).height();
			$("#page-wrapper").height(wh-45);
			$("#page_body").height(wh-78);
			
			$(window).resize(function(){
				initView();
			});
		}
		
		//小菜单事件
		function addNavEvent(navuid){
			var nav=$("#nav_box").find("[navuid='"+navuid+"']")
			nav.click(function(){
				if($(this).hasClass("active")){
					return false;
				}
				var pageuid=$(this).attr("navuid");
		    	$("#page_body>iframe").hide();
		    	$("#nav_box .active").removeClass("active");
				
		    	$("#page_body").find("[iframeuid='"+pageuid+"']").show();
		    	$(this).addClass("active");
		    	return false;
			});
			nav.find("i").click(function(){
				var aele=$(this).parent();
				var uid=aele.attr("navuid");
				
				if(aele.hasClass("active")){
					aele.prev().trigger("click");
				}
				
				$("#page_body").find("[iframeuid='"+uid+"']").remove();
				aele.remove();
				return false;
			});
		}
		
		//点击链接---新窗口事件
	    function clickLink(href,pageuid,pageName,flush){
	    	$("#page_body>iframe").hide();
	    	$("#nav_box .active").removeClass("active");
	    	var iframe=$("#page_body").find("[iframeuid='"+pageuid+"']");
	    	if(!flush&&iframe.length>0){
	    		iframe.show();
	    		iframe[0].contentWindow.location.reload();
	    		$("#nav_box").find("[navuid='"+pageuid+"']").addClass("active");
	    	}else{
	    		$("#page_body").find("[iframeuid='"+pageuid+"']").remove();
	    		$("#nav_box").find("[navuid='"+pageuid+"']").remove();
	    		
	    		$("#page_body").append("<iframe iframeuid=\""+pageuid+"\" style=\"width:100%;height:100%;border: 0;\" src=\""+href+"\" ></iframe>");
	    		$("#nav_box").append("<a href=\"javascript:;\" class=\"J_menuTab active\" navuid=\""+pageuid+"\">"+pageName+"&nbsp;<i class=\"fa fa-times-circle\"></i></a>");
	    		addNavEvent(pageuid);
	    	}
	    }
		
	    function initMain(){
	    	//初始化---页面布局
	    	initView();
	    	//初始化---首页面事件
	    	addNavEvent("adminMain");
	    	//绑定---左侧菜单点击事件
	        $("#left_body").find("[pageuid]").click(function(){
	        	var pageuid=$(this).attr("pageuid");
	        	var pageName=$(this).text();
	        	var href=$(this).attr("href");

	        	clickLink(href,pageuid,pageName,false);
	        	return false;
	        });
	    }
	    
	    
	

	//小菜单---jquery插件---方便外部使用
	$.fn.mmgrid = function() {
		this.click(function(){
        	var pageuid=$(this).attr("pageuid");
        	var pageName=$(this).attr("pagename");
        	if(!pageName){
        		pageName=$(this).text();
        	}
        	var href=$(this).attr("href");
        	
        	parent.mmgrid(href,pageuid,pageName,true);
        	return false;
	    });
	};
	
    //parent.window.mmgrid("${ctx}/admin/forecast/auditForecastList?forecastNo="+forecastNo,"adminForecastAuditListByNo","预报明细",true);
    
    window.initMain=initMain;
	window.mmgrid=clickLink;
	
	/**************************页面都加载完了之后，初始化************************/
	
	$(function() {
		//初始化整个页面骨架
		//window.initMain();
	});
	
}(window,jQuery)

/**************************header************************/

$(function(){
	
	//loadHead();
	
})

/*********************************left*********************************/

$(function() {
	
});