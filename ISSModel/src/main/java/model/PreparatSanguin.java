package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 *
 */
public class PreparatSanguin implements Serializable, Comparable<PreparatSanguin> {

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

    public Boolean isExpirat() {
        return !(new Date()).before(this.dataExpirare);
    }

    public String getValid() {
        if (isExpirat())
            return "Nu";
        return "Da";
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

    public String getCantitateString() {
        return "" + cantitate + "ml";
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

    public int compare(PreparatSanguin prep1, PreparatSanguin prep2) {
        LocalDate datePrep1 = prep1.getDataPrelevare().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate datePrep2 = prep2.getDataPrelevare().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        if(datePrep1.isBefore(datePrep2)) return 1;
        else if(datePrep1.isEqual(datePrep2)) return 0;
        return -1;
    }

    public int compareTo(PreparatSanguin preparatSanguin){
        return preparatSanguin.getDataPrelevare().compareTo(this.getDataPrelevare());
    }
}