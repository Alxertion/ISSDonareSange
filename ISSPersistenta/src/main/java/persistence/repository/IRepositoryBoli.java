package persistence.repository;

import model.Boala;

public interface IRepositoryBoli extends CRUDRepository<Integer, Boala> {

    Boala cautareDupaNume(String numeBoala);
}
