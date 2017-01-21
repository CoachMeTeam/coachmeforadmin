package com.coachme.coachmeforadmin.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.coachme.coachmeforadmin.R;
import com.coachme.coachmeforadmin.activities.admin.AdminActivity;
import com.coachme.coachmeforadmin.api.v1.CoachMeServer;

public class MainActivity extends Activity {
    private Button adminModuleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

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

    private void initComponents() {
        adminModuleButton = (Button) findViewById(R.id.adminModuleButton);
    }
}
