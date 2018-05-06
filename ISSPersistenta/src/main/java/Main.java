import model.Cont;
import persistence.repository.HibernateFactory;
import persistence.repository.IRepositoryConturi;
import persistence.repository.RepositoryConturi;

public class Main {

    public static void main(String[] args){

        Seed seed = new Seed();

        seed.seed();



        HibernateFactory.closeFactory();

    }
}
