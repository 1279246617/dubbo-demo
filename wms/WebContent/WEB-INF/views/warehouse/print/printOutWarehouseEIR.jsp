<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${baseUrl}/static/css/print/common.css" rel="stylesheet" type="text/css" />
<link href="${baseUrl}/static/bootstrap/bootstrap.min.css" rel="stylesheet"type="text/css" />
<link href="${baseUrl}/static/bootstrap/common.css" rel="stylesheet" type="text/css"/>
<!-- print-page-a4 控制A4宽度, 不控制高度 -->
<body class="print-page-a4" style="font-size:12px;font-weight: bold;">
			<div class='a4-for-packagelist change-page'>
					<table class="table" border="0" rules="none">	
						<tr >
							<td colspan="4" style="text-align: center; height: 30px;">
								<H3 style="color:black;">交接单号:${coeTrackingNo}</H3>
							</td>
						</tr>
						<tr>
							<td  colspan="4">仓库名称:${warehouse.warehouseName} &nbsp;&nbsp;&nbsp; 出库日期:${timeNow}</td>
						</tr>
						
						<tr>
							<th>序号</th><th>出库运单号</th><th>出库订单号</th><th>重量KG</th>
						</tr>
						
						<c:forEach var="item"  items="${mapList}" varStatus="status">  
							  <tr>
							  	<td>${status.index + 1}</td>
							  	<td>${item.ourWarehouseOrderTrackingNo}</td>
							  	<td>${item.customerReferenceNo}</td>
							  	<td>${item.outWarehouseWeight}</td>
							  </tr>
						</c:forEach>
						
						
						<tr>
							<td colspan="1">
								 合计顺丰单号个数:${total}
							</td>
							<td></td>
							<td></td>					
							<td colspan="1">
								 合计重量:${totalWeight}
							</td>		
						</tr>
					</table>	
			</div>
</body>
</html>