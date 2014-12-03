<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="${baseUrl}/static/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${baseUrl}/static/bootstrap/bootstrap.min.css" rel="stylesheet"type="text/css" />
	<link href="${baseUrl}/static/bootstrap/common.css" rel="stylesheet" type="text/css"/>
</head>
<body>
</body>
<form enctype="multipart/form-data" action="${baseUrl}/warehouse/storage/executeImportInWarehouseOrder.do" method="post">
	<input type="file" name="file1" /> <input type="text" name="alais" />
	
	<input type="submit" value="上传" />
</form>
	 <script type="text/javascript">
	 	var baseUrl = "${baseUrl}";
	 	
	 	
	 
	 </script>
</html>