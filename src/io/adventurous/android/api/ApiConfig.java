package io.adventurous.android.api;

public class ApiConfig {
	/**
	 * Set to {@code true} to output wire-level debug logging from HttpClient.
	 * Don't forget to set the logging level for the HttpClient tags:
	 * {@code adb shell setprop log.tag.org.apache.http VERBOSE}
	 * {@code adb shell setprop log.tag.org.apache.http.wire VERBOSE}
	 * {@code adb shell setprop log.tag.org.apache.http.headers VERBOSE}
	 */
	public static final boolean DEBUG_HTTP = false;
}