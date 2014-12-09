package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.BigPackageReceiver;
import com.coe.wms.util.Pagination;

public interface IBigPackageReceiverDao {

	public long saveBigPackageReceiver(BigPackageReceiver receiver);

	public int saveBatchBigPackageReceiver(List<BigPackageReceiver> receiverList);

	public int saveBatchBigPackageReceiverWithPackageId(List<BigPackageReceiver> receiverList, Long orderId);

	public List<BigPackageReceiver> findBigPackageReceiver(BigPackageReceiver order, Map<String, String> moreParam, Pagination page);

	public BigPackageReceiver getBigPackageReceiverByPackageId(Long orderId);
}
