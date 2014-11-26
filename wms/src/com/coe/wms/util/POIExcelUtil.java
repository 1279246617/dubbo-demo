package com.coe.wms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hsmf.datatypes.Types;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.examples.CellTypes;
import org.apache.poi.hssf.util.HSSFColor;
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

	/**
	 * 创建excel
	 * 
	 * @param sheetTitle
	 * @param headers
	 * @param rows
	 * @param filePathAndName
	 * @return
	 * @throws IOException
	 */
	public static String createExcel(String sheetTitle, String[] headers, List<String[]> rows, String filePathAndName) throws IOException {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(sheetTitle);
		// 设置表格默认列宽15个字节
		sheet.setDefaultColumnWidth(15);

		// 生成表头样式
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.BRIGHT_GREEN.index);// 设置这些样式--表头
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 11);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);

		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.WHITE.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		HSSFCellStyle style3 = workbook.createCellStyle();
		style3.setFillForegroundColor(HSSFColor.WHITE.index);
		style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font3 = workbook.createFont();
		font3.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style3.setFont(font3);
		HSSFDataFormat format = workbook.createDataFormat();
		style3.setDataFormat(format.getFormat("@"));

		// 生成表头内容
		HSSFRow hssfRow = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = hssfRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(headers[i]);
		}

		// 生成表头以外的内容
		int rowIndex = 1;
		for (String[] row : rows) {
			hssfRow = sheet.createRow(rowIndex);
			for (int i = 0; i < row.length; i++) {
				HSSFCell cell = hssfRow.createCell(i);
				if (NumberUtil.isDecimal(row[i]) && row[i].indexOf(".") > 0) {
					cell.setCellStyle(style2);
					cell.setCellValue(Double.valueOf(row[i]));
				} else if (NumberUtil.isNumberic(row[i]) && row[i].length() < 10) {// 32位机器,整型最大4294967296
					cell.setCellStyle(style2);
					cell.setCellValue(Integer.valueOf(row[i]));
				} else {
					cell.setCellStyle(style3);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row[i]);
				}
			}
			rowIndex++;
		}
		return FileUtil.writeHSSFWorkbook(workbook, filePathAndName);
	}
}
