package model;

import java.io.Serializable;
import java.util.*;

/**
 * 
 */
public class PersonalTransfuzii extends Persoana implements Serializable {

    private int idPersonalTransfuzii;

    public PersonalTransfuzii() {
    }

    /**
     * @param nume
     * @param prenume
     */
    public PersonalTransfuzii(String nume, String prenume, Cont cont, String CNP, String email) {
        super(nume, prenume, cont, CNP, email);
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
}