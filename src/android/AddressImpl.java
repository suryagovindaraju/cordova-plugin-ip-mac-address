package com.tv.plugin;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.WIFI_SERVICE;

public class AddressImpl extends CordovaPlugin {
    CallbackContext callbackCtx;
    Context ctx;
    Activity activity;

    public AddressImpl(){
    }

    @Override
    public boolean execute(String action, JSONArray data, final CallbackContext callbackContext) throws JSONException {
        switch (action) {
            case "getIPAddress":
                // need to send appId and password and image path
                callbackCtx = callbackContext;
                ctx = this.cordova.getActivity().getApplicationContext();
                activity = this.cordova.getActivity();
                JSONObject request = new JSONObject(data.getString(0));

                WifiManager wm = (WifiManager) ctx.getSystemService(WIFI_SERVICE);
                String ipAddress = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

                PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, "ipAddress");
                callbackCtx.success(ipAddress);
                pluginResult.setKeepCallback(true);
                callbackCtx.sendPluginResult(pluginResult);
                
               return true;
            case "getMACAddress":
                callbackCtx = callbackContext;
                ctx = this.cordova.getActivity().getApplicationContext();
                activity = this.cordova.getActivity();
                WifiManager wifiManager = (WifiManager) ctx.getSystemService(WIFI_SERVICE);
                String macAddress = wifiManager.getConnectionInfo().getMacAddress();

                PluginResult result = new PluginResult(PluginResult.Status.OK, "macAddress");
                callbackCtx.success(macAddress);
                result.setKeepCallback(true);
                callbackCtx.sendPluginResult(result);

                return true;
            default:
                return true;
        }
    }
}
