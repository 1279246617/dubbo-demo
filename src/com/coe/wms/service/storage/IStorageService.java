package com.coe.wms.service.storage;

import java.util.List;

import org.apache.log4j.Logger;

import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.warehouse.TrackingNo;
import com.coe.wms.model.warehouse.Warehouse;
import com.coe.wms.model.warehouse.shipway.Shipway;

/**
 * 仓配 api service层
 * 
 * @author Administrator
 * 
 */
public interface IStorageService {

	static final Logger logger = Logger.getLogger(IStorageService.class);

	public TrackingNo getTrackingNoById(Long id) throws ServiceException;

	public List<Shipway> findAllShipway() throws ServiceException;

	public Warehouse getWarehouseById(Long fwarehouseId) throws ServiceException;

	/**
	 * 获取所有仓库
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public List<Warehouse> findAllWarehouse() throws ServiceException;

	/**
	 * 获取所有仓库,并指定排在第一位的仓库id
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public List<Warehouse> findAllWarehouse(Long firstWarehouseId) throws ServiceException;

}
