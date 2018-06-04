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
    public PreparatSanguinDTO(int ID,String tip, Date dataPrelevare, Date dataExpirare, String IDDonator, String IDAnaliza, Double cantitate,String IDPacient , String stagiu) {
        this.dataPrelevare = dataPrelevare;
        this.dataExpirare = dataExpirare;
        this.cantitate = cantitate;
        this.stagiu = stagiu;
        this.tip = tip;
        this.ID=ID;
        setIDAnalizaNA(IDAnaliza);
        setIDDonatorNA(IDDonator);
        setIDPacientNA(IDPacient);
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

    public String getIDAnalizaNA() {
        if(IDAnaliza==-1){
            return "N/A";
        }
        return ""+IDAnaliza;
    }

    public void setIDAnaliza(int IDAnaliza) {
        this.IDAnaliza = IDAnaliza;
    }

    public void setIDAnalizaNA(String IDAnaliza) {
        if(IDAnaliza.equals("N/A")){
            this.IDAnaliza = -1;
        }
        try {
            this.IDAnaliza =Integer.parseInt(IDAnaliza);
        }catch (NumberFormatException err){
            this.IDAnaliza = -1;
        }
    }


    public int getIDPacient() {
        return IDPacient;
    }


    public String getIDPacientNA() {
        if(IDPacient==-1){
            return "N/A";
        }
        return ""+IDPacient;
    }

    public void setIDPacient(int IDPacient) {
        this.IDPacient = IDPacient;
    }

    public void setIDPacientNA(String IDPacient) {
        if(IDPacient.equals("N/A")){
            this.IDPacient = -1;
        }
        try {
            this.IDPacient =Integer.parseInt(IDPacient);
        }catch (NumberFormatException err){
            this.IDPacient = -1;
        }
    }

    public int getIDDonator() {
        return IDDonator;
    }

    public String getIDDonatorNA() {
        if(IDDonator==-1){
            return "N/A";
        }
        return ""+IDDonator;
    }

    public void setIDDonator(int IDDonator) {
        this.IDDonator = IDDonator;
    }

    public void setIDDonatorNA(String IDDonator) {
        if(IDDonator.equals("N/A")){
            this.IDDonator = -1;
        }
        try {
            this.IDDonator =Integer.parseInt(IDDonator);
        }catch (NumberFormatException err){
            this.IDDonator = -1;
        }
    }
}
