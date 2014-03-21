package io.adventurous.android.api;

import io.adventurous.android.api.exceptions.ApiBadRequestException;
import io.adventurous.android.api.exceptions.ApiException;
import io.adventurous.android.api.exceptions.ApiForbiddenException;
import io.adventurous.android.api.exceptions.ApiUnauthorizedException;
import io.adventurous.android.api.parsers.ActivityParser;
import io.adventurous.android.api.parsers.AdventureParser;
import io.adventurous.android.api.parsers.DeviceRegistrationParser;
import io.adventurous.android.api.parsers.PhotoParser;
import io.adventurous.android.api.parsers.SyncRemoteStatusParser;
import io.adventurous.android.api.parsers.TrackParser;
import io.adventurous.android.api.parsers.UserParser;
import io.adventurous.android.api.parsers.WaypointParser;
import io.adventurous.android.api.types.ActivityType;
import io.adventurous.android.api.types.AdventureType;
import io.adventurous.android.api.types.DeviceRegistrationType;
import io.adventurous.android.api.types.PhotoType;
import io.adventurous.android.api.types.TrackType;
import io.adventurous.android.api.types.UserType;
import io.adventurous.android.api.types.WaypointType;
import io.adventurous.android.api.types.sync.SyncRemoteStatus;
import io.adventurous.android.util.MyLog;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;

import android.net.Uri;


public class Api {
	private static final String TAG = "Api";
	
	private final String mBaseUrl;
	private ApiCredentials mCredentials;
	private String mDeviceId;
	
	public Api(String baseUrl) {
		this.mBaseUrl = baseUrl;
	}
	
	public void setCredentials(ApiCredentials credentials) {
		this.mCredentials = credentials;
	}
	
	public void setDeviceId(String deviceId) {
		this.mDeviceId = deviceId;
	}

	@V1
	public ApiResponse<List<ActivityType>> findActivities(String path, DomainObjectQueryListener<ActivityType> listener) throws IOException, ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, ApiException {
		HttpGet get = ApiUtils.createJsonGet( mBaseUrl + path );
		HttpResponse response = ApiHttpClient.execute( get, mCredentials, mDeviceId );
		return new ActivityParser().parseResponseArray( response, "activities", listener );
	}
	
	@V1
	public ApiResponse<ActivityType> getActivity(Uri uri) throws ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, IOException, ApiException {
		HttpGet get = ApiUtils.createJsonGet( uri.toString() );
		HttpResponse response = ApiHttpClient.execute( get, mCredentials, mDeviceId );
		return new ActivityParser().parseResponseObject( response, "activity" );
	}
	
	@V1
	public ApiResponse<AdventureType> getAdventure(Uri uri) throws IOException, ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, ApiException {
		HttpGet get = ApiUtils.createJsonGet( uri.toString() );
		HttpResponse response = ApiHttpClient.execute( get, mCredentials, mDeviceId );
		return new AdventureParser().parseResponseObject( response, "adventure" );
	}
	
	@V1
	public ApiResponse<ActivityType> createActivity(ActivityType activity) throws ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, IOException, ApiException {
		HttpPost post = ApiUtils.createJsonPost( mBaseUrl + "/activity", activity.getCreationJSON() );
		HttpResponse response = ApiHttpClient.execute( post, mCredentials, mDeviceId );
		return new ActivityParser().parseResponseObject( response, null );
	}
	
	@V1
	public ApiResponse<ActivityType> deleteActivity(Uri uri) throws ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, IOException, ApiException {
		HttpDelete delete = ApiUtils.createJsonDelete( uri.toString() );
		HttpResponse response = ApiHttpClient.execute( delete, mCredentials, mDeviceId );
		return new ActivityParser().parseResponseObject( response, null );
	}
	
	@V1
	public ApiResponse<ActivityType> updateActivity(ActivityType activity) throws IOException, ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, ApiException {
		Uri activityUri = activity.getRemoteUri();
		if( activityUri == null ) {
			MyLog.e( TAG, "Could not update activity because its remote URI is null" );
			return null;
		}
		
		HttpPut put = ApiUtils.createJsonPut( activityUri.toString(), activity.getUpdateJSON() );
		HttpResponse response = ApiHttpClient.execute( put, mCredentials, mDeviceId );
		return new ActivityParser().parseResponseObject( response, null );
	}
	
	@V1
	public ApiResponse<List<AdventureType>> findAdventures(Uri uri, DomainObjectQueryListener<AdventureType> listener) throws IOException, ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, ApiException {
		HttpGet get = ApiUtils.createJsonGet( uri.toString() );
		HttpResponse response = ApiHttpClient.execute( get, mCredentials, mDeviceId );
		return new AdventureParser().parseResponseArray( response, "adventures", listener );
	}

	@V1
	public ApiResponse<List<AdventureType>> loadAdventuresFromUrl(String url, DomainObjectQueryListener<AdventureType> listener) throws IOException, ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, ApiException {
		HttpGet get = ApiUtils.createJsonGet( url );
		HttpResponse response = ApiHttpClient.execute( get, mCredentials, mDeviceId );
		return new AdventureParser().parseResponseArray( response, "adventures", listener );
	}
	
	@V1
	public ApiResponse<AdventureType> createAdventure(AdventureType adventure) throws ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, IOException, ApiException {
		HttpPost post = ApiUtils.createJsonPost( mBaseUrl + "/adventure", adventure.getCreationJSON() );
		HttpResponse response = ApiHttpClient.execute( post, mCredentials, mDeviceId );
		return new AdventureParser().parseResponseObject( response, null );
	}
	
	@V1
	public ApiResponse<AdventureType> updateAdventure(AdventureType adventure) throws ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, IOException, ApiException {
		Uri adventureUri = adventure.getRemoteUri();
		if( adventureUri == null ) {
			MyLog.e( TAG, "Could not update adventure because its remote URI is null" );
			return null;
		}
		
		HttpPut put = ApiUtils.createJsonPut( adventureUri.toString(), adventure.getUpdateJSON() );
		HttpResponse response = ApiHttpClient.execute( put, mCredentials, mDeviceId );
		return new AdventureParser().parseResponseObject( response, null );
	}
	
	@V1
	public ApiResponse<AdventureType> deleteAdventure(Uri uri) throws ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, IOException, ApiException {
		HttpDelete delete = ApiUtils.createJsonDelete( uri.toString() );
		HttpResponse response = ApiHttpClient.execute( delete, mCredentials, mDeviceId );
		return new AdventureParser().parseResponseObject( response, null );
	}
	
	@V1
	public ApiResponse<List<PhotoType>> findPhotos(String path, DomainObjectQueryListener<PhotoType> listener) throws IOException, ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, ApiException {
		HttpGet get = ApiUtils.createJsonGet( mBaseUrl + path );
		HttpResponse response = ApiHttpClient.execute( get, mCredentials, mDeviceId );
		return new PhotoParser().parseResponseArray( response, "photos", listener );
	}
	
	@V1
	public ApiResponse<PhotoType> getPhoto(Uri uri) throws ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, IOException, ApiException {
		HttpGet get = ApiUtils.createJsonGet( uri.toString() );
		HttpResponse response = ApiHttpClient.execute( get, mCredentials, mDeviceId );
		return new PhotoParser().parseResponseObject( response, "photo" );
	}
	
	@V1
	public ApiResponse<PhotoType> createPhoto(PhotoType photo) throws ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, IOException, ApiException {
		HttpPost post = new HttpPost( mBaseUrl + "/photo" );
		MultipartEntity multipart = photo.getMultipartEntity(); // make sure setSourceFile(File) was called beforehand
		
		post.setEntity( multipart );
		
		HttpResponse response = ApiHttpClient.execute( post, mCredentials, mDeviceId );
		ApiResponse<PhotoType> apiResponse = new PhotoParser().parseResponseObject( response, null );
		String uri = apiResponse.getLocationValue();
		
		if( uri != null ) {
			photo.setRemoteUri( Uri.parse( uri ) );
		}
		
		return apiResponse;
	}
	
	@V1
	public ApiResponse<PhotoType> updatePhoto(PhotoType photo) throws ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, IOException, ApiException {
		Uri adventurousUri = photo.getRemoteUri();
		if( adventurousUri == null ) {
			MyLog.e( TAG, "Could not update photo because its remote URI is null" );
			return null;
		}
		
		HttpPut put = new HttpPut( adventurousUri.toString() );
		
		put.setEntity( new StringEntity( photo.getUpdateJSON().toString() ) );
		put.addHeader( new JSONContentTypeHeader() );
		
		HttpResponse response = ApiHttpClient.execute( put, mCredentials, mDeviceId );
		
		return new PhotoParser().parseResponseObject( response, null );  // Expect 200 OK / No response body
	}
	
	@V1
	public ApiResponse<List<UserType>> findUsers(String path, DomainObjectQueryListener<UserType> listener) throws IOException, ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, ApiException {
		HttpGet get = ApiUtils.createJsonGet( mBaseUrl + path );
		HttpResponse response = ApiHttpClient.execute( get, mCredentials, mDeviceId );
		return new UserParser().parseResponseArray( response, "users", listener );
	}
	
	@V1
	public ApiResponse<UserType> createUser(UserType user) throws ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, IOException, ApiException {
		HttpPost post = ApiUtils.createJsonPost( mBaseUrl + "/user", user.getCreationJSON() );
		HttpResponse response = ApiHttpClient.execute( post, mCredentials, mDeviceId );
		return new UserParser().parseResponseObject( response, "user" );
	}
	
	@V1
	public ApiResponse<UserType> getUser(String username) throws ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, IOException, ApiException {
		if( null == username || "".equals( username ) ) {
			MyLog.e( TAG, "Could not get user: username is either null or blank.  Value: '" + username +"'" );
		}
		
		Uri uri = Uri.parse( mBaseUrl + "/user/" + username );
		return getUser( uri );
	}
	
	@V1
	public ApiResponse<UserType> getUser(Uri uri) throws ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, IOException, ApiException {
		HttpGet get = ApiUtils.createJsonGet( uri.toString() );
		HttpResponse response = ApiHttpClient.execute( get, mCredentials, mDeviceId );
		return new UserParser().parseResponseObject( response, "user" );
	}
	
	@V1
	public ApiResponse<UserType> updateUser(UserType user) {
		throw new UnsupportedOperationException( "Not yet implemented" );
	}
	
	@V1
	public ApiResponse<List<WaypointType>> findWaypoints(String path, DomainObjectQueryListener<WaypointType> listener) throws ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, IOException, ApiException {
		HttpGet get = ApiUtils.createJsonGet( mBaseUrl + path );
		HttpResponse response = ApiHttpClient.execute( get, mCredentials, mDeviceId );
		return new WaypointParser().parseResponseArray( response, "waypoints", listener );
	}
	
	@V1
	public ApiResponse<WaypointType> getWaypoint(Uri uri) throws ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, IOException, ApiException {
		HttpGet get = ApiUtils.createJsonGet( uri.toString() );
		HttpResponse response = ApiHttpClient.execute( get, mCredentials, mDeviceId );
		return new WaypointParser().parseResponseObject( response, "waypoint" );
	}
	
	@V1
	public ApiResponse<WaypointType> createWaypoint(WaypointType waypoint) throws ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, IOException, ApiException {
		HttpPost post = ApiUtils.createJsonPost( mBaseUrl + "/waypoint", waypoint.getCreationJSON() );
		HttpResponse response = ApiHttpClient.execute( post, mCredentials, mDeviceId );
		return new WaypointParser().parseResponseObject( response, null ); // Expect 201/No Body
	}
	
	@V1
	public ApiResponse<WaypointType> updateWaypoint(WaypointType waypoint) throws ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, IOException, ApiException {
		Uri waypointUri = waypoint.getRemoteUri();
		if( waypointUri == null ) {
			MyLog.e( TAG, "Could not update waypoint because its remote URI is null" );
			return null;
		}
		
		HttpPut put = ApiUtils.createJsonPut( waypointUri.toString(), waypoint.getUpdateJSON() );
		HttpResponse response = ApiHttpClient.execute( put, mCredentials, mDeviceId );
		return new WaypointParser().parseResponseObject( response, null ); // Expect 200/No Body
	}
	
	@V1
	public ApiResponse<WaypointType> deleteWaypoint(Uri uri) throws ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, IOException, ApiException {
		HttpDelete delete = ApiUtils.createJsonDelete( uri.toString() );
		HttpResponse response = ApiHttpClient.execute( delete, mCredentials, mDeviceId );
		return new WaypointParser().parseResponseObject( response, null );
	}
	
	@V1
	public ApiResponse<List<TrackType>> findTracks(String path, DomainObjectQueryListener<TrackType> listener) throws ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, IOException, ApiException {
		HttpGet get = ApiUtils.createJsonGet( mBaseUrl + path );
		HttpResponse response = ApiHttpClient.execute( get, mCredentials, mDeviceId );
		return new TrackParser().parseResponseArray( response, "tracks", listener );
	}
	
	@V1
	public ApiResponse<TrackType> createTrack(TrackType track) throws ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, IOException, ApiException {
		HttpPost post = ApiUtils.createJsonPost( mBaseUrl + "/track", track.getCreationJSON() );
		HttpResponse response = ApiHttpClient.execute( post, mCredentials, mDeviceId );
		return new TrackParser().parseResponseObject( response, null ); // Expect 201/No Body
	}
	
	@V1
	public ApiResponse<TrackType> updateTrack(TrackType track) throws ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, IOException, ApiException {
		Uri remoteUri = track.getRemoteUri();
		if( remoteUri == null ) {
			MyLog.e( TAG, "Cannot PUT to update: track does not have its remote uri set" );
			return null;
		}
		
		HttpPut put = ApiUtils.createJsonPut( remoteUri.toString(), track.getUpdateJSON() );
		HttpResponse response = ApiHttpClient.execute( put, mCredentials, mDeviceId );
		return new TrackParser().parseResponseObject( response, null ); // Expect 200/No Body
	}
	
	@V1
	public ApiResponse<TrackType> getTrack(Uri uri) throws IOException, ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, ApiException {
		if( null == uri ) {
			MyLog.e( TAG, "Cannot GET track: uri is null" );
			return null;
		}
		
		HttpGet get = ApiUtils.createJsonGet( uri.toString() );
		HttpResponse response = ApiHttpClient.execute( get, mCredentials, mDeviceId );
		return new TrackParser().parseResponseObject( response, "track" );
	}
	
	@V1
	public ApiResponse<DeviceRegistrationType> createDeviceRegistration(DeviceRegistrationType registration) throws ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, IOException, ApiException {
		HttpPost post = ApiUtils.createJsonPost( mBaseUrl + "/deviceregistration", registration.getCreationJSON() );
		HttpResponse response = ApiHttpClient.execute( post, mCredentials, mDeviceId );
		return new DeviceRegistrationParser().parseResponseObject( response, null ); // Expect 201/No Body
	}
	
	@V1
	public ApiResponse<DeviceRegistrationType> getDeviceRegistration(Uri uri) throws ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, IOException, ApiException {
		HttpGet get = ApiUtils.createJsonGet( uri.toString() );
		HttpResponse response = ApiHttpClient.execute( get, mCredentials, mDeviceId );
		return new DeviceRegistrationParser().parseResponseObject( response, "registration" );
	}
	
	@V1
	public ApiResponse<DeviceRegistrationType> updateDeviceRegistration(DeviceRegistrationType registration) throws ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, IOException, ApiException {
		Uri registrationUri = registration.getRemoteUri();
		if( registrationUri == null ) {
			MyLog.e( TAG, "Could not update device registration because its remote URI is null" );
			return null;
		}
		
		HttpPut put = ApiUtils.createJsonPut( registrationUri.toString(), registration.getUpdateJSON() );
		HttpResponse response = ApiHttpClient.execute( put, mCredentials, mDeviceId );
		return new DeviceRegistrationParser().parseResponseObject( response, null ); // Expect 200/No Body
	}
	
	@V1
	public ApiResponse<DeviceRegistrationType> deleteDeviceRegistration(Uri uri) throws ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, IOException, ApiException {
		HttpDelete delete = ApiUtils.createJsonDelete( uri.toString() );
		HttpResponse response = ApiHttpClient.execute( delete, mCredentials, mDeviceId );
		return new DeviceRegistrationParser().parseResponseObject( response, null );
	}
	
	@V1
	public ApiResponse<List<ActivityType>> getAdventureActivities(Uri adventureUri, DomainObjectQueryListener<ActivityType> listener) throws ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, IOException, ApiException {
		if( adventureUri == null ) {
			MyLog.e( TAG, "Cannot GET activities for adventure: Adventure does not have its remote uri set" );
			return null;
		}
		
		HttpGet get = ApiUtils.createJsonGet( adventureUri.toString() + "/activities?depth=1" );
		HttpResponse response = ApiHttpClient.execute( get, mCredentials, mDeviceId );
		return new ActivityParser().parseResponseArray( response, "activities", listener );
	}
	
	@V1
	public ApiResponse<SyncRemoteStatus> getSyncRemoteStatus(String username, long since) throws IOException, ApiBadRequestException, ApiUnauthorizedException, ApiForbiddenException, ApiException {
		HttpGet get = new HttpGet( mBaseUrl + "/user/" + username + "/sync?since=" + since ); // don't add JSON Content-Type (Grails blows since there's URL params, but no JSON)
		HttpResponse response = ApiHttpClient.execute( get, mCredentials, mDeviceId );
		return new SyncRemoteStatusParser().parseResponseObject( response, "sync" );
	}
	
	@interface V1 {
    }
}
