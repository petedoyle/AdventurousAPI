package io.adventurous.android.api;

public interface ApiError {
	public static final String ERROR_LOGIN_REQUIRED = "login.required";
	public static final String ERROR_LOGIN_FAILURE = "login.failure";
	
	public String getCode();
	public String getMessage();
}
