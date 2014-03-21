package io.adventurous.android.api;

public interface ApiResponse<T> {
	public static final String STAT_OK = "ok";
	public static final String STAT_FAIL = "fail";
	
	public boolean isOK();
	public boolean isFail();
	public ApiError getError();
	public T getPayload();
	
	public int getStatusCode();
	public String getEtagValue();
	public String getLocationValue();
	public long getLastModifiedValue();
	public String getDeviceIdValue();
}