<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${baseUrl}/static/css/print/common.css" rel="stylesheet" type="text/css" />

<body class="print-page-label">
	<c:forEach var="map"  items="${mapList}"><!-- 循环订单,生成DIV -->
	
		<c:if test="${map.shipwayCode == 'SF'}">
			<!-- 顺丰渠道标签,shipwayCode:SF   --------------------------------------------------------------开始------------------------------------------------------------>
			<div class="label-100-150 change-page mt_1mm" id="SF">
				<div style="height:11mm;width:100%; border-bottom:1px solid  #000;" >
						<div style="width:34mm;float: left;margin-top: 2mm;margin-left: 2mm;">
						<%-- <img src="${baseUrl}/static/img/print/sflogo.png" style="height:7mm;width: 19mm;float: left;"> --%>
						</div>
						<div style="width: 18mm;float: left;margin-top: 0mm;font-size: 7mm;;">
							${map.tradeType}
						</div>
						<div style="width: 8mm;float: left;margin-top: -1.5mm;font-size: 10mm;;">
							E
						</div>
						<div style="width: 32mm;float: right;margin-top: 3mm;">
							<div style="height:2mm;line-height: 2mm;">
									<!--0800 088 830 -->
							</div>
							<div style="height:2mm;line-height: 2mm;">
									<!--www.sf-express.com -->
							</div>
						</div>
				</div>
				<div style="height:12.5mm;width:96mm; border-bottom:1px solid  #000;">
						<div style="height:12mm;width:57mm; border-right:1px solid  #000;line-height:3mm; font-size:12px; overflow: hidden;" class="pull-left">
							<span style="margin-top:0mm;margin-left: 0mm;"  class="pull-left">
								寄方:<c:out value="${map.sender.name}"/>	
									<c:out value="${map.sender.phoneNumber}"/>	<br>
									<c:out value="${map.sender.addressLine1}"/>	
							</span>
						</div>
						<div style="height:12mm;width:38mm;line-height:3mm;font-size: 12px;" class="pull-right">
							<span style="margin-top:2mm;; width: 16mm;"  class="pull-left">
								原寄地
							</span>
							<span  style="margin-top:2mm;"  class="pull-left">
								<c:out value="${map.additionalSf.shipperCode}"/>
							</span>
						</div>
				</div>
				<div style="height:12.5mm;width:100%; border-bottom:1mm solid  #000;">
						<div style="height:12.5mm;width:57mm; border-right:1px solid  #000;line-height:3.5mm; overflow: hidden;font-size: 12px;" class="pull-left">
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
						<div style="height:12.5mm;width:38mm;" class="pull-right">
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
									<img  style="margin-top:3mm;margin-left: 2mm;width:80%" src="data:image/png;base64,<c:out value="${map.trackingNoBarcodeData}"/>">
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
				<div style="height:34mm;width:100%; border-bottom:1px solid  #000;">
					<table  rules="all" style="line-height: 4mm;width:100%;height:31mm;border-color: #000;">
						<tr>
							<td rowspan="1" style="width:57mm;height:14mm;text-align: center;">
								<img  style="margin-left: 1mm;width:80%" src="data:image/png;base64,<c:out value="${map.trackingNoBarcodeData}"/>">
							</td>
							<td rowspan="1" style="width:39mm;height:14mm;text-align: left;line-height:3mm; overflow: hidden;">
									寄方:<c:out value="${map.sender.name}"/><br>	
									<c:out value="${map.sender.phoneNumber}"/>&nbsp;	
									<c:out value="${map.sender.addressLine1}"/>			
							</td>
						</tr>
						<tr>
							<td rowspan="2" style="width:58mm;height:15.5mm;">
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
							<td><c:out value="${map.additionalSf.customerOrderId}"/></td>
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
				</div>	
			</div>
			<!-- 顺丰渠道标签,shipwayCode:SF   --------------------------------------------------------------结束------------------------------------------------------------>
		</c:if>		
		
		
		
		
		<c:if test="${map.shipwayCode == 'ETK'}">
			<!-- ETK渠道标签,shipwayCode:ETK   --------------------------------------------------------------开始------------------------------------------------------------>	
			<div class="label-etk-105-156 change-page mt_1mm"  id="etk">
					<div class="pull-left label-top-1">
				    	<!-- 第一行内容 -->
				    	<div class="etk">
						    e特快 
				    	</div>
				    	<div class="receiver"><b>收件人联</b></div>
				    	<div class="tracking-no-top1">${map.etkTrackingNo}</div>
				    	<!-- 第二行内容 -->
				    	<table style="height:10mm;margin-left:1mm;width: 100%">
				    		<tbody>
				    		<tr>
				    			<td style="width:50%;"><img class="etkpng" src="${baseUrl}/static/img/print/etk.png"></td>
				    			<td style="width:50%;">
				    					<div class="zone" style="margin-left: 6mm;">
				    						<div style="font-size: 41px;margin-top: -8px;">${map.shipwayExtra1}</div>
				    					</div>
				    			</td>
				    		</tr>
				    	</tbody>
				    </table>
			    	<!-- 寄件人信息 居左-->
			    	<div style="float: left;margin-left: 1mm;line-height: 4mm;font-size: 12px;">
			    		<span style="line-height:3mm;">寄件人姓名及地址</span><br>
			    		<span style="width:32mm;">${map.customerNo}</span>
			    		<span style="margin-left:7mm;">852-27555000</span>
			    		<br>
			    		<span style="">Flat G &amp; H, 6/F, Blk 1,</span>
			    		<br>
			    		<span style="">Kwai Tak Industrial Centre,</span>
			    		<br>
			    		<span style="">No 15-33 Kwai Tak St, Kwai Chung</span>
			    		<br>
			    		<span style="">HK&nbsp;&nbsp;NT</span><span style="font-weight: bold;margin-left: 10mm;"><b>邮政编码</b>${map.receiver.postalCode}</span>
			    		<br>
			    	</div>
			    	<!-- 收件人信息 巨右 -->
			    	<div style="float: right;width:42mm;line-height: 4mm;font-size: 12px;">
			    		<span style="line-height:3mm;">收件人姓名及地址</span>
			    		<br>
			    		<div style="float:left; overflow: hidden;width:16mm;height:3.5mm;">${map.receiverName}</div>
			    		<div style="float:right;overflow: hidden;width:24mm;height:3.5mm;">${map.phoneOrMobileNumber}</div>
			    		<br>
			    		<div style="float: left;width:40mm;height:4mm;overflow:hidden;">
			    			${map.receiver.addressLine1}
			    		</div>	
			    		<div style="width:15mm;float: left;">${map.receiver.city}</div>
			    		<div style="float: right;margin-right: 10mm;text-align: left;">${map.receiver.countryName}</div>
			    		<br>
			    		<div style="width:15mm;float: left;">总重</div>
			    		<div style="float: right;margin-right: 10mm;text-align: left;">${map.totalWeight}公斤</div>
			    	</div>	
				 </div>
			     
			   <div class="pull-left label-top-2">
			    	<div class="etk">e特快</div>
			      	<div class="receiver" style="margin-top: -10mm;line-height:5.2mm;"><b>投递局联</b></div>
			      	<br>
			      	<!-- 收件人姓名及地址  居左-->
			    	<div style="float: left;width:54mm;margin-left: 1mm;margin-top: 1mm;line-height: 3.9mm;font-size: 12px;">
			    		<span style="line-height:5px;">收件人信息</span>
			    		<div style="float:right; width: 35mm; overflow:hidden">${map.customerReferenceNo}</div>
			    		<br>
			    		<div style="float:left; overflow: hidden;width:20mm;height:3.5mm;">${map.receiverName}</div>
			    		<div style="float:right;overflow: hidden;width:34mm;height:3.5mm;">${map.phoneOrMobileNumber}</div>
			    		<div style="float:left;overflow: hidden;width:52mm;height:7mm;">
			    				${map.receiver.addressLine1} ${map.receiver.addressLine2}
			    		</div>
				    	<br>
				    	<div style="width:25mm;float: left;">${map.receiver.city}</div>
			    		<div style="float: right;margin-right: 10mm;text-align: left;">${map.receiver.stateOrProvince}</div>
			    		<br>
			    		<div style="width:20mm;float: left;">${map.receiver.countryName}</div>
			    		<div style="float: right;text-align: left;">邮政编码&nbsp;&nbsp;${map.receiver.postalCode}</div>
			    		<br>
			    		<div style="width:15mm;float: left;">运单号码</div>
			    		<div style="float: right;text-align: left;">${map.etkTrackingNo}</div>
			    		<br>
			    		<div style="text-align: left;">
			    			<img  style="margin-top:1mm;" src="data:image/png;base64,<c:out value="${map.trackingNoBarcodeData2}"/>">
			            </div>
			    	</div>
			    	<div style="float: right;width:42mm;margin-right: -1mm;line-height: 3.9mm;font-size: 12px;">
			    		<span>总重</span>
			    		<div style="float: right;margin-right: 10mm;text-align: left;">
			    			${map.totalWeight}公斤
			    		</div>
			    		<br>
			    		<span>收件人簽名</span><br>
			    		<div style="border-bottom:1px solid #000;margin-right: 5mm;height:16mm;"></div>
			    		<span>日期</span><br>
			    		<div style="border-bottom:1px solid #000;margin-right: 5mm;margin-left: 7mm;height:4mm;"></div>
		    		<span>時間</span>
		    		<div style="border-bottom:1px solid #000;margin-right: 5mm;margin-left: 7mm;height:4mm;"></div>
		    	</div>
		     </div>
		     <div class="pull-left label-top-3">
		    	<div class="etk">e特快</div>
		      	<div class="receiver" style="line-height:5.2mm;"><b>海关联</b></div>
		      	<br>
		      	<!-- 寄件人信息 居左-->
		    	<div style="float: left;line-height: 3.2mm;font-size: 12px;margin-left: 1mm;">
		    		<span style="line-height:3mm;">寄件人姓名及地址</span>
		    		<br>
		    		<span style="width:32mm;">${map.customerNo}</span>
		    		<br>
		    		<span style="">Flat G &amp; H, 6/F, Blk 1,</span>
		    		<br>
		    		<span style="">Kwai Tak Industrial Centre,</span>
		    		<br>
		    		<span style="">No 15-33 Kwai Tak St</span>
		    		<br>
		    		<span style=""> Kwai Chung &nbsp;HK&nbsp;&nbsp;NT</span>
		    		<br>
		    		<span style="width:15mm;float: left;">运单号码</span>
		    		<span style=";text-align: left;">${map.etkTrackingNo}</span>
		    	</div>
    	
		    	<!-- 收件人信息 巨右 -->
		    	<div style="float: right;width:48mm;margin-right: 1mm;line-height: 3.2mm;font-size: 12px;">
		    		<span style="line-height:3mm;">收件人姓名及地址</span>
		    		<br>
		    		<div style="float:left; overflow: hidden;width:16mm;height:3.5mm;">${map.receiverName}</div>
		    		<div style="float:right;overflow: hidden;width:31mm;height:3.5mm;">${map.phoneOrMobileNumber}</div>
		    		<span style="">${map.receiver.addressLine1} ${map.receiver.addressLine2}</span><br>
		    		<div style="width:15mm;float: left;">${map.receiver.city}</div>
		    		<div style="float: right;margin-right: 10mm;text-align: left;">${map.receiver.stateOrProvince}</div>
		    		<br>
		    		<div style="width:15mm;float: left;">${map.receiver.countryName}</div>
		    		<div style="float: right;text-align: left; margin-right: 5mm">邮政编码&nbsp;&nbsp;${map.receiver.postalCode}</div>
		    		<div style="width:15mm;float: left;">总重</div>
		    		<div style="float: right;margin-right: 10mm;text-align: left;">${map.totalWeight}公斤</div>
		    	</div>	
		    	
		    	<table style="float: left;border:2px solid #000;width:97mm;margin-left: 0.5mm;font-size: 12;line-height: 3mm;" rules="all">
		    			<tbody><tr style="height:3mm">
		    				<td colspan="5" style="text-align: center">货物信息</td>
		    			</tr>
						<tr style="height:3mm">
							<td>货品详细描述</td>
							<td>重量</td>
							<td>单价</td>
							<td>件数</td>
							<td>海关申报值</td>
						</tr>
						<c:forEach var="customs"  items="${map.etkCustoms}">
							<tr style="height:3.2mm">
								<td style="width:130px;overflow: hidden;height:10px;"><c:out value="${customs.skuName}"/></td>
								<td><c:out value="${customs.customsWeight}"/></td>
								<td><c:out value="${customs.customsValue}"/></td>
								<td><c:out value="${customs.quantity}"/></td>
								<td><c:out value="${customs.totalvalue}"/></td>
							</tr>  
						</c:forEach>
						<tr style="height:4.5mm">
		    				<td colspan="3" style="text-align: center">海关申报总值</td>
		    				<td colspan="1" style="text-align: center">${map.currency}</td>
		    				<td colspan="1" style="text-align: center">${map.totalPrice}</td>
		    			</tr>
		    		</tbody>
		    	</table>
		     </div> 
			</div>	 
			<!-- ETK渠道标签,shipwayCode:ETK   --------------------------------------------------------------结束------------------------------------------------------------>
		</c:if>	
	</c:forEach>
</body>
</html>
