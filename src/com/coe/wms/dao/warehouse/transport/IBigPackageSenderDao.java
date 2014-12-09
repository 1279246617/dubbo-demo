package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.BigPackageSender;
import com.coe.wms.util.Pagination;

public interface IBigPackageSenderDao {

	public long saveBigPackageSender(BigPackageSender sender);

	public int saveBatchBigPackageSender(List<BigPackageSender> senderList);

	public int saveBatchBigPackageSenderWithPackageId(List<BigPackageSender> senderList, Long orderId);

	public List<BigPackageSender> findBigPackageSender(BigPackageSender orderSender, Map<String, String> moreParam, Pagination page);

	public BigPackageSender getBigPackageSenderByPackageId(Long orderId);
}
