package com.coachme.coachmeforadmin.activities;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.coachme.coachmeforadmin.R;
import com.coachme.coachmeforadmin.services.WifiService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private WifiManager wifiManager;
    WifiApManager wifiApManager;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        textView = (TextView) findViewById(R.id.test);
        wifiApManager = new WifiApManager(this);
        setContentView(R.layout.activity_main);

        scan();
    }

    @Override
    protected void onStart() {
        super.onStart();
        WifiService.createCoachMeWifiNetwork(wifiManager);
        scan();
    }

    private void scan() {
        // Pour essayer la liste des devices connectés au réseau Wi-Fi mais ca ne marche pas
        ArrayList<ClientScanResult> clients = wifiApManager.getClientList(false);

        textView.append("Clients: \n");
        for (ClientScanResult clientScanResult : clients) {
            textView.append("####################\n");
            textView.append("IpAddr: " + clientScanResult.getIpAddr() + "\n");
            textView.append("Device: " + clientScanResult.getDevice() + "\n");
            textView.append("HWAddr: " + clientScanResult.getHWAddr() + "\n");
            textView.append("isReachable: " + clientScanResult.isReachable() + "\n");
        }
    }
}

class WifiApManager {
    private final WifiManager mWifiManager;

    public WifiApManager(Context context) {
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }



    /**
     * Gets a list of the clients connected to the Hotspot, reachable timeout is 300
     * @param onlyReachables {@code false} if the list should contain unreachable (probably disconnected) clients, {@code true} otherwise
     * @return ArrayList of {@link ClientScanResult}
     */
    public ArrayList<ClientScanResult> getClientList(boolean onlyReachables) {
        return getClientList(onlyReachables, 300);
    }

    /**
     * Gets a list of the clients connected to the Hotspot
     * @param onlyReachables {@code false} if the list should contain unreachable (probably disconnected) clients, {@code true} otherwise
     * @param reachableTimeout Reachable Timout in miliseconds
     * @return ArrayList of {@link ClientScanResult}
     */
    public ArrayList<ClientScanResult> getClientList(boolean onlyReachables, int reachableTimeout) {
        BufferedReader br = null;
        ArrayList<ClientScanResult> result = null;

        try {
            result = new ArrayList<ClientScanResult>();
            br = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitted = line.split(" +");

                if ((splitted != null) && (splitted.length >= 4)) {
                    // Basic sanity check
                    String mac = splitted[3];

                    if (mac.matches("..:..:..:..:..:..")) {
                        boolean isReachable = InetAddress.getByName(splitted[0]).isReachable(reachableTimeout);

                        if (!onlyReachables || isReachable) {
                            result.add(new ClientScanResult(splitted[0], splitted[3], splitted[5], isReachable));
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(this.getClass().toString(), e.getMessage());
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                Log.e(this.getClass().toString(), e.getMessage());
            }
        }

        return result;
    }
}

class ClientScanResult {

    private String IpAddr;

    private String HWAddr;

    private String Device;

    private boolean isReachable;

    public ClientScanResult(String ipAddr, String hWAddr, String device, boolean isReachable) {
        super();
        IpAddr = ipAddr;
        HWAddr = hWAddr;
        Device = device;
        this.setReachable(isReachable);
    }

    public String getIpAddr() {
        return IpAddr;
    }

    public void setIpAddr(String ipAddr) {
        IpAddr = ipAddr;
    }

    public String getHWAddr() {
        return HWAddr;
    }

    public void setHWAddr(String hWAddr) {
        HWAddr = hWAddr;
    }

    public String getDevice() {
        return Device;
    }

    public void setDevice(String device) {
        Device = device;
    }

    public void setReachable(boolean isReachable) {
        this.isReachable = isReachable;
    }

    public boolean isReachable() {
        return isReachable;
    }
}

enum WIFI_AP_STATE
{
    WIFI_AP_STATE_DISABLING, WIFI_AP_STATE_DISABLED, WIFI_AP_STATE_ENABLING, WIFI_AP_STATE_ENABLED, WIFI_AP_STATE_FAILED
}