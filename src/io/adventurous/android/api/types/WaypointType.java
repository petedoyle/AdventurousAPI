package io.adventurous.android.api.types;

import io.adventurous.android.util.MyLog;

import org.json.JSONException;
import org.json.JSONObject;


import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class WaypointType extends AbstractApiType implements Parcelable {
	
	private static final String TAG = "WaypointType";

	private Uri mRemoteUri;
	private Uri mRemoteParentActivityUri;
	private String mRemoteEtag;
	private long mRemoteLastModified;
	
	private String mName;
	private String mDescription;
	private String mComment;
	private long mDate;
	private double mLatitude;
	private double mLongitude;
	private double mAltitude;
	private double mAccuracy;
	private int mSatelliteCount;
	
	public WaypointType() {
	}
	
	public WaypointType(WaypointType other) {
		mRemoteUri = other.mRemoteUri;
		mRemoteParentActivityUri = other.mRemoteParentActivityUri;
		mRemoteEtag = other.mRemoteEtag;
		mRemoteLastModified = other.mRemoteLastModified;
		mName = other.mName;
		mDescription = other.mDescription;
		mComment = other.mComment;
		mDate = other.mDate;
		mLatitude = other.mLatitude;
		mLongitude = other.mLongitude;
		mAltitude = other.mAltitude;
		mAccuracy = other.mAccuracy;
		mSatelliteCount = other.mSatelliteCount;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeParcelable( mRemoteUri, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeParcelable( mRemoteParentActivityUri, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeString( mRemoteEtag );
		out.writeLong( mRemoteLastModified );
		out.writeString( mName );
		out.writeString( mDescription );
		out.writeString( mComment );
		out.writeLong( mDate );
		out.writeDouble( mLatitude );
		out.writeDouble( mLongitude );
		out.writeDouble( mAltitude );
		out.writeDouble( mAccuracy );
		out.writeInt( mSatelliteCount );
	}
	
	public static final Parcelable.Creator<WaypointType> CREATOR
    = new Parcelable.Creator<WaypointType>() {
		public WaypointType createFromParcel(Parcel in) {
			return new WaypointType(in);
		}

		public WaypointType[] newArray(int size) {
			return new WaypointType[size];
		}
	};

	protected WaypointType(Parcel in) {
		mRemoteUri = in.readParcelable( Uri.class.getClassLoader() );
		mRemoteParentActivityUri = in.readParcelable( Uri.class.getClassLoader() );
		mRemoteEtag = in.readString();
		mRemoteLastModified = in.readLong();
		mName = in.readString();
		mDescription = in.readString();
		mComment = in.readString();
		mDate = in.readLong();
		mLatitude = in.readDouble();
		mLongitude = in.readDouble();
		mAltitude = in.readDouble();
		mAccuracy = in.readDouble();
		mSatelliteCount = in.readInt();
	}

	@Override
	public String toString() {
		return String
			.format(
					"WaypointType [mRemoteUri=%s, mRemoteParentActivityUri=%s, mRemoteEtag=%s, mRemoteLastModified=%s, mName=%s, mDescription=%s, mComment=%s, mDate=%s, mLatitude=%s, mLongitude=%s, mAltitude=%s, mAccuracy=%s, mSatelliteCount=%s]",
					mRemoteUri, mRemoteParentActivityUri, mRemoteEtag, mRemoteLastModified, mName, mDescription,
					mComment, mDate, mLatitude, mLongitude, mAltitude, mAccuracy, mSatelliteCount );
	}
	
	@Override
	public JSONObject getCreationJSON() {
		try {
			return asJson();
		} catch( JSONException e ) {
			MyLog.e( TAG, "Error creating JSON for waypoint creation" );
			return null;
		}
	}

	public JSONObject getUpdateJSON() {
		try {
			return asJson();
		} catch( JSONException e ) {
			MyLog.e( TAG, "Error creating JSON for waypoint update" );
			return null;
		}
	}
	
	private JSONObject asJson() throws JSONException {
		
		if( mRemoteParentActivityUri == null ) {
			MyLog.e( TAG, "parent_activity is not set in update or creation JSON.  Expect API call to fail" );
		}
		
		JSONObject json = new JSONObject();
		
		JSONObject details = new JSONObject();
		details.put( "parent_activity", mRemoteParentActivityUri );
		details.put( "name", mName );
		details.put( "description", mDescription );
		details.put( "comment", mComment );
		details.put( "latitude", mLatitude );
		details.put( "longitude", mLongitude );
		details.put( "altitude", mAltitude );
		details.put( "date", mDate );
		details.put( "horizontal_accuracy", mAccuracy );
		details.put( "satellite_count", mSatelliteCount );
		
		json.put( "waypoint", details );
		
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

	public Uri getRemoteParentActivityUri() {
		return mRemoteParentActivityUri;
	}

	public void setRemoteParentActivityUri(Uri parentActivityRef) {
		this.mRemoteParentActivityUri = parentActivityRef;
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

	public String getComment() {
		return mComment;
	}

	public void setComment(String comment) {
		this.mComment = comment;
	}

	public long getDate() {
		return mDate;
	}

	public void setDate(long date) {
		this.mDate = date;
	}

	public double getLatitude() {
		return mLatitude;
	}

	public void setLatitude(double latitude) {
		this.mLatitude = latitude;
	}

	public double getLongitude() {
		return mLongitude;
	}

	public void setLongitude(double longitude) {
		this.mLongitude = longitude;
	}

	public double getAltitude() {
		return mAltitude;
	}

	public void setAltitude(double altitude) {
		this.mAltitude = altitude;
	}

	public double getAccuracy() {
		return mAccuracy;
	}

	public void setAccuracy(double mAccuracy) {
		this.mAccuracy = mAccuracy;
	}

	public int getSatelliteCount() {
		return mSatelliteCount;
	}

	public void setSatelliteCount(int mSatelliteCount) {
		this.mSatelliteCount = mSatelliteCount;
	}
}