package io.adventurous.android.api.types.sync;

import io.adventurous.android.api.types.AbstractApiType;

import java.util.List;

import org.json.JSONObject;

import android.net.Uri;


public class SyncRemoteStatus extends AbstractApiType {
	private long mServerTime;
	private List<SyncUpdatedObject> mUpdated;
	private List<SyncDeletedObject> mDeleted;
	
	public long getServerTime() {
		return mServerTime;
	}
	
	public void setServerTime(long serverTime) {
		this.mServerTime = serverTime;
	}
	
	public List<SyncUpdatedObject> getUpdated() {
		return mUpdated;
	}
	
	public void setUpdated(List<SyncUpdatedObject> updated) {
		this.mUpdated = updated;
	}
	
	public List<SyncDeletedObject> getDeleted() {
		return mDeleted;
	}
	
	public void setDeleted(List<SyncDeletedObject> deleted) {
		this.mDeleted = deleted;
	}

	@Override
	public Uri getRemoteUri() {
		throw new UnsupportedOperationException( "Not yet implemented" );
	}
	
	@Override
	public void setRemoteUri(Uri remoteUri) {
		throw new UnsupportedOperationException( "Not yet implemented" );
	}
	
	@Override
	public String getRemoteEtag() {
		throw new UnsupportedOperationException( "Not yet implemented" );
	}
	
	@Override
	public void setRemoteEtag(String remoteEtag) {
		// do nothing - ETag is not important on this object in the api call
	}
	
	@Override
	public long getRemoteLastModified() {
		throw new UnsupportedOperationException( "Not yet implemented" );
	}
	
	@Override
	public void setRemoteLastModified(long remoteLastModified) {
		// do nothing - Last modified is not important on this object in the api call
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