package com.search.common;

import java.io.Serializable;

public class BaseResp implements Serializable{
	
	private static final long serialVersionUID = -7382949983832251815L;
	
	private Result result;
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	
}
