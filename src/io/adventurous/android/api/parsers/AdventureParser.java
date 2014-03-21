package io.adventurous.android.api.parsers;

import io.adventurous.android.api.types.ActivityType;
import io.adventurous.android.api.types.AdventureType;
import io.adventurous.android.util.MyLog;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import android.net.Uri;


public class AdventureParser extends AbstractParser<AdventureType> {
	
	private static final String TAG = "AdventureParser";
	
	private final UserParser mUserParser;
	private final ArrayParser<ActivityType> mActivityArrayParser;
	
	public AdventureParser() {
		this.mUserParser = new UserParser();
		this.mActivityArrayParser = new ArrayParser<ActivityType>( new ActivityParser() );
	}

	@Override
	public AdventureType parseType(JsonParser parser) throws JsonParseException, IOException {
		AdventureType adventure = new AdventureType();
		
		while (parser.nextToken() != JsonToken.END_OBJECT) {
			String nameField = parser.getCurrentName();
			JsonToken currentToken = parser.nextToken(); // move to value
			
			if( "parent_user".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					adventure.setParentUser( mUserParser.parseType( parser ) );
				}
			} else if( "uri".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					adventure.setRemoteUri( Uri.parse( parser.getText() ) );
				}
			} else if( "name".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					adventure.setName( parser.getText() );
				}
			} else if( "etag".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					adventure.setRemoteEtag( parser.getText() );
				}
			} else if( "last_modified".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					adventure.setRemoteLastModified( parser.getLongValue() );
				}
			} else if( "description".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					adventure.setDescription( parser.getText() );
				}
			} else if( "startdate".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					adventure.setStartDate( parser.getLongValue() );
				}
			} else if( "thumbnail".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					adventure.setThumbUri( Uri.parse( parser.getText() ) );
				}
			} else if( "activities".equals( nameField ) ) {
				adventure.setActivities( mActivityArrayParser.parseType( parser, null ) );
			} else {
				MyLog.w( TAG, "Unexpected field: '" + nameField + "'.  Skipping children." );
				parser.skipChildren();
			}
		}
		
		return adventure;
	}
}