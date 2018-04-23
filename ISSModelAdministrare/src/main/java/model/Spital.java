package model;
import java.util.*;

/**
 * 
 */
public class Spital {

    private List<Pacient> pacienti;
    /**
     *
     */

    private List<Medic> medici;
    /**
     * 
     */
    private Integer idSpital;

    /**
     * 
     */
    private String nume;

    /**
     * 
     */
    private double longitudine;

    /**
     * 
     */
    private double latitudine;


    /**
     * Default constructor
     */
    public Spital() {
    }

    /**
     * @param idSpital
     * @param nume 
     * @param longitudine 
     * @param latitudine
     */
    public Spital(Integer idSpital, String nume, double longitudine, double latitudine) {
        this.idSpital = idSpital;
        this.nume = nume;
        this.longitudine = longitudine;
        this.latitudine = latitudine;
    }

    public List<Medic> getMedici() {
        return medici;
    }

    public void setMedici(List<Medic> medici) {
        this.medici = medici;
    }

    public Integer getIdSpital() {
        return idSpital;
    }

    public void setIdSpital(Integer idSpital) {
        this.idSpital = idSpital;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    public List<Pacient> getPacienti() {
        return pacienti;
    }

    public void setPacienti(List<Pacient> pacienti) {
        this.pacienti = pacienti;
    }
}