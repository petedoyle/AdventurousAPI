package io.adventurous.android.api;

public class ApiCredentials {
	
	private final String mUsername;
	private final String mPassword;
	
	public ApiCredentials(String username, String password) {
		this.mUsername = username;
		this.mPassword = password;
	}

	public String getUsername() {
		return mUsername;
	}

	public String getPassword() {
		return mPassword;
	}

}
