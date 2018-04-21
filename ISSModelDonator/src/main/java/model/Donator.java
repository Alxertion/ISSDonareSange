package model;

import java.util.*;

/**
 * 
 */
public class Donator extends Persoana {

    /**
     * Default constructor
     */
    public Donator() {
    }

    /**
     * 
     */
    private String sex;

    /**
     * 
     */
    private Date dataNasterii;

    /**
     * 
     */
    private Double greutate;

    /**
     * 
     */
    private Integer puls;

    /**
     * 
     */
    private Integer tensiuneArterialaSistolica;

    /**
     * 
     */
    private Boolean interventiiChirurgicaleRecente;

    /**
     * 
     */
    private Boolean insarcinata;

    /**
     * 
     */
    private Boolean perioadaLauzie;

    /**
     * 
     */
    private Boolean perioadaMenstruala;

    /**
     * 
     */
    private Boolean consumGrasimi48h;

    /**
     * 
     */
    private Boolean consumBauturiAlcoolice48h;

    /**
     * 
     */
    private Boolean subTratament;

    /**
     * 
     */
    private List<Boala> boliAnterioare;






    /**
     * @param nume 
     * @param prenume
     */
    public void Donator(String nume, String prenume) {
        // TODO implement here
    }

    /**
     * @return
     */
    public String getSex() {
        // TODO implement here
        return "";
    }

    /**
     * @return
     */
    public Date getDataNasterii() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Boolean isEligibil() {
        // TODO implement here
        return null;
    }

}