function clearForm(id) {
    var objId = document.getElementById(id);
    if (objId == undefined) {
        return;
    }
    for (var i = 0; i < objId.elements.length; i++) {
        if (objId.elements[i].type == "text") {
            objId.elements[i].value = "";
        }
        else if (objId.elements[i].type == "password") {
            objId.elements[i].value = "";
        }
        else if (objId.elements[i].type == "radio") {
            objId.elements[i].checked = false;
        }
        else if (objId.elements[i].type == "checkbox") {
            objId.elements[i].checked = false;
        }
        else if (objId.elements[i].type == "select-one") {
            objId.elements[i].options[0].selected = true;
        }
        else if (objId.elements[i].type == "select-multiple") {
            for (var j = 0; j < objId.elements[i].options.length; j++) {
                objId.elements[i].options[j].selected = false;
            }
        }
        else if (objId.elements[i].type == "textarea") {
            objId.elements[i].value = "";
        }
    }
}
//根据字典表加载 相应界面   id=要渲染的模块id,type=界面类型，typeCode=字典表类型，name=单选复选框的name属性值
function initDict(id,type,typeCode,name){
    mmUtl.ajax.postJsonSyn(url.path+"/wms-web-symgr/symgr/dictItem/getAllItemByCode",function(data){
	    var resultVo=data.data;
	    
	    if(type=="checkbox"){
	    	 $.each(resultVo,function(i,item){
	  		   $("#"+id+"").append('<label style="width: 50px;"> <input type="checkbox" class="form-control width100" name="'+name+'"value="'
	  				   +item.itemCode+'" onclick="ban(this)">'+item.itemValue+'</label> ');
	        });
	  	}
	    if(type=="radio"){
	    	 $.each(resultVo,function(i,item){
	    	        
	          	$("#"+id+"").append('<input type="radio" name="'+name+'" value="'
	          			+item.itemCode +'""><h>'+item.itemValue+'</h>');
	        });
	    }
	    if(type=="option"){
	    	$("#"+id+"").append('<option value=""></option>');
	    	 $.each(resultVo,function(i,item){
	              $("#"+id+"").append('<option value="'+item.itemCode+'">'+item.itemValue+'</option>');
	        });
	    }
	 },{
		code:typeCode,
		codeType:'aaa'
		
   });
	
};
//根据参数名获取上一个页面的参数值
function getParam(paramName){
	var param=location.search.substring(1);
	var paramArray=new Array();
	paramArray=param.split("&");
	var newParamName = paramName.concat("=");
	for(var i=0;i<paramArray.length;i++){
		if(paramArray[i].indexOf(newParamName)>-1){
		  var paramValue=paramArray[i].replace(newParamName,'');
		  return paramValue;
		}		
	}
	return null;
};
//日期控件
function toDate(id){
	mmUtl.time.setDateTime(id,{format:'yyyy-mm-dd hh:mm:ss'});
};
function toDate_(id){
	mmUtl.time.setDateTime(id,{format:'yyyy-mm-dd'});
};
//比较日期大小
function dateCompare(startTime,endTime){
	var startTime = new Date(Date.parse(startTime));
	var endTime = new Date(Date.parse(endTime));
	return (endTime>startTime);
};
//补全小数位数
function completionDecimal(value,num) {
	var xsd = value.split(".");
	var str1=".";
    for(var i=0;i<num;i++){
    	str1+="0";
    }
	if (xsd.length == 1) {
		value = value + str1;
		return value;
	}
	var str2="";
    for(var j=0;j<num-xsd[1].length;j++){
    	str2+="0";
    }
	if (xsd.length > 1) {
		if (xsd[1].length < num) {
			value = value + str2;
		}
		return value;
	}
};

//获取ctx路径
var ctx =function(){
	return $("#ctxPath").val();
};

/*
 * 获取页面的按钮
 */
function getButton(){
	var id = getParam("menuId");
	if(id!=null)
	mmUtl.ajax.postJson(globalUrl.symgmtPath+"/symgmt/menu/getButtonByPid/"+id+"",function(data){
		if(data.code == 0){
			menuList = data.data;
			addButton(menuList);
		}else{
			mmui.oper(data.msg,1); 
		}
	});
	
	function addButton(menuList){
		$.each(menuList, function (n, value) {
				$("#buttonAll").append(value.menuEvent+"&nbsp;");
		});
	};
};

/**
 * 跳转新页面
 */
function reloadNewUri(newHref,timeOut){
	 
	if(timeOut&&timeOut!=0)
		window
				.setTimeout(
						function() {
							location.href =newHref; //"${ctx}/jsp/admin/login.jsp"
						}, timeOut)
	else
		location.href =newHref;
	
}

/*
 * 获取表格中的选中
 */
var selectData = function getSelectData(){
	var data=$("#list_body").bootstrapTable("getSelections");
	if(data.length!=1){
		parent.mmui.alert("请选择一条要修改的数据",3);
		return null;
	}
	return data;
}

/*
 * 通用新增方法
 * url: 访问的新增界面;格式如下:symgr/user/add.jsp
 * pageuid:每个页面唯一的id
 * title:访问页面的标题
 */
function addMethod(obj){
	var url = obj.getAttribute("url");
	var pageuid = obj.getAttribute("pageuid");
	var title = obj.getAttribute("title");
	var menuid=obj.getAttribute("menuid");
	//parent.mmgrid(ctx+url+"?method=add",pageuid,title,true);
	

	parent.mmui.open({
			  type: 2,
			  title: title,
			  shadeClose: true,
			  area: ['800px','600px'],
			  content: ctx+url,
			  btn: ['提交','取消'],
			  yes: function(index,obj){	
				 
				  obj.find("iframe")[0].contentWindow.add(index,location.href,menuid);
			  },cancel: function(index){
			  },
			  afterFn:function(index,obj){}
		});
};

/*
 * 通用删除方法
 * obj 是传过来的this
 * code: 要获取的code
 * url格式: /wms-web-symgr/symgr/userMgr/delUser
 */
function delMethod(obj,code){
	data = selectData();
	console.info("code==",code);
	var objCode = code.getAttribute("name");
   	if(data == null){
   		return false;
   	}
	var id=data[0].id;
	var objCodeValue = data[0][objCode];
	var delUrl = obj.getAttribute("url");
	var dataJson={};
	dataJson[objCode] = objCodeValue;
	dataJson.id = id;
	
	parent.mmui.confirm("删除"+objCodeValue,function(delUser){
		parent.mmui.close(delUser);
		
		mmUtl.ajax.postJson(url.path+delUrl,function(data){
			mmui.oper(data.msg,1);
			document.location.reload();
		},dataJson);
	},function(){
		 
	});
};


/*
 * 通用修改方法
 * obj:传过来的this对象
 *
 * 
 */
function updMethod(obj){
	data = selectData();
   	if(data == null){
   		return false;
   	}
	var id = data[0].id;
	var url = obj.getAttribute("url");
	var pageuid = obj.getAttribute("pageuid");
	var title = obj.getAttribute("title");
	var menuid=obj.getAttribute("menuid");
	
	/* parent.window.ssss=data; */
	//var objCode = code.getAttribute("name");
	//parent.mmgrid(ctx+url+"?method=update&id="+id+"&"+objCode+"="+data[0][objCode],pageuid,title,true);
	 parent.mmui.open({
		  type: 2,
		  title: title,
		  shadeClose: true,
		  area: ['800px','600px'],
		  content: ctx+url,
		  btn: ['提交','取消'],
		  yes: function(index,obj){	
			  obj.find("iframe")[0].contentWindow.upd(index,location.href,menuid);
		  },cancel: function(index){
		  },
		  afterFn:function(index,obj){}
	});
	
};

/*
 * 通用导出方法
 */
function exportMethod(){
	
};

/*
 * 通用导入方法
 */
function importMethod(){
	
};
//加载配置
function loadConfig(){
	var configMapStr=$.cookie('configStr')
	if(configMapStr!=null){
	  var configMap=JSON.parse(configMapStr);
	  packageConfig(configMap);
	}
	
}
//封装配置
function packageConfig(configMap){
	 globalUrl.symgmtPath=configMap['symgmtPath']
     globalUrl.inwarehousePath=configMap['inwarehousePath']
     globalUrl.outwarehousePath=configMap['outwarehousePath']
     globalUrl.basePath=configMap['basePath']
     globalUrl.inventoryPath=configMap['inventoryPath']
     globalUrl.reportPath=configMap['reportPath']
}
//刷新配置
function refreshConfig(wareHouseCode){
			$.ajax({
				url : globalUrl.symgmtPath
						+ "/symgmt/config/getConfigByWareHouseCode/"
						+ wareHouseCode + "",
				success : function(data) {
					//loadWareHouse
					if (data.code != 0) {
						mmui.alert(data.msg, 2, 2000);
						return;
					}
					var configMap = data.data;
					packageConfig(configMap)

					var configStr = JSON.stringify(configMap);

					$.cookie("configStr", configStr, {
						path : '/'
					})

				},
				crossDomain: true,
	        	xhrFields: {withCredentials: true},
				dataType : 'json'
			})
		}
