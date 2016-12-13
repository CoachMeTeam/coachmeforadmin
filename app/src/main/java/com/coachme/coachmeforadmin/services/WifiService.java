package com.coachme.coachmeforadmin.services;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WifiService {
    private static final String SSID = "CoachMeWifi";
    // private static final String WPA_KEY = "b91979f93dbcb4678ac79bfdfd59105816ba13660a2ebe4dc5cd098da71b68d6";
    private static final String WPA_KEY = "coachmewifi";

    private static WifiManager wifiManager;

    public static void createCoachMeWifiNetwork(WifiManager manager) {
        wifiManager = manager;
        WifiConfiguration configuration = new WifiConfiguration();
        configuration.SSID = SSID;
        configuration.preSharedKey = WPA_KEY;
//        configuration.hiddenSSID = true;
        configuration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        configuration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        configuration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);

        if (wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        }

        try {
            Method setWifiApMethod = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            boolean apstatus = (Boolean) setWifiApMethod.invoke(wifiManager, configuration, true);

            Method isWifiApEnabledmethod = wifiManager.getClass().getMethod("isWifiApEnabled");
            while (!(Boolean) isWifiApEnabledmethod.invoke(wifiManager)) {
            }
            Method getWifiApStateMethod = wifiManager.getClass().getMethod("getWifiApState");
            int apstate = (Integer) getWifiApStateMethod.invoke(wifiManager);
            Method getWifiApConfigurationMethod = wifiManager.getClass().getMethod("getWifiApConfiguration");
            configuration = (WifiConfiguration) getWifiApConfigurationMethod.invoke(wifiManager);
            Log.e("CLIENT", "\nSSID:" + configuration.SSID + "\nPassword:" + configuration.preSharedKey + "\n");

        } catch (Exception e) {
            Log.e("Error", "Error");
        }
    }
}
