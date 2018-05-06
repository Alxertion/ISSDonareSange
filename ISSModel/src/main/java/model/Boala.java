package model;

import java.io.Serializable;

public class Boala implements Serializable{

    private int idBoala;
    private String nume;

    public Boala(){

    }

    public Boala(String nume){
        this.nume = nume;
    }

    public int getIdBoala() {
        return idBoala;
    }

    public void setIdBoala(int idBoala) {
        this.idBoala = idBoala;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
}
