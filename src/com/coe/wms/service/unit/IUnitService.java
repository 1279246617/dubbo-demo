package com.coe.wms.service.unit;

import java.util.List;

import org.apache.log4j.Logger;

import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.unit.Currency;

/**
 * 单位服务
 * 
 * @author Administrator
 * 
 */
public interface IUnitService {

	static final Logger logger = Logger.getLogger(IUnitService.class);

	public Currency findCurrencyByCode(String code) throws ServiceException;

	public List<Currency> findAllCurrency() throws ServiceException;
}
