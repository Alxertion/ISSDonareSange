package model;

import java.io.Serializable;
import java.util.*;

/**
 * 
 */
public class Cont implements Serializable {

    /**
     * Default constructor
     */
    public Cont() {
    }

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private String parola;



    /**
     * @param username
     */
    public Cont(String username,String parola) {
        this.username=username;
        this.parola=parola;
    }

    /**
     * @return
     */
    public String getUsername() {
        // TODO implement here
        return username;
    }

    public String getParola(){
        return parola;
    }


}