SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `w_s_out_warehouse_package`
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
