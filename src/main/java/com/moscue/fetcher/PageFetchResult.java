package com.moscue.fetcher;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class PageFetchResult {

	protected static final Logger logger = Logger
			.getLogger(PageFetchResult.class);
	protected int statusCode;
	protected HttpEntity entity = null;
	protected String fetchedUrl = null;
	protected String movedToUrl = null;
	protected byte[] byteResult = null;
	protected List<String> cookieStrings = new ArrayList<String>();

	public List<String> getCookieStrings() {
		return cookieStrings;
	}

	public void setCookieStrings(List<String> cookieStrings) {
		this.cookieStrings = cookieStrings;
	}

	public void addCookieString(String cookieString) {
		this.cookieStrings.add(cookieString);
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public HttpEntity getEntity() {
		return entity;
	}

	public void setEntity(HttpEntity entity) throws Exception {
		this.entity = entity;
		try {
			byteResult = EntityUtils.toByteArray(entity);
		} catch (Exception e) {
			discardContentIfNotConsumed();
			throw e;
		}
	}

	public String getFetchedUrl() {
		return fetchedUrl;
	}

	public void setFetchedUrl(String fetchedUrl) {
		this.fetchedUrl = fetchedUrl;
	}

	public String getMovedToUrl() {
		//discardContentIfNotConsumed();
		return movedToUrl;
	}

	public void setMovedToUrl(String movedToUrl) {
		this.movedToUrl = movedToUrl;
	}

	public String getContentString() {
		if (this.byteResult == null) {
			return null;
		}
		ContentType contentType = ContentType.getOrDefault(entity);
		Charset charset = contentType.getCharset();
		if (charset == null) {
			charset = HTTP.DEF_CONTENT_CHARSET;
		}
		try {
			String ret = new String(byteResult, charset.name());
			return ret;
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	public byte[] getContentBytes() {
		return byteResult;
	}

	public void discardContentIfNotConsumed() {
		try {
			if (entity != null) {
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			System.out.println("忽略异常：" + e);
		}
	}
}
