package com.coe.wms.dao.warehouse;

import java.util.List;

import com.coe.wms.model.warehouse.Seat;

public interface ISeatDao {

	public Seat getSeatByCode(Long code);

	public List<Seat> findSeat(Seat seat);
	
}
