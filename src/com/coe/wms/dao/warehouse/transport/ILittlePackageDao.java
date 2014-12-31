package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.FirstWaybill;
import com.coe.wms.util.Pagination;

public interface ILittlePackageDao {

	public long saveLittlePackage(FirstWaybill littlePackage);

	public FirstWaybill getLittlePackageById(Long LittlePackageId);

	public int deleteLittlePackageById(Long LittlePackageId);

	public List<FirstWaybill> findLittlePackage(FirstWaybill LittlePackage, Map<String, String> moreParam, Pagination page);

	public List<String> findLittlePackageTrackingNos(Long bigPackageId);

	public Long countLittlePackage(FirstWaybill LittlePackage, Map<String, String> moreParam);

	public int updateLittlePackageSeatCode(FirstWaybill LittlePackage);

	public int updateLittlePackageCallback(FirstWaybill LittlePackage);

	public int updateLittlePackageStatus(FirstWaybill LittlePackage);

	public int updateLittlePackageStatus(Long bigPackageId, String newStatus);

	public int receivedLittlePackage(FirstWaybill LittlePackage);

	public List<Long> findCallbackUnSuccessPackageId();
}
