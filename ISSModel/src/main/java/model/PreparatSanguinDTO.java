package model;

import java.io.Serializable;
import java.util.Date;

public class PreparatSanguinDTO implements Serializable {
    private Date dataPrelevare, dataExpirare;
    private Double cantitate;
    private String tip, stagiu;
    private int ID, IDAnaliza, IDPacient,IDDonator;

    public PreparatSanguinDTO(int ID,String tip, Date dataPrelevare, Date dataExpirare, int IDDonator, int IDAnaliza, Double cantitate,int IDPacient , String stagiu) {
        this.dataPrelevare = dataPrelevare;
        this.dataExpirare = dataExpirare;
        this.cantitate = cantitate;
        this.stagiu = stagiu;
        this.tip = tip;
        this.ID=ID;
        this.IDDonator=IDDonator;
        this.IDAnaliza=IDAnaliza;
        this.IDPacient=IDPacient;
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

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getStagiu() {
        return stagiu;
    }

    public void setStagiu(String stagiu) {
        this.stagiu = stagiu;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getIDAnaliza() {
        return IDAnaliza;
    }

    public void setIDAnaliza(int IDAnaliza) {
        this.IDAnaliza = IDAnaliza;
    }

    public int getIDPacient() {
        return IDPacient;
    }

    public void setIDPacient(int IDPacient) {
        this.IDPacient = IDPacient;
    }

    public int getIDDonator() {
        return IDDonator;
    }

    public void setIDDonator(int IDDonator) {
        this.IDDonator = IDDonator;
    }
}
