package com.coe.wms.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.encode.Code39Encoder;
import org.jbarcode.encode.EAN13Encoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.jbarcode.util.ImageUtil;

import sun.misc.BASE64Encoder;

import com.bokai.barcodes.Barcode;
import com.bokai.barcodes.BarcodeExtraTextPosition;
import com.bokai.barcodes.BarcodeOrientation;
import com.bokai.barcodes.BarcodeTextPosition;
import com.bokai.barcodes.CheckCharShowMode;

public class BarcodeUtil {

	private static Logger logger = Logger.getLogger(BarcodeUtil.class);

	public static String createCode39(String barcodeText, boolean isShowBarcodeText, Double height, Double xd) {
		JBarcode jbcode = new JBarcode(EAN13Encoder.getInstance(), WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());
		jbcode.setEncoder(Code39Encoder.getInstance());
		jbcode.setTextPainter(BaseLineTextPainter.getInstance());
		jbcode.setCheckDigit(false);
		jbcode.setShowCheckDigit(false);
		jbcode.setShowText(isShowBarcodeText);
		jbcode.setBarHeight(height);
		ByteArrayOutputStream bos = null;
		try {
			if (xd != null) {
				jbcode.setXDimension(xd);
			}
			// jbcode.setWideRatio(3d);
			BufferedImage img = jbcode.createBarcode(barcodeText.toUpperCase());
			bos = new ByteArrayOutputStream();
			ImageUtil.encodeAndWrite(img, ImageUtil.PNG, bos, 96, 96);
			// 内存 图片
			byte[] data = bos.toByteArray();
			// 转成字符串,img :src:data
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(data);
		} catch (Exception e) {
			logger.error("生成条码 barcodeText:" + barcodeText + "出现异常:" + e, e);
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static String createCode128(String barcodeText, boolean isShowBarcodeText, Double height, Double xd, Double wideRatio, Double barcodeScale) {
		JBarcode jbcode = new JBarcode(EAN13Encoder.getInstance(), WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());
		jbcode.setEncoder(Code128Encoder.getInstance());
		jbcode.setTextPainter(BaseLineTextPainter.getInstance());
		jbcode.setCheckDigit(false);
		jbcode.setShowCheckDigit(false);
		jbcode.setShowText(isShowBarcodeText);
		jbcode.setBarHeight(height);
		ByteArrayOutputStream bos = null;
		try {
			if (wideRatio == null) {
				wideRatio = 3d;
			}
			jbcode.setWideRatio(wideRatio);
			if (xd != null) {
				jbcode.setXDimension(xd);
			}
			BufferedImage img = jbcode.createBarcode(barcodeText.toUpperCase());
			if (barcodeScale != null) {// 放大图片
				img = ImageUtils.zoomInImage(img, barcodeScale);
			}
			bos = new ByteArrayOutputStream();
			ImageUtil.encodeAndWrite(img, ImageUtil.PNG, bos, 96, 96);
			// 内存 图片
			byte[] data = bos.toByteArray();
			// 转成字符串,img :src:data
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(data);
		} catch (Exception e) {
			logger.error("生成条码 barcodeText:" + barcodeText + "出现异常:" + e, e);
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param barcodeText
	 * @param isShowBarcodeText
	 * @param height
	 * @return
	 */
	public static String createCode128C(String barcodeText, boolean isShowBarcodeText, Double height) {
		ByteArrayOutputStream bos = null;
		try {
			Barcode _barcode = new Barcode(Barcode.BCT_CODE128C);
			_barcode.setData(barcodeText);
			if (isShowBarcodeText) {
				_barcode.setTextAlign(BarcodeTextPosition.below);
			} else {
				_barcode.setTextAlign(BarcodeTextPosition.notShown);
			}
			_barcode.setExtraTextPosition(BarcodeExtraTextPosition.above);
			_barcode.setCheckCharShowMode(CheckCharShowMode.hide);
			_barcode.setOrientation(BarcodeOrientation.bottomFacing);
			// _barcode.setBarTypeWidth(1, 3);
			int width = _barcode.getModuleCount();
			int barcodeHeight = height.intValue();
			int scale = 1;
			bos = new ByteArrayOutputStream();
			_barcode.makeSimpleImage(Barcode.BARCODE_PNG, width, barcodeHeight, false, scale, null, bos);
			byte[] data = bos.toByteArray();
			// 转成字符串,img :src:data
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(data);
		} catch (Exception e) {
			logger.error("生成条码 barcodeText:" + barcodeText + "出现异常:" + e, e);
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param barcodeText
	 * @param isShowBarcodeText
	 * @param height
	 * @return
	 */
	public static String createCode128Auto(String barcodeText, boolean isShowBarcodeText, Double height) {
		ByteArrayOutputStream bos = null;
		try {
			Barcode barcode = new Barcode(Barcode.BCT_CODE128AUTO);
			barcode.setData(barcodeText);
			if (isShowBarcodeText) {
				barcode.setTextAlign(BarcodeTextPosition.below);
			} else {
				barcode.setTextAlign(BarcodeTextPosition.notShown);
			}
			barcode.setExtraTextPosition(BarcodeExtraTextPosition.above);
			barcode.setCheckCharShowMode(CheckCharShowMode.hide);
			barcode.setOrientation(BarcodeOrientation.bottomFacing);
			// _barcode.setBarTypeWidth(1, 3);
			int width = barcode.getModuleCount();
			int barcodeHeight = height.intValue();
			int scale = 1;
			bos = new ByteArrayOutputStream();
			barcode.makeSimpleImage(Barcode.BARCODE_PNG, width, barcodeHeight, false, scale, null, bos);
			byte[] data = bos.toByteArray();
			// 转成字符串,img :src:data
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(data);
		} catch (Exception e) {
			logger.error("生成条码 barcodeText:" + barcodeText + "出现异常:" + e, e);
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
