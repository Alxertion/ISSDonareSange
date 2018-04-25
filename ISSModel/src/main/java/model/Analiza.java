package model;

import java.util.List;

/**
 * 
 */
public class Analiza {

    /**
     * Default constructor
     */
    private int idAnaliza;
    private String grupa;
    private String RH;
    private List<Boala> boli;
    private List<PreparatSanguin> preparateSanguine;

    public Analiza() {
    }

    /**
     * @param grupa
     * @param RH
     */
    public Analiza(String grupa, String RH) {
        this.grupa = grupa;
        this.RH = RH;
    }

    /**
     * @return
     */
    public Boolean isValida() {
        // TODO implement here
        return null;
    }

    public int getIdAnaliza() {
        return idAnaliza;
    }

    public void setIdAnaliza(int idAnaliza) {
        this.idAnaliza = idAnaliza;
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

    public List<Boala> getBoli() {
        return boli;
    }

    public void setBoli(List<Boala> boli) {
        this.boli = boli;
    }

    public List<PreparatSanguin> getPreparateSanguine() {
        return preparateSanguine;
    }

    public void setPreparateSanguine(List<PreparatSanguin> preparateSanguine) {
        this.preparateSanguine = preparateSanguine;
    }

    public void setAllFields(Analiza analiza) {
        this.grupa = analiza.getGrupa();
        this.RH = analiza.getRH();
    }
}