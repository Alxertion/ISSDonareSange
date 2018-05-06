package persistence.repository;

import model.PreparatSanguin;
import model.TipPreparatSanguin;

public interface IRepositoryPreparateSanguine extends CRUDRepository<Integer, PreparatSanguin> {
    int cautareAnalizaDupaPreparat(int idPreparatSanguin);
}
