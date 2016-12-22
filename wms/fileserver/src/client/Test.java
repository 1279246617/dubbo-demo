package client;

public class Test {

	public static void main(String[] args) {

		// 接收文件的服务器地址
		String serverUrl = "http://47.90.76.15:7000/fileserver/upload";

		String fileNameAndPath = "D:\\chrome download\\海淘1号仓.xls";
		try {
			System.out.println(FileServerClient.fileUpload(serverUrl, fileNameAndPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
