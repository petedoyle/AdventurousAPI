package io.adventurous.android.api.exceptions;

import io.adventurous.android.api.ApiResponse;

@SuppressWarnings("rawtypes")
public class ApiException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private final ApiResponse response;
	
	public ApiException() {
		super();
		this.response = null;
	}
	
	public ApiException(ApiResponse response) {
		super();
		this.response = response;
	}
	
	public ApiException(String msg) {
		super( msg );
		this.response = null;
	}
	
	public ApiException(String msg, ApiResponse response) {
		super( msg );
		this.response = response;
	}
	
	public ApiException(String msg, Throwable cause) {
		super( msg, cause );
		this.response = null;
	}
	
	public ApiException(String msg, ApiResponse response, Throwable cause) {
		super( msg, cause );
		this.response = response;
	}

	public ApiResponse getApiResponse() {
		return response;
	}
}
