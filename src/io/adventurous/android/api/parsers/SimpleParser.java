package io.adventurous.android.api.parsers;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public interface SimpleParser<T> {
	public T parseType(JsonParser parser) throws JsonParseException, IOException;
}