package io.adventurous.android.api.parsers;

import io.adventurous.android.api.DomainObjectQueryListener;
import io.adventurous.android.api.types.ApiType;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;


public interface Parser<T extends ApiType> {
	public T parseType(JsonParser parser) throws JsonParseException, IOException;
	public List<T> parseType(JsonParser parser, DomainObjectQueryListener<T> listener) throws JsonParseException, IOException;
}