package com.coachme.coachmeforadmin.activities.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.coachme.coachmeforadmin.R;
import com.coachme.coachmeforadmin.activities.MainActivity;
import com.coachme.coachmeforadmin.activities.admin.machines.AddMachineActivity;
import com.coachme.coachmeforadmin.activities.admin.machines.MachinesListActivity;
import com.coachme.coachmeforadmin.activities.admin.users.AddUserActivity;
import com.coachme.coachmeforadmin.activities.admin.users.UsersListActivity;


public class AdminActivity extends Activity {
    private Button addUserButton;
    private Button usersListButton;
    private Button addMachineButton;
    private Button machinesListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        initComponents();

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddUserActivity.class);
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

        addMachineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddMachineActivity.class);
                startActivity(i);
            }
        });

        machinesListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MachinesListActivity.class);
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

    private void initComponents() {
        addUserButton = (Button) findViewById(R.id.addUserButton);
        usersListButton = (Button) findViewById(R.id.usersListButton);
        addMachineButton = (Button) findViewById(R.id.addMachineButton);
        machinesListButton = (Button) findViewById(R.id.machinesListButton);
    }
}