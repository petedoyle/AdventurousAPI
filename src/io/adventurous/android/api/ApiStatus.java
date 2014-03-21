package io.adventurous.android.api;

public class ApiStatus {
	public static final String STAT_OK = "ok";
	public static final String STAT_FAIL = "fail";
	
	private final String mStatus;
	private final ApiError mApiError;
	
	public ApiStatus(String status, String code) {
		this.mApiError = null;
		this.mStatus = status;
	}

	public boolean isOk() {
		return STAT_OK.equals( mStatus );
	}
	
	public boolean isFail() {
		return STAT_FAIL.equals( mStatus );
	}
	
	public String getStatus() {
		return mStatus;
	}

	public ApiError getApiError() {
		return mApiError;
	}
}