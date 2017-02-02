package com.coachme.coachmeforadmin.activities.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coachme.coachmeforadmin.R;

public class AdminLoginActivity extends Activity {
    private final String ADMIN_PASSWORD = "coachme_admin";

    private EditText adminPasswordEditText;
    private Button submitButton;
    private Button backButton;
    private String passwordEntered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        initComponents();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordEntered = adminPasswordEditText.getText().toString();
                if (passwordEntered.equals(ADMIN_PASSWORD)) {
                    Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Le mot de passe saisi est incorrect, veuillez réessayer.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(i);
            }
        });
    }

    private void initComponents() {
        submitButton = (Button) findViewById(R.id.addUserSubmitButton);
        backButton = (Button) findViewById(R.id.backButton);
        adminPasswordEditText = (EditText) findViewById(R.id.adminPasswordEditText);
    }
}
