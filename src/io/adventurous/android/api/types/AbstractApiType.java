package io.adventurous.android.api.types;

public abstract class AbstractApiType implements ApiType {
	
	public boolean isPersistent() {
		return false;
	}
}