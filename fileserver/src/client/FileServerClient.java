package client;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import sun.swing.StringUIClientPropertyKey;

/**
 * 文件服务器客户端工具类 复制此文件到客户端工程,修改serverUrl 即可使用
 * 
 * @author yechao
 * 
 */
public class FileServerClient {

	/**
	 * 上传本地文件
	 * 
	 * @param fileNameAndPath
	 * @return
	 * @throws Exception
	 */
	public static String fileUpload(String serverUrl, String fileNameAndPath) throws Exception {
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try {
			File file = new File(fileNameAndPath);
			if (!file.exists()) {
				return null;
			}
			fis = new FileInputStream(file);
			bos = new ByteArrayOutputStream(1024);
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			bos.flush();
			return fileUpload(serverUrl, bos.toByteArray(), file.getName());
		} catch (Exception e) {
			throw e;
		} finally {
			if (fis != null) {
				fis.close();
			}
			if (bos != null) {
				bos.close();
			}
		}
	}

	/**
	 * 
	 * @param bytes
	 *            字节数组内容
	 * @param fileName
	 *            文件名称
	 * @return
	 * @throws Exception
	 */
	public static String fileUpload(String serverUrl, byte[] bytes, String fileName) throws Exception {
		String resultSet = null;

		try {
			HttpURLConnection huc = (HttpURLConnection) new URL(serverUrl).openConnection();

			huc.setRequestMethod("POST");// 设置提交方式为post方式
			huc.setDoInput(true);
			huc.setDoOutput(true);// 设置允许output
			huc.setUseCaches(false);// POST不能使用缓存
			// 设置请求头信息
			huc.setRequestProperty("Connection", "Keep-Alive");
			huc.setRequestProperty("Charset", "UTF-8");

			// 设置边界
			String boundary = "----------" + System.currentTimeMillis();
			huc.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

			// 头部：
			StringBuilder sb = new StringBuilder();
			sb.append("--"); // ////////必须多两道线
			sb.append(boundary);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + changeFileName(fileName) + "\"\r\n");
			sb.append("Content-Type: application/octet-stream\r\n\r\n");

			// 获得输出流
			OutputStream out = new DataOutputStream(huc.getOutputStream());
			out.write(sb.toString().getBytes("utf-8"));// 写入header
			// 文件数据部分
			out.write(bytes, 0, bytes.length);// 写入文件数据

			// 结尾部分
			byte[] foot = ("\r\n--" + boundary + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
			out.write(foot);// 写入尾信息

			out.flush();
			out.close();

			// 执行提交后获取执行结果
			BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
			huc.connect();
			String line = null;
			resultSet = br.readLine();

			// 循环按行读取文本流
			while ((line = br.readLine()) != null) {
				resultSet += line + "\r\n";// 此处未加上\r\n
			}
			br.close();
			resultSet = resultSet.trim();
			huc.disconnect();
		} catch (Exception e) {
			throw e;
		}
		return resultSet;
	}

	/**
	 * 改变文件名称
	 * 
	 * @param fileName
	 * @return
	 */
	public static String changeFileName(String fileName) {
		if (!isContainsChinese(fileName)) {
			return fileName;
		}
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		return getStringRandom(10) + "." + fileType;
	}

	/**
	 * 是否包含中文
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isContainsChinese(String str) {
		if (str.length() == str.replaceFirst("[\u4e00-\u9fa5]", "").length()) {
			return false;
		}
		return true;
	}

	/**
	 * 生成随机数字和字母
	 * 
	 * @param length
	 * @return
	 */
	public static String getStringRandom(int length) {
		String val = "";
		Random random = new Random();
		// 参数length，表示生成几位随机数
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (random.nextInt(26) + temp);
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}
}
