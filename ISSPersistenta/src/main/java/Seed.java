import model.Cont;
import model.Donator;
import persistence.repository.IRepositoryConturi;
import persistence.repository.IRepositoryDonatori;
import persistence.repository.RepositoryConturi;
import persistence.repository.RepositoryDonatori;

public class Seed {

    IRepositoryConturi repoConturi;
    IRepositoryDonatori repoDonatori;

    public Seed(){
        repoConturi = new RepositoryConturi();
        repoDonatori = new RepositoryDonatori();
    }

    public void seed(){

//        adaugaConturi();
        repoConturi.getAll();


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
