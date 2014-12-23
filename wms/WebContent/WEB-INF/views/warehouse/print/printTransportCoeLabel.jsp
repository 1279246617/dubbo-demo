<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${baseUrl}/static/css/print/common.css" rel="stylesheet" type="text/css" />

<body class="print-page-label">
			<div class="label-100-150 change-page mt_1mm">
				<div style="height:8mm;width:100%; border-bottom:0px solid  #000;text-align: center" >
						<div style="width: 68mm;float: left;margin-top: 0mm;margin-left:19mm; font-size: 5mm;;">
							${map.outWarehouseRecord.coeTrackingNo}	&nbsp;&nbsp;<span style="font-size: 4mm;">PCS/件數:${map.quantity}</span>
						</div>
				</div>
				<div style="height:21mm;width:96mm; border-bottom:0px solid  #000;">
						 <div style="width:4mm;line-height:3.5mm;font-weight: bold;" class="pull-left">
						 		<font size="3.5">F<br>R</font><br>/<br>寄件人
						 </div>
						 
						 <div style="width:90mm;line-height:3.5mm;"  class="pull-left">
						 		<table style="width:90mm;font-weight: bold;">
						 			<tr>
						 				<td>ECM1833M</td>
						 			</tr>
						 			<tr>
						 				<td>COE香港仓</td>
						 			</tr>
						 		</table>
						 </div>
				</div>
				
				<div style="height:21mm;width:96mm; border-bottom:2px solid  #000;font-size: 12px;">
					<div style="margin-left: 2mm;" >
							<table style="line-height: 3mm;width:90mm;" class="pull-left">
								<tr>	
									<td >ShipDate/發貨日:${map.shipdate}</td>
									<td>WT/實重: ${map.totalWeight}kg</td>
								</tr>
								<tr>	
									<td >Origin ID/始發地:HK</td>
									<td>DWT/體積重: ${map.totalWeight}kg</td>
								</tr>
								<tr>	
									<td >SVC/服務:HKG</td>
									<td>Payment/付費方式:PP</td>
								</tr>
								<tr>	
									<td >PCS/件數:${map.quantity}</td>
									<td>CC Charge/到付款:0</td>
								</tr>
								<tr>	
									<td >DESC/品名:順豐商品</td>
									<td>DC Charge/代付款:0</td>
								</tr>
							</table>
					</div>
				</div>
				
				<div style="height:21mm;width:96mm; border-bottom:0px solid  #000;">
							<div style="height:5mm;margin-left: 70mm;font-weight: bold;">
									SP/說明:
							</div>	
							
						 <div style="width:4mm;line-height:3.5mm;font-weight: bold;" class="pull-left">
						 		<font size="3">T<br>O</font><br>/<br>收件人
						 </div>
						 
						 <div style="width:90mm;line-height:3.5mm;"  class="pull-left">
						 		<table style="width:90mm;font-weight: bold;">
						 			<tr>
						 				<td>吳偉利</td>
						 			</tr>
						 			<tr>
						 				<td>67951518</td>
						 			</tr>
						 			<tr>
						 				<td>新界葵涌葵豐街 18-26號永康工業大廈 J座地下吳偉利</td>
						 			</tr>
						 			<tr>
						 				<td>香港特别行政区</td>
						 			</tr>
						 		</table>
						 </div>
				</div>
				
				<table style="border-top: 1px solid #000; border-bottom:  1px solid #000; width:100%;height:7mm;font-weight: bold;" rules="all">
						<tr>
							<td style="width:48mm;">派送附加費項目:</td>
							<td>&nbsp;</td>
						</tr>
				</table>
				
				<div style="width:100%;height:5mm; text-align: center;font-weight: bold;">
						${map.outWarehouseRecord.coeTrackingNo}
				</div>
				<div style="width:100%;height:52mm;text-align: center;border-bottom: 2px solid #000; ">
						<img src="data:image/png;base64,${map.coeTrackingNoBarcodeData}">
						<div style="line-height: 3mm;width: 100%;font-size: 12px;text-align: left;">
							COE standard terms and conditions apply (including terms on this label),Warsaw Convention may also apply;Informations of shipper and consignee,waybillnumber and the piece on this label would be approved by COE,but the other informations(for example of weight) should be finally confirmed by COE.
						</div>
				</div>
				
				
				
				<div style="width: 100%;font-size: 12px;line-height: 4mm;">
					<span class="pull-left" style="width:50mm;margin-left: 2mm;">
						詢網址:WWW.COE.COM.HK  	
					</span>
					<span class="pull-right" style="width:40mm;margin-right: 1mm;text-align: left;">
						諮詢電話:00852-27579053
					</span>
				</div>
		</div>
</body>
</html>

