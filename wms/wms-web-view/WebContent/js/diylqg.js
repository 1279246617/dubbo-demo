function showParentModal(parentModelId,modelCopyId,modelId){
	window.parent.document.getElementById(parentModelId).innerHTML=$("#"+modelCopyId+"").html()
	var newScript = document.createElement('script');
     newScript.type = 'text/javascript';
     newScript.innerHTML = $("#"+modelCopyId+" script").html();
     window.parent.document.getElementById(parentModelId).appendChild(newScript)
	 window.parent.document.getElementById(parentModelId)
	 $(window.parent.document.getElementById(modelId)).modal('show')

}


function getIfram(locationUri){
	
	var iframes=$(window.parent.document.getElementById("Allbody")).find("iframe")
	
	
	var paIfram;
	
	
	$(iframes).each(function(){
		
		
		if($(this).attr('src')==locationUri){
			paIfram=this
			
		}
	})
	
	return paIfram
}

function refresh(index,str){
	 mmui.alert(str, 1, 2000);
	 parent.mmui.close(index);
	 $("#list_body").bootstrapTable('refresh')
}
function closeIframe(index){
	 parent.mmui.close(index);
	
}



$(function(){
//	document.onkeydown = function () {
//        if (window.event && window.event.keyCode == 13 || window.event.keyCode == 32) {
//            window.event.returnValue = false;
//        }
//		}
	$("form").each(function(){
		$(this).submit( function () {
			  $("#submit_btn").trigger('click');
			  return false;
			} );
	})
})
function valiNotNull(mark) {
	
	
	
	var errStr="";
	$(mark).each(function(){
		
		var sbiling=$(this).prev()
		
		var sbilingStr=$(sbiling).text();
		
		
		if(sbilingStr==''){
			sbilingStr=$(this).attr('placeholder')
		}else if(!sbilingStr){
			sbilingStr=$(this).attr('placeholder')
		}
         
		
		var val=$(this).val();
		 //空字符串
        var zengze=/^\s*$/g;
        if(val.match(zengze)||val==''){
        	errStr+=sbilingStr+"不能为空<br/>";
            
        }
       
	});
	return errStr;
   
}