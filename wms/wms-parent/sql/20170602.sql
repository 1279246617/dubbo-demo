#管理员表
CREATE TABLE `u_admin` (
`id`  bigint NULL ,
`user_name`  varchar(50) NULL COMMENT '用户名字' ,
`login_name`  varchar(50) NULL COMMENT '登录名' ,
`password`  varchar(50) NULL COMMENT '密码' ,
`status`  int NULL COMMENT '状态' ,
`created_time`  datetime NULL COMMENT '创建时间' ,
`created_by_user_id`  bigint NULL COMMENT '创建者用户id' ,
`last_updated_time`  datetime NULL COMMENT '最后更新时间' ,
`lasts_updated_by_user_id`  bigint NULL COMMENT '最后更新者的id' ,
PRIMARY KEY (`id`)
);


#客户表



#作业系统用户(操作员)表


