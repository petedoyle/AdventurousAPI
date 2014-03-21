package io.adventurous.android.api.types;

import io.adventurous.android.util.MyLog;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;


import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class AdventureType extends AbstractApiType implements Parcelable {
	
	private static final String TAG = "AdventureType";
	
	private long mStartDate;
	private String mRemoteEtag;
	private long mRemoteLastModified;
	private String mDescription;
	private String mName;
	private Uri mRemoteUri;
	private UserType mParentUser;
	private Uri mThumbUri;
	private List<ActivityType> mActivities = new ArrayList<ActivityType>();
	
	public AdventureType() {
	}
	
	public AdventureType(AdventureType other) {
		mStartDate = other.mStartDate;
		mRemoteEtag = other.mRemoteEtag;
		mRemoteLastModified = other.mRemoteLastModified;
		mDescription = other.mDescription;
		mName = other.mName;
		mRemoteUri = other.mRemoteUri;
		mParentUser = other.mParentUser;
		mThumbUri = other.mThumbUri;
		mActivities = other.mActivities;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeLong( mStartDate );
		out.writeString( mRemoteEtag );
		out.writeLong( mRemoteLastModified );
		out.writeString( mDescription );
		out.writeString( mName );
		out.writeParcelable( mRemoteUri, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeParcelable( mParentUser, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeParcelable( mThumbUri, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeList( mActivities );
	}
	
	public static final Parcelable.Creator<AdventureType> CREATOR
    = new Parcelable.Creator<AdventureType>() {
		public AdventureType createFromParcel(Parcel in) {
			return new AdventureType(in);
		}

		public AdventureType[] newArray(int size) {
			return new AdventureType[size];
		}
	};

	protected AdventureType(Parcel in) {
		mStartDate = in.readLong();
		mRemoteEtag = in.readString();
		mRemoteLastModified = in.readLong();
		mDescription = in.readString();
		mName = in.readString();
		mRemoteUri = (Uri)in.readParcelable( Uri.class.getClassLoader() );
		mParentUser = in.readParcelable( UserType.class.getClassLoader() );
		mThumbUri = (Uri)in.readParcelable( Uri.class.getClassLoader() );
		in.readList( mActivities, ActivityType.class.getClassLoader() );
	}

	@Override
	public String toString() {
		return String
			.format(
					"AdventureType [mStartDate=%s, mRemoteEtag=%s, mRemoteLastModified=%s, mDescription=%s, mName=%s, mRemoteUri=%s, mParentUser=%s, mThumbUri=%s, mActivities=%s]",
					mStartDate, mRemoteEtag, mRemoteLastModified, mDescription, mName, mRemoteUri, mParentUser,
					mThumbUri, mActivities );
	}
	
	@Override
	public JSONObject getCreationJSON() {
		JSONObject json = new JSONObject();

		try {
			JSONObject details = new JSONObject();
			details.put( "name", mName );
			details.put( "startdate", mStartDate );
			details.put( "description", mDescription );

			json.put( "adventure", details );
		} catch( JSONException e ) {
			MyLog.e( TAG, "Error while creating creation JSON for Adventure: " + toString(), e );
		}

		return json;
	}
	
	public JSONObject getUpdateJSON() {
		JSONObject json = new JSONObject();
		
		try {
			JSONObject details = new JSONObject();
			
			details.put( "name", mName );
			details.put( "description", mDescription );
			details.put( "startdate", mStartDate );
			
			json.put( "adventure", details );
		} catch( JSONException e ) {
			MyLog.e( TAG, "Error creating JSON for activity update", e );
		}
		
		return json;
	}

	@Override
	public Uri getRemoteUri() {
		return mRemoteUri;
	}

	@Override
	public void setRemoteUri(Uri uri) {
		this.mRemoteUri = uri;
	}

	public long getStartDate() {
		return mStartDate;
	}
	
	public void setStartDate(long startDate) {
		this.mStartDate = startDate;
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

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		this.mDescription = description;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public UserType getParentUser() {
		return mParentUser;
	}

	public void setParentUser(UserType parentUser) {
		this.mParentUser = parentUser;
	}

	public Uri getThumbUri() {
		return mThumbUri;
	}

	public void setThumbUri(Uri thumbUri) {
		this.mThumbUri = thumbUri;
	}

	public boolean addActivity(ActivityType activity) {
		return mActivities.add( activity );
	}
	
	public boolean removeActivity(ActivityType activity) {
		return mActivities.remove( activity );
	}
	
	public void setActivities(List<ActivityType> activities) {
		this.mActivities = activities;
	}
	
	public List<ActivityType> getActivities() {
		return mActivities;
	}
}