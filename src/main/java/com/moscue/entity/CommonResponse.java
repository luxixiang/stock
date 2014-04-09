package com.moscue.entity;

public class CommonResponse<T> extends ResponseBase {
	
	private static final long serialVersionUID = 1L;
	private T retObj = null;
	
	public CommonResponse(int code, String message) {
		super(code, message);
	}
	
	public CommonResponse(int code, String message, T obj) {
		super(code, message);
		this.retObj = obj;
	}

	public T getRetObj() {
		return retObj;
	}

	public void setRetObj(T retObj) {
		this.retObj = retObj;
	}

}
