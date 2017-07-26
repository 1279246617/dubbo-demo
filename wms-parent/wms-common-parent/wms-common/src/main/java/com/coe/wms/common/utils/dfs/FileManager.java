package com.coe.wms.common.utils.dfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.coe.wms.common.utils.StringUtil;

public class FileManager implements FileManagerConfig {
	private static Logger logger = Logger.getLogger(FileManager.class);
	private static final long serialVersionUID = -9042217554595446459L;
	private static TrackerClient trackerClient;
	private static TrackerServer trackerServer;
	private static StorageServer storageServer;
	private static StorageClient storageClient;

	static {
		try {
			String fdfsClientConfigFilePath = CLIENT_CONFIG_FILE;
			ClientGlobal.init(fdfsClientConfigFilePath);

			trackerClient = new TrackerClient();
			trackerServer = trackerClient.getConnection();

			storageClient = new StorageClient(trackerServer, storageServer);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <strong>方法概要： 文件上传</strong> <br>
	 * <strong>创建时间： 2016-9-26 上午10:26:11</strong> <br>
	 * 
	 * @param FastDFSFile
	 *            file
	 * @return fileAbsolutePath
	 * @author Wang Liang
	 */
	public static String upload(MultipartFile attach) {
		String[] uploadResults = null;
		String ext = attach.getOriginalFilename().substring(attach.getOriginalFilename().lastIndexOf(".") + 1);
		try {
			FastDFSFile file = new FastDFSFile(attach.getBytes(), ext);
			NameValuePair[] meta_list = new NameValuePair[4];
			meta_list[0] = new NameValuePair("fileName", attach.getOriginalFilename());
			meta_list[1] = new NameValuePair("fileLength", String.valueOf(attach.getSize()));
			meta_list[2] = new NameValuePair("fileExt", ext);
			meta_list[3] = new NameValuePair("fileAuthor", "WangLiang");
			uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String groupName = uploadResults[0];
		String remoteFileName = uploadResults[1];

		String fileAbsolutePath = PROTOCOL + TRACKER_NGNIX_ADDR
		// + trackerServer.getInetSocketAddress().getHostName()
		// + SEPARATOR + TRACKER_NGNIX_PORT
				+ SEPARATOR + groupName + SEPARATOR + remoteFileName;
		return fileAbsolutePath;
	}

	/**
	 * 文件上传
	 * 
	 * @param fileUpload
	 * @return
	 */
	public static String upload(File fileUpload) {
		byte[] bytes = null;
		FileInputStream fileInputStream = null;
		try {
			// 创建输入流
			fileInputStream = new FileInputStream(fileUpload);
			// 把流数据写进字节里面
			bytes = new byte[fileInputStream.available()];
			fileInputStream.read(bytes);
		} catch (Exception e) {
			return null;
		} finally {
			try {
				fileInputStream.close();
			} catch (Exception e2) {
			}

		}

		String[] uploadResults = null;
		String ext = fileUpload.getName().substring(fileUpload.getName().lastIndexOf(".") + 1);
		try {
			FastDFSFile file = new FastDFSFile(bytes, ext);
			NameValuePair[] meta_list = new NameValuePair[4];
			meta_list[0] = new NameValuePair("fileName", fileUpload.getName());
			meta_list[1] = new NameValuePair("fileLength", String.valueOf(bytes.length));
			meta_list[2] = new NameValuePair("fileExt", ext);
			meta_list[3] = new NameValuePair("fileAuthor", "WangLiang");
			uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String groupName = uploadResults[0];
		String remoteFileName = uploadResults[1];

		String fileAbsolutePath = PROTOCOL + TRACKER_NGNIX_ADDR
		// + trackerServer.getInetSocketAddress().getHostName()
		// + SEPARATOR + TRACKER_NGNIX_PORT
				+ SEPARATOR + groupName + SEPARATOR + remoteFileName;
		return fileAbsolutePath;

	}

	/**
	 * <strong>方法概要： 文件下载</strong> <br>
	 * <strong>创建时间： 2016-9-26 上午10:28:21</strong> <br>
	 * 
	 * @param String
	 *            groupName
	 * @param String
	 *            remoteFileName
	 * @return returned value comment here
	 * @author Wang Liang
	 */
	public static ResponseEntity<byte[]> download(String filePath, String fileName) {
		byte[] content = null;
		HttpHeaders headers = new HttpHeaders();
		String substr = filePath.substring(filePath.indexOf("group"));
		String groupName = substr.split("/")[0];
		String remoteFileName = substr.substring(substr.indexOf("/") + 1);
		String specFileName = substr.substring(substr.indexOf("."));
		try {
			if (StringUtil.isEmpty(fileName)) {
				fileName = UUID.randomUUID() + specFileName;
			} else {
				fileName = fileName + specFileName;
			}
			content = storageClient.download_file(groupName, remoteFileName);
			headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("UTF-8"), "iso-8859-1"));
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<byte[]>(content, headers, HttpStatus.CREATED);
	}

	public static FileInfo getFile(String filePath) {
		String substr = filePath.substring(filePath.indexOf("group"));
		String groupName = substr.split("/")[0];
		String remoteFileName = substr.substring(substr.indexOf("/") + 1);
		try {
			return storageClient.get_file_info(groupName, remoteFileName);
		} catch (IOException e) {
			logger.error("IO Exception: Get File from Fast DFS failed", e);
		} catch (Exception e) {
			logger.error("Non IO Exception: Get File from Fast DFS failed", e);
		}
		return null;
	}

	/**
	 * 文件下载
	 * 
	 * @param filePath
	 * @return
	 */
	public static byte[] download(String filePath) {
		String substr = filePath.substring(filePath.indexOf("group"));
		String groupName = substr.split("/")[0];
		String remoteFileName = substr.substring(substr.indexOf("/") + 1);
		byte[] content = null;
		try {
			content = storageClient.download_file(groupName, remoteFileName);
		} catch (Exception e) {
			return null;
		}
		return content;

	}

	/**
	 * 文件下载
	 * 
	 * @param filePath
	 *            nginx地址
	 * @param realPath
	 *            本地临时地址
	 * @return
	 */
	public static boolean downloadToLocalPath(String filePath, String realPath) {
		// 如果获取文件为null
		byte[] fileByte = download(filePath);
		if (fileByte == null) {
			return false;
		}
		// 如果不为空 保存
		FileOutputStream fileOutputStream = null;
		try {
			// 把文件保存到本地
			fileOutputStream = new FileOutputStream(realPath);
			fileOutputStream.write(fileByte);
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (Exception e) {
			// 保存失败
			return false;
		} finally {
			try {
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * 获取文件名称后缀
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileNameSuffix(String fileName) {
		if (fileName == null) {
			return null;
		}
		int lastIndexOf = fileName.lastIndexOf(".");
		if (lastIndexOf >= 0) {
			return fileName.substring(lastIndexOf + 1, fileName.length());
		} else {
			return null;
		}
	}

}
