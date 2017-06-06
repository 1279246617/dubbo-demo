#管理员表
CREATE TABLE `u_admin` (
`id`  bigint NULL ,
`user_name`  varchar(50) NULL COMMENT '用户名字' ,
`login_name`  varchar(50) NULL COMMENT '登录名' ,
`password`  varchar(50) NULL COMMENT '密码' ,
`status`  int NULL COMMENT '状态' ,
`created_time`  datetime NULL COMMENT '创建时间' ,
`created_by_admin_id`  bigint NULL COMMENT '创建者用户id' ,
`last_updated_time`  datetime NULL COMMENT '最后更新时间' ,
`lasts_updated_by_admin_id`  bigint NULL COMMENT '最后更新者的id' ,
PRIMARY KEY (`id`)
);

#仓库表
CREATE TABLE `w_warehouse` (
`id`  bigint NULL ,
`whse_name`  varchar(50) NULL COMMENT '仓库名字' ,
`whse_code`  varchar(20) NULL COMMENT '仓库代码' 
);

#服务器表
CREATE TABLE `s_server` (
`id`  bigint NULL ,
`server_code`  varchar(20) NULL COMMENT '服务器代码' ,
`server_name`  varchar(50) NULL COMMENT '服务器名字' ,
`server_index_url`  varchar(255) NULL COMMENT '服务器入口地址' 
);

#客户表
CREATE TABLE `u_customer` (
`id`  bigint NULL ,
`user_name`  varchar(50) NULL COMMENT '用户名字' ,
`login_name`  varchar(50) NULL COMMENT '登录名' ,
`password`  varchar(50) NULL COMMENT '密码' ,
`status`  int NULL COMMENT '状态' ,
`created_time`  datetime NULL COMMENT '创建时间' ,
`created_by_admin_id`  bigint NULL COMMENT '创建者用户id' ,
`last_updated_time`  datetime NULL COMMENT '最后更新时间' ,
`lasts_updated_by_admin_id`  bigint NULL COMMENT '最后更新者的id' ,
PRIMARY KEY (`id`)
);

#作业系统用户(操作员)表
CREATE TABLE `u_operator` (
`id`  bigint NULL ,
`user_name`  varchar(50) NULL COMMENT '用户名字' ,
`login_name`  varchar(50) NULL COMMENT '登录名' ,
`password`  varchar(50) NULL COMMENT '密码' ,
`status`  int NULL COMMENT '状态' ,
`created_time`  datetime NULL COMMENT '创建时间' ,
`created_by_admin_id`  bigint NULL COMMENT '创建者用户id' ,
`last_updated_time`  datetime NULL COMMENT '最后更新时间' ,
`lasts_updated_by_admin_id`  bigint NULL COMMENT '最后更新者的id' ,
PRIMARY KEY (`id`)
);


