package model;

import java.util.*;

/**
 * 
 */
public class Persoana {

    /**
     * Default constructor
     */
    public Persoana() {
    }

    /**
     * 
     */
    private String nume;

    /**
     * 
     */
    private String prenume;

    /**
     * 
     */
    private Cont cont;



    /**
     * @param nume 
     * @param prenume
     */
    public void Persoana(String nume, String prenume) {
        // TODO implement here
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public Cont getCont() {
        return cont;
    }

    public void setCont(Cont cont) {
        this.cont = cont;
    }
}