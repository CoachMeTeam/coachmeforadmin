package com.coachme.coachmeforadmin.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coachme.coachmeforadmin.R;
import com.coachme.coachmeforadmin.model.Adherent;
import com.coachme.coachmeforadmin.utils.FichierMethode;

import java.util.ArrayList;
import java.util.List;


public class TestFichier extends AppCompatActivity {
    //variables
    private TextView affichageFichier;
    private Button buttonRetour;
    private FichierMethode file = new FichierMethode();
    private List<Adherent> listAd = new ArrayList<>();
    // private ServiceAdherent serviceAdherent = new ServiceAdherent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testfichier);
    }

    public void onStart() {
        super.onStart();
        //recuperation des object par les ID
        affichageFichier = (TextView) findViewById(R.id.textViewtestfile);
        buttonRetour = (Button) findViewById(R.id.button2);
        try {
            String test = "";
            //recuperation de la liste d'adherent du ficheir
            listAd = file.ReadSettings(getApplicationContext());
            //affichage de la liste
            for (int i = 0; i < listAd.size(); i++) {
                test = test + listAd.get(i).getNUMERO_ID() + " " +
                        listAd.get(i).getNOM() + " " +
                        listAd.get(i).getPRENOM() + " " +
                        listAd.get(i).getOBJECTIF() + "\n";
            }
            //affichage
            affichageFichier.setText(test);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            affichageFichier.setText("Erreur de chargement des adherents");
        }

        buttonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //retour page d'accueil
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

            }
        });

    }
}
