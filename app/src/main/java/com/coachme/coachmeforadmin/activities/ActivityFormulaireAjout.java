package com.coachme.coachmeforadmin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.coachme.coachmeforadmin.R;
import com.coachme.coachmeforadmin.model.Adherent;
import com.coachme.coachmeforadmin.utils.FichierMethode;

import java.util.ArrayList;
import java.util.List;


public class ActivityFormulaireAjout extends AppCompatActivity {
    //Declaration des variables utilisees:
    private Spinner spinner;
    private TextView TV1, TV2, TV3, TV4, TV5;
    private EditText ED1, ED2, ED3;
    private Button Val, ret;
    private String nom = "", prenom = "", objectif = "";
    private String numero = "";
    private FichierMethode file = new FichierMethode();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire_ajout);
    }

    public void onStart() {
        super.onStart();
        //Récupération des objets via les ID:
        ED1 = (EditText) findViewById(R.id.eTlastname);
        ED2 = (EditText) findViewById(R.id.eTfirstname);
        ED3 = (EditText) findViewById(R.id.eTnumero);
        TV1 = (TextView) findViewById(R.id.textView1);
        TV2 = (TextView) findViewById(R.id.textView2);
        TV3 = (TextView) findViewById(R.id.textView3);
        TV4 = (TextView) findViewById(R.id.textView4);
        TV5 = (TextView) findViewById(R.id.textView5);
        spinner = (Spinner) findViewById(R.id.spinnerObjectif);
        Val = (Button) findViewById(R.id.button);
        ret = (Button) findViewById(R.id.buttonRetour4);
        //SPINNER:
        List<String> list = new ArrayList<String>();
        list.add("Fitness");
        list.add("Remise_en_forme");
        list.add("Prise_de_masse");
        list.add("Séche");
        list.add("Perte_de_poids_1_-_5_kg");
        list.add("Perte_de_poids_6_-_10_kg");
        list.add("Perte_de_poids_11_-_15_kg");
        list.add("Perte_de_poids_>15_kg");
        list.add("Sportif_de_haut_niveau");
        //Data adapter utilise pour le spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        //Affichage:
        TV1.setText("NOM DE FAMILLE:");
        TV2.setText("PRENOM:");
        TV3.setText("NUMERO:");
        TV4.setText("OBJECTIF:");
        // si on clique:
        Val.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Recuperer les valeur du nouvel adherent:
                nom = ED1.getText().toString();
                prenom = ED2.getText().toString();
                numero = ED3.getText().toString();
                objectif = String.valueOf(spinner.getSelectedItem());
                //test si tous les champs sont remplis
                if (!nom.equals("") && !prenom.equals("") && !objectif.equals("") && !numero.equals("")) {
                    try {
                        //on cree un adherent
                        Adherent adherent = new Adherent(numero, nom, prenom, objectif);
                        // ouvre et ecrit dans le fichier qui sera remplace par la suite par une bdd
                        file.WriteSettings(getApplicationContext(), adherent.getNUMERO_ID() + " "
                                + adherent.getNOM() + " "
                                + adherent.getPRENOM() + " "
                                + adherent.getOBJECTIF() + "\n");

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    //changement de "page", activity via intent
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Problème d'enregistrement, renseigner tous les champs.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //changement de "page", activity via intent
                Intent i = new Intent(getApplicationContext(), TestFichier.class);
                startActivity(i);
            }
        });
    }
}
