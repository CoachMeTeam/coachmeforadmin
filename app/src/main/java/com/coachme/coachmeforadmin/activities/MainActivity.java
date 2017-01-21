package com.coachme.coachmeforadmin.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.coachme.coachmeforadmin.R;
import com.coachme.coachmeforadmin.activities.admin.AdminActivity;
import com.coachme.coachmeforadmin.api.v1.CoachMeServer;
import com.coachme.coachmeforadmin.services.WifiService;

public class MainActivity extends Activity {
    private WifiManager wifiManager;
    private Button adminModuleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        WifiService.createCoachMeWifiNetwork(wifiManager);

        try {
            CoachMeServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        initComponents();
        adminModuleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }

    private void initComponents() {
        adminModuleButton = (Button) findViewById(R.id.adminModuleButton);
    }
}
