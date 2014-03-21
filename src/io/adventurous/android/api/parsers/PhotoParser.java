package io.adventurous.android.api.parsers;

import io.adventurous.android.api.types.ImageType;
import io.adventurous.android.api.types.PhotoType;
import io.adventurous.android.util.MyLog;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import android.net.Uri;


public class PhotoParser extends AbstractParser<PhotoType> {
	private static final String TAG = "PhotoParser";

	private static final int EXPECTED_IMAGE_COUNT = 14;

	private final ImageArrayParser mImageArrayParser;

	public PhotoParser() {
		this.mImageArrayParser = new ImageArrayParser( EXPECTED_IMAGE_COUNT );
	}

	@Override
	public PhotoType parseType(JsonParser parser) throws JsonParseException, IOException {
		PhotoType obj = new PhotoType();

		while( parser.nextToken() != JsonToken.END_OBJECT ) {
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
			} else if( "title".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setTitle( parser.getText() );
				}
			} else if( "caption".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setCaption( parser.getText() );
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
			} else if( "accuracy".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setAccuracy( parser.getDoubleValue() );
				}
			} else if( "satellitecount".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setSatelliteCount( parser.getIntValue() );
				}
			} else if( "images".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					Map<String, ImageType> images = mImageArrayParser.parseType( parser );

					obj.setThumb32sq( images.get( "thumb32sq" ) );
					obj.setThumb48sq( images.get( "thumb48sq" ) );
					obj.setThumb64sq( images.get( "thumb64sq" ) );
					obj.setThumb96sq( images.get( "thumb96sq" ) );
					obj.setThumb192sq( images.get( "thumb192sq" ) );
					obj.setPreview256( images.get( "preview256" ) );
					obj.setLarge640( images.get( "large640" ) );
					obj.setLarge800( images.get( "large800" ) );
					obj.setLarge900( images.get( "large900" ) );
					obj.setLarge960( images.get( "large960" ) );
					obj.setLarge1120( images.get( "large1120" ) );
					obj.setLarge1280( images.get( "large1280" ) );
					obj.setLarge2048( images.get( "large2048" ) );
					obj.setOriginal( images.get( "original" ) );
				}
			} else {
				MyLog.w( TAG, "Unexpected field: '" + nameField + "'.  Skipping children (if any)." );
				parser.skipChildren();
			}
		}

		return obj;
	}
}
