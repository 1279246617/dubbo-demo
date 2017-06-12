package com.coe.wms.facade.symgmt.service.impl;

import org.mybatis.plugin.model.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coe.wms.facade.symgmt.entity.Operator;
import com.coe.wms.facade.symgmt.service.OperatorService;
import com.coe.wms.service.symgmt.biz.OperatorBiz;

@Service("operatorService")
public class OperatorServiceImpl implements OperatorService {
	@Autowired
	private OperatorBiz operatorBiz;

	private static final Logger logger = LoggerFactory.getLogger(OperatorServiceImpl.class);

	public Operator add(Operator record) {
		return this.operatorBiz.add(record);
	}

	public boolean delete(Long id) {
		return this.delete(id);
	}

	public Operator update(Operator record) {
		return this.operatorBiz.update(record);
	}

	public Operator get(Long id) {
		return this.get(id);
	}

	public Pager<Operator> list(int page, int limit) {
		return this.operatorBiz.list(page, limit);
	}
}