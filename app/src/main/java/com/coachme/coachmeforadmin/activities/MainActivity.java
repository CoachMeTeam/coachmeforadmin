package com.coachme.coachmeforadmin.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coachme.coachmeforadmin.CoachMeForAdminApp;
import com.coachme.coachmeforadmin.R;
import com.coachme.coachmeforadmin.activities.admin.AdminLoginActivity;
import com.coachme.coachmeforadmin.api.v1.CoachMeServer;
import com.coachme.coachmeforadmin.database.helpers.MachineDatabaseHelper;
import com.coachme.coachmeforadmin.model.Machine;
import com.coachme.coachmeforadmin.services.WifiService;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends Activity implements Observer {
    private static final String TIMER_FORMAT = "%02d";

    private WifiManager wifiManager;
    private MachineDatabaseHelper machineDatabaseHelper;
    private LinearLayout machinesContainer;
    private Button adminModuleButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);

        CoachMeForAdminApp.getReservationDatabaseHelper().addObserver(this);
        CoachMeForAdminApp.getMachineDatabaseHelper().addObserver(this);
        machineDatabaseHelper = CoachMeForAdminApp.getMachineDatabaseHelper();
        initComponents();

        if (machineDatabaseHelper.getMachines().size() == 0) {
            addDefaultMachines();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initMachinesListLayout();

        WifiService.createCoachMeWifiNetwork(wifiManager);
        try {
            CoachMeServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
    }

    private void initComponents() {
        adminModuleButton = (Button) findViewById(R.id.adminModuleButton);
        machinesContainer = (LinearLayout) findViewById(R.id.machinesListContainer);

        adminModuleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initMachinesListLayout() {
        machinesContainer.removeAllViews();

        List<Machine> machines = machineDatabaseHelper.getMachines();
        for (Machine machine : machines) {
            String machineAvailable = machine.isAvailable() ? "\nDisponible : Oui " : "\nDisponible : Non ";

            final TextView textView = new TextView(this);
            textView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.WHITE);
            textView.setPadding(0, 0, 0, 30);
            textView.setText(machine.getMachineName() + machineAvailable);
            machinesContainer.addView(textView);
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initMachinesListLayout();
            }
        });
    }

    private void addDefaultMachines() {
        machineDatabaseHelper.addMachine(new Machine("Développé Couché", "Musculation", true, false));
        machineDatabaseHelper.addMachine(new Machine("Leg Curl", "Musculation", true, false));
        machineDatabaseHelper.addMachine(new Machine("Triceps Extension", "Musculation", true, false));
        machineDatabaseHelper.addMachine(new Machine("Tirage à la Poulie Haute", "Musculation", true, false));
        machineDatabaseHelper.addMachine(new Machine("Tapis de course", "Cardio", true, false));
        machineDatabaseHelper.addMachine(new Machine("Vélo élliptique", "Cardio", true, false));
        machineDatabaseHelper.addMachine(new Machine("Stepper", "Cardio", true, false));
        machineDatabaseHelper.addMachine(new Machine("Rameur", "Cardio", true, false));
        machineDatabaseHelper.addMachine(new Machine("Plateforme oscillante 1", "Fitness", true, false));
        machineDatabaseHelper.addMachine(new Machine("Plateforme oscillante 1", "Fitness", true, false));
    }
}
