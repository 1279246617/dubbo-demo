\<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${baseUrl}/static/css/print/common.css" rel="stylesheet" type="text/css" />

<!-- print-page-a4 控制A4宽度, 不控制高度 -->
<body class="print-page-a4" style="font-size:12px;">
		<!-- 每个出库订单打印一个a4纸 ,控制每个A4的高度和分页,以及上下边距-->
		<c:forEach var="map"  items="${mapList}">  
			<div class='a4-for-packagelist change-page'>
					<!-- 第一行 -->
					<div style="height:17mm;weight:100%;">
						<div style="width:38%;height:15mm;" class="pull-left">
							<img src="${baseUrl}/static/img/print/sflogo.png" style="height:13mm;margin-left: 1mm;">	
						</div>
						<div class="pull-left" style="width:62%;height:14mm;font-size: 7mm;font-weight: bold;margin-top: 1mm;">
							顺丰海淘购物清单	
						</div>					
					</div>
					<!-- 第二行 -->
					<div style="height:13mm;width:100%;">
						<table style="width:100%;">
							<tr>
								<td style=" height:12mm;">
										<span class="pull-left" style="margin-left: 2mm;">
								 				<b>清单号:</b><c:out value="${map.trackingNo}"/>
										</span>	
								</td>
								<td>
									<span class="pull-left" style="margin-left: 3mm;">
											<b>海淘订单号:</b>
											<c:out value="${map.customerOrderNo}"/>
									</span>	
								</td>
								<td style="height:12mm;">
										<table style="" class="pull-right">
											<tr>
												<td><b>客户订单号:</b></td>
												<td>
														<img  src="data:image/png;base64,<c:out value="${map.customerReferenceNoBarcodeData}"/>">
												</td>
											</tr>
										</table>
								</td>
							</tr>
						</table>
					</div>
					
					<!-- 第三行 -->
					<div style="height:7mm;weight:100%;">
						<span style="float:left;margin-left: 2mm;">
							卖家备注:<c:out value="${map.logisticsRemark}"/>										
						</span>
					</div>
					<!-- 第四行 -->
					<div style="height:7mm;weight:100%;">
						<span style="float:left;margin-left: 2mm;">
								买家备注:<c:out value="${map.tradeRemark}"/>
						</span>
					</div>					
					<!-- 第六行  商品列表 不指定高度,不管有多少item 都必须显示-->
					<div style="weight:100%;" >
							<table style="width:204mm;height:auto;margin-left: 2mm;" rules="all" border="1">
							<tr>
								<td colspan="7">
										<span style="float: left;width:15mm;">收货人:</span>
										<span style="float: left;width:50mm;">${map.receiverName}</span>
										
										<span style="float: left;width:10mm;">电话:</span>
										<span style="float: left;width:100mm;">${map.receiverMobileNumber}  ${map.receiverPhoneNumber}</span>
								</td>
							</tr>	
							<tr style="height:5mm;">
								<th style="text-align:left">序号</th>
								<th style="text-align:left">商品编号</th>
								<th style="text-align:left">商品名称</th>
								<th style="text-align:left">型号规格</th>
								<th style="text-align:left">货位信息</th>
								<th style="text-align:left">数量</th>
								<th style="text-align:left">金额</th>
							</tr>
							<c:forEach var="item"  items="${map.items}" varStatus="status">  
								<tr style="height:7mm;">
									<td style="width:15mm;">
											${ status.index + 1}
									</td>
									<td style="width:40mm;">${item.sku }</td>
									<td style="width:60mm;">${item.skuName }</td>
									
									<td style="width:24mm;"></td>
									<td style="width:24mm;">${item.seatCode}</td>
									<td style="width:14mm;">${item.quantity }</td>
									<td style="width:auto;">${item.skuUnitPrice}  ${item.skuPriceCurrency} </td>
								</tr>
							</c:forEach>
						</table>
					</div>
					<!-- 第七行 -->
					<div style="height:8mm;weight:100%;" >
						<span style="float:left;margin-left: 2mm;">
							商品数量：${map.totalQuantity}
						</span>
					</div>		
					<!-- 第八行 -->
					<div style="height:12mm;weight:100%;" >
						<span style="float:left;margin-left: 2mm;">
								发货日期:	${timeNow}
						</span>
					</div>
					
					<!-- 第九行 -->
					<div  style="height:18mm;weight:100%;margin-left: 2mm;">
						<div>高品质生活，就在顺丰海淘！</div>
						<div>温馨提示：如发现商品破损或与描述不符等任何问题，请及时联系我们的售后服务热线 ：400-8111111。</div>
						<div>欢迎对我们的工作进行监督，我们将不断改进，直至符合您高品质的要求！</div>
						<div>顺丰海淘祝您生活愉快!</div>
					</div>
			</div>
		</c:forEach>
		
		  
</body>
</html>