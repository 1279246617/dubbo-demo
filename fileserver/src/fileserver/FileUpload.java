package fileserver;

import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

@WebServlet(urlPatterns = { "/upload" })
public class FileUpload extends HttpServlet implements FileRenamePolicy {

	private static final long serialVersionUID = 1L;

	// 定义限制文件大小：
	private static int maxSize = 1024 * 1024 * 1024;// 1G

	private static String rootFilePath;

	private static SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

	private Long time;

	public FileUpload() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	// 重写HttpServlet的Post方法
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		time = System.currentTimeMillis();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			// 根目录
			if (rootFilePath == null) {
				Properties props = new Properties();
				InputStream inStream = getClass().getResourceAsStream("/config.properties");
				props.load(inStream);
				rootFilePath = props.getProperty("rootFilePath");
			}
			// 根据日期构建文件存放目录
			String dectory = df.format(new Date(time));
			// 要保存文件的绝对路径
			String buildPath = rootFilePath + "/" + dectory + "/";
			// 目标目录不存在的话就自动创建
			File f1 = new File(buildPath);
			if (!f1.exists()) {
				f1.mkdirs();// 建立目录
			}
			FileUpload fu = new FileUpload();

			MultipartRequest multi = new MultipartRequest(request, buildPath, maxSize, "UTF-8", fu);
			Enumeration<?> enums = multi.getFileNames();
			while (enums.hasMoreElements()) {
				String fileName = (String) enums.nextElement();
				File file = multi.getFile(fileName);
				if (file != null) {
					String name = multi.getFilesystemName(fileName);
					String webroot = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
					String fileurl = webroot + "/download?name=" + name;
					out.println(fileurl);
				}
			}
		} catch (Exception e) {
			out.println("Server Exception!");
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

	@Override
	public File rename(File file) {
		if (time == null) {
			time = System.currentTimeMillis();
		}
		int index = file.getName().lastIndexOf(".");
		String postfix = file.getName().substring(index);
		String oldName = file.getName().substring(0, index);
		// 构建新文件名
		String newFileName = oldName + time + postfix;
		return new File(file.getParent(), newFileName);
	}
}
