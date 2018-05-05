package model;

import java.util.*;

/**
 * 
 */
public class Medic extends Persoana {

    private int idMedic;
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
    public Medic(String nume, String prenume, Cont cont, String CNP, String email) {
        super(nume, prenume, cont, CNP, email);
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
}