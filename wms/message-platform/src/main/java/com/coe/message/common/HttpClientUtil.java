package com.coe.message.common;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**HttpClient工具类*/
public class HttpClientUtil {

	/**连接池管理对象*/
	private static PoolingHttpClientConnectionManager cm;

	/**初始化连接池*/
	private static void init() {
		if (cm == null) {
			cm = new PoolingHttpClientConnectionManager();
			// 整个连接池最大连接数
			cm.setMaxTotal(50);
			// 每个路由最大连接数，默认值是2
			cm.setDefaultMaxPerRoute(5);
		}
	}

	/** 
	 * 通过连接池获取HttpClient 
	 * @return CloseableHttpClient
	 */

	public static CloseableHttpClient getHttpClient() {
		init();
		return HttpClients.custom().setConnectionManager(cm).build();
	}

	/**
	 * 创建HttpGet,get方式请求（无参数）
	 * @param url
	 * @param headers
	 * @return
	 */
	public static HttpGet initHttpGet(String url, Map<String, Object> headers) {
		HttpGet httpGet = new HttpGet(url);
		if (headers != null) {
			for (Map.Entry<String, Object> param : headers.entrySet()) {
				httpGet.addHeader(param.getKey(), param.getValue().toString());
			}

		}
		return httpGet;
	}

	/**
	 * get方式请求（无参数）
	 * @param url 请求地址
	 * @param headers  头信息
	 * @return 任意格式的字符串
	 */
	public static String httpGetRequest(String url, Map<String, Object> headers) {
		HttpGet httpGet = initHttpGet(url, headers);
		return getResult(httpGet);
	}

	/**
	 *创建HttpGet, get方式请求（有参数）
	 * @param url 请求地址
	 * @param params 参数集合
	 * @param headers  头信息
	 * @return 任意格式的字符串
	 */
	public static HttpGet initHttpGet(String url, Map<String, Object> params, Map<String, Object> headers) {
		HttpGet httpGet = null;
		try {
			URIBuilder ub = new URIBuilder();
			ub.setPath(url);
			ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
			ub.setParameters(pairs);
			httpGet = new HttpGet(ub.build());
			if (headers != null) {
				for (Map.Entry<String, Object> param : headers.entrySet()) {
					httpGet.addHeader(param.getKey(), param.getValue().toString());
				}

			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return httpGet;

	}

	/**
	 * get方式请求（有参数）
	 * @param url 请求地址
	 * @param params 参数集合
	 * @param headers  头信息
	 * @return 任意格式的字符串
	 */
	public static String httpGetRequest(String url, Map<String, Object> params, Map<String, Object> headers) {
		HttpGet httpGet = initHttpGet(url, params, headers);
		return getResult(httpGet);

	}

	/**
	 * 创建HttpPost,post方式请求（无参）
	 * @param url 请求地址
	 * @param headers 头信息
	 * @return HttpPost实例
	 */
	public static HttpPost initHttpPost(String url, Map<String, Object> headers) {
		HttpPost httpPost = new HttpPost(url);
		if (headers != null) {
			for (Map.Entry<String, Object> param : headers.entrySet()) {
				httpPost.addHeader(param.getKey(), param.getValue().toString());
			}
		}
		return httpPost;
	}

	/**
	 * post方式请求（无参）
	 * @param url 请求地址
	 * @param headers 头信息
	 * @return 任意格式的字符串
	 */
	public static String httpPostRequest(String url, Map<String, Object> headers) {
		HttpPost httpPost = initHttpPost(url, headers);
		return getResult(httpPost);
	}

	/**
	 * 创建HttpPost,post方式请求（有参）
	 * @param url 请求地址
	 * @param params 参数集合
	 * @param headers 头信息集合
	 * @return HttpPost实例
	 */
	public static HttpPost initHttpPost(String url, Map<String, Object> params, Map<String, Object> headers) {
		HttpPost httpPost = new HttpPost(url);
		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (headers != null) {
			for (Map.Entry<String, Object> param : headers.entrySet()) {
				httpPost.addHeader(param.getKey(), param.getValue().toString());
			}
		}
		return httpPost;
	}
	/**
	 * post方式请求（有参）
	 * @param url 请求地址
	 * @param params 参数集合
	 * @param headers 头信息集合
	 * @return 任意格式的字符串
	 */
	public static String httpPostRequest(String url, Map<String, Object> params, Map<String, Object> headers) {
		HttpPost httpPost = initHttpPost(url,params,headers);
		return getResult(httpPost);
	}

	/**
	 * 将参数封装成集合
	 * @param params 参数集合
	 * @return 装载NameValuePair对象的结合
	 */
	public static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params) {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		Set<Entry<String, Object>> set = params.entrySet();
		for (Entry<String, Object> param : set) {
			pairs.add(new BasicNameValuePair(param.getKey(), param.getValue().toString()));
		}
		return pairs;
	}

	/**
	 * 执行请求返回HttpResponse
	 * @param request 请求对象
	 * @return HttpResponse实例
	 */
	public static HttpResponse getResponse(HttpRequestBase request) throws Exception {
		CloseableHttpClient httpClient = getHttpClient();
		CloseableHttpResponse response = httpClient.execute(request);
		return response;
	}

	/**
	 * 执行请求返回结果
	 * @param request 请求对象
	 * @return 任意格式的字符串
	 */
	public static String getResult(HttpRequestBase request) {
		try {
			CloseableHttpResponse response = (CloseableHttpResponse) getResponse(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);
				response.close();
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
