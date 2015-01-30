package com.coe.wms.service.directprint;

import java.util.Map;

public interface IDirectPrintService {
	public Map<String, Object> getStorageSfShipLabelData(Long outWarehouseOrderId, Double height, Double barcodeScale);

	public Map<String, Object> getTransportSfShipLabelData(Long orderId, Double height, Double barcodeScale);

	public Map<String, Object> getTransportEtkShipLabelData(Long orderId, Double height, Double barcodeScale);
}
