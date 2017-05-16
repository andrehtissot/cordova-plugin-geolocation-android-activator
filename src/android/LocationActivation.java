package com.andretissot.locationactivator;

import android.content.pm.PackageManager;
import android.Manifest;
import com.andretissot.locationactivator.ResultHelper.ReturnStatus;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.LOG;
import org.apache.cordova.PermissionHelper;
import org.json.JSONArray;
import org.json.JSONException;
import android.content.Intent;

/**
 * Created by André Augusto Tissot on 13/05/2017.
 */

public class LocationActivation extends CordovaPlugin {

    protected CallbackContext callbackContext;
    protected String [] permissions = {
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    };
    protected boolean askActivateAfterPermission;

    public boolean execute(final String action, final JSONArray args,
                           final CallbackContext callbackContext) throws JSONException {
        askActivateAfterPermission = false;
        this.callbackContext = callbackContext;
        if (action.equals("askPermission")) {
            cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    ResultHelper.getInstance().setCallbackContext(callbackContext);
                    if(hasAllPermissions()) {
                        ResultHelper.getInstance().sendSuccess(ResultHelper.ReturnStatus.ALREADY_HAS_ALL_PERMISSIONS);
                    } else askPermission();
                }
            });
        } else if (action.equals("askActivation")) {
            cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    ResultHelper.getInstance().setCallbackContext(callbackContext);
                    if(hasAllPermissions()) {
                        askActivation();
                    } else {
                        askActivateAfterPermission = true;
                        askPermission();
                    }
                }
            });
        } else return false;
        return true;
    }

    protected boolean hasAllPermissions(){
        for(String p : permissions)
            if(!PermissionHelper.hasPermission(this, p))
                return false;
        return true;
    }

    protected void askPermission(){
        PermissionHelper.requestPermissions(this, 0, permissions);
    }

    protected void askActivation(){
        cordova.setActivityResultCallback(this);
        new LocationActivator().askToActivate(cordova.getActivity());
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        if(requestCode != LocationActivator.REQUEST_CHECK_SETTINGS)
            super.onActivityResult(requestCode, resultCode, data);
        else if(resultCode == 0)
            ResultHelper.getInstance().sendFailure(ResultHelper.ReturnStatus.GPS_ACTIVATION_DENIED);
        else ResultHelper.getInstance().sendSuccess(ResultHelper.ReturnStatus.GPS_ACTIVATED);
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults)
        throws JSONException {
        if(callbackContext == null){
            ResultHelper.getInstance().sendError(ResultHelper.ReturnStatus.CALLBACK_CONTEXT_NOT_FOUND);
            return;
        }
        for (int r : grantResults) {
            if (r == PackageManager.PERMISSION_DENIED) {
                ResultHelper.getInstance().sendFailure(ResultHelper.ReturnStatus.PERMISSION_DENIED);
                return;
            }
        }
        if(askActivateAfterPermission){
            askActivation();
            return;
        }
        ResultHelper.getInstance().sendSuccess(ResultHelper.ReturnStatus.PERMISSION_OBTAINED);
    }

    public void requestPermissions(int requestCode) {
        PermissionHelper.requestPermissions(this, requestCode, permissions);
    }
}
