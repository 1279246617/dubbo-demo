/**
 * 2013-12-8
 * by lihaipeng
 */
		var scrollOpt = null;
$.extend({
			showMessage:function(msg){ //滚动字幕
				if(!!$("#message").length || msg=="")return;
				var msgDiv = '<div class="dropdown" id = "message" style="white-space:nowrap;overflow:hidden;background-color:#fff;color:#ff0000;left:30%;top:40px;width:40%;height:20px;border-radius: 10px;position:absolute;z-index:1">'+
							  '<div class="pull-left" style="padding:2px 0 0 0;margin-left:'+$(window).width()*0.4+'px;" id="messageContent">'+msg+'</div></div>';
				
				$("body").prepend(msgDiv);
				scrollOpt = setInterval("scrollMessage(document.getElementById('messageContent'))",30);
			},
            showErrorMessage:function(orderId,msg){
                $(".error-message-"+orderId).show().ligerTip({
                    auto:true,
                    content:msg
                });
            },
            showTips:function(id,msg){
                $("#"+id).show().ligerTip({
                    auto:true,
                    content:msg
                });
            },
            showCanCancelMessage:function(orderId,msg){
                $(".isCanCancel-message-"+orderId).show().ligerTip({
                    auto:true,
                    content:msg
                });
            },
            showInSureMessage:function(orderId,msg){
                $(".inSure-message-"+orderId).show().ligerTip({
                    auto:true,
                    content:msg
                });
            },
            
            showDialogMessage:function(content, callback, parent ){//弹出窗提示
                return lhgdialog({
                    title: '警告',
                    id: 'Alert',
                    zIndex: _zIndex(),
                    icon: 'alert.gif',
                    fixed: true,
                    lock: true,
                    content: content,
                    ok: true,
                    resize: false,
                    close: callback,
                    parent: parent || null
                });
            },
            showShortMessage:function(args){//显示简短提示
                if($("#showMessage").length) return false;
                var msg = typeof args.msg != "undefined" ? args.msg : "提示信息内容", //提示内容
                    animate = typeof args.animate != "undefined" && !args.animate ? false : true,  //是否动态向上移送
                    bgColor = typeof args.bgColor != "undefined" ? args.bgColor : "#ff0000",  //背景色
                    top = typeof args.top != "undefined" ? args.top:"50%", //上边距
                    left = typeof args.left != "undefined" ? args.left:"40%", //左边距
                    toTop = typeof args.toTop != "undefined" ? args.toTop : "0", //向上移动到的位置
                    callback = typeof args.callback != "undefined" ? setTimeout(args.callback,6010):null;
                //html
                var messageHtml = "<div class='showMessage' id='showMessage' align='center'><span>"+msg+"</span></div>";
                $(document.body).prepend(messageHtml);
                //显示
                $("#showMessage").css(
                    {
                        padding:"10px 15px 15px 15px",
                        cursor:"default" ,
                        backgroundColor:bgColor,
                        top:top,
                        left:left,
                        float: "left",
                        textAlign:"center",
                        lineHeight: "24px",
                        fontSize: "16px",
                        fontWeight:"bold",
                        height: "18px",
                        borderRadius:"5px",
                        color: "#ffffff",
                        display: "none",
                        visibility: "visible",
                        position: "absolute",
                        zIndex: "99999"
                    })
                .fadeIn(1000);
                animate ? setTimeout(function(){
                    executiveAnimation(toTop);
                },1000):setTimeout(function(){
                    $("#showMessage").fadeOut(3000);
                    setTimeout(function(){$("#showMessage").remove();},3000);
                },1000);
            },
            isDateTime:function(dateTime){//验证日期格式
                return /^(\d{4})\-(\d{2})\-(\d{2})(\s{1})(\d{2}):(\d{2}):(\d{2})$/.test(dateTime);
            }
		});

    $.fn.extend({
         inputInsideTip:function(color){ //输入框未输入字符时提示字体
            var defaultValue = $(this).val();
            $(this).css({color:color?color:"#999"});
            $(this).bind({
                focus:function(){
                    var curValue = $.trim($(this).val());
                    if(curValue == defaultValue){
                        $(this).val("");
                    }
                },
                blur:function(){
                    var curValue = $.trim($(this).val());
                    if(curValue == ""){
                        $(this).val(defaultValue);
                    }
                }
            });
         }
    });

		//滚动字幕 type,message
		function scrollMessage(obj) {
			var lf = $(obj).offset().left;
			if(obj.offsetLeft<-obj.offsetWidth){
		    	clearInterval(scrollOpt);
		    	$("#message").remove();
		    	return false;
		    }
			$(obj).offset({left:lf-=1});
		}

        //向上移动动画淡出
        function executiveAnimation(toTop){
            $("#showMessage").animate({top:toTop},1500);
            $("#showMessage").fadeOut(4500);
            setTimeout(function(){$("#showMessage").remove();},6000);
        }