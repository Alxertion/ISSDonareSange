package persistence.repository;

import model.Donator;
import model.PreparatSanguin;
import model.TipPreparatSanguin;

import java.util.*;

/**
 * 
 */
public interface IRepositoryDonatori extends CRUDRepository<Integer, Donator> {

    Donator findDonatorByUsername(String username);
    Donator findDonatorByEmail(String email);
    Donator findDonatorByCNP(String CNP);

}