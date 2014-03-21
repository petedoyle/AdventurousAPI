package io.adventurous.android.api.parsers;

import io.adventurous.android.api.types.ImageType;
import io.adventurous.android.util.MyLog;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;


public class ImageParser implements SimpleParser<ImageType> {
	private static final String TAG = "ImageParser";

	@Override
	public ImageType parseType(JsonParser parser) throws JsonParseException, IOException {
		ImageType obj = new ImageType();

		while( parser.nextToken() != JsonToken.END_OBJECT ) {
			String nameField = parser.getCurrentName();
			JsonToken currentToken = parser.nextToken(); // move to value
			if( "type".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setType( parser.getText() );
				}
			} else if( "url".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					try {
						obj.setUrl( new URL( parser.getText() ) );
					} catch( MalformedURLException e ) {
						MyLog.e( TAG, "Failed to parse image url" + parser.getText() + "'", e );
					}
				}
			} else if( "width".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setWidth( parser.getIntValue() );
				}
			} else if( "height".equals( nameField ) ) {
				if( JsonToken.VALUE_NULL != currentToken ) {
					obj.setHeight( parser.getIntValue() );
				}
			} else {
				MyLog.w( TAG, "Unexpected field: '" + nameField + "'.  Skipping children (if any)." );
				parser.skipChildren();
			}
		}

		return obj;
	}

}
