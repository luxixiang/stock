package com.moscue.fetcher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.SSLException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParamBean;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;

public class PageFetcher {

	protected static final Logger logger = Logger.getLogger(PageFetcher.class);
	protected static final int HTTP_REQUEST_RETRY_COUNT = 5;
	protected PoolingClientConnectionManager connectionManager;
	protected IdleConnectionMonitorThread connectionMonitorThread;
	protected DefaultHttpClient httpClient;
	protected FetcherConfig config;

	public PageFetcher() {
		this(new FetcherConfig());
	}

	public PageFetcher(FetcherConfig config) {

		this.config = config;
		this.config.randomComputerUserAgent();
		HttpParams params = new BasicHttpParams();
		HttpProtocolParamBean paramsBean = new HttpProtocolParamBean(params);
		paramsBean.setVersion(HttpVersion.HTTP_1_1);
		paramsBean.setContentCharset("UTF-8");

		params.setParameter(CoreProtocolPNames.USER_AGENT,
				config.getUserAgentString());
		params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT,
				config.getSocketTimeout());
		params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				config.getConnectionTimeout());
		params.setBooleanParameter("http.protocol.handle-redirects",
				config.isFollowRedirects());

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory
				.getSocketFactory()));

		if (config.isIncludeHttpsPages()) {
			schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory
					.getSocketFactory()));
		}

		connectionManager = new PoolingClientConnectionManager(schemeRegistry);
		connectionManager.setMaxTotal(config.getMaxTotalConnections());
		connectionManager.setDefaultMaxPerRoute(config
				.getMaxConnectionsPerHost());
		httpClient = new DefaultHttpClient(connectionManager, params);

		if (config.getProxyHost() != null) {

			if (config.getProxyUsername() != null) {
				httpClient.getCredentialsProvider()
						.setCredentials(
								new AuthScope(config.getProxyHost(),
										config.getProxyPort()),
								new UsernamePasswordCredentials(config
										.getProxyUsername(), config
										.getProxyPassword()));
			}

			setProxy(config.getProxyHost(), config.getProxyPort());
		}

		setHttpRequestRetryHandler();
		addResponseInterceptor();

		if (connectionMonitorThread == null) {
			connectionMonitorThread = new IdleConnectionMonitorThread(
					connectionManager);
		}
		connectionMonitorThread.start();
	}

	public void setProxy(String host, int port) {
		HttpHost proxy = new HttpHost(host, port);
		httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
				proxy);
		httpClient = (DefaultHttpClient) WebClientDevWrapper
				.wrapClient(this.httpClient);
	}

	public PageFetchResult get(String toFetchURL) {
		return get(toFetchURL, "", "");
	}

	public PageFetchResult get(String toFetchURL, String cookies,
			String userAgent) {
		HttpGet get = new HttpGet(toFetchURL);
		get.addHeader("Accept-Encoding", "gzip");
		get.addHeader("Connection", "Keep-Alive");
		if (StringUtils.isNotEmpty(cookies)) {
			get.addHeader("Cookie", cookies);
		}
		if (StringUtils.isNotBlank(userAgent)) {
			get.addHeader("User-Agent", userAgent);
		}
		return request(get);
	}

	public FetchResult get(FetchEntity param) {
		HttpGet get = new HttpGet(param.getUrl());
		for (NameValuePair nvp : getNameValuePair(param.getHeader())) {
			get.addHeader(nvp.getName(), nvp.getValue());
		}
		return new FetchResult(request(get));
	}

	public PageFetchResult post(String toFetchURL, List<NameValuePair> nvps) {
		return post(toFetchURL, nvps, "");
	}

	public FetchResult post(FetchEntity param) {
		HttpPost post = new HttpPost(param.getUrl());
		for (NameValuePair nvp : getNameValuePair(param.getHeader())) {
			post.addHeader(nvp.getName(), nvp.getValue());
		}

		try {
			post.setEntity(new UrlEncodedFormEntity(getNameValuePair(param.getParam()), param
					.getDataEncoding()));
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException(ex);
		}
		return new FetchResult(request(post));
	}
	
	private List<NameValuePair> getNameValuePair(Map<String,String> map){
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (Entry<String, String> ent : map.entrySet()) {
			list.add(new BasicNameValuePair(ent.getKey(), ent.getValue()));
		}
		return list;
	}

	public PageFetchResult post(String toFetchURL, List<NameValuePair> nvps,
			String cookies) {
		HttpPost post = new HttpPost(toFetchURL);
		post.addHeader("Accept-Encoding", "gzip");
		post.addHeader("Connection", "Keep-Alive");
		if (StringUtils.isNotEmpty(cookies)) {
			post.addHeader("Cookie", cookies);
		}
		try {
			post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException(ex);
		}
		return request(post);
	}

	public PageFetchResult request(HttpGet get) {

		PageFetchResult fetchResult = new PageFetchResult();
		String toFetchURL = get.getURI().toString();
		try {
			HttpResponse response = httpClient.execute(get);
			fetchResult.setEntity(response.getEntity());
			for (Header header : response.getHeaders("Set-Cookie")) {
				fetchResult.addCookieString(header.getValue());
			}

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				if (statusCode != HttpStatus.SC_NOT_FOUND) {
					if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
							|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
						Header header = response.getFirstHeader("Location");
						if (header != null) {
							String movedToUrl = header.getValue();
							fetchResult.setMovedToUrl(movedToUrl);
						}
						fetchResult.setStatusCode(statusCode);
						return fetchResult;
					}
					logger.info("Failed: "
							+ response.getStatusLine().toString()
							+ ", while fetching " + toFetchURL);
					throw new PageFetcherException(response.getStatusLine()
							.toString());
				}
				fetchResult.setStatusCode(response.getStatusLine()
						.getStatusCode());
				return fetchResult;
			}
			if (fetchResult.getEntity() != null) {
				fetchResult.setStatusCode(HttpStatus.SC_OK);
				return fetchResult;
			} else {
				get.abort();
			}
		} catch (SocketTimeoutException sockEx){
			get.abort();
			logger.error("读数据连接超时:" + sockEx.getMessage());
		} catch (IOException e) {
			throw new PageFetcherException(e.getMessage());
		} catch (Exception e) {
			if (e.getMessage() == null) {
				logger.error("Error while fetching " + toFetchURL);
			} else {
				logger.error(e.getMessage() + " while fetching " + toFetchURL);
			}
		} finally {
			try {
				if (fetchResult.getEntity() == null) {
					get.abort();
				}
			} catch (Exception e) {
				logger.error("ignore " + e.getMessage());
			}
		}
		return fetchResult;
	}

	public void clearCookies() {
		httpClient.getCookieStore().clear();
	}

	public PageFetchResult request(HttpPost post) {
		PageFetchResult fetchResult = new PageFetchResult();
		String toFetchURL = post.getURI().toString();
		try {
			HttpResponse response = httpClient.execute(post);
			fetchResult.setEntity(response.getEntity());
			for (Header header : response.getHeaders("Set-Cookie")) {
				fetchResult.addCookieString(header.getValue());
			}

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				if (statusCode != HttpStatus.SC_NOT_FOUND) {
					if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
							|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
						Header header = response.getFirstHeader("Location");
						if (header != null) {
							String movedToUrl = header.getValue();
							fetchResult.setMovedToUrl(movedToUrl);
						}
						fetchResult.setStatusCode(statusCode);
						return fetchResult;
					}
					logger.info("Failed: "
							+ response.getStatusLine().toString()
							+ ", while fetching " + toFetchURL);
					throw new PageFetcherException(response.getStatusLine()
							.toString());
				}
				System.out.println("Set-Cookie:" + response.getHeaders("Set-Cookie"));
				fetchResult.setStatusCode(response.getStatusLine()
						.getStatusCode());
				return fetchResult;
			}

			fetchResult.setFetchedUrl(toFetchURL);

		} catch (SocketTimeoutException sockEx){
			post.abort();
			logger.error("读数据连接超时:" + sockEx.getMessage());
		}catch (IOException e) {
			logger.error("Fatal transport error: " + e.getMessage()
					+ " while fetching " + toFetchURL);
			throw new PageFetcherException(e.getMessage());
		} catch (Exception e) {
			if (e.getMessage() == null) {
				logger.error("Error while fetching " + toFetchURL);
			} else {
				logger.error(e.getMessage() + " while fetching " + toFetchURL);
			}
		} finally {
			try {
				if (fetchResult.getEntity() == null) {
					post.abort();
				}
			} catch (Exception e) {
				logger.error("ignore " + e.getMessage());
			}
		}
		return fetchResult;
	}

	public DefaultHttpClient getHttpClient() {
		return httpClient;
	}

	public synchronized void shutDown() {
		if (connectionMonitorThread != null) {
			connectionManager.shutdown();
			connectionMonitorThread.shutdown();
		}
	}

	private void setHttpRequestRetryHandler() {
		HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
			public boolean retryRequest(IOException exception,
					int executionCount, HttpContext context) {
				if (executionCount >= 5) {
					return false;
				}
				if (exception instanceof UnknownHostException) {
					return false;
				}
				if ((exception instanceof InterruptedIOException)) {
					return true;
				}
				if ((exception instanceof NoHttpResponseException)) {
					return true;
				}
				if ((exception instanceof SocketException)) {
					return true;
				}
				if (exception instanceof ConnectException) {
					return false;
				}
				if (exception instanceof SSLException) {
					return false;
				}
				HttpRequest request = (HttpRequest) context
						.getAttribute(ExecutionContext.HTTP_REQUEST);
				boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
				if (idempotent) {
					// Retry if the request is considered idempotent
					return true;
				}
				return false;
			}
		};
		this.httpClient.setHttpRequestRetryHandler(myRetryHandler);
	}

	private void addResponseInterceptor() {
		httpClient.addResponseInterceptor(new HttpResponseInterceptor() {
			@Override
			public void process(final HttpResponse response,
					final HttpContext context) throws HttpException,
					IOException {
				HttpEntity entity = response.getEntity();
				Header contentEncoding = entity.getContentEncoding();
				if (contentEncoding != null) {
					HeaderElement[] codecs = contentEncoding.getElements();
					for (HeaderElement codec : codecs) {
						if (codec.getName().equalsIgnoreCase("gzip")) {
							response.setEntity(new GzipDecompressingEntity(
									response.getEntity()));
							return;
						}
					}
				}
			}
		});
	}

	private static class GzipDecompressingEntity extends HttpEntityWrapper {

		public GzipDecompressingEntity(final HttpEntity entity) {
			super(entity);
		}

		@Override
		public InputStream getContent() throws IOException,
				IllegalStateException {
			InputStream wrappedin = wrappedEntity.getContent();
			return new GZIPInputStream(wrappedin);
		}

		@Override
		public long getContentLength() {
			return -1;
		}
	}

	public static void main(String[] args) throws Exception {
		String url = "http://fengchao.baidu.com/nirvana/vcode?src=prv&nocache=0.381767557002604";
		PageFetcher fetcher = new PageFetcher();
		fetcher.setProxy("127.0.0.1", 8080);
		byte[] image = fetcher.get(url).getContentBytes();
		FileUtils.writeByteArrayToFile(new File("E:/vcode.jpg"), image);
	}
}
