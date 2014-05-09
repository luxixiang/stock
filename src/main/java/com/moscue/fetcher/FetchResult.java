package com.moscue.fetcher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class FetchResult implements Serializable {

	private static final long serialVersionUID = 1L;
	protected int statusCode;
	protected byte[] contentBytes = null;

	public byte[] getContentBytes() {
		return contentBytes;
	}

	public void setContentBytes(byte[] contentBytes) {
		this.contentBytes = contentBytes;
	}

	protected String fetchedUrl = null;
	protected String movedToUrl = null;
	protected List<String> cookieStrings = new ArrayList<String>();

	public FetchResult(PageFetchResult result) {
		this.statusCode = result.getStatusCode();
		this.contentBytes = result.getContentBytes();
		this.fetchedUrl = result.getFetchedUrl();
		this.movedToUrl = result.getMovedToUrl();
		this.cookieStrings.addAll(result.getCookieStrings());
	}

	public FetchResult() {

	}

	public String getCookie(String cookieName) {
		for (String cookieStr : cookieStrings) {
			Map<String, String> map = CookiesUtil.parseToMap(cookieStr);
			String cookie = map.get(cookieName);
			if (StringUtils.isNotBlank(cookie)) {
				return cookie;
			}
		}
		return "";
	}

	public String getContentString() {
		if (contentBytes == null) {
			return "";
		}

		try {
			return new String(contentBytes);
		} catch (Exception e) {

		}
		return "";
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getFetchedUrl() {
		return fetchedUrl;
	}

	public void setFetchedUrl(String fetchedUrl) {
		this.fetchedUrl = fetchedUrl;
	}

	public String getMovedToUrl() {
		return movedToUrl;
	}

	public void setMovedToUrl(String movedToUrl) {
		this.movedToUrl = movedToUrl;
	}

	public List<String> getCookieStrings() {
		return cookieStrings;
	}

	public void setCookieStrings(List<String> cookieStrings) {
		this.cookieStrings = cookieStrings;
	}

}
