package com.silvercare.util;

// to be used by Create, Update, Delete operations of DAOs
public class OperationResponse {
	private boolean success;
	private String code;
	private String message;
	private Object data;

	public OperationResponse(boolean success, String code, String message, Object data) {
		this.success = success;
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public OperationResponse(boolean success, String code, Object data) {
		this.success = success;
		this.code = code;
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}