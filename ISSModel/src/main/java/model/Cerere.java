package model;

import java.io.Serializable;
import java.util.*;

/**
 * 
 */
public class Cerere implements Serializable {

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
    private Boolean RH;

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
    public Cerere(Prioritate prioritate, String grupa, Boolean RH, Double cantitateCeruta, Double cantitateActuala, Date dataEfectuare) {
        this.prioritate = prioritate;
        this.grupa = grupa;
        this.RH = RH;
        this.cantitateCeruta = cantitateCeruta;
        this.cantitateActuala = cantitateActuala;
        this.dataEfectuare = dataEfectuare;
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

    public String getStatus() {
        if (cantitateActuala >= cantitateCeruta)
            return "Gata";
        return "Nefinalizată";
    }

    public String getPrioritateString() {
        return prioritate.toString();
    }

    public String getRhString() {
        if (RH)
            return "+";
        return "-";
    }

    public String getCantitateActualaCerutaString() {
        return "" + cantitateActuala + "ml" + "/" + cantitateCeruta + "ml";
    }

    public Integer getPrioritateNr() {
        if (prioritate == Prioritate.MARE)
            return 3;
        if (prioritate == Prioritate.MEDIE)
            return 2;
        if (prioritate == Prioritate.MICA)
            return 1;
        return 0;
    }

    public void setPrioritateNr(Integer prioritate) {
        if (prioritate == 3)
            this.prioritate = Prioritate.MARE;
        if (prioritate == 2)
            this.prioritate = Prioritate.MEDIE;
        if (prioritate == 1)
            this.prioritate = Prioritate.MICA;
    }

    public String getGrupa() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }

    public Boolean getRH() {
        return RH;
    }

    public void setRH(Boolean RH) {
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