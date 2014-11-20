var ws;
//连接
function toggleConnection() {
	try {
		ws = new WebSocket("ws://127.0.0.1:9999");// 连接服务器
		ws.onopen = function(event) {
			alert("已经与服务器建立了连接\r\n当前连接状态：" + this.readyState);
		};
		ws.onmessage = function(event) {
//			alert("接收到服务器发送的数据：\r\n" + event.data);
		};
		ws.onclose = function(event) {
//			alert("已经与服务器断开连接\r\n当前连接状态：" + this.readyState);
		};
		ws.onerror = function(event) {
			alert("WebSocket异常,电子秤自动读取功能不可用!");
		};
	} catch (ex) {
		alert(ex.message);
	}
};

var getWeight=function(){
	try {
		ws.send("getweig");
	} catch (ex) {
		return ex.message;
	}
};
 
//
//function SendData() {
//	try {
//		ws.send("beston");
//	} catch (ex) {
//		alert(ex.message);
//	}
//};
//
//function seestate() {
//	alert(ws.readyState);
//}
//
//function seedata() {
//	ws.onmessage = function(event) {
//		alert("接收到服务器发送的数据：\r\n" + event.data);
//	};
//}
