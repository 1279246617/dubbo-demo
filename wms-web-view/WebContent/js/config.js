var globalUrl = new Object();

var ctx;
//swagger路径配置-------------------------start-------------------------

//// 用户管理
//url.user = 'http://192.168.80.110:8080/wms-web-user';
//// 系统管理
////url.symgr = 'http://192.168.80.110:8080/wms-web-symgr';
//swagger本地路径配置-------------------------end-------------------------

//线上路径配置-------------------------start-------------------------

////测试环境地址
//url.path='http://192.168.80.39:8080';

////映射地址
//url.path='http://183.57.45.146:9205';

//本地地址
//url.path='http://localhost:8080';
//系统模块地址
globalUrl.symgmtPath='http://test.coewms.com:8080/wms-web-symgmt';
//入库订单模块地址
globalUrl.inwarehousePath='http://test.coewms.com:8080';
//出库订单模块地址
globalUrl.outwarehousePath='http://test.coewms.com:8080';
//基础信息模块地址
globalUrl.basePath='http://test.coewms.com:8080';
//库存模块地址
globalUrl.inventoryPath='http://test.coewms.com:8080';
//报表模块地址
globalUrl.reportPath='http://test.coewms.com:8080';
//线上路径配置-------------------------end-------------------------
