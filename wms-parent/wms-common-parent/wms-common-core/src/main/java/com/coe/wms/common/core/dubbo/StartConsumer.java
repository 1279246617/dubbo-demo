package com.coe.wms.common.core.dubbo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * 消费者项目,启动dubbo
 * 
 * @ClassName: StartConsumer
 * @author yechao
 * @date 2017年3月7日 上午11:58:46
 * @Description: TODO
 */
public class StartConsumer extends HttpServlet {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 4995786968861622598L;

	public void init() throws ServletException {
		try {
			Consumer.singleton();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}