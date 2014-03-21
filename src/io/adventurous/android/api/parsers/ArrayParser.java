package io.adventurous.android.api.parsers;

import io.adventurous.android.api.DomainObjectQueryListener;
import io.adventurous.android.api.types.ApiType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;


public class ArrayParser<T extends ApiType> implements Parser<T> {

	private final Parser<T> mSubParser;
	
	public ArrayParser(Parser<T> parser) {
		mSubParser = parser;
	}
	
	@Override
	public T parseType(JsonParser parser) throws JsonParseException, IOException {
		throw new JsonParseException( "An ArrayParser expects only JSONArrays", null );
	}
	
	@Override
	public List<T> parseType(JsonParser parser, DomainObjectQueryListener<T> listener) throws JsonParseException, IOException {
		List<T> objects = new ArrayList<T>();

		while (parser.nextToken() != JsonToken.END_ARRAY) {
			objects.add( mSubParser.parseType( parser ) );
		}
		
		return objects;
	}
}