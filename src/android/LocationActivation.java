package com.andretissot.locationactivator;

import org.apache.cordova.CordovaPlugin;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.apache.cordova.CallbackContext;
import android.content.Context;
import android.app.Activity;
import org.apache.cordova.LOG;

/**
 * Created by André Augusto Tissot on 13/05/2017.
 */

public class LocationActivation extends CordovaPlugin {

    public boolean execute(final String action, final JSONArray args,
                           final CallbackContext callbackContext) throws JSONException {
        if (action.equals("askActivation")) {
            new LocationActivator().askToActivate(cordova.getActivity());
            callbackContext.success(new JSONObject());
        } else return false;
        return true;
    }
}
