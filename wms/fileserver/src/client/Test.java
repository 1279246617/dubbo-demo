package client;

public class Test {

	public static void main(String[] args) {

		// 接收文件的服务器地址
		String serverUrl = "http://47.90.76.15:7000/fileserver/upload";

		String fileNameAndPath = "F:\\Lodop6.208_CLodop2.068\\CLodop_Setup_for_Win32NT.exe";
		try {
			System.out.println(FileServerClient.fileUpload(serverUrl, fileNameAndPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
