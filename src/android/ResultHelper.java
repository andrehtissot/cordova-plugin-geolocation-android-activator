package com.andretissot.locationactivator;

import android.app.Activity;
import java.util.HashMap;
import org.apache.cordova.CallbackContext;
// import org.apache.cordova.LOG;
import org.apache.cordova.PluginResult;
import org.json.JSONObject;

/**
 * Created by André Augusto Tissot on 15/05/2017.
 */

public class ResultHelper {
    protected static ResultHelper instance;
    CallbackContext callbackContext;

    protected ResultHelper() {}

    public static ResultHelper getInstance() {
        if(instance == null)
            instance = new ResultHelper();
        return instance;
    }

    public static final String[] ReturnStatusMessages = new String[] {
        "Has all necessary permissions",
        "CallbackContext not found",
        "Permission denied",
        "Permission obtained",
        "GPS already active",
        "Error on request check settings",
        "GPS cannot be activated",
        "GPS activation denied",
        "GPS activated",
    };

    public enum ReturnStatus {
        ALREADY_HAS_ALL_PERMISSIONS,
        CALLBACK_CONTEXT_NOT_FOUND,
        PERMISSION_DENIED,
        PERMISSION_OBTAINED,
        GPS_ALREADY_ACTIVE,
        ERROR_ON_REQUEST_CHECK_SETTINGS,
        GPS_CANNOT_BE_ACTIVATED,
        GPS_ACTIVATION_DENIED,
        GPS_ACTIVATED
    }

    public void setCallbackContext(CallbackContext cbContext){
        callbackContext = cbContext;
    }

    public void sendSuccess(ReturnStatus status){
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("status", status.ordinal());
        result.put("message", ReturnStatusMessages[status.ordinal()]);
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, new JSONObject(result)));
    }

    public void sendFailure(ReturnStatus status){
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("status", status.ordinal());
        result.put("message", ReturnStatusMessages[status.ordinal()]);
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ILLEGAL_ACCESS_EXCEPTION,
            new JSONObject(result)));
    }

    public void sendError(ReturnStatus status){
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("status", status.ordinal());
        result.put("message", ReturnStatusMessages[status.ordinal()]);
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, new JSONObject(result)));
    }

    public void sendException(ReturnStatus status, Exception exception){
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("status", status.ordinal());
        result.put("message", ReturnStatusMessages[status.ordinal()]+": "+exception.getMessage());
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, new JSONObject(result)));
    }
}
