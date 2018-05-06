package model;
import java.io.Serializable;
import java.util.*;

/**
 *
 */
public class CentruTransfuzii implements Serializable{

    private List<PersonalTransfuzii> personalTransfuzii;
    private List<Donator> donatori;


    /**
     *
     */
    private Integer idCentruTransfuzii;

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
    public CentruTransfuzii() {
    }

    public CentruTransfuzii(Integer idCentruTransfuzii, String nume, double longitudine, double latitudine) {
        this.idCentruTransfuzii = idCentruTransfuzii;
        this.nume = nume;
        this.longitudine = longitudine;
        this.latitudine = latitudine;
    }

    public List<PersonalTransfuzii> getPersonalTransfuzii() {
        return personalTransfuzii;
    }

    public void setPersonalTransfuzii(List<PersonalTransfuzii> personalTransfuzii) {
        this.personalTransfuzii = personalTransfuzii;
    }

    public Integer getIdCentruTransfuzii() {
        return idCentruTransfuzii;
    }

    public void setIdCentruTransfuzii(Integer idCentruTransfuzii) {
        this.idCentruTransfuzii = idCentruTransfuzii;
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

    public List<Donator> getDonatori() {
        return donatori;
    }

    public void setDonatori(List<Donator> donatori) {
        this.donatori = donatori;
    }

}