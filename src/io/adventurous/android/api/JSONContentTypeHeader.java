package io.adventurous.android.api;

import org.apache.http.HttpRequest;
import org.apache.http.message.BasicHeader;

/**
 * Add to a {@link HttpRequest} using {@link HttpRequest#addHeader(org.apache.http.Header)} to 
 * set the Content-Type to {@code application/json}.  Utility class so you don't have to
 * specify "Content-Type" and "application/json" as Strings every time. 
 * @author Pete
 */
public class JSONContentTypeHeader extends BasicHeader {
	
	public JSONContentTypeHeader() {
		super( "Content-Type", "application/json" );
	}
	
}
