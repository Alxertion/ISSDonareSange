import model.Cont;
import model.Medic;
import model.Donator;
import persistence.repository.*;

public class Seed {

    IRepositoryConturi repoConturi;
    IRepositoryMedici repoMedici;
    IRepositoryDonatori repoDonatori;

    public Seed(){
        repoConturi = new RepositoryConturi();
        repoMedici = new RepositoryMedici();
        repoDonatori = new RepositoryDonatori();
    }

    public void seed(){

//        adaugaConturi();
        adaugaMedici();
        adaugareContMedici();
        HibernateFactory.closeFactory();


    }

    private void adaugareContMedici() {

        Medic medic = repoMedici.cautare(2);
        Cont cont1 = repoConturi.cautare("roots");
        medic.setCont(cont1);
        repoMedici.modificare(medic);

        Medic medic2 = repoMedici.cautare(1);
        Cont cont2 = repoConturi.cautare("root");
        medic2.setCont(cont2);
        repoMedici.modificare(medic2);


    }

    private void adaugaMedici(){

        //creearea medici
        Medic medic1 = new Medic("Chise", "Bogdan");
        Medic medic2 = new Medic("Boros", "Otniel");
        Medic medic3 = new Medic("Cezar", "Ouatu");

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

    }

    private void adaugaDonatori(){
        repoDonatori.adaugare(new Donator("Boros","Otniel"));
        repoDonatori.adaugare(new Donator("Chise","Bogdan"));
    }
}
