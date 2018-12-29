package com.search.common;

import java.io.Serializable;

public class Result implements Serializable{

	private static final long serialVersionUID = -4438561845189523489L;
	public Result() {

	}
	/**
	 * 0正常、1异常
	 */
	private int code;
	private String msg;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
