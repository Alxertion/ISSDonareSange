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

    private int idDonator;

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
    public Boolean isEligibil() {
        // TODO implement here
        return null;
    }

    public int getIdDonator() {
        return idDonator;
    }

    public void setIdDonator(int idDonator) {
        this.idDonator = idDonator;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getDataNasterii() {
        return dataNasterii;
    }

    public void setDataNasterii(Date dataNasterii) {
        this.dataNasterii = dataNasterii;
    }

    public Double getGreutate() {
        return greutate;
    }

    public void setGreutate(Double greutate) {
        this.greutate = greutate;
    }

    public Integer getPuls() {
        return puls;
    }

    public void setPuls(Integer puls) {
        this.puls = puls;
    }

    public Integer getTensiuneArterialaSistolica() {
        return tensiuneArterialaSistolica;
    }

    public void setTensiuneArterialaSistolica(Integer tensiuneArterialaSistolica) {
        this.tensiuneArterialaSistolica = tensiuneArterialaSistolica;
    }

    public Boolean getInterventiiChirurgicaleRecente() {
        return interventiiChirurgicaleRecente;
    }

    public void setInterventiiChirurgicaleRecente(Boolean interventiiChirurgicaleRecente) {
        this.interventiiChirurgicaleRecente = interventiiChirurgicaleRecente;
    }

    public Boolean getInsarcinata() {
        return insarcinata;
    }

    public void setInsarcinata(Boolean insarcinata) {
        this.insarcinata = insarcinata;
    }

    public Boolean getPerioadaLauzie() {
        return perioadaLauzie;
    }

    public void setPerioadaLauzie(Boolean perioadaLauzie) {
        this.perioadaLauzie = perioadaLauzie;
    }

    public Boolean getPerioadaMenstruala() {
        return perioadaMenstruala;
    }

    public void setPerioadaMenstruala(Boolean perioadaMenstruala) {
        this.perioadaMenstruala = perioadaMenstruala;
    }

    public Boolean getConsumGrasimi48h() {
        return consumGrasimi48h;
    }

    public void setConsumGrasimi48h(Boolean consumGrasimi48h) {
        this.consumGrasimi48h = consumGrasimi48h;
    }

    public Boolean getConsumBauturiAlcoolice48h() {
        return consumBauturiAlcoolice48h;
    }

    public void setConsumBauturiAlcoolice48h(Boolean consumBauturiAlcoolice48h) {
        this.consumBauturiAlcoolice48h = consumBauturiAlcoolice48h;
    }

    public Boolean getSubTratament() {
        return subTratament;
    }

    public void setSubTratament(Boolean subTratament) {
        this.subTratament = subTratament;
    }

    public List<Boala> getBoliAnterioare() {
        return boliAnterioare;
    }

    public void setBoliAnterioare(List<Boala> boliAnterioare) {
        this.boliAnterioare = boliAnterioare;
    }
}