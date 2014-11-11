package com.coe.wms.dao.warehouse;

import java.util.List;

import com.coe.wms.model.warehouse.Shelf;
import com.coe.wms.util.Pagination;

public interface IShelfDao {

	public Shelf getShelfByCode(Long code);

	public List<Shelf> findShelf(Shelf shelf, Pagination page);

	public Long countShelf(Shelf shelf);

	public Integer addShelf(Shelf shelf);
}
