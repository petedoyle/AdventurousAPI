package io.adventurous.android.api.types;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class TrackSegment implements Parcelable {

	private List<Coordinate> mCoordinates = new ArrayList<Coordinate>();
	
	public TrackSegment() {
	}
	
	@Override
	public String toString() {
		return "track coordinates length: " + (mCoordinates == null ? null : mCoordinates.size());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeList( mCoordinates );
	}
	
	public static final Parcelable.Creator<TrackSegment> CREATOR
    = new Parcelable.Creator<TrackSegment>() {
		public TrackSegment createFromParcel(Parcel in) {
			return new TrackSegment(in);
		}

		public TrackSegment[] newArray(int size) {
			return new TrackSegment[size];
		}
	};

	private TrackSegment(Parcel in) {
		in.readList( mCoordinates, Coordinate.class.getClassLoader() );
	}

	public List<Coordinate> getCoordinates() {
		return mCoordinates;
	}

	public void setCoordinates(List<Coordinate> coordinates) {
		this.mCoordinates = coordinates;
	}
	
	public boolean addCoordinate(Coordinate coordinate) {
		return mCoordinates.add( coordinate );
	}
	
}
