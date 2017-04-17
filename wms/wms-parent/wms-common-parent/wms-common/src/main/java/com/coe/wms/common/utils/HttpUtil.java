package com.coe.wms.common.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.coe.wms.common.constants.Charsets;
import com.coe.wms.common.constants.HttpConnectConstant;

/**
 * httpclient4 工具类
 * 
 * @author sunkey
 * @date Mar 15, 2013 6:35:11 PM
 * @version 1.0.0
 */
public class HttpUtil {

	/**
	 * post请求
	 * 
	 * @param url
	 * @param parames
	 * @return
	 * @throws IOException
	 */
	public static String post(String url, Map<String, String> parames) throws IOException {
		CloseableHttpClient http = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		try {
			List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
			Set<String> keySet = parames.keySet();
			for (String key : keySet) {
				String value = parames.get(key);
				nvps.add(new BasicNameValuePair(key, value));
			}
			post.setEntity(new UrlEncodedFormEntity(nvps, Charsets.UTF_8));
			HttpResponse httpResponse = http.execute(post);
			return EntityUtils.toString(httpResponse.getEntity());
		} catch (IOException e) {
			throw e;
		} finally {
			http.close();
		}
	}

	/**
	 * 发送post请求
	 * 
	 * @param urlPath
	 * @param basicNameValuePairs
	 *            参数
	 * @return 转换成字符中返回
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws Exception
	 */
	public static String postRequest(String urlPath, List<BasicNameValuePair> basicNameValuePairs) throws ClientProtocolException, IOException {

		RequestConfig config = RequestConfig.custom().setSocketTimeout(HttpConnectConstant.TIMEOUT)
											.setConnectTimeout(HttpConnectConstant.TIMEOUT)
											.setConnectionRequestTimeout(HttpConnectConstant.TIMEOUT).build();
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
		HttpPost httpPost = new HttpPost(urlPath);
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(basicNameValuePairs, Charsets.UTF_8);
		httpPost.setEntity(entity);

		ResponseHandler<String> handler = new ResponseHandler<String>() {
			public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
				HttpEntity httpEntity = null;
				httpEntity = response.getEntity();

				if (httpEntity != null) {
					return EntityUtils.toString(httpEntity);
				} else {
					return null;
				}
			}
		};

		String result = "";
		try {
			result = httpClient.execute(httpPost, handler);
		} catch (HttpHostConnectException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} finally {
			abortRequest(httpPost);
			httpClient.close();
		}
		return result;

	}

	/**
	 * httpclient4发送http请求
	 * 
	 * @param urlPath
	 *            例如：http://10.1.1.1:8080/test?aa=1&bb=2
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String postRequest(String urlPath) throws ClientProtocolException, IOException {
		if (urlPath.indexOf("?") == -1) {
			throw new IllegalArgumentException("URL路径必须带参数");
		}

		String queryData = urlPath.substring(urlPath.indexOf("?") + 1);
		List<BasicNameValuePair> list = getBasicNameValuePairs(queryData);
		return postRequest(urlPath, list);
	}

	public static void abortRequest(final HttpRequestBase hrb) {
		if (hrb != null && hrb.isAborted()) {
			hrb.abort();
		}
	}

	/**
	 * 为httpClient包装SSL
	 * 
	 * @param base
	 * @return
	 */
	public static HttpClient wrapSSLClient() {

		LayeredConnectionSocketFactory sslsf = null;

		RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();

		PlainConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
		registryBuilder.register("http", plainsf);

		try {

			// Trust own CA and all self-signed certs
			SSLContext sslcontext = SSLContext.getInstance("TLS");

			HostnameVerifier allowAllHostnameVerifier = NoopHostnameVerifier.INSTANCE;
			sslsf = new SSLConnectionSocketFactory(sslcontext, allowAllHostnameVerifier);

			registryBuilder.register("https", sslsf);

		} catch (Throwable e) {
			e.printStackTrace();
		}

		Registry<ConnectionSocketFactory> r = registryBuilder.build();

		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(r);
		connManager.setMaxTotal(100);// 连接池最大并发连接数
		connManager.setDefaultMaxPerRoute(100);// 单路由最大并发数
		// 请求重试处理
		HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				if (executionCount >= 5) {// 如果已经重试了5次，就放弃
					return false;
				}
				if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
					return true;
				}
				if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
					return false;
				}
				if (exception instanceof InterruptedIOException) {// 超时
					return false;
				}
				if (exception instanceof UnknownHostException) {// 目标服务器不可达
					return false;
				}
				if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
					return false;
				}
				if (exception instanceof SSLException) {// SSL握手异常
					return false;
				}

				HttpClientContext clientContext = HttpClientContext.adapt(context);
				HttpRequest request = clientContext.getRequest();
				// 如果请求是幂等的，就再次尝试
				if (!(request instanceof HttpEntityEnclosingRequest)) {
					return true;
				}
				return false;
			}
		};
		HttpClient base = HttpClients.custom().setConnectionManager(connManager).setRetryHandler(httpRequestRetryHandler).build();
		return base;

	}

	/**
	 * 得到httpGet的response,设置cookie策略
	 * 
	 * @param httpClient
	 * @param url
	 *            请求的url
	 * @param timeout
	 *            超时时间
	 * @return
	 */
	public static HttpResponse getGetResponse(final String url, int timeout) {

		RequestConfig config = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).setConnectionRequestTimeout(timeout)
				.setCookieSpec(CookieSpecs.DEFAULT).build();
		HttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
		HttpGet httpget = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(httpget);

			if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode()) {
			}
			return response;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 发送post请求
	 * 
	 * @param httpClient
	 * @param strUrl
	 *            http路径
	 * @param basicNameValuePairs
	 *            参数
	 * @return 301-304返回重定向url,其它返回字符串,没有数据返回null
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String postRquest(final String strUrl, List<BasicNameValuePair> basicNameValuePairs) throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(strUrl);
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(basicNameValuePairs, Charsets.UTF_8);
		httpPost.setEntity(entity);

		ResponseHandler<String> handler = getResponseHandler();

		String strResult = httpClient.execute(httpPost, handler);
		return strResult;

	}

	/**
	 * 发送post请求
	 * 
	 * @param strUrl
	 *            http路径
	 * @param data
	 *            参数
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String postRquest(final String strUrl, String data) throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(strUrl);
		org.apache.http.entity.StringEntity entity = new org.apache.http.entity.StringEntity(data);
		httpPost.setEntity(entity);

		ResponseHandler<String> handler = getResponseHandler();

		String strResult = httpClient.execute(httpPost, handler);
		return strResult;

	}

	private static ResponseHandler<String> getResponseHandler() {
		ResponseHandler<String> handler = new ResponseHandler<String>() {
			public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
				HttpEntity httpEntity = response.getEntity();

				String strReslut = null;
				int statuscode = response.getStatusLine().getStatusCode();
				if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY) || (statuscode == HttpStatus.SC_MOVED_PERMANENTLY)
						|| (statuscode == HttpStatus.SC_SEE_OTHER) || (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) {

					Header locationHeader = response.getFirstHeader("Location");
					if (locationHeader != null) {
						strReslut = locationHeader.getValue();
					}
				} else if (httpEntity != null) {
					strReslut = EntityUtils.toString(httpEntity, Charsets.UTF_8);
				}
				return strReslut;

			}
		};

		return handler;
	}

	/**
	 * 封装post请求参数
	 * 
	 * @param strParamater
	 *            参数以&号分开， eg: aaa=111&bbb=222
	 * @return 参数列表
	 */
	public static List<BasicNameValuePair> getBasicNameValuePairs(String strParamater) {
		List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
		String arr[] = strParamater.split("&");
		for (String str : arr) {
			String strArg[] = str.split("=");
			if (strArg.length < 2) {
				BasicNameValuePair basicNameValuePair = new BasicNameValuePair(strArg[0], "");
				list.add(basicNameValuePair);
			} else {
				BasicNameValuePair basicNameValuePair = new BasicNameValuePair(strArg[0], strArg[1]);
				list.add(basicNameValuePair);
			}
		}
		return list;
	}

	/**
	 * HttpURLConnection 請求
	 * 
	 * @param url
	 * @param postData
	 *            發送內容
	 * @param methodType
	 *            put,post等方法
	 * @return 服務端返回內容
	 * @throws IOException
	 */
	public static String getResponseData(String url, String postData, String methodType, String contentType) throws IOException {

		StringBuffer result = new StringBuffer();

		HttpURLConnection httpConnection = null;
		BufferedReader in = null;
		try {
			httpConnection = getHttpURLConnection(url, postData, methodType, contentType);
			OutputStream os = httpConnection.getOutputStream();
			OutputStreamWriter dos = new OutputStreamWriter(os, Charsets.UTF_8);
			dos.write(postData);
			dos.flush();
			dos.close();

			in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), Charsets.UTF_8));

			String line;
			while ((line = in.readLine()) != null) {
				result.append(line).append("\n");
			}
		} finally {
			if (null != in) {
				in.close();
			}
			if (null != httpConnection) {
				httpConnection.disconnect();
			}
		}

		return result.toString();
	}

	/**
	 * 获取getHttpURLConnection链接
	 * 
	 * @param url
	 * @param postData
	 * @param methodType
	 * @param contentType
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private static HttpURLConnection getHttpURLConnection(String url, String postData, String methodType, String contentType)
			throws MalformedURLException, IOException {
		HttpURLConnection httpConnection = (HttpURLConnection) new URL(url).openConnection();
		httpConnection.setRequestMethod(methodType);
		httpConnection.setRequestProperty("Content-Type", contentType);
		httpConnection.setRequestProperty("Proxy-Connection", "Keep-Alive");
		httpConnection.setDoOutput(true);
		httpConnection.setDoInput(true);

		httpConnection.setRequestProperty("Content-Length", "" + postData.length());
		httpConnection.setRequestProperty("accept", contentType);
		return httpConnection;
	}

	/**
	 * 执行文件下载
	 * 
	 * @param httpClient
	 *            HttpClient客户端实例，传入null会自动创建一个
	 * @param remoteFileUrl
	 *            远程下载文件地址
	 * @param localFilePath
	 *            本地存储文件地址
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static boolean executeDownloadFile(String remoteFileUrl, String localFilePath) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		InputStream in = null;
		FileOutputStream fout = null;
		try {
			HttpGet httpget = new HttpGet(remoteFileUrl);
			response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return false;
			}
			in = entity.getContent();
			File file = new File(localFilePath);
			fout = new FileOutputStream(file);
			int l = -1;
			byte[] tmp = new byte[1024];
			while ((l = in.read(tmp)) != -1) {
				fout.write(tmp, 0, l);
				// 注意这里如果用OutputStream.write(buff)的话，图片会失真
			}
			// 将文件输出到本地
			fout.flush();
			EntityUtils.consume(entity);
			return true;
		} finally {
			// 关闭低层流。
			if (fout != null) {
				try {
					fout.close();
				} catch (Exception e) {
				}
			}
			if (response != null) {
				try {
					response.close();
				} catch (Exception e) {
				}
			}
			try {
				httpClient.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 下载文件并直接转base64
	 * 
	 * @param url
	 * @return
	 */
	public static String downloadToBase64(String url) {
		RequestConfig config = RequestConfig.custom().setSocketTimeout(HttpConnectConstant.TIMEOUT)
													.setConnectTimeout(HttpConnectConstant.TIMEOUT)
													.setConnectionRequestTimeout(HttpConnectConstant.TIMEOUT)
				.setCookieSpec(CookieSpecs.DEFAULT).build();
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
		HttpGet httpget = new HttpGet(url);
		InputStream is = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			HttpResponse response = httpClient.execute(httpget);
			if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode()) {
				return null;
			}

			byte[] bb = new byte[1024];
			is = response.getEntity().getContent();
			int length = 0;
			while ((length = is.read(bb)) != -1) {
				bos.write(bb, 0, length);
			}
			return Base64.getEncoder().encodeToString(bos.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
