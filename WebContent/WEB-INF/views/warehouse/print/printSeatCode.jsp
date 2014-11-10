<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${baseUrl}/static/css/print/common.css" rel="stylesheet" type="text/css" />
<body class="print-page-label" style="font-size: 12px;">
	<c:forEach var="map"  items="${mapList}">  
	<div class="label-100-70 change-page mt_1mm">
			<table style="width: 100%;text-align: center;margin-top: 8mm;">
				<tr><td >
					<font style="font-size: 9mm;">
						<c:out value="${map.warehouse}"/>
					</font>
				</td></tr>
				
				<tr><td>
				
					<img  style="margin-left: 1mm;" src="data:image/png;base64,<c:out value="${map.seatCodeBarcodeData}"/>">
				</td></tr>
				
				<tr><td>
					<font style="font-size: 18mm;">
						<c:out value="${map.seatCode}"/>
					</font>
				</td></tr>
			</table>
	</div>
	</c:forEach>
</body>
</html>