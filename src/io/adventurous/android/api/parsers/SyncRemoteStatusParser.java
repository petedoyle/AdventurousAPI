package io.adventurous.android.api.parsers;

import io.adventurous.android.api.types.sync.SyncDeletedObject;
import io.adventurous.android.api.types.sync.SyncRemoteStatus;
import io.adventurous.android.api.types.sync.SyncUpdatedObject;
import io.adventurous.android.util.MyLog;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;


public class SyncRemoteStatusParser extends AbstractParser<SyncRemoteStatus> {
	private static final String TAG = "SyncRemoteStatusParser";
	
	private final ArrayParser<SyncUpdatedObject> mSyncUpdatedObjectParser;
	private final ArrayParser<SyncDeletedObject> mSyncDeletedObjectParser;
	
	public SyncRemoteStatusParser() {
		this.mSyncUpdatedObjectParser = new ArrayParser<SyncUpdatedObject>( new SyncUpdatedObjectParser() );
		this.mSyncDeletedObjectParser = new ArrayParser<SyncDeletedObject>( new SyncDeletedObjectParser() );
	}

	@Override
	public SyncRemoteStatus parseType(JsonParser parser) throws JsonParseException, IOException {
		SyncRemoteStatus obj = new SyncRemoteStatus();
		
		while (parser.nextToken() != JsonToken.END_OBJECT) {
			String nameField = parser.getCurrentName();
			JsonToken currentToken = parser.nextToken(); // move to value
			
			if( "server_time".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setServerTime( parser.getLongValue() );
				}
			} else if( "updates".equals( nameField ) ) { // ASSUMPTION: always a JSONArray
				obj.setUpdated( mSyncUpdatedObjectParser.parseType( parser, null ) );
			} else if( "deletes".equals( nameField ) ) { // ASSUMPTION: always a JSONArray
				obj.setDeleted( mSyncDeletedObjectParser.parseType( parser, null ) );
			} else {
				MyLog.w( TAG, "Unexpected field: '" + nameField + "'.  Skipping children (if any)." );
				parser.skipChildren();
			}
		}
		
		return obj;
	}
}