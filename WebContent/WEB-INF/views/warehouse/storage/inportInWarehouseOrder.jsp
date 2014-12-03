<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
</head>
<body>
</body>
<form enctype="multipart/form-data" action="<c:url value="/background/fileOperate/upload.html" />" method="post">
	<input type="file" name="file1" /> <input type="text" name="alais" /><br />
	<input type="file" name="file2" /> <input type="text" name="alais" /><br />
	<input type="file" name="file3" /> <input type="text" name="alais" /><br />
	<input type="submit" value="上传" />
</form>
</html>