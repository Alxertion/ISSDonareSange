package model;

import java.util.*;

/**
 * 
 */
public class Pacient {


    private List<Cerere> cereri;

    private int idPacient;
    /**
     *
     */
    private String cnp;

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

    /**
     * Default constructor
     */
    public Pacient() {
    }



    /**
     * @param cnp
     * @param nume
     */

    public Pacient(String cnp, String nume, String prenume) {
        this.cnp = cnp;
        this.nume = nume;
        this.prenume = prenume;
    }

    public int getIdPacient() {
        return idPacient;
    }

    public void setIdPacient(int idPacient) {
        this.idPacient = idPacient;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
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

    public List<Cerere> getCereri() {
        return cereri;
    }

    public void setCereri(List<Cerere> cereri) {
        this.cereri = cereri;
    }
}