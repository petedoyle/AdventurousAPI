package io.adventurous.android.api.exceptions;

import io.adventurous.android.api.ApiError;
import io.adventurous.android.api.ApiStatus;
import io.adventurous.android.util.MyLog;


/**
 * Checks for stat=fail in the API response, throws the appropriate {@link ApiException}.
 * @author Pete
 */
public class ApiExceptionFactory {
	public static final String TAG = "ApiExceptionFactory";
	
	private ApiExceptionFactory() {
	}
	
	public static ApiException getException(ApiError error) throws ApiException {
		if( null == error ) {
			MyLog.e( TAG, "API returned stat=fail with no error code/message." );
			throw new ApiException( "Error while connecting to API" );
		}
		
		final String code = error.getCode();
		final String msg = error.getMessage();

		if( ApiError.ERROR_LOGIN_FAILURE.equals( code ) ) {
			return new ApiForbiddenException( msg );
		}
		
		if( ApiError.ERROR_LOGIN_REQUIRED.equals( code ) ) {
			return new ApiUnauthorizedException( msg );
		}
		
		return new ApiException( msg );
	}
	
	public static void throwOnFailure(ApiStatus status) throws ApiException {
		if( ApiStatus.STAT_FAIL.equals( status.getStatus() ) ) {
			throw getException( status.getApiError() );
		}
	}
}
