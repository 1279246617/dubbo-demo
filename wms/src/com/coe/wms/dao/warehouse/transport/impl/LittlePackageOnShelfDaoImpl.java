package com.coe.wms.dao.warehouse.transport.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.warehouse.transport.ILittlePackageOnShelfDao;
import com.coe.wms.model.warehouse.transport.LittlePackageOnShelf;
import com.coe.wms.util.Pagination;

@Repository("littlePackageOnShelfDao")
public class LittlePackageOnShelfDaoImpl implements ILittlePackageOnShelfDao {

	Logger logger = Logger.getLogger(LittlePackageOnShelfDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	public long saveLittlePackageOnShelf(LittlePackageOnShelf littlePackageOnShelf) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<LittlePackageOnShelf> findLittlePackageOnShelf(LittlePackageOnShelf littlePackageOnShelf, Map<String, String> moreParam, Pagination page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countLittlePackageOnShelf(LittlePackageOnShelf littlePackageOnShelf, Map<String, String> moreParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateLittlePackageOnShelf(LittlePackageOnShelf littlePackageOnShelf) {
		// TODO Auto-generated method stub
		return 0;
	}
 
}
