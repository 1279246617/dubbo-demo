package com.coe.wms.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

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
		DefaultHttpClient http = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		try {
			List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
			Set<String> keySet = parames.keySet();
			for (String key : keySet) {
				String value = parames.get(key);
				nvps.add(new BasicNameValuePair(key, value));
			}
			post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			HttpResponse httpResponse = http.execute(post);
			return EntityUtils.toString(httpResponse.getEntity());
		} catch (IOException e) {
			throw e;
		} finally {
			http.getConnectionManager().shutdown();
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

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(urlPath);
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(basicNameValuePairs, Constant.UTF_8);
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

			String xmlResult = httpClient.execute(httpPost, handler);

			return xmlResult;

		} finally {
			abortRequest(httpPost);
			shutdown(httpClient);
		}
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

	public static void shutdown(final HttpClient httpclient) {
		if (httpclient != null) {
			httpclient.getConnectionManager().shutdown();
		}
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
	public static HttpClient wrapSSLClient(HttpClient base) {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				@Override
				public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};

			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = base.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", 443, ssf));
			return new DefaultHttpClient(ccm, base.getParams());

		} catch (Exception e) {
			return null;
		}
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
	public static HttpResponse getGetResponse(HttpClient httpClient, final String url, int timeout) {

		HttpClientParams.setCookiePolicy(httpClient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);

		// if (useProxy) {
		// HttpHost proxy = new HttpHost("rproxy01.hongkongpost.com", 80);
		// httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
		// proxy);
		// }
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
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
	public static String postRquest(HttpClient httpClient, final String strUrl, List<BasicNameValuePair> basicNameValuePairs) throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost(strUrl);
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(basicNameValuePairs, Constant.UTF_8);
		httpPost.setEntity(entity);

		ResponseHandler<String> handler = getResponseHandler();

		String strResult = httpClient.execute(httpPost, handler);
		return strResult;

	}

	public static String postRquest(HttpClient httpClient, final String strUrl, String data) throws ClientProtocolException, IOException {
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
				if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY) || (statuscode == HttpStatus.SC_MOVED_PERMANENTLY) || (statuscode == HttpStatus.SC_SEE_OTHER) || (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) {

					Header locationHeader = response.getFirstHeader("Location");
					if (locationHeader != null) {
						strReslut = locationHeader.getValue();
					}
				} else if (httpEntity != null) {
					strReslut = EntityUtils.toString(httpEntity, Constant.UTF_8);
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
	 * 获取请求参数，取不到从头部取，并设置参数
	 * 
	 * @param request
	 * @param parameterName
	 *            请求参数名称
	 * @return
	 */
	public static String getParameter(HttpServletRequest request, String parameterName) {
		String parameterValue = request.getParameter(parameterName);
		if (parameterValue == null) {
			parameterValue = request.getHeader(parameterName);
			if (parameterValue != null) {
				request.setAttribute(parameterName, parameterValue);
			}
		}
		return parameterValue;
	}

	/**
	 * 取消息格式，先从请取参数中取，没有的情况再从头部取,默认为json
	 * 
	 * @param request
	 * @return 消息内容格式
	 */
	public static String getFormat(HttpServletRequest request) {
		String format = request.getParameter("format");
		if (format == null) {
			format = request.getParameter("format");
		}
		if (format == null) {
			format = request.getHeader("Accept");
		}
		if (format == null) {
			format = request.getHeader("Content-Type");
		}
		return format;
	}

	/**
	 * 获取客户端的IP
	 * 
	 * @param request
	 * @return IP地址
	 */
	public static String getRemoteIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (isNullIp(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (isNullIp(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (isNullIp(ip)) {
			ip = request.getHeader("X-Cluster-Client-Ip");
		}
		if (isNullIp(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	private static boolean isNullIp(String ip) {
		return ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip);
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
			OutputStreamWriter dos = new OutputStreamWriter(os, Constant.UTF_8);
			dos.write(postData);
			dos.flush();
			dos.close();

			in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), Constant.UTF_8));

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

	private static HttpURLConnection getHttpURLConnection(String url, String postData, String methodType, String contentType) throws MalformedURLException, IOException {
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

}
