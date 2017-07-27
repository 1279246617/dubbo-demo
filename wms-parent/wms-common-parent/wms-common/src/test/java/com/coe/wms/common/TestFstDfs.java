package com.coe.wms.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.csource.fastdfs.FileInfo;

import com.coe.wms.common.utils.dfs.FileManager;

public class TestFstDfs {
	
	public static void main6(String[] args) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
	    if (loader == null) {
	      loader = ClassLoader.getSystemClassLoader();
	    }
	     InputStream resourceAsStream = loader.getResourceAsStream("/client_dfs.properties");
	     System.out.println(resourceAsStream);
	}
	
	public static void main(String[] args) {
		String upload = FileManager.upload(new File("E:/test/微信图片_20170724191729.jpg"));
		System.out.println(upload);

	}

	public static void main5(String[] args) {
		String testName = "http://asfasfassfasf/asfasf.asfjpg";
		int lastIndexOf = testName.lastIndexOf(".");
		if (lastIndexOf >= 0) {
			String substring = testName.substring(lastIndexOf + 1, testName.length());
			System.out.println(substring);
		} else {
			System.out.println("null");
		}
	}

	public static void main3(String[] args) throws Exception {
		byte[] download = FileManager.download("http://192.168.80.39/group1/M00/00/00/wKhQJ1l1YFSAU29JAAAKgGsYKbE302.jpg");
		FileOutputStream fileOutputStream = new FileOutputStream("E:/testJava/test0724.jpg");
		fileOutputStream.write(download);
		fileOutputStream.flush();
		fileOutputStream.close();
	}
}
