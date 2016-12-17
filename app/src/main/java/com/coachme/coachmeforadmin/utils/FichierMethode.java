package com.coachme.coachmeforadmin.utils;

import android.content.Context;
import android.widget.Toast;

import com.coachme.coachmeforadmin.model.Adherent;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class FichierMethode {
    //ecrire dans le fichier
    public void WriteSettings(Context context, String data) {
        //variables
        FileOutputStream fOut = null;
        OutputStreamWriter osw = null;

        try {
            //"ouvrir le fichier" utilisation de fileoutputstream
            fOut = context.openFileOutput("COATCHMEFile.dat", Context.MODE_APPEND);
            osw = new OutputStreamWriter(fOut);
            //on ecrit dans le fichier
            osw.write(data);
            osw.flush();
            Toast.makeText(context, "Save OK", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Save KO", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                // on ferme le outputstreamer et fileoutstrem
                osw.close();
                fOut.close();
            } catch (IOException e) {
                Toast.makeText(context, "Save KO", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // lecture dans le fichier et recuperation d'une liste d'adherent
    public List<Adherent> ReadSettings(Context context) {
        //variable
        FileInputStream fIn = null;
        InputStreamReader isr = null;
        String ligne;
        char[] inputBuffer = new char[255];
        String data = null;
        //liste d'adherent
        List<Adherent> listAdherent = new ArrayList<>();

        try {
            //"ouverture du ficheir" utilisation de fileinputstream
            fIn = context.openFileInput("COATCHMEFile.dat");
            isr = new InputStreamReader(fIn);
            BufferedReader br = new BufferedReader(isr);

            String s;
            //on recupere ligne par ligne les donnees du fichier jusqua la derneire
            while ((s = br.readLine()) != null) {
                //on cree un adherent
                Adherent adherent = new Adherent();
                //on separe la ligne par les espaces.
                String[] parts = s.split(" ");
                // on renseigne les informations dans l'adherent
                adherent.setNUMERO_ID(parts[0]);
                adherent.setNOM(parts[1]);
                adherent.setPRENOM(parts[2]);
                adherent.setOBJECTIF(parts[3]);
                //on ajout l'adherent a la liste
                listAdherent.add(adherent);

            }
            // on ferme le inputstreamReader et fileinputstram
            isr.close();
            fIn.close();

        } catch (Exception e) {

        }
        //on retourne la liste
        return listAdherent;
    }

}
