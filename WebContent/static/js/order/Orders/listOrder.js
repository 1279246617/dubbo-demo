/**
 * Created by JetBrains WebStorm.
 * User: lihaipeng
 * Date: 13-12-26
 * Time: 下午9:31
 * To change this template use File | Settings | File Templates.
 */
$(function(){
    //日期
    $('#date_inp1,#date_inp2').calendar({ format:'yyyy-MM-dd HH:mm:ss' });
    //设置分页高度
    var win = $(window).height();
    $("#orderList").css({height:parseInt(win)-114});
    //给页面默认一个显示订单数量
    $("#pagination .orderListSize").val(listOrder.pageSize);
    $("#pagination .perPageSize").text(listOrder.pageSize);

    //获取订单数据
    listOrder.pageSize = $("#pagination .orderListSize").val();
    listOrder.curPage = $("#pagination .curPage").val();
    refreshOrderList();
});

//获取订单列表  公共方法，供所有订单展示页面和搜索操作调用
var refreshOrderList = function(){
    var dateVal1 = $("#date_inp1").val();
    var dateVal2 = $("#date_inp2").val();
    //关键字搜索
    var keyword = $.trim($("#keyword").val());  
    
  //如果选择了日期，需要验证格式是否正确，后面日期是否比前面小
	if(dateVal1 && !parent.$.isDateTime(dateVal1)){
		 parent.$.showShortMessage({msg:"起始时间格式错误",animate:false});
	     return false;
	}
	if(dateVal2 && !parent.$.isDateTime(dateVal2)){
		parent.$.showShortMessage({msg:"结束时间格式错误",animate:false});
        return false;
	}
    if(dateVal1 && dateVal2 && (dateVal1 > dateVal2)){
    	parent.$.showShortMessage({msg:"起始时间不能大于结束时间",animate:false});
        return false;
    }
    
  //搜索时，显示加载中...
    var loadingHtml = '<img id="loading" src="'+listOrder.ctxpath+'/public/img/loading.gif" alt="加载中..." />';
    $("#orderList").html(loadingHtml);
    
    var queryVo = {}; //参数
    queryVo.orderListSize = listOrder.pageSize;  //当前页显示的订单数量
    queryVo.orderListPage = listOrder.curPage; //当前页码
    queryVo.status = listOrder.status;  //当前页面订单状态
    queryVo.shipwayCode = listOrder.shipwayCode; //当前页面订单运送方式
    queryVo.timeStart = dateVal1;  //日期起
    queryVo.timeEnd = dateVal2; //日期止
    queryVo.search = keyword;
    
    //请求--使用同步请求
    $.ajax({
        type:'post',
        url:listOrder.ctxpath+'/order.Orders/listOrderInfo',
        async:false,
        data:{queryVo:queryVo},
        success:function(result){
            $("#orderList").html(result);
            //订单总数
            $("#pagination .total").text(total);
            var curPage = listOrder.curPage;
            var pageSize = listOrder.pageSize;
            var pageTotal = Math.ceil(parseInt(total,10)/parseInt(pageSize,10));  //向上取整，Math.floor()为向下取整
            $("#pagination .perPageSize").text(pageSize);
            $("#pagination .pageDataStart").text((parseInt(curPage,10)-1)*pageSize+1);
            $("#pagination .pageTotal").text(pageTotal<1?1:pageTotal);
            //上翻页
            if(parseInt(curPage,10)>1){ //可以上翻
                $("#pagination .l-bar-btnfirst,#pagination .l-bar-btnprev").children().removeClass("l-disabled");
            }else{
                $("#pagination .l-bar-btnfirst,#pagination .l-bar-btnprev").children().addClass("l-disabled");
            }
            //下翻页
            if(curPage*pageSize >= parseInt(total,10)){  //当前页的最大数大于订单总数
                $("#pagination .pageDataEnd").text(total);
                //下一页按钮不能点击
                $("#pagination .l-bar-btnnext,#pagination .l-bar-btnlast").children().addClass("l-disabled");
            }else{
                $("#pagination .pageDataEnd").text(curPage*pageSize);
                //下一页按钮可点击
                $("#pagination .l-bar-btnnext,#pagination .l-bar-btnlast").children().removeClass("l-disabled");
            }
            //生成订单轨迹操作
            createOrderTrack();
        }
    });
    //刷新订单列表时  全选按钮 取消选中状态
    $("#check_all").attr("checked",false);
    
    //如果订单为已打印之后，则用户不能对订单做任何操作
    if(listOrder.status > 1){
        $("span").unbind("click");
        $("span").removeAttr("onclick");
        $("#orderList .btn").hide();
    }
};

//全选和取消全选
$("#check_all").click(function(){
    if(!this.checked){ //已经选上了
        $(this).attr("checked",false);
        $(".checkbox-per").each(function(i,e){
            $(e).attr("checked",false);
        });
    }else{ //全选
        $(this).attr("checked",true);
        $(".checkbox-per").each(function(i,e){
            $(e).attr("checked",true);
        });
    }
});
//当订单状态为已经收货（包括已经收货）之后状态时，跟踪号码变为可以点击，弹出订单轨迹
var createOrderTrack = function(){
    if(listOrder.status < 4){ //已经收货之前订单无轨迹
        return false;
    }
    $(".trackingNo_span").each(function(){
        var orderId = $(this).attr("orderId");
        var trackingNo = $(this).text();
        $(this).replaceWith('<a class="trackingNo_span overflow-ellipsis trackingNo-a" style="text-decoration: underline;" href="javascript:void(0);" onclick="findOrderTrack('+orderId+');">'+trackingNo+'</a>');
    });
};
listOrder.clickPerCheckbox = function(){
    var num = 0;
    var len = $(".checkbox-per").length;
    $(".checkbox-per").each(function(i,e){
        if(e.checked){
            num++;
        }
    });
    if(num == len){//全选
        $("#check_all").attr("checked",true);
    }
    if(len-num == len){//取消全选
        $("#check_all").attr("checked",false);
    }
};

//查找订单轨迹
var findOrderTrack = function(orderId){
    if(!orderId){
        return ;
    }
    $.dialog({
		lock : true,
		title : '订单轨迹',
		width : '600px',
		height : '400px',
		content : 'url:' + listOrder.ctxpath+"/order.Orders/showTrack?orderId="+orderId,
		button : [{
			name : '确定',
			callback : function() {
				
			}
		}],
		cancel : true
	});
    
};

//搜索订单
$("#btn_search").click(function(){
    refreshOrderList();
});
//查询条件改变
$("#oCategory").change(function(){
    $("#oKeywords").attr("name","o."+this.value);
});
//获取用户选择订单
listOrder.getSelectedOrders = function(){
	var orderIds = "";
	$(".checkbox-per").each(function(i, e) {
        if(e.checked){
            orderIds += $(e).attr("orderId")+",";
        }
	});
    if(!orderIds){//没有数据
        return "";
    }
    return orderIds?orderIds.substring(0,orderIds.length-1):"";
};

//获取当前页表格中所有订单的ID
listOrder.getAllOrderIds = function(){
    var allOrderIds = "";
	$(".checkbox-per").each(function(i, e) {
            allOrderIds += $(e).attr("orderId")+",";
	});
    if(!allOrderIds){//没有数据
        return "";
    }
    allOrderIds = allOrderIds.substring(0,allOrderIds.length-1);
    return allOrderIds;
};

//订单导出
listOrder.exportOrder = function(){
    var contentArr = [];
        contentArr.push('<div id="showLoading" align="center" style="padding:10px;display: none;cursor: default;">');
        contentArr.push('   <img src='+listOrder.ctxpath+'"/public/img/loading.gif"><span style="margin-bottom: 10px;">正在操作中......</span>');
        contentArr.push('</div>');
        contentArr.push('<div id="changeContent" style="padding:10px;width: 240px;">');
        contentArr.push('   <div class="pull-left" style="width: 100%">');
        contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" checked="checked" value="selected" id="selected">');
        contentArr.push('       <label class="pull-left" style="margin-left: 5px" for="selected">导出选中</label>');
        contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" value="all" id="all">');
        contentArr.push('       <label class="pull-left" style="margin-left: 5px;" for="all">导出当前页</label>');
        contentArr.push('   </div>');
        contentArr.push('</div>');
        var contentHtml = contentArr.join('');
        $.dialog({
                lock: true,
                max: false,
                min: false,
                title: '订单导出',
                width: 260,
                height: 60,
                content:contentHtml ,
                button: [{
				name: '导出',
				callback: function() {
                    var selected = parent.$("#selected").attr("checked");
                    var all = parent.$("#all").attr("checked");
                    var orderIds = "";
                    if(selected){ //更改选中
                        orderIds = listOrder.getSelectedOrders();
                        if(!orderIds){
                            this.close();
                            parent.$.showShortMessage({msg:"请至少选择一项",animate:false});
                            return false;
                        }
                    }else{
                          orderIds = listOrder.getAllOrderIds();
                    }
                    //导出选择订单
                    window.location = listOrder.ctxpath+"/order.OrderExport/exportOrder?orderIds="+orderIds;

                    this.close();
                    return false;
				}
				},
				{
					name: '取消'
		        }]
	    })
};

//删除单个订单
listOrder.delSingleOrder = function(thisDom) {
    var orderId = $(thisDom).attr("orderId");
	$.dialog({
		lock: true,
		max: false,
		min: false,
		title: '提示',
		width: 260,
		height: 60,
		content: '<div style="margin: 10px;">您确认删除该订单吗？</div>',
		button: [{
			name: '确认',
			callback: function() {
                var that = this;
				$.getJSON(listOrder.ctxpath+"/order.Orders/deleteOrder?orderIds="+orderId,function(msg) {
					if (msg == 1) {
                        parent.refreshLeftMenu();//刷新左侧菜单数量
						refreshOrderList();
                    }
                    that.close();
				});
                return false;
			}
		},
		{
			name: '取消'
		}]
	})
};

//批量删除订单
listOrder.delBatchOrder = function() {
	 var contentArr = [];
     contentArr.push('<div id="showLoading" align="center" style="padding:10px;display: none;cursor: default;">');
     contentArr.push('   <img src='+listOrder.ctxpath+'"/public/img/loading.gif"><span style="margin-bottom: 10px;">正在操作中......</span>');
     contentArr.push('</div>');
     contentArr.push('<div id="changeContent" style="padding:10px;width: 240px;">');
     contentArr.push('   <div class="pull-left" style="width: 100%">');
     contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" checked="checked" value="selected" id="selected">');
     contentArr.push('       <label class="pull-left" style="margin-left: 5px" for="selected">删除选中</label>');
     contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" value="all" id="all">');
     contentArr.push('       <label class="pull-left" style="margin-left: 5px;" for="all">删除当前页</label>');
     contentArr.push('   </div>');
     contentArr.push('</div>');
     var contentHtml = contentArr.join('');
     $.dialog({
             lock: true,
             max: false,
             min: false,
             title: '删除订单',
             width: 260,
             height: 60,
             content:contentHtml ,
             button: [{
	             name: '确认',
				 callback: function() {
	                 //显示加载中并隐藏按钮
	                 parent.$("#changeContent").hide();
	                 parent.$("#showLoading").show();
	                 $(this.DOM.buttons[0]).hide();
	                 var selected = parent.$("#selected").attr("checked");
	                 var all = parent.$("#all").attr("checked");
	                 var orderIds = "";
	                 if(selected){ //更改选中
	                      orderIds = listOrder.getSelectedOrders();
	                      if(!orderIds){
	                          this.close();
	                          parent.$.showShortMessage({msg:"请至少选择一项",animate:false});
	                          return false;
	                      }
	                 }else{
	                      orderIds = listOrder.getAllOrderIds();
	                 }
	                 var that = this;
	                 $.getJSON(listOrder.ctxpath+"/order.Orders/deleteOrder?orderIds="+orderIds, function(msg) {
	  					if (msg == 1) {
	                        parent.refreshLeftMenu();
	  						refreshOrderList();
	                    }
	  					that.close();
	  				});
	                return false;
				}
			},
			{
				name: '取消'
	        }]
	    })
};


//更改订单状态
listOrder.changeStatus = function(toStatus){
        var contentArr = [];
        contentArr.push('<div id="showLoading" align="center" style="padding:10px;display: none;cursor: default;">');
        contentArr.push('   <img src='+listOrder.ctxpath+'"/public/img/loading.gif"><span style="margin-bottom: 10px;">正在操作中......</span>');
        contentArr.push('</div>');
        contentArr.push('<div id="changeContent" style="padding:10px;width: 240px;">');
        contentArr.push('   <div class="pull-left" style="width: 100%">');
        contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" checked="checked" value="selected" id="selected">');
        contentArr.push('       <label class="pull-left" style="margin-left: 5px" for="selected">更改选中</label>');
        contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" value="all" id="all">');
        contentArr.push('       <label class="pull-left" style="margin-left: 5px;" for="all">更改当前页</label>');
        contentArr.push('   </div>');
        contentArr.push('</div>');
        var contentHtml = contentArr.join('');
        $.dialog({
                lock: true,
                max: false,
                min: false,
                title: '更改状态',
                width: 260,
                height: 60,
                content:contentHtml ,
                button: [{
				name: '确认',
				callback: function() {
                    //显示加载中并隐藏按钮
                    parent.$("#changeContent").hide();
                    parent.$("#showLoading").show();
                    $(this.DOM.buttons[0]).hide();

                    var selected = parent.$("#selected").attr("checked");
                    var all = parent.$("#all").attr("checked");
                    var orderIds = "";
                    if(selected){ //更改选中
                        orderIds = listOrder.getSelectedOrders();
                        if(!orderIds){
                            this.close();
                            parent.$.showShortMessage({msg:"请至少选择一项",animate:false});
                            return false;
                        }
                    }else{
                          orderIds = listOrder.getAllOrderIds();
                    }
                    //更改状态，
                    var that = this;
                    $.getJSON(listOrder.ctxpath+"/order.Orders/updateOrderStatus?orderIds="+orderIds+"&status="+toStatus,function(msg) {
                    	if (msg.status == 1) {
                            parent.refreshLeftMenu();
                            refreshOrderList();
                        }
                    	that.close();
					});
                    return false;
				}
				},
				{
					name: '取消'
		        }]
	    })
};
//设置运输方式
listOrder.settingShipway = function(){
        var contentArr = [];
        contentArr.push('<div id="showLoading" align="center" style="padding:10px;display: none;cursor: default;">');
        contentArr.push('   <img src='+listOrder.ctxpath+'"/public/img/loading.gif"><span style="margin-bottom: 10px;">正在操作中......</span>');
        contentArr.push('</div>');
        contentArr.push('<div id="settingContent" style="padding:10px;width: 240px;">');
        contentArr.push('   <div class="pull-left" style="width: 100%">');
        contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" checked="checked" value="selected" id="selected">');
        contentArr.push('       <label class="pull-left" style="margin-left: 5px" for="selected">设置选中</label>');
        contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" value="all" id="all">');
        contentArr.push('       <label class="pull-left" style="margin-left: 5px;" for="all">设置当前页</label>');
        contentArr.push('   </div>');
        contentArr.push('   <div class="pull-left" style="width: 100%;margin-top: 5px;">');
        contentArr.push('       <span style="margin-left: 30px;">运输方式:</span><select id="shipwayCode" style="margin-left:10px;width: 120px;">'+listOrder.allShipway+'</select>');
        contentArr.push('   </div>');
        contentArr.push('</div>');
        var contentHtml = contentArr.join('');
        $.dialog({
                lock: true,
                max: false,
                min: false,
                title: '设置运送方式',
                width: 260,
                height: 60,
                content:contentHtml ,
                button: [{
				name: '确认',
				callback: function() {
                    //显示加载中并隐藏按钮
                    parent.$("#settingContent").hide();
                    parent.$("#showLoading").show();
                    $(this.DOM.buttons[0]).hide();

                    var selected = parent.$("#selected").attr("checked");
                    var all = parent.$("#all").attr("checked");
                    var shipwayCode = parent.$("#shipwayCode").val();
                    var orderIds = "";
                    if(selected){ //更改选中
                        orderIds = listOrder.getSelectedOrders();
                        if(!orderIds){
                            this.close();
                            parent.$.showShortMessage({msg:"请至少选择一项",animate:false});
                            return false;
                        }
                    }else{
                        orderIds = listOrder.getAllOrderIds();
                    }
                    //更改状态，
                    var that = this;
                    $.getJSON(listOrder.ctxpath+"/order.Orders/updateOrderShipway?orderIds="+orderIds+"&shipwayCode="+shipwayCode,function(msg) {
                    	if (msg.status == 1) {
                            parent.refreshLeftMenu();
                            refreshOrderList();
                        }
                    	that.close();
					});
                    return false;
				}
				},
				{
					name: '取消'
		        }]
	    });
};

//提交预报
listOrder.submitRegister = function(){
        var contentArr = [];
        contentArr.push('<div id="showLoading" align="center" style="padding:10px;display: none;cursor: default;">');
        contentArr.push('   <img src='+listOrder.ctxpath+'"/public/img/loading.gif"><span style="margin-bottom: 10px;">正在操作中......</span>');
        contentArr.push('</div>');
        contentArr.push('<div id="changeContent" style="padding:10px;width: 240px;">');
        contentArr.push('   <div class="pull-left" style="width: 100%">');
        contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" checked="checked" value="selected" id="selected">');
        contentArr.push('       <label class="pull-left" style="margin-left: 5px" for="selected">提交选中</label>');
        contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" value="all" id="all">');
        contentArr.push('       <label class="pull-left" style="margin-left: 5px;" for="all">提交当前页</label>');
        contentArr.push('   </div>');
        contentArr.push('</div>');
        var contentHtml = contentArr.join('');
        $.dialog({
                lock: true,
                max: false,
                min: false,
                title: '提交预报',
                width: 260,
                height: 60,
                content:contentHtml ,
                button: [{
				name: '确认',
				callback: function() {
                    //显示加载中并隐藏按钮
                    parent.$("#changeContent").hide();
                    parent.$("#showLoading").show();
                    $(this.DOM.buttons[0]).hide();

                    var selected = parent.$("#selected").attr("checked");
                    var all = parent.$("#all").attr("checked");
                    var orderIds = "";
                    if(selected){ //更改选中
                        orderIds = listOrder.getSelectedOrders();
                        if(!orderIds){
                            this.close();
                            parent.$.showShortMessage({msg:"请至少选择一项",animate:false});
                            return false;
                        }
                    }else{
                          orderIds = listOrder.getAllOrderIds();
                    }
                    //提交预报
                    var that = this;
                    $.getJSON(listOrder.ctxpath+"/order.Orders/submitRegister?orderIds="+orderIds,function(msg) {
                    	if (msg.status == 1) {
                            parent.refreshLeftMenu();
                            refreshOrderList();
                        }
                    	that.close();
					});
                    return false;
				}
				},
				{
					name: '取消'
		        }]
	    })
};

//取消预报
listOrder.cancelRegister = function(){
        var contentArr = [];
        contentArr.push('<div id="showLoading" align="center" style="padding:10px;display: none;cursor: default;">');
        contentArr.push('   <img src='+listOrder.ctxpath+'"/public/img/loading.gif"><span style="margin-bottom: 10px;">正在操作中......</span>');
        contentArr.push('</div>');
        contentArr.push('<div id="changeContent" style="padding:10px;width: 240px;">');
        contentArr.push('   <div class="pull-left" style="width: 100%">');
        contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" checked="checked" value="selected" id="selected">');
        contentArr.push('       <label class="pull-left" style="margin-left: 5px" for="selected">更改选中</label>');
        contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" value="all" id="all">');
        contentArr.push('       <label class="pull-left" style="margin-left: 5px;" for="all">更改当前页</label>');
        contentArr.push('   </div>');
        contentArr.push('</div>');
        var contentHtml = contentArr.join('');
        $.dialog({
                lock: true,
                max: false,
                min: false,
                title: '取消预报',
                width: 260,
                height: 60,
                content:contentHtml ,
                button: [{
				name: '确认',
				callback: function() {
                    //显示加载中并隐藏按钮
                    parent.$("#changeContent").hide();
                    parent.$("#showLoading").show();
                    $(this.DOM.buttons[0]).hide();

                    var selected = parent.$("#selected").attr("checked");
                    var all = parent.$("#all").attr("checked");
                    var orderIds = "";
                    if(selected){ //更改选中
                        orderIds = listOrder.getSelectedOrders();
                        if(!orderIds){
                            this.close();
                            parent.$.showShortMessage({msg:"请至少选择一项",animate:false});
                            return false;
                        }
                    }else{
                          orderIds = listOrder.getAllOrderIds();
                    }
                    //取消预报
                    var that = this;
                    $.getJSON(listOrder.ctxpath+"/order.Orders/cancelRegister?orderIds="+orderIds,function(msg) {
                    	if (msg.status == 1) {
                            parent.refreshLeftMenu();
                            refreshOrderList();
                        }
                    	that.close();
					});
                    return false;
				}
				},
				{
					name: '取消'
		        }]
	    })
};


//标记发货
listOrder.submitShipped = function(){
        var contentArr = [];
        contentArr.push('<div id="showLoading" align="center" style="padding:10px;display: none;cursor: default;">');
        contentArr.push('   <img src='+listOrder.ctxpath+'"/public/img/loading.gif"><span style="margin-bottom: 10px;">正在操作中......</span>');
        contentArr.push('</div>');
        contentArr.push('<div id="changeContent" style="padding:10px;width: 240px;">');
        contentArr.push('   <div class="pull-left" style="width: 100%">');
        contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" checked="checked" value="selected" id="selected">');
        contentArr.push('       <label class="pull-left" style="margin-left: 5px" for="selected">标记选中</label>');
        contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" value="all" id="all">');
        contentArr.push('       <label class="pull-left" style="margin-left: 5px;" for="all">标记当前页</label>');
        contentArr.push('   </div>');
        
        contentArr.push('<div class="pull-left" style="width: 100%;margin-top: 5px;">');
        contentArr.push('   <input class="pull-left" style="margin-left: 30px;" id="isCover" type="checkbox" checked>');
        contentArr.push('   <label class="pull-left" style="margin-left: 5px" for="isCover">覆盖已标记成功的订单</label>');
        contentArr.push('</div>');
        
        contentArr.push('</div>');
        var contentHtml = contentArr.join('');
        $.dialog({
                lock: true,
                max: false,
                min: false,
                title: '标记发货',
                width: 260,
                height: 60,
                content:contentHtml ,
                button: [{
				name: '确认',
				callback: function() {
                    //显示加载中并隐藏按钮
                    parent.$("#changeContent").hide();
                    parent.$("#showLoading").show();
                    $(this.DOM.buttons[0]).hide();

                    var selected = parent.$("#selected").attr("checked");
                    var all = parent.$("#all").attr("checked");
                    var isCover = parent.$("#isCover").attr("checked");
                    if(isCover){
                    	isCover = true;
                    }
                    var orderIds = "";
                    if(selected){ //更改选中
                        orderIds = listOrder.getSelectedOrders();
                        if(!orderIds){
                            this.close();
                            parent.$.showShortMessage({msg:"请至少选择一项",animate:false});
                            return false;
                        }
                    }else{
                          orderIds = listOrder.getAllOrderIds();
                    }
                    var that = this;
                    $.getJSON(listOrder.ctxpath+"/order.Orders/submitShipped?orderIds="+orderIds+"&isCover="+isCover,function(msg) {
                    	if (msg.status == 1) {
                            parent.refreshLeftMenu();
                            refreshOrderList();
                        }
                    	that.close();
					});
                    return false;
				}
				},
				{
					name: '取消'
		        }]
	    })
};


//平邮件转至已经发货
listOrder.toReceived = function(status){
        var contentArr = [];
        contentArr.push('<div id="showLoading" align="center" style="padding:10px;display: none;cursor: default;">');
        contentArr.push('   <img src='+listOrder.ctxpath+'"/public/img/loading.gif"><span style="margin-bottom: 10px;">正在操作中......</span>');
        contentArr.push('</div>');
        contentArr.push('<div id="changeContent" style="padding:10px;width: 240px;">');
        contentArr.push('   <div class="pull-left" style="width: 100%">');
        contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" checked="checked" value="selected" id="selected">');
        contentArr.push('       <label class="pull-left" style="margin-left: 5px" for="selected">移动选中</label>');
        contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" value="all" id="all">');
        contentArr.push('       <label class="pull-left" style="margin-left: 5px;" for="all">移动当前页</label>');
        contentArr.push('   </div>');
        
        contentArr.push('<div class="pull-left" style="width: 100%;margin-top: 5px;">');
        contentArr.push('		<input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" value='+status+' id="status">');
        contentArr.push('   	<label class="pull-left" style="margin-left: 5px" for="isCover">移动当前状态下所有平邮件</label>');
        contentArr.push('</div>');
        
        contentArr.push('</div>');
        var contentHtml = contentArr.join('');
        $.dialog({
                lock: true,
                max: false,
                min: false,
                title: '平邮移至已发货',
                width: 260,
                height: 60,
                content:contentHtml ,
                button: [{
				name: '确认',
				callback: function() {
                    //显示加载中并隐藏按钮
                    parent.$("#changeContent").hide();
                    parent.$("#showLoading").show();
                    $(this.DOM.buttons[0]).hide();
                    var selected = parent.$("#selected").attr("checked");
                    var all = parent.$("#all").attr("checked");
                    var status = parent.$("#status").attr("value");
                    
                    var orderIds = "";
                    if(selected){ //更改选中
                        orderIds = listOrder.getSelectedOrders();
                        if(!orderIds){
                            this.close();
                            parent.$.showShortMessage({msg:"请至少选择一项",animate:false});
                            return false;
                        }
                    } else if(all){
                    	//当前页面
                        orderIds = listOrder.getAllOrderIds();
                    }else {
                    	//当前状态下全部平邮件
                    	orderIds = "";
                    }
                    var that = this;
                    $.getJSON(listOrder.ctxpath+"/order.Orders/toReceived?orderIds="+orderIds+"&status="+status,function(msg) {
                    	if (msg.status == 1) {
                            parent.refreshLeftMenu();
                            refreshOrderList();
                        }
                    	that.close();
					});
                    
                    return false;
                  }
				},
				{
					name: '取消'
		        }]
	    })
};


//订单彻底删除 
listOrder.shiftDelete = function(){
	 var contentArr = [];
     contentArr.push('<div id="showLoading" align="center" style="padding:10px;display: none;cursor: default;">');
     contentArr.push('   <img src='+listOrder.ctxpath+'"/public/img/loading.gif"><span style="margin-bottom: 10px;">正在操作中......</span>');
     contentArr.push('</div>');
     contentArr.push('<div id="changeContent" style="padding:10px;width: 240px;">');
     contentArr.push('   <div class="pull-left" style="width: 100%">');
     contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" checked="checked" value="selected" id="selected">');
     contentArr.push('       <label class="pull-left" style="margin-left: 5px" for="selected">删除选中</label>');
     contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" value="all" id="all">');
     contentArr.push('       <label class="pull-left" style="margin-left: 5px;" for="all">删除当前页</label>');
     contentArr.push('   </div>');
     contentArr.push('</div>');
     var contentHtml = contentArr.join('');
     $.dialog({
             lock: true,
             max: false,
             min: false,
             title: '彻底删除订单',
             width: 260,
             height: 60,
             content:contentHtml ,
             button: [{
	             name: '确认',
				 callback: function() {
	                 //显示加载中并隐藏按钮
	                 parent.$("#changeContent").hide();
	                 parent.$("#showLoading").show();
	                 $(this.DOM.buttons[0]).hide();
	                 var selected = parent.$("#selected").attr("checked");
	                 var all = parent.$("#all").attr("checked");
	                 var orderIds = "";
	                 if(selected){ //更改选中
	                      orderIds = listOrder.getSelectedOrders();
	                      if(!orderIds){
	                          this.close();
	                          parent.$.showShortMessage({msg:"请至少选择一项",animate:false});
	                          return false;
	                      }
	                 }else{
	                      orderIds = listOrder.getAllOrderIds();
	                 }
	                 var that = this;
	                 $.getJSON(listOrder.ctxpath+"/order.Orders/shiftDelete?orderIds="+orderIds, function(msg) {
	  					if (msg == 1) {
	                        parent.refreshLeftMenu();//刷新左侧菜单数量
	  						refreshOrderList();
	  					}
	  				   that.close();
	  				});
	                return false;
				}
			},
			{
				name: '取消'
	        }]
	    })	
};

//订单恢复，（无论订单之前是何种状态，都直接恢复到草稿状态)
listOrder.recoverOrder = function(){
	 var contentArr = [];
     contentArr.push('<div id="showLoading" align="center" style="padding:10px;display: none;cursor: default;">');
     contentArr.push('   <img src='+listOrder.ctxpath+'"/public/img/loading.gif"><span style="margin-bottom: 10px;">正在操作中......</span>');
     contentArr.push('</div>');
     contentArr.push('<div id="changeContent" style="padding:10px;width: 240px;">');
     contentArr.push('   <div class="pull-left" style="width: 100%">');
     contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" checked="checked" value="selected" id="selected">');
     contentArr.push('       <label class="pull-left" style="margin-left: 5px" for="selected">恢复选中</label>');
     contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" value="all" id="all">');
     contentArr.push('       <label class="pull-left" style="margin-left: 5px;" for="all">恢复当前页</label>');
     contentArr.push('   </div>');
     contentArr.push('</div>');
     var contentHtml = contentArr.join('');
     $.dialog({
             lock: true,
             max: false,
             min: false,
             title: '恢复订单',
             width: 260,
             height: 60,
             content:contentHtml ,
             button: [{
	             name: '确认',
				 callback: function() {
	                 //显示加载中并隐藏按钮
	                 parent.$("#changeContent").hide();
	                 parent.$("#showLoading").show();
	                 $(this.DOM.buttons[0]).hide();
	                 var selected = parent.$("#selected").attr("checked");
	                 var all = parent.$("#all").attr("checked");
	                 var orderIds = "";
	                 if(selected){ //更改选中
	                      orderIds = listOrder.getSelectedOrders();
	                      if(!orderIds){
	                          this.close();
	                          parent.$.showShortMessage({msg:"请至少选择一项",animate:false});
	                          return false;
	                      }
	                 }else{
	                      orderIds = listOrder.getAllOrderIds();
	                 }
	                 var that = this;
	                 $.getJSON(listOrder.ctxpath+"/order.Orders/recoverOrder?orderIds="+orderIds, function(msg) {
	  					if (msg == 1) {
	                        parent.refreshLeftMenu();
	  						refreshOrderList();
	                    }
	  					that.close();
	  				});
	                 return false;
				}
			},
			{
				name: '取消'
	        }]
	    })	
};

/**
 * 客户申请拦截订单 (仅已经收货状态有此操作)
 */
listOrder.holdUp = function(){
    var contentArr = [];
    contentArr.push('<div id="showLoading" align="center" style="padding:10px;display: none;cursor: default;">');
    contentArr.push('   <img src='+listOrder.ctxpath+'"/public/img/loading.gif"><span style="margin-bottom: 10px;">正在操作中......</span>');
    contentArr.push('</div>');
    contentArr.push('<div id="changeContent" style="padding:10px;width: 240px;">');
    contentArr.push('   <div class="pull-left" style="width: 100%">');
    contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" checked="checked" value="selected" id="selected">');
    contentArr.push('       <label class="pull-left" style="margin-left: 5px" for="selected">拦截选中</label>');
    contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" value="all" id="all">');
    contentArr.push('       <label class="pull-left" style="margin-left: 5px;" for="all">拦截当前页</label>');
    contentArr.push('   </div>');
    contentArr.push('</div>');
    var contentHtml = contentArr.join('');
    $.dialog({
            lock: true,
            max: false,
            min: false,
            title: '拦截订单',
            width: 260,
            height: 60,
            content:contentHtml ,
            button: [{
				name: '确认',
				callback: function() {
	                //显示加载中并隐藏按钮
	                parent.$("#changeContent").hide();
	                parent.$("#showLoading").show();
	                $(this.DOM.buttons[0]).hide();
	
	                var selected = parent.$("#selected").attr("checked");
	                var all = parent.$("#all").attr("checked");
	                var orderIds = "";
	                if(selected){ //更改选中
	                    orderIds = listOrder.getSelectedOrders();
	                    if(!orderIds){
	                        this.close();
	                        parent.$.showShortMessage({msg:"请至少选择一项",animate:false});
	                        return false;
	                    }
	                }else{
	                      orderIds = listOrder.getAllOrderIds();
	                }
	                var that = this;
				    $.getJSON(listOrder.ctxpath+"/order.Orders/holdUp?orderIds="+orderIds,function(msg){
				        if(msg.status == 1){
				            refreshOrderList();
				        }
				        that.close();
				    });
	                return false;
				}
			},
			{
				name: '取消'
	        }]
    })
};

/**
 * 客户取消拦截订单 (仅已经收货状态有此操作)
 */
listOrder.cancelHoldUp = function(){
    var contentArr = [];
    contentArr.push('<div id="showLoading" align="center" style="padding:10px;display: none;cursor: default;">');
    contentArr.push('   <img src='+listOrder.ctxpath+'"/public/img/loading.gif"><span style="margin-bottom: 10px;">正在操作中......</span>');
    contentArr.push('</div>');
    contentArr.push('<div id="changeContent" style="padding:10px;width: 240px;">');
    contentArr.push('   <div class="pull-left" style="width: 100%">');
    contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" checked="checked" value="selected" id="selected">');
    contentArr.push('       <label class="pull-left" style="margin-left: 5px" for="selected">取消选中</label>');
    contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" value="all" id="all">');
    contentArr.push('       <label class="pull-left" style="margin-left: 5px;" for="all">取消当前页</label>');
    contentArr.push('   </div>');
    contentArr.push('</div>');
    var contentHtml = contentArr.join('');
    $.dialog({
            lock: true,
            max: false,
            min: false,
            title: '取消拦截订单',
            width: 260,
            height: 60,
            content:contentHtml ,
            button: [{
				name: '确认',
				callback: function() {
	                //显示加载中并隐藏按钮
	                parent.$("#changeContent").hide();
	                parent.$("#showLoading").show();
	                $(this.DOM.buttons[0]).hide();
	
	                var selected = parent.$("#selected").attr("checked");
	                var all = parent.$("#all").attr("checked");
	                var orderIds = "";
	                if(selected){ //更改选中
	                    orderIds = listOrder.getSelectedOrders();
	                    if(!orderIds){
	                        this.close();
	                        parent.$.showShortMessage({msg:"请至少选择一项",animate:false});
	                        return false;
	                    }
	                }else{
	                      orderIds = listOrder.getAllOrderIds();
	                }
	                var that = this;
				    $.getJSON(listOrder.ctxpath+"/order.Orders/cancelHoldUp?orderIds="+orderIds,function(msg){
				        if(msg.status == 1){
				            refreshOrderList();
				        }
				        that.close();
				    });
	                return false;
				}
			},
			{
				name: '取消'
	        }]
    })
};

/**
 * 复制订单
 */
listOrder.copyOrder = function(){
    var contentArr = [];
    contentArr.push('<div id="showLoading" align="center" style="padding:10px;display: none;cursor: default;">');
    contentArr.push('   <img src='+listOrder.ctxpath+'"/public/img/loading.gif"><span style="margin-bottom: 10px;">正在操作中......</span>');
    contentArr.push('</div>');
    contentArr.push('<div id="changeContent" style="padding:10px;width: 240px;">');
    contentArr.push('   <div class="pull-left" style="width: 100%">');
    contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" checked="checked" value="selected" id="selected">');
    contentArr.push('       <label class="pull-left" style="margin-left: 5px" for="selected">复制选中</label>');
    contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" value="all" id="all">');
    contentArr.push('       <label class="pull-left" style="margin-left: 5px;" for="all">复制当前页</label>');
    contentArr.push('   </div>');
    contentArr.push('</div>');
    var contentHtml = contentArr.join('');
    $.dialog({
            lock: true,
            max: false,
            min: false,
            title: '复制订单',
            width: 260,
            height: 60,
            content:contentHtml ,
            button: [{
				name: '确认',
				callback: function() {
	                //显示加载中并隐藏按钮
	                parent.$("#changeContent").hide();
	                parent.$("#showLoading").show();
	                $(this.DOM.buttons[0]).hide();
	
	                var selected = parent.$("#selected").attr("checked");
	                var all = parent.$("#all").attr("checked");
	                var orderIds = "";
	                if(selected){ //更改选中
	                    orderIds = listOrder.getSelectedOrders();
	                    if(!orderIds){
	                        this.close();
	                        parent.$.showShortMessage({msg:"请至少选择一项",animate:false});
	                        return false;
	                    }
	                }else{
	                      orderIds = listOrder.getAllOrderIds();
	                }
	                //更改状态，
	                var that = this;
	                //复制
				    $.getJSON(listOrder.ctxpath+"/order.Orders/copyOrder?orderIds="+orderIds,function(msg){
				        if(msg.status == 1){
				            parent.refreshLeftMenu();//刷新左侧菜单数量
				            refreshOrderList();
				        }
				        that.close();
				    });
	                return false;
				}
			},
			{
				name: '取消'
	        }]
    })
};

//打印地址标签  isShowChangeStatusToPrinted 是否展示 转移订单至已经打印( 只有提交预报状态的订单,展示 是否转移至 已经打印)
listOrder.printOrderLabel = function(isShowChangeStatusToPrinted){
         var contentArr = [];
          contentArr.push('<div id="showLoading" align="center" style="padding:100px;display: none;cursor: default;">');
          contentArr.push('   <img src='+listOrder.ctxpath+'"/public/img/loading.gif"><span style="margin-bottom: 10px;">正在操作中......</span>');
          contentArr.push('</div>');

          contentArr.push('<div id="changeContent" style="font-family:tahoma;width: 95%;margin-top: 15px;padding-left:15px;overflow: hidden;">');
          
          contentArr.push('<div class="pull-left" style="width: 100%;text-align:center;line-height:20px;margin-top: 5px;">');
          contentArr.push('   <span class="pull-left">打印范围：</span>');
          contentArr.push('   <input class="pull-left" name="chooseOption" style="margin-left:30px;vertical-align: middle;" type="radio" checked="checked" value="selected" id="selected">');
          contentArr.push('   <label class="pull-left" style="margin-left: 5px;line-height:20px;vertical-align: middle;" for="selected">打印选中</label>');
          contentArr.push('   <input class="pull-left" name="chooseOption" style="margin-left:56px;vertical-align: middle;" type="radio" value="all" id="all">');
          contentArr.push('   <label class="pull-left" style="margin-left: 5px;line-height:20px;vertical-align: middle;" for="all">打印当前页</label>');
          contentArr.push('</div>');
          
          contentArr.push('<div id="printOption" class="pull-left" style="width: 100%;text-align:center;line-height:20px;margin-top: 5px;">');
          contentArr.push('   <span class="pull-left">打印选项：</span>');
          contentArr.push('   <input class="pull-left" style="margin-left:30px;vertical-align: middle;" id="printProductInfo" type="checkbox" checked>');
          contentArr.push('   <label class="pull-left" style="margin-left: 5px;line-height:20px;vertical-align: middle;" for="printProductInfo">打印配货信息</label>');
          contentArr.push('   <input class="pull-left" style="margin-left:30px;vertical-align: middle;" id="printBuyerId" type="checkbox" checked>');
          contentArr.push('   <label class="pull-left" style="margin-left: 5px;line-height:20px;vertical-align: middle;" for="printBuyerId">打印买家帐号</label>');
          contentArr.push('</div>');

          contentArr.push('<div class="pull-left" style="margin-top:5px;text-align:center;line-height:20px;width: 100%">');
          contentArr.push('   <span class="pull-left">打印内容：</span>');
          contentArr.push('   <input class="pull-left" style="margin-left:30px;vertical-align: middle;" id="printLabel" type="checkbox" checked>');
          contentArr.push('   <label class="pull-left" style="margin-left: 5px;line-height:20px;vertical-align: middle;" for="printLabel">打印地址标签</label>');
          contentArr.push('   <input class="pull-left" style="margin-left:30px;vertical-align: middle;" id="printCustoms" type="checkbox" checked>');
          contentArr.push('   <label class="pull-left" style="margin-left: 5px;line-height:20px;vertical-align: middle;" for="printCustoms">打印报关单</label>');
          contentArr.push('</div>');

          contentArr.push('<div class="pull-left" style="width: 100%;margin-top: 5px;">');
          contentArr.push('   <span class="pull-left" >打印纸张：</span>');
          contentArr.push('   <select class="pull-left" id="paperType" onchange="document.getElementById(\''+listOrder.iframeId+'\').contentWindow.listOrder.choosePaperType();" style="width: 100px;margin-left: 30px;">');
          contentArr.push('       <option value="1">标签纸</option>');
          contentArr.push('       <option value="2">A4纸</option>');
          contentArr.push('   </select>');
          contentArr.push('   <select class="pull-left" id="paperSize_label" style="width: 125px;margin-left: 1;">');
          contentArr.push('       <option value="100*100">100mm*100mm</option>');
          contentArr.push('       <option value="101*76">101mm*76mm</option>');
          contentArr.push('   </select>');
          contentArr.push('   <select class="pull-left" id="paperSize_a4" style="width: 125px;margin-left: 1;display:none;">');
          contentArr.push('       <option value="A4*3">A4每页3行</option>');
          contentArr.push('       <option value="A4*4">A4每页4行</option>');
          contentArr.push('   </select>');
          contentArr.push('</div>');
      	  if(isShowChangeStatusToPrinted){
      	    contentArr.push('<div class="pull-left" style="width: 100%;margin-top: 5px;text-align: center;line-height: 20px;">');
            contentArr.push('   <input class="pull-left" style="vertical-align: middle;margin-left:55px;" id="isChangeStatusToPrinted" type="checkbox" checked>');
            contentArr.push('   <label class="pull-left" style="margin:0 0 0 5px;line-height:20px;vertical-align: middle;display: inline-block;" for="isChangeStatusToPrinted">订单移至已经打印</label>');
            contentArr.push('</div>');
      	  }
          
          contentArr.push('</div>');
          var contentHtml = contentArr.join('');
          $.dialog({
                lock: true,
                max: false,
                min: false,
                title: '打印地址标签',
                width: 380,
                height: 230,
                content:contentHtml ,
                button: [{
                name: '打印',
                callback: function() {
                      var selected = parent.$("#selected").attr("checked");
                      var all = parent.$("#all").attr("checked");
                      var orderIds = "";
                      if(selected){ //更改选中
                          orderIds = listOrder.getSelectedOrders();
                          if(!orderIds){
                              this.close();
                              parent.$.showShortMessage({msg:"请至少选择一项",animate:false});
                              return false;
                          }
                      }else{
                            orderIds = listOrder.getAllOrderIds();
                      }
                      //是否打印配货信息
                      var isPrintProductInfo = parent.$("#printProductInfo").attr("checked")=="checked"?true:false;
                      //是否打印买家id
                      var isPrintBuyerId = parent.$("#printBuyerId").attr("checked")=="checked"?true:false;
                      //打印地址标签
                      var isPrintLabel = parent.$("#printLabel").attr("checked")=="checked"?true:false;
                      //打印报关单
                      var isPrintCustoms = parent.$("#printCustoms").attr("checked")=="checked"?true:false;
                      
                      //打印纸张
                      var paperType = parent.$("#paperType").val();
                      var paperSize = "";
                      if(paperType ==1){
                    	  paperSize = parent.$("#paperSize_label").val();
                      }
                      if(paperType ==2){
                    	  paperSize = parent.$("#paperSize_a4").val();
                      }
                      //用户打印地址标签和打印报关单必需选择一项
                      if(!isPrintLabel && !isPrintCustoms){
                          parent.$.showShortMessage({msg:"打印内容必需选择一项",left:"43%",top:"23%",animate:false});
                          return false;
                      }
                      var url = listOrder.ctxpath+"/order.Print/printOrderLabel?orderIds="+orderIds+"&paperSize="+paperSize+"&paperType="+paperType
                        +"&isPrintCustoms="+isPrintCustoms+"&isPrintLabel="+isPrintLabel+"&isPrintProductInfo="+isPrintProductInfo
                        +"&isPrintBuyerId="+isPrintBuyerId;
		  			  window.open(url);
		  			  
		  			  if(isShowChangeStatusToPrinted){
	                    	 //是否改变状态到已经打印
	                    	 var isChangeStatusToPrinted = parent.$("#isChangeStatusToPrinted").attr("checked")=="checked"?true:false;
	                         if(isChangeStatusToPrinted){
	                        	 $.getJSON(listOrder.ctxpath+"/order.Orders/updateOrderStatus?orderIds="+orderIds+"&status="+3,function(msg) {
	                              	if (msg.status == 1) {
	                                      parent.refreshLeftMenu();
	                                      refreshOrderList();
	                                  }
	          					});
	                         }
	                   }
		  			  
                }
            },
            {
                name: '取消'
            }]
  	    })
};

//选择打印纸张类型，如果为标签纸，则有标签纸大小选择下拉；如果为A4纸，则隐藏
listOrder.choosePaperType = function(){
	var paperType =parent.$("#paperType").val();
	//根据是a4还是label 选择显示和隐藏 paperSize
	if(paperType == 1){
		parent.$("#paperSize_a4").hide();
		parent.$("#paperSize_label").show();
	}
	if(paperType == 2){
		parent.$("#paperSize_label").hide();
		parent.$("#paperSize_a4").show();
	}
};

//打印拣货单
listOrder.printPickingList = function(){
    var contentArr = [];
          contentArr.push('<div id="showLoading" align="center" style="padding:10px;display: none;cursor: default;">');
          contentArr.push('   <img src='+listOrder.ctxpath+'"/public/img/loading.gif"><span style="margin-bottom: 10px;">正在操作中......</span>');
          contentArr.push('</div>');
          contentArr.push('<div id="changeContent" style="padding:10px;width: 240px;">');
          contentArr.push('   <div class="pull-left" style="width: 100%">');
          contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" checked="checked" value="selected" id="selected">');
          contentArr.push('       <label class="pull-left" style="margin-left: 5px" for="selected">打印选中</label>');
          contentArr.push('       <input class="pull-left" name="chooseOption" style="margin-left: 30px;" type="radio" value="all" id="all">');
          contentArr.push('       <label class="pull-left" style="margin-left: 5px;" for="all">打印当前页</label>');
          contentArr.push('   </div>');
          contentArr.push('</div>');
          contentArr.push('<div style="color: #ff0000;margin-left: 40px;">注：请使用A4纸打印</div>');
          var contentHtml = contentArr.join('');
          $.dialog({
              lock: true,
              max: false,
              min: false,
              title: '打印拣货单',
              width: 260,
              height: 60,
              content:contentHtml ,
              button: [{
                  name: '确认',
                  callback: function() {
                      //打印范围
                      var selected = parent.$("#selected").attr("checked");
                      var all = parent.$("#all").attr("checked");
                      var orderIds = "";
                      if(selected){ //更改选中
                          orderIds = listOrder.getSelectedOrders();
                          if(!orderIds){
                              this.close();
                              parent.$.showShortMessage({msg:"请至少选择一项",animate:false});
                              return false;
                          }
                      }else{
                            orderIds = listOrder.getAllOrderIds();
                      }
                      
                    window.open(listOrder.ctxpath+"/order.Print/printPickingList?orderIds="+orderIds);
                  }
              },
              {
                name: '取消'
              }]
  	    })
};

//国家焦点失去事件
listOrder.countryBlur = function(thisDom){
	//国家必须通过联想输入, 失去焦点后 把输入框的值还原为 输入之前的, 等联想输入再次赋值
	 $(thisDom).hide();
     $(thisDom).prev().show();
};

//给可以修改字段绑定双击事件
listOrder.editField = function(that){
    //如果为下拉框时，点开自动选择中与之对应的值domType为1表示为输入框，2表示下拉框
    var thisText = $(that).text();
    var $nextDom = $(that).next();
    var domType = $nextDom.attr("domType");
    if(domType == 2){
        $.each($nextDom.find("option"),function(i,e){
            if($(e).text() == thisText){
                $(e).attr("selected",true);
                return false;
            }
        });
    }
    $(that).hide();
    $(that).next().val(thisText).show();
    $(that).next().focus();
};
//保存订单修改字段
listOrder.saveField = function(that){
    var domType = $(that).attr("domType"); //domType为1表示为输入框，2表示下拉框
    var orderId = $(that).attr("orderId");
    var fieldName = $(that).attr("field");
    var thisValue = $.trim($(that).val());
    var isRequired = $(that).attr("required"); //required为1表示必填字段
    if(isRequired && thisValue == ""){
        var txt = $(that).attr("title");
        parent.$.showShortMessage({msg:txt,animate:false});
        return ;
    }
    var thisText =  domType == 2? $.trim($(that).find(":selected").text()): $.trim($(that).val());
    var prevValue = $.trim($(that).prev().text());
    if(fieldName != "country" && thisText == prevValue){ //无变动，则不发送请求保存
        $(that).hide();
        $(that).prev().show();
    }else{
        if(fieldName == "country"){
            thisValue = $(that).next().val();
        }
        listOrder.saveOrderExecute(that,thisText,orderId,fieldName,thisValue);
        if(fieldName == "shipwayCode"){
        	parent.refreshLeftMenu();
        	refreshOrderList();
        }
    }
};

/**
 * 发送请求保存订单数据
 * @param that  当前dom对象
 * @param thisText  该字段显示的时候的text值
 * @param orderId  订单ID
 * @param fieldName  字段名称
 * @param fieldValue  字段用户输入的新值
 */
listOrder.saveOrderExecute = function(that,thisText,orderId,fieldName,fieldValue){
        $.ajax({
            type:'get',
            async:true,
            url:listOrder.ctxpath+'/order.Orders/saveOrderFields?orderId='+orderId+"&fieldName="+fieldName+'&fieldValue='+fieldValue,
            success:function(result){
                if(result == 1){
                    if(fieldName == "country"){
                        return ;
                    }
                    $(that).hide();
                    $(that).prev().text(thisText).attr("title",fieldValue).show();
                }else{
                    //保存失败，显示回原先值
                    $(that).val(thisText);
                    $(that).hide();
                    $(that).prev().show();
                    parent.$.showShortMessage({msg:result,animate:false});
                }
            }
        });
};

//****************************************************报关信息编辑、删除、保存操作***************************************************
//编辑报关信息
listOrder.editCustomsField = function(thisDom){
    var thisText = $(thisDom).text();
    $(thisDom).hide();
    $(thisDom).next().val(thisText).show().focus();
};
//保存这些编辑字段
listOrder.saveCustomsField = function(thisDom){
	if(!$(thisDom).is(":visible")){
        return ;
    }
    //验证通过操作
    var thisText =$(thisDom).prev().text().trim();
    var thisValue = $(thisDom).val();
    var thisField = $(thisDom).attr("field");
    var customsId = $(thisDom).attr("customsId")?$(thisDom).attr("customsId"):0;
    var orderId = $(thisDom).parents(".order-customs").find(".orderId").val();
    //如果该字段由之前空->空，则不保存到后台，若果是之前有值->空，则更新数据库该值为null
    if(thisText == "" && thisValue == ""){
        $(thisDom).hide();
        $(thisDom).prev().show();
        return ;
    }
    //报关信息验证
    var flag = listOrder.customsInfoValid(thisField,thisValue);
    if(!flag){
        return ;
    }
    $.post(listOrder.ctxpath+'/order.OrderCustomss/saveOrderCustoms',{orderId:orderId,customsId:customsId,fieldName:thisField,fieldValue:thisValue},function(result){
        if(result.status == 1){ //保存成功
            var customsId = result.customsId;
            var $parentDom = $(thisDom).parents(".per-customs");
            $(thisDom).hide();
            $(thisDom).prev().text(thisValue).attr("title",thisValue).show();
            $parentDom.find("input").attr("customsId",customsId);
            var index = $parentDom.attr("index");
            $(thisDom).parents(".order-customs").find(".customs-edit-btn .edit-btn-del .btn-del[index='"+index+"']").attr("customsId",customsId);
        }else{ //出现异常保存失败
            $(thisDom).val('').hide();
            $(thisDom).prev().show();
            parent.$.showShortMessage({msg:result.errorMessage,animate:false});
        }
    });
};

//报关信息验证
listOrder.customsInfoValid = function(thisField,thisValue){
    var flag = true;
    //验证报关数量和报关单价
    if(thisField == "customsQuantity"){//报关数量，必需为正整数
        flag = isInteger(thisValue);
    }
    if(!flag){
        parent.$.showShortMessage({msg:"申报数量必需为正整数",animate:false});
        return false;
    }
    if(thisField == "customsValue"){
        flag = !isNaN(thisValue);
    }
    if(!flag){
        parent.$.showShortMessage({msg:"申报单价必需为数字",animate:false});
        return false;
    }
    return true;
};

//删除报关信息，删除时，点击保存后才更新数据库
listOrder.delCustoms = function(thisDom){
    var index = $(thisDom).attr("index");
    var orderCustomsId = $(thisDom).attr("customsId");
    if(!orderCustomsId){ //不存在，直接删除
        $(thisDom).parents(".order-customs").find(".order-customs-view .per-customs[index="+index+"]").remove();
         //同时删除按钮
        $(thisDom).parent().remove();
    }else{
        $.getJSON(listOrder.ctxpath+"/order.OrderCustomss/deleteCustoms?orderCustomsId="+orderCustomsId,function(result){
            if(result.status == 1){//删除成功
                $(thisDom).parents(".order-customs").find(".order-customs-view .per-customs[index="+index+"]").remove();
                //同时删除按钮
                $(thisDom).parent().remove();
            }else{
                parent.$.showShortMessage({msg:result.errorMessage,animate:false});
            }
        });
    }
};

//添加报关信息
var customsIndex = 10000; //添加报关信息时，用到的
listOrder.addCustoms = function(thisDom){
    var $orderCustoms = $(thisDom).parents(".order-customs");
    var addHtmlArr = [];
    addHtmlArr.push('<div class="pull-left widAll per-customs" index="'+customsIndex+'">');
    addHtmlArr.push('   <span class="pull-left">产品SKU:</span>');
    addHtmlArr.push('   <span class="pull-left view-span customs-field-span wid62 overflow-ellipsis sku-view" onclick="listOrder.editCustomsField(this);"></span>');
    addHtmlArr.push('   <input type="text" field="sku" data-provide="typeahead" class="pull-left customs-field hide wid60 sku" onblur="listOrder.saveCustomsField(this);" />');
    addHtmlArr.push('   <span class="pull-left ml5">申报品名:</span>');
    addHtmlArr.push('   <span class="pull-left view-span customs-field-span wid62 overflow-ellipsis customsDescription-view" onclick="listOrder.editCustomsField(this);" ></span>');
    addHtmlArr.push('   <input type="text" field="customsDescription" class="pull-left customs-field hide wid60 customsDescription" onblur="listOrder.saveCustomsField(this);" />');
    addHtmlArr.push('   <span class="pull-left ml5">申报数量:</span>');
    addHtmlArr.push('   <span class="pull-left view-span customs-field-span wid42 overflow-ellipsis customsQuantity-view" onclick="listOrder.editCustomsField(this);"></span>');
    addHtmlArr.push('   <input type="text" field="customsQuantity" class="pull-left customs-field hide wid40 customsQuantity" onblur="listOrder.saveCustomsField(this);" title="必需为正整数" />');
    addHtmlArr.push('   <span class="pull-left ml5">申报单价:</span>');
    addHtmlArr.push('   <span class="pull-left view-span customs-field-span wid42 overflow-ellipsis customsValue-view" onclick="listOrder.editCustomsField(this);"></span>');
    addHtmlArr.push('   <input type="text" field="customsValue" class="pull-left customs-field hide wid40 customsValue" onblur="listOrder.saveCustomsField(this);" title="必需为数字" />');
    addHtmlArr.push('   <span class="pull-left itemPrice">USD</span>');
    addHtmlArr.push('   <span class="pull-left ml5">申报重量:</span>');
    addHtmlArr.push('   <span class="pull-left view-span customs-field-span wid42 overflow-ellipsis customsWeight-view" onclick="listOrder.editCustomsField(this);"></span>');
    addHtmlArr.push('   <input type="text" field="customsWeight" class="pull-left customs-field hide wid40 customsWeight" onblur="listOrder.saveCustomsField(this);" />');
    addHtmlArr.push('  <span class="pull-left itemPrice">KG</span>');
    addHtmlArr.push('   <span class="pull-left ml5">海关编号:</span>');
    addHtmlArr.push('   <span class="pull-left view-span customs-field-span wid42 overflow-ellipsis hsCode-view" onclick="listOrder.editCustomsField(this);"></span>');
    addHtmlArr.push('   <input type="text" field="hsCode" class="pull-left customs-field hide wid40 hsCode" onblur="listOrder.saveCustomsField(this);" />');
    
    addHtmlArr.push('  <span class="pull-left ml5">配货信息:</span>');
    addHtmlArr.push('  <span class="pull-left view-span customs-field-span wid82 overflow-ellipsis productName-view" onclick="listOrder.editCustomsField(this);"></span>');
    addHtmlArr.push('  <input type="text" field="productName" class="pull-left customs-field hide wid80 productName" onblur="listOrder.saveCustomsField(this);" />');
    
    addHtmlArr.push('</div>');

    $orderCustoms.find(".order-customs-view").append(addHtmlArr.join(''));

    //绑定联想输入事件
    $orderCustoms.find(".sku").typeahead(skuAutoComplete);

    //对应的删除按钮
    var delBtnHtml = '<div class="pull-left customs-btn-del"><a class="btn btn-small btn-danger btn-del" index="'+customsIndex+'" onclick="listOrder.delCustoms(this);" title="删除">-</a></div>';
    $orderCustoms.find(".customs-edit-btn .edit-btn-del").append(delBtnHtml);
    customsIndex++;
};

//报关信息在sku自动完成时，保存该条报关信息
listOrder.getAndsaveCustomsForAutoComplete = function(thisDom,orderId,customsId,sku){
    $.ajax({
        url:listOrder.ctxpath+'/order.OrderCustomss/getAndsaveCustomsForAutoComplete?orderId='+orderId+'&customsId='+customsId+'&sku='+sku,
        async:false,
        success:function(result){
            if(result.status == 1){
                var $parentDom = $(thisDom).parent();
                $parentDom.find("input").attr("customsId",result.customsId);
                $parentDom.find(".customsDescription-view").text(result.customsDescription).attr("title",result.customsDescription);
                $parentDom.find(".customsValue-view").text(result.customsValue).attr("title",result.customsValue);
                $parentDom.find(".customsWeight-view").text(result.customsWeight).attr("title",result.customsWeight);
                $parentDom.find(".productName-view").text(result.productName).attr("title",result.productName);
            }else{
                parent.$.showShortMessage({msg:result.errorMessage,animate:false});
            }
        }
    });
};
//****************************分页***begin*********************************************
//翻页鼠标悬停，离开事件
$("#pagination .l-bar-btnfirst,#pagination .l-bar-btnprev,#pagination .l-bar-btnnext,#pagination .l-bar-btnlast").hover(function(){
    //如果翻不了页，就不触发该事件
    var isDisabled = $(this).children().is(".l-disabled");
    if(!isDisabled){
         $(this).addClass("l-bar-button-over");
    }
},function(){
     //如果翻不了页，就不触发该事件
    var isDisabled = $(this).children().is(".l-disabled");
    if(!isDisabled){
          $(this).removeClass("l-bar-button-over");
    }
});

//刷新订单
$("#pagination .l-bar-btnload").bind("mouseover",function(){
    $(this).addClass("l-bar-button-over");
}).bind("mouseout",function(){
    $(this).removeClass("l-bar-button-over")
}).bind("click",function(){
    refreshOrderList();
});

//跳转到首页
$("#pagination .l-bar-btnfirst").click(function(){
    if(listOrder.curPage == 1){
        return;
    }
    $(this).removeClass("l-bar-button-over");
    listOrder.curPage = 1;
    $("#pagination .curPage").val(listOrder.curPage);
    refreshOrderList();
});
//跳转到上一页
$("#pagination .l-bar-btnprev").click(function(){
    if(listOrder.curPage == 1){
        return;
    }
    if(listOrder.curPage == 2){
        $(this).removeClass("l-bar-button-over");
    }
    listOrder.curPage--;
    $("#pagination .curPage").val(listOrder.curPage);
    refreshOrderList();
});
//跳转到下一页
$("#pagination .l-bar-btnnext").click(function(){
    var pageTotal = $("#pagination .pageTotal").text();
    if(listOrder.curPage == pageTotal){
        return;
    }
    if(listOrder.curPage == pageTotal-1){
        $(this).removeClass("l-bar-button-over");
    }
    listOrder.curPage++;
    $("#pagination .curPage").val(listOrder.curPage);
    refreshOrderList();
});
//跳转到尾页
$("#pagination .l-bar-btnlast").click(function(){
    var pageTotal = $("#pagination .pageTotal").text();
    if(listOrder.curPage == pageTotal){
        return;
    }
    $(this).removeClass("l-bar-button-over");
    listOrder.curPage = pageTotal;
    $("#pagination .curPage").val(listOrder.curPage);
    refreshOrderList();
});
//跳转到指定页
$("#pagination .curPage").bind("blur",function(){
    var curValue = $.trim($(this).val());
    if(!isInteger(curValue)){
        parent.$.showShortMessage({msg:"页数输入有误",animate:false});
        return ;
    }
    var pageTotal = $("#pagination .pageTotal").text();
    if(curValue < 1 || curValue > pageTotal){
        return;
    }
    listOrder.curPage = curValue;
    $("#pagination .curPage").val(curValue);
    refreshOrderList();
});
//选择页面显示订单数量点击事件
$("#pagination .orderListSize").change(function(){
    listOrder.pageSize = $(this).val();
     $("#pagination .perPageSize").text(listOrder.pageSize);
    refreshOrderList();
});
//****************************分页***end*********************************************
//是否是正整数
var isInteger = function(num){
    return /^[1-9]+[0-9]*$/.test(num);
};
//邮箱格式是否正确
var isEmail = function(email){
    return /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(email);
};

//取精度  num需要取精度的数字  n精度（小数点后几位）
var getPrecision = function(num,n){
    return Math.round(num*Math.pow(10,n))/Math.pow(10,n);
};


//开关退货服务
function isCanCancelClick(orderId){
	 $.ajax({
	        url:listOrder.ctxpath+'/order.Orders/isCanCancel?orderId='+orderId,
	        async:false,
	        success:function(result){
	            if(result .status == 1){
	            	 //取消退货服务
	            	$("#img_return_"+orderId).hide();
	            	$("#img_return_gray_"+orderId).show();
	            } if(result .status == 2){
	            	 //开通退货服务
	            	$("#img_return_"+orderId).show();
	            	$("#img_return_gray_"+orderId).hide();
	            }
	            parent.$.showShortMessage({msg:result.message,animate:false});
	        }
	    });
}
//保险服务
function inSureClick(orderId,shipwayCode){
	//先判断渠道是否允许投保
	if(shipwayCode == null||shipwayCode ==""){
		parent.$.showShortMessage({msg:"请先设置订单渠道",animate:false});
    	return false;
	}
	var status = 0;
    $.ajax({
        type:'post',
        url:listOrder.ctxpath+'/order.Orders/getShipwayInSureByCode',
        async:false,
        data:{shipwayCode:shipwayCode},
        success:function(map){
        	if(map.status == '1'){
        		var inSure = map.inSure;
            	if(inSure == 'Y'){
            		staus = 1;
            	}else{
            		status = 3;
            	}
        	}else{
        		status = 2;
        	}
        }
    });
    if(status == 2){
    	parent.$.showShortMessage({msg:"获取渠道信息失败",animate:false});
    	return false;
    }
    if(status == 3){
    	parent.$.showShortMessage({msg:"渠道不支持保险服务",animate:false});
    	return false;
    }
    //弹出窗 编辑保险
	$.dialog({
 		lock : false,
 		title : '保险服务',
 		width : '210px',
 		height : '100px',
 		zIndex:2999,
 		content : 'url:' + listOrder.ctxpath+"/order.Orders/inSure?orderId="+orderId,
 		button : [{
 			name : '确定',
 			callback : function() {
                var isInSure =  this.content.$("#isInSure").attr("checked"); 
                var inSure = this.content.$("#inSure").val();
                $.ajax({
        	        url:listOrder.ctxpath+'/order.Orders/saveInSure?orderId='+orderId+'&isInSure='+isInSure+'&inSure='+inSure,
        	        async:false,
        	        success:function(result){
        	        	if(result.status=="0"){
        	        		parent.$.showShortMessage({msg:result.message,animate:false});
        	        	}else if(result.status=="1"){
        	        		 //开通保险服务
        	            	$("#inSure_gray_"+orderId).hide();
        	            	$("#inSure_"+orderId).show();
        	        	}else if(result.status=="2"){
        	        		//取消保险服务
        	        		$("#inSure_"+orderId).hide();
        	            	$("#inSure_gray_"+orderId).show();
        	        	}
        	        }
        	    });
 			}
 		}],
 		cancel : true
 	});
}
