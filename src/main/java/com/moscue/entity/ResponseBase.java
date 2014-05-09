package com.moscue.entity;

import java.io.Serializable;

public abstract class ResponseBase implements Serializable {
	private static final long serialVersionUID = 1L;
	private int code;
	private String message;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public ResponseBase(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
}
