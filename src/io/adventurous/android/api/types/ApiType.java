package io.adventurous.android.api.types;

import org.json.JSONObject;

import android.net.Uri;

public interface ApiType {
	public Uri getRemoteUri();
	public void setRemoteUri(Uri remoteUri);
	public String getRemoteEtag();
	public void setRemoteEtag(String remoteEtag);
	public long getRemoteLastModified();
	public void setRemoteLastModified(long remoteLastModified);
	
	public JSONObject getCreationJSON();
	public JSONObject getUpdateJSON();
	
	public boolean isPersistent();
}