package com.coe.wms.model.warehouse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.coe.wms.util.StringUtil;

/**
 * 货架
 * 
 * @author Administrator
 * 
 */
public class Shelf implements Serializable {

	/**
	 * 地面货架
	 */
	public static final String TYPE_GROUND = "G";

	/**
	 * 立体货架
	 */
	public static final String TYPE_BUILD = "B";

	/**
	 * 
	 */
	private static final long serialVersionUID = 5963221596346743409L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 地面货架的起始货位
	 */
	private Integer seatStart;

	/**
	 * 地面货架的终止货位
	 */
	private Integer seatEnd;

	/**
	 * 行 当货架类型是:立体货架时
	 */
	private Integer rows;

	/**
	 * 列 当货架类型是:立体货架时
	 */
	private Integer cols;

	/**
	 * 类型
	 */
	private String shelfType;

	/**
	 * 货架号
	 */
	private String shelfCode;

	/**
	 * 所属仓库
	 */
	private Long warehouseId;

	/**
	 * 备注
	 */
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getShelfType() {
		return shelfType;
	}

	public void setShelfType(String shelfType) {
		this.shelfType = shelfType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getShelfCode() {
		return shelfCode;
	}

	public void setShelfCode(String shelfCode) {
		this.shelfCode = shelfCode;
	}

	public Integer getSeatStart() {
		return seatStart;
	}

	public void setSeatStart(Integer seatStart) {
		this.seatStart = seatStart;
	}

	public Integer getSeatEnd() {
		return seatEnd;
	}

	public void setSeatEnd(Integer seatEnd) {
		this.seatEnd = seatEnd;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getCols() {
		return cols;
	}

	public void setCols(Integer cols) {
		this.cols = cols;
	}

	/**
	 * 根据货架创建货位
	 * 
	 * @return
	 */
	public static List<Seat> createSeatsByShelf(Shelf shelf) {
		List<Seat> seatList = new ArrayList<Seat>();
		// 地面货架按照 货位起始终止生成货位
		if (StringUtil.isEqual(shelf.getShelfType(), TYPE_GROUND)) {
			for (int i = shelf.getSeatStart(); i <= shelf.getSeatEnd(); i++) {
				Seat seat = new Seat();
				seat.setSeatCode(shelf.getShelfCode() + "" + i);
				seat.setShelfCode(shelf.getShelfCode());
				seat.setWarehouseId(shelf.getWarehouseId());
				seatList.add(seat);
			}
		}
		// 立体货架
		if (StringUtil.isEqual(shelf.getShelfType(), TYPE_BUILD)) {
			for (int i = 1; i <= shelf.getRows(); i++) {
				for (int j = 1; j <= shelf.getCols(); j++) {
					Seat seat = new Seat();
					seat.setSeatCode(shelf.getShelfCode() + "" + i + j);
					seat.setShelfCode(shelf.getShelfCode());
					seat.setWarehouseId(shelf.getWarehouseId());
					seatList.add(seat);
				}
			}
		}
		return seatList;
	}
}
