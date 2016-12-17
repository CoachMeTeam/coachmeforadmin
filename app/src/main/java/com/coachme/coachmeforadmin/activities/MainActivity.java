package com.coachme.coachmeforadmin.activities;

import android.app.Activity;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.coachme.coachmeforadmin.R;
import com.coachme.coachmeforadmin.services.WifiService;

public class MainActivity extends Activity {
    private WifiManager wifiManager;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        textView = (TextView) findViewById(R.id.test);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        WifiService.createCoachMeWifiNetwork(wifiManager);
    }
}