package com.coachme.coachmeforadmin.activities.admin.machines;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.coachme.coachmeforadmin.R;
import com.coachme.coachmeforadmin.activities.admin.AdminActivity;
import com.coachme.coachmeforadmin.activities.admin.users.UsersListActivity;
import com.coachme.coachmeforadmin.database.helpers.MachineDatabaseHelper;
import com.coachme.coachmeforadmin.model.Machine;

import org.apache.commons.lang3.StringUtils;

public class AddMachineActivity extends Activity {
    private final String FIELD_REQUIRED = "Ce champ est requis.";

    private Spinner machineTypesSpinner;
    private EditText machineNameEditText;
    private Button submitButton, goBackButton;
    MachineDatabaseHelper machineDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_machine);
        initComponents();

        machineDatabaseHelper = new MachineDatabaseHelper();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), AdminActivity.class);
        startActivity(i);
    }

    private boolean checkIfFormValid() {
        boolean isFormValid = true;
        String machineTypesSpinnerPrompt = getResources().getStringArray(R.array.machinetypes_array)[0];
        if (StringUtils.isBlank(machineNameEditText.getText().toString())) {
            isFormValid = false;
            machineNameEditText.setError(FIELD_REQUIRED);
        }

        if (StringUtils.isBlank(machineTypesSpinner.getSelectedItem().toString()) ||
                StringUtils.equals(machineTypesSpinner.getSelectedItem().toString(), machineTypesSpinnerPrompt)) {
            isFormValid = false;
            Toast.makeText(getApplicationContext(),
                    "Veuillez choisir un type de machine.",
                    Toast.LENGTH_SHORT).show();
        }
        return isFormValid;
    }

    private void initComponents() {
        machineNameEditText = (EditText) findViewById(R.id.machineNameEditText);
        machineTypesSpinner = (Spinner) findViewById(R.id.machineTypesSpinner);
        submitButton = (Button) findViewById(R.id.addMachineSubmitButton);
        goBackButton = (Button) findViewById(R.id.addMachineGoBackButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkIfFormValid()) {
                    String machineName = machineNameEditText.getText().toString();
                    String machineType = String.valueOf(machineTypesSpinner.getSelectedItem());

                    Machine machine = new Machine(machineName, machineType, true, false);
                    machineDatabaseHelper.addMachine(machine);

                    Intent i = new Intent(getApplicationContext(), MachinesListActivity.class);
                    startActivity(i);
                }
            }
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(i);
            }
        });
    }
}
