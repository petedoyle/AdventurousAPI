package io.adventurous.android.api.types.sync;

import io.adventurous.android.api.types.AbstractApiType;

import org.json.JSONObject;

import android.net.Uri;


public class SyncDeletedObject extends AbstractApiType {

	private Uri mRemoteUri;
	private long mDeletedTime;
	
	public long getDeletedTime() {
		return mDeletedTime;
	}
	
	public void setDeletedTime(long deletedTime) {
		this.mDeletedTime = deletedTime;
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
		throw new UnsupportedOperationException( "Not yet implemented" );
	}
	
	@Override
	public void setRemoteEtag(String remoteEtag) {
		// Do nothing- we don't care about the etag
	}
	
	@Override
	public long getRemoteLastModified() {
		throw new UnsupportedOperationException( "Not yet implemented" );
	}
	
	@Override
	public void setRemoteLastModified(long remoteLastModified) {
		// Do nothing- we don't care about last modified (we care about deleted time)
	}

	@Override
	public JSONObject getCreationJSON() {
		throw new UnsupportedOperationException();
	}

	@Override
	public JSONObject getUpdateJSON() {
		throw new UnsupportedOperationException();
	}
}