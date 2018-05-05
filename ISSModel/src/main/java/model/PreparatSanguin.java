package model;

import java.util.*;

/**
 *
 */
public class PreparatSanguin {

    /**
     * Default constructor
     */
    public PreparatSanguin() {
    }

    /**
     *
     */
    private Date dataPrelevare;

    /**
     *
     */
    private Date dataExpirare;

    /**
     *
     */
    private Double cantitate;

    /**
     *
     */
    private String stagiu;

    /**
     *
     */
    private int idPreparatSanguin;

    /**
     *
     */
    private String tip;


    /**
     * @param dataPrelevare
     * @param dataExpirare
     * @param cantitate
     */

    public PreparatSanguin(Date dataPrelevare, Date dataExpirare, Double cantitate, String tip, String stagiu) {
        this.dataPrelevare = dataPrelevare;
        this.dataExpirare = dataExpirare;
        this.cantitate = cantitate;
        this.stagiu = stagiu;
        this.tip = tip;
    }


    /**
     * @return
     */
    public Boolean isExpirat() {
        // TODO implement here
        return null;
    }

    public Date getDataPrelevare() {
        return dataPrelevare;
    }

    public void setDataPrelevare(Date dataPrelevare) {
        this.dataPrelevare = dataPrelevare;
    }

    public Date getDataExpirare() {
        return dataExpirare;
    }

    public void setDataExpirare(Date dataExpirare) {
        this.dataExpirare = dataExpirare;
    }

    public Double getCantitate() {
        return cantitate;
    }

    public void setCantitate(Double cantitate) {
        this.cantitate = cantitate;
    }

    public String getStagiu() {
        return stagiu;
    }

    public void setStagiu(String stagiu) {
        this.stagiu = stagiu;
    }

    public int getIdPreparatSanguin() {
        return idPreparatSanguin;
    }

    public void setIdPreparatSanguin(int idPreparatSanguin) {
        this.idPreparatSanguin = idPreparatSanguin;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}