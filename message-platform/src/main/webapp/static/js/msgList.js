$(function() {
	queryDataList(true);
});

function queryDataList(ifOnload) {
	var queryParamsJson = "";
	if (ifOnload) {
		queryParamsJson = JSON.stringify(new QueryParams("", "", "", "", "", ""));
	} else {
		queryParamsJson = getParams();
	}
	$("#msgTable").datagrid({
		url : '/message/queryForPage.do',
		fitColumns : true,
		collapsible : false,// 是否可折叠的
		fit : true,// 自动大小
		nowrap : false,
		rownumbers : false,
		showFooter : true,
		pagination : true,
		nowrap : true,
		checkOnSelect : true,
		queryParams : {
			"queryParamsJson" : queryParamsJson
		},
		singleSelect : false,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		columns : [ [ {
			field : 'id',
			checkbox : true
		}, {
			field : 'index',
			title : '序号',
			width : 40,
			align : 'center',
			formatter : function(val, row, index) {
				var options = $("#msgTable").datagrid('getPager').data("pagination").options;
				var currentPage = options.pageNumber;
				var pageSize = options.pageSize;
				return (pageSize * (currentPage - 1)) + (index + 1);
			}
		}, {
			field : 'requestId',
			title : '唯一编号',
			width : 80,
			align : 'center'
		}, {
			field : 'keyword1',
			title : '第一关键字',
			width : 120,
			align : 'center'
		}, {
			field : 'keyword2',
			title : '第二关键字',
			width : 120,
			align : 'center'
		}, {
			field : 'keyword3',
			title : '第三关键字',
			width : 120,
			align : 'center'
		}, {
			field : 'createdTime',
			title : '创建时间',
			width : 150,
			align : 'center'
		}, {
			field : 'status',
			title : '状态',
			width : 120,
			align : 'center'
		}, {
			field : 'count',
			title : '累计发送次数',
			width : 120,
			align : 'center'
		}, {
			field : 'isValid',
			title : '是否有效',
			width : 120,
			align : 'center'
		}, {
			field : 'edit',
			title : '操作',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
				var id = row.id;
				return '<a href="javascript:getReqMsg(' + id + ');">请求信息</a><a style="margin-left:10px;" href="javascript:getNewResMsg(' + id + ');">最新一次响应信息</a>';
			}
		} ] ],
		toolbar : [ {
			iconCls : 'icon-remove',
			text : '暂停发送(设置报文无效)',
			handler : function() {
				stopOrContinueToSend(1);
			}
		}, '-', {
			iconCls : 'icon-add',
			text : '继续发送(设置报文有效)',
			handler : function() {
				stopOrContinueToSend(2);
			}
		} ]
	});
}

// 查询参数实体
function QueryParams(requestId, keyword1, keyword2, keyword3, startTime, endTime) {
	this.requestId = requestId;
	this.keyword1 = keyword1;
	this.keyword2 = keyword2;
	this.keyword3 = keyword3;
	this.startTime = startTime;
	this.endTime = endTime;
}

// 获取输入的查询的参数
function getParams() {
	var requestId = $("#requestId").val();
	var keyword1 = $("#keyword1").val();
	var keyword2 = $("#keyword2").val();
	var keyword3 = $("#keyword3").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var params = new QueryParams(requestId, keyword1, keyword2, keyword3, startTime, endTime);
	return JSON.stringify(params);
}
// 刷新页面
function reloadData() {
	queryDataList(false);
}
// 清空输入框
function cleanInputData() {
	$("#requestId").val("");
	$("#keyword1").val("");
	$("#keyword2").val("");
	$("#keyword3").val("");
	$("#startTime").val("");
	$("#endTime").val("");
}

// 暂停或继续发送，设置isValid字段，设置是否有效，action:1,停止，2，继续
function stopOrContinueToSend(action) {
	var rowArray = getSelectedRow();
	var rowLength = rowArray.length;
	var idArray = new Array();
	if (rowLength > 0) {
		for (var i = 0; i < rowLength; i++) {
			var row = rowArray[i];
			var id = row.id;
			idArray.push(id);
		}
		$.ajax({
			url : "/message/stopOrContinueToSend.do",
			type : "post",
			data : {
				"idArray" : idArray.join(","),
				"action" : action
			},
			dataType : "json",
			success : function(result) {
				if (result.records > 0) {
					alert("操作成功！");
					location.reload();
				} else {
					alert("操作出错！");
				}
			},
			error : function() {
				alert("请求出错，请稍后再试！");
			}
		});
	} else {
		alert("您还未选择任何记录！");
	}
}

// 获取所有被选择的行
function getSelectedRow() {
	var rowArray = $("#msgTable").datagrid('getChecked');
	return rowArray;
}

// 获取请求信息
function getReqMsg(messageId) {
	$.ajax({
		url : "/messageRequest//getMsgReqByExample.do",
		type : "post",
		data : {
			"messageId" : messageId
		},
		dataType : "json",
		success : function(result) {
			var msgReqMapList = result.msgReqMapList;
			if (msgReqMapList.length > 0) {
				var msgReqMap = msgReqMapList[0];
				var url = msgReqMap.url;
				var body = msgReqMap.body;
				var headerParams = msgReqMap.headerParams;
				var bodyParams = msgReqMap.bodyParams;
				var method = msgReqMap.method;
				var soTimeOut = msgReqMap.soTimeOut;
				var connectTimeOut = msgReqMap.connectionTimeOut;
				var callbackUrl = msgReqMap.callbackUrl;
				var createdTime = msgReqMap.createdTime;
				// 先清空原来的数据
				clearRequestWin();
				$("#url").val(url);
				$("#body").val(body);
				$("#header-params").val(headerParams);
				$("#body-params").val(bodyParams);
				$("#method").val(method);
				$("#socket-time-out").val(soTimeOut);
				$("#connection-time-out").val(connectTimeOut);
				$("#callback-url").val(callbackUrl);
				$("#created-time").val(createdTime);
				showRequstMsgWin();
			} else {
				alert("未成功获取到数据！");
			}
		},
		error : function() {
			alert("请求出错，请稍后再试！");
		}
	});
}
// 显示请求信息窗
function showRequstMsgWin() {
	$("#request-msg-win").window({
		title : "报文请求信息",
		modal : true,
		collapsible : false,
		minimizable : false,
		maximizable : false,
		resizable : false
	});
}
// 清除requestWin数据
function clearRequestWin() {
	$("#url").val("");
	$("#body").val("");
	$("#header-params").val("");
	$("#body-params").val("");
	$("#method").val("");
	$("#socket-time-out").val("");
	$("#connection-time-out").val("");
	$("#callback_url").val("");
	$("#created_time").val("");
}
// 清除responseNewestWin数据
function clearRespNewestWin() {
	$("#http-status").val("");
	$("#http-status-msg").val("");
	$("#response-header").val("");
	$("#response-body").val("");
	$("#send-begin-time").val("");
	$("#send-end-time").val("");
	$("#used-time").val("");
	$("#created-time-newest").val("");
}

// 最新一次响应信息
function getNewResMsg(messageId) {
	$.ajax({
		url : "/msgRespNewest/getMsgRespNewest.do",
		type : "post",
		data : {
			"messageId" : messageId
		},
		dataType : "json",
		success : function(result) {
			var msgRespNewestList = result.msgRespNewestList;
			var msgLength = msgRespNewestList.length;
			if (msgLength > 0) {
				var msgRespNewest = msgRespNewestList[0];
				// 先清空之前的数据
				clearRespNewestWin();
				$("#http-status").val(msgRespNewest.httpStatus);
				$("#http-status-msg").val(msgRespNewest.httpStatusMsg);
				$("#response-header").val(msgRespNewest.responseHeader);
				$("#response-body").val(msgRespNewest.responseBody);
				$("#send-begin-time").val(msgRespNewest.sendBeginTime);
				$("#send-end-time").val(msgRespNewest.sendEndTime);
				$("#used-time").val(msgRespNewest.usedTime);
				$("#created-time-newest").val(msgRespNewest.createdTime);
				$("#resp-msg-new-win").window({
					title : "当前最新一次响应信息",
					modal : true,
					collapsible : false,
					minimizable : false,
					maximizable : false,
					resizable : false
				});
			} else {
				alert("暂未获得响应信息，请稍后再试！");
			}
		},
		error : function() {
			alert("请求出错，请稍后再试！");
		}
	});
}