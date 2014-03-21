package io.adventurous.android.api.exceptions;

import io.adventurous.android.api.ApiResponse;

/**
 * Used to specify that the API returned a 403.
 * @author Pete
 */
@SuppressWarnings("rawtypes")
public class ApiForbiddenException extends ApiException {
	private static final long serialVersionUID = 1L;
	
	public ApiForbiddenException() {
		super();
	}
	
	public ApiForbiddenException(String msg) {
		super(msg);
	}
	
	public ApiForbiddenException(ApiResponse response) {
		super( response );
	}
	
	public ApiForbiddenException(String msg, ApiResponse response) {
		super(msg, response);
	}
	
	public ApiForbiddenException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public ApiForbiddenException(String msg, ApiResponse response, Throwable cause) {
		super(msg, response, cause);
	}
}
