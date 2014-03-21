package io.adventurous.android.api.types;

import io.adventurous.android.util.MyLog;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;


public class PhotoType extends AbstractApiType implements Parcelable {
	
	private static final String TAG = "PhotoType";

	protected Uri mRemoteParentActivityUri;
	
	/**
	 * The uri at Adventurous.  Will be <code>null</code> until photo has been uploaded.
	 */
	protected Uri mRemoteUri;
	
	protected String mRemoteEtag;
	protected long mRemoteLastModified;
	
	protected String mTitle;
	protected String mCaption;
	protected long mDate;
	protected int mOrientation;
	protected double mLatitude;
	protected double mLongitude;
	protected double mAltitude;
	protected double mAccuracy;
	protected int mSatelliteCount;
	
	protected ImageType mThumb32sq;
	protected ImageType mThumb48sq;
	protected ImageType mThumb64sq;
	protected ImageType mThumb96sq;
	protected ImageType mThumb192sq;
	protected ImageType mPreview256;
	protected ImageType mLarge640;
	protected ImageType mLarge800;
	protected ImageType mLarge900;
	protected ImageType mLarge960;
	protected ImageType mLarge1120;
	protected ImageType mLarge1280;
	protected ImageType mLarge2048;
	protected ImageType mOriginal;

	private File mUploadSourceFile;

	public PhotoType() {
	}
	
	public PhotoType(PhotoType other) {
		mRemoteParentActivityUri = other.mRemoteParentActivityUri;
		mRemoteUri = other.mRemoteUri;
		mRemoteEtag = other.mRemoteEtag;
		mRemoteLastModified = other.mRemoteLastModified;
		mTitle = other.mTitle;
		mCaption = other.mCaption;
		mDate = other.mDate;
		mOrientation = other.mOrientation;
		mLatitude = other.mLatitude;
		mLongitude = other.mLongitude;
		mAltitude = other.mAltitude;
		mAccuracy = other.mAccuracy;
		mSatelliteCount = other.mSatelliteCount;
		mThumb32sq = other.mThumb32sq;
		mThumb48sq = other.mThumb48sq;
		mThumb64sq = other.mThumb64sq;
		mThumb96sq = other.mThumb96sq;
		mThumb192sq = other .mThumb192sq;
		mPreview256 = other.mPreview256;
		mLarge640 = other.mLarge640;
		mLarge800 = other.mLarge800;
		mLarge900 = other.mLarge900;
		mLarge960 = other.mLarge960;
		mLarge1120 = other.mLarge1120;
		mLarge1280 = other.mLarge1280;
		mLarge2048 = other.mLarge2048;
		mOriginal = other.mOriginal;
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
		out.writeString( mTitle );
		out.writeString( mCaption );
		out.writeLong( mDate );
		out.writeInt( mOrientation );
		out.writeDouble( mLatitude );
		out.writeDouble( mLongitude );
		out.writeDouble( mAltitude );
		out.writeDouble( mAccuracy );
		out.writeInt( mSatelliteCount );
		out.writeParcelable( mThumb32sq, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeParcelable( mThumb48sq, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeParcelable( mThumb64sq, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeParcelable( mThumb96sq, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeParcelable( mThumb192sq, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeParcelable( mPreview256, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeParcelable( mLarge640, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeParcelable( mLarge800, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeParcelable( mLarge900, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeParcelable( mLarge960, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeParcelable( mLarge1120, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeParcelable( mLarge1280, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeParcelable( mLarge2048, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeParcelable( mOriginal, PARCELABLE_WRITE_RETURN_VALUE );
		out.writeString( mUploadSourceFile == null ? null : mUploadSourceFile.getAbsolutePath() );
	}
	
	public static final Parcelable.Creator<PhotoType> CREATOR
    = new Parcelable.Creator<PhotoType>() {
		public PhotoType createFromParcel(Parcel in) {
			return new PhotoType(in);
		}

		public PhotoType[] newArray(int size) {
			return new PhotoType[size];
		}
	};

	protected PhotoType(Parcel in) {
		mRemoteParentActivityUri = in.readParcelable( Uri.class.getClassLoader() );
		mRemoteUri = in.readParcelable( Uri.class.getClassLoader() );
		mRemoteEtag = in.readString();
		mRemoteLastModified = in.readLong();
		mTitle = in.readString();
		mCaption = in.readString();
		mDate = in.readLong();
		mOrientation = in.readInt();
		mLatitude = in.readDouble();
		mLongitude = in.readDouble();
		mAltitude = in.readDouble();
		mAccuracy = in.readDouble();
		mSatelliteCount = in.readInt();
		
		final ClassLoader imageClassLoader = ImageType.class.getClassLoader();
		mThumb32sq = in.readParcelable( imageClassLoader );
		mThumb48sq = in.readParcelable( imageClassLoader );
		mThumb64sq = in.readParcelable( imageClassLoader );
		mThumb96sq = in.readParcelable( imageClassLoader );
		mThumb192sq = in.readParcelable( imageClassLoader );
		mPreview256 = in.readParcelable( imageClassLoader );
		mLarge640 = in.readParcelable( imageClassLoader );
		mLarge800 = in.readParcelable( imageClassLoader );
		mLarge900 = in.readParcelable( imageClassLoader );
		mLarge960 = in.readParcelable( imageClassLoader );
		mLarge1120 = in.readParcelable( imageClassLoader );
		mLarge1280 = in.readParcelable( imageClassLoader );
		mLarge2048 = in.readParcelable( imageClassLoader );
		mOriginal = in.readParcelable( imageClassLoader );
		
		String uploadSourceFileString = in.readString();
		if( null != uploadSourceFileString ) {
			mUploadSourceFile = new File( uploadSourceFileString );
		}
	}
	
	@Override
	public JSONObject getCreationJSON() {
		throw new UnsupportedOperationException( "Photos are posted with a multipart post.  Not with JSON" );
	}
	
	public JSONObject getUpdateJSON() {
		JSONObject json = new JSONObject();
		
		try {
			JSONObject details = new JSONObject();
			details.put( "title", mTitle );
			details.put( "caption", mCaption );
			
			json.put( "photo", details );
		} catch( JSONException e ) {
			MyLog.e( TAG, "Error creating JSON for photo update", e );
		}
		
		return json;
	}

	public MultipartEntity getMultipartEntity() {
		MultipartEntity multipart = new MultipartEntity();

		try {
			multipart.addPart( "parent_activity", new StringBody( mRemoteParentActivityUri.toString() ));
			
			if( mTitle == null ) {
				mTitle = "";
			}
			multipart.addPart( "title", new StringBody( mTitle ) );
			
			if( mCaption == null ) {
				mCaption = "";
			}
			multipart.addPart( "caption", new StringBody( mCaption ) );
			
			multipart.addPart( "date", new StringBody( Long.toString( mDate ) ) );
			multipart.addPart( "latitude", new StringBody( Double.toString( mLatitude ) ) );
			multipart.addPart( "longitude", new StringBody( Double.toString( mLongitude ) ) );
			multipart.addPart( "altitude", new StringBody( Double.toString( mAltitude ) ) );
			multipart.addPart( "accuracy", new StringBody( Double.toString( mAccuracy ) ) );
			multipart.addPart( "satellite_count", new StringBody( Integer.toString( mSatelliteCount ) ) );
			multipart.addPart( "photo", new FileBody( mUploadSourceFile ) );
		} catch( UnsupportedEncodingException e ) {
			MyLog.e( TAG, "Unsupported encoding while preparing MultipartEntity", e );
		}

		return multipart;
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

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		this.mTitle = title;
	}

	public String getCaption() {
		return mCaption;
	}

	public void setCaption(String caption) {
		this.mCaption = caption;
	}

	public long getDate() {
		return mDate;
	}

	public void setDate(long time) {
		this.mDate = time;
	}
	
	public int getOrientation() {
		return mOrientation;
	}
	
	public void setOrientation(int orientation) {
		this.mOrientation = orientation;
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

	public void setAccuracy(double accuracy) {
		this.mAccuracy = accuracy;
	}

	public int getSatelliteCount() {
		return mSatelliteCount;
	}

	public void setSatelliteCount(int satelliteCount) {
		this.mSatelliteCount = satelliteCount;
	}
	
	public ImageType getThumb32sq() {
		return mThumb32sq;
	}

	public void setThumb32sq(ImageType thumb32sq) {
		this.mThumb32sq = thumb32sq;
	}

	public ImageType getThumb48sq() {
		return mThumb48sq;
	}

	public void setThumb48sq(ImageType thumb48sq) {
		this.mThumb48sq = thumb48sq;
	}

	public ImageType getThumb64sq() {
		return mThumb64sq;
	}

	public void setThumb64sq(ImageType thumb64sq) {
		this.mThumb64sq = thumb64sq;
	}

	public ImageType getThumb96sq() {
		return mThumb96sq;
	}

	public void setThumb96sq(ImageType thumb96sq) {
		this.mThumb96sq = thumb96sq;
	}

	public ImageType getThumb192sq() {
		return mThumb192sq;
	}

	public void setThumb192sq(ImageType thumb192sq) {
		this.mThumb192sq = thumb192sq;
	}

	public ImageType getPreview256() {
		return mPreview256;
	}

	public void setPreview256(ImageType preview256) {
		this.mPreview256 = preview256;
	}

	public ImageType getLarge640() {
		return mLarge640;
	}

	public void setLarge640(ImageType large640) {
		this.mLarge640 = large640;
	}

	public ImageType getLarge800() {
		return mLarge800;
	}

	public void setLarge800(ImageType large800) {
		this.mLarge800 = large800;
	}

	public ImageType getLarge900() {
		return mLarge900;
	}

	public void setLarge900(ImageType large900) {
		this.mLarge900 = large900;
	}

	public ImageType getLarge960() {
		return mLarge960;
	}

	public void setLarge960(ImageType large960) {
		this.mLarge960 = large960;
	}

	public ImageType getLarge1120() {
		return mLarge1120;
	}

	public void setLarge1120(ImageType large1120) {
		this.mLarge1120 = large1120;
	}

	public ImageType getLarge1280() {
		return mLarge1280;
	}

	public void setLarge1280(ImageType large1280) {
		this.mLarge1280 = large1280;
	}

	public ImageType getLarge2048() {
		return mLarge2048;
	}

	public void setLarge2048(ImageType large2048) {
		this.mLarge2048 = large2048;
	}

	public ImageType getOriginal() {
		return mOriginal;
	}

	public void setOriginal(ImageType original) {
		this.mOriginal = original;
	}

	public File getUploadSourceFile() {
		return mUploadSourceFile;
	}

	public void setUploadSourceFile(File uploadSourceFile) {
		this.mUploadSourceFile = uploadSourceFile;
	}
}