/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : coewms

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2014-11-26 10:22:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `p_product`
-- ----------------------------
DROP TABLE IF EXISTS `p_product`;
CREATE TABLE `p_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id_of_customer` bigint(20) DEFAULT NULL,
  `product_name` varchar(200) DEFAULT NULL,
  `product_type_id` bigint(20) DEFAULT NULL,
  `sku` varchar(50) DEFAULT NULL,
  `warehouse_sku` varchar(50) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `currency` varchar(255) DEFAULT NULL,
  `customs_weight` double DEFAULT NULL,
  `is_need_batch_no` varchar(2) DEFAULT NULL,
  `model` varchar(100) DEFAULT NULL,
  `customs_value` double DEFAULT NULL,
  `origin` varchar(255) DEFAULT NULL,
  `last_update_time` bigint(20) DEFAULT NULL,
  `created_time` bigint(20) DEFAULT NULL,
  `tax_code` varchar(50) DEFAULT NULL,
  `volume` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_product
-- ----------------------------
