package io.adventurous.android.api.parsers;

import io.adventurous.android.api.types.Coordinate;
import io.adventurous.android.api.types.TrackSegment;
import io.adventurous.android.api.types.TrackType;
import io.adventurous.android.util.MyLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import android.net.Uri;


public class TrackParser extends AbstractParser<TrackType> {
	private static final String TAG = "TrackParser";

	@Override
	public TrackType parseType(JsonParser parser) throws JsonParseException, IOException {
		TrackType obj = new TrackType();
		
		// Check for VALUE_NULL to prevent infinite loop checking for END_OBJECT
		if( parser.getCurrentToken() == JsonToken.VALUE_NULL ) {
			return null;
		}
		
		while ( parser.nextToken() != JsonToken.END_OBJECT ) {
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
			} else if( "pointcount".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setPointCount( parser.getIntValue() );
				}
			} else if( "type".equals( nameField ) ) {
				// ignore (we don't need to keep track of this right now, but don't want to log it below either)
			} else if( "maxlat".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setMaxLat( parser.getDoubleValue() );
				}
			} else if( "minlat".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setMinLat( parser.getDoubleValue() );
				}
			} else if( "maxlng".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setMaxLng( parser.getDoubleValue() );
				}
			} else if( "minlng".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setMinLng( parser.getDoubleValue() );
				}
			} else if( "start_time".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setStartTime( parser.getLongValue() );
				}
			} else if( "coordinates".equals( nameField ) ) {
				List<TrackSegment> segments;
				
				long pointCount = obj.getPointCount();
				if( pointCount > 0 ) {
					segments = new ArrayList<TrackSegment>( (int)pointCount );
				} else {
					segments = new ArrayList<TrackSegment>();
				}
				
				while (parser.nextToken() != JsonToken.END_ARRAY) { // step into segments array
					TrackSegment segment = new TrackSegment();
					
					while (parser.nextToken() != JsonToken.END_ARRAY) { // step into segment array
						while (parser.nextToken() != JsonToken.END_ARRAY) { // step into coord array
							double lat = parser.getDoubleValue(); // already at first value, no need to call nextToken()
							
							parser.nextToken(); // move to lng
							double lng = parser.getDoubleValue();
							
							parser.nextToken(); // move to alt
							double alt = parser.getDoubleValue();
							
							parser.nextToken(); // move to time
							long time = parser.getLongValue();
							
							segment.addCoordinate( new Coordinate( lng, lat, alt, time ) );
						}
					}
					segments.add( segment );
				}
				
				obj.setSegments( segments );
			} else if( "maxlat".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setMaxLat( parser.getDoubleValue() );
				}
			} else if( "minlat".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setMinLat( parser.getDoubleValue() );
				}
			} else if( "maxlng".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setMaxLng( parser.getDoubleValue() );
				}
			} else if( "minlng".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setMinLng( parser.getDoubleValue() );
				}
			} else {
				MyLog.w( TAG, "Unexpected field: '" + nameField + "'.  Skipping children (if any)." );
				parser.skipChildren();
			}
		}

		return obj;
	}

}
