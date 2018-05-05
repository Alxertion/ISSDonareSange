package model;

import java.io.Serializable;

/**
 * 
 */
public class Persoana implements Serializable{

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
    public Persoana(String nume, String prenume,Cont cont) {
        this.nume=nume;
        this.prenume=prenume;
        this.cont=cont;
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