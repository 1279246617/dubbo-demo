<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>æ·»å å¤æ³¨</title>
    <link href="@{'public/bootstrap/bootstrap.min.css'}" rel="stylesheet"type="text/css" />
   	<link href="@{'public/ligerui/css/ligerui-all.css'}" rel="stylesheet" type="text/css" />
    <style type="text/css">
        html{overflow: hidden;}
        body{padding: 20px;font-family:tahoma;font-size:12px;}
        td{height: 50px;}
        #Alipay{margin-left: 30px}
        img{margin-left: 10px}
    </style>
</head>
<body>
     <div id="f_remark">
            <div class="pull-left">
               <span class="f-header h4"></span>
            </div>
            <div class="pull-left sep"></div>
            <div class="pull-left f-tb">
                <div class="pull-left widAll">
                    <span class="pull-left">
                        <textarea style="margin: 0px; width: 408px; height: 230px;" title="å¤æ³¨ä¿¡æ¯"  name="remark" id="remark">${remark}</textarea>
                    </span>
                </div>
            </div>
       </div>
</body>
</html>