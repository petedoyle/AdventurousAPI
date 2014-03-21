package io.adventurous.android.api;

public class DefaultApiError implements ApiError {
	
	private final String code;
	private final String message;
	
	public DefaultApiError(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	@Override
	public String getCode() {
		return code;
	}
	@Override
	public String getMessage() {
		return message;
	}
	
	
}
