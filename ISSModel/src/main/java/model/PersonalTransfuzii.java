package model;

import java.io.Serializable;
import java.util.*;

/**
 * 
 */
public class PersonalTransfuzii extends Persoana implements Serializable {

    private int idPersonalTransfuzii;
    private int idCentruTransfuzii;

    public PersonalTransfuzii() {
    }

    /**
     * @param nume
     * @param prenume
     */
    public PersonalTransfuzii(int idPersonalTransfuzii, String nume, String prenume, Cont cont, String CNP, String email, int idCentruTransfuzii) {
        super(nume, prenume, cont, CNP, email);
        this.idCentruTransfuzii = idCentruTransfuzii;
        this.idPersonalTransfuzii = idPersonalTransfuzii;
    }

    public String getUsername() {
        return getCont().getUsername();
    }

    public int getIdPersonalTransfuzii() {
        return idPersonalTransfuzii;
    }

    public void setIdPersonalTransfuzii(int idPersonalTransfuzii) {
        this.idPersonalTransfuzii = idPersonalTransfuzii;
    }

    public int getIdCentruTransfuzii() {
        return idCentruTransfuzii;
    }

    public void setIdCentruTransfuzii(int idCentruTransfuzii) {
        this.idCentruTransfuzii = idCentruTransfuzii;
    }
}