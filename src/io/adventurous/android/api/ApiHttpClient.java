/*
 * Copyright (C) 2008 Romain Guy
 * Copyright (C) 2010 Pete Doyle 
 *
 * Modified version of Romain Guy's org.curiouscreature.android.shelves.util.HttpManager.
 * Modified by Pete Doyle
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.adventurous.android.api;

import io.adventurous.android.util.MyLog;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;


public class ApiHttpClient {
	
	private static final String TAG = "ApiHttpClient";
	
	private static final String API_USER_AGENT = "Adventurous/1.0";
	private static final int DEFAULT_CONNECTION_TIMEOUT_MS = 20 * 1000;
	private static final int DEFAULT_SOCKET_TIMEOUT_MS = 20 * 1000;
	
	private static final DefaultHttpClient mClient;

	static {
		final HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion( params, HttpVersion.HTTP_1_1 );
		HttpProtocolParams.setContentCharset( params, "UTF-8" );

		HttpConnectionParams.setStaleCheckingEnabled( params, false );
		HttpConnectionParams.setConnectionTimeout( params, DEFAULT_CONNECTION_TIMEOUT_MS );
		HttpConnectionParams.setSoTimeout( params, DEFAULT_SOCKET_TIMEOUT_MS );
		HttpConnectionParams.setSocketBufferSize( params, 8192 );

		HttpClientParams.setRedirecting( params, false );

		HttpProtocolParams.setUserAgent( params, API_USER_AGENT );

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register( new Scheme( "http", PlainSocketFactory.getSocketFactory(), 80 ) );
		schemeRegistry.register( new Scheme( "https", SSLSocketFactory.getSocketFactory(), 443 ) );

		ClientConnectionManager manager = new ThreadSafeClientConnManager( params, schemeRegistry );
		mClient = new DefaultHttpClient( manager, params );
	}

	private ApiHttpClient() {
	}

	public static HttpResponse execute(HttpRequestBase request, ApiCredentials credentials, String deviceId) throws IOException {
		MyLog.d( TAG, request.getMethod() + ": " + request.getURI() );
		setApiCredentials( credentials, request );
		setDeviceIdHeader( request, deviceId );
		
		long start = System.currentTimeMillis();
		HttpResponse response = mClient.execute( request );
		long end = System.currentTimeMillis();
		MyLog.d( TAG, request.getMethod() + ": " + request.getURI() + " - took " + (end-start) + "ms" );
		return response;
	}

	private static void setApiCredentials(ApiCredentials credentials, HttpRequestBase request) {
		if( credentials == null || credentials instanceof NullApiCredentials) {
			mClient.getCredentialsProvider().clear();
		} else {
			String host = request.getURI().getHost();
			int authPort = request.getURI().getPort();
			
			mClient.getCredentialsProvider().setCredentials( 
					new AuthScope( host, authPort ),
					new UsernamePasswordCredentials( credentials.getUsername(), credentials.getPassword() ) );
		}
	}
	
	private static void setDeviceIdHeader(HttpRequestBase request, String deviceId) {
		if( null != deviceId ) {
			request.setHeader( ApiConstants.HEADER_DEVICE_ID, deviceId );
		}
	}
}