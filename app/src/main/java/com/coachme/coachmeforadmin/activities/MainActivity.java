package com.coachme.coachmeforadmin.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.coachme.coachmeforadmin.R;
import com.coachme.coachmeforadmin.services.WifiService;

public class MainActivity extends Activity {
    private WifiManager wifiManager;
    TextView textView;
    private Button BoutonAjout;
    private Button Boutontest;
    private TextView TVvelo;
    private TextView TVdevlper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        WifiService.createCoachMeWifiNetwork(wifiManager);

        BoutonAjout = (Button) findViewById(R.id.button1);
        Boutontest = (Button) findViewById(R.id.button3);
        TVvelo = (TextView) findViewById(R.id.textViewvelo);
        TVdevlper = (TextView) findViewById(R.id.textViewdvp);

        //lors des cliques
        BoutonAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //changement de "page", activity via intent
                Intent i = new Intent(getApplicationContext(), MainActivitySecureAdmin.class);
                startActivity(i);
            }
        });

        Boutontest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //changement de "page", activity via intent
                Intent i = new Intent(getApplicationContext(), TestFichier.class);
                startActivity(i);
            }
        });
        // Par la suite, nous recupererons les donnes issus des tablettes secondaires
        TVvelo.setText("Libre");
        TVdevlper.setText("Libre");
    }
}