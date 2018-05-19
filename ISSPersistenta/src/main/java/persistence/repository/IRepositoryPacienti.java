package persistence.repository;

import model.Pacient;

public interface IRepositoryPacienti extends CRUDRepository<Integer, Pacient> {

    Pacient cautaPacientDupaCNP(String CNP);
}
