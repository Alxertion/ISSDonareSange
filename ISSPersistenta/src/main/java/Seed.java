import model.Cont;
import model.Medic;
import persistence.repository.IRepositoryConturi;
import persistence.repository.IRepositoryMedici;
import persistence.repository.RepositoryConturi;
import persistence.repository.RepositoryMedici;

public class Seed {

    IRepositoryConturi repoConturi;
    IRepositoryMedici repoMedici;

    public Seed(){
        repoConturi = new RepositoryConturi();
        repoMedici = new RepositoryMedici();
    }

    public void seed(){

//        adaugaConturi();
        repoConturi.getAll();


    }

    private void adaugaMedici(){

        repoMedici.adaugare(new Medic("Chise", "Bogdan"));

    }

    private void adaugaConturi() {

        repoConturi.adaugare(new Cont("roots", "roots"));
        repoConturi.adaugare(new Cont("root", "root"));
        repoConturi.adaugare(new Cont("test", "test"));
        repoConturi.adaugare(new Cont("chise_b", "pass"));
    }
}
