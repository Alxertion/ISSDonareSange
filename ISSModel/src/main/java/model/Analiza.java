package model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 */
public class Analiza implements Serializable {

    /**
     * Default constructor
     */
    private int idAnaliza;
    private String grupa;
    private Boolean RH;
    private List<Boala> boli;
    private List<PreparatSanguin> preparateSanguine;

    public Analiza() {
    }

    /**
     * @param grupa
     * @param RH
     */
    public Analiza(String grupa, Boolean RH) {
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

    public Boolean getRH() {
        return RH;
    }

    public void setRH(Boolean RH) {
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

    @Override
    public String toString(){
        String toFile="Grupa sanguina:"+grupa+".\n"+"RH:"+RH.toString()+".\n";
        if(boli==null)
            toFile=toFile+"Rezultatul analizelor: POZITIV -> APT PENTRU DONARE";
        else{
            toFile=toFile+"Rezultatul analizelor: NEGATIV.\nMotive:\n";
            for(Boala boala:boli){
                toFile=toFile + boala.getNume()+"\n";
            }
        }
        return toFile;
    }
}