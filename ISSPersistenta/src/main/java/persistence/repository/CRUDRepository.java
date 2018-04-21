package persistence.repository;

import model.PreparatSanguin;

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
    public Iterable<PreparatSanguin> getAll();

}