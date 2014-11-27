<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${baseUrl}/static/css/print/common.css" rel="stylesheet" type="text/css" />
<body class="print-page-label" style="font-size: 12px;">
	<c:forEach var="map"  items="${mapList}">  
	<div class="label-80-25 change-page mt_1mm">
			<table style="width: 100%;text-align: center;margin-top: 2mm;margin-left: 4mm;">
				<tr>
					<td>
						<img  style="margin-left: 1mm;" width="90%" src="data:image/png;base64,<c:out value="${map.skuBarcodeData}"/>">
					</td>
				</tr>
				
				<tr>
					<td>
						<font style="font-size: 6mm;margin-left: 3mm;"><c:out value="${map.sku}"/></font>
					</td>
				 </tr>
			</table>
	</div>
	</c:forEach>
</body>
</html>