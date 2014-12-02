--出库订单下架 规格
ALTER TABLE `w_s_out_warehouse_order_item_shelf`
ADD COLUMN `specification`  varchar(100) NULL AFTER `batch_no`;

---入库收货记录明细sku_no
ALTER TABLE `w_s_in_warehouse_record_item`
ADD COLUMN `sku_no`  varchar(100) NULL AFTER `user_id_of_operator`;
