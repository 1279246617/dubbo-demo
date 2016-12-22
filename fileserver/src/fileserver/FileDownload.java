package fileserver;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/download" })
public class FileDownload extends HttpServlet {

	private static final long serialVersionUID = -2142723162865292420L;

	private static String rootFilePath;

	private static SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		if (name == null || name.trim().equals("")) {
			return;
		}

		String timeStr = null;
		String oldName = null;
		try {
			timeStr = name.substring(name.lastIndexOf(".") - 13, name.lastIndexOf("."));
			oldName = name.replace(timeStr, "");
			if (!timeStr.matches("\\d{13}")) {
				return;
			}
		} catch (Exception e) {
			return;
		}
		// 根目录
		if (rootFilePath == null) {
			Properties props = new Properties();
			InputStream inStream = getClass().getResourceAsStream("/config.properties");
			props.load(inStream);
			rootFilePath = props.getProperty("rootFilePath");
		}
		// 根据日期构建文件存放目录
		String dectory = df.format(new Date(Long.valueOf(timeStr)));
		// 要保存文件的绝对路径
		String buildPath = rootFilePath + "/" + dectory + "/";
		File file = new File(buildPath + name);
		if (!file.exists()) {
			return;
		}
		FileInputStream is = new FileInputStream(file);
		ServletOutputStream os = response.getOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(os);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + oldName + "\"");
		byte[] len = new byte[1024];
		int read = 0;
		while ((read = is.read(len)) != -1) {
			bos.write(len, 0, read);
		}
		bos.flush();
		bos.close();
		is.close();
	}
}