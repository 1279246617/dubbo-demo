package com.coe.wms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class POIExcelUtil {

	Logger logger = Logger.getLogger(POIExcelUtil.class);
	/**
	 * 总行数
	 */
	public int totalRows = 0;
	/**
	 * 总列数
	 */
	public int totalCells = 0;

	/**
	 * 根据文件名读取excel文件
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public List<ArrayList<String>> readFile(String fileName) {
		List<ArrayList<String>> dataLst = new ArrayList<ArrayList<String>>();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		// 检查文件是否存在
		File file = new File(fileName);
		if (file == null || !file.exists()) {
			return dataLst;
		}
		// 读取excel
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			// 根据版本选择创建Workbook的方式
			Workbook wb = null;
			if (fileType.equalsIgnoreCase("xls")) {
				wb = new HSSFWorkbook(inputStream);
			} else if (fileType.equalsIgnoreCase("xlsx")) {
				wb = new XSSFWorkbook(inputStream);
			}

			if (wb == null) {
				return dataLst;
			}
			dataLst = readWorkbook(wb);
		} catch (Exception e) {
			logger.error("解析excel异常:" + e, e);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
				logger.error("关闭流异常:" + e, e);
			}
		}
		return dataLst;
	}

	/**
	 * 读取数据
	 * 
	 * @param wb
	 * @return
	 */
	private List<ArrayList<String>> readWorkbook(Workbook wb) {
		List<ArrayList<String>> dataLst = new ArrayList<ArrayList<String>>();
		// 得到第一个shell
		Sheet sheet = wb.getSheetAt(0);
		// 行数
		this.totalRows = sheet.getPhysicalNumberOfRows();
		if (this.totalRows >= 1 && sheet.getRow(0) != null) {
			// 列数
			this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}
		// 循环Excel的行
		for (int r = 0; r < this.totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			ArrayList<String> rowLst = new ArrayList<String>();
			// 循环Excel的列
			for (int cellIndex = 0; cellIndex < this.totalCells; cellIndex++) {
				Cell cell = row.getCell(cellIndex);
				String cellValue = "";
				if (cell == null) {
					rowLst.add(cellValue);
					continue;
				}
				// 处理数字型的,自动去零
				if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
					// 在excel里,日期也是数字,在此要进行判断
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						cellValue = DateUtil.dateConvertString(cell.getDateCellValue(), DateUtil.yyyy_MM_ddHHmmss);
					} else {
						cellValue = StringUtil.getRightStr(cell.getNumericCellValue() + "");
					}
				} else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {// 处理字符串型
					cellValue = cell.getStringCellValue();
				} else if (Cell.CELL_TYPE_BOOLEAN == cell.getCellType()) {// 处理布尔型
					cellValue = cell.getBooleanCellValue() + "";
				} else {// 其它数据类型
					cellValue = cell.toString() + "";
				}
				rowLst.add(cellValue);
			}
			dataLst.add(rowLst);
		}
		return dataLst;
	}
}
