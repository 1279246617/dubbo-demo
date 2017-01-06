package com.coe.message.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**登陆验证码图片工具类*/
public class LoginCodeImageUtil {
	/**生成图片宽度*/
	private static final int WIDTH = 100;
	/**生成图片高度*/
	private static final int HEIGHT = 30;
	/**生成的验证码*/
	private String code;
	/**生成的图片转成的二进制数组*/
//	private byte[] imgByte;
	private BufferedImage bufferedImg;

	public  void generatorCodeImg() throws IOException {
		// 内存中创建一张图片
		bufferedImg = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
		// 获取画笔
		Graphics graphics = bufferedImg.getGraphics();
		// 设置图片背景色
		graphics.setColor(Color.WHITE);
		// 填充图片
		graphics.fillRect(0, 0, WIDTH, HEIGHT);
		// 设置边框
		graphics.setColor(Color.WHITE);
		graphics.drawRect(0, 0, WIDTH - 2, HEIGHT - 2);
		// 画干扰线条
		drawRandomLine(graphics);
		// 写验证码
		code = drawCode((Graphics2D) graphics, 4);
//		ByteArrayOutputStream os = new ByteArrayOutputStream();
//		ImageIO.write(bufferedImg, "jpg", os);
//		imgByte = os.toByteArray();
	}

	/**
	 * 写验证码
	 * @param graphics2D
	 * @param length
	 * @return
	 */
	public String drawCode(Graphics2D graphics2D, int length) {
		StringBuffer code = new StringBuffer();
		// 设置字体属性
		graphics2D.setFont(new Font("宋体", Font.BOLD,20));
		graphics2D.setColor(Color.BLACK);
		int x = 5;
		for (int i = 0; i < length; i++) {
			// 设置字体旋转角度
			int degree = new Random().nextInt() % 30;
			// 正向角度
			graphics2D.rotate(degree * Math.PI / 180, x, 20);
//			String singleLetter = YzmUtil.getCodeForLetter(1);
//			String singleLetter = YzmUtil.getCodeForChinese(1);
			String singleLetter = YzmUtil.getCodeForNumber(1);
			code.append(singleLetter);
			graphics2D.drawString(singleLetter, x, 20);
			// 反向角度
			graphics2D.rotate(-degree * Math.PI / 180, x, 20);
			x += 25;
		}
		return code.toString();
	}

	/**
	 * 画干扰线条
	 * @param graphics
	 */
	public void drawRandomLine(Graphics graphics) {
		// 设置颜色
		graphics.setColor(Color.gray);
		// 设置线条个数并画线
		for (int i = 0; i < 10; i++) {
			int x1 = new Random().nextInt(WIDTH);
			int y1 = new Random().nextInt(HEIGHT);
			int x2 = new Random().nextInt(WIDTH);
			int y2 = new Random().nextInt(HEIGHT);
			graphics.drawLine(x1, y1, x2, y2);
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

//	public byte[] getImgByte() {
//		return imgByte;
//	}
//
//	public void setImgByte(byte[] imgByte) {
//		this.imgByte = imgByte;
//	}

	public BufferedImage getBufferedImg() {
		return bufferedImg;
	}

	public void setBufferedImg(BufferedImage bufferedImg) {
		this.bufferedImg = bufferedImg;
	}

}
