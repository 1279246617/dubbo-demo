package com.coe.wms.model.warehouse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	 * 业务类型:仓配
	 */
	public static final String BUSINESS_TYPE_STORAGE = "S";
	/**
	 * 业务类型:直接转运
	 */
	public static final String BUSINESS_TYPE_TRANSPORT_Z = "Z";
	
	/**
	 * 业务类型:集货转运
	 */
	public static final String BUSINESS_TYPE_TRANSPORT_J = "J";
	

	private static final long serialVersionUID = 5963221596346743409L;

	private static Map<String, String> numberToABC = new HashMap<String, String>();

	static {
		numberToABC.put("1", "A");
		numberToABC.put("2", "B");
		numberToABC.put("3", "C");
		numberToABC.put("4", "D");
		numberToABC.put("5", "E");
		numberToABC.put("6", "F");
		numberToABC.put("7", "G");
		numberToABC.put("8", "H");
		numberToABC.put("9", "I");
		numberToABC.put("10", "J");
		numberToABC.put("11", "K");
		numberToABC.put("12", "L");
		numberToABC.put("13", "M");
		numberToABC.put("14", "N");
		numberToABC.put("15", "O");
		numberToABC.put("16", "P");
		numberToABC.put("17", "Q");
		numberToABC.put("18", "R");
		numberToABC.put("19", "S");
		numberToABC.put("20", "T");
		numberToABC.put("21", "U");
		numberToABC.put("22", "V");
		numberToABC.put("23", "W");
		numberToABC.put("24", "X");
		numberToABC.put("25", "Y");
		numberToABC.put("26", "Z");
	}
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
	 * 货架指定业务类型
	 * 
	 * S:storage仓配: T:transport转运
	 */
	private String businessType;
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

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
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
				String str = "" + i;
				if (str.length() == 1) {
					str = "00" + str;
				}
				if (str.length() == 2) {
					str = "0" + str;
				}
				seat.setSeatCode(shelf.getShelfCode() + "" + str);
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

					String strJ = "" + j;// 列数最大2位数
					if (strJ.length() == 1) {
						strJ = "0" + strJ;
					}
					// i 是数字,切换成 ABDEF
					String strI = numberToABC.get("" + i);
					seat.setSeatCode(shelf.getShelfCode() + "" + strI + strJ);
					seat.setShelfCode(shelf.getShelfCode());
					seat.setWarehouseId(shelf.getWarehouseId());
					seatList.add(seat);
				}
			}
		}
		return seatList;
	}
}
