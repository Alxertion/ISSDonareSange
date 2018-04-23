import model.Cont;
import persistence.repository.HibernateFactory;
import persistence.repository.IRepositoryConturi;
import persistence.repository.RepositoryConturi;

public class Main {

    public static void main(String[] args){

        IRepositoryConturi repoConturi = new RepositoryConturi();
        repoConturi.adaugare(new Cont("root", "root"));
        HibernateFactory.closeFactory();
    }
}
