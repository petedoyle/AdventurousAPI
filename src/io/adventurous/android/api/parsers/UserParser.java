package io.adventurous.android.api.parsers;

import io.adventurous.android.api.types.UserType;
import io.adventurous.android.util.MyLog;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import android.net.Uri;


public class UserParser extends AbstractParser<UserType> {
	private static final String TAG = "UserParser";

	@Override
	public UserType parseType(JsonParser parser) throws JsonParseException, IOException {
		UserType user = new UserType();
		
		while (parser.nextToken() != JsonToken.END_OBJECT) {
			String nameField = parser.getCurrentName();
			JsonToken currentToken = parser.nextToken(); // move to value
			
			if( "uri".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					user.setRemoteUri( Uri.parse( parser.getText() ) );
				}
			} else if( "etag".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					user.setRemoteEtag( parser.getText() );
				}
			} else if( "last_modified".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					user.setRemoteLastModified( parser.getLongValue() );
				}
			} else if( "username".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					user.setUsername( parser.getText() );
				}
			} else if( "fullname".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					user.setFullName( parser.getText() );
				}
			} else if( "bio".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					user.setBio( parser.getText() );
				}
			} else if( "location".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					user.setLocation( parser.getText() );
				}
			} else if( "url".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					String url = parser.getText();
					try {
						user.setUrl( new URL( url ) );
					} catch (MalformedURLException e) {
						MyLog.e( TAG, "Invalid url: " + url + " for user " + user.getUsername() );
					}
				}
			} else if( "photo_large".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					String photoLargeUrl = parser.getText();
					try { 
						user.setLargeUrl( new URL( photoLargeUrl ) );
					} catch (MalformedURLException e) {
						MyLog.e( TAG, "Invalid large photo url: " + photoLargeUrl + " for user " + user.getUsername() );
					}
				}
			} else if( "photo_thumbnail".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					String photoThumbUrl = parser.getText();
					try {
						user.setThumbUrl( new URL( photoThumbUrl ) );
					} catch (MalformedURLException e) {
						MyLog.e( TAG, "Invalid thumb url: " + photoThumbUrl + " for user " + user.getUsername() );
					}
				}
			} else if( "email".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					user.setEmail( parser.getText() );
				}
			} else if( "following_count".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					user.setFollowingCount( parser.getIntValue() );
				}
			} else if( "adventure_count".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					user.setAdventureCount( parser.getIntValue() );
				}
			} else {
				MyLog.w( TAG, "Unexpected field: '" + nameField + "'.  Skipping children (if any)." );
				parser.skipChildren();
			}
		}
		return user;
	}

}
