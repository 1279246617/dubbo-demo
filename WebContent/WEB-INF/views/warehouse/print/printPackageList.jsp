<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
					<div style="height:15mm;weight:100%;">
						<div style="width:45mm;height:13mm;" class="pull-left">
							<img src="${baseUrl}/static/img/print/sflogo.png" style="height:13mm;margin-left: 1mm;">	
						</div>
						<div style="width:55mm;height:13mm;" class="pull-left">
							<img src="${baseUrl}/static/img/print/htlogo.png" style="height:13mm;margin-left: 1mm;">	
						</div>
						<div class="pull-left" style="width:100mm;height:10mm;font-size: 5mm;margin-top: 4mm;">
							顺丰海淘(www.sfht.com)购物清单	
						</div>					
					</div>
					
					<!-- 第二行 -->
					<div style="height:13mm;width:90%;">
						<table style="width:100%;">
							<tr>
								<td style=" height:12mm;">
										<span class="pull-left" style="margin-left: 2mm;font-size: 4mm;">
								 				清单号:<c:out value="${map.trackingNo}"/>
										</span>	
								</td>
								<td>
									<span class="pull-left" style="margin-left: 3mm;font-size: 4mm;">
											海淘订单号:
											<c:out value="${map.customerOrderNo}"/>
									</span>	
								</td>
								<td style="height:12mm;font-size: 4mm;">
									订购日期:<c:out value="${map.createdTime}"/>
								</td>
							</tr>
						</table>
					</div>
					
					<!-- 第三行 -->
					<div style="height:7mm;weight:100%;">
							<table style="margin-left: 2mm;" class="pull-left">
									<tr>
										<td style="font-size: 4.5mm;">客户订单号:</td>
										<td>
												<img  src="data:image/png;base64,<c:out value="${map.customerReferenceNoBarcodeData}"/>">
										</td>
									</tr>
							</table>
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
							<tr style="height:6mm;">
								<th style="text-align: center;">序号</th>
								<th style="text-align:center">商品编号</th>
								<th style="text-align:center">商品名称</th>
								<th style="text-align:center">型号规格</th>
								<th style="text-align:center">货位信息</th>
								<th style="text-align:center">数量</th>
								<th style="text-align:center">金额</th>
							</tr>
							<c:forEach var="item"  items="${map.items}" varStatus="status">  
								<tr style="height:8mm;">
									<td style="width:15mm;height:8mm; text-align: center;">
											${ status.index + 1}
									</td>
									<td style="width:40mm;font-size: 16px;text-align: center;"><b>${item.sku }</b></td>
									<td style="width:60mm;text-align: center;">${item.skuName }</td>
									<td style="width:24mm;text-align: center;">${item.specification }</td>
									<td style="width:24mm;font-size: 18px;text-align: center;"><b>${item.seatCode}</b></td>
									<td style="width:14mm;text-align: center;">${item.quantity }</td>
									<td style="width:auto;text-align: right;">
										<span style="margin-right: 5mm;">
											${item.quantity * item.skuUnitPrice /100}  元
										</span>
									</td>
								</tr>
								<tr>
									<td colspan="7" style="height:8mm;text-align: right;">
										<span style="margin-right: 5mm;">
											总计:${map.totalPrice}元
										</span>
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>
					<!-- 第七行 -->
					<div style="height:8mm;width:100%;margin-top: 3mm;" >
						<table style="width:204mm;height:auto;margin-left: 2mm;" rules="none" border="0">
								<tr>
									<td>配送费(元) : 0.00</td>
									<td>促销折扣(元) : 0.00</td>
									<td>货款合计(元) : ${map.totalPrice}</td>
									<td style="text-align: right;">
										<span style="margin-right: 5mm;">
											实际收取(元) : ${map.totalPrice}
										 </span>
									</td>
								</tr>
						</table>
					</div>		
					<!-- 第九行 -->
					<div  style="height:18mm;weight:100%;margin-left: 2mm;font-size: 4mm;">
						<div style="height:8mm;">非常感谢您在顺丰海淘网站(www.sfht.com)购物 , 我们期待您的再次光临!</div>	
						<div style="height:8mm;">如发现商品破损或与描述不符等任何问题 , 请及时联系顺丰海淘客服中心 021-69763622</div>
						<div style="height:8mm;">顺丰海淘祝您生活愉快!</div>
					</div>
			</div>
		</c:forEach>
		
		  
</body>
</html>