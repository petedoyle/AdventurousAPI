package io.adventurous.android.api.types;

import io.adventurous.android.util.MyLog;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;


public class UserType extends AbstractApiType implements Parcelable {
	
	private static final String TAG = "UserType";
	
	private Uri mRemoteUri;
	private String mRemoteEtag;
	private long mRemoteLastModified;
	private String mUsername;
	private String mFullName;
	private String mBio;
	private String mLocation;
	private URL mUrl;
	private URL mLargeUrl;
	private URL mThumbUrl;
	private String mEmail;
	private int mFollowingCount;
	private int mAdventureCount;
	
	private String mPassword; //only used when creating a user
	
	public UserType() {
	}
	
	public UserType(UserType other) {
		mRemoteUri = other.mRemoteUri;
		mRemoteEtag = other.mRemoteEtag;
		mRemoteLastModified = other.mRemoteLastModified;
		mUsername = other.mUsername;
		mFullName = other.mFullName;
		mBio = other.mBio;
		mLocation = other.mLocation;
		mUrl = other.mUrl;
		mLargeUrl = other.mLargeUrl;
		mThumbUrl = other.mThumbUrl;
		mEmail = other.mEmail;
		mFollowingCount = other.mFollowingCount;
		mAdventureCount = other.mAdventureCount;
		mPassword = other.mPassword;
	}

	@Override
	public String toString() {
		return String
			.format(
					"UserType [mRemoteUri=%s, mRemoteEtag=%s, mRemoteLastModified=%s, mUsername=%s, mFullName=%s, mBio=%s, mLocation=%s, mUrl=%s, mLargeUrl=%s, mThumbUrl=%s, mEmail=%s, mFollowingCount=%s, mAdventureCount=%s, mPassword=%s]",
					mRemoteUri, mRemoteEtag, mRemoteLastModified, mUsername, mFullName, mBio, mLocation, mUrl,
					mLargeUrl, mThumbUrl, mEmail, mFollowingCount, mAdventureCount, mPassword );
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeParcelable( mRemoteUri, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeString( mRemoteEtag );
		out.writeLong( mRemoteLastModified );
		out.writeString( mUsername );
		out.writeString( mFullName );
		out.writeString( mBio );
		out.writeString( mLocation );
		out.writeString( mUrl == null ? null : mUrl.toString() );
		out.writeString( mLargeUrl == null ? null : mLargeUrl.toString() );
		out.writeString( mThumbUrl == null ? null : mThumbUrl.toString() );
		out.writeString( mEmail );
		out.writeInt( mFollowingCount );
		out.writeInt( mAdventureCount );
	}
	
	public static final Parcelable.Creator<UserType> CREATOR
    = new Parcelable.Creator<UserType>() {
		public UserType createFromParcel(Parcel in) {
			return new UserType(in);
		}

		public UserType[] newArray(int size) {
			return new UserType[size];
		}
	};

	protected UserType(Parcel in) {
		mRemoteUri = in.readParcelable( Uri.class.getClassLoader() );
		mRemoteEtag = in.readString();
		mRemoteLastModified = in.readLong();
		mUsername = in.readString();
		mFullName = in.readString();
		mBio = in.readString();
		mLocation = in.readString();
		
		String urlString = in.readString();
		String largeUrlString = in.readString();
		String thumbUrlString = in.readString();
		
		if( null != urlString ) {
			try {
				mUrl = new URL( urlString );
			} catch(MalformedURLException e ) {
				MyLog.e( TAG, "Error parsing user photo url from value: '" + urlString + "'" );		
			}
		}
		
		if( null != largeUrlString ) {
			try {
				mLargeUrl = new URL( largeUrlString );
			} catch(MalformedURLException e ) {
				MyLog.e( TAG, "Error parsing large user photo url from value: '" + largeUrlString + "'" );		
			}
		}
		
		if( null != thumbUrlString ) {
			try {
				mThumbUrl = new URL( thumbUrlString );
			} catch(MalformedURLException e ) {
				MyLog.e( TAG, "Error parsing user photo thumb url from value: '" + largeUrlString + "'" );		
			}
		}
		
		mEmail = in.readString();
		mFollowingCount = in.readInt();
		mAdventureCount = in.readInt();
	}
	
	@Override
	public JSONObject getCreationJSON() {
		JSONObject json = new JSONObject();
		
		try {
			JSONObject details = new JSONObject();
			details.put( "username", mUsername );
			details.put( "password", mPassword );
			details.put( "fullname", mFullName );
			details.put( "email", mEmail );
			
			json.put( "user", details );
		} catch( JSONException e ) {
			MyLog.e( TAG, "", e );
		}
		
		return json;
	}
	
	@Override
	public JSONObject getUpdateJSON() {
		throw new UnsupportedOperationException( "Not yet implemented" );
	}

	@Override
	public Uri getRemoteUri() {
		return mRemoteUri;
	}

	@Override
	public void setRemoteUri(Uri remoteUri) {
		this.mRemoteUri = remoteUri;
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
	
	public String getUsername() {
		return mUsername;
	}

	public void setUsername(String username) {
		this.mUsername = username;
	}

	public String getFullName() {
		return mFullName;
	}

	public void setFullName(String realName) {
		this.mFullName = realName;
	}

	public String getBio() {
		return mBio;
	}

	public void setBio(String bio) {
		this.mBio = bio;
	}

	public String getLocation() {
		return mLocation;
	}

	public void setLocation(String location) {
		this.mLocation = location;
	}

	public URL getUrl() {
		return mUrl;
	}

	public void setUrl(URL url) {
		this.mUrl = url;
	}

	public URL getLargeUrl() {
		return mLargeUrl;
	}

	public void setLargeUrl(URL largeUrl) {
		this.mLargeUrl = largeUrl;
	}

	public URL getThumbUrl() {
		return mThumbUrl;
	}

	public void setThumbUrl(URL thumbUrl) {
		this.mThumbUrl = thumbUrl;
	}

	public String getEmail() {
		return mEmail;
	}

	public void setEmail(String email) {
		this.mEmail = email;
	}

	public int getFollowingCount() {
		return mFollowingCount;
	}

	public void setFollowingCount(int followingCount) {
		this.mFollowingCount = followingCount;
	}

	public int getAdventureCount() {
		return mAdventureCount;
	}

	public void setAdventureCount(int adventureCount) {
		this.mAdventureCount = adventureCount;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setPassword(String password) {
		this.mPassword = password;
	}
}
