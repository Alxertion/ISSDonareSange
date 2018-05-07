package model;

import java.io.Serializable;
import java.util.*;

/**
 * 
 */
public class Medic extends Persoana implements Serializable{

    private int idMedic;
    private int idSpital;
    private List<Cerere> cereri;

    /**
     * Default constructor
     */
    public Medic() {
    }

    /**
     *
     */


    /**
     * @param nume 
     * @param prenume
     */
    public Medic(int idMedic, String nume, String prenume, Cont cont, String CNP, String email, int idSpital) {
        super(nume, prenume, cont, CNP, email);
        this.idSpital = idSpital;
        this.idMedic = idMedic;
    }

    public String getUsername() {
        return getCont().getUsername();
    }

    public int getIdMedic() {
        return idMedic;
    }

    public void setIdMedic(int idMedic) {
        this.idMedic = idMedic;
    }

    public List<Cerere> getCereri() {
        return cereri;
    }

    public void setCereri(List<Cerere> cereri) {
        this.cereri = cereri;
    }

    public int getIdSpital() {
        return idSpital;
    }

    public void setIdSpital(int idSpital) {
        this.idSpital = idSpital;
    }
}