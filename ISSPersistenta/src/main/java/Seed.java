import model.*;
import persistence.repository.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Seed {

    private IRepositoryConturi repoConturi;
    private IRepositoryMedici repoMedici;
    private IRepositoryDonatori repoDonatori;
    private IRepositoryPersonalTransfuzii repositoryPersonalTransfuzii;
    private IRepositoryBoli repositoryBoli;
    private IRepositoryAnalize repoAnalize;
    private IRepositoryPreparateSanguine repoPreparateSanguine;

    public Seed(){
        repoConturi = new RepositoryConturi();
        repoMedici = new RepositoryMedici();
        repoDonatori = new RepositoryDonatori();
        repositoryPersonalTransfuzii = new RepositoryPersonalTransfuzii();
        repositoryBoli = new RepositoryBoala();
        repoAnalize = new RepositoryAnalize();
        repoPreparateSanguine = new RepositoryPreparateSanguine();

    }

    public void seed() {

        adaugaConturi();
        adaugaMedici();
        adaugaPersonalTransfuzii();
        adaugaBoli();
        adaugaAnaliza();
        adaugaBoliLaAnaliza();
        adaugaDonatori();
        adaugaPreparateSanguine();
        addPreparatSanguinLaDonator();
    }

    private void addPreparatLaAnaliza(){
        Analiza analiza=repoAnalize.cautare(repoPreparateSanguine.cautareAnalizaDupaPreparat(1));
        PreparatSanguin preparatSanguin=repoPreparateSanguine.cautare(1);
        analiza.getPreparateSanguine().add(preparatSanguin);
        repoAnalize.modificare(analiza);

        PreparatSanguin preparatSanguin2=repoPreparateSanguine.cautare(5);
        Analiza analiza2=repoAnalize.cautare(8);
        analiza2.getPreparateSanguine().add(preparatSanguin2);
        repoAnalize.modificare(analiza2);
    }

    private void adaugaPreparateSanguine() {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dataRecoltarii1 = sdf.parse("21/05/2018");
            Date dataRecoltarii2 = sdf.parse("26/01/2018");

            repoPreparateSanguine.adaugare(new PreparatSanguin(dataRecoltarii1, dataRecoltarii1, 400.0, TipPreparatSanguin.SANGE_NEFILTRAT.name(), Stagiu.PRELEVARE.name()));
            repoPreparateSanguine.adaugare(new PreparatSanguin(dataRecoltarii1, dataRecoltarii1, 100.0, TipPreparatSanguin.TROMBOCITE.name(), Stagiu.PRELEVARE.name()));
            repoPreparateSanguine.adaugare(new PreparatSanguin(dataRecoltarii1, dataRecoltarii1, 100.0, TipPreparatSanguin.GLOBULE_ROSII.name(), Stagiu.PRELEVARE.name()));
            repoPreparateSanguine.adaugare(new PreparatSanguin(dataRecoltarii1, dataRecoltarii1, 200.0, TipPreparatSanguin.PLASMA.name(), Stagiu.PRELEVARE.name()));

            repoPreparateSanguine.adaugare(new PreparatSanguin(dataRecoltarii2, dataRecoltarii2, 400.0, TipPreparatSanguin.SANGE_NEFILTRAT.name(), Stagiu.PRELEVARE.name()));
            repoPreparateSanguine.adaugare(new PreparatSanguin(dataRecoltarii2, dataRecoltarii2, 100.0, TipPreparatSanguin.TROMBOCITE.name(), Stagiu.PRELEVARE.name()));
            repoPreparateSanguine.adaugare(new PreparatSanguin(dataRecoltarii2, dataRecoltarii2, 100.0, TipPreparatSanguin.GLOBULE_ROSII.name(), Stagiu.PRELEVARE.name()));
            repoPreparateSanguine.adaugare(new PreparatSanguin(dataRecoltarii2, dataRecoltarii2, 200.0, TipPreparatSanguin.PLASMA.name(), Stagiu.PRELEVARE.name()));
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    private void adaugaBoliLaAnaliza() {

        Analiza analiza = repoAnalize.cautare(7);
        Boala boala = repositoryBoli.cautareDupaNume(BoalaEnum.MALARIE.name());
        analiza.getBoli().add(boala);
        repoAnalize.modificare(analiza);

    }

    private void adaugaAnaliza() {

        repoAnalize.adaugare(new Analiza("B(III)", false));
        repoAnalize.adaugare(new Analiza("A(II)", true));
        repoAnalize.adaugare(new Analiza("O(III)", true));
        repoAnalize.adaugare(new Analiza("O(III)", false));
        repoAnalize.adaugare(new Analiza("AB(IV)", true));
        repoAnalize.adaugare(new Analiza("AB(IV)", false));

    }

    private void adaugaBoli() {

        repositoryBoli.adaugare(new Boala(BoalaEnum.HEPATITA.name()));
        repositoryBoli.adaugare(new Boala(BoalaEnum.BOLI_DE_INIMA.name()));
        repositoryBoli.adaugare(new Boala(BoalaEnum.BOLI_PSIHICE.name()));
        repositoryBoli.adaugare(new Boala(BoalaEnum.BRUCELOZA.name()));
        repositoryBoli.adaugare(new Boala(BoalaEnum.CANCER.name()));
        repositoryBoli.adaugare(new Boala(BoalaEnum.DIABET_ZAHARAT.name()));
        repositoryBoli.adaugare(new Boala(BoalaEnum.EPILEPSIE.name()));
        repositoryBoli.adaugare(new Boala(BoalaEnum.MALARIE.name()));
        repositoryBoli.adaugare(new Boala(BoalaEnum.MIOPIE_FORTE.name()));
        repositoryBoli.adaugare(new Boala(BoalaEnum.PSORIAZIS.name()));
        repositoryBoli.adaugare(new Boala(BoalaEnum.SIFILIS.name()));
        repositoryBoli.adaugare(new Boala(BoalaEnum.TBC.name()));
        repositoryBoli.adaugare(new Boala(BoalaEnum.ULCER.name()));
        repositoryBoli.adaugare(new Boala(BoalaEnum.VITILIGO.name()));

    }

    private void adaugaPersonalTransfuzii() {

        Cont cont1 = repoConturi.cautare("personal1");
        Cont cont2 = repoConturi.cautare("personal2");

        repositoryPersonalTransfuzii.adaugare(new PersonalTransfuzii("Cezara", "Grigoreta", cont1, "1970725055038", "oti_otniel97@yahoo.com"));
        repositoryPersonalTransfuzii.adaugare(new PersonalTransfuzii("Nicolae", "Ceausescu", cont2,"1970725055000", "oti_otniel97@yahoo.com"));

    }

    private void adaugaMedici(){

        //creearea medici
        Cont cont1 = repoConturi.cautare("roots");
        Cont cont2 = repoConturi.cautare("test");
        Cont cont3 = repoConturi.cautare("root");
        Medic medic1 = new Medic("Chise", "Bogdan", cont1,"297072508", "oti_otniel97@yahoo.com");
        Medic medic2 = new Medic("Boros", "Otniel", cont2,"18507255038", "oti_otniel97@yahoo.com");
        Medic medic3 = new Medic("Cezar", "Ouatu", cont3,"18607255038", "oti_otniel97@yahoo.com");

        //adaugare medici
        repoMedici.adaugare(medic1);
        repoMedici.adaugare(medic2);
        repoMedici.adaugare(medic3);
    }

    private void adaugaConturi() {

        repoConturi.adaugare(new Cont("roots", "roots"));
        repoConturi.adaugare(new Cont("root", "root"));
        repoConturi.adaugare(new Cont("test", "test"));
        repoConturi.adaugare(new Cont("chise_b", "pass"));
        repoConturi.adaugare(new Cont("chise_bogdan", "pass2"));
        repoConturi.adaugare(new Cont("chise_boby", "pass3"));
        repoConturi.adaugare(new Cont("personal1", "personal1"));
        repoConturi.adaugare(new Cont("personal12", "personal2"));

    }

    private void adaugaDonatori(){
        Cont cont2 = repoConturi.cautare("chise_bogdan");
        Cont cont3 = repoConturi.cautare("chise_boby");
        repoDonatori.adaugare(new Donator("Boros","Otniel", cont2, "1770725055098", "chise_b@yahoo.com"));
        repoDonatori.adaugare(new Donator("Chise","Bogdan", cont3,"1270725055088", "chise_bogdan@yahoo.com"));

    }

    private void addPreparatSanguinLaDonator(){

        Donator donator = repoDonatori.cautare(1);

        PreparatSanguin preparatSanguinSangeNefiltrat = repoPreparateSanguine.cautare(1);
        PreparatSanguin preparatSanguinTrombocite = repoPreparateSanguine.cautare(2);
        PreparatSanguin preparatSanguinGlobuleRosii = repoPreparateSanguine.cautare(3);
        PreparatSanguin preparatSanguinPlasma = repoPreparateSanguine.cautare(4);

        donator.getPreparateSanguine().add(preparatSanguinSangeNefiltrat);
        donator.getPreparateSanguine().add(preparatSanguinTrombocite);
        donator.getPreparateSanguine().add(preparatSanguinGlobuleRosii);
        donator.getPreparateSanguine().add(preparatSanguinPlasma);

        repoDonatori.modificare(donator);

        Donator donator2 = repoDonatori.cautare(2);

        PreparatSanguin preparatSanguinSangeNefiltrat2 = repoPreparateSanguine.cautare(5);
        PreparatSanguin preparatSanguinTrombocite2 = repoPreparateSanguine.cautare(6);
        PreparatSanguin preparatSanguinGlobuleRosii2 = repoPreparateSanguine.cautare(7);
        PreparatSanguin preparatSanguinPlasma2 = repoPreparateSanguine.cautare(8);

        donator2.getPreparateSanguine().add(preparatSanguinSangeNefiltrat2);
        donator2.getPreparateSanguine().add(preparatSanguinTrombocite2);
        donator2.getPreparateSanguine().add(preparatSanguinGlobuleRosii2);
        donator2.getPreparateSanguine().add(preparatSanguinPlasma2);

        repoDonatori.modificare(donator2);

    }
}
