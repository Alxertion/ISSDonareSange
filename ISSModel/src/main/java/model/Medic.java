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
    public void Medic(String nume, String prenume) {
        // TODO implement here
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