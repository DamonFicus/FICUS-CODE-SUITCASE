package ficus.suitcase.http;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.*;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

public class HttpURLClient {
	private static HttpURLConnection getConnection(String submitUrl, String charset, String method, int connectTimeout,
                                                   int readTimeout) throws Exception {
		if (StringUtils.isBlank(charset)) {
			charset = "utf-8";
		}
		if (connectTimeout == 0) {
			connectTimeout = 30000;
		}
		if (readTimeout == 0) {
			readTimeout = 60000;
		}

		URL url = new URL(submitUrl);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		httpURLConnection.setConnectTimeout(connectTimeout);
		httpURLConnection.setReadTimeout(readTimeout);
		httpURLConnection.setDoInput(true);
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod(method);
		httpURLConnection.setRequestProperty("Content-type", "text/xml;charset=" + charset);
		httpURLConnection.setUseCaches(false);
		if (StringUtils.equalsIgnoreCase("https", url.getProtocol())) {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
			if (httpURLConnection instanceof HttpsURLConnection) {
				HttpsURLConnection httpsUrlCon = (HttpsURLConnection) httpURLConnection;
				httpsUrlCon.setSSLSocketFactory(sc.getSocketFactory());
				httpsUrlCon.setHostnameVerifier(new TrustAnyHostnameVerifier());
			}
		}
		return httpURLConnection;
	}

	private static String getResult(HttpURLConnection httpURLConnection, String resCharset) throws Exception {
		// httpURLConnection.connect();
		int responseCode = httpURLConnection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			InputStream inputStream = httpURLConnection.getInputStream();
			String result = IOUtils.toString(inputStream, resCharset);
			return result;
		} else {
			throw new Exception("httpGet发包失败,responseCode=" + responseCode);
		}
	}

	private static String getRequestParamString(Map<String, String> requestParam, String charset) throws Exception {
		StringBuffer sb = new StringBuffer("");
		for (String key : requestParam.keySet()) {
			String str = String.format("%s=%s&", key, URLEncoder.encode(requestParam.get(key), charset));
			sb.append(str);
		}
		String paramStr = StringUtils.substringBeforeLast(sb.toString(), "&");
		// SysLogUtils.debug("参数拼接:", requestParam);
		return paramStr;
	}

	public static String httpGet(String submitUrl, Map<String, String> param, String reqCharset, String resCharset,
                                 int connectTimeout, int readTimeout) throws Exception {
		String paramStr = getRequestParamString(param, reqCharset);
		submitUrl = String.format("%s?%s", submitUrl, paramStr);
		HttpURLConnection conn = getConnection(submitUrl, resCharset, "GET", connectTimeout, readTimeout);
		return getResult(conn, resCharset);
	}

	public static String httpPost(String submitUrl, Map<String, String> param, String reqCharset, String resCharset,
                                  int connectTimeout, int readTimeout) throws Exception {
		HttpURLConnection conn = getConnection(submitUrl, reqCharset, "POST", connectTimeout, readTimeout);
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + reqCharset);
		try {
			PrintStream out = new PrintStream(conn.getOutputStream(), false, reqCharset);
			out.print(getRequestParamString(param, reqCharset));
			out.flush();
		} catch (Exception e) {
			throw e;
		}
		return getResult(conn, resCharset);
	}

	public static String httpPostStream(String submitUrl, String content, String reqCharset, String resCharset,
                                        int connectTimeout, int readTimeout) throws Exception {
		HttpURLConnection conn = getConnection(submitUrl, reqCharset, "POST", connectTimeout, readTimeout);
		byte[] arr = content.getBytes(reqCharset);
		conn.setRequestProperty("Content-Length", String.valueOf(arr.length));
		conn.getOutputStream().write(arr);
		conn.getOutputStream().flush();
		return getResult(conn, resCharset);
	}

	private static class TrustAnyTrustManager implements X509TrustManager {
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		@Override
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
}
