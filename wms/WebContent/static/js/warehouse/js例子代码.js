//实时动态获取input 值
$('#inputId').bind('input propertychange', function() {
	  alert($(this).val());
});
//ajax全局同步,有效范围包括($post $get) 
$.ajaxSetup({   
    async : false  
}); 
//原始ajax
$.ajax({  
    type : "post",  
     url : "register/RegisterState",  
     data : "test=" + test,  
     async : false,  //同步
     success : function(data){  
       data = eval("(" + data + ")");  
       aDataSet = data;  
     }  
});
//jquery ajax
$.post(url,{
	param1:param1,
	param2:param2
},function(respone) {
		
},"json");//json text

//定时任务 只执行一次
setTimeout(function(){
	alert("1500毫秒");
}, 1500);

//循环任务
var jobName = window.setInterval(function(){ 
		alert("每200毫秒");		 
}, 200);
clearInterval(jobName);//结束循环定时任务

//onclick
$("#checkbox1").click(function(){
		if($("#checkbox1").attr("checked")=="checked"){
		 }else{
		}
		//$("#checkbox1").attr("checked",true); 更改checkbox选中
});

//只读
$("#inputId").attr("readonly","readonly");
//解开只读
$("#inputId").removeAttr("readonly");