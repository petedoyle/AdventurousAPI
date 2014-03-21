package io.adventurous.android.api.types;

import io.adventurous.android.util.MyLog;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class TrackType extends AbstractApiType implements Parcelable {
	
	private static final String TAG = "TrackType";
	
	private Uri mRemoteParentActivityUri;
	private Uri mRemoteUri;
	private String mRemoteEtag;
	private long mRemoteLastModified;
	private long mPointCount;
	private double mMaxLat;
	private double mMinLat;
	private double mMaxLng;
	private double mMinLng;
	private long mStartTime;
	private int mMaxZoomLevel;
	
	private List<TrackSegment> mSegments = new ArrayList<TrackSegment>();

	public TrackType() {
	}
	
	public TrackType(TrackType other) {
		mRemoteParentActivityUri = other.mRemoteParentActivityUri;
		mRemoteUri = other.mRemoteUri;
		mRemoteEtag = other.mRemoteEtag;
		mRemoteLastModified = other.mRemoteLastModified;
		mPointCount = other.mPointCount;
		mMaxLat = other.mMaxLat;
		mMinLat = other.mMinLat;
		mMaxLng = other.mMaxLng;
		mMinLng = other.mMinLng;
		mStartTime = other.mStartTime;
		mMaxZoomLevel = other.mMaxZoomLevel;
		mSegments = other.mSegments;
	}
	
	@Override
	public String toString() {
		return String
			.format(
					"TrackType [mRemoteParentActivityUri=%s, mRemoteUri=%s, mRemoteEtag=%s, mRemoteLastModified=%s, mPointCount=%s, mMaxLat=%s, mMinLat=%s, mMaxLng=%s, mMinLng=%s, mStartTime=%s, mMaxZoomLevel=%s, mSegments=%s]",
					mRemoteParentActivityUri, mRemoteUri, mRemoteEtag, mRemoteLastModified, mPointCount, mMaxLat,
					mMinLat, mMaxLng, mMinLng, mStartTime, mMaxZoomLevel, mSegments );
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeParcelable( mRemoteParentActivityUri, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeParcelable( mRemoteUri, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeString( mRemoteEtag );
		out.writeLong( mRemoteLastModified );
		out.writeLong( mPointCount );
		out.writeDouble( mMaxLat );
		out.writeDouble( mMinLat );
		out.writeDouble( mMaxLng );
		out.writeDouble( mMinLng );
		out.writeLong( mStartTime );
		out.writeInt( mMaxZoomLevel );
		out.writeList( mSegments );
	}
	
	public static final Parcelable.Creator<TrackType> CREATOR
    = new Parcelable.Creator<TrackType>() {
		public TrackType createFromParcel(Parcel in) {
			return new TrackType(in);
		}

		public TrackType[] newArray(int size) {
			return new TrackType[size];
		}
	};

	protected TrackType(Parcel in) {
		mRemoteParentActivityUri = in.readParcelable( Uri.class.getClassLoader() );
		mRemoteUri = in.readParcelable( Uri.class.getClassLoader() );
		mRemoteEtag = in.readString();
		mRemoteLastModified = in.readLong();
		mPointCount = in.readLong();
		mMaxLat = in.readDouble();
		mMinLat = in.readDouble();
		mMaxLng = in.readDouble();
		mMinLng = in.readDouble();
		mStartTime = in.readLong();
		mMaxZoomLevel = in.readInt();
		in.readList( mSegments, TrackSegment.class.getClassLoader() );
	}
	
	@Override
	public JSONObject getCreationJSON() {
		
		JSONObject json = new JSONObject();
		JSONArray segmentsArrayJson = new JSONArray();
		
		try {
			if( mSegments != null ) {
			
				int segmentCount = mSegments.size();
				TrackSegment currentSegment = null;
				for( int i = 0; i < segmentCount; i++ ) {
					currentSegment = mSegments.get( i );
				
					JSONObject currentSegmentJson = new JSONObject();
					
					List<Coordinate> segmentCoords = currentSegment.getCoordinates();
					JSONArray coordinatesJson = new JSONArray();
					
					int coordCount = segmentCoords.size();
					Coordinate coord = null;
					for( int j = 0; j < coordCount; j++ ) {
						coord = segmentCoords.get( j );
					
						JSONArray coordArray = new JSONArray();
						coordArray.put( coord.getY() );
						coordArray.put( coord.getX() );
						coordArray.put( coord.getZ() );
						coordArray.put( coord.getTime() );
						coordinatesJson.put( coordArray );
					}
					currentSegmentJson.put( "coordinates", coordinatesJson );
					segmentsArrayJson.put( currentSegmentJson );
				}
			}
			JSONObject detail = new JSONObject();
			detail.put( "parent_activity", mRemoteParentActivityUri.toString() );
			detail.put( "start_time", mStartTime );
			detail.put( "segments", segmentsArrayJson );
			json.put( "track", detail );
		} catch(JSONException e) {
			MyLog.e( TAG, "Error creating json to track creation", e );
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
	
	public Uri getRemoteParentActivityUri() {
		return mRemoteParentActivityUri;
	}

	public void setRemoteParentActivityUri(Uri remoteParentActivityUri) {
		this.mRemoteParentActivityUri = remoteParentActivityUri;
	}

	public long getPointCount() {
		return mPointCount;
	}

	public void setPointCount(long pointCount) {
		this.mPointCount = pointCount;
	}

	public List<TrackSegment> getSegments() {
		return mSegments;
	}

	public void setSegments(List<TrackSegment> segments) {
		this.mSegments = segments;
	}
	
	public double getMaxLat() {
		return mMaxLat;
	}

	public void setMaxLat(double maxLat) {
		this.mMaxLat = maxLat;
	}

	public double getMinLat() {
		return mMinLat;
	}

	public void setMinLat(double minLat) {
		this.mMinLat = minLat;
	}

	public double getMaxLng() {
		return mMaxLng;
	}

	public void setMaxLng(double maxLng) {
		this.mMaxLng = maxLng;
	}

	public double getMinLng() {
		return mMinLng;
	}

	public void setMinLng(double minLng) {
		this.mMinLng = minLng;
	}
	
	public long getStartTime() {
		return mStartTime;
	}

	public void setStartTime(long startTime) {
		this.mStartTime = startTime;
	}

	public int getMaxZoomLevel() {
		return mMaxZoomLevel;
	}
	
	public void setMaxZoomLevel(int maxZoomLevel) {
		this.mMaxZoomLevel = maxZoomLevel;
	}
	
	public double getCenterLat() {
		return mMinLat + ((mMaxLat - mMinLat) / 2);
	}
	
	public double getCenterLng() {
		return mMinLng + ((mMaxLng - mMinLng) / 2);
	}
}