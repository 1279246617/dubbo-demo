package com.coe.wms.service.transport;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.warehouse.shipway.Shipway;
import com.coe.wms.model.warehouse.transport.FirstWaybill;
import com.coe.wms.model.warehouse.transport.FirstWaybillItem;
import com.coe.wms.model.warehouse.transport.FirstWaybillOnShelf;
import com.coe.wms.model.warehouse.transport.FirstWaybillStatus;
import com.coe.wms.model.warehouse.transport.Order;
import com.coe.wms.model.warehouse.transport.OrderStatus;
import com.coe.wms.model.warehouse.transport.OutWarehousePackage;
import com.coe.wms.pojo.api.warehouse.EventBody;
import com.coe.wms.util.Pagination;

/**
 * 转运 api service层
 * 
 * @author Administrator
 * 
 */
public interface ITransportService {

	static final Logger logger = Logger.getLogger(ITransportService.class);

	public List<Shipway> findAllShipway() throws ServiceException;

}
