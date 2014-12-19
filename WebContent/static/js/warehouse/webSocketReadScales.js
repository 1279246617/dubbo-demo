var ws;
var ports = ["9999", "888", "8888", "999","8080"]; 
var index = 0;
function toggleConnection(port) {
	  index = index+1;	 
        try {
            ws = new WebSocket("ws://127.0.0.1:"+port);//连接服务器
			ws.onopen = function(event){
            	parent.$.showShortMessage({msg:'电子秤自动读取功能已经启动成功',animate:false,left:"45%"});
            };
			ws.onmessage = function(event){
				var message = event.data;
				var weight = message.match(/([0-9\.]+)/ig);
				$("#weight").val(weight);
			};
			ws.onclose = function(event){
				parent.$.showShortMessage({msg:'电子秤自动读取功能已经关闭',animate:false,left:"45%"});
				shutdown();
			};
			ws.onerror = function(event){
				if(index>=5){
					parent.$.showShortMessage({msg:'电子秤自动读取功能异常,请手动输入重量!',animate:false,left:"45%"});	
					shutdown();
				}else{
					toggleConnection(ports[index]);	
				}
			};
        } catch (ex) {
        	if(index>=5){
        		parent.$.showShortMessage({msg:'电子秤自动读取功能异常:'+ex.message, animate:false,left:"45%"});
        		shutdown();
			}else{
				toggleConnection(ports[index]);	
			}
		}
};

//关闭自动读取
 function shutdown(){
	 clearInterval(autoWeight);
	$("#weight").removeAttr("readonly");
 }