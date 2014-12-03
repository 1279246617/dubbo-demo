package com.coe.wms.service.unit.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.coe.wms.dao.unit.ICurrencyDao;
import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.unit.Currency;
import com.coe.wms.service.unit.IUnitService;

/**
 * 仓配服务
 * 
 * @author Administrator
 * 
 */
@Service("unitService")
public class UnitServiceImpl implements IUnitService {

	@Resource(name = "currencyDao")
	private ICurrencyDao currencyDao;

	private static final Logger logger = Logger.getLogger(UnitServiceImpl.class);

	@Override
	public Currency findCurrencyByCode(String code) throws ServiceException {
		return currencyDao.findCurrencyByCode(code);
	}

	@Override
	public List<Currency> findAllCurrency() throws ServiceException {
		return currencyDao.findAllCurrency();
	}
}
