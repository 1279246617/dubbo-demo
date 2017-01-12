//重置
function reSet() {
	$("#userName").val("");
	$("#password").val("");
}

// 登录
function doLogin() {
	var userName = $("#userName").val();
	var password = $("#password").val();
	if(userName==null||userName==""||password==null||password==""){
		$("#login-notice").html("用户名和密码均不能为空！");
		return;
	}else{
		$("#login-notice").html("");
	}
	var data = {
			"userName":userName,
			"password":password,
			"isValid":0
	        };
	$.ajax({
		url:"/loginUser/doLogin.do",
		type:"post",
		data:data,
		dataType:"json",
		success:function(result){
			var flag = result.flag;
			if(flag){
				location.href="/loginUser/toIndex.do";
			}else{
				alert("用户名或密码错误！");
			}
		},
		error:function(){
			alert("请求出错，请稍后再试！");
		}
	});
}