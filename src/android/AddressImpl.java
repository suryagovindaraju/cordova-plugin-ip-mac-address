package com.tv.plugin;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

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
                String ipAddress = "";

                try {
                    ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    if (activeNetwork != null) {
                        if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI && activeNetwork.getState() == NetworkInfo.State.CONNECTED) {
                            ipAddress = getWifiIPAddress();
                        } else if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE && activeNetwork.getState() == NetworkInfo.State.CONNECTED) {
                            ipAddress = getMobileIPAddress();
                        }
                        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, "ipAddress");
                        callbackCtx.success(ipAddress);
                        pluginResult.setKeepCallback(true);
                        callbackCtx.sendPluginResult(pluginResult);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
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

    private String getWifiIPAddress() {
        WifiManager wm = (WifiManager) ctx.getSystemService(WIFI_SERVICE);
        String ipAddress = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        return  ipAddress;
    }

    private String getMobileIPAddress() {
        try {
            List<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : networkInterfaces) {
                List<InetAddress> inetAddresses = Collections.list(networkInterface.getInetAddresses());
                for (InetAddress inetAddress : inetAddresses) {
                    if (!inetAddress.isLoopbackAddress()) {
                        String ipAddress = inetAddress.getHostAddress().toUpperCase();
                        return  ipAddress;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

}
