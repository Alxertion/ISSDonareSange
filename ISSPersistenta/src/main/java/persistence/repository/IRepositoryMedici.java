package persistence.repository;

import model.Cont;
import model.Medic;

import java.util.*;

/**
 * 
 */
public interface IRepositoryMedici extends CRUDRepository<Integer, Medic> {
    boolean verificaUsername(String username);
}