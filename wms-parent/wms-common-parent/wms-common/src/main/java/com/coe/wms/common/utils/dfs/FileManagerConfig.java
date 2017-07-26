package com.coe.wms.common.utils.dfs;

import java.io.Serializable;

public interface FileManagerConfig extends Serializable{
//	public static final String FILE_DEFAULT_AUTHOR = "WangLiang";

    public static final String PROTOCOL = "http://";

    public static final String SEPARATOR = "/";

    public static final String TRACKER_NGNIX_ADDR = "192.168.80.39";

//    public static final String TRACKER_NGNIX_PORT = "";

    public static final String CLIENT_CONFIG_FILE = "client_dfs.properties";
//    public static final String CLIENT_CONFIG_FILE = "client.properties";
}
