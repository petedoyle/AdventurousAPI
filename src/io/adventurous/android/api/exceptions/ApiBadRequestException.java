package io.adventurous.android.api.exceptions;

import io.adventurous.android.api.ApiResponse;

@SuppressWarnings("rawtypes")
public class ApiBadRequestException extends ApiException {
	private static final long serialVersionUID = 1L;
	
	public ApiBadRequestException() {
		super();
	}
	
	public ApiBadRequestException(ApiResponse response) {
		super( response );
	}
	
	public ApiBadRequestException(String msg) {
		super(msg);
	}
	
	public ApiBadRequestException(String msg, ApiResponse response) {
		super(msg, response);
	}
	
	public ApiBadRequestException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public ApiBadRequestException(String msg, ApiResponse response, Throwable cause) {
		super(msg, response, cause);
	}
}
