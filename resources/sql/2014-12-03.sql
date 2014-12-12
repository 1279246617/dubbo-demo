--商品类型 客户id
ALTER TABLE `p_product_type`
ADD COLUMN `user_id_of_customer`  bigint NULL AFTER `product_type_name`;


--入库订单物品规格
ALTER TABLE `w_s_in_warehouse_order_item`
ADD COLUMN `specification`  varchar(100) NULL AFTER `sku_no`;

