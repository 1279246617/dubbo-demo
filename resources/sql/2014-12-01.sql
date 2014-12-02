SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- 创建出库建包记录表
-- ----------------------------
DROP TABLE IF EXISTS `w_s_out_warehouse_package`;
CREATE TABLE `w_s_out_warehouse_package` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `warehouse_id` bigint(20) DEFAULT NULL,
  `user_id_of_operator` bigint(20) DEFAULT NULL,
  `user_id_of_customer` bigint(20) DEFAULT NULL,
  `coe_tracking_no` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `coe_tracking_no_id` bigint(20) DEFAULT NULL,
  `created_time` bigint(20) DEFAULT NULL,
  `remark` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- ----------------------------
-- 更新入库订单物品表
-- ----------------------------
 ALTER TABLE `w_s_in_warehouse_order_item`
ADD COLUMN `sku_no`  varchar(100) NULL AFTER `sku_unit_price`;

-- ----------------------------
-- 更新出库订单物品表
-- ----------------------------
ALTER TABLE `w_s_out_warehouse_order_item`
ADD COLUMN `sku_no`  varchar(100) NULL AFTER `sku_net_weight`,
ADD COLUMN `specification`  varchar(100) NULL AFTER `sku_no`;




