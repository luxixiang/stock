package com.moscue.fetcher;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

public class CookiesUtil {

	public static String getCookieValue(CookieStore cookieStore, String cookieName) {
		Cookie cookie = getCookie(cookieStore, cookieName);
		if (cookie == null) {
			return "";
		}
		return ((BasicClientCookie) cookie).getValue();
	}

	public static boolean setCookieValue(CookieStore cookieStore, String cookieName, String cookieValue) {
		Cookie cookie = getCookie(cookieStore, cookieName);
		if (cookie == null) {
			return false;
		}
		((BasicClientCookie) cookie).setValue(cookieValue);
		return true;
	}

	public static Cookie getCookie(CookieStore cookieStore, String cookieName) {
		return getCookie(cookieStore.getCookies(), cookieName);
	}

	public static Cookie getCookie(List<Cookie> cookies, String cookieName) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(cookieName)) {
				return cookie;
			}
		}
		return null;
	}

	public static Map<String, String> parseToMap(String cookiesContent) {
		Map<String, String> result = new HashMap<String, String>();
		String[] cookies = cookiesContent.split(";");
		for (String cookieStr : cookies) {
			String[] cookie = cookieStr.split("=", 2);
			if (cookie.length == 2) {
				result.put(cookie[0].trim(), cookie[1].trim());
			}
		}
		return result;
	}

	public static void print(CookieStore cookieStore) {
		List<Cookie> cookies = cookieStore.getCookies();
		for (Cookie cookie : cookies) {
			System.out.println(cookie.getName() + "=" + cookie.getValue());
		}
	}

	public static boolean isValid(CookieStore cookieStore) {
		return StringUtils.isBlank(getCookieValue(cookieStore, "__cas__st__3"))
				|| StringUtils.isBlank(getCookieValue(cookieStore, "__cas__id__3"));
	}

	public static String mapToCookies(Map<String, String> cookiesMap) {
		StringBuffer sb = new StringBuffer();
		Set<Map.Entry<String, String>> set = cookiesMap.entrySet();
		for (Iterator<Map.Entry<String, String>> it = set.iterator(); it.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
			sb.append(entry.getKey()).append("=").append(entry.getValue()).append("; ");
		}
		return sb.toString();
	}

}
