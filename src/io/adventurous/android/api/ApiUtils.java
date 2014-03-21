package io.adventurous.android.api;

import io.adventurous.android.util.MyLog;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;


public final class ApiUtils {
	private static final String TAG = "ApiUtils";
	
	public static HttpGet createJsonGet(String uri) {
		HttpGet get = new HttpGet( uri );
		get.setHeader( new JSONContentTypeHeader() );
		return get;
	}
	
	public static HttpPost createJsonPost(String uri, JSONObject json) {
		HttpPost post = new HttpPost( uri );
		
		try {
			post.setEntity( new StringEntity( json.toString() ) );
		} catch (UnsupportedEncodingException e) {
			MyLog.e( TAG, "Unsupported Encoding" , e);
		}
		post.setHeader( new JSONContentTypeHeader() );
	
		return post;
	}
	
	public static HttpPut createJsonPut(String uri, JSONObject json) {
		HttpPut put = new HttpPut( uri );
		
		try {
			put.setEntity( new StringEntity( json.toString() ) );
		} catch (UnsupportedEncodingException e) {
			MyLog.e( TAG, "Unsupported Encoding" , e);
		}
		put.setHeader( new JSONContentTypeHeader() );
	
		return put;
	}
	
	public static HttpDelete createJsonDelete(String uri) {
		HttpDelete delete = new HttpDelete( uri );
		delete.setHeader( new JSONContentTypeHeader() );
		return delete;
	}
}
