package persistence.repository;

import model.Donator;

import java.util.*;

/**
 * 
 */
public interface IRepositoryDonatori extends CRUDRepository<Integer, Donator> {

    Donator findDonatorByUsername(String username);

}