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
        System.out.println("Cevlslla");
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

    private String CNP;

    private String email;



    /**
     * @param nume 
     * @param prenume
     */
    public Persoana(String nume, String prenume, Cont cont, String CNP, String email) {
        this.nume = nume;
        this.prenume = prenume;
        this.cont = cont;
        this.email = email;
        this.CNP = CNP;
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

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString(){
        return nume+" "+prenume;
    }
}