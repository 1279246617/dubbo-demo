package com.coe.wms.service.fee;

import java.util.Map;

import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.fee.ShipwayTax;

public interface IShipwayTaxService {
	/**
	 * 添加运输渠道税费
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, String> saveAddShipwayTax(ShipwayTax shipwayTax) throws ServiceException;

	/**
	 * 推送税费给客户
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, String> sendShipwayTax(ShipwayTax shipwayTax) throws ServiceException;
}
