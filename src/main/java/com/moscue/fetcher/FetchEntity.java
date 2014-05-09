package com.moscue.fetcher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.http.NameValuePair;

public class FetchEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private String url;
	
	private Map<String, String> headerMap = new HashMap<String, String>();
	private Map<String, String> paramMap = new HashMap<String, String>();
	
	private String dataEncoding = "UTF-8";

	public void addHeader(String name, String value) {
		headerMap.put(name, value);
	}
	
	public void addHeaderMap(Map<String, String> map) {
		headerMap.putAll(map);
	}
	
	public Map<String, String> getHeader(){
		return headerMap;
	}
	
	public Map<String, String> getParam(){
		return paramMap;
	}
	
	public void putHeaderMap(Map<String, String> map) {
		paramMap.putAll(map);
	}
	
	public void addParam(String name, String value) {
		paramMap.put(name, value);
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getDataEncoding() {
		return dataEncoding;
	}

	public void setDataEncoding(String dataEncoding) {
		this.dataEncoding = dataEncoding;
	}
}
