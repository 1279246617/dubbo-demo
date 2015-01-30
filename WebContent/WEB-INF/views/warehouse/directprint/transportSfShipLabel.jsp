<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div style="width: 98mm;height: 148mm;" id="printDiv">
<div style="border:1px solid  #000;width: 98mm;height: 148mm;">
	<link href="${baseUrl}/static/css/print/common.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript"  src="${baseUrl}/static/js/print/LodopFuncs.js"></script>
	<object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
	       <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
	</object>
	
	<div style="height:11mm; width:98mm;border-bottom:1px solid  #000;" >
			<div style="width:42mm;float: left;margin-top: 2mm;margin-left: 2mm;">
			</div>
			<div style="width: 8mm;float: left;margin-top: -1.5mm;font-size: 10mm;;">
				E
			</div>
	</div>
	
	<div style="height:12.5mm;width:98mm; border-bottom:1px solid  #000;">
			<div style="height:12.5mm;width:60mm; border-right:1px solid  #000;line-height:3mm; font-size:11px; overflow: hidden;" class="pull-left">
				<span style="margin-top:0mm;margin-left: 0mm;"  class="pull-left">
					寄方:<c:out value="${map.sender.name}"/>	
						<c:out value="${map.sender.phoneNumber}"/>	<br>
						<c:out value="${map.sender.addressLine1}"/>	
				</span>
			</div>
			<div style="height:12.5mm;width:37mm;line-height:3mm;font-size: 13px;" class="pull-right">
				<span style="margin-top:2mm; width: 16mm;"  class="pull-left">
					原寄地
				</span>
				<span  style="margin-top:2mm;"  class="pull-left">
					<c:out value="${map.additionalSf.shipperCode}"/>
				</span>
			</div>
	</div>
	
	<div style="height:12.5mm;width: 98mm; border-bottom:2px solid  #000;">
			<div style="height:12.5mm;width:60mm; border-right:1px solid  #000;line-height:3.5mm; overflow: hidden;font-size: 12px;" class="pull-left">
					收方:	<c:out value="${map.receiver.countryName}"/>
					<c:out value="${map.receiver.stateOrProvince}"/>
					<c:out value="${map.receiver.city}"/>
					<c:out value="${map.receiver.addressLine1}"/>
					<c:out value="${map.receiver.addressLine2}"/>
					&nbsp;
					<c:out value="${map.receiver.name}"/>
					&nbsp;
					<c:out value="${map.receiver.mobileNumber}"/>
					&nbsp;
					<c:out value="${map.receiver.phoneNumber}"/>
			</div>
			<div style="height:12.5mm;width:37mm;" class="pull-left">
				<div style="margin-top:3mm; width: 16mm;"  class="pull-left">
					目的地
				</div>
				<div  style="margin-top:1mm;font-size: 8mm;font-weight: bold;"  class="pull-left">
					<c:out value="${map.additionalSf.deliveryCode}"/>
				</div>
			</div>
	</div>	
		
	<div style="height:20mm;width: 98mm; border-bottom:1px solid  #000;">
			<div style="height:20mm;width:60mm;text-align: center; border-right:1px solid  #000;" class="pull-left">
						<img  style="margin-top:1mm;margin-left: 0mm;" src="data:image/png;base64,<c:out value="${map.trackingNoBarcodeData}"/>">
						<span style="font-size: 12px; margin-left: 1mm">${map.trackingNo}</span>
			</div>
			
			<div style="height:20mm;width:37mm;" class="pull-left">
				<table  style="width:100%;height:20mm;"  border="0" cellpadding="0" cellspacing="0">
					<tr><td style="text-align: center;line-height:7mm;border-bottom:1px solid #000;"><font style="font-weight: bold;font-size: 5.5mm;">全球顺</font></td></tr>
					<tr style="font-size: 12px;"><td style="height:3mm;width:38mm;border-bottom:1px solid #000;">收件员</td></tr>
					<tr style="font-size: 12px;"><td style="height:3mm;border-bottom:1px solid #000;">寄件日期</td></tr>
					<tr style="font-size: 12px;"><td style="height:3mm;">派件员</td></tr>
				</table>
			</div>
	</div>	
	
	<div style="height:10mm;width: 98mm; border-bottom:1px solid  #000;">
		<table  style="width: 98mm;height:10mm; font-size: 11px;" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<th style="width:20mm;border-bottom:1px solid #000;border-right:1px solid #000;">寄托物品数量</th>
				<th style="width:20mm;border-bottom:1px solid #000;border-right:1px solid #000;">实际重量</th>
				<th style="width:19mm;border-bottom:1px solid #000;border-right:1px solid #000;">计费重量</th>
				<th style="width:15mm;border-bottom:1px solid #000;border-right:1px solid #000;">运费</th>
				<th style="border-bottom:1px solid #000;">费用合计</th>
			</tr>
			<tr>
				<td style="text-align: center;border-right:1px solid #000;"><c:out value="${map.totalQuantity}"/></td>
				<td style="text-align: center;border-right:1px solid #000;"><c:out value="${map.totalWeight}"/></td>
				<td style="text-align: center;border-right:1px solid #000;"><c:out value="${map.totalWeight}"/></td>
				<td style="text-align: center;border-right:1px solid #000;"></td>
				<td style="text-align: center;"></td>
			</tr>
		</table>
	</div>	
	
	<div style="height:20mm;width: 98mm; border-bottom:1px dashed  #000;">
		<table style="line-height: 4mm;height:20mm;width:100%;font-size: 11px;" border="0" cellpadding="0" cellspacing="0">
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
	
	<div style="height:39mm;width: 98mm;">
		<table style="width: 98mm; ;margin-top:0mm;border-color: #000;font-size: 11px;"  border="0" cellpadding="0" cellspacing="0">
			<tr style="height:22mm;">
				<td rowspan="1" style="width:60mm;text-align: center;border-right:1px solid #000;border-bottom:1px solid #000;">
					<img  style="margin-left: 0mm;" src="data:image/png;base64,<c:out value="${map.trackingNoBarcodeData}"/>">
					<span style="font-size: 12px; margin-left: 1mm">${map.trackingNo}</span>
				</td>
				<td rowspan="1" style="width:37mm;height:16mm; text-align: left;line-height:3.5mm; overflow: hidden;border-bottom:1px solid #000;">
						<div style="height:1mm;line-height: 2mm;">
						</div>
						寄方:<c:out value="${map.sender.name}"/><br>	
						<c:out value="${map.sender.phoneNumber}"/>&nbsp;	
						<c:out value="${map.sender.addressLine1}"/>			
				</td>
			</tr>
			<tr>
				<td rowspan="2" style="width:60mm;height:17mm;border-right:1px solid #000;border-bottom:1px solid #000; ">
					收件:
					<c:out value="${map.receiver.countryName}"/>
					<c:out value="${map.receiver.stateOrProvince}"/>
					<c:out value="${map.receiver.city}"/>
					<c:out value="${map.receiver.addressLine1}"/>
					<c:out value="${map.receiver.addressLine2}"/>
					&nbsp;
					<c:out value="${map.receiver.name}"/>
					&nbsp;
					<c:out value="${map.receiver.mobileNumber}"/>
					&nbsp;
					<c:out value="${map.receiver.phoneNumber}"/>
				</td>
				<td>订单号</td>
			</tr>
			<tr>
				<td style="border-bottom:1px solid #000;"><c:out value="${map.additionalSf.orderId}"/></td>
			</tr>
		</table>
	</div>	
	
	<div style="height:21mm;width: 98mm; border-bottom:0px solid  #000;font-size: 11px;">
			<div>
				
			</div>
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
</div>
 </div>

<script type="text/javascript">
		var baseUrl = "${baseUrl}";
		var LODOP=getLodop();
	 	LODOP.PRINT_INIT("出货运单");
	 	LODOP.SET_PRINT_PAGESIZE(0,1000,1500,"Lab");
	 	LODOP.ADD_PRINT_HTM(0,0,960,1460,document.getElementById("printDiv").innerHTML);
// 	 	LODOP.PREVIEW();
	 	LODOP.PRINT();    
	 	window.close();
</script>