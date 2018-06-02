package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 */
public class Donator extends Persoana implements Serializable{

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
    private String telefon;

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

    private List<PreparatSanguin> preparateSanguine;

    /**
     *
     */
    private Double latitudine;

    /**
     *
     */
    private Double longitudine;

    /**
     * @param nume 
     * @param prenume
     */
    public Donator(String nume, String prenume, Cont cont, String CNP, String email) {
        super(nume, prenume, cont, CNP, email);
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


    public List<PreparatSanguin> getPreparateSanguine() {
        return preparateSanguine;
    }

    public void setPreparateSanguine(List<PreparatSanguin> preparateSanguine) {
        this.preparateSanguine = preparateSanguine;
    }

    public String toString(){
        return super.toString()+" "+super.getCNP();
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Double getLatitudine(){
        return latitudine;
    }

    public void setLatitudine(Double latitudine){
        this.latitudine=latitudine;
    }

    public Double getLongitudine(){
        return longitudine;
    }

    public void setLongitudine(Double longitudine){
        this.longitudine=longitudine;
    }

}