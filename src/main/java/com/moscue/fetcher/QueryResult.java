package com.moscue.fetcher;

import java.io.Serializable;

public class QueryResult implements Serializable{

	private static final long serialVersionUID = 1L;
	private String queryId;
	private Long queryTime;
	private Long resultTime;
	private byte[] data;

	public Long getResultTime() {
		return resultTime;
	}

	public void setResultTime(Long resultTime) {
		this.resultTime = resultTime;
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public Long getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(Long queryTime) {
		this.queryTime = queryTime;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}
