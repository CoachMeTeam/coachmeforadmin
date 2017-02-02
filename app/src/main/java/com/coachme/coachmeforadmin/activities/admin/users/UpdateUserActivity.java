
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
import com.coachme.coachmeforadmin.database.helpers.UserDatabaseHelper;
import com.coachme.coachmeforadmin.model.User;
import com.coachme.coachmeforadmin.utils.Helper;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class UpdateUserActivity extends Activity {
    private final String FIELD_REQUIRED = "Ce champ est requis.";

    private Spinner goalSpinner;
    private EditText nameEditText, firstNameEditText;
    private Button submitButton, goBackButton;
    UserDatabaseHelper userDatabaseHelper;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        String jsonUser = getIntent().getStringExtra("userToUpdate");
        user = Helper.convertJsonToObject(jsonUser, User.class);

        initComponents();
        userDatabaseHelper = new UserDatabaseHelper();
    }

    private void initComponents() {
        nameEditText = (EditText) findViewById(R.id.updateUserNameEditText);
        firstNameEditText = (EditText) findViewById(R.id.updateUserFirstNameEditText);
        goalSpinner = (Spinner) findViewById(R.id.updateUserGoalSpinner);
        submitButton = (Button) findViewById(R.id.updateUserSubmitButton);
        goBackButton = (Button) findViewById(R.id.updateUserGoBackButton);

        nameEditText.setText(user.getName());
        firstNameEditText.setText(user.getFirstName());
        goalSpinner.setSelection(getGoalSpinnerPosition());

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkIfFormValid()) {
                    user.setName(nameEditText.getText().toString());
                    user.setFirstName(firstNameEditText.getText().toString());
                    user.setGoal(String.valueOf(goalSpinner.getSelectedItem()));

                    userDatabaseHelper.updateUser(user.getId(), user);

                    Intent i = new Intent(getApplicationContext(), UsersListActivity.class);
                    startActivity(i);
                }
            }
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), UsersListActivity.class);
                startActivity(i);
            }
        });
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

    private int getGoalSpinnerPosition() {
        String[] goalSpinnerValues = getResources().getStringArray(R.array.goals_array);
        return ArrayUtils.indexOf(goalSpinnerValues, user.getGoal());
    }
}
