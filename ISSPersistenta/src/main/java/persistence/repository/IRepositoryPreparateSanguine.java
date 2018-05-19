package persistence.repository;

import model.PreparatSanguin;
import model.TipPreparatSanguin;

import java.util.List;

public interface IRepositoryPreparateSanguine extends CRUDRepository<Integer, PreparatSanguin> {
    int cautareAnalizaDupaPreparat(int idPreparatSanguin);
    List<PreparatSanguin> cautareUltimeleNPreparateSanguine(int n);
}
