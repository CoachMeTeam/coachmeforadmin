package com.coachme.coachmeforadmin.activities.admin.users;

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
import com.coachme.coachmeforadmin.database.helpers.UserDatabaseHelper;
import com.coachme.coachmeforadmin.model.User;

import org.apache.commons.lang3.StringUtils;


public class AddUserActivity extends Activity {
    private final String FIELD_REQUIRED = "Ce champ est requis.";

    private Spinner goalSpinner;
    private EditText nameEditText, firstNameEditText;
    private Button submitButton, goBackButton;
    UserDatabaseHelper userDatabaseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        initComponents();
        userDatabaseHelper = new UserDatabaseHelper();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), AdminActivity.class);
        startActivity(i);
    }

    private boolean checkIfFormValid() {
        boolean isFormValid = true;
        String goalSpinnerPrompt = getResources().getStringArray(R.array.goals_array)[0];
        if (StringUtils.isBlank(nameEditText.getText().toString())) {
            isFormValid = false;
            nameEditText.setError(FIELD_REQUIRED);
        }
        if (StringUtils.isBlank(firstNameEditText.getText().toString())) {
            isFormValid = false;
            firstNameEditText.setError(FIELD_REQUIRED);
        }
        if (StringUtils.isBlank(goalSpinner.getSelectedItem().toString()) ||
                StringUtils.equals(goalSpinner.getSelectedItem().toString(), goalSpinnerPrompt)) {
            isFormValid = false;
            Toast.makeText(getApplicationContext(),
                    "Veuillez choisir un objectif.",
                    Toast.LENGTH_SHORT).show();
        }
        return isFormValid;
    }

    private void initComponents() {
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        goalSpinner = (Spinner) findViewById(R.id.goalSpinner);
        submitButton = (Button) findViewById(R.id.addUserSubmitButton);
        goBackButton = (Button) findViewById(R.id.addUserGoBackButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkIfFormValid()) {
                    String name = nameEditText.getText().toString();
                    String firstName = firstNameEditText.getText().toString();
                    String goal = String.valueOf(goalSpinner.getSelectedItem());

                    User user = new User(name, firstName, goal, false);
                    userDatabaseHelper.addUser(user);

                    Intent i = new Intent(getApplicationContext(), UsersListActivity.class);
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
