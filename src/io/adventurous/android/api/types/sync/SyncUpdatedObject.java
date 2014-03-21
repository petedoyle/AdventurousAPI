package io.adventurous.android.api.types.sync;

import io.adventurous.android.api.types.AbstractApiType;

import org.json.JSONObject;

import android.net.Uri;


public class SyncUpdatedObject extends AbstractApiType {
	
	private Uri mRemoteUri;
	private String mRemoteEtag;
	private long mLastModified;
	
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
		return this.mLastModified;
	}
	
	@Override
	public void setRemoteLastModified(long remoteLastModified) {
		this.mLastModified = remoteLastModified;
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