package model;

import java.util.*;

/**
 * 
 */
public class PersonalTransfuzii extends Persoana {

    private int idPersonalTransfuzii;
    private String nume;
    private String prenume;
    private Cont cont;

    public PersonalTransfuzii() {
    }


    /**
     * @param nume
     * @param prenume
     */
    public void PersonalTransfuzii(String nume, String prenume) {
        // TODO implement here
    }


    @Override
    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    @Override
    public Cont getCont() {
        return cont;
    }

    public void setCont(Cont cont) {
        this.cont = cont;
    }

    public int getIdPersonalTransfuzii() {
        return idPersonalTransfuzii;
    }

    public void setIdPersonalTransfuzii(int idPersonalTransfuzii) {
        this.idPersonalTransfuzii = idPersonalTransfuzii;
    }

}