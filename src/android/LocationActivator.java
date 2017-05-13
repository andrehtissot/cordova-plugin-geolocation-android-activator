package com.andretissot.locationactivator;

import android.content.Context;
import android.app.Activity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import org.apache.cordova.LOG;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationSettingsRequest;
import android.content.IntentSender.SendIntentException;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;

/**
 * Created by André Augusto Tissot on 13/05/2017.
 */

public class LocationActivator extends FragmentActivity
    implements ConnectionCallbacks, OnConnectionFailedListener {
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

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
                if(status.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED){
                    try {
                        status.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS);
                    } catch (SendIntentException e) {} //Silent for now
                }
            }
        });
    }
}
