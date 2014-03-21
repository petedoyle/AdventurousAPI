package io.adventurous.android.api.parsers;

import io.adventurous.android.api.ApiResponse;
import io.adventurous.android.api.DefaultApiError;
import io.adventurous.android.api.DefaultApiResponse;
import io.adventurous.android.api.DomainObjectQueryListener;
import io.adventurous.android.api.exceptions.ApiBadRequestException;
import io.adventurous.android.api.exceptions.ApiException;
import io.adventurous.android.api.exceptions.ApiForbiddenException;
import io.adventurous.android.api.exceptions.ApiUnauthorizedException;
import io.adventurous.android.api.exceptions.ApiUnavailableException;
import io.adventurous.android.api.types.ApiType;
import io.adventurous.android.util.MyLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;


public abstract class AbstractParser<T extends ApiType> implements Parser<T> {
	protected static final String TAG = "AbstractParser";

	public ApiResponse<T> parseResponseObject(HttpResponse response, String apiObjectName) throws IOException, ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, ApiException {
		DefaultApiResponse.Builder<T> builder = parseResponseMetadataForObject( response );
		
		HttpEntity entity = response.getEntity();
		if( null != entity ) {
			JsonFactory f = new JsonFactory();
			JsonParser jp = f.createJsonParser( entity.getContent() );
			
			try {
				if( null != apiObjectName ) {
					long start = System.currentTimeMillis();
	
					jp.nextToken(); // should always return JsonToken.START_OBJECT
					JsonToken token = null;
					while( (token = jp.nextToken()) != JsonToken.END_OBJECT && token != null ) { // Main "rsp" obj.  Make sure to check for null to prevent infinite loops
						String nameField = jp.getCurrentName();
						jp.nextToken(); // move to value, or START_OBJECT/START_ARRAY
						if( "rsp".equals( nameField ) ) {
							while( jp.nextToken() != JsonToken.END_OBJECT ) { // rsp obj
								String rspNameField = jp.getCurrentName();
								jp.nextToken(); // move to value
								
								if( "stat".equals( rspNameField ) ) {
									builder.setApiStatus( jp.getText() );
								} else if( "err".equals( rspNameField ) ) {
									while( jp.nextToken() != JsonToken.END_OBJECT ) { // err obj
										String errNameField = jp.getCurrentName();
										jp.nextToken(); // move to value
										
										String code = null, msg = null;
										if( "code".equals( errNameField ) ) {
											code = jp.getText();
										} else if( "msg".equals( errNameField ) ) {
											msg = jp.getText();
										}
										
										builder.setError( new DefaultApiError( code, msg ) );
									}
								} else if( apiObjectName.equals( rspNameField ) ) {
									builder.setPayload( parseType( jp ) );
								} else {
									MyLog.w( TAG, "Unexpected object name in rsp: '" + rspNameField + "'.  Skipping children (if any)." );
									jp.skipChildren();
								}
							}
						}
					}
					long end = System.currentTimeMillis();
					MyLog.d( TAG, "Parsed JSON to Domain Objects in " + (end-start) + "ms" );
				}
			} catch(JsonParseException e) {
				throw new ApiException("Error parsing JSON", builder.build(), e);
			} finally {
				jp.close();
				entity.consumeContent();
			}
		}
		
		ApiResponse<T> apiResponse = builder.build();
		throwOnErrorStatus( apiResponse );
		return apiResponse;
	}
	
	public ApiResponse<List<T>> parseResponseArray(HttpResponse response, String apiObjectName, DomainObjectQueryListener<T> listener) throws IOException, ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, ApiException {
		DefaultApiResponse.Builder<List<T>> builder = parseResponseMetadataForArray( response );
		
		HttpEntity entity = response.getEntity();
		if( null != entity ) {
			JsonFactory f = new JsonFactory();
			JsonParser jp = f.createJsonParser( entity.getContent() );
			
			try {
				if( null != apiObjectName ) {
					long start = System.currentTimeMillis();
	
					jp.nextToken(); // should always return JsonToken.START_OBJECT
					while( jp.nextToken() != JsonToken.END_OBJECT ) { // main obj
						String nameField = jp.getCurrentName();
						jp.nextToken(); // move to value, or START_OBJECT/START_ARRAY
						if( "rsp".equals( nameField ) ) {
							while( jp.nextToken() != JsonToken.END_OBJECT ) { // rsp obj
								String rspNameField = jp.getCurrentName();
								jp.nextToken(); // move to value
								
								if( "stat".equals( rspNameField ) ) {
									builder.setApiStatus( jp.getText() );
								} else if( "err".equals( rspNameField ) ) {
									while( jp.nextToken() != JsonToken.END_OBJECT ) { // err obj
										String errNameField = jp.getCurrentName();
										jp.nextToken(); // move to value
										
										String code = null, msg = null;
										if( "code".equals( errNameField ) ) {
											code = jp.getText();
										} else if( "msg".equals( errNameField ) ) {
											msg = jp.getText();
										}
										
										builder.setError( new DefaultApiError( code, msg ) );
									}
								} else if( apiObjectName.equals( rspNameField ) ) {
									builder.setPayload( parseType( jp, listener ) );
								} else {
									MyLog.w( TAG, "Unexpected object name in rsp: '" + rspNameField + "'.  Calling skipChildren()" );
									jp.skipChildren();
								}
							}
						}
					}
					long end = System.currentTimeMillis();
					MyLog.d( TAG, "Parsed JSON to Domain Objects in " + (end-start) + "ms" );
				}
			} catch(JsonParseException e) {
				throw new ApiException("Error parsing JSON", builder.build(), e);
			} finally {
				jp.close();
				entity.consumeContent();
			}
		}
		
		ApiResponse<List<T>> apiResponse = builder.build();
		throwOnErrorStatus( apiResponse );
		return apiResponse;
	}
	
	@Override
	public abstract T parseType(JsonParser parser) throws JsonParseException, IOException;

	@Override
	public List<T> parseType(JsonParser parser, DomainObjectQueryListener<T> listener) throws JsonParseException, IOException {
		final List<T> list = new ArrayList<T>();
		
		while( parser.nextToken() != JsonToken.END_ARRAY ) {
			T domainObj = parseType(parser);
			list.add( domainObj );
			if( null != listener ) {
				listener.onResultFound( domainObj, list );
			}
		}
		
		return list;
	}
	
	private DefaultApiResponse.Builder<T> parseResponseMetadataForObject(HttpResponse response) {
		int statusCode = response.getStatusLine().getStatusCode();
		Header[] headers = response.getAllHeaders();
		
		return new DefaultApiResponse.Builder<T>( statusCode, headers );
	}
	
	private DefaultApiResponse.Builder<List<T>> parseResponseMetadataForArray(HttpResponse response) {
		int statusCode = response.getStatusLine().getStatusCode();
		Header[] headers = response.getAllHeaders();
		
		return new DefaultApiResponse.Builder<List<T>>( statusCode, headers );
	}
	
	private void throwOnErrorStatus(ApiResponse<?> apiResponse) throws ApiBadRequestException, ApiForbiddenException, ApiUnauthorizedException, ApiUnavailableException {
		final int statusCode = apiResponse.getStatusCode(); 
		switch(statusCode) {
			case HttpStatus.SC_BAD_REQUEST:         // 400
				throw new ApiBadRequestException( apiResponse );
			case HttpStatus.SC_FORBIDDEN:           // 403
				throw new ApiForbiddenException( apiResponse );
			case HttpStatus.SC_UNAUTHORIZED:        // 401
				throw new ApiUnauthorizedException( apiResponse  );
			case HttpStatus.SC_BAD_GATEWAY:         // 502
			case HttpStatus.SC_SERVICE_UNAVAILABLE: // 503
			case HttpStatus.SC_GATEWAY_TIMEOUT:     // 504
				throw new ApiUnavailableException( apiResponse );
			default:
				break;
		}
	}
}