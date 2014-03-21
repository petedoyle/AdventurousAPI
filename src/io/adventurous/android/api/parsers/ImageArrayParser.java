package io.adventurous.android.api.parsers;

import io.adventurous.android.api.types.ImageType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;


public class ImageArrayParser {
	private final int mExpectedSize;
	private final ImageParser mImageParser;

	/**
	 * @param expectedSize The number of images expected so we can reduce memory usage and create
	 * the exact map size we need.
	 */
	public ImageArrayParser(int expectedSize) {
		this.mExpectedSize = expectedSize;
		this.mImageParser = new ImageParser();
	}
	
	public Map<String, ImageType> parseType(JsonParser parser) throws JsonParseException, IOException {
		Map<String, ImageType> images = new HashMap<String, ImageType>( mExpectedSize );
		
		while( parser.nextToken() != JsonToken.END_ARRAY ) {
			ImageType currentImage = mImageParser.parseType( parser );
			
			images.put( currentImage.getType(), currentImage );
		}
		
		return images;
	}
}