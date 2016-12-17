package com.coachme.coachmeforadmin.model;

public class Adherent {

    private String NUMERO_ID;
    private String NOM;
    private String PRENOM;
    private String OBJECTIF;

    public Adherent() {
    }

    public Adherent(String NUMERO_ID, String NOM, String PRENOM, String OBJECTIF) {
        this.NUMERO_ID = NUMERO_ID;
        this.NOM = NOM;
        this.PRENOM = PRENOM;
        this.OBJECTIF = OBJECTIF;
    }

    public String getNUMERO_ID() {
        return NUMERO_ID;
    }

    public void setNUMERO_ID(String NUMERO_ID) {
        this.NUMERO_ID = NUMERO_ID;
    }

    public String getNOM() {
        return NOM;
    }

    public void setNOM(String NOM) {
        this.NOM = NOM;
    }

    public String getPRENOM() {
        return PRENOM;
    }

    public void setPRENOM(String PRENOM) {
        this.PRENOM = PRENOM;
    }

    public String getOBJECTIF() {
        return OBJECTIF;
    }

    public void setOBJECTIF(String OBJECTIF) {
        this.OBJECTIF = OBJECTIF;
    }

    public String toString() {
        return "NUMERO_ID : " + NUMERO_ID + ", NOM : " + NOM + ", PRENOM : " + PRENOM + ", OBJECTIF : " + OBJECTIF + "\n";
    }
}
