package io.adventurous.android.api.parsers;

import io.adventurous.android.api.types.WaypointType;
import io.adventurous.android.util.MyLog;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import android.net.Uri;


public class WaypointParser extends AbstractParser<WaypointType> {

	@Override
	public WaypointType parseType(JsonParser parser) throws JsonParseException, IOException {
		WaypointType obj = new WaypointType();

		while (parser.nextToken() != JsonToken.END_OBJECT) {
			String nameField = parser.getCurrentName();
			JsonToken currentToken = parser.nextToken(); // move to value
			
			if( "parent_activity".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setRemoteParentActivityUri( Uri.parse( parser.getText() ) );
				}
			} else if( "uri".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setRemoteUri( Uri.parse( parser.getText() ) );
				}
			} else if( "etag".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setRemoteEtag( parser.getText() );
				}
			} else if( "last_modified".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setRemoteLastModified( parser.getLongValue() );
				}
			} else if( "name".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setName( parser.getText() );
				}
			} else if( "description".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setDescription( parser.getText() );
				}
			} else if( "comment".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setComment( parser.getText() );
				}
			} else if( "date".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setDate( parser.getLongValue() );
				}
			} else if( "latitude".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setLatitude( parser.getDoubleValue() );
				}
			} else if( "longitude".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setLongitude( parser.getDoubleValue() );
				}
			} else if( "altitude".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setAltitude( parser.getDoubleValue() );
				}
			} else if( "horizontal_accuracy".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setAccuracy( parser.getDoubleValue() );
				}
			} else if( "satellite_count".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setSatelliteCount( parser.getIntValue() );
				}
			} else {
				MyLog.w( TAG, "Unexpected field: '" + nameField + "'.  Skipping children (if any)." );
				parser.skipChildren();
			}
		}
		
		return obj;
	}

}
