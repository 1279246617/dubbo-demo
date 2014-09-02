package com.coe.wms.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * FileUtil. Simple file operation class.
 * 
 * @author BeanSoft
 */
public class FileUtil {
	/**
	 * The buffer.
	 */
	protected static byte buf[] = new byte[1024];

	/**
	 * 创建路径
	 * 
	 * @param path
	 * @return
	 */
	public static boolean mkdirs(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				f.mkdirs();
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Read content from local file. FIXME How to judge UTF-8 and GBK, the
	 * correct code should be: FileReader fr = new FileReader(new
	 * InputStreamReader(fileName, "ENCODING")); Might let the user select the
	 * encoding would be a better idea. While reading UTF-8 files, the content
	 * is bad when saved out.
	 * 
	 * @param fileName
	 *            - local file name to read
	 * @return
	 * @throws Exception
	 */
	public static String readFileAsString(String fileName) throws Exception {
		String content = new String(readFileBinary(fileName));

		return content;
	}

	/**
	 * 读取文件并返回为给定字符集的字符串.
	 * 
	 * @param fileName
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String readFileAsString(String fileName, String encoding) throws Exception {
		String content = new String(readFileBinary(fileName), encoding);

		return content;
	}

	/**
	 * 读取文件并返回为给定字符集的字符串.
	 * 
	 * @param fileName
	 * @param encoding
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String readFileAsString(InputStream in) throws IOException {
		String content = new String(readFileBinary(in));
		return content;
	}

	/**
	 * Read content from local file to binary byte array.
	 * 
	 * @param fileName
	 *            - local file name to read
	 * @return
	 * @throws Exception
	 */
	public static byte[] readFileBinary(String fileName) throws Exception {
		FileInputStream fin = new FileInputStream(fileName);

		return readFileBinary(fin);
	}

	/**
	 * 从输入流读取数据为二进制字节数组.
	 * 
	 * @param streamIn
	 * @return
	 * @throws IOException
	 */
	public static byte[] readFileBinary(InputStream streamIn) throws IOException {
		BufferedInputStream in = new BufferedInputStream(streamIn);

		ByteArrayOutputStream out = new ByteArrayOutputStream(10240);

		int len;
		while ((len = in.read(buf)) >= 0)
			out.write(buf, 0, len);
		in.close();

		return out.toByteArray();
	}

	/**
	 * Write string content to local file.
	 * 
	 * @param fileName
	 *            - local file name will write to
	 * @param content
	 *            String text
	 * @return true if success
	 * @throws IOException
	 */
	public static boolean writeFileString(String fileName, String content) throws IOException {
		FileWriter fout = new FileWriter(fileName);
		fout.write(content);
		fout.close();
		return true;
	}

	/**
	 * Write string content to local file using given character encoding.
	 * 
	 * @param fileName
	 *            - local file name will write to
	 * @param content
	 *            String text
	 * @param encoding
	 *            the encoding
	 * @return true if success
	 * @throws IOException
	 */
	public static boolean writeFileString(String fileName, String content, String encoding) throws IOException {
		OutputStreamWriter fout = new OutputStreamWriter(new FileOutputStream(fileName), encoding);

		fout.write(content);
		fout.close();
		return true;
	}

	/**
	 * Write binary byte array to local file.
	 * 
	 * @param fileName
	 *            - local file name will write to
	 * @param content
	 *            binary byte array
	 * @return true if success
	 * @throws IOException
	 */
	public static boolean writeFileBinary(String fileName, byte[] content) throws IOException {
		if (content == null || StringUtil.isNull(fileName)) {
			return false;
		}
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(fileName);
			fout.write(content);
		} catch (IOException e) {
			throw e;
		} finally {
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
				}
			}
		}
		return true;
	}

	/**
	 * 检查文件名是否合法.文件名字不能包含字符\/:*?"<>|
	 * 
	 * @param fileName文件名
	 *            ,不包含路径
	 * @return boolean is valid file name
	 */
	public static boolean isValidFileName(String fileName) {
		boolean isValid = true;
		String errChar = "\\/:*?\"<>|"; //
		if (fileName == null || fileName.length() == 0) {
			isValid = false;
		} else {
			for (int i = 0; i < errChar.length(); i++) {
				if (fileName.indexOf(errChar.charAt(i)) != -1) {
					isValid = false;
					break;
				}
			}
		}
		return isValid;
	}

	/**
	 * 把非法文件名转换为合法文件名.
	 * 
	 * @param fileName
	 * @return
	 */
	public static String replaceInvalidFileChars(String fileName) {
		StringBuffer out = new StringBuffer();

		for (int i = 0; i < fileName.length(); i++) {
			char ch = fileName.charAt(i);
			// Replace invlid chars: \\/:*?\"<>|
			switch (ch) {
			case '\\':
			case '/':
			case ':':
			case '*':
			case '?':
			case '\"':
			case '<':
			case '>':
			case '|':
				out.append('_');
				break;
			default:
				out.append(ch);
			}
		}

		return out.toString();
	}

	/**
	 * Convert a given file name to a URL(URI) string.
	 * 
	 * @param fileName
	 *            - the file to parse
	 * @return - URL string
	 */
	public static String filePathToURL(String fileName) {
		String fileUrl = new File(fileName).toURI().toString();
		return fileUrl;
	}

	/**
	 * Write string content to local file.
	 * 
	 * @param fileName
	 *            - local file name will write to
	 * @param content
	 *            String text
	 * @return true if success
	 * @throws IOException
	 */
	public static boolean appendFileString(String fileName, String content) throws IOException {
		OutputStreamWriter fout = new OutputStreamWriter(new FileOutputStream(fileName, true), "UTF-8");
		fout.write(content);
		fout.close();
		return true;
	}

	public static String writeHSSFWorkbook(HSSFWorkbook workbook, String filePathAndName) throws IOException {
		OutputStream out = null;
		try {
			out = new FileOutputStream(filePathAndName);
			workbook.write(out);
		} catch (IOException e) {
			throw e;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return filePathAndName;
	}

	/**
	 * 解压
	 * 
	 * @param path
	 * @throws Exception
	 */
	public static void unZip(String pathAndName, String savePathAndName) throws Exception {
		int buffer = 2048;
		ZipEntry entry = null;
		int count = -1;
		ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(pathAndName)));
		while ((entry = zis.getNextEntry()) != null) {
			byte data[] = new byte[buffer];
			File f = new File(savePathAndName);
			f.createNewFile();
			FileOutputStream fos = new FileOutputStream(f);
			BufferedOutputStream bos = new BufferedOutputStream(fos, buffer);
			while ((count = zis.read(data, 0, buffer)) != -1) {
				bos.write(data, 0, count);
			}
			bos.flush();
			bos.close();
		}
		zis.close();
	}
}