package model;

import java.io.Serializable;
import java.util.*;

/**
 * 
 */
public class Pacient implements Serializable{


    private List<Cerere> cereri;

    private List<PreparatSanguin> preparateSanguine;

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

    public String getNumePrenume() {
        return getNume() + " " + getPrenume();
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

    public List<PreparatSanguin> getPreparateSanguine() {
        return preparateSanguine;
    }

    public void setPreparateSanguine(List<PreparatSanguin> preparateSanguine) {
        this.preparateSanguine = preparateSanguine;
    }
}