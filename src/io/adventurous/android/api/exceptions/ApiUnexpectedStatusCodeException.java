package io.adventurous.android.api.exceptions;

public class ApiUnexpectedStatusCodeException extends ApiException {
	private static final long serialVersionUID = 1L;
	
	private String response;
	private int statusCode;
	
	public ApiUnexpectedStatusCodeException(String msg, int code, String response) {
		super(msg);
		this.statusCode = code;
		this.response = response;
	}
	
	public ApiUnexpectedStatusCodeException(String msg, Throwable cause, int code, String response) {
		super(msg, cause);
		this.statusCode = code;
		this.response = response;
	}

	public String getResponse() {
		return response;
	}

	public int getStatusCode() {
		return statusCode;
	}
	
}
