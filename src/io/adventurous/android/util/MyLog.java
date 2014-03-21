package io.adventurous.android.util;

import io.adventurous.android.api.ApiConfig;

import android.os.Build;
import android.util.Log;

public final class MyLog {

	private static final int DEFAULT_RETURN_VAL = 0;

	private static final String APP_TAG = "Adventurous";
	
	public static final int ASSERT = Log.ASSERT;
	public static final int DEBUG = Log.DEBUG;
	public static final int ERROR = Log.ERROR;
	public static final int INFO = Log.INFO;
	public static final int WARN = Log.WARN;
	public static final int VERBOSE = Log.VERBOSE;

	static {
		if( ApiConfig.DEBUG_HTTP ) {
			java.util.logging.Logger.getLogger("org.apache.http.wire").setLevel(java.util.logging.Level.FINEST);
			java.util.logging.Logger.getLogger("org.apache.http.headers").setLevel(java.util.logging.Level.FINEST);
	
			System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
			System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
			System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "debug");
			System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "debug");
			System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.headers", "debug");
		}
	}
	
	private MyLog() {
	}
	
	public static int d( String tag, String message ) {
		if( Log.isLoggable( APP_TAG, DEBUG ) ) {
			return Log.d( APP_TAG, tag + "::" + message );
		} else {
			return DEFAULT_RETURN_VAL;
		}
	}
	
	public static int e( String tag, String message ) {
		if( Log.isLoggable( APP_TAG, ERROR ) ) {
			return Log.e( APP_TAG, tag + "::" + message );
		} else {
			return DEFAULT_RETURN_VAL;
		}
	}
	
	public static int i( String tag, String message ) {
		if( Log.isLoggable( APP_TAG, INFO ) ) {
			return Log.i( APP_TAG, tag + "::" + message );
		} else {
			return DEFAULT_RETURN_VAL;
		}
	}
	
	public static int v( String tag, String message ) {
		if( Log.isLoggable( APP_TAG, VERBOSE ) ) {
			return Log.v( APP_TAG, tag + "::" + message );
		} else {
			return DEFAULT_RETURN_VAL;
		}
	}
	
	public static int w( String tag, String message ) {
		if( Log.isLoggable( APP_TAG, WARN ) ) {
			return Log.w( APP_TAG, tag + "::" + message );
		} else {
			return DEFAULT_RETURN_VAL;
		}
	}
	
	public static int d( String tag, String message, Throwable t ) {
		if( Log.isLoggable( APP_TAG, DEBUG ) ) {
			return Log.d( APP_TAG, tag + "::" + message, t );
		} else {
			return DEFAULT_RETURN_VAL;
		}
	}
	
	public static int e( String tag, String message, Throwable t ) {
		if( Log.isLoggable( APP_TAG, ERROR ) ) {
			return Log.e( APP_TAG, tag + "::" + message, t );
		} else {
			return DEFAULT_RETURN_VAL;
		}
	}
	
	public static int i( String tag, String message, Throwable t ) {
		if( Log.isLoggable( APP_TAG, INFO ) ) {
			return Log.i( APP_TAG, tag + "::" + message, t );
		} else {
			return DEFAULT_RETURN_VAL;
		}
	}
	
	public static int v( String tag, String message, Throwable t ) {
		if( Log.isLoggable( APP_TAG, VERBOSE ) ) {
			return Log.v( APP_TAG, tag + "::" + message, t );
		} else {
			return DEFAULT_RETURN_VAL;
		}
	}

	public static int w( String tag, String message, Throwable t ) {
		if( Log.isLoggable( APP_TAG, WARN ) ) {
			return Log.w( APP_TAG, tag + "::" + message, t );
		} else {
			return DEFAULT_RETURN_VAL;
		}
	}
	
	public static int wtf (String tag, Throwable tr) {
		if( android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO ) {
			return Log.wtf( APP_TAG, tr );
		} else {
			return Log.e( APP_TAG, "WTF", tr);
		}
	}
	
	public static int wtf (String tag, String msg) {
		if( android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO ) {
			return Log.wtf( APP_TAG, tag + "::" + msg );
		} else {
			return Log.e( APP_TAG, tag + "::WTF!!!::" + msg );
		}
	}
	
	public static int wtf (String tag, String msg, Throwable tr) {
		if( android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO ) {
			return Log.wtf( APP_TAG, tag + "::" + msg, tr );
		} else {
			return Log.e( APP_TAG, tag + "::WTF!!!::" + msg, tr );
		}
	}
	
	public static boolean isLoggable( String tag, int level ) {
			return Log.isLoggable( tag, level );
	}
	
	public static String getStackTraceString( Throwable t ) {
		return Log.getStackTraceString( t );
	}
	
	public static int println( int priority, String tag, String msg ) {
		return Log.println( priority, tag, msg );
	}
}