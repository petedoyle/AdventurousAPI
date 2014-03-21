package io.adventurous.android.api.parsers;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public interface SimpleArrayParser<T> {
	public List<T> parseType(JsonParser parser) throws JsonParseException, IOException;
}