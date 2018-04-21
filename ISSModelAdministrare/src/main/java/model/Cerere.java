package model;

import java.util.*;

/**
 * 
 */
public class Cerere {

    /**
     * Default constructor
     */
    public Cerere() {
    }

    /**
     * 
     */
    private Medic medic;

    /**
     * 
     */
    private Prioritate prioritate;

    /**
     * 
     */
    private String grupa;

    /**
     * 
     */
    private String RH;

    /**
     * 
     */
    private Double cantitateCeruta;

    /**
     * 
     */
    private Double cantitateActuala;

    /**
     * 
     */
    private Date dataEfectuare;

    /**
     * 
     */
    private Pacient pacient;





    /**
     * @param medic 
     * @param prioritate 
     * @param grupa 
     * @param RH 
     * @param cantitateCeruta 
     * @param dataEfectuare 
     * @param pacient
     */
    public void Cerere(Medic medic, Prioritate prioritate, String grupa, String RH, Double cantitateCeruta, Date dataEfectuare, Pacient pacient) {
        // TODO implement here
    }

    /**
     * @return
     */
    public Boolean isFinalizata() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Prioritate getPrioritate() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public String getGrupa() {
        // TODO implement here
        return "";
    }

    /**
     * @return
     */
    public String getRH() {
        // TODO implement here
        return "";
    }

    /**
     * @return
     */
    public Double getCantitateCeruta() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Double getCantitateActuala() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Date getDataEfectuare() {
        // TODO implement here
        return null;
    }

    /**
     * @param newCantitateActuala
     */
    public void setCantitateActuala(Double newCantitateActuala) {
        // TODO implement here
    }

    /**
     * @return
     */
    public Medic getMedic() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Pacient getPacient() {
        // TODO implement here
        return null;
    }

}