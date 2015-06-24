package com.appnometry.appnomars.util;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * HttpRequest This class handles POST and GET requests and enables you to
 * upload files via post. Cookies are stored in the HttpClient.
 *
 */
public class HttpRequest {

	/*
	 * 
	 * do log in
	 */

	public static String doLogin(String json, final String url) {

		final HttpPost httpost = new HttpPost(url);

		// httpost.setHeader("Accept", "application/json");
		// httpost.setHeader("Content-type", "application/json");

		// JSONObject ob=new
		// JSONObject("\"user\"\"sessionId\":1002,\"userId\":0,\"username\":\"user201\",\"password\":\"password01\",\"userInfo\"\"id\":0,\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"middleName\":\"middleName\"},\"address\"\"id\":0,\"address1\":\"address1\",\"address2\":\"address2\",\"city\":\"city\",\"state\":\"state\",\"country\":\"country\",\"zip5\":12345,\"zip4\":1234},\"contact\"\"id\":0,\"email1\":\"email1\",\"email2\":\"email2\",\"homePhone\":\"homePhone\",\"mobilePhone\":\"mobilePhone\",\"workPhone\":\"workPhone\"},\"userPrefs\"\"id\":0,\"q1Color\":\"q1Color\",\"q2Color\":\"q2Color\",\"q3Color\":\"q3Color\",\"q4Color\":\"q4Color\"}});

		Log.d("Log in URL is ", url);

		final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("data", json));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			// httpost.setEntity(new StringEntity(json));

			return HttpRequest.getData(httpost);

		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";

	}

	/*
	 * get data as string
	 */

	public static String getData(final HttpPost httpost) throws IOException,
			URISyntaxException {

		String inputLine = "Error";
		final StringBuffer buf = new StringBuffer();

		{

			InputStream ins = null;

			ins = HttpRequest.getUrlData(httpost);

			final InputStreamReader isr = new InputStreamReader(ins);
			final BufferedReader in = new BufferedReader(isr);

			while ((inputLine = in.readLine()) != null) {
				buf.append(inputLine);
			}

			in.close();

		}

		return buf.toString();

	}

	/*
	 * get input stream
	 */
	public static InputStream getUrlData(final HttpPost httpost)
			throws URISyntaxException, ClientProtocolException, IOException {

		final HttpClient client = HttpRequest.getClient();

		/*
		 * 
		 * send cookie
		 */

		// if (CurrentData.getCookies() != null) {
		//
		// System.out.println("Cookie is added to client");
		//
		// for (final Cookie cok : CurrentData.getCookies()) {
		// ((DefaultHttpClient) client).getCookieStore().addCookie(cok);
		// }
		// } else {
		// System.out.println("Cookie is empty");
		// }

		final HttpResponse res = client.execute(httpost);

		System.out.println("post response for  register: "
				+ res.getStatusLine());

		return res.getEntity().getContent();
	}

	/*
	 * get https client
	 */
	public static DefaultHttpClient getClient() {
		DefaultHttpClient ret = null;
		// sets up parameters
		final HttpParams params = new BasicHttpParams();

		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, "utf-8");
		params.setBooleanParameter("http.protocol.expect-continue", false);
		// registers schemes for both http and https
		final SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		final SSLSocketFactory sslSocketFactory = SSLSocketFactory
				.getSocketFactory();
		sslSocketFactory
				.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		registry.register(new Scheme("https", sslSocketFactory, 443));
		final ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(
				params, registry);
		ret = new DefaultHttpClient(manager, params);
		return ret;

	}

	/*
	 * get inputstream for get request
	 */

	public static InputStream getInputStreamForGetRequest(final String url)
			throws URISyntaxException, ClientProtocolException, IOException {

		Log.w("URL is ", url);
		final DefaultHttpClient httpClient = HttpRequest.getClient();
		URI uri;
		InputStream data = null;

		uri = new URI(url);
		final HttpGet method = new HttpGet(uri);

		// /*
		// *
		// * send cookie
		// */
		//
		// if (CurrentData.getCookies() != null) {
		//
		// System.out.println("Cookie is added to client");
		//
		// for (final Cookie cok : CurrentData.getCookies()) {
		// httpClient.getCookieStore().addCookie(cok);
		// }
		// } else {
		// System.out.println("Cookie is empty");
		// }

		final HttpResponse res = httpClient.execute(method);

		System.out.println("Login form get: " + res.getStatusLine());

		System.out.println("get login cookies:");
		httpClient.getCookieStore().getCookies();

		// if (cookies.isEmpty()) {
		// System.out.println("None");
		// } else {
		//
		// CurrentData.setCookies(cookies);
		//
		// System.out.println("size of cokies " + cookies.size());
		//
		// for (int i = 0; i < cookies.size(); i++) {
		// Log.e("- " + cookies.get(i).toString(), "");
		// }
		// }

		final String code = res.getStatusLine().toString();
		Log.w("status line ", code);
		data = res.getEntity().getContent();

		return data;
	}

	/**
	 * Get string from stream
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String GetText(final InputStream in) throws IOException {
		String text = "";
		final BufferedReader reader = new BufferedReader(new InputStreamReader(
				in));
		final StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			text = sb.toString();

		} finally {

			in.close();

		}
		return text;
	}

	public static InputStream GetRequest(final String url)
			throws URISyntaxException, ClientProtocolException, IOException {

		Log.w("Current url is ", url);
		final DefaultHttpClient httpClient = HttpRequest.getClient();
		URI uri;
		InputStream data = null;

		uri = new URI(url);
		final HttpGet method = new HttpGet(uri);

		/*
		 * 
		 * send cookie
		 */

		if (CurrentData.getCookies() != null) {

			System.out.println("Cookie is added to client");

			for (final Cookie cok : CurrentData.getCookies()) {
				httpClient.getCookieStore().addCookie(cok);
			}
		} else {
			System.out.println("Cookie is empty");
		}

		final HttpResponse res = httpClient.execute(method);

		System.out.println("Login form get: " + res.getStatusLine());

		System.out.println("get login cookies:");
		final List<Cookie> cookies = httpClient.getCookieStore().getCookies();

		if (cookies.isEmpty()) {
			System.out.println("None");
		} else {

			CurrentData.setCookies(cookies);

			System.out.println("size of cokies " + cookies.size());

			for (int i = 0; i < cookies.size(); i++) {
				Log.e("- " + cookies.get(i).toString(), "");
			}
		}

		final String code = res.getStatusLine().toString();
		Log.w("status line ", code);
		data = res.getEntity().getContent();

		return data;
	}

}
