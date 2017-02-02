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
import com.coachme.coachmeforadmin.database.helpers.MachineDatabaseHelper;
import com.coachme.coachmeforadmin.model.Machine;
import com.coachme.coachmeforadmin.utils.Helper;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class UpdateMachineActivity extends Activity {
    private final String FIELD_REQUIRED = "Ce champ est requis.";

    private Spinner machineTypesSpinner;
    private EditText machineNameEditText;
    private Button submitButton, goBackButton;
    MachineDatabaseHelper machineDatabaseHelper;
    Machine machine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_machine);
        String jsonMachine = getIntent().getStringExtra("machineToUpdate");
        machine = Helper.convertJsonToObject(jsonMachine, Machine.class);

        initComponents();
        machineDatabaseHelper = new MachineDatabaseHelper();
    }

    private void initComponents() {
        machineNameEditText = (EditText) findViewById(R.id.updateMachineMachineNameEditText);
        machineTypesSpinner = (Spinner) findViewById(R.id.updateMachineMachineTypesSpinner);
        submitButton = (Button) findViewById(R.id.updateMachineSubmitButton);
        goBackButton = (Button) findViewById(R.id.updateMachineGoBackButton);

        machineNameEditText.setText(machine.getMachineName());
        machineTypesSpinner.setSelection(getTypesSpinnerPosition());

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkIfFormValid()) {
                    machine.setMachineName(machineNameEditText.getText().toString());
                    machine.setMachineType(String.valueOf(machineTypesSpinner.getSelectedItem()));

                    machineDatabaseHelper.updateMachine(machine.getId(), machine);

                    Intent i = new Intent(getApplicationContext(), MachinesListActivity.class);
                    startActivity(i);
                }
            }
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MachinesListActivity.class);
                startActivity(i);
            }
        });
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

    private int getTypesSpinnerPosition() {
        String[] typesSpinnerValues = getResources().getStringArray(R.array.machinetypes_array);
        return ArrayUtils.indexOf(typesSpinnerValues, machine.getMachineType());
    }
}
