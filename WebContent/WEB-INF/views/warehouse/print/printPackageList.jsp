<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${baseUrl}/static/css/print/common.css" rel="stylesheet" type="text/css" />

<!-- print-page-a4 控制A4宽度, 不控制高度 -->
<body class="print-page-a4">
		
		<!-- 每个出库订单打印一个a4纸 ,控制每个A4的高度和分页,以及上下边距-->
		<div class='a4 change-page'>
				<div style="height:17mm;weight:100%;">
					<div style="width:38%;height:15mm;" class="pull-left">
						<img src="${baseUrl}/static/img/print/sflogo.png" style="height:14mm;">	
					</div>
					<div class="pull-left" style="width:62%;height:14mm;font-size: 7mm;font-weight: bold;margin-top: 1mm;">
						顺丰海淘购物清单	
					</div>					
				</div>
				
				<div style="height:7mm;weight:100%;">
					<div style="width:38%;height:7mm;" class="pull-left">
						<span class="pull-left" style="margin-left: 1mm;">
							 <b>清单号:</b>出库订单号,出库订单id111111
						</span>	
					</div>
					<div class="pull-left" style="width:62%;height:7mm;">
						<span class="pull-right" style="margin-right: 1mm;">
							<b>订单号:</b>顺丰海涛订单号,COE客户参考号
						</span>
					</div>	
				</div>
				
				
				发货日期:	${timeNow}
		</div>
		
		<div class='a4 change-page'>
				<img alt="" src="${baseUrl}/static/img/print/sflogo.png" style="">
				
					发货日期:	${timeNow}
		</div>
		
</body>
</html>