/*
Navicat MySQL Data Transfer

Source Server         : 192.168.80.12
Source Server Version : 50631
Source Host           : 192.168.80.12:3306
Source Database       : message_platform

Target Server Type    : MYSQL
Target Server Version : 50631
File Encoding         : 65001

Date: 2016-12-27 11:56:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `message`
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `request_id` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '请求id,长度36的UUID,不可重复',
  `keyword` varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '用于搜索报文的关键字 一般如:订单号,跟踪单号,订单id,客户id之类',
  `keyword1` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用于搜索报文的关键字 一般如:订单号,跟踪单号,订单id,客户id之类',
  `keyword2` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用于搜索报文的关键字 一般如:订单号,跟踪单号,订单id,客户id之类',
  `created_time` bigint(20) DEFAULT NULL,
  `count` int(11) DEFAULT '0' COMMENT '发送次数',
  `status` int(11) DEFAULT '0' COMMENT '0:未推送,1:已推送并得到http status 200响应 2:已推送,但响应失败',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_request_id` (`request_id`) USING BTREE,
  KEY `index_keyword` (`keyword`) USING BTREE,
  KEY `index_status` (`status`),
  KEY `index_keyword1` (`keyword1`),
  KEY `index_keyword2` (`keyword2`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for `message_callback`
-- ----------------------------
DROP TABLE IF EXISTS `message_callback`;
CREATE TABLE `message_callback` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message_id` bigint(20) DEFAULT NULL,
  `callback_url` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '结果回调url:以request body 回传对方系统返回的内容',
  `created_time` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_message_id` (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of message_callback
-- ----------------------------

-- ----------------------------
-- Table structure for `message_request`
-- ----------------------------
DROP TABLE IF EXISTS `message_request`;
CREATE TABLE `message_request` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message_id` bigint(20) DEFAULT NULL COMMENT '消息id',
  `url` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '报文请求地址',
  `body` text COLLATE utf8_unicode_ci COMMENT '请求体',
  `header_params` text COLLATE utf8_unicode_ci,
  `body_params` text COLLATE utf8_unicode_ci COMMENT '请求参数 以Map json字符串格式存储',
  `method` int(1) DEFAULT '2' COMMENT '请求类型:1=GET,2=POST',
  `time_out` int(8) DEFAULT NULL COMMENT '超时时间:毫秒',
  `created_time` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_message_id` (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of message_request
-- ----------------------------

-- ----------------------------
-- Table structure for `message_response`
-- ----------------------------
DROP TABLE IF EXISTS `message_response`;
CREATE TABLE `message_response` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message_id` bigint(20) DEFAULT NULL,
  `http_status` int(11) DEFAULT NULL COMMENT 'http状态 如:200 ,404,500',
  `response_header` text COLLATE utf8_unicode_ci,
  `response_body` text COLLATE utf8_unicode_ci COMMENT '对方响应内容',
  `send_begin_time` bigint(20) DEFAULT NULL COMMENT '发送起始时间:时间戳类型',
  `send_end_time` bigint(20) DEFAULT NULL COMMENT '发送结束时间',
  `used_time` bigint(20) DEFAULT NULL COMMENT '历时多少毫秒',
  `status` int(11) DEFAULT NULL COMMENT '枚举状态:1=成功,2:连接超时 3:响应超时 4:对方报错(状态不等于200)',
  `status_desc` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '状态解释',
  `created_time` date DEFAULT NULL COMMENT '此记录创建时间',
  PRIMARY KEY (`id`),
  KEY `index_message_id` (`message_id`),
  KEY `index_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of message_response
-- ----------------------------

-- ----------------------------
-- Table structure for `message_response_newest`
-- ----------------------------
DROP TABLE IF EXISTS `message_response_newest`;
CREATE TABLE `message_response_newest` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message_id` bigint(20) DEFAULT NULL,
  `http_status` int(11) DEFAULT NULL COMMENT 'http状态 如:200 ,404,500',
  `response_header` text COLLATE utf8_unicode_ci,
  `response_body` text COLLATE utf8_unicode_ci COMMENT '对方响应内容',
  `send_begin_time` bigint(20) DEFAULT NULL COMMENT '发送起始时间:时间戳类型',
  `send_end_time` bigint(20) DEFAULT NULL COMMENT '发送结束时间',
  `used_time` bigint(20) DEFAULT NULL COMMENT '历时多少毫秒',
  `status` int(11) DEFAULT NULL COMMENT '枚举状态:1=成功,2:连接超时 3:响应超时 4:对方报错(状态不等于200)',
  `status_desc` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '状态解释',
  `created_time` date DEFAULT NULL COMMENT '此记录创建时间',
  PRIMARY KEY (`id`),
  KEY `index_message_id` (`message_id`),
  KEY `index_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of message_response_newest
-- ----------------------------
