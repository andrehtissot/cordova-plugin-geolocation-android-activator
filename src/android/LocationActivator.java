package com.andretissot.locationactivator;

import android.app.Activity;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
// import org.apache.cordova.LOG;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

/**
 * Created by André Augusto Tissot on 13/05/2017.
 */

public class LocationActivator extends FragmentActivity
    implements ConnectionCallbacks, OnConnectionFailedListener {
    public static final int REQUEST_CHECK_SETTINGS = 0x1;

    public void onConnectionFailed(ConnectionResult result) {} //Silent for now

    public void onConnectionSuspended(int code) {} //Silent for now

    public void onConnected(Bundle savedInstanceState) {} //Silent for now

    public void askToActivate(final Activity activity){
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(activity.getApplicationContext())
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build();
        googleApiClient.connect();
        LocationRequest locationRequest = new LocationRequest()
            .setNumUpdates(1)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
            .setAlwaysShow(true)
            .addLocationRequest(locationRequest);
        PendingResult<LocationSettingsResult> result =
            LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch(status.getStatusCode()){
                    case LocationSettingsStatusCodes.SUCCESS:
                        ResultHelper.getInstance().sendSuccess(ResultHelper.ReturnStatus.GPS_ALREADY_ACTIVE);
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS);
                        } catch (SendIntentException e) {
                            ResultHelper.getInstance().sendException(
                                ResultHelper.ReturnStatus.ERROR_ON_REQUEST_CHECK_SETTINGS, e);
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        ResultHelper.getInstance().sendFailure(ResultHelper.ReturnStatus.GPS_CANNOT_BE_ACTIVATED);
                }
            }
        });
    }
}
