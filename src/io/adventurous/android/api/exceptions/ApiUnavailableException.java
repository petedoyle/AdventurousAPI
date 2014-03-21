package io.adventurous.android.api.exceptions;

import io.adventurous.android.api.ApiResponse;

@SuppressWarnings("rawtypes")
public class ApiUnavailableException extends ApiException {
	private static final long serialVersionUID = 1L;
	
	public ApiUnavailableException(String msg) {
		super(msg);
	}
	
	public ApiUnavailableException(ApiResponse response) {
		super( response );
	}
	
	public ApiUnavailableException(String msg, ApiResponse response) {
		super(msg, response);
	}
	
	public ApiUnavailableException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public ApiUnavailableException(String msg, ApiResponse response, Throwable cause) {
		super(msg, response, cause);
	}
}
