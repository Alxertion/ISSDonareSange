package persistence.repository;

import model.PersonalTransfuzii;

import java.util.*;

/**
 * 
 */
public interface IRepositoryPersonalTransfuzii extends CRUDRepository<Integer, PersonalTransfuzii> {
    boolean verificaUsername(String username);
}