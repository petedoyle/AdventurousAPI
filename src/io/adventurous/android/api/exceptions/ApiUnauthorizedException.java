package io.adventurous.android.api.exceptions;

import io.adventurous.android.api.ApiResponse;

/**
 * Used to specify that the API returned a 401 UNAUTHORIZED.
 * @author Pete
 */
@SuppressWarnings("rawtypes")
public class ApiUnauthorizedException extends ApiException {
	private static final long serialVersionUID = 1L;
	
	public ApiUnauthorizedException() {
		super( "Login Required" );
	}
	
	public ApiUnauthorizedException(ApiResponse response) {
		super( response );
	}
	
	public ApiUnauthorizedException(String msg) {
		super(msg);
	}
	
	public ApiUnauthorizedException(String msg, ApiResponse response) {
		super(msg, response);
	}
	
	public ApiUnauthorizedException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public ApiUnauthorizedException(String msg, ApiResponse response, Throwable cause) {
		super(msg, response, cause);
	}
}