package io.adventurous.android.api.types;

import io.adventurous.android.util.MyLog;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;


import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class ActivityType extends AbstractApiType implements Parcelable {
	
	private static final String TAG = "ActivityType";
	
	private Uri mRemoteParentAdventureUri;
	
	private Uri mRemoteUri;
	private String mRemoteEtag;
	private long mRemoteLastModified;
	private long mStartDate;
	private long mStopDate;
	private String mName;
	private String mDescription;
	private String mType;
	private Uri mThumbUri;

	private List<WaypointType> mWaypoints = new ArrayList<WaypointType>();
	private List<PhotoType> mPhotos = new ArrayList<PhotoType>();
	
	private TrackType mTrack;
	
	public ActivityType() {
	}
	
	public ActivityType(ActivityType other) {
		mRemoteParentAdventureUri = other.mRemoteParentAdventureUri;
		mRemoteUri = other.mRemoteUri;
		mRemoteEtag = other.mRemoteEtag;
		mRemoteLastModified = other.mRemoteLastModified;
		mStartDate = other.mStartDate;
		mStopDate = other.mStopDate;
		mName = other.mName;
		mDescription = other.mDescription;
		mType = other.mType;
		mThumbUri = other.mThumbUri;
		mWaypoints = other.mWaypoints;
		mPhotos = other.mPhotos;
		mTrack = other.mTrack;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeParcelable( mRemoteParentAdventureUri, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeParcelable( mRemoteUri, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeString( mRemoteEtag );
		out.writeLong( mRemoteLastModified );
		out.writeLong( mStartDate );
		out.writeLong( mStopDate );
		out.writeString( mName );
		out.writeString( mDescription );
		out.writeString( mType );
		out.writeParcelable( mThumbUri, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeList( mWaypoints );
		out.writeList( mPhotos );
		out.writeParcelable( mTrack, PARCELABLE_WRITE_RETURN_VALUE );
	}
	
	public static final Parcelable.Creator<ActivityType> CREATOR
    = new Parcelable.Creator<ActivityType>() {
		public ActivityType createFromParcel(Parcel in) {
			return new ActivityType(in);
		}

		public ActivityType[] newArray(int size) {
			return new ActivityType[size];
		}
	};

	protected ActivityType(Parcel in) {
		mRemoteParentAdventureUri = (Uri)in.readParcelable( Uri.class.getClassLoader() );
		mRemoteUri = (Uri)in.readParcelable( Uri.class.getClassLoader() );
		mRemoteEtag = in.readString();
		mRemoteLastModified = in.readLong();
		mStartDate = in.readLong();
		mStopDate = in.readLong();
		mName = in.readString();
		mDescription = in.readString();
		mType = in.readString();
		mThumbUri = (Uri)in.readParcelable( Uri.class.getClassLoader() );
		in.readList( mWaypoints, WaypointType.class.getClassLoader() );
		in.readList( mPhotos, PhotoType.class.getClassLoader() );
		mTrack = (TrackType)in.readParcelable( TrackType.class.getClassLoader() );
	}

	@Override
	public String toString() {
		return String
			.format(
					"ActivityType [mRemoteParentAdventureUri=%s, mRemoteUri=%s, mRemoteEtag=%s, mRemoteLastModified=%s, mStartDate=%s, mStopDate=%s, mName=%s, mDescription=%s, mType=%s, mThumbUri=%s, mWaypoints=%s, mPhotos=%s, mTrack=%s]",
					mRemoteParentAdventureUri, mRemoteUri, mRemoteEtag, mRemoteLastModified, mStartDate, mStopDate,
					mName, mDescription, mType, mThumbUri, mWaypoints, mPhotos, mTrack );
	}
	
	@Override
	public JSONObject getCreationJSON() {
		JSONObject json = new JSONObject();

		try {
			JSONObject details = new JSONObject();
			details.put( "parent_adventure", mRemoteParentAdventureUri.toString() );
			details.put( "startdate", mStartDate );
			details.put( "name", mName );
			details.put( "description", mDescription );
			details.put( "type", mType );

			json.put( "activity", details );
		} catch( JSONException e ) {
			MyLog.e( TAG, "Could not create JSON for Activity", e );
		}

		return json;
	}
	
	@Override
	public JSONObject getUpdateJSON() {
		JSONObject json = new JSONObject();
		
		try {
			JSONObject details = new JSONObject();
			
			if( mRemoteParentAdventureUri != null ) {
				details.put( "parent_adventure", mRemoteParentAdventureUri.toString() );
			}
			
			details.put( "startdate", mStartDate );
			
			if( 0 != mStopDate ) {
				details.put( "stopdate", mStopDate );
			}
			
			details.put( "type", mType );
			details.put( "name", mName );
			details.put( "description", mDescription );
			
			json.put( "activity", details );
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

	public Uri getRemoteParentAdventureUri() {
		return mRemoteParentAdventureUri;
	}

	public void setRemoteParentAdventureUri(Uri remoteParentAdventureUri) {
		this.mRemoteParentAdventureUri = remoteParentAdventureUri;
	}

	public String getRemoteEtag() {
		return mRemoteEtag;
	}

	public void setRemoteEtag(String remoteEtag) {
		this.mRemoteEtag = remoteEtag;
	}

	public long getRemoteLastModified() {
		return mRemoteLastModified;
	}

	public void setRemoteLastModified(long remoteLastModified) {
		this.mRemoteLastModified = remoteLastModified;
	}

	public long getStartDate() {
		return mStartDate;
	}

	public void setStartDate(long startdate) {
		this.mStartDate = startdate;
	}

	public void setStopDate(long stopDate) {
		this.mStopDate = stopDate;
	}

	public long getStopDate() {
		return mStopDate;
	}

	public String getType() {
		return mType;
	}

	public void setType(String type) {
		this.mType = type;
	}
	
	public Uri getThumbUri() {
		return mThumbUri;
	}

	public void setThumbUri(Uri thumbUri) {
		this.mThumbUri = thumbUri;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		this.mDescription = description;
	}
	
	public List<WaypointType> getWaypoints() {
		return mWaypoints;
	}

	public void setWaypoints(List<WaypointType> waypoint) {
		this.mWaypoints = waypoint;
	}

	public List<PhotoType> getPhotos() {
		return mPhotos;
	}

	public void setPhotos(List<PhotoType> photos) {
		this.mPhotos = photos;
	}
	
	public TrackType getTrack() {
		return mTrack;
	}

	public void setTrack(TrackType track) {
		this.mTrack = track;
	}
}