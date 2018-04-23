package model;

import java.util.*;

/**
 * 
 */
public class Cerere {

    private int idCerere;
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
     * Default constructor
     */
    public Cerere() {
    }

    /**
     * @param prioritate 
     * @param grupa 
     * @param RH 
     * @param cantitateCeruta 
     * @param dataEfectuare
     */
    public void Cerere(Prioritate prioritate, String grupa, String RH, Double cantitateCeruta, Date dataEfectuare) {
        // TODO implement here
    }

    /**
     * @return
     */
    public Boolean isFinalizata() {
        // TODO implement here
        return null;
    }

    public Prioritate getPrioritate() {
        return prioritate;
    }

    public void setPrioritate(Prioritate prioritate) {
        this.prioritate = prioritate;
    }

    public String getGrupa() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }

    public String getRH() {
        return RH;
    }

    public void setRH(String RH) {
        this.RH = RH;
    }

    public Double getCantitateCeruta() {
        return cantitateCeruta;
    }

    public void setCantitateCeruta(Double cantitateCeruta) {
        this.cantitateCeruta = cantitateCeruta;
    }

    public Double getCantitateActuala() {
        return cantitateActuala;
    }

    public void setCantitateActuala(Double cantitateActuala) {
        this.cantitateActuala = cantitateActuala;
    }

    public Date getDataEfectuare() {
        return dataEfectuare;
    }

    public void setDataEfectuare(Date dataEfectuare) {
        this.dataEfectuare = dataEfectuare;
    }

    public int getIdCerere() {
        return idCerere;
    }

    public void setIdCerere(int idCerere) {
        this.idCerere = idCerere;
    }
}