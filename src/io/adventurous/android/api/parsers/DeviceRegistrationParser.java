package io.adventurous.android.api.parsers;

import io.adventurous.android.api.types.DeviceRegistrationType;
import io.adventurous.android.util.MyLog;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import android.net.Uri;


public class DeviceRegistrationParser extends AbstractParser<DeviceRegistrationType> {

	private final UserParser mUserParser;
	
	public DeviceRegistrationParser() {
		this.mUserParser = new UserParser();
	}
	
	@Override
	public DeviceRegistrationType parseType(JsonParser parser) throws JsonParseException, IOException {
		DeviceRegistrationType obj = new DeviceRegistrationType();

		while (parser.nextToken() != JsonToken.END_OBJECT) {
			String nameField = parser.getCurrentName();
			JsonToken currentToken = parser.nextToken(); // move to value
			
			if( "uri".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setRemoteUri( Uri.parse( parser.getText() ) );
				}
			} else if( "parent_user".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setParentUser( mUserParser.parseType( parser ) );
				}
			} else if( "device_id".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setDeviceId( parser.getText() );
				}
			} else if( "registration_id".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setRegistrationId( parser.getText() );
				}
			} else if( "etag".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setRemoteEtag( parser.getText() );
				}
			} else if( "last_modified".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setRemoteLastModified( parser.getLongValue() );
				}
			} else {
				MyLog.w( TAG, "Unexpected field: '" + nameField + "'.  Skipping children (if any)." );
				parser.skipChildren();
			}
		}
		
		return obj;
	}
}