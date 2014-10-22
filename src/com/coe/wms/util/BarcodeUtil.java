package com.coe.wms.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.encode.EAN13Encoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.jbarcode.util.ImageUtil;

import sun.misc.BASE64Encoder;

public class BarcodeUtil {

	private static Logger logger = Logger.getLogger(BarcodeUtil.class);

	public static String createCode128(String barcodeText, boolean isShowBarcodeText, Double height) {
		JBarcode jbcode = new JBarcode(EAN13Encoder.getInstance(), WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());
		jbcode.setEncoder(Code128Encoder.getInstance());
		jbcode.setTextPainter(BaseLineTextPainter.getInstance());
		jbcode.setCheckDigit(false);
		jbcode.setShowCheckDigit(false);
		jbcode.setShowText(isShowBarcodeText);
		jbcode.setBarHeight(height);

		ByteArrayOutputStream bos = null;
		try {
			jbcode.setWideRatio(3d);
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
}
