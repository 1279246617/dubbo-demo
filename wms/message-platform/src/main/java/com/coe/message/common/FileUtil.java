package com.coe.message.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class FileUtil {

	/**单文件上传*/
	public static File oneFileUpload(MultipartFile multipartFile, String targetDir) {
		try {
			// 获取文件名
			if (multipartFile != null) {
				String fileName = multipartFile.getOriginalFilename();
				// 文件夹不存在则建立
				File dirFile = new File(targetDir);
				if (!dirFile.exists()) {
					dirFile.mkdirs();
				}
				File newFile = new File(targetDir, fileName);
				FileOutputStream out = new FileOutputStream(newFile);
				InputStream in = multipartFile.getInputStream();
				IOUtils.copy(in, out);
				return newFile;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**多文件上传*/
	public static boolean moreFileUpload(MultipartHttpServletRequest request, String targetDir) {
		try {
			// 获取上传文件集合
			Map<String, MultipartFile> fileMap = request.getFileMap();
			// int fileSize = fileMap.size();
			Set<String> fileSet = fileMap.keySet();
			for (String uploadFileName : fileSet) {
				MultipartFile multipartFile = fileMap.get(uploadFileName);
				String fileName = multipartFile.getOriginalFilename();
				if (fileName != null && fileName != "") {
					InputStream in = multipartFile.getInputStream();
					// 文件夹不存在则建立
					File dirFile = new File(targetDir);
					if (!dirFile.exists()) {
						dirFile.mkdirs();
					}
					File newFile = new File(targetDir, fileName);
					FileOutputStream out = new FileOutputStream(newFile);
					IOUtils.copy(in, out);
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**文件下载
	 * @throws UnsupportedEncodingException */
	public static void downloadFile(HttpServletResponse response, HttpServletRequest request, File file) throws Exception {
		String fileName = file.getName();
		// 将正确能识别的中文文件名转成ISO8859-1编码才能正确下载
		String fileNameEncode = new String(fileName.getBytes(), "ISO8859-1");
		// String sourceDir = request.getSession().getServletContext().getRealPath("")+"\\"+fileName;
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileNameEncode);
		FileInputStream fileIn = new FileInputStream(file);
		OutputStream out = response.getOutputStream();
		IOUtils.copy(fileIn, out);
	}

	/**压缩文件夹，文件夹嵌套*/
	public static File compressExe1(String baseDir) {
		File srcdir = new File(baseDir);
		File zipFile = new File(baseDir + ".zip");
		if (!srcdir.exists()) {
			throw new RuntimeException(srcdir + "不存在！");
		}
		Project prj = new Project();
		Zip zip = new Zip();
		zip.setProject(prj);
		zip.setDestFile(zipFile);
		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		fileSet.setDir(srcdir);
		// fileSet.setIncludes("**/*.java"); //包括哪些文件或文件夹 eg:zip.setIncludes("*.java");
		// fileSet.setExcludes(...); //排除哪些文件或文件夹
		zip.addFileset(fileSet);
		zip.execute();
		return zipFile;
	}

	/**
	 * 将文件夹打包成zip格式(文件夹下是文件)
	 */
	public static File compressExe(String baseDir) throws Exception {
		// 文件夹
		File dirFile = new File(baseDir);
		// 创建zip文件
		File zipFile = new File(baseDir + ".zip");
		File[] fileArray = dirFile.listFiles();
		int fileLength = fileArray.length;
		FileOutputStream fos = new FileOutputStream(zipFile);
		ZipOutputStream zipOs = new ZipOutputStream(fos);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		byte[] bufs = new byte[1024 * 10];
		for (int i = 0; i < fileLength; i++) {
			// 创建ZIP实体，并添加进压缩包
			ZipEntry zipEntry = new ZipEntry(fileArray[i].getName());
			zipOs.putNextEntry(zipEntry);
			// 读取待压缩的文件并写进压缩包里
			fis = new FileInputStream(fileArray[i]);
			bis = new BufferedInputStream(fis, 1024 * 10);
			int read = 0;
			while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
				zipOs.write(bufs, 0, read);
			}
			zipOs.flush();
		}
		bis.close();
		fis.close();
		zipOs.close();
		fos.close();
		return zipFile;
	}

	/**解压zip文件*/
	public static List<File> decompress(File file) throws IOException {
		String absolutePath = file.getAbsolutePath();
		@SuppressWarnings("resource")
		ZipFile zipFile = new ZipFile(file);
		@SuppressWarnings("resource")
		ZipInputStream zipInput = new ZipInputStream(new FileInputStream(file));
		ZipEntry entry = null;
		List<File> fileList = new ArrayList<File>();
		while ((entry = zipInput.getNextEntry()) != null) {
			String fileName = entry.getName();
			String saveDir = absolutePath.substring(0, absolutePath.indexOf("."));
			File dirFile = new File(saveDir);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			File saveFile = new File(saveDir + File.separator + fileName);
			InputStream inPut = zipFile.getInputStream(entry);
			OutputStream outPut = new FileOutputStream(saveFile);
			IOUtils.copy(inPut, outPut);
			fileList.add(saveFile);
		}
		return fileList;
	}

	// 判断文件是否是图片,gif除外
	public static boolean judgeImgFile(String fileName) {
		if (StringUtils.isEmpty(fileName)) {
			return false;
		}
		String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length()).toUpperCase();
		if (".BMP".equals(fileType) || ".PNG".equals(fileType) || ".JPEG".equals(fileType) || ".JPG".equals(fileType)) {
			return true;
		} else {
			return false;
		}
	}
}
