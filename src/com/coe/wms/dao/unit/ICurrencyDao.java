package com.coe.wms.dao.unit;

import java.util.List;

import com.coe.wms.model.unit.Currency;

public interface ICurrencyDao {

	public Currency findCurrencyByCode(String code);

	public List<Currency> findAllCurrency();
}
