package com.coe.wms.common.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SystemUtil {

	/**
	 * 机器ip
	 * 
	 * @return
	 */
	public static String getHostIp() {
		InetAddress netAddress = null;
		try {
			netAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		if (null == netAddress) {
			return null;
		}
		String ip = netAddress.getHostAddress(); // get the ip address
		return ip;
	}

	/**
	 * 获取进程号
	 * 
	 * @return
	 */
	public static int getProcessID() {
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		return Integer.valueOf(runtimeMXBean.getName().split("@")[0]).intValue();
	}
}
