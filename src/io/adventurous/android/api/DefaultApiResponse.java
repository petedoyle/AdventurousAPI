package io.adventurous.android.api;

import io.adventurous.android.api.exceptions.ApiBadRequestException;
import io.adventurous.android.api.exceptions.ApiException;
import io.adventurous.android.api.exceptions.ApiForbiddenException;
import io.adventurous.android.api.exceptions.ApiUnauthorizedException;
import io.adventurous.android.api.exceptions.ApiUnavailableException;

import org.apache.http.Header;


public class DefaultApiResponse<T> implements ApiResponse<T> {
	
	/** The value of the responses's {@code stat} field (i.e. "ok" or "fail") */
	private final String mApiStatus;
	
	/**
	 * The HTTP status code.
	 */
	private final int mStatusCode;
	
	/**
	 * The raw HTTP headers.
	 */
	private final Header[] mHeaders;
	
	private ApiError mError;
	private T mPayload;

	private DefaultApiResponse(Builder<T> builder) {
		this.mApiStatus = builder.apiStatus;
		this.mStatusCode = builder.statusCode;
		this.mHeaders = builder.headers;
		this.mPayload = builder.payload;
		
		this.mError = builder.error;
	}
	
	@Override
	public boolean isOK() {
		return STAT_OK.equals( mApiStatus );
	}

	@Override
	public boolean isFail() {
		return STAT_FAIL.equals( mApiStatus );
	}
	
	@Override
	public ApiError getError() {
		return mError;
	}
	
	@Override
	public T getPayload() {
		return mPayload;
	}
	
	@Override
	public int getStatusCode() {
		return mStatusCode;
	}

	@Override
	public String getEtagValue() {
		Header etagHeader = getFirstHeader( "ETag" );
		
		return etagHeader == null ? null : etagHeader.getValue();
	}
	
	private Header getFirstHeader(String name) {
		for( int i = 0; i < mHeaders.length; i++ ) {
			if( mHeaders[i].getName().equalsIgnoreCase( name ) ) {
				return mHeaders[i];
			}
		}
		return null;
	}

	@Override
	public String getLocationValue() {
		Header locationHeader = getFirstHeader( "Location" );
		
		if( locationHeader == null ) { 
			return null;
		} else {
			return locationHeader.getValue();
		}
	}
	
	@Override
	public long getLastModifiedValue() {
		Header lastModifiedHeader = getFirstHeader( "Last-Modified" );
		
		if( lastModifiedHeader == null ) {
			return 0L;
		} else {
			return Long.parseLong( lastModifiedHeader.getValue() );
		}
	}
	
	@Override
	public String getDeviceIdValue() {
		Header deviceIdHeader = getFirstHeader( ApiConstants.HEADER_DEVICE_ID );
		
		if( deviceIdHeader == null ) { 
			return null;
		} else {
			return deviceIdHeader.getValue();
		}
	}
	
	public Header[] getAllHeaders() {
		return mHeaders;
	}

	@Override
	public String toString() {
		return "DefaultApiResponse[status code: " + mStatusCode + ", Location: " + getLocationValue() + ", ETag: " + getEtagValue() + "]";
	}
	
	public static class Builder<U> {
		// required
		private final int statusCode;
		private final Header[] headers;
		
		// optional
		private String apiStatus;
		private ApiError error;
		private U payload;
		
		public Builder(int statusCode, Header[] headers) {
			this.statusCode = statusCode;
			this.headers = headers;
			
			this.error = null;
			this.payload = null;
		}
		
		public Builder<U> setApiStatus(String apiStatus) {
			this.apiStatus = apiStatus;
			return this;
		}
		
		public Builder<U> setError(ApiError error) {
			this.error = error;
			return this;
		}
		
		public Builder<U> setPayload(U obj) {
			this.payload = obj;
			return this;
		}
		
		public ApiResponse<U> build() throws ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, ApiException {
			final DefaultApiResponse<U> apiResponse = new DefaultApiResponse<U>(this);
			
			switch( statusCode ) {
				case 400:
					throw new ApiBadRequestException( apiResponse );
				case 401:
					throw new ApiUnauthorizedException( apiResponse );
				case 403:
					throw new ApiForbiddenException( apiResponse );
				case 500:
					throw new ApiException( apiResponse );
				case 502:
				case 503:
					throw new ApiUnavailableException( apiResponse );
					
				default:
					return apiResponse;
			}
		}
	}
}
