package io.adventurous.android.api.types;

import android.os.Parcel;
import android.os.Parcelable;

public class Coordinate implements Parcelable {

	private final double mX;
	private final double mY;
	private final double mZ;
	private final long mTime;

	public Coordinate(double x, double y, double z, long time) {
		this.mX = x;
		this.mY = y;
		this.mZ = z;
		this.mTime = time;
	}
	
	@Override
	public String toString() {
		return "x: " + mX + ", y: " + mY + ", z: " + mZ + ", time: " + mTime;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeDouble( mX );
		out.writeDouble( mY ); 
		out.writeDouble( mZ );
		out.writeLong( mTime );
	}
	
	public static final Parcelable.Creator<Coordinate> CREATOR
    = new Parcelable.Creator<Coordinate>() {
		public Coordinate createFromParcel(Parcel in) {
			return new Coordinate(in);
		}

		public Coordinate[] newArray(int size) {
			return new Coordinate[size];
		}
	};

	private Coordinate(Parcel in) {
		mX = in.readDouble();
		mY = in.readDouble();
		mZ = in.readDouble();
		mTime = in.readLong();
	}

	public double getX() {
		return mX;
	}

	public double getY() {
		return mY;
	}

	public double getZ() {
		return mZ;
	}

	public long getTime() {
		return mTime;
	}
	
}
