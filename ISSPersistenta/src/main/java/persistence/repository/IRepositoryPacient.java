package persistence.repository;

import model.Pacient;

public interface IRepositoryPacient extends CRUDRepository<Integer, Pacient> {

    Pacient cautaPacientDupaCNP(String CNP);
}
