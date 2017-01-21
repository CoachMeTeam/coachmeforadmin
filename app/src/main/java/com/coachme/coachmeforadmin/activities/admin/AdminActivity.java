package com.coachme.coachmeforadmin.activities.admin;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.coachme.coachmeforadmin.R;
import com.coachme.coachmeforadmin.activities.MainActivity;
import com.coachme.coachmeforadmin.activities.admin.users.UsersListActivity;
import com.coachme.coachmeforadmin.services.WifiService;


public class AdminActivity extends Activity {
    private WifiManager wifiManager;
    TextView textViewServerInfo;
    private Button addUserButton;
    private Button usersListButton;
    private TextView TVvelo;
    private TextView TVdevlper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        WifiService.createCoachMeWifiNetwork(wifiManager);

        addUserButton = (Button) findViewById(R.id.addUserButton);
        usersListButton = (Button) findViewById(R.id.usersListButton);


        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AdminLoginActivity.class);
                startActivity(i);
            }
        });

        usersListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), UsersListActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}