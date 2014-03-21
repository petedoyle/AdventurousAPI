package io.adventurous.android.api.types;

import io.adventurous.android.util.MyLog;

import org.json.JSONException;
import org.json.JSONObject;


import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class DeviceRegistrationType extends AbstractApiType implements Parcelable {
	
	private static final String TAG = "DeviceRegistrationType";
	
	private Uri mRemoteUri;
	private UserType mParentUser;
	private String mRemoteEtag;
	private long mRemoteLastModified;
	
	private String mDeviceId;
	private String mRegistrationId;
	
	public DeviceRegistrationType() {
	}
	
	public DeviceRegistrationType(DeviceRegistrationType other) {
		mRemoteUri = other.mRemoteUri;
		mParentUser = other.mParentUser;
		mRemoteEtag = other.mRemoteEtag;
		mRemoteLastModified = other.mRemoteLastModified;
		
		mDeviceId = other.mDeviceId;
		mRegistrationId = other.mRegistrationId;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeParcelable( mRemoteUri, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeParcelable( mParentUser, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeString( mRemoteEtag );
		out.writeLong( mRemoteLastModified );
		out.writeString( mDeviceId );
		out.writeString( mRegistrationId );
	}
	
	public static final Parcelable.Creator<DeviceRegistrationType> CREATOR
    = new Parcelable.Creator<DeviceRegistrationType>() {
		public DeviceRegistrationType createFromParcel(Parcel in) {
			return new DeviceRegistrationType(in);
		}

		public DeviceRegistrationType[] newArray(int size) {
			return new DeviceRegistrationType[size];
		}
	};

	protected DeviceRegistrationType(Parcel in) {
		mRemoteUri = in.readParcelable( Uri.class.getClassLoader() );
		mParentUser = in.readParcelable( UserType.class.getClassLoader() );
		mRemoteEtag = in.readString();
		mRemoteLastModified = in.readLong();
		mDeviceId = in.readString();
		mRegistrationId = in.readString();
	}

	@Override
	public String toString() {
		return String
			.format(
					"DeviceRegistrationType [mRemoteUri=%s, mParentUser=%s, mRemoteEtag=%s, mRemoteLastModified=%s, mDeviceId=%s, mRegistrationId=%s]",
					mRemoteUri, mParentUser, mRemoteEtag, mRemoteLastModified, mDeviceId, mRegistrationId );
	}
	
	@Override
	public JSONObject getCreationJSON() {
		JSONObject json = null;
		try {
			json = new JSONObject();
			
			JSONObject details = new JSONObject();
			details.put( "device_id", mDeviceId );
			details.put( "registration_id", mRegistrationId );
			
			json.put( "registration", details );
		} catch( JSONException e ) {
			MyLog.e( TAG, "Error creating JSON for deviceregistration creation" );
		}
		
		return json;
	}

	public JSONObject getUpdateJSON() {
		return getCreationJSON();
	}
	
	@Override
	public Uri getRemoteUri() {
		return mRemoteUri;
	}

	@Override
	public void setRemoteUri(Uri uri) {
		this.mRemoteUri = uri;
	}

	public UserType getParentUser() {
		return mParentUser;
	}

	public void setParentUser(UserType mParentUser) {
		this.mParentUser = mParentUser;
	}	

	@Override
	public String getRemoteEtag() {
		return mRemoteEtag;
	}

	@Override
	public void setRemoteEtag(String remoteEtag) {
		this.mRemoteEtag = remoteEtag;
	}

	@Override
	public long getRemoteLastModified() {
		return mRemoteLastModified;
	}

	@Override
	public void setRemoteLastModified(long remoteLastModified) {
		this.mRemoteLastModified = remoteLastModified;
	}
	
	public String getDeviceId() {
		return mDeviceId;
	}

	public void setDeviceId(String mDeviceId) {
		this.mDeviceId = mDeviceId;
	}

	public String getRegistrationId() {
		return mRegistrationId;
	}

	public void setRegistrationId(String mRegistrationId) {
		this.mRegistrationId = mRegistrationId;
	}
}