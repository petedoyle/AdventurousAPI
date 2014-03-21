package io.adventurous.android.api.types;

import io.adventurous.android.util.MyLog;

import java.net.MalformedURLException;
import java.net.URL;

import android.os.Parcel;
import android.os.Parcelable;


public class ImageType implements Parcelable {
	
	private static final String TAG = "ImageType";

	private String mType;
	private URL mUrl;
	private int mWidth;
	private int mHeight;
	
	public ImageType() {
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString( mType );
		out.writeString( mUrl == null ? null : mUrl.toString() );
		out.writeInt( mWidth );
		out.writeInt( mHeight );
	}
	
	public static final Parcelable.Creator<ImageType> CREATOR
    = new Parcelable.Creator<ImageType>() {
		public ImageType createFromParcel(Parcel in) {
			return new ImageType(in);
		}

		public ImageType[] newArray(int size) {
			return new ImageType[size];
		}
	};

	protected ImageType(Parcel in) {
		mType = in.readString();
		
		String imageUrlString = in.readString();
		if( null != imageUrlString ) {
			try {
				mUrl = new URL( imageUrlString );
			} catch( MalformedURLException e ) {
				MyLog.e( TAG, "Error recreating image url from value: '" + imageUrlString + "'", e );
			}
		}
		
		mWidth = in.readInt();
		mHeight = in.readInt();
	}
	
	public String getType() {
		return mType;
	}
	
	public void setType(String type) {
		this.mType = type;
	}
	
	public URL getUrl() {
		return mUrl;
	}

	public void setUrl(URL imageUrl) {
		this.mUrl = imageUrl;
	}

	public int getWidth() {
		return mWidth;
	}

	public void setWidth(int width) {
		this.mWidth = width;
	}

	public int getHeight() {
		return mHeight;
	}

	public void setHeight(int height) {
		this.mHeight = height;
	}
}