package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.FirstWaybillOnShelf;
import com.coe.wms.util.Pagination;

public interface ILittlePackageOnShelfDao {

	public long saveLittlePackageOnShelf(FirstWaybillOnShelf littlePackageOnShelf);

	public List<FirstWaybillOnShelf> findLittlePackageOnShelf(FirstWaybillOnShelf littlePackageOnShelf, Map<String, String> moreParam, Pagination page);

	public Long countLittlePackageOnShelf(FirstWaybillOnShelf littlePackageOnShelf, Map<String, String> moreParam);

	public int updateLittlePackageOnShelf(FirstWaybillOnShelf littlePackageOnShelf);

	public int updateLittlePackageOnShelf(Long bigPackageId, String newStatus);

	public String findSeatCodeForOnShelf(String businessType);

	public String findStatusByLittlePackageId(Long littlePackageId);

	public FirstWaybillOnShelf findLittlePackageOnShelfByLittlePackageId(Long littlePackageId);
}
