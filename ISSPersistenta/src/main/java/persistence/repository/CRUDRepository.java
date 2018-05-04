package persistence.repository;

import model.PreparatSanguin;

import java.util.List;

/**
 * 
 */
public interface CRUDRepository<ID, E> {

    /**
     * @param element
     */
    public void adaugare(E element);

    /**
     * @param element
     */
    public void modificare(E element);

    /**
     * @param id 
     * @return
     */
    public E stergere(ID id);

    /**
     * @param id 
     * @return
     */
    public E cautare(ID id);

    /**
     * @return
     */
    public List<E> getAll();

}