package com.coe.wms.facade.symgmt.service.impl;

import org.mybatis.plugin.model.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coe.wms.facade.symgmt.entity.Server;
import com.coe.wms.facade.symgmt.service.ServerService;
import com.coe.wms.service.symgmt.biz.ServerBiz;

@Service("serverService")
public class ServerServiceImpl implements ServerService {
	@Autowired
	private ServerBiz serverBiz;

	private static final Logger logger = LoggerFactory.getLogger(ServerServiceImpl.class);

	public Server add(Server record) {
		return this.serverBiz.add(record);
	}

	public boolean delete(Long id) {
		return this.serverBiz.delete(id);
	}

	public Server update(Server record) {
		return this.serverBiz.update(record);
	}

	public Server get(Long id) {
		return this.serverBiz.get(id);
	}

	public Pager<Server> list(int page, int limit) {
		return this.serverBiz.list(page, limit);
	}
}