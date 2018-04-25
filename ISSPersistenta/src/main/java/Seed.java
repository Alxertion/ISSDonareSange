import model.Cont;
import persistence.repository.IRepositoryConturi;
import persistence.repository.RepositoryConturi;

public class Seed {

    IRepositoryConturi repoConturi;

    public Seed(){
        repoConturi = new RepositoryConturi();
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
}
