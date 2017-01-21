package com.coachme.coachmeforadmin.activities.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.coachme.coachmeforadmin.R;
import com.coachme.coachmeforadmin.activities.admin.users.AddUserActivity;

public class AdminLoginActivity extends AppCompatActivity {
    //variables
    private TextView affichage;
    private EditText mdpAdmin;
    private Button Valider;
    private Button retour;
    private String Code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
    }

    public void onStart() {
        super.onStart();
        //recuperation des object par les ID
        Valider = (Button) findViewById(R.id.butOK);
        retour = (Button) findViewById(R.id.buttonRetour1);
        mdpAdmin = (EditText) findViewById(R.id.editTextCode);
        affichage = (TextView) findViewById(R.id.textViewCode);
        //lorsque l'on clique
        Valider.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                    //test du code admin
                        Code = mdpAdmin.getText().toString();
                        if (testCodeAdmin(Code)) {
                            //si c'est OK
                            affichage.setText("BRAVO ! le mot de passe est correct !!");
                            //changement d'activity vers le formulaire d'inscription
                            Intent i = new Intent(getApplicationContext(), AddUserActivity.class);
                            startActivity(i);
                        } else {
                            affichage.setText("Attention, le mot de passe est faux !!");
                        }
            }
        });

        retour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //changement d'actvity vers l'accueil
                Intent i = new Intent(getApplicationContext(),AdminActivity.class );
                startActivity(i);
            }
        });

    }


    public Boolean testCodeAdmin(String valeur){
        return valeur.equals("bob");
    }

}
