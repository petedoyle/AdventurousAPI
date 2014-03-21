package io.adventurous.android.api.parsers;

import io.adventurous.android.api.types.sync.SyncDeletedObject;
import io.adventurous.android.util.MyLog;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import android.net.Uri;


public class SyncDeletedObjectParser extends AbstractParser<SyncDeletedObject> {
	private static final String TAG = "SyncDeletedObjectParser";

	@Override
	public SyncDeletedObject parseType(JsonParser parser) throws JsonParseException, IOException {
		SyncDeletedObject obj = new SyncDeletedObject();
		
		while (parser.nextToken() != JsonToken.END_OBJECT) {
			String nameField = parser.getCurrentName();
			JsonToken currentToken = parser.nextToken(); // move to value
			
			if( "uri".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setRemoteUri( Uri.parse( parser.getText() ) );
				}
			} else if( "deleted_time".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setDeletedTime( parser.getLongValue() );
				}
			} else {
				MyLog.w( TAG, "Unexpected field: '" + nameField + "'.  Skipping children (if any)." );
				parser.skipChildren();
			}
		}
		
		return obj;
	}
}