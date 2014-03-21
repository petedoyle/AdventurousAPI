package io.adventurous.android.api.parsers;

import io.adventurous.android.api.types.ActivityType;
import io.adventurous.android.api.types.PhotoType;
import io.adventurous.android.api.types.WaypointType;
import io.adventurous.android.util.MyLog;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import android.net.Uri;


public class ActivityParser extends AbstractParser<ActivityType> {
	private static final String TAG = "ActivityParser";
	
	private final TrackParser mTrackParser;
	private final ArrayParser<WaypointType> mWaypointArrayParser;
	private final ArrayParser<PhotoType> mPhotoArrayParser;
	
	public ActivityParser() {
		this.mTrackParser = new TrackParser();
		this.mWaypointArrayParser = new ArrayParser<WaypointType>( new WaypointParser() );
		this.mPhotoArrayParser = new ArrayParser<PhotoType>( new PhotoParser() );
	}

	@Override
	public ActivityType parseType(JsonParser parser) throws JsonParseException, IOException {
		ActivityType obj = new ActivityType();

		while (parser.nextToken() != JsonToken.END_OBJECT) {
			String nameField = parser.getCurrentName();
			JsonToken currentToken = parser.nextToken(); // move to value
		
			if( "uri".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setRemoteUri( Uri.parse( parser.getText() ) );
				}
			} else if( "parent_adventure".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setRemoteParentAdventureUri( Uri.parse( parser.getText() ) );	
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
			} else if( "startdate".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setStartDate( parser.getLongValue() );
				}
			} else if( "type".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setType( parser.getText() );
				}
			} else if( "thumbnail".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setThumbUri( Uri.parse( parser.getText() ) );
				}
			} else if( "waypoints".equals( nameField ) ) {
				obj.setWaypoints( mWaypointArrayParser.parseType( parser, null ) );
			} else if( "photos".equals( nameField ) ) {
				obj.setPhotos( mPhotoArrayParser.parseType( parser, null ) );
			} else if( "track".equals( nameField ) ) {
				obj.setTrack( mTrackParser.parseType( parser ) );
			} else {
				MyLog.w( TAG, "Unexpected field: '" + nameField + "'.  Skipping children (if any)." );
				parser.skipChildren();
			}
		}
		
		return obj;
	}
}