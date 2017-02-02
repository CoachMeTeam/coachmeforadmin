package com.coachme.coachmeforadmin.activities.admin.machines;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.coachme.coachmeforadmin.R;
import com.coachme.coachmeforadmin.activities.admin.AdminActivity;
import com.coachme.coachmeforadmin.database.helpers.MachineDatabaseHelper;
import com.coachme.coachmeforadmin.model.Machine;
import com.coachme.coachmeforadmin.utils.Helper;

import java.util.ArrayList;
import java.util.List;

public class MachinesListActivity extends Activity {
    private final String ID_COLUMN = "ID";
    private final String MACHINE_NAME_COLUMN = "NOM";
    private final String MACHINE_TYPE_COLUMN = "TYPE";

    private Button goBackButton;
    private List<Machine> machines = new ArrayList<>();
    MachineDatabaseHelper machineDatabaseHelper = new MachineDatabaseHelper();
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machines_list);

        initComponents();
        machineDatabaseHelper = new MachineDatabaseHelper();
        machines = machineDatabaseHelper.getMachines();

        addHeaders();
        addData(machines);

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(i);
            }
        });
    }

    private void initComponents() {
        tableLayout = (TableLayout) findViewById(R.id.machinesTable);
        goBackButton = (Button) findViewById(R.id.machinesListGoBackButton);
    }

    private void addHeaders() {
        TableRow rowHeader = new TableRow(getApplicationContext());
        rowHeader.setBackgroundResource(R.drawable.rowborder_shape);
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText = {ID_COLUMN, MACHINE_NAME_COLUMN, MACHINE_TYPE_COLUMN};
        for (String c : headerText) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.START);
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(18);
            tv.setPadding(5, 5, 5, 5);
            tv.setText(c);
            rowHeader.addView(tv);
        }
        tableLayout.addView(rowHeader);
    }

    private void addData(List<Machine> machines) {
        for (final Machine machine : machines) {
            TableRow row = new TableRow(getApplicationContext());
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            String[] colText = {Integer.toString(machine.getId()), machine.getMachineName(), machine.getMachineType()};
            for (String text : colText) {
                TextView tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setGravity(Gravity.START);
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(16);
                tv.setPadding(5, 5, 5, 5);
                tv.setText(text);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), UpdateMachineActivity.class);
                        intent.putExtra("machineToUpdate", Helper.convertObjectToJson(machine));
                        startActivity(intent);
                    }
                });
                row.addView(tv);
            }
            tableLayout.addView(row);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
        startActivity(intent);
    }
}
