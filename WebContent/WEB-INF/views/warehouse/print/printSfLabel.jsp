<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${baseUrl}/static/css/print/common.css" rel="stylesheet" type="text/css" />

<body class="print-page-label">
		<!-- 循环订单,生成DIV -->
		<c:forEach var="map"  items="${mapList}">  
			<div class="label-100-150 change-page mt_1mm">
				<div style="height:11mm;width:100%; border-bottom:1px solid  #000;" >
						<div style="width:54mm;float: left;margin-top: 2mm;margin-left: 2mm;">
<%-- 							<img src="${baseUrl}/static/img/print/sflogo.png" style="height:7mm;width: 19mm;float: left;"> --%>
						</div>
						<div style="width: 8mm;float: left;margin-top: -1.5mm;font-size: 10mm;;">
							E
						</div>
						<div style="width: 32mm;float: right;margin-top: 3mm;">
							<div style="height:2mm;line-height: 2mm;">
<!-- 								0800 088 830 -->
							</div>
							<div style="height:2mm;line-height: 2mm;">
<!-- 								www.sf-express.com -->
							</div>
						</div>
				</div>
				
				<div style="height:11mm;width:96mm; border-bottom:1px solid  #000;">
						<div style="height:12mm;width:57mm; border-right:1px solid  #000;" class="pull-left">
							<span style="margin-top:2mm;margin-left: 2mm;"  class="pull-left">
								寄方:<c:out value="${map.additionalSf.senderAddress}"/>
							</span>
						</div>
						
						<div style="height:12mm;width:38mm;" class="pull-right">
							<span style="margin-top:2mm;; width: 16mm;"  class="pull-left">
								原寄地
							</span>
							<span  style="margin-top:2mm;"  class="pull-left">
								<c:out value="${map.additionalSf.shipperCode}"/>
							</span>
						</div>
				</div>
				
				<div style="height:12mm;width:100%; border-bottom:1mm solid  #000;">
						<div style="height:12mm;width:57mm; border-right:1px solid  #000;" class="pull-left">
							<span style="margin-top:3mm;margin-left: 2mm;"  class="pull-left">
								收方:<b>自取</b>
							</span>
						</div>
						<div style="height:12mm;width:38mm;" class="pull-right">
							<span style="margin-top:3mm; width: 16mm;"  class="pull-left">
								目的地
							</span>
							<span  style="margin-top:1mm;font-size: 8mm;font-weight: bold;"  class="pull-left">
								<c:out value="${map.additionalSf.deliveryCode}"/>
							</span>
						</div>
				</div>	
					
				<div style="height:22mm;width:100%; border-bottom:1mm solid  #000;">
						<div style="height:22mm;width:57mm;text-align: center; border-right:1px solid  #000;" class="pull-left">
									<img  style="margin-top:3mm;margin-left: 2mm;" src="data:image/png;base64,<c:out value="${map.trackingNoBarcodeData}"/>">
						</div>
						
						<div style="height:12mm;width:38mm;" class="pull-left">
							<table rules="all"style="width:100%;">
								<tr><td style="text-align: center;"><font style="font-weight: bold;font-size: 5mm;">全球顺</font></td></tr>
								<tr><td>收件员</td></tr>
								<tr><td>寄件日期</td></tr>
								<tr><td>派件员</td></tr>
							</table>
						</div>
				</div>	
				
				<div style="height:10mm;width:100%; border-bottom:1px solid  #000;">
					<table rules="all"style="width:100%;height:10mm;border-color: #000;">
						<tr>
							<th style="width:22mm;">寄托物品数量</th>
							<th style="width:18mm;">实际重量</th>
							<th style="width:18mm;">计费重量</th>
							<th style="width:15mm;">运费</th>
							<th>费用合计</th>
						</tr>
						<tr>
							<td style="text-align: center;"><c:out value="${map.totalQuantity}"/></td>
							<td style="text-align: center;"><c:out value="${map.totalWeight}"/></td>
							<td style="text-align: center;"><c:out value="${map.totalWeight}"/></td>
							<td style="text-align: center;"></td>
							<td style="text-align: center;"></td>
						</tr>
					</table>
				</div>	
				<div style="height:20mm;width:100%; border-bottom:1px dashed  #000;">
					<table  rules="none" style="line-height: 4mm;border-color: #000;">
						<tr>
							<td style="width:31mm;">付款方式:<c:out value="${map.additionalSf.payMethod}"/></td>
							<td style="width:34mm;">月结帐号:<c:out value="${map.additionalSf.custId}"/></td>
							<td>第三方地区:<c:out value="${map.additionalSf.deliveryCode}"/></td>
						</tr>
						
						<tr>
							<td style="width:31mm;">保价声明值:</td>
							<td style="width:34mm;">保费:</td>
							<td></td>
						</tr>
						
						<tr>
							<td style="width:31mm;">代收货款金额:</td>
							<td style="width:34mm;">卡号:</td>
							<td>收方签署:</td>
						</tr>
						
						<tr>
							<td colspan="3"><span class="pull-right">日期:
							&nbsp; &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;月
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日</span></td>
						</tr>
						
					</table>
				</div>	
				
				<div style="height:32mm;width:100%; border-bottom:1px solid  #000;">
					<table  rules="all" style="line-height: 4mm;width:100%;height:31mm;border-color: #000;">
						<tr>
							<td rowspan="2" style="width:58mm;height:15.5mm;text-align: center;">
								<img  style="margin-left: 1mm;" src="data:image/png;base64,<c:out value="${map.trackingNoBarcodeData}"/>">
							</td>
							<td>寄方</td>
						</tr>
						<tr>
							<td>518000</td>
						</tr>
						
						<tr>
							<td rowspan="2" style="width:58mm;height:15.5mm;">
								收件:
								<c:out value="${map.receiver.countryName}"/>
								<c:out value="${map.receiver.stateOrProvince}"/>
								<c:out value="${map.receiver.city}"/>
								<c:out value="${map.receiver.addressLine1}"/>
								<c:out value="${map.receiver.addressLine2}"/>
								<c:out value="${map.receiver.name}"/>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<c:out value="${map.receiver.phoneNumber}"/>
								&nbsp;&nbsp;&nbsp;
								<c:out value="${map.receiver.mobileNumber}"/>
							</td>
							<td>订单号</td>
						</tr>
						<tr>
							<td><c:out value="${map.additionalSf.orderId}"/></td>
						</tr>
						
					</table>
				</div>	
				<div style="height:auto;width:100%; border-bottom:0px solid  #000;">
						<div style="line-height: 3.2mm;width:100%;">
							<c:forEach var="item"  items="${map.items}">
									<c:if test="${item.skuName != ''}">
										<c:out value="${item.skuName}"/>,
									</c:if>
									<c:if test="${item.sku != ''}">
										<c:out value="${item.sku}"/>,
									</c:if>
									<c:out value="${item.quantity}"/>,
									<c:out value="${item.remark}"/>
									&nbsp;&nbsp;&nbsp;&nbsp;
							</c:forEach>
							
						</div>
						
<!-- 						<table style="height:auto;width:100%;border-color: #000;" rules="rows"> -->
<!-- 							<tr  style="height:4mm;"> -->
<!-- 								<th style="width:19">寄托物</th> -->
<!-- 								<th style="width:19">SKU</th> -->
<!-- 								<th style="width:19">数量</th> -->
<!-- 								<th>备注</th> -->
<!-- 							</tr> -->
<%-- 							<c:forEach var="item"  items="${map.items}">   --%>
<!-- 								<tr style="height:4mm;"> -->
<!-- 									<td style="text-align: center;"> -->
<%-- 										<c:out value="${item.skuName}"/> --%>
<!-- 									</td> -->
<!-- 									<td style="text-align: center;"> -->
<%-- 										<c:out value="${item.sku}"/> --%>
<!-- 									</td> -->
<!-- 									<td style="text-align: center;"> -->
<%-- 										<c:out value="${item.quantity}"/> --%>
<!-- 									</td> -->
<!-- 									<td style="text-align: center;"> -->
<%-- 										<c:out value="${item.remark}"/> --%>
<!-- 									</td>						 -->
<!-- 								</tr>		 -->
<%-- 							</c:forEach> --%>
<!-- 						</table> -->
				</div>	
			</div>
		</c:forEach>
</body>
</html>
